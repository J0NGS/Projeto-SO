package mars.tools;
import javax.swing.*;

import javafx.event.ActionEvent;
import mars.mips.SO.ProcessManager.TabelaProcessos;
import mars.mips.SO.ProcessManager.Escalonador;

import java.awt.*;
import java.awt.event.*;
import mars.*;
import java.util.Observable;
import mars.util.SystemIO;



import mars.mips.hardware.AccessNotice;
import mars.mips.hardware.Memory;
import mars.mips.hardware.MemoryAccessNotice;


/*
Copyright (c) 2003-2006,  Pete Sanderson and Kenneth Vollmar
Developed by Pete Sanderson (psanderson@otterbein.edu)
and Kenneth Vollmar (kenvollmar@missouristate.edu)
Permission is hereby granted, free of charge, to any person obtaining 
a copy of this software and associated documentation files (the 
"Software"), to deal in the Software without restriction, including 
without limitation the rights to use, copy, modify, merge, publish, 
distribute, sublicense, and/or sell copies of the Software, and to 
permit persons to whom the Software is furnished to do so, subject 
to the following conditions:
The above copyright notice and this permission notice shall be 
included in all copies or substantial portions of the Software.
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, 
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF 
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. 
IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR 
ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION 
WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
(MIT license, http://www.opensource.org/licenses/mit-license.html)
*/

public class EscalonamentoPreemptivo extends AbstractMarsToolAndApplication {

   private static String heading =  "Escalonamento preemptivo";
   private static String version = " Version 1.0";
   public static boolean podeExecutar = true;

    public EscalonamentoPreemptivo(String title, String heading) {
      super(title,heading);
   }


    public EscalonamentoPreemptivo() {
      super (heading+", "+version, heading);
   }


    public static void main(String[] args) {
      new EscalonamentoPreemptivo(heading+", "+version,heading).go();
   }

    // serve para setar o nome da tool
    public String getName() {
      return "Escalonamento Preemptivo";
   }

  protected int ultimoEndereco = -1; // comparativo de endereço
  protected int counter = 0;
	private JTextField counterField; // campo para contador

	protected int countTimer = 10; // Timer de interrupção
	
	protected int countInter = 0; // contador de interrupções
	private JTextField counterInterField; // campo para o countInter
   
  protected int countInst = 3;
	private JProgressBar progressbarInst;


	private JToggleButton timerOn; // ligar o timer
	private JSpinner timerConfig;	

  @Override
	protected JComponent buildMainDisplayArea() {
		JPanel panel = new JPanel(new GridBagLayout());

		counterField = new JTextField("0", 10);
		counterField.setEditable(false);

		counterInterField = new JTextField("0", 10);
		counterInterField.setEditable(false);

		progressbarInst = new JProgressBar(JProgressBar.HORIZONTAL);
		progressbarInst.setStringPainted(true);

		timerOn = new JToggleButton("ON/OFF"); 
		timerOn.setToolTipText("Enable interruption");

		timerConfig = new JSpinner();
		timerConfig.setModel(new SpinnerNumberModel(10, 2, 100, 1));
		timerConfig.setToolTipText("Sets the time for the interruption");

		// Add them to the panel

		// Fields
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.LINE_START;
		c.gridheight = c.gridwidth = 1;
		c.gridx = 3;
		c.gridy = 1;
		c.insets = new Insets(0, 0, 17, 0);
		panel.add(counterField, c);

		c.insets = new Insets(0, 0, 0, 0);
		c.gridy++;
		panel.add(counterInterField, c);

		// progress bar
		c.gridy++;
		panel.add(progressbarInst,c);
		// spinner
		c.gridy++;
		panel.add(timerConfig, c);

		// Labels
		c.anchor = GridBagConstraints.LINE_END;
		c.gridx = 1;
		c.gridwidth = 2;
		c.gridy = 1;
		c.insets = new Insets(0, 0, 17, 0);
		panel.add(new JLabel("Instructions so far: "), c);

		c.insets = new Insets(0, 0, 0, 0);
		c.gridx = 2;
		c.gridwidth = 1;
		c.gridy++;
		panel.add(new JLabel("Interruptions so far: "), c);
		c.gridy++;
		panel.add(new JLabel("Time so far: "), c);
		c.gridy++;
		panel.add(new JLabel("Timer: "), c);

		// lock
		c.insets = new Insets(3, 3, 3, 3);
		c.gridx = 4;
		c.gridy = 2;
		panel.add(timerOn, c);

   		return panel;
	}

//	@Override
	protected void addAsObserver() {
		addAsObserver(Memory.textBaseAddress, Memory.textLimitAddress);
	}

//  @Override
	protected void processMIPSUpdate(Observable memory, AccessNotice notice) {
		podeExecutar = true;
		if (notice.getAccessType() != AccessNotice.READ) return;
		MemoryAccessNotice m = (MemoryAccessNotice) notice;
		int a = m.getAddress();
		if (a == ultimoEndereco) return;
		if (TabelaProcessos.getProcExect() != null) {
			ultimoEndereco = a;
			++counter;
			if(timerOn.isSelected()) {
				++countInst;
				if (countInst > (int)timerConfig.getValue()) {
					podeExecutar = false;
					SystemIO.printString("-- Hora de trocar!\n");
					++countInter; // incrementa interrupções
					countInst = 0; // zera o progressBar				
				//ProcessesTable.setProcessoExecutando("roteamento");  // aqui era processChange
        Escalonador.escalonar(); // vai ser esse?
				}
			}
			updateDisplay();
		}
	}

//  @Override
	protected void initializePreGUI() {
		countInst = 0;
		countTimer = 10;
		countInter = 0;
		ultimoEndereco = -1;
	}

//  @Override
	protected void reset() {
		countInst = 0;
		countTimer = 10;
		countInter = 0;
		ultimoEndereco = -1;		
		updateDisplay();
	}
//  @Override
	protected void updateDisplay() {
		counterField.setText(String.valueOf(counter));
		counterInterField.setText(String.valueOf(countInter));
		progressbarInst.setValue(countInst);
		progressbarInst.setMaximum((int)timerConfig.getValue());
	}
}