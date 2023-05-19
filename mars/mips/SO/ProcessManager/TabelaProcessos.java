package mars.mips.SO.ProcessManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TabelaProcessos {

    private static List<PCB> procProntos = new ArrayList<>();      //Lista de processos prontos para serem executados
    private static PCB procExec;                                   //Processo seno executado

    //Criando um novo processo
    public static void novoProcesso(int inicio){
        PCB processo = new PCB(inicio);
        procProntos.add(processo);
        Escalonador.escalonar();
    }
    

    public static PCB getProcExect(){
        return procExec;
    }       
    
    public static void setProcExec(PCB pcb){
        // setando o estado para executando do processo
        pcb.setEstadoProcesso("Executando");
        procExec = pcb;
    }


    public static void adicionarProcPronto(PCB pcb) {
        if(!pcb.getEstadoProcesso().equals("Pronto")) {
            pcb.setEstadoProcesso("Pronto");
        }

        procProntos.add(pcb);
    }

    public static void removerProcPronto(PCB pcb) {
        procProntos.remove(pcb);
    }
}