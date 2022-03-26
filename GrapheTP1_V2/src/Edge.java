
public class Edge {
	int sommetInitial; 
	int sommetTerminal; 
	double valeurs[]; 
	
	public Edge() {
		}

	public Edge(int sommetInitial, int sommetTerminal, double[] valeurs) {
		super();
		this.sommetInitial = sommetInitial;
		this.sommetTerminal = sommetTerminal;
		this.valeurs = valeurs;
	}

	public int getSommetInitial() {
		return sommetInitial;
	}

	public int getSommetTerminal() {
		return sommetTerminal;
	}

	public double[] getValeurs() {
		return valeurs;
	}

	
}


	