package batailleNavale;

public class Navire {

	private Coordonnee debut;
	private Coordonnee fin;
	private Coordonnee[] partiesTouchees;
	private int nbTouchees;
	
	public Navire(Coordonnee debut, int longueur, boolean estVertical) {
		/*permet d'obtenir un navire débutant en debut et de taille longueur. Ce navire est
		disposé verticalement si estVertical vaut true, horizontalement sinon.*/
		
		/*
		 * on suppose que debut est toujours inférieure à fin. C'est-à-dire que soit on a un navire avec des coordonnées qui s'étend vers le bas,
		 * soit un navire avec des coordonées qui s'étend vers la droite. En fonction du sens du navire
		 * */
		if (longueur > 26 || longueur <= 0) {
			throw new IllegalArgumentException("La longueur du navire doit être comprises entre 0 et 26 avec 26 exclus");
		}
		
		
		this.debut = debut;
		nbTouchees = 0;
		partiesTouchees = new Coordonnee[longueur];
		
		if (estVertical) {
			this.fin = new Coordonnee((this.debut.getLigne()+longueur) -1,this.debut.getColonne());
		} else {
			this.fin = new Coordonnee(this.debut.getLigne(),(this.debut.getColonne()+longueur)-1);
		}
		
		
	}
	public int getLongueurNavire(){
		if (estVertical())
			return (fin.getLigne() - debut.getLigne()) +1;
		else
			return (fin.getColonne() - debut.getColonne()) +1;
		
		
		// Le mettre en une ligne avec la formule du if statment ? true : false
	}
	public boolean estVertical(){
		return debut.getColonne() == fin.getColonne();
	}
	
	public String toString() {
		/*Retourne une String représentant this. On souhaite obtenir une représentation de la
forme "Navire(B1, 4, horizontal)" (pour un navire de taille 4 placé
horizontalement par exemple).*/
		
		String sensNavire = estVertical() ? "vertical" : "horizontal";
		
		return "Navire(" +  debut.toString() + ", "+ getLongueurNavire() + ", " + sensNavire + ")";
	}
	
	public Coordonnee getDebut() {
		return debut;
	}
	
	public Coordonnee getFin() {
		return fin;
	}
	
	public boolean contient(Coordonnee c) {
		/*Retourne true si et seulement si this occupe c.*/
		if (estVertical())
			return c.getColonne() == debut.getColonne() && c.getLigne() >= debut.getLigne() && c.getLigne() <= fin.getLigne();
		else {
			return c.getLigne() == debut.getLigne() && c.getColonne() >= debut.getColonne() && c.getColonne() <= fin.getColonne();
		}
	}

	public boolean touche(Navire n) {
		/*Retourne true si et seulement si this est adjacent à n. L'adjacence par la diagonale ne compte pas.*/
		
		Coordonnee d1 = n.debut;
		Coordonnee f1 = n.fin;
		Coordonnee d2 = this.debut;
		Coordonnee f2 = this.fin;
		
		if ((f2.getLigne() >= d1.getLigne() && f1.getLigne() >= d2.getLigne() 
				&& (f2.getColonne() + 1 == d1.getColonne() || f1.getColonne() + 1 == d2.getColonne()))
				||
				(f2.getColonne() >= d1.getColonne() && f1.getColonne() >= d2.getColonne() 
				&& (f2.getLigne() + 1 == d1.getLigne() || f1.getLigne() + 1 == d2.getLigne()))) {
			return true;
		} else {
			return false;
		}
	}
		
	public boolean chevauche(Navire n) {
		/*Retourne true si et seulement si this chevauche n, c'est-à-dire que this et n occupent
		au moins une coordonnée en commun.*/	
		
		Coordonnee d1 = n.debut;
		Coordonnee f1 = n.fin;
		Coordonnee d2 = this.debut;
		Coordonnee f2 = this.fin;
		
		if (f2.getLigne() >= d1.getLigne() && f1.getLigne() >= d2.getLigne() 
				&& f2.getColonne() >= d1.getColonne() && f1.getColonne() >= d2.getColonne()) {
			return true;
		} else {
			return false;
		}
	}	
	
	public boolean recoitTir(Coordonnee c) {
		if (contient(c)) {
			// test si c est déjà dans partiesTouchees
			boolean coordPresente = false;
			for (int i = 0; i < nbTouchees; i++) {
				if (partiesTouchees[i].equals(c)) {
					coordPresente = true;
				}
			}
			if (!coordPresente) {
				partiesTouchees[nbTouchees] = c;
				nbTouchees++;
			}
			return true;
		}
		return false;
			
	}
	
	public boolean estTouche(Coordonnee c) {
		if (nbTouchees == 0)
			return false;
		for (int i = 0; i < nbTouchees; i++) {
			if (partiesTouchees[i].equals(c)) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean estTouche() {
		return nbTouchees != 0;
	}
	
	public boolean estCoule() {
		return nbTouchees == getLongueurNavire();
	}
}
