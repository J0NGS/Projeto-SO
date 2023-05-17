package mars.mips.SO.ProcessManager;
import mars.mips.hardware.RegisterFile;
import java.util.Random;

// Classe que define a PCB
public class PCB {

    // Informações do processo
    private int inicioPrograma;
    private int PID;
    private String estadoProcesso;
    // Informações do hardware
    private int[] registradores;
    private final int quantidadeRegistradores = 32;
    
    // Construtor do objeto PCB
    public PCB(int inicioPrograma) {
        registradores = new int[quantidadeRegistradores];
    }
    // ------------------------------------------------------

    // Método de cópia registradores -> PCB
    public void registradoresPCB() {
        for(int i = 0; i < quantidadeRegistradores; i++) {
            registradores[i] = RegisterFile.getValue(i);
        }
    }
    // ------------------------------------------------------

    // Método de cópia PCB -> registradores
    public void PCBRegistradores() {
        for(int i = 0; i < quantidadeRegistradores; i++) {
            RegisterFile.updateRegister(i, registradores[1]);
        }
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
}