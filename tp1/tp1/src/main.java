import java.io.File;
import java.io.IOException;

import graphe.Graphe;

public class main {

	public static void main(String[] args){
		// TODO Auto-generated method stub
		 long debut = System.currentTimeMillis();
		Graphe g = new Graphe("D:\\3A Polytech Cours\\Graphe et Application\\Projet Graphe\\CommunesFrance_5000.tgoGraph");
		System.out.println(g.ToString());
		/*g.AlgoDjikstra(g.getListeSommets().get(0));
		String[] ordre = g.plusCourtChemin(g.getListeSommets().get(1)); 
		for(int i =0;i<ordre.length;i++ )
		{
			//System.out.println(ordre[i]);
		}*/ 
		 long fin = System.currentTimeMillis();
		  System.out.println("Programme exécuté en " + Long.toString(fin - debut) + " millisecondes");
		//g.CalculDistance(4.91667, 46.3833, 5.06667, 46.1833); 
	}
}
