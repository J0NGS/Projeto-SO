package mars.mips.SO.ProcessManager;

public class Escalonador {

    ///Metodo para escolnar o processo
    public static void escalonar(){
       PCB procExec = TabelaProcessos.removerProcTop();
        if(!procExec.getEstadoProcesso().equals("Bloqueando"))
            TabelaProcessos.adicionarProc(procExec);
        }   
}