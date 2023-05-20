package mars.mips.SO.ProcessManager;
import javax.xml.transform.Source;
import mars.mips.SO.*;
import java.util.LinkedList;
import java.util.Queue;
import mars.mips.hardware.RegisterFile;


public class TabelaProcessos {

    private static Queue<PCB> processos = new LinkedList<>();                                  //Processo sendo executado
    private static PCB procExec; // processo executando
    public static Queue<PCB> getProcessos() {
		return processos;
	}
	public static void setProcessos(Queue<PCB> processos) {
		TabelaProcessos.processos = processos;
	} 


    //Criando um novo processo
    public static void novoProcesso(int adress){
        PCB processo = new PCB(adress);
        adicionarProc(processo);
    }
    
    public static PCB getProcTop(){
        return processos.peek();
    }       

    public static void adicionarProc(PCB pcb) {
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

    public static String algoritmoPadrao = "FIFO"; // algoritmo de escalonamento padrao


	public static String getTypeAlgoritmo() {
		return algoritmoPadrao;
	}

	public static void setTypeAlgoritmo(String str) {
		TabelaProcessos.algoritmoPadrao = str;
	}

    public static void processChange(String algoritmo) {

        if(getProcTop() != null) { // processo sendo executado
			System.out.println("Salvando");
			procExec.setEstadoProcesso("ready"); // mudando meu estado
			procExec.setInicioPrograma(RegisterFile.getProgramCounter());
			procExec.registradoresPCB();  // salvando contexto
		}

        switch (algoritmo) {
            case "FIFO":
			    if(Escalonador.fifo()) {
				    RegisterFile.setProgramCounter(procExec.getNumeroDeRegistradores());

				    System.out.println("Indo para: " + RegisterFile.getProgramCounter());
				    procExec.PCBRegistradores();
			    }
                    break;
            case "PFixa":    
                if(Escalonador.fixedPriority()) {
				    for(int i = 0;  i < procExec.getValorRegistros().length; i++) {
					    RegisterFile.updateRegister(i, procExec.getInicioPrograma());
				    }
			    }
			    if(procExec != null) {
				    RegisterFile.setProgramCounter(procExec.getInicioPrograma());
			    }
                    break;
            case "Loteria": 
                if(Escalonador.lottery()) {
                    for(int i = 0;  i < procExec.getRegistradores().length; i++) {
                        RegisterFile.updateRegister(i, procExec.getInicioPrograma());
                    }
                }
                if(procExec != null) {
                    RegisterFile.setProgramCounter(procExec.getInicioPrograma());
                }
                break;
            default:
                System.out.println("indo parar aqui");
            break;
         }
    }

} 