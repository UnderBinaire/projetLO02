package Controleur;

import java.awt.Font;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextPane;

import projetLO02.InvalidModeException;
import projetLO02.InvalidNbrOfPlayersException;
import projetLO02.Jeu;

public class ControleurMenu implements Runnable {

	private JTextPane textPane;
	private JLabel lblJoueur1;
	private JLabel lblJoueur2;
	private JLabel lblJoueur3;
	private JButton btnSaveJoueur;
	private JButton btnAddIA;
	private JRadioButton rdbtnModeClassique;
	private JRadioButton rdbtnModeAvance;
	private JRadioButton rdbtnModePerso;
	private JButton btnLancerPartie;
	private JFrame frameMenu;
	private JFrame framePlateau;
	private JLabel lblNomDuJoueur;
	
	private Jeu jeu;
	private Thread thread;

	public ControleurMenu(Jeu jeu, JTextPane textPane, JLabel lblJoueur1, JLabel lblJoueur2, JLabel lblJoueur3, JButton btnSaveJoueur, JButton btnAddIA, JRadioButton rdbtnModeClassique, JRadioButton rdbtnModeAvance, JRadioButton rdbtnModePerso, JButton btnLancerPartie, JFrame frameMenu, JFrame framePlateau, JLabel lblNomDuJoueur) {
		this.textPane = textPane;
		this.lblJoueur1 = lblJoueur1;
		this.lblJoueur2 = lblJoueur2;
		this.lblJoueur3 = lblJoueur3;
		this.btnSaveJoueur = btnSaveJoueur;
		this.btnAddIA = btnAddIA;
		this.rdbtnModeClassique = rdbtnModeClassique;
		this.rdbtnModeAvance = rdbtnModeAvance;
		this.rdbtnModePerso = rdbtnModePerso;
		this.btnLancerPartie = btnLancerPartie;
		this.frameMenu = frameMenu;
		this.framePlateau = framePlateau;
		this.lblNomDuJoueur = lblNomDuJoueur;
		
		this.jeu = jeu;
		
		listenButtons();
	}
	
	private void listenButtons() {		
		btnSaveJoueur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textPane.getText().isEmpty()) {
					JLabel message = new JLabel("Entrez un nom!");
					message.setFont(new Font("Tahoma", Font.PLAIN, 15));
					JOptionPane.showMessageDialog(null, message);
				}
				else { 
					String name = textPane.getText().trim(); //copie le texte pr�sent dans le Jtext
					jeu.addJoueur(name);
				
					if((jeu.getNbrJoueurs()+jeu.getNbrIA()) == 1) { 
						lblJoueur1.setText(jeu.getPlayerName(1));
					}
					else if ((jeu.getNbrJoueurs()+jeu.getNbrIA()) == 2) {
						lblJoueur1.setText(jeu.getPlayerName(1));
						lblJoueur2.setText(jeu.getPlayerName(2));
					}
					else if ((jeu.getNbrJoueurs()+jeu.getNbrIA()) == 3) {
						lblJoueur1.setText(jeu.getPlayerName(1));
						lblJoueur2.setText(jeu.getPlayerName(2));
						lblJoueur3.setText(jeu.getPlayerName(3));
					}
					if( ( (jeu.getNbrJoueurs()+jeu.getNbrIA())>0 ) && ( (jeu.getNbrJoueurs()+jeu.getNbrIA())<4 ) ) {
						JLabel message = new JLabel("Le nom a �t� enregistr� avec succ�s !");
						message.setFont(new Font("Tahoma", Font.PLAIN, 15));
						JOptionPane.showMessageDialog(null, message);
					}
					textPane.setText(""); //r�initialise la zone de texte 
				}
			}
		});
		
		btnAddIA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jeu.addIA();
				if((jeu.getNbrJoueurs()+jeu.getNbrIA()) == 1) { 
					lblJoueur1.setText(jeu.getPlayerName(1));
				}
				else if ((jeu.getNbrJoueurs()+jeu.getNbrIA()) == 2) {
					lblJoueur1.setText(jeu.getPlayerName(1));
					lblJoueur2.setText(jeu.getPlayerName(2));
				}
				else if ((jeu.getNbrJoueurs()+jeu.getNbrIA()) == 3) {
					lblJoueur1.setText(jeu.getPlayerName(1));
					lblJoueur2.setText(jeu.getPlayerName(2));
					lblJoueur3.setText(jeu.getPlayerName(3));
				}
				if( ( (jeu.getNbrJoueurs()+jeu.getNbrIA())>0 ) && ( (jeu.getNbrJoueurs()+jeu.getNbrIA())<4 ) ) {
					JLabel message = new JLabel("IA ajout�e avec succ�s !");
					message.setFont(new Font("Tahoma", Font.PLAIN, 15));
					JOptionPane.showMessageDialog(null, message);
				}
			}
		});
			
		btnLancerPartie.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				if(jeu.getMode() == null) {
					if(rdbtnModeClassique.isSelected()) {
						jeu.setMode(1);
					}
					else if(rdbtnModeAvance.isSelected()) {
						jeu.setMode(2);
					}
					else if(rdbtnModePerso.isSelected()) {
						jeu.setMode(3);
					} 
				}
				else {
					JLabel message = new JLabel("Mode d�j� param�tr� via la console!");
					message.setFont(new Font("Tahoma", Font.PLAIN, 15));
					JOptionPane.showMessageDialog(null, message);
				}
				framePlateau.setVisible(true); //permet d'ouvrir l'interface graphique du Plateau
				frameMenu.setVisible(false);
				String currentPlayer = jeu.getPlayerName(1); //l'idee est d'afficher le nom du joueur et changer � chaque fois que c'est au joueur suivant de jouer. 
				lblNomDuJoueur.setText(currentPlayer); 
				lancerPartie();
			}
		});
	}
	
	private void lancerPartie() {
		this.thread = new Thread(this);
		this.thread.start();
	}

	public void run() {	
		try {
			jeu.start();
		} catch (InvalidModeException | InvalidNbrOfPlayersException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
