package graphe;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class Graphe {
	boolean oriente;
	int nbSommets;
	int nbArcs;
	int nbValeursParArc;
	ArrayList<Vertex> listeSommets;
	ArrayList<LinkedList<Edge>> listeAdjacence;
	int[] degres;
	File file;
	public Graphe(String str)
	{
		listeSommets = new ArrayList<Vertex>();	
		listeAdjacence = new ArrayList<LinkedList<Edge>>();
		file=new File(str);
		creer_graphe();
	}
	public BufferedReader import_graphe()
	{

        String line;
          
        BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));
			return br;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}  
        
	}
	
	public void creer_graphe()
	{
		
		try {
			BufferedReader bf = import_graphe();
			ArrayList<String> contenuFichier = new ArrayList<>();
			int r=0;  
			//Remplir le fichier
			String premiereLigne="";
			for (String line = bf.readLine(); line != null; line = bf.readLine())
			{  
				contenuFichier.add(line); 
				
			}  
			bf.close();
			
			//Deuxieme ligne
			String[] oriented = contenuFichier.get(1).split(":");
			oriented[1] = oriented[1].replace(" ", "");
			if(oriented[1].equals("true"))
			{
				oriente = true;
			}
			else
			{
				oriente = false;
			}
			
			//Quatrieme ligne
			String[] parameters = contenuFichier.get(3).split(" ");
			nbSommets = Integer.parseInt(parameters[0]);
			nbArcs = Integer.parseInt(parameters[2]);
			nbValeursParArc = Integer.parseInt(parameters[3]);
			
			
			//Vertices
			for(int i = 0; i<nbSommets;i++)
			{
				listeSommets.add(new Vertex(contenuFichier.get(i+5).split(" ")[1],Integer.parseInt(contenuFichier.get(i+5).split(" ")[0])));
				LinkedList l = new LinkedList<Edge>();
				listeAdjacence.add(l);
			}
			
			int incr = nbSommets+6;
			//Edges 
			for(int i = 0; i<nbArcs;i++)
			{
				String[] edges = contenuFichier.get(i+incr).split(" ");
				double[] valeurs = new double[edges.length-2];
				for(int j = 2;j<edges.length;j++)
				{
					valeurs[j-2] = Double.parseDouble(edges[j]);
				}
				Edge edge = new Edge(Integer.parseInt(edges[1]),valeurs);
				if(oriente==false)
				{
					Edge edge1 = new Edge(Integer.parseInt(edges[0]),valeurs);
					listeAdjacence.get(Integer.parseInt(edges[1])).add(edge1);
				}
				listeAdjacence.get(Integer.parseInt(edges[0])).add(edge);
			}
			
			//Degr� 
            this.degres = new int[nbSommets];
            //Mise � 0 pour degr�
            for(int i=0;i<degres.length;i++)
            {
                this.degres[i] = 0;
            }
            int nbarcstart;
            int sommetTerminal;
            for(int i=0;i<listeAdjacence.size();i++)
            {
                nbarcstart = listeAdjacence.get(i).size();
                this.degres[i] += nbarcstart;
                if(oriente == true)
                {
                    for(int j=0;j<nbarcstart;j++)
                    {
                        sommetTerminal = listeAdjacence.get(i).get(j).sommetTerminal;
                        this.degres[sommetTerminal] += 1;
                    }
                }
            }
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String ToString()
	{
		String afficher = "";
		afficher = "Oriente = ";
		if(oriente==true)
		{
			afficher+="true";
		}
		else
		{
			afficher+="false";
		}
		afficher += "\n";
		afficher += "nbSommets = ";
		afficher += Integer.toString(nbSommets);
		
		afficher += "\n";
		afficher += "nbArcs = ";
		afficher += Integer.toString(nbArcs);
		
		afficher += "\n";
		afficher += "nbValeursParArcs = ";
		afficher += Integer.toString(nbValeursParArc);
		
		afficher += "\n";
		afficher += "listeAdjacence = ";
		
		int i = 0;
		afficher+="\n";
		for(LinkedList<Edge> l : listeAdjacence)
		{
			afficher += i+" : ";
			double[] valeurs;
			for(Edge e:l)
			{
				afficher +=e.getSommetTerminal();
				afficher +="(";
				valeurs = e.getValeurs();
				for(int j = 0;j<valeurs.length-1;j++)
				{
					afficher+=Double.toString(valeurs[j])+",";
				}
				if(valeurs.length!=0)
				{
					afficher+=Double.toString(valeurs[valeurs.length-1]);
				}
				
				afficher+= ") ";
			}
			afficher+="\n";
			i++;
		}
		
		return afficher;
		
	}
	
    public String[] parcours_largeur(Vertex vertex)
    {
        boolean[] marquer = new boolean[this.listeSommets.size()];
        String[] sommetTraite = new String[listeSommets.size()];
        int[] traiter = new int[this.listeSommets.size()];
        ArrayList<Vertex> F = new ArrayList<Vertex>();
        int p = 1;
        
        for(int i=0;i<listeSommets.size();i++)
        {
            marquer[i] = false;
            traiter[i] = 0;
        }
        marquer[vertex.getSommet()] = true;
        int ordre = 0;
        F.add(vertex);
        while(F.isEmpty()==false)
        {
        	
        	Vertex element = F.get(0);
        	
        	for(Edge successeur:listeAdjacence.get(element.getSommet()))
        	{
        		if(marquer[successeur.getSommetTerminal()]==false)
        		{
        			marquer[successeur.getSommetTerminal()] = true;
        			
        			F.add(listeSommets.get(successeur.getSommetTerminal()));
        		}
        		
        	}
        	traiter[F.get(0).getSommet()] = p;
        	sommetTraite[ordre] = F.get(0).getNom();
        	ordre++;
        	F.remove(0);
        	p+=1;
        	
        }
        return sommetTraite;
        
    }
    
    public String[] plusCourtChemin(Vertex vertex)
    {
        boolean[] marquer = new boolean[this.listeSommets.size()];
        String[] sommetTraite = new String[listeSommets.size()];
        int[] longueur = new int[this.listeSommets.size()];
        ArrayList<Vertex> F = new ArrayList<Vertex>();
        
        for(int i=0;i<listeSommets.size();i++)
        {
            marquer[i] = false;
            longueur[i] = -1; //-1 = +infini
        }
        marquer[vertex.getSommet()] = true;
        int ordre = 0;
        F.add(vertex);
        longueur[vertex.getSommet()] = 0;
        while(F.isEmpty()==false)
        {
        	
        	Vertex element = F.get(0);
        	for(Edge successeur:listeAdjacence.get(element.getSommet()))
        	{
        		if(marquer[successeur.getSommetTerminal()]==false)
        		{
        			marquer[successeur.getSommetTerminal()] = true;
        			longueur[successeur.getSommetTerminal()] = longueur[element.getSommet()]+1;
        			F.add(listeSommets.get(successeur.getSommetTerminal()));
        		}
        		
        	}
        	sommetTraite[ordre] = F.get(0).getNom() + " Longueur : "+longueur[element.getSommet()];
        	ordre++;
        	F.remove(0);
        	
        }
        return sommetTraite;
        
    }
	public ArrayList<Vertex> getListeSommets() {
		return listeSommets;
	}
	
	public double CalculDistance(double longitudeA, double latitudeA, double longitudeB, double latitudeB ) {
		double dlambda = longitudeB - longitudeA ; 
		double Distance =  6371 *Math.acos(Math.sin(Math.toRadians(latitudeA))*Math.sin(Math.toRadians(latitudeB)) + Math.cos(Math.toRadians(latitudeA))*Math.cos(Math.toRadians(latitudeB))*Math.cos(Math.toRadians(dlambda)));
		System.out.printf("La distance entre ces deux points vaut : %f\n", Distance );
		return Distance; 
	}
	
	public void AlgoDjikstra (Vertex vertex) {
		// Initialisation 
		 String[] sommetTraite = new String[listeSommets.size()];
	     int[] dist = new int[this.listeSommets.size()];
	     int ordre=0; 
	     ArrayList<Vertex> Z = new ArrayList<Vertex>();
	     
	     //On supprime le sommet de d�part de la liste 
	    // A faire apr�s Z.remove(vertex);
	     
	     //On associe � tous les autres sommets la valeur infinie
	     // #TODO Verifier s'il est bien dans le graphe  
	     for(int i=0;i<listeSommets.size();i++)
	        {
	    	 dist[i] = Integer.MAX_VALUE; 
	        }

	      // Distance entre le sommet de d�part et lui-m�me
	        dist[vertex.getSommet()] = 0;
	        while (Z.isEmpty()==false) {
	        	//Prendre x de mani�re � ce que le chemins soit le plus court entre x et le prochain sommet
	        	//Comment faire  ? Prendre X et le prochain voir la valeur dans edge faire un comparauteur de valeur et prendre la plus faible 
	        	// supprimer la valeur du vertex pr�c�dent de la liste 
	        	// Ins�rer la distance dans le tableau distance 
	        }

	}
	
	
	
	
	
}










