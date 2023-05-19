package mars.mips.SO.ProcessManager;
import java.util.PriorityQueue;
import java.util.Queue;

public class Escalonador {

    //Fila de processos
    private static Queue<PCB> procProntos = new PriorityQueue<PCB>();

    ///Metodo para escolnar o processo
    public static void escalonar(){
        PCB processoExecutando = TabelaProcessos.getProcExect();
        TabelaProcessos.adicionarProcPronto(processoExecutando);
        TabelaProcessos.setProcExec(procProntos.remove());
    }   
}