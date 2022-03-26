package graphe;

public class Edge {
	int sommetTerminal;
	double valeurs[];
	
	public Edge(int sommetTerminal, double[] valeurs) {
		this.sommetTerminal = sommetTerminal;
		this.valeurs = valeurs;
	}

	public int getSommetTerminal() {
		return sommetTerminal;
	}

	public double[] getValeurs() {
		return valeurs;
	}
		
	
}
