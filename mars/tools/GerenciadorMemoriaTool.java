package mars.tools;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import mars.mips.SO.ProcessManager.PCB;
import mars.mips.SO.ProcessManager.TabelaProcessos;
import mars.mips.SO.Memory.*;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Observable;
import java.util.Vector;

import mars.mips.hardware.AccessNotice;
import mars.mips.hardware.Memory;
import mars.mips.hardware.MemoryAccessNotice;

 
 @SuppressWarnings("serial")
 public class GerenciadorMemoriaTool extends AbstractMarsToolAndApplication {    	
	 public static JTextField hitsCounts;
	 public static JTextField missCounts;
	 public static JTextField SubsTPagina;
	 public static JTextField TempoTotal;
	 public static JTextField TempoSubst;
	 public static JTextField TempoAcessoMem;
	 public static JTextField TempoAcessoDisco;
	 
	 JTextArea JLog;

	 public static JComboBox<Integer> tamPagina;
	 public static JComboBox<Integer> qtdMolduraMemFisica;
	 public static JComboBox<String> metodoPaginacao;
	 public static JButton botaoConfirmar;

	 private static String heading =  "Gerenciador de memória";
	 private static String version = " Version 1.0";
	 private static JTable table;
	 private static JTextArea hitMissMessage;
	 public static int lastAdress = -1;

	public GerenciadorMemoriaTool(String title, String heading) {
	  super(title,heading);
   }

	public GerenciadorMemoriaTool() {
	  super (heading+", "+version, heading);
   }
		 
	public static void main(String[] args) {
	  new GerenciadorMemoriaTool(heading+", "+version,heading).go();
   }
	

	/**
	  *  Required method to return Tool name.  
	  *  @return  Tool name.  MARS will display this in menu item.
	  */
	public String getName() {
	  return "Memory Manager Tool";
   }
	
 
  protected JComponent buildMainDisplayArea() {
	  
	 JPanel painel = new JPanel();

		BorderLayout layout = new BorderLayout();
		layout.setVgap(10);
		layout.setHgap(10);
		painel.setLayout(layout); 
		
		JScrollPane scrollPane = new JScrollPane();
	  scrollPane.setBounds(196, 11, 343, 397);
	  painel.add(scrollPane);
		  
	  table = new JTable();
	  table.setModel((TableModel) new DefaultTableModel(
		  new Object[][] {
		  },
		  new String[] {
			  "Index da Tabela", "Moldura"
		  } 			
		  ) {
		  Class[] columnTypes = new Class[] {
			  String.class, String.class
		  };
		  public Class<?> getColumnClass(int columnIndex) {
			  return columnTypes[columnIndex];
		  }
		  boolean[] columnEditables = new boolean[] {
			  false, true
		  };
		  public boolean isCellEditable(int row, int column) {
			  return columnEditables[column];
		  }
		  });
		  table.getColumnModel().getColumn(0).setResizable(false);
		  table.getColumnModel().getColumn(0).setPreferredWidth(87);
		  table.getColumnModel().getColumn(1).setResizable(false);
		  table.getColumnModel().getColumn(1).setPreferredWidth(92);
		  scrollPane.setViewportView(table);	
		  


		  hitMissMessage = new JTextArea();
		  hitMissMessage.setEditable(false);
		  hitMissMessage.setLineWrap(true);
		  hitMissMessage.setWrapStyleWord(true);
		  hitMissMessage.setFont(new Font("Ariel",Font.PLAIN,12));
		 
		 //int valor = 0;
		 //if(ProcessTable.getExeProc()!=null){
		 // valor = ProcessTable.getExeProc().getPidProc();
		 //}
		 //hitMissMessage.setText(
		//	  " "
		//	);

		 hitMissMessage.setCaretPosition(0); // Assure first line is visible and at top of scroll pane.
		 painel.add(hitMissMessage);
		 //painel.add(buildConfigPanel(), BorderLayout.NORTH);
		 painel.add(buildInfoPanel(), BorderLayout.EAST);
		 painel.add(buildSelectPainel(), BorderLayout.WEST);
		 painel.add(scrollPane);			
		 return painel;
	   }
  
  private void disableAllInputs() {
	tamPagina.setEnabled(false);
	metodoPaginacao.setEnabled(false);
   	qtdMolduraMemFisica.setEnabled(false);
  }

  private void setMemoryManagerData() {
	GerenciadorMemoria.setAlgoritmoSubstituicao(
		metodoPaginacao.getSelectedItem().toString()
	);
	GerenciadorMemoria.setTamPagVirtual(
		Integer.valueOf(tamPagina.getSelectedItem().toString())
	);
	GerenciadorMemoria.setQntMaxBlocos(
		Integer.valueOf(qtdMolduraMemFisica.getSelectedItem().toString())
	);
  }

  private void handleOnClickConfirm() {
	disableAllInputs();
	setMemoryManagerData();
  } 

  private JPanel buildSelectPainel() {
	 Vector<Integer> tamPg = new Vector<Integer>();
	  tamPg.add(4);
	  tamPg.add(8);
	  tamPg.add(16);
	  tamPg.add(32);
	  tamPg.add(64);

	  Vector<Integer> qtdBloc = new Vector<Integer>();
	  qtdBloc.add(4);
	  qtdBloc.add(8);
	  qtdBloc.add(16);
	  qtdBloc.add(32);

	  Vector<String> metodos = new Vector<String>();
	  metodos.add("NRU");
	  metodos.add("FIFO");
	  metodos.add("Segunda Chance");
	  metodos.add("LRU"); 
	  
	  metodoPaginacao = new JComboBox<String>(metodos);
	  metodoPaginacao.setSelectedIndex(1);
	 qtdMolduraMemFisica = new JComboBox<Integer>(qtdBloc);
	 tamPagina = new JComboBox<Integer>(tamPg);
	 botaoConfirmar = new JButton("Confirmar configurações");

	 botaoConfirmar.addActionListener(new ActionListener() { 
		public void actionPerformed(ActionEvent e) { 
			handleOnClickConfirm();
		} 
	  } );
	  
	  metodoPaginacao.setAlignmentX(CENTER_ALIGNMENT);
	  qtdMolduraMemFisica.setAlignmentX(CENTER_ALIGNMENT);
	  tamPagina.setAlignmentX(CENTER_ALIGNMENT);
	  botaoConfirmar.setAlignmentX(CENTER_ALIGNMENT);	
	  
	  JPanel panel = new JPanel();
	  JPanel outerPanel = new JPanel();
	  outerPanel.setLayout(new BorderLayout());

	  GridBagLayout gbl = new GridBagLayout();
	  panel.setLayout(gbl);

	  GridBagConstraints c = new GridBagConstraints();

	  c.insets = new Insets(5, 5, 2, 5);
	  c.gridx = 1;
	  c.gridy = 1;
 
	  c.gridy++;
	  panel.add(new JLabel("Tamanho da página"), c);
	  c.gridy++;
	  panel.add(tamPagina, c);
	  
	  c.gridy++;
	  panel.add(new JLabel("Qt. Molduras"), c);
	  c.gridy++;
	  panel.add(qtdMolduraMemFisica, c);
	  
	  c.gridy++;
	  panel.add(new JLabel("Método paginação"), c);
	  c.gridy++;
	  panel.add(metodoPaginacao, c);

	  c.gridy++;
	  panel.add(botaoConfirmar, c);

	  outerPanel.add(panel, BorderLayout.NORTH);
	  return outerPanel;
 }	

  private JPanel buildInfoPanel() {
	  hitsCounts = new JTextField();
	  hitsCounts.setColumns(10);
	  hitsCounts.setEditable(false);
	  hitsCounts.setAlignmentX(CENTER_ALIGNMENT);
	  hitsCounts.setText(String.valueOf(0));
	  
	  missCounts = new JTextField();
	  missCounts.setColumns(10);
	  missCounts.setEditable(false);
	  missCounts.setAlignmentX(CENTER_ALIGNMENT);
	  missCounts.setText(String.valueOf(0));
	  
	  SubsTPagina = new JTextField();
	  SubsTPagina.setColumns(10);
	  SubsTPagina.setEditable(false);
	  SubsTPagina.setAlignmentX(CENTER_ALIGNMENT);
	  SubsTPagina.setText(String.valueOf(0));

	  JPanel panel = new JPanel();
	  JPanel outerPanel = new JPanel();
	  outerPanel.setLayout(new BorderLayout());

	  GridBagLayout gbl = new GridBagLayout();
	  panel.setLayout(gbl);

	  GridBagConstraints c = new GridBagConstraints();

	  c.insets = new Insets(5, 5, 2, 5);
	  c.gridx = 1;
	  c.gridy = 1;

	  panel.add(new JLabel("Hits"), c);
	  c.gridy++;
	  panel.add(hitsCounts, c);
	  
	  c.gridy++;
	  panel.add(new JLabel("Miss"), c);
	  c.gridy++;
	  panel.add(missCounts, c);
	  
	  c.gridy++;
	  panel.add(new JLabel("Substituições de Pagina"), c);
	  c.gridy++;
	  panel.add(SubsTPagina, c);
	  outerPanel.add(panel, BorderLayout.NORTH);
	  return outerPanel;
  }
  
	public static void updateDisplay(String[] dados) {    	  
		((DefaultTableModel)table.getModel()).addRow(dados);          
	}

	public static void updateDisplay(int indexRemove) {   
		((DefaultTableModel)table.getModel()).removeRow(indexRemove);      	   
	}

	public static void updateDisplay(String dados, int index) {   
		((DefaultTableModel)table.getModel()).setValueAt(dados, index, 1);    	   
	}
		
	protected void processMIPSUpdate(Observable memory, AccessNotice notice) {
		if (notice.getAccessType() != AccessNotice.READ) return;
			MemoryAccessNotice m = (MemoryAccessNotice) notice;
			int a = m.getAddress();
			if (a == lastAdress) return;
			lastAdress = a;
			
			GerenciadorMemoria.verificarMemoria();
			table.removeAll();
			// TODO
			for(PCB processo : TabelaProcessos.getListaProcessos()) {
				List<EntradaTabelaVirtual> tabelaEntradas = processo.getTabelaVirtual().getTabelaEntradas();

				for(int ind=0 ; ind<tabelaEntradas.size() ; ind++) {
					String dados[] = new String[2];
					dados[0] = "STRING";
					dados[1] = "TESTE";
					updateDisplay(dados);
				}
			}
	}
		
	protected void addAsObserver() {
		addAsObserver(Memory.textBaseAddress, Memory.textLimitAddress);
	}
}