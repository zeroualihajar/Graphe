package Interface;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;

import javax.swing.JButton;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileOutputStream;
import java.net.URL;

import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import algorithmes.BFS;
import algorithmes.BellmanFord;
import algorithmes.Coloriage;
import algorithmes.DFS;
import algorithmes.Kruskal;
import algorithmes.Prim;
import algorithmes.Wareshall;
import algorithmes.Dijkstra;
import algorithmes.Marquage;
import algorithmes.Residuelle;
import elements.InfosGraph;
import elements.Sommets;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;

import java.awt.Toolkit;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class Principal {

	public static JFrame frameprincipal;
	JLabel sommet;
	JLabel lblNewLabel;
	public String type;
	
	public static JTextField textSommet;
	public static JTextField textArc;
	public static JTextField textDens;
	
	public static JButton  btnMatrice;
	private static JButton btnMatrice_1;
	public static JButton btnBFS;
	public static JButton btnDFS;
	public static JButton btnWareshall;
	public static JButton btnPrim;
	public static JButton btnKruskal;
	public static JButton btnDijkstra;
	public static JButton btnBellmanford;
	public static JButton btnMarquage;
	public static JButton btnResiduelle;
	public static JButton btnColoriage;
	public static Sommets sm;
	public static JTextArea txtTrace;
	
	public static JScrollPane scrollPaneSommet;
	public static int [][] matriceAdj;
	
	public void setLbl(String text)
	{
		lblNewLabel.setText(text);
	}
	public void setType(String type)
	{
		this.type = type;
	}
	public String getType()
	{
		return this.type;
	}
	public void setSommet(String text)
	{
		textSommet.setText(text);
	}
	
	
	
	public Principal(String type)
	{
		if(type == "Non Orienté")
		{
			initialize();
			InfosGraph.simple = true;
			Principal.btnColoriage.setEnabled(true);
		}
		if(type == "Orienté")
		{
			initialize();
			InfosGraph.oriente = true;
		}
		if(type == "Non Orienté et Pondéré")
		{
			initialize();
			InfosGraph.simple = true;
			InfosGraph.pondere = true;
			Principal.btnPrim.setEnabled(true);
			Principal.btnKruskal.setEnabled(true);
			Principal.btnColoriage.setEnabled(true);
		}
		if(type == "Orienté et Pondéré")
		{
			initialize();
			Principal.btnMarquage.setEnabled(true);
			Principal.btnResiduelle.setEnabled(true);
			InfosGraph.oriente = true;
			InfosGraph.pondere = true;
		}
	}

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Principal window = new Principal();
//					window.frameprincipal.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the application.
	 */
	public Principal() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frameprincipal = new JFrame();
		frameprincipal.setIconImage(Toolkit.getDefaultToolkit().getImage(Principal.class.getResource("/icon/first4.png")));
		frameprincipal.getContentPane().setBackground(new Color(255, 255, 255));
		frameprincipal.setTitle("Graphe");
		frameprincipal.setBounds(100, 10, 1063, 704);
		frameprincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		//------------ panel principal -----------
		JPanel panelPrincipal = new JPanel();
		panelPrincipal.setBackground(new Color(255, 255, 255));
		frameprincipal.getContentPane().add(panelPrincipal, BorderLayout.CENTER);
		panelPrincipal.setLayout(null);
		
		//------------ panel d'information -------	
		JPanel infos = new JPanel();
		infos.setBorder(new LineBorder(new Color(0, 0, 128), 3));
		infos.setBackground(new Color(255, 255, 255));
		infos.setBounds(520, 22, 271, 642);
		panelPrincipal.add(infos);
		infos.setLayout(null);
		
		//-------- btn : matrices d'adjascnece --------
		btnMatrice = new JButton("Matrices du graphe");
		
		//-------- panel : trace d'algorithme ---------
		JPanel panelTrace = new JPanel();
		panelTrace.setBorder(new LineBorder(new Color(0, 0, 128), 3));
		panelTrace.setBackground(Color.WHITE);
		panelTrace.setBounds(10, 10, 251, 44);
		infos.add(panelTrace);
		
		//-------- label : trace d'algorithme ---------
		JLabel lblTracesDeLalgorithme = new JLabel("Trace de l'algorithme");
		lblTracesDeLalgorithme.setHorizontalAlignment(SwingConstants.CENTER);
		lblTracesDeLalgorithme.setForeground(new Color(220, 20, 60));
		lblTracesDeLalgorithme.setFont(new Font("Adobe Hebrew", Font.BOLD, 21));
		lblTracesDeLalgorithme.setBackground(new Color(0, 0, 128));
		panelTrace.add(lblTracesDeLalgorithme);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 59, 251, 442);
		infos.add(scrollPane);
		
		txtTrace = new JTextArea();
		scrollPane.setViewportView(txtTrace);
		txtTrace.setBackground(new Color(176, 196, 222));
		
		//-------- btn : exporter PDF -----------------
		JButton btnExporterPdf = new JButton("Exporter PDF");
		btnExporterPdf.setBounds(30, 556, 217, 33);
		infos.add(btnExporterPdf);
		btnExporterPdf.setToolTipText("");
		btnExporterPdf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try 
				{
					String file = "src\\TraceAlgorithmesPDF\\" + InfosGraph.nomAlgo + ".pdf";
					Document document = new Document();
					PdfWriter.getInstance(document, new FileOutputStream(file));
					document.open();
					Paragraph trace = new Paragraph(txtTrace.getText());
					document.add(trace);
					document.close();
					JOptionPane.showMessageDialog(frameprincipal, "Votre pdf est enregistré dans le dossier \"TraceAlgorithmesPDF\" :)", "PDF enregistré", JOptionPane.INFORMATION_MESSAGE, null);
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
			}
		});
		
		btnExporterPdf.setForeground(new Color(240, 255, 240));
		btnExporterPdf.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		btnExporterPdf.setBackground(new Color(60, 179, 113));
		
		//-------- btn : Revenir en arrière -------------
		JButton btnRevenirEnArrire = new JButton("Revenir à l'acceuil");
		btnRevenirEnArrire.setBounds(30, 599, 217, 33);
		infos.add(btnRevenirEnArrire);
		btnRevenirEnArrire.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int retour = JOptionPane.showConfirmDialog(frameprincipal, "Vous allez perdre votre graphe et toutes les informations y liées ! \n Etes-vous sur de vouloir revenir à la page d'accueil ?!", "Attention !", JOptionPane.OK_CANCEL_OPTION);
				if(retour == JOptionPane.OK_OPTION)
				{
					InfosGraph.simple = false;
					InfosGraph.oriente = false;
					InfosGraph.pondere = false;
					btnBFS.setEnabled(false);
					btnDFS.setEnabled(false);
					frameprincipal.setVisible(false);
					First first = new First();
					first.frame.setVisible(true);
				}
			}
		});
		btnRevenirEnArrire.setIcon(new ImageIcon("C:\\Users\\HP\\Desktop\\lsi\\S2\\TG\\project\\12.png"));
		btnRevenirEnArrire.setForeground(new Color(240, 255, 240));
		btnRevenirEnArrire.setFont(new Font("Times New Roman", Font.PLAIN, 19));
		btnRevenirEnArrire.setBackground(new Color(128, 0, 128));
		
		//--------- btn : A propos du graphe -------------------------
		JButton btnAPropos = new JButton("A propos du graphe");
		btnAPropos.setBounds(30, 511, 217, 35);
		infos.add(btnAPropos);
		btnAPropos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new InfoGraph(sm).setVisible(true);;
			}
		});
		btnAPropos.setForeground(new Color(0, 0, 139));
		btnAPropos.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		btnAPropos.setBackground(new Color(255, 215, 0));
		
		//-------- panel decor --------------------------	
		JPanel decor = new JPanel();
		decor.setBounds(0, 0, 1049, 12);
		decor.setBackground(new Color(0, 0, 139));
		panelPrincipal.add(decor);
		decor.setLayout(null);
		
		//-------- panel algos ---------------------------		
		JPanel algos = new JPanel();
		algos.setBackground(new Color(0, 0, 128));
		algos.setBounds(801, 22, 248, 642);
		panelPrincipal.add(algos);
		algos.setLayout(null);
		
		//-------- Title ----------------------------------
		JLabel titleAlgos = new JLabel("Algorithmes");
		titleAlgos.setForeground(new Color(255, 215, 0));
		titleAlgos.setBackground(new Color(220, 220, 220));
		titleAlgos.setFont(new Font("Adobe Hebrew", Font.BOLD, 26));
		titleAlgos.setHorizontalAlignment(SwingConstants.CENTER);
		titleAlgos.setBounds(10, 10, 214, 38);
		algos.add(titleAlgos);
		
		
		
		
		//-------- btn : BFS -------------------------------
		btnBFS = new JButton("BFS");
		btnBFS.setEnabled(false);
		btnBFS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread(new BFS(sm)).start();
			}
		});
		btnBFS.setForeground(new Color(0, 0, 139));
		btnBFS.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		btnBFS.setBackground(new Color(220, 220, 220));
		btnBFS.setBounds(28, 105, 91, 33);
		algos.add(btnBFS);
		
		//-------- btn : DFS --------------------------------
		btnDFS = new JButton("DFS");
		btnDFS.setEnabled(false);
		btnDFS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread(new DFS(sm)).start();
			}
		});
		btnDFS.setForeground(new Color(0, 0, 128));
		btnDFS.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		btnDFS.setBackground(new Color(220, 220, 220));
		btnDFS.setBounds(122, 105, 86, 33);
		algos.add(btnDFS);
		
		//--------- btn : Wareshall --------------------------
		btnWareshall = new JButton("Wareshall");
		btnWareshall.setToolTipText("");
		btnWareshall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread(new Wareshall(sm)).start();
			}
		});
		btnWareshall.setForeground(new Color(0, 0, 128));
		btnWareshall.setFont(new Font("Times New Roman", Font.PLAIN, 19));
		btnWareshall.setBackground(new Color(220, 220, 220));
		btnWareshall.setBounds(28, 148, 183, 33);
		algos.add(btnWareshall);
		
		//-------- label : parcours du graphe ----------------
		JLabel parcours = new JLabel("Parcours du graphe");
		parcours.setFont(new Font("Times New Roman", Font.PLAIN, 19));
		parcours.setForeground(new Color(255, 255, 255));
		parcours.setHorizontalAlignment(SwingConstants.CENTER);
		parcours.setBounds(44, 58, 181, 37);
		algos.add(parcours);
		
		//--------- label : arbre couvrant minimal ------------
		JLabel Acm = new JLabel("Arbre Couvrant Minimal");
		Acm.setForeground(Color.WHITE);
		Acm.setFont(new Font("Times New Roman", Font.PLAIN, 19));
		Acm.setBounds(28, 191, 198, 37);
		Acm.setHorizontalAlignment(SwingConstants.CENTER);
		algos.add(Acm);
		
		//--------- btn : Prim --------------------------------
		btnPrim = new JButton("Prim");
		btnPrim.setEnabled(false);
		btnPrim.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread(new Prim(sm)).start();
			}
		});
		btnPrim.setForeground(new Color(0, 0, 139));
		btnPrim.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		btnPrim.setBackground(new Color(220, 220, 220));
		btnPrim.setBounds(28, 238, 71, 33);
		algos.add(btnPrim);
		
		//--------- btn : Kruskal ------------------------------
		btnKruskal = new JButton("Kruskal");
		btnKruskal.setEnabled(false);
		btnKruskal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread(new Kruskal(sm)).start();
			}
		});
		btnKruskal.setForeground(new Color(0, 0, 139));
		btnKruskal.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		btnKruskal.setBackground(new Color(220, 220, 220));
		btnKruskal.setBounds(109, 238, 108, 33);
		algos.add(btnKruskal);
		
		//--------- label : Court chemin ------------------------
		JLabel lblCourtChemin = new JLabel("Court Chemin");
		lblCourtChemin.setForeground(Color.WHITE);
		lblCourtChemin.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lblCourtChemin.setBounds(60, 281, 128, 37);
		lblCourtChemin.setHorizontalAlignment(SwingConstants.CENTER);
		algos.add(lblCourtChemin);
		
		//--------- btn : Dijkstra -----------------------------
		btnDijkstra = new JButton("Dijkstra");
		btnDijkstra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread(new Dijkstra(sm)).start();
			}
		});
		btnDijkstra.setEnabled(false);
		btnDijkstra.setForeground(new Color(0, 0, 139));
		btnDijkstra.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		btnDijkstra.setBackground(new Color(220, 220, 220));
		btnDijkstra.setBounds(28, 318, 188, 33);
		algos.add(btnDijkstra);
		
		//--------- btn : BellmaFord -----------------------------
		btnBellmanford = new JButton("Bellman-Ford");
		btnBellmanford.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread(new BellmanFord(sm)).start();
			}
		});
		btnBellmanford.setEnabled(false);
		btnBellmanford.setForeground(new Color(0, 0, 139));
		btnBellmanford.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		btnBellmanford.setBackground(new Color(220, 220, 220));
		btnBellmanford.setBounds(28, 361, 188, 33);
		algos.add(btnBellmanford);
		
		//--------- label : Reseau du Transport -------------------
		JLabel lblRseauDeTransport = new JLabel("R\u00E9seau de Transport");
		lblRseauDeTransport.setForeground(Color.WHITE);
		lblRseauDeTransport.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lblRseauDeTransport.setBounds(44, 404, 180, 37);
		lblRseauDeTransport.setHorizontalAlignment(SwingConstants.CENTER);
		algos.add(lblRseauDeTransport);
		
		//--------- btn : Methode de Marquage ----------------------
		btnMarquage = new JButton("Méthode de Marquage");
		btnMarquage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Marquage fordFulkerson = new Marquage(sm);
		        fordFulkerson.fordFulkerson(sm.getMatriceCout(sm.getMatriceAdjacence()));
			}
		});
		btnMarquage.setEnabled(false);
		btnMarquage.setForeground(new Color(0, 0, 139));
		btnMarquage.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btnMarquage.setBackground(new Color(220, 220, 220));
		btnMarquage.setBounds(28, 441, 196, 33);
		algos.add(btnMarquage);
		
		//--------- btn : Methode Residuelle -------------------------
		btnResiduelle = new JButton("Méthode Résiduelle");
		btnResiduelle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Residuelle rs = new Residuelle(sm);
		        rs.fordFulkerson(sm.getMatriceCout(sm.getMatriceAdjacence()));
			}
		});
		btnResiduelle.setEnabled(false);
		btnResiduelle.setForeground(new Color(0, 0, 139));
		btnResiduelle.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btnResiduelle.setBackground(new Color(220, 220, 220));
		btnResiduelle.setBounds(28, 484, 196, 33);
		algos.add(btnResiduelle);
		
		//--------- label: Coloriage ---------------------------------
		JLabel lblColoriage = new JLabel("Coloriage");
		lblColoriage.setForeground(Color.WHITE);
		lblColoriage.setFont(new Font("Times New Roman", Font.PLAIN, 19));
		lblColoriage.setBounds(74, 527, 102, 37);
		lblColoriage.setHorizontalAlignment(SwingConstants.CENTER);
		algos.add(lblColoriage);
		
		//--------- btn : welch et Powell ----------------------------
		btnColoriage = new JButton("Welch et Powell");
		btnColoriage.setEnabled(false);
		btnColoriage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread(new Coloriage(sm)).start();;
			}
		});
		
		btnColoriage.setForeground(new Color(0, 0, 128));
		btnColoriage.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		btnColoriage.setBackground(new Color(220, 220, 220));
		btnColoriage.setBounds(28, 574, 196, 33);
		algos.add(btnColoriage);
		
		//Image img1 = new ImageIcon(this.getClass().getResource("/mark.png")).getImage();
		JButton help = new JButton(new ImageIcon(Principal.class.getResource("/icon/mark.png")));
		help.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
	              
				if (Desktop.isDesktopSupported())
		        {
		            try
		            {
		            	Platform.startup(
		            			()->{
		            				JFrame web = new JFrame("Guide d'utilisation");
		            		        WebView webView = new WebView();

		            		        URL url = this.getClass().getResource("guide/index.html");
		            		        webView.getEngine().load(url.toString());

		            		        VBox vBox = new VBox(webView);
		            		        Scene scene = new Scene(vBox, 1200, 600);

		            		        JFXPanel pan = new JFXPanel();
		            		        pan.setScene(scene);
		            		        
		            		        web.add(pan);
		            		        web.setVisible(true);
		            		        web.pack();
		            			}
		            			);          
		            } catch (Exception ex)
		            {
		                System.out.println(ex);
		            }
		        }
			}
			});
		help.setToolTipText("Cliquer ici pour voir votre guide d'utilisation.");
		help.setForeground(new Color(0, 0, 0));
		help.setBounds(210, 0, 38, 38);
		algos.add(help);
		help.setBackground(new Color(0, 0, 128));
		
		
		JScrollPane scrollPaneSommet = new JScrollPane();
		scrollPaneSommet.setBounds(10, 20, 500, 500);
		panelPrincipal.add(scrollPaneSommet);
		
		//---------- sommet sm  : dessin -----------------------------
		sm = new Sommets();
		scrollPaneSommet.setViewportView(sm);
		
		JPanel infos_1 = new JPanel();
		infos_1.setLayout(null);
		infos_1.setBorder(new LineBorder(new Color(0, 0, 128), 3));
		infos_1.setBackground(Color.WHITE);
		infos_1.setBounds(10, 530, 500, 134);
		panelPrincipal.add(infos_1);
		
		//----------- panel Informations ---------
		JPanel panelInfos = new JPanel();
		panelInfos.setBounds(356, 0, 144, 36);
		infos_1.add(panelInfos);
		panelInfos.setBorder(new LineBorder(new Color(0, 0, 128), 3));
		panelInfos.setBackground(new Color(255, 255, 255));
		
		//---------- Title : information ---------
		JLabel label = new JLabel("Informations");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setForeground(new Color(220, 20, 60));
		label.setFont(new Font("Dialog", Font.BOLD, 18));
		label.setBackground(new Color(0, 0, 128));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		panelInfos.add(label);
		
		//--------- label : nbre de sommets -------
		JLabel lblNewLabel_1 = new JLabel("Nombre de Sommets");
		lblNewLabel_1.setBounds(10, 10, 151, 20);
		infos_1.add(lblNewLabel_1);
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		
		//-------- textField(): Sommet -----------
		textSommet = new JTextField();
		textSommet.setBounds(176, 6, 58, 33);
		infos_1.add(textSommet);
		textSommet.setForeground(new Color(255, 255, 255));
		textSommet.setHorizontalAlignment(SwingConstants.CENTER);
		textSommet.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		textSommet.setText("0");
		textSommet.setBackground(new Color(70, 130, 180));
		textSommet.setHorizontalAlignment(SwingConstants.CENTER);
		textSommet.setEditable(false);
		textSommet.setColumns(10);
		
		//------ label : densité --------------------
		JLabel lblDensit = new JLabel("Densit\u00E9                     ");
		lblDensit.setBounds(10, 96, 142, 20);
		infos_1.add(lblDensit);
		lblDensit.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		
		//------ textField() : densité --------------
		textDens = new JTextField();
		textDens.setBounds(176, 92, 58, 33);
		infos_1.add(textDens);
		textDens.setText("0");
		textDens.setHorizontalAlignment(SwingConstants.CENTER);
		textDens.setForeground(Color.WHITE);
		textDens.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		textDens.setColumns(10);
		textDens.setBackground(new Color(70, 130, 180));
		textDens.setEditable(false);
		textDens.setHorizontalAlignment(SwingConstants.CENTER);
		
		//------- label : nbre d'arretes -----------
		JLabel lblNombreDarrtee = new JLabel("Nombre d'arr\u00E9tes (arcs)");
		lblNombreDarrtee.setBounds(10, 49, 167, 20);
		infos_1.add(lblNombreDarrtee);
		lblNombreDarrtee.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		
		//------- textField() : arrete ------------
		textArc = new JTextField();
		textArc.setBounds(176, 49, 58, 33);
		infos_1.add(textArc);
		textArc.setText("0");
		textArc.setHorizontalAlignment(SwingConstants.CENTER);
		textArc.setForeground(Color.WHITE);
		textArc.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		textArc.setColumns(10);
		textArc.setBackground(new Color(70, 130, 180));
		textArc.setHorizontalAlignment(SwingConstants.CENTER);
		textArc.setEditable(false);
		btnMatrice_1 = new JButton("Les matrices du graphe");
		btnMatrice_1.setBounds(285, 89, 205, 35);
		infos_1.add(btnMatrice_1);
		btnMatrice_1.setToolTipText("");
		btnMatrice_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sm.isComplet();
				new Matrice(sm.getMatriceAdjacence(), sm.getMatriceIncidence(), sm.getMatriceArc(), sm.liste(), sm.getSommets()).setVisible(true);;
			}
		});
		btnMatrice_1.setForeground(new Color(240, 255, 240));
		btnMatrice_1.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		btnMatrice_1.setBackground(new Color(0, 0, 128));
	
	}
}
