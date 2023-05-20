package mars.mips.SO.ProcessManager;
import mars.mips.SO.Memory.TabelaVirtual;
import mars.mips.hardware.RegisterFile;
import java.util.Random;

public class PCB {
    private int adressInit = 0;     // endereço de incio;
    private int adressTail = 0;     // endereço de Final;
    private int PID;
    private String estado;
    private int prioridade;
    private int[] valorRegistros;
    private static final int registradores = 34;
    private TabelaVirtual tabelaVirtual;
    
    public PCB(int adressInit, int prioridade, TabelaVirtual tabelaVirtual){
        valorRegistros = new int[registradores];
        setEnderecoInicio(adressInit);
        setPrioridade(prioridade);
        estado = "Pronto";
        PID = new Random().nextInt(Integer.MAX_VALUE);
        setTabelaVirtual(tabelaVirtual);
    }

    public void RegistradoresPCB(){
        for(int i = 0; i < registradores; i++){
            if (i == 31) valorRegistros[i] = RegisterFile.getProgramCounter();
            else if (i >= 32) valorRegistros[i] = RegisterFile.getValue(i+1);
            else valorRegistros[i] = RegisterFile.getValue(i);
        }
    }

    public void PCBRegistradores(){
        for(int i = 0; i < registradores; i++) {
            if(i == 31) continue;
                
            if (i >= 32) RegisterFile.updateRegister(i+1, valorRegistros[i]);
            else RegisterFile.updateRegister(i, valorRegistros[i]);
        }

        int pc = valorRegistros[31] == 0 ? adressInit : valorRegistros[31];
        RegisterFile.setProgramCounter(pc);
    }

    public int getNumeroDeRegistradores() {
        return registradores;
    }

    public int[] getValorRegistros() {
        return valorRegistros;
    }

    public void setValorRegistros(int[] valorRegistros) {
        this.valorRegistros = valorRegistros;
    }

    public String getEstadoProcesso() {
        return estado;
    }

    public void setEstadoProcesso(String estado) {
        this.estado = estado;
    }

    public int getEnderecoInicio() {
        return adressInit;
    }

    public void setEnderecoInicio(int adress) {
        this.adressInit = adress;
    }

    public int getPID() {
        return PID;
    }

    public void setPID(int PID) {
        this.PID = PID;
    }

    public int getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(int prioridade) {
        this.prioridade = prioridade;
    }

    public int getAdressTail() {
        return adressTail;
    }

    public void setAdressTail(int adress) {
        this.adressInit = adressTail;
    }

    public TabelaVirtual getTabelaVirtual() {
        return tabelaVirtual;
    }

    public void setTabelaVirtual(TabelaVirtual tabelaVirtual) {
        this.tabelaVirtual = tabelaVirtual;
    }
}