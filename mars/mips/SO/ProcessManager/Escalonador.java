package mars.mips.SO.ProcessManager;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class Escalonador {
	private String tipoEscalonamento;

	private Queue<PCB> baixaPrioridade = null;
	private Queue<PCB> mediaPrioridade = null;
	private Queue<PCB> altaPrioridade = null;

	public Escalonador(String tipoEscalonamento) {
		this.tipoEscalonamento = tipoEscalonamento;
	}

	// Utilizado para setar processo para o início (Executando).
	private void trocarPosicoesDaLista(int posicao) {
		Collections.swap(TabelaProcessos.getListaProcessos(), 0, posicao);
	}

    public void escalonar(boolean encerrarProcesso) {
		if(TabelaProcessos.getTamanhoLista() == 0) return;
		PCB processoAntigo = TabelaProcessos.getProcExec();

		boolean unicoElemento = TabelaProcessos.getTamanhoLista() == 1;
		if(!unicoElemento) {
			switch (tipoEscalonamento) {
				case "FIFO": {
					fifo(processoAntigo);
					break;
				}
			
				case "PFixa": {
					prioridadeFixa();
					break;
				}
			
				case "Loteria": {
					loteria();
					break;
				}
					
				default: System.out.println("Tipo escolhido inválido");
			}	
		}

		if(processoAntigo != null) {
			if(encerrarProcesso && !unicoElemento) 
				TabelaProcessos.remover(processoAntigo);
			else {
				processoAntigo.setEstadoProcesso("Pronto");
				processoAntigo.RegistradoresPCB();
			}
		}

		PCB processoExecutando = TabelaProcessos.getProcTop();
		processoExecutando.setEstadoProcesso("Executando");
        processoExecutando.PCBRegistradores();
    }

	// Funções FIFO.
    private void fifo(PCB processoAntigo) {
		if(processoAntigo != null) {
			TabelaProcessos.removerProcessoTopo();
			TabelaProcessos.adicionarProcesso(processoAntigo);
		}
	}

	// Funções prioridade fixa.
	private void adicionarNaFilaDePrioridade(PCB processo) {
		switch(processo.getPrioridade()) {
			case 0: {
				if(baixaPrioridade == null) {
					baixaPrioridade = new LinkedList<PCB>();
				}

				baixaPrioridade.add(processo);
				break;
			}
			case 1: {
				if(mediaPrioridade == null) {
					mediaPrioridade = new LinkedList<PCB>();
				}

				mediaPrioridade.add(processo);
				break;
			}
			case 2: {
				if(altaPrioridade == null) {
					altaPrioridade = new LinkedList<PCB>();
				}

				altaPrioridade.add(processo);
				break;
			}
			default: return;
		}
	}
	
	private void criarFilasDePrioridade() {
		List<PCB> listaProcessos = TabelaProcessos.getListaProcessos();

		for(PCB processo : listaProcessos) {
			adicionarNaFilaDePrioridade(processo);
		}
	}

	private PCB obterProcessoPrioritario(Queue<PCB> filaPrioridade) {
		if(filaPrioridade == null) return null;

		PCB topoPrioridade = filaPrioridade.peek();
		return topoPrioridade;
	}

	private void prioridadeFixa() {
		criarFilasDePrioridade();
		PCB maiorPrioridade = obterProcessoPrioritario(altaPrioridade);

		if(maiorPrioridade == null) {
			maiorPrioridade = obterProcessoPrioritario(mediaPrioridade);

			if(maiorPrioridade == null) {
				maiorPrioridade = obterProcessoPrioritario(baixaPrioridade);
			}
		}

		int indexProcessoPrioritario = TabelaProcessos.getListaProcessos().indexOf(maiorPrioridade);
		trocarPosicoesDaLista(indexProcessoPrioritario);
	}
	
	// Funções loteria.
	private void loteria() {
		Random random = new Random();
		int valorAleatorio = random.nextInt(TabelaProcessos.getTamanhoLista()-1)+1;

		trocarPosicoesDaLista(valorAleatorio);
	}
}