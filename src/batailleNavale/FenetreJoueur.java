package batailleNavale;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

public class FenetreJoueur extends JFrame {
	private JPanel contentPane;
	private GrilleGraphique grilleTirs;
	private GrilleNavaleGraphique grilleDefense;
	
	public FenetreJoueur() {
		this("Nom du joueur", 10);
	}
	
	public FenetreJoueur(String nom, int taille) {
		contentPane = new JPanel();
		grilleTirs = new GrilleGraphique(taille);
		grilleDefense = new GrilleNavaleGraphique(taille);
		
		//JFrame frameJoueur = new JFrame();
		this.setTitle(nom);
		this.setBounds(100, 100, 488, 325);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setContentPane(contentPane);
		this.getContentPane().setLayout(new GridLayout(0, 2, 0, 0));
		
		this.getContentPane().add(grilleTirs);
		this.getContentPane().add(grilleDefense.getGrilleGraphique());
		
		/*
		JPanel panelTirs = new JPanel();
		panelTirs.setBorder(new TitledBorder(null, "Grille de tirs", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		frameJoueur.getContentPane().add(panelTirs);
		
		JPanel panelJeu = new JPanel();
		panelJeu.setBorder(new TitledBorder(null, "Grille de jeu", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelJeu.setToolTipText("");
		frameJoueur.getContentPane().add(panelJeu);
		*/
		initialize();
	}
	
	private void initialize() {
		
	}
	
	public GrilleGraphique getGrilleTirs() {
		return grilleTirs;
	}
	
	public GrilleNavaleGraphique getGrilleDefense() {
		return grilleDefense;
	}
	
	public static void main(String[] args) {
		FenetreJoueur joueur = new FenetreJoueur("toto", 10);
		joueur.setVisible(true);
		
		/*
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FenetreJoueur joueur = new FenetreJoueur("toto", 10);
					joueur.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		*/
	}
}
