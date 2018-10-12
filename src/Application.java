import java.io.Serializable;

/**
 * Regroupe les donn�es de l'application dans le cadre du tp1A18INF111.
 * 
 * (voir �nonc�)
 * 
 * @author Pierre B�lisle
 * @revisor M�lanie Lord
 * @version A2018
 */
public class Application implements Serializable {

   /*
    * Strat�gie : Pour les ma�tres, nous utilisons un tableau dynamique qui 
    * s'agrandit � chaque ajout (sauf lors de la r�cup�ration de fichier,
    * (voir UtilitaireFichier.ouvrirFichierTexte)).
    * 
    * Pour les engagements, nous utilisons  un tableau statique 
    * (taille pr�fix�e � l'avance)
    * 
    * on utilise un tableau 2D pour les assignations o� l'intersection 
    * correspond � un "engagement-ma�tre".
    * 
    * Dans le cas des tableaux 1D, les d�cisions d'impl�mentation sont 
    * purement p�dagogiques pour fournir de l'exp�rience avec un tableau 
    * dynamique vs un tableau statique.  Des collections standards de Java
    * seraient privil�gi�s dans un vrai projet. 
    */
   
   // Valeur arbitraire bas�e sur les donn�es fournies avec l'�nonc�.
   public static final int MAX_ENGAGEMENTS = 20;

   // Tableau dynamique
   Maitre[] tabMaitres;

   // Tableau statique
   Engagement[] tabEngagements = new Engagement[MAX_ENGAGEMENTS];
   int nbEngagements;

	// Le tableau d'assignation des ma�tres aux engagements.
   // (Qui fait quoi).
   int[][] assignation;

}

