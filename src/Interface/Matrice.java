package Interface;

import java.awt.BorderLayout;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import elements.InfosGraph;
import elements.MatriceAdjacenceModel;
import elements.MatriceArcModel;
import elements.MatriceIncidenceModel;
import elements.Sommet;
import elements.Sommets;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.SwingConstants;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Toolkit;

public class Matrice extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable tableMatriceAdj;
	private JScrollPane scrollPaneMatriceAdj;
	public static int [][] matriceAdj;
	public static int [][] matriceInc;
	public static String [][] matriceArc;
	private static String liste;
	public static ArrayList<Sommet> sommets = new ArrayList<Sommet>();
	private JLabel typeGraphComplet;
	private JTable tableMatriceInc;
	private JScrollPane scrollPaneMatriceInc;
	private JTable tableMatriceArc;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		try {
//			Matrice dialog = new Matrice(matriceAdj, matriceInc, matriceArc, liste, sommets);
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Create the dialog.
	 */
	public Matrice(int [][] matriceAdj, int[][] matriceInc, String[][] matriceArc, String liste, ArrayList<Sommet> sommets) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Matrice.class.getResource("/icon/first4.png")));
		Matrice.matriceAdj = matriceAdj;
		Matrice.matriceInc = matriceInc;
		Matrice.matriceArc = matriceArc;
		this.liste = liste;
		Matrice.sommets = sommets;

		this.setLocationRelativeTo(null);
		setTitle("Matrice Correspondant à votre graphe");
		setBounds(100, 100, 824, 529);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(0, 0, 128));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblAdj = new JLabel("Matrice d'adjacence");
		lblAdj.setForeground(new Color(240, 255, 255));
		lblAdj.setHorizontalAlignment(SwingConstants.CENTER);
		lblAdj.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblAdj.setBounds(10, 10, 390, 35);
		contentPanel.add(lblAdj);
		
		scrollPaneMatriceAdj = new JScrollPane();
		scrollPaneMatriceAdj.setBounds(10, 50, 390, 162);
		contentPanel.add(scrollPaneMatriceAdj);
		
		tableMatriceAdj = new JTable(new MatriceAdjacenceModel(matriceAdj, sommets));
		//this.tableMatrice.setModel(new MatriceAdjacenceModel(MatriceAdjacence.matriceAdj, MatriceAdjacence.sommets));
		scrollPaneMatriceAdj.setViewportView(tableMatriceAdj);
		tableMatriceAdj.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tableMatriceAdj.setBackground(new Color(222, 245, 255));
		
		typeGraphComplet = new JLabel("Complet");
		typeGraphComplet.setForeground(Color.YELLOW);
		typeGraphComplet.setFont(new Font("Tahoma", Font.BOLD, 15));
		typeGraphComplet.setBounds(695, 441, 105, 35);
		contentPanel.add(typeGraphComplet);
		
		scrollPaneMatriceInc = new JScrollPane();
		scrollPaneMatriceInc.setBounds(410, 50, 390, 162);
		contentPanel.add(scrollPaneMatriceInc);
		
		tableMatriceInc = new JTable(new MatriceIncidenceModel(matriceInc, sommets));
		tableMatriceInc.setFont(new Font("Tahoma", Font.PLAIN, 15));
		scrollPaneMatriceInc.setViewportView(tableMatriceInc);
		tableMatriceInc.setBackground(new Color(222, 245, 255));
		
		JLabel lblinc = new JLabel("Matrice d'incidence");
		lblinc.setForeground(new Color(240, 255, 255));
		lblinc.setHorizontalAlignment(SwingConstants.CENTER);
		lblinc.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblinc.setBounds(410, 10, 390, 35);
		contentPanel.add(lblinc);
		
		JLabel lblArc = new JLabel("Matrice aux arcs");
		lblArc.setForeground(new Color(240, 255, 255));
		lblArc.setHorizontalAlignment(SwingConstants.CENTER);
		lblArc.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblArc.setBounds(10, 222, 390, 35);
		contentPanel.add(lblArc);
		
		JScrollPane scrollPaneMatriceArc = new JScrollPane();
		scrollPaneMatriceArc.setBounds(10, 260, 390, 171);
		contentPanel.add(scrollPaneMatriceArc);
		
		tableMatriceArc = new JTable(new MatriceArcModel(matriceArc, sommets));
		tableMatriceArc.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tableMatriceArc.setBackground(new Color(222, 245, 255));
		scrollPaneMatriceArc.setViewportView(tableMatriceArc);
		
		JLabel lblTitleListe = new JLabel("Liste du graphe");
		lblTitleListe.setForeground(new Color(240, 255, 255));
		lblTitleListe.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitleListe.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblTitleListe.setBounds(410, 222, 390, 35);
		contentPanel.add(lblTitleListe);
		
		JScrollPane scrollPaneListe = new JScrollPane();
		scrollPaneListe.setBounds(410, 260, 390, 171);
		contentPanel.add(scrollPaneListe);
		
		JTextArea textAreaListe = new JTextArea(liste);
		scrollPaneListe.setViewportView(textAreaListe);
		textAreaListe.setBackground(new Color(222, 245, 255));
		textAreaListe.setFont(tableMatriceInc.getFont());
		typeGraphComplet.setVisible(false);
		if(InfosGraph.complet)
			typeGraphComplet.setVisible(true);
	}
}
