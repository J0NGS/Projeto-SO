package mars.mips.SO.Memory;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

import mars.mips.SO.ProcessManager.PCB;
import mars.mips.SO.ProcessManager.TabelaProcessos;
import mars.mips.hardware.RegisterFile;
import mars.simulator.Simulator;
import mars.util.SystemIO;

public class GerenciadorMemoria {
    
    private static int tamPagVirtual = 4; 

   
    private static int qntMaxBlocos = 16; // 4, 8, 16, 32
    
    //-----------------------------------------
    private static String algoritmoSubstituicao = "FIFO";

    public static void verificarMemoria() {
        PCB procExec = TabelaProcessos.getProcTop();
        if(procExec == null) return;

        int pc = RegisterFile.getProgramCounter();
        // TODO
        
        if (procExec.getInicioPrograma() > pc || procExec.getFimPrograma() < pc){
            SystemIO.printString(
                "Os limites de endereço do processo em execução, que possui limite superior: " + 
                procExec.getInicioPrograma() + " e limite inferior: " +
                procExec.getFimPrograma() + " estão fora da área de acesso.\n"
            );
            SystemIO.printString("Endereço da tentativa de acesso: " + pc);


            Simulator.getInstance().stopExecution(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                }
            });
        } else {

        }
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