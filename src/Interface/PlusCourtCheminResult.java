package Interface;

import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import elements.DijkstraModel;
import elements.Sommet;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Toolkit;

public class PlusCourtCheminResult extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable tableResult;
	
	public static JFrame parent;
	public static Sommet first;
	public static HashMap<String,Double> l;
	public static HashMap<String,String> p;
	public static String nameAlgo = "";

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		try {
//			PlusCourtCheminResult dialog = new PlusCourtCheminResult(parent ,first, l, p, nameAlgo);
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Create the dialog.
	 */
	public PlusCourtCheminResult(JFrame parent, Sommet first, HashMap<String,Double> l, HashMap<String,String> p, String nameAlgo) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(PlusCourtCheminResult.class.getResource("/icon/first4.png")));
		//super(parent, true);
		this.parent= parent;
        this.first = first;
        this.l = l;
        this.p = p;
        this.nameAlgo = nameAlgo;
        
		setBounds(100, 100, 917, 600);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(0, 0, 128));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(13, 134, 880, 71);
		contentPanel.add(scrollPane);
		
		tableResult = new JTable();
		tableResult.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tableResult.setBackground(new Color(222, 245, 255));
		scrollPane.setViewportView(tableResult);
		
		JTextArea textAreaResult = new JTextArea();
		textAreaResult.setBackground(new Color(222, 245, 255));
		textAreaResult.setBounds(13, 273, 880, 257);
		contentPanel.add(textAreaResult);
		
		JLabel lblDepart = new JLabel("");
		lblDepart.setForeground(new Color(255, 255, 255));
		lblDepart.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblDepart.setBounds(169, 53, 191, 41);
		contentPanel.add(lblDepart);
		
		lblDepart.setText(first.getNameSommet());
		tableResult.setModel(new DijkstraModel(this.l, this.p));
		
		StringBuffer sb = new StringBuffer();
        
        for (Map.Entry<String, Double> entry : l.entrySet()) {
            String key = entry.getKey(); 
            Double value = entry.getValue();
            
            if(!key.equals(first.getNameSommet())){
                
                StringBuffer line = new StringBuffer();
                line.append(" : "+value+"");
                line.insert(0, " -> " + key);
                String pre = p.get(key);
                while((pre!=null) && !pre.equals(first.getNameSommet())){
                    line.insert(0, " -> " + pre);
                    pre = p.get(pre);
                }
                line.insert(0, pre);
                if(pre!=null)sb.append(line.toString() + "\n");
            }
        }
        textAreaResult.setText(sb.toString());
        
        JLabel lblTitle = new JLabel("Résultat de l'algorithme de ");
        lblTitle.setForeground(new Color(255, 215, 0));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 30));
        lblTitle.setBounds(10, 10, 842, 47);
        contentPanel.add(lblTitle);
        lblTitle.setText(lblTitle.getText() + nameAlgo);
        
        JLabel lblsmd = new JLabel("Sommet de départ : ");
        lblsmd.setForeground(new Color(255, 255, 224));
        lblsmd.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblsmd.setBounds(13, 53, 167, 41);
        contentPanel.add(lblsmd);
        
        JLabel lblres = new JLabel("Résultats : ");
        lblres.setForeground(new Color(255, 255, 224));
        lblres.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblres.setBounds(13, 99, 155, 33);
        contentPanel.add(lblres);
        
        JLabel lblInterp = new JLabel("Interprètation : ");
        lblInterp.setForeground(new Color(255, 255, 224));
        lblInterp.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblInterp.setBounds(13, 225, 167, 33);
        contentPanel.add(lblInterp);
	}
}
