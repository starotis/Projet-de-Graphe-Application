
public class Vertex {
	String nom;
	int sommet;
	
	public Vertex()
	{
		nom = "unknown";
		sommet = 0;
	}
	
	public Vertex(String nom, int s)
	{
		this.nom = nom;
		this.sommet = s;
	}
	public String getNom() {
		return nom;
	}
	public int getSommet() {
		return sommet;
	}
	
}
