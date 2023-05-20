package mars.mips.SO.Memory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class TabelaVirtual {
    private List<EntradaTabelaVirtual> tabelaEntradas;
	
    // vetor limitado para definir 
    private List<Integer> memoriaFisica;

    public TabelaVirtual(int quantidadeMaxima) {
        memoriaFisica = new ArrayList<>(quantidadeMaxima);
        tabelaEntradas = new ArrayList<>();
    }

    private EntradaTabelaVirtual obterElementoFIFO() {
        return tabelaEntradas.get(0);
    }

    private EntradaTabelaVirtual obterElementoNRU() {
        List<EntradaTabelaVirtual> tabelaProvisoria = new ArrayList<>();
        tabelaProvisoria.addAll(tabelaEntradas);

        Collections.sort(tabelaProvisoria, new Comparator<EntradaTabelaVirtual>() {
            @Override
            public int compare(EntradaTabelaVirtual left, EntradaTabelaVirtual right) {
                int leftValue = (left.getPaginaReferenciada() ? 1 : 0) + 
                (left.getPaginaModificada() ? 1 : 0);

                int rightValue = (right.getPaginaReferenciada() ? 1 : 0) + 
                (right.getPaginaModificada() ? 1 : 0);

                if(leftValue == rightValue) return 0;
                if(leftValue < rightValue) return -1;
                return 1;
            }
        });

        return tabelaProvisoria.get(0);
    }

    private EntradaTabelaVirtual obterElementoSegundaChance() {
        EntradaTabelaVirtual elementoIterativo = obterElementoFIFO();

        while(elementoIterativo.getPaginaReferenciada()) {
            tabelaEntradas.remove(elementoIterativo);
            tabelaEntradas.add(elementoIterativo);
            elementoIterativo = obterElementoFIFO();
        }

        return elementoIterativo;
    }

    private EntradaTabelaVirtual obterElementoLRU() {
        List<EntradaTabelaVirtual> tabelaProvisoria = new ArrayList<>();
        tabelaProvisoria.addAll(tabelaEntradas);

        Collections.sort(tabelaProvisoria, new Comparator<EntradaTabelaVirtual>() {
            @Override
            public int compare(EntradaTabelaVirtual left, EntradaTabelaVirtual right) {
                Date leftValue = left.getUltimaUtilizacao();
                Date rightValue = right.getUltimaUtilizacao();

                return leftValue.compareTo(rightValue);
            }
        });

        return tabelaProvisoria.get(0);
    }

    private EntradaTabelaVirtual obterElementoParaSubstituir() {
        switch(GerenciadorMemoria.getAlgoritmoSubstituicao()) {
            case "NRU": return obterElementoNRU();
            case "FIFO": return obterElementoFIFO();
            case "Segunda Chance": return obterElementoSegundaChance();
            case "LRU": return obterElementoLRU();
            default: return obterElementoFIFO();
        }
    }

    public void adicionarElementoMemoria(int memoria, int memoriaVirtual) {
        int tamanhoMemoriaFisica = memoriaFisica.size();

        if(tamanhoMemoriaFisica == GerenciadorMemoria.getQntMaxBlocos()) {
            EntradaTabelaVirtual novaEntrada = new EntradaTabelaVirtual(
                memoriaVirtual, false, false
            );

            EntradaTabelaVirtual elementoParaSubstituir = obterElementoParaSubstituir();
            int memoriaFisicaEncontrada = UnidadeGerenciamentoMemoria.traduzirParaEnderecoFisico(
                elementoParaSubstituir.getNumMolduraMapeada()
            );
            
            memoriaFisica.remove(memoriaFisicaEncontrada);
            tabelaEntradas.remove(elementoParaSubstituir);
        } else memoriaFisica.add(memoria);
    }

    public void adicionarElementoTabela(EntradaTabelaVirtual elemento) {
        tabelaEntradas.add(elemento);
    }

    public void removerElementoTabela(EntradaTabelaVirtual elemento) {
        tabelaEntradas.remove(elemento);
    }

    public List<EntradaTabelaVirtual> getTabelaEntradas() {
        return tabelaEntradas;
    }

    public void setTabelaEntradas(List<EntradaTabelaVirtual> tabelaEntradas) {
        this.tabelaEntradas = tabelaEntradas;
    }
    
    public List<Integer> getMemoriaFisica() {
        return memoriaFisica;
    }

    public void setMemoriaFisica(List<Integer> memoriaFisica) {
        this.memoriaFisica = memoriaFisica;
    }

}
