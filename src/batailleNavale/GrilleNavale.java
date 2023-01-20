package batailleNavale;

import java.util.Random;

public class GrilleNavale {

	private Navire[] navires;
	private int nbNavires; //
	private int taille; //
	private Coordonnee[] tirsRecus;
	private int nbTirsRecus;
	
	public GrilleNavale(int taille, int[] taillesNavires) {
		this(taille, taillesNavires.length);		
		// mettre les n Navires dans le tableau
		placementAuto(taillesNavires);
	}
	public GrilleNavale(int taille, int nbNavires) {
		if (taille < 6) {
			throw new IllegalArgumentException("La taille de la grille ne peut pas être inférieure à 6");
		}
		if (nbNavires < 1) {
			throw new IllegalArgumentException("Il faut au moins un navire !");
		}
		this.taille = taille;
		tirsRecus = new Coordonnee[taille * taille];
		nbTirsRecus = 0;
		navires = new Navire[nbNavires];
		this.nbNavires = 0;
	}
	
	public int getNbNavires() {
		return nbNavires;
	}
	
	public Navire[] getNavires () {
		return navires;
	}
	
	public String toString() {
		StringBuffer res = new StringBuffer("   "); // 3 espaces
		// affichage des lettres de A à n
		for (int i = 0; i < taille; i++) {
			res.append((char)('A' + i) + " ");
		}
		// on remplit la grille avec des .
		for (int i = 0; i < taille; i++) { // parcours ligne
			res.append("\n");
			if (i < 9) {
				res.append(" ");
			}
			res.append((i+1) + " ");
			for (int j = 0; j < taille; j++) {
				res.append(". ");
			}
		}
		
		// on affiche les navires
		if (nbNavires > 0) {
			for (int i = 0; i < nbNavires; i++) {
				int indexDebut = navires[i].getDebut().getColonne()*2+3 + (4+2*taille)*(navires[i].getDebut().getLigne()+1);
				int indexPlus;
				if (navires[i].estVertical()) {
					indexPlus = 4+2*taille;
				} else {
					indexPlus = 2;
				}
				for (int j = 0; j < navires[i].getLongueurNavire(); j++) {
					res.setCharAt(indexDebut + j * indexPlus, '#');
				}
			}
		}
		
		// on afficher les tirs
		if (nbTirsRecus > 0) {
			for (int i = 0; i < nbTirsRecus; i++) {
				int indexTir = tirsRecus[i].getColonne()*2+3 + (4+2*taille)*(tirsRecus[i].getLigne()+1);
				if (res.charAt(indexTir) == '.') {
					res.setCharAt(indexTir, 'O');
				} else {
					res.setCharAt(indexTir, 'X');
				}
			}
		}
		return res.toString();
	}
	
	public int getTaille() {
		return taille;
	}
	
	public boolean ajouteNavire(Navire n) {
		if (n.getLongueurNavire() > taille) {
			throw new IllegalArgumentException("Navire plus grand que la grille");
		}
		if (nbNavires == navires.length) {
			throw new RuntimeException("Nombre max de navire déjà atteint");
		}
		if (!estDansGrille(n.getDebut()) || !estDansGrille(n.getFin())) {
			return false;
		}
		
		for (int i = 0; i < nbNavires; i++) {
			if (n.chevauche(navires[i])) {
				return false;
			}
			if (n.touche(navires[i])) {
				return false;
			}
		}
		
		navires[nbNavires] = n;
		nbNavires++;
		return true;
	}
	
	public void placementAuto(int[] taillesNavires) {
		if (taillesNavires.length == 0) {
			throw new IllegalArgumentException("Le tableau de taille de navires ne peut pas être vide. Il faut au moins un navire.");
		}
		for (int i = 0; i < taillesNavires.length; i++) {
			if (taillesNavires[i] < 1) {
				throw new IllegalArgumentException("La taille du navire d'indice " + i + " doit être de 1 minimum");
			}
			if (taillesNavires[i] > taille) {
				throw new IllegalArgumentException("La taille du navire d'indice " + i + " doit être plus petite que la taille ed la grille");
			}
		}
		
		for (int i = 0; i < taillesNavires.length; i++) {
			
			int ligneRandom = (int) (Math.random() * taille);
			int colonneRandom = (int) (Math.random() * taille);
			Coordonnee debut = new Coordonnee(ligneRandom, colonneRandom);
			boolean estVertical = new Random().nextBoolean();
			
			Navire navire = new Navire(debut, taillesNavires[i], estVertical);
			boolean test = ajouteNavire(navire);
			
			//System.out.println(this); // ------------------ monitoring ---------------------
			
			for (; !test; ) {
					for (int j = 0; j < taille && !test; j++) {
						estVertical = !estVertical; // on essaie de tourner le navire
						navire = new Navire(debut, taillesNavires[i], estVertical);
						test = ajouteNavire(navire);
						if (!test) {
							estVertical = !estVertical; // si toujorus pas bon, on remet le navire dans sa direction de base
							colonneRandom = (colonneRandom + 1) % (taille - (taillesNavires[i] - 1)); // on le décale de 1 sur la colonne
							
							debut = new Coordonnee(ligneRandom, colonneRandom);
							navire = new Navire(debut, taillesNavires[i], estVertical);
							test = ajouteNavire(navire);
						}
					}
					if (!test) {
						ligneRandom = (ligneRandom + 1) % (taille - (taillesNavires[i] - 1)); // on le décale de 1 sur la ligne
						
						navire = new Navire(debut, taillesNavires[i], estVertical);
						test = ajouteNavire(navire);
					}
				
			}
		}
	}
	
	public boolean placementManuel(Coordonnee debut, int longueur, boolean estVertical) {
		Navire navire = new Navire(debut, longueur, estVertical);
		return ajouteNavire(navire);
	}
	
	private boolean estDansGrille(Coordonnee c) {
		return c.getColonne() >= 0 && c.getColonne() < taille && c.getLigne() >= 0 && c.getLigne() < taille;
	}
	
	private boolean estDansTirsRecus(Coordonnee c) {
		if (!estDansGrille(c)) {
			//System.out.println("Coordonnée hors grille"); // ----------------- monitoring --------------
			return false;
		}
		for (int i = 0; i < nbTirsRecus; i++) {
			if (tirsRecus[i].equals(c)) {
				return true;
			};
		}
		return false;
	}
	
	private boolean ajouteDansTirsRecus(Coordonnee c) {
		if (!estDansGrille(c)) {
			return false;
		}
		if (!estDansTirsRecus(c)) {
			tirsRecus[nbTirsRecus] = c;
			nbTirsRecus++;
			return true;
		}
		return false;
	}
	
	/*
	 * Ajoute c aux tirs reçus de this si nécessaire.
	 * Retourne true si et seulement si c ne correspondait pas déjà à un tir reçu et a permis de toucher un navire de this.
	 */
	public boolean recoitTir(Coordonnee c) {
		if (!estDansTirsRecus(c) && !estTouche(c)) {
			ajouteDansTirsRecus(c);
			for (int i = 0; i < nbNavires; i++) {
				if (navires[i].contient(c)) {
					return navires[i].recoitTir(c);
				}
			}
		}
		return false;
	}
	
	/*
	 * Retourne true si et seulement si un des navires présents dans this a été touché en c.
	 */
	public boolean estTouche(Coordonnee c) {
		if (!estDansGrille(c)) {
			return false;
		}
		for (int i = 0; i < nbNavires; i++) {
			if (navires[i].contient(c)) {
				return navires[i].estTouche(c);
			}
		}
		return false;
	}
	
	public boolean estALEau(Coordonnee c) {
		if (!estDansGrille(c)) {
			return false;
		}
		return !estTouche(c);
	}
	
	public boolean estCoule(Coordonnee c) {
		if (!estDansGrille(c)) {
			return false;
		}
		for (int i = 0; i < nbNavires; i++) {
			if (navires[i].contient(c)) {
				return navires[i].estCoule();
			}
		}
		return false;
	}
	
	public boolean perdu() {
		for (int i = 0; i < nbNavires; i++) {
			if (!navires[i].estCoule()) {
				return false;
			}
		}
		return true;
	}
	
	public static void main(String[] args) {
		
		Navire nav1 = new Navire(new Coordonnee(3,5), 3, false);
		GrilleNavale grille = new GrilleNavale(8, 1);
		grille.ajouteNavire(nav1);
		
		Coordonnee tir1 = new Coordonnee("F5");
		grille.ajouteDansTirsRecus(tir1);
		System.out.println(grille);
		System.out.println("tir1");
		if (!grille.estDansGrille(tir1)) {
			System.out.println("pb estDansGrille()");
		}
		if (!grille.estDansTirsRecus(tir1)) {
			System.out.println("pb estDansTirsRecus()");
		}
		if (!grille.estALEau(tir1)) {
			System.out.println("pb estALEau()");
		}
		if (grille.estTouche(tir1)) {
			System.out.println("pb estTouche()");
		}
		if (grille.estCoule(tir1)) {
			System.out.println("pb estCoule()");
		}
		
		Coordonnee tir2 = new Coordonnee("F4");
		grille.ajouteDansTirsRecus(tir2);
		nav1.recoitTir(tir2);
		System.out.println(grille);
		System.out.println("tir2");
		if (!grille.estDansGrille(tir2)) {
			System.out.println("pb estDansGrille()");
		}
		if (!grille.estDansTirsRecus(tir2)) {
			System.out.println("pb estDansTirsRecus()");
		}
		if (grille.estALEau(tir2)) {
			System.out.println("pb estALEau()");
		}
		if (!grille.estTouche(tir2)) {
			System.out.println("pb estTouche()");
		}
		if (grille.estCoule(tir2)) {
			System.out.println("pb estCoule()");
		}
		
		Coordonnee tir3 = new Coordonnee("G4");
		grille.ajouteDansTirsRecus(tir3);
		nav1.recoitTir(tir3);
		System.out.println(grille);
		System.out.println("tir3");
		if (!grille.estDansGrille(tir3)) {
			System.out.println("pb estDansGrille()");
		}
		if (!grille.estDansTirsRecus(tir3)) {
			System.out.println("pb estDansTirsRecus()");
		}
		if (grille.estALEau(tir3)) {
			System.out.println("pb estALEau()");
		}
		if (!grille.estTouche(tir3)) {
			System.out.println("pb estTouche()");
		}
		if (grille.estCoule(tir3)) {
			System.out.println("pb estCoule()");
		}
		
		Coordonnee tir4 = new Coordonnee("H4");
		grille.ajouteDansTirsRecus(tir4);
		nav1.recoitTir(tir4);
		System.out.println(grille);
		System.out.println("tir4");
		if (!grille.estDansGrille(tir4)) {
			System.out.println("pb estDansGrille()");
		}
		if (!grille.estDansTirsRecus(tir4)) {
			System.out.println("pb estDansTirsRecus()");
		}
		if (grille.estALEau(tir4)) {
			System.out.println("pb estALEau()");
		}
		if (!grille.estTouche(tir4)) {
			System.out.println("pb estTouche()");
		}
		if (!grille.estCoule(tir4)) {
			System.out.println("pb estCoule()");
		}
		
		Coordonnee tir5 = new Coordonnee("G4");
		grille.ajouteDansTirsRecus(tir5);
		nav1.recoitTir(tir5);
		System.out.println(grille);
		System.out.println("tir5");
		if (!grille.estDansGrille(tir5)) {
			System.out.println("pb estDansGrille()");
		}
		if (!grille.estDansTirsRecus(tir5)) {
			System.out.println("pb estDansTirsRecus()");
		}
		if (grille.estALEau(tir5)) {
			System.out.println("pb estALEau()");
		}
		if (!grille.estTouche(tir5)) {
			System.out.println("pb estTouche()");
		}
		if (!grille.estCoule(tir5)) {
			System.out.println("pb estCoule()");
		}
		
		Coordonnee tir6 = new Coordonnee("I4");
		grille.ajouteDansTirsRecus(tir6);
		System.out.println(grille);
		System.out.println("tir6");
		if (grille.estDansGrille(tir6)) {
			System.out.println("pb estDansGrille()");
		}
		if (grille.estDansTirsRecus(tir6)) {
			System.out.println("pb estDansTirsRecus()");
		}
		if (grille.estALEau(tir6)) {
			System.out.println("pb estALEau()");
		}
		if (grille.estTouche(tir6)) {
			System.out.println("pb estTouche()");
		}
		if (grille.estCoule(tir6)) {
			System.out.println("pb estCoule()");
		}
		
		
		/*
		GrilleNavale grilleAuto = new GrilleNavale(10, new int[]{2,2,3,3,4,5});
		Coordonnee tir1 = new Coordonnee("B2");
		Coordonnee tir2 = new Coordonnee("D5");
		Coordonnee tir3 = new Coordonnee("G8");
		grilleAuto.ajouteDansTirsRecus(tir1);
		grilleAuto.ajouteDansTirsRecus(tir2);
		grilleAuto.ajouteDansTirsRecus(tir3);
		
		System.out.println(grilleAuto);
		*/
	}

}
