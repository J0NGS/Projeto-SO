package mars.mips.SO.ProcessManager;
import mars.mips.hardware.RegisterFile;
import java.util.Random;

// Classe que define a PCB
public class PCB {

    // Informações do processo
    private int inicioPrograma;
    private int fimPrograma;
    private int PID;
    private int prioridade;
    private String estadoProcesso;
    // Informações do hardware
    private int[] registradores;
    private static final int quantidadeRegistradores= 34;
    
    // Construtor do objeto PCB
    public PCB(int inicioPrograma, int prioridade) {
        registradores = new int [ quantidadeRegistradores ];
        this.inicioPrograma = inicioPrograma;
        this.prioridade = prioridade;
        estadoProcesso = "Pronto";
        PID = new Random().nextInt( Integer.MAX_VALUE );
    }
    // ------------------------------------------------------

    // Método de cópia registradores -> PCB
    public void registradoresPCB() {
          for(int i = 0; i < quantidadeRegistradores; i++){
            if (i == 31) {
                registradores[i] = RegisterFile.getProgramCounter();
            }
            else if (i >= 32){ 
                registradores[i] = RegisterFile.getValue(i+1);
            }
            else registradores[i] = RegisterFile.getValue(i);
        }
    }
    // ------------------------------------------------------

    // Método de cópia PCB -> registradores
    public void PCBRegistradores() {
       for(int i = 0; i < quantidadeRegistradores; i++) {
            if(i == 31){ 
                continue;
            }
            if (i >= 32){ 
                RegisterFile.updateRegister(i+1, registradores[i]);
            }
            else RegisterFile.updateRegister(i, registradores[i]);
        }

        int pc = registradores[31] == 0 ? inicioPrograma : registradores[31];
        RegisterFile.setProgramCounter(pc);
    }
    // ------------------------------------------------------

    // inicioPrograma -> get e set
    public int getInicioPrograma(){
        return this.inicioPrograma;
    }
    
    public void setInicioPrograma(int inicioPrograma) {
        this.inicioPrograma = inicioPrograma;
    }
    // ------------------------------------------------------
    
    // fimprograma -> get e set
    public int getFimPrograma(){
        return this.fimPrograma;
    }
    
    public void setFimPrograma(int fimPrograma) {
        this.fimPrograma = fimPrograma;
    }
    // ------------------------------------------------------

    // PID -> get e set
    public int getPID(){
        return this.PID;
    }

    public void setPID(int PID) {
        this.PID = PID;
    }
    // ------------------------------------------------------

    // estadoProcesso -> get e set
    public String getEstadoProcesso(){
        return this.estadoProcesso;
    }

    public void setEstadoProcesso(String estadoProcesso) {
        this.estadoProcesso = estadoProcesso;
    }
    // ------------------------------------------------------
     public int getQuantidadeRegistradores() {
        return quantidadeRegistradores;
    }
    // ------------------------------------------------------
     public int[] getRegistradores() {
        return registradores;
    }
     public void setRegistradores(int[] registradores) {
        this.registradores = registradores;
    }
    // ------------------------------------------------------
     public int getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(int prioridade) {
        this.prioridade = prioridade;
    }
}