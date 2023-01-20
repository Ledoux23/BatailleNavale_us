package batailleNavale;

public class Coordonnee implements Comparable<Coordonnee> {
	
	private static final char[] TAB_COL = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 
			'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

	private int ligne;
	private int colonne;

	public Coordonnee(int ligne, int colonne) {
//		if (ligne < 0 || colonne < 0) {
//			throw new IllegalArgumentException("Les indices sont invalides : ils doivent être compris entre 0 et 25");
//		}
		this.ligne = ligne;
		this.colonne = colonne;
	}

	public Coordonnee(String s) {
		// Exceptions :
		if (s == null) {
			throw new IllegalArgumentException ("Entrez des coordonnées valides");
		}

		s = s.toUpperCase();

		if (s.length() < 2 || s.length() > 3) {
			throw new IllegalArgumentException("Longueur des coordonnées invalides");
		}

// être sûr qu'on a une lettre pour les colonnes (et pas des caractères spéciaux par exemple) et des nombres entre 1 et 26 pour les lignes

		if (!(s.charAt(0) >= 'A' && s.charAt(0) <= 'Z')) {
			throw new IllegalArgumentException(
					"L'indice des colonnes est invalide : seules les lettres capitales sont des indices valides");
		}

		if (Integer.parseInt(s.substring(1)) > 26 || Integer.parseInt(s.substring(1)) < 1) {
			throw new IllegalArgumentException("L'indice des lignes doivent être entre 1 et 26");
		}
		
		this.ligne = Integer.parseInt(s.substring(1)) - 1;
		this.colonne = s.charAt(0) - 65;
	}

	public String toString() {
		String coor = TAB_COL[this.colonne] + "" + (this.ligne + 1);
		return coor;
	}

	public int getLigne() {
		return this.ligne;
	}

	public int getColonne() {
		return this.colonne;
	}

	public boolean equals(Object obj) {
		if (obj != null) {
			Coordonnee c = (Coordonnee) obj;
			return (this.getLigne() == c.getLigne() && this.getColonne() == c.getColonne());
		}

		return false;
	}

	public boolean voisine(Coordonnee c) {

		return (this.getColonne() == c.getColonne() + 1 | this.getColonne() == c.getColonne() - 1
				| this.getLigne() == c.getLigne() + 1 | this.getLigne() == c.getLigne() - 1
				&& (this.getColonne() == c.getColonne() | this.getLigne() == c.getLigne()));
	}

	public int compareTo(Coordonnee c) {
		/*
		if (this.getLigne() - c.getLigne() > 0)
			return 1;
		else if (this.getColonne() - c.getColonne() > 0 && this.getLigne() == c.getLigne())
			return 1;
		else if (this.getColonne() - c.getColonne() < 0)
			return -1;
		else
			return 0;
		*/
		if (this.getLigne() != c.getLigne()) {
			return this.getLigne() - c.getLigne();
		} else {
			return this.getColonne() - c.getColonne();
		}
	}

	public static void main(String[] args) {

		Coordonnee c = new Coordonnee("C6");

		if (c.getLigne() != 5) {
			System.out.println("PB avec ligne");
		}

		if (c.getColonne() != 2) {
			System.out.println("PB avec colonne");
		}
		if (!c.toString().equals("C6")) {
			System.out.println(c.toString());
			System.out.println("PB avec toString");
		}

		if (!c.equals(new Coordonnee("C6"))) {
			System.out.println("PB 1 avec equals");
		}
		if (c.equals(new Coordonnee("C7"))) {
			System.out.println("PB 2 avec equals");
		}
		if (!c.voisine(new Coordonnee("C7"))) {
			System.out.println("PB 1 avec voisines");
		}
		if (!c.voisine(new Coordonnee("C5"))) {
			System.out.println("PB 2 avec voisines");
		}
		if (!c.voisine(new Coordonnee("B6"))) {
			System.out.println("PB 3 avec voisines");
		}
		if (!c.voisine(new Coordonnee("D6"))) {
			System.out.println("PB 4 avec voisines");
		}
		if (c.voisine(new Coordonnee("D7"))) {
			System.out.println("PB 5 avec voisines");
		}

		Coordonnee c2 = new Coordonnee("D6");

		Coordonnee c3 = new Coordonnee("A3");

		Coordonnee c4 = new Coordonnee("D7");

		if (c.compareTo(c2) >= 0) {

			System.out.println("PB 1 avec compareTo");
		}
		if (c.compareTo(c3) <= 0) {
			System.out.println("PB 2 avec compareTo");
		}
		if (c.compareTo(c4) >= 0) {
			System.out.println("PB 3 avec compareTo");
		}
		if (c2.compareTo(c) <= 0) {
			System.out.println(c2.compareTo(c));
			System.out.println("PB 4 avec compareTo");
		}
		if (c3.compareTo(c) >= 0) {

			System.out.println(c3.compareTo(c));

			System.out.println("PB 5 avec compareTo");
		}
		if (c4.compareTo(c) <= 0) {
			System.out.println("PB 6 avec compareTo");
		}
		if (c.compareTo(c) != 0) {
			System.out.println("PB 7 avec compareTo");
		}
	}

}