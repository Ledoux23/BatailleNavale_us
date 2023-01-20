package batailleNavale;


public class Jeu {

	public Jeu() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		int taille = 10;
		int[] navires = new int[]{2,2,3,3,4,5};
		
		/*
		GrilleNavale grilleJ1 = new GrilleNavale(taille, navires);
		GrilleNavale grilleJ2 = new GrilleNavale(taille, navires);
		JoueurTexte j1 = new JoueurTexte(grilleJ1, "Joueur 1");
		JoueurTexte j2 = new JoueurTexte(grilleJ2, "Joueur 2");
		
		j1.jouerAvec(j2);
		*/
		
		
		
		FenetreJoueur fJoueur = new FenetreJoueur("Joueur 1", taille);
		
		GrilleNavaleGraphique grilleDef = fJoueur.getGrilleDefense();
		GrilleGraphique grilleTir = fJoueur.getGrilleTirs();
		
		JoueurGraphique j1 = new JoueurGraphique(grilleDef, grilleTir, "Joueur 1");
		

		GrilleNavale grilleOrdi = new GrilleNavale(taille, navires);
		
		
		JoueurAuto j2 = new JoueurAuto(grilleOrdi, "Ordi");
		
		
		fJoueur.setVisible(true);
		j1.jouerAvec(j2);
	}

}
