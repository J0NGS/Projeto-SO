package mars.mips.SO.ProcessManager;

import java.util.ArrayList;
import java.util.List;

import mars.tools.Timer;

public class Semaforo {
    private int valor;
    private int adress;

    private List<PCB> processos;

    public Semaforo(int valor, int adress) {
        this.valor = valor;
        this.adress = adress;
    }

    public void KillList() {
        processos.clear();
    }

    public void incrementarValor() {
        if(processos.size() > 0) {
            PCB processoPronto = processos.remove(0);
            TabelaProcessos.adicionarProcesso(processoPronto);
        } else ++valor;
    }

    public void decrementarValor() {
        if(valor > 0) --valor;
        else {
            PCB processoExecutando = TabelaProcessos.getProcExec();
            boolean processoExiste = processoExecutando != null;

            if(processoExiste) {
                processoExecutando.setEstadoProcesso("Bloqueado");
                processos.add(processoExecutando);
            }

            Escalonador escalonador = new Escalonador(Timer.getAlgoritmoSelecionado());
            escalonador.escalonar(processoExiste);
        }
    }

    public int getValor() {
        return valor;
    }

    public int getAdress() {
        return adress;
    }
}
