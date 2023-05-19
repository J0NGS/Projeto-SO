package mars.tools;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import mars.*;


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

    public EscalonamentoPreemptivo(String title, String heading) {
      super(title,heading);
   }


    public EscalonamentoPreemptivo() {
      super (heading+", "+version, heading);
   }


    public static void main(String[] args) {
      new IntroToTools(heading+", "+version,heading).go();
   }

    // serve para setar o nome da tool
    public String getName() {
      return "Escalonamento Preemptivo";
   }

    protected JComponent buildMainDisplayArea() {
      Box boxArea = Box.createHorizontalBox();

      JTextField textField = new JTextField();
      boxArea.add(textField);

      ActionListener taskPerformer = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent evt) {
            textField.setText("Atualizou");
        }
      };

      JButton startButton = new JButton("Iniciar");
      startButton.addActionListener(
          new ActionListener() {
            @Override
              public void actionPerformed(ActionEvent e) {
                String textFieldValue = textField.getText();
                textField.setEditable(false);
                Timer timer = new Timer(Integer.parseInt(textFieldValue), taskPerformer);
                timer.start();
              }
      });

      startButton.addKeyListener(new EnterKeyListener(startButton));
      boxArea.add(startButton);

      return new JScrollPane(boxArea);
   }

}