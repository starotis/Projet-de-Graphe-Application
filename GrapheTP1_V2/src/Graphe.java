import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.StringTokenizer;

//import graphe.Edge;
//import graphe.Vertex;


public class Graphe {
	boolean oriente;
	int nbSommets;
	int nbArcs; 
	int nbValeursParArc;
	int[] degres;
	ArrayList<Vertex> listeSommets = new ArrayList<Vertex>();
	ArrayList<LinkedList<Edge>> listeAdjacence = new ArrayList<LinkedList<Edge>>();
	LinkedList<Edge> listEdge = new LinkedList<Edge>();
	ArrayList<Integer> listeEdge = new ArrayList<Integer>();
	
	public Graphe(String chemin) {
		Vertex v= new Vertex(); 
	  	ArrayList<Integer> intTemp; 

		try
	    {
	      // Le fichier d'entrée
	      FileInputStream file = new FileInputStream(chemin);   
	      Scanner scanner = new Scanner(file); 
	      
	  	  ArrayList<String> temp;
	      String s = scanner.nextLine(); 
	      s = scanner.nextLine(); 
	      temp = StringToArrayList(s);

	      
	      //Test orientation 
	      if(temp.get(1).equalsIgnoreCase("true")) {oriente=true;}
	      else oriente=false;  
	      
	      System.out.println("Orientation du graphe:"+oriente);
	      
	      // Paramètres
	      s = scanner.nextLine(); 
	      nbSommets = scanner.nextInt();
	      System.out.println(nbSommets);
	      nbArcs = scanner.nextInt();
	      nbArcs = scanner.nextInt();
	      System.out.println(nbArcs);
	      nbValeursParArc = scanner.nextInt();
	      System.out.println(nbValeursParArc);
	      s = scanner.nextLine(); 
	      s = scanner.nextLine(); 
	      
	      //Vertices 
	      for(int i=0; i<nbSommets;i++)
	      {
	    	  s = scanner.nextLine(); 
	    	  temp = StringToArrayList(s);
	    	  v.nom=temp.get(1);
	    	  listeSommets.add(v);
	    	  System.out.println(listeSommets.get(i).nom);
	      }
	      
    	  s = scanner.nextLine(); 
    	  //Edges
       	  int j=0; 
    	  Edge e= new Edge(); 
    		for(int i = 0; i<nbArcs;i++)
			{
    			
    			s = scanner.nextLine(); 
  	    	  	intTemp = IntToArrayList(s);
  	    	    int size = intTemp.size(); 
  	    	  	//System.out.println("taille ="+size);
  	    	    System.out.println(intTemp);

  	    	  	e.sommetInitial=intTemp.get(0);
  	    	  	e.sommetTerminal=intTemp.get(1);
  	    	  	 
  	    	  	//System.out.println(intTemp);
  	    	  	//System.out.println(intTemp.get(3));
  	    	  	  	    	  
  	    	  listeEdge.add(intTemp.get(2));
  	    	  listeEdge.add(intTemp.get(3));
  	    	 // System.out.println(listeEdge.get(j) +" "+ listeEdge.get(j+1));
  	    	  j=j+2; 
  	    	  	
  	    	  Object[] valeurs = intTemp.toArray();
			}

    		
    		
    		//Degres
    		this.degres = new int[nbSommets];
            //Mise à 0 pour degré
            for(int i=0;i<degres.length;i++)
            {
                this.degres[i] = 0;
            }
            
            int nbarcstart;
            int sommetTerminal;
            System.out.println(listeAdjacence.size());
            for(int i=0;i<listeAdjacence.size();i++)
                {
                    nbarcstart = listeAdjacence.get(i).size();
                    this.degres[i] += nbarcstart;
                    if(oriente == true)
                    {
                        for(int k=0; k<nbarcstart ; k++)
                        {
                            sommetTerminal = listeAdjacence.get(i).get(k).sommetTerminal;
                            this.degres[sommetTerminal] += 1;
                        }
                    }
                }
                
    			
    		
	    	
	      scanner.close();    
	    }
	    catch(IOException E)
	    {
	      E.printStackTrace();
	    }

		}
	
	
    public static ArrayList<String> StringToArrayList(String pString){
    ArrayList<String> v = new ArrayList<>();
    StringTokenizer st = new StringTokenizer(pString);
    
    while (st.hasMoreElements()) v.add(st.nextToken());
    return v; 
    }
    
    public static ArrayList<Integer> IntToArrayList(String pString){
    ArrayList<Integer> v = new ArrayList<>();
    StringTokenizer st = new StringTokenizer(pString);
    
    while (st.hasMoreElements()) v.add(Integer.parseInt(st.nextToken()));
    return v; 
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

}