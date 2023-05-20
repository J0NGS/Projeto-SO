package mars.mips.SO.ProcessManager;
import java.util.ArrayList;
import java.util.List;

import mars.mips.SO.Memory.GerenciadorMemoria;
import mars.mips.SO.Memory.TabelaVirtual;



public class TabelaProcessos {
    private static int lastAdress = 0;
    private static List<PCB> processos = new ArrayList<PCB>();
    
    public static void adicionarProcesso(PCB processo) {
        processo.setEstadoProcesso("Pronto");
        
        if(processo.getAdressTail() == 0) {
            processo.setAdressTail(lastAdress);
        }

        int tamanho = processos.size();
        if(tamanho > 0) {
            PCB penultimoProcesso = processos.get(tamanho - 1);

            if(penultimoProcesso.getAdressTail() == 0) {
                penultimoProcesso.setAdressTail(
                    processo.getEnderecoInicio() - 4
                );
            }
        }

        processos.add(processo);
    }

    public static void create(int adressInit, int prioridade){
        PCB processo = new PCB(
            adressInit, prioridade, 
            new TabelaVirtual(GerenciadorMemoria.getQntMaxBlocos())
        );
        adicionarProcesso(processo);
    }

    public static int getTamanhoLista() {
        return processos.size();
    }

    public static PCB getProcTop() {
        return processos.size() == 0 ? null : processos.get(0);
    }

    public static PCB getProcExec() {
        PCB procTop = getProcTop();

        if(
            procTop == null || 
            !procTop.getEstadoProcesso().equals("Executando")
        ) {
            return null;
        }

        return procTop;
    }

    public static boolean remover(PCB processo) {
        PCB procTop = getProcTop();

        if(processo == procTop) {
            removerProcessoTopo();
            return true;
        }

        return processos.remove(processo);
    }

    public static PCB removerProcessoTopo() {
        PCB processoRemovido = processos.remove(0);
        return processoRemovido;
    }

    public static List<PCB> getListaProcessos() {
        return processos;
    }

    public static int getUltimoEnderecoPrograma() {
        return lastAdress;
    }

    public static void setUltimoEnderecoPrograma(
        int ultimoEnderecoProgramaRecebido
    ) {
        lastAdress = ultimoEnderecoProgramaRecebido;
    }

} 