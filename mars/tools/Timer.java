package mars.tools;
import javax.swing.*;

import mars.ProcessingException;
import mars.mips.SO.ProcessManager.TabelaProcessos;
import mars.mips.SO.ProcessManager.Escalonador;

import java.awt.*;
import java.awt.event.*;
import java.util.Observable;


import mars.mips.hardware.AccessNotice;
import mars.mips.hardware.Memory;
import mars.mips.hardware.MemoryAccessNotice;
import mars.mips.hardware.RegisterFile;
 

@SuppressWarnings("serial")
public class Timer extends AbstractMarsToolAndApplication {
    
   private static String heading =  "Escalonamento preemptivo";
   private static String version = "Version 1.0";
   public static boolean canExec = true;
   private int ultimoPC = 0;

   private JComboBox<String> selecaoAlgoritmo = new JComboBox<String>(new String[]{"FIFO","PFixa", "Loteria"});
   private static String algoritmoSelecionado = "FIFO";
	
    public Timer(String title, String heading) {
      super(title,heading);
   }
     
     
    public Timer() {
      super (heading+", "+version, heading);
   }
         
         
   
    public static void main(String[] args) {
      new Timer(heading+", "+version,heading).go();
   }
    
   
    public String getName() {
      return "Timer";
   }

   public static String getAlgoritmoSelecionado() {
	   return algoritmoSelecionado;
   }
    
	protected int lastAddress = -1; // comparativo de endereço
    
	private JTextField counterField;
	/**
     * Timer definition.
     */
	protected int countTimer = 10; // Timer de interrupção
	/**
     * Number of interruptions until now.
     */
	protected int countInter = 0; // contador de interrupções
	private JTextField counterInterField;
    /**
     * Number of instructions until interruption.
     */
    protected int countInst = 3;
	private JProgressBar progressbarInst;
	
	/**
	 * Configuration tools
	*/
	private JToggleButton timerOn;
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

		timerOn = new JToggleButton("Habilitar interrupções"); 
		timerOn.setToolTipText("Enable interruption");
		
		timerConfig = new JSpinner();
		timerConfig.setModel(new SpinnerNumberModel(10, 2, 100, 1));
		timerConfig.setToolTipText("Sets the time for the interruption");
		
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
		panel.add(new JLabel("Instruções"), c);
		
		c.insets = new Insets(0, 0, 0, 0);
		c.gridx = 2;
		c.gridwidth = 1;
		c.gridy++;
		panel.add(new JLabel("Interrupções "), c);
		c.gridy++;
		panel.add(new JLabel("Tempo total "), c);
		c.gridy++;
		panel.add(new JLabel("Tempo: "), c);
		
		// lock
		c.insets = new Insets(3, 3, 3, 3);
		c.gridx = 4;
		c.gridy = 2;
		panel.add(timerOn, c);

		selecaoAlgoritmo.setSelectedItem(0);
		selecaoAlgoritmo.addActionListener (new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				algoritmoSelecionado = selecaoAlgoritmo.getSelectedItem().toString();
			}
		});

		panel.add(selecaoAlgoritmo);  
		
   		return panel;
	}
	
//	@Override
	protected void addAsObserver() {
		addAsObserver(Memory.textBaseAddress, Memory.textLimitAddress);
	}
	
//  @Override (Esse aqui é o antigo!!!!!)
	/*protected void processMIPSUpdate(Observable memory, AccessNotice notice) {
		canExec = true;
		if (notice.getAccessType() != AccessNotice.READ) return;
		MemoryAccessNotice m = (MemoryAccessNotice) notice;
		int a = m.getAddress();
		if (a == lastAddress) return;
		if (ProcessesTable.getProcessoTopo() != null) {
			lastAddress = a;
			++counter;
			if(timerOn.isSelected()) {
				++countInst;
				if (countInst > (int)timerConfig.getValue()) {
					canExec = false;
					SystemIO.printString("-- Hora de trocar!\n");
					++countInter; // incrementa interrupções
					countInst = 0; // zera o progressBar				
				//ProcessesTable.setProcessoExecutando("roteamento");  // aqui era processChange
        Scheduler.escalonar(); // vai ser esse?
				}
			}
			updateDisplay();
		}
	}*/
	
	protected void processMIPSUpdate(Observable memory, AccessNotice notice) {
		if(ultimoPC != 0) {
			RegisterFile.setProgramCounter(ultimoPC);
			ultimoPC = 0;
		}

		if (!notice.accessIsFromMIPS()) return;
		if (notice.getAccessType() != AccessNotice.READ) return;
		
		MemoryAccessNotice m = (MemoryAccessNotice) notice;
		int a = m.getAddress();
		if (a == lastAddress) return;

		lastAddress = a;

		if(
			TabelaProcessos.getProcExec() == null || 
			!timerOn.isSelected()
		) {
			return;
		}

		//MemoryManager.verificarMemoria();
		
		//Verifica a quantidade de instruções ultrapassou o limite do timer
		if(++countInst >= (int)timerConfig.getValue()){
			countInter++; // incrementa qnt de interrupções
			countInst = 0; //zera o contador de instruções
            
            // TODO
			
            Escalonador escalonador = new Escalonador(algoritmoSelecionado);
			escalonador.escalonar(false);
            ultimoPC = RegisterFile.getProgramCounter();
		}

		updateDisplay();
	}
	
//  @Override
	protected void initializePreGUI() {
		countInst = 0;
		countTimer = 10;
		countInter = 0;
		lastAddress = -1;
	}
	
//  @Override
	protected void reset() {
		countInst = 0;
		countTimer = 10;
		countInter = 0;
		lastAddress = -1;
		updateDisplay();
	}
//  @Override
	protected void updateDisplay() {
		counterField.setText(String.valueOf(countInst));
		counterInterField.setText(String.valueOf(countInter));
		progressbarInst.setValue(countInst);
		progressbarInst.setMaximum((int)timerConfig.getValue());
	}
}