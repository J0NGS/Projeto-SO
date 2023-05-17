package mars.mips.SO.ProcessManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TabelaProcessos {

    private static List<PCB> procProntos = new ArrayList<>();      //Lista de processos prontos para serem executados
    private static PCB procExec;                                   //Processo seno executado

    

    //Criando um novo processo
    public static void novo(int inicio){
        PCB processo = new PCB(inicio);
        procProntos.add(processo);
        Escalonador.escalonar();
    }
    
    ///Atualiza processo para executado
    public static void executando(){
        procExec.registradoresPCB();
        procExec.PCBRegistradores();
    }


    public static PCB getProcExect(){
        return procExec;
    }       
    
    public static void setProcExec(PCB pcb){
        //setando processo como pronto e adicionando na lista
        procExec.setEstadoProcesso("Pronto");
        procProntos.add(procExec);

        //setando o novo processo como exec
        procExec = pcb;
        procExec.setEstadoProcesso("Exec");
    }

}