package mars.mips.SO.Memory;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

import mars.mips.SO.ProcessManager.PCB;
import mars.mips.SO.ProcessManager.TabelaProcessos;
import mars.mips.hardware.RegisterFile;
import mars.simulator.Simulator;
import mars.util.SystemIO;

public class GerenciadorMemoria {
    /*Foram utilizados valores padrões encontrados 
    no livro de Tanenbaum para os possíveis tamanhos 
    de página. A de 4kb é uma das mais usadas no mercado*/
    private static int tamPagVirtual = 4; //4kb, 8kb, 16kb, 32kb, 64kb

    //-----------------------------------------    
    /*Quantidade máxima de molduras na memória física.
    A quantidade de molduras também pode ser entendida 
    como a quantidade máxima de páginas virtuais mapeadas 
    permitida.*/
    private static int qntMaxBlocos = 16; // 4, 8, 16, 32
    
    //-----------------------------------------
    private static String algoritmoSubstituicao = "FIFO";

    public static void verificarMemoria() {
        PCB procExec = TabelaProcessos.getProcExect();
        if(procExec == null) return;

        int pc = RegisterFile.getProgramCounter();
        // TODO
        /*
        if (procExec.getInicioPrograma() > pc || procExec.getEnderecoFim() < pc){
            SystemIO.printString(
                "Os limites de endereço do processo em execução, que possui limite superior: " + 
                procExec.getInicioPrograma() + " e limite inferior: " +
                // TODO
                //procExec.getEnderecoFim() + " estão fora da área de acesso.\n"
            );
            SystemIO.printString("Endereço da tentativa de acesso: " + pc);

            /*System.out.println(
                "Os limites de endereço do processo em execução, que possui limite superior: " + 
                procExec.getEnderecoInicio() + " e limite inferior: " +
                procExec.getEnderecoFim() + " estão fora da área de acesso."
            );
            System.out.println("Endereço da tentativa de acesso: " + pc);

            Simulator.getInstance().stopExecution(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                }
            });
        } else {

        }
         * 
         */
    }

    public static int getTamPagVirtual() {
        return tamPagVirtual;
    }

    public static void setTamPagVirtual(int tamPagVirtual) {
        GerenciadorMemoria.tamPagVirtual = tamPagVirtual;
    }

    public static int getQntMaxBlocos() {
        return qntMaxBlocos;
    }

    public static void setQntMaxBlocos(int qntMaxBlocos) {
        GerenciadorMemoria.qntMaxBlocos = qntMaxBlocos;
    }

    public static String getAlgoritmoSubstituicao() {
        return algoritmoSubstituicao;
    }

    public static void setAlgoritmoSubstituicao(String algoritmoSubstituicao) {
        GerenciadorMemoria.algoritmoSubstituicao = algoritmoSubstituicao;
    }
}