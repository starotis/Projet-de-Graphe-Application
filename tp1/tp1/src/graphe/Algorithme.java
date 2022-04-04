package graphe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;


public class Algorithme {
	
	
		public void AlgoDjikstra (Graphe graphe,Vertex vertex) {
			// Initialisation
			
		     ArrayList<Vertex> Z = (ArrayList<Vertex>) graphe.getListeSommets().clone();
		     HashMap <Vertex, Double> Dist = new  HashMap<Vertex, Double> ();
		     HashMap <Vertex, Double> Dist2 = new  HashMap<Vertex, Double> ();
		     
		     Dist2.put(vertex,0.00); 
		     Z.remove(vertex); 

		     for(Vertex v: Z)
		        { 
		    	 Dist2.put(v,Double.POSITIVE_INFINITY);
		        }
		     
		     
		     Vertex v_successeur_depart;
		     for(int i=0;i<graphe.listeAdjacence.get(vertex.getSommet()).size();i++)
		     {
		    	 v_successeur_depart = graphe.getListeSommets().get(graphe.listeAdjacence.get(vertex.getSommet()).get(i).getSommetTerminal() );	 
		    	 Dist2.put(v_successeur_depart, graphe.listeAdjacence.get(vertex.getSommet()).get(i).getValeurs()[0]);
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
		    	   
		    	   for(int i=0;i<graphe.listeAdjacence.get(element.getSommet()).size();i++)
		   	     		{
				   	    	v_successeur_element = graphe.getListeSommets().get(graphe.listeAdjacence.get(element.getSommet()).get(i).getSommetTerminal());	 
				        	double d1 = Dist2.get(element);
				        	double d2 = graphe.listeAdjacence.get(element.getSommet()).get(i).getValeurs()[0]; 
				        	if(d1+d2 < Dist2.get(v_successeur_element)) {
				        		Dist.replace(v_successeur_element,d1+d2);
			        			Dist2.replace(v_successeur_element,d1+d2);	
				        	}
		   	     		}
		    	   
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
		
		
		
		
		public void AlgoAetoile (Graphe graphe,Vertex vertex) {
		     ArrayList<Vertex> Z = (ArrayList<Vertex>) graphe.getListeSommets().clone();
		     HashMap <Vertex, Double> Dist = new  HashMap<Vertex, Double> ();
		     HashMap <Vertex, Double> Dist2 = new  HashMap<Vertex, Double> ();
		     
		     Dist2.put(vertex,0.00); 
		     Z.remove(vertex); 

		     for(Vertex v: Z) 
		        { 
		    	 Dist2.put(v,Double.POSITIVE_INFINITY);
		        }
		     
		     
		     Vertex v_successeur_depart;
		     for(int i=0;i<graphe.listeAdjacence.get(vertex.getSommet()).size();i++)
		     {
		    	 v_successeur_depart = graphe.getListeSommets().get(graphe.listeAdjacence.get(vertex.getSommet()).get(i).getSommetTerminal() );	 
		    	 Dist2.put(v_successeur_depart, graphe.listeAdjacence.get(vertex.getSommet()).get(i).getValeurs()[0]);
		     	 //System.out.println("Test" + Dist2.get(v_successeur_depart));
		     }
		     
		      
		       while (Z.isEmpty()==false) {
		    	   
		    	   for ( Vertex v: Z)
		    		   //sommet prédécesseur ?
		    	   {
		    		   Dist.put(v,Dist2.get(v)); 
		    	   }
		    	   
	    		   Double min = Collections.min(Dist.values());
		    	   Vertex element = getSingleKeyFromValue(Dist,min);
		    	   Z.remove(element); 
		    	   Vertex v_successeur_element;
		    	   
		    	   for(int i=0;i<graphe.listeAdjacence.get(element.getSommet()).size();i++)
		   	     		{
				   	    	v_successeur_element = graphe.getListeSommets().get(graphe.listeAdjacence.get(element.getSommet()).get(i).getSommetTerminal());
				   			
				   	    	double volDoiseau = CalculDistance(vertex.getLongitude(), v_successeur_element.getLongitude(), vertex.getLatitude(), v_successeur_element.getLatitude());
				        	double d1 = Dist2.get(element);
				        	double d2 = graphe.listeAdjacence.get(element.getSommet()).get(i).getValeurs()[0]; 
					    	double heuristic = volDoiseau + d2;
				        	if(heuristic < Dist2.get(v_successeur_element)) {
				        		Dist.replace(v_successeur_element,d1+d2);
			        			Dist2.replace(v_successeur_element,d1+d2);
				        	}
		   	     		}
		    	   
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

		
		
		public double CalculDistance(double longitudeA, double latitudeA, double longitudeB, double latitudeB ) {
			double dlambda = longitudeB - longitudeA ; 
			double Distance =  6371 *Math.acos(Math.sin(Math.toRadians(latitudeA))*Math.sin(Math.toRadians(latitudeB)) + Math.cos(Math.toRadians(latitudeA))*Math.cos(Math.toRadians(latitudeB))*Math.cos(Math.toRadians(dlambda)));
			return Distance; 
		}
		
		
		

}
