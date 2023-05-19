package mars.mips.SO.ProcessManager;

import java.util.LinkedList;
import java.util.Queue;

public class TabelaProcessos {

    private static Queue<PCB> processos = new LinkedList<>();                                  //Processo seno executado

    //Criando um novo processo
    public static void novoProcesso(int adress){
        PCB processo = new PCB(adress);
        adicionarProc(processo);
    }
    
    public static PCB getProcTop(){
        return processos.peek();
    }       

    public static void adicionarProc(PCB pcb) {
        if(processos.size() == 0){
            pcb.setEstadoProcesso("Executando");
            pcb.PCBRegistradores();
        }
        else
            pcb.setEstadoProcesso("Pronto");
        
        processos.add(pcb);
    }

    public static PCB removerProcTop() {
        PCB processo = processos.remove();
        if(processos.size() != 0){
            PCB top = getProcTop();
            top.setEstadoProcesso("Executando");
             top.PCBRegistradores();
        }   
        return processo;
    }
} 