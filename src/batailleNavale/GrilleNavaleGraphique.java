package batailleNavale;

import java.awt.Color;

public class GrilleNavaleGraphique extends GrilleNavale {
	
	private GrilleGraphique grille;
	
	public GrilleNavaleGraphique(int taille) {
		super(taille, 300);
		
		if (taille > 26 || taille < 2) {
			throw new IllegalArgumentException("La taille de la grille doit être comprise entre 2 et 26");
		}
		grille = new GrilleGraphique(taille);
		
		grille.colorie(new Coordonnee(0,0), new Coordonnee(taille - 1,taille - 1), Color.WHITE);
		
		//super.placementAuto(new int[]{2,2,3,3,4,5});
		
	}
	
	public GrilleGraphique getGrilleGraphique() {
		return grille;
	}
	
	public boolean ajouteNavire(Navire n) {
		if (super.ajouteNavire(n)) {
			grille.colorie(n.getDebut(), n.getFin(), Color.GREEN);
			return true;
		} else {
			return false;
		}
	}
	
	public boolean recoitTir(Coordonnee c) {
		if (super.recoitTir(c)) {
			grille.colorie(c, Color.RED);
			return true;
		} else {
			grille.colorie(c, Color.BLUE);
			return false;
		}
	}

}
