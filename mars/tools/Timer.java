package mars.tools;

import mars.mips.hardware.Memory;

import javax.swing.*;
import java.awt.*;

public class Timer extends AbstractMarsToolAndApplication {

    private static String name    = "Timer";
    private static String version = "Version 0.1 (João goulart)";
    private static String heading = "Timer";

    public int totalInstructions;
    public int instructionsCount;

    public JButton initCountButton;
    public JTextField quantInstructionsField;

    /**
     * Simple constructor
     *
     * @param title   String containing title bar text
     * @param heading
     */
    public Timer(String title, String heading) {
        super(title,heading);
    }

    public Timer() {
        super(name + ", " + version, heading);
    }

    @Override
    public String getName() {
        return name;
    }
    //	@Override
    protected void addAsObserver() {
        addAsObserver(Memory.textBaseAddress, Memory.textLimitAddress);
    }

    @Override
    protected JComponent buildMainDisplayArea() {
        // Create everything
        JPanel panel = new JPanel();
        panel.add(new JLabel("Quantidade de instruções: "));
        // form para quantidade de instrucoes a serem contadas
        quantInstructionsField = new JTextField( 10);
        panel.add(quantInstructionsField);

        // botao iniciar contagem
        initCountButton = new JButton("Iniciar contagem");
        panel.add(initCountButton);


        return panel;
    }
    // @Override
    protected void reset() {
        instructionsCount = 0;
        updateDisplay();
    }

    @Override
    protected void updateDisplay() {
    }
}