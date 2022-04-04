package graphe;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

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
				listeSommets.add(new Vertex(contenuFichier.get(i+5).split(" ")[1],Integer.parseInt(contenuFichier.get(i+5).split(" ")[0]),Double.parseDouble(contenuFichier.get(i+5).split(" ")[2]) , Double.parseDouble(contenuFichier.get(i+5).split(" ")[3])));
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
			
			//Degré 
            this.degres = new int[nbSommets];
            //Mise à 0 pour degré
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
	

	
	public void AlgoDjikstra (Vertex vertex) {
		// Initialisation
	     ArrayList<Vertex> Z = (ArrayList<Vertex>) listeSommets.clone();
	     HashMap <Vertex, Double> Dist = new  HashMap<Vertex, Double> ();
	     HashMap <Vertex, Double> Dist2 = new  HashMap<Vertex, Double> ();
	     
	     Dist2.put(vertex,0.00); 
	     Z.remove(vertex); 

	     for(Vertex v: Z) 
	        { 
	    	 Dist2.put(v,Double.POSITIVE_INFINITY);
	        }
	     
	     
	     Vertex v_successeur_depart;
	     for(int i=0;i<listeAdjacence.get(vertex.getSommet()).size();i++)
	     {
	    	 v_successeur_depart = listeSommets.get( listeAdjacence.get(vertex.getSommet()).get(i).getSommetTerminal() );	 
	    	 Dist2.put(v_successeur_depart, listeAdjacence.get(vertex.getSommet()).get(i).getValeurs()[0]);
	     	 //System.out.println("Test" + Dist2.get(v_successeur_depart));
	     }
	     
	      
	       while (Z.isEmpty()==false) {
	    	   
	    	   for ( Vertex v: Z)
	    	   {
	    		   Dist.put(v,Dist2.get(v)); 
	    	   }
	    	   
    		   Double min = Collections.min(Dist.values());
	    	   Vertex element = getSingleKeyFromValue(Dist,min);
	    	   Z.remove(element); 
	    	   Vertex v_successeur_element;
	    	   
	    	   for(int i=0;i<listeAdjacence.get(element.getSommet()).size();i++)
	   	     		{
	    		   		// run que 5 fois , bug la sixième fois 
			   	    	v_successeur_element = listeSommets.get(listeAdjacence.get(element.getSommet()).get(i).getSommetTerminal());	 
			        	double d1 = Dist2.get(element);
			        	double d2 = listeAdjacence.get(element.getSommet()).get(i).getValeurs()[0]; 
			        	if(d1+d2 < Dist2.get(v_successeur_element)) {
			        		Dist.replace(v_successeur_element,d1+d2);
		        			Dist2.replace(v_successeur_element,d1+d2);	
			        	}
	   	     		}
	    	   
	    	  //  System.out.println("Element supprimé " + element );
		    	Dist.clear();

	        }
	    Set listKeys=Dist2.keySet();
   		Iterator iterateur=listKeys.iterator();   
	       while(iterateur.hasNext())
   		{
   			Object key= iterateur.next();
   			System.out.println (key+"=>"+Dist2.get(key));
   		}

	}
	
	
	public static <K, V> K getSingleKeyFromValue(Map<K, V> map, V value) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }
	
	
	
	
	
}










