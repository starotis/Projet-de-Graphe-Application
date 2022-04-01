package graphe;

public class Vertex {
	String nom;
	int num_sommet;
	double longitude;
	double latitude; 
	 
	public Vertex(String nom, int s)
	{
		this.nom = nom;
		this.num_sommet = s;
	}
	
	public String getNom() {
		return nom;
	}
	public int getSommet() {
		return num_sommet;
	}
	
	public Vertex(String nom, int s, double longitude, double latitude)
	{
		this.nom = nom;
		this.num_sommet = s;
		this.longitude = longitude;
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	
}
