package mars.mips.SO.ProcessManager;

import java.util.ArrayList;
import java.util.List;

public class Semaforo {
    private int valor;
    private int adress;
    private List<PCB> procBloq = new ArrayList<>();

    public Semaforo(int valor, int adress){
        this.valor = valor;
        this.adress = adress;
    }

    //Função para limpar a lista de processos bloqueados
    public void killLista(){
        procBloq.clear();
    }

    //Função para incrimentar valor na lista
    public void addProc(){
        if(procBloq.size() > 0){
            PCB procPronto = procBloq.remove(0);
            TabelaProcessos.adicionarProc(procPronto);;
        }else
            valor++;
    }

    //Função para decrementar o valor na lista
    public void removeProc(){
        if(valor > 0)
            valor--;
        else{
            PCB procExec = TabelaProcessos.getProcTop();
            procExec.setEstadoProcesso("Bloqueando");
            procBloq.add(procExec);
            Escalonador.escalonar();
        }
    }

    //Getters
    public int getValor() {
        return this.valor;
    }

    public int getAdress() {
        return this.adress;
    }

    public List<PCB> getProcBloq() {
        return this.procBloq;
    }

}
