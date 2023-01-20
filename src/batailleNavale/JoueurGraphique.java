package batailleNavale;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JOptionPane;

public class JoueurGraphique extends JoueurAvecGrille {
	private GrilleGraphique grilleTirs;
	
	public JoueurGraphique(GrilleNavaleGraphique grilleDefense,
			GrilleGraphique grilleTirs, String nom) {
		super(grilleDefense, nom);
		this.grilleTirs = grilleTirs;
	}
	
	public JoueurGraphique(GrilleNavaleGraphique grilleDefense,
			GrilleGraphique grilleTirs) {
		this(grilleDefense, grilleTirs, "Joueur");
	}
	
	public Coordonnee choixAttaque() {

		
		return grilleTirs.getCoordonneeSelectionnee();
		
	}
	
	protected void retourDefense(Coordonnee c, int etat) {
		if (etat == GAMEOVER) {
			JOptionPane.showMessageDialog(grilleTirs, "Vous avez perdu !");
			//int response = JOptionPane.showConfirmDialog(grilleTirs, "Vous avez perdu ! Voulez-vous rejouez ?");
			/*
			if (response == 0) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							BatailleNavale window = new BatailleNavale();
							window.frmBataillenavale.setVisible(true);

						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
			*/
		}
	}
	
	protected void retourAttaque(Coordonnee c, int etat) {
		Color couleur = etat == A_L_EAU ? Color.BLUE : Color.RED;
		grilleTirs.colorie(c, couleur);
		switch (etat) {
			case TOUCHE:
			JOptionPane.showMessageDialog(grilleTirs, "Vous avez touché un navire en " + c);
			break;
			case COULE:
			JOptionPane.showMessageDialog(grilleTirs, "Vous avez coulé un navire en " + c);
			break;
			case GAMEOVER:
			JOptionPane.showMessageDialog(grilleTirs, "Vous avez gagné!!!");
		}
	}
}