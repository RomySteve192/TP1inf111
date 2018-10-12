import java.io.Serializable;

/**
 * Regroupe les données de l'application dans le cadre du tp1A18INF111.
 * 
 * (voir énoncé)
 * 
 * @author Pierre Bélisle
 * @revisor Mélanie Lord
 * @version A2018
 */
public class Application implements Serializable {

   /*
    * Stratégie : Pour les maîtres, nous utilisons un tableau dynamique qui 
    * s'agrandit à chaque ajout (sauf lors de la récupération de fichier,
    * (voir UtilitaireFichier.ouvrirFichierTexte)).
    * 
    * Pour les engagements, nous utilisons  un tableau statique 
    * (taille préfixée à l'avance)
    * 
    * on utilise un tableau 2D pour les assignations où l'intersection 
    * correspond à un "engagement-maître".
    * 
    * Dans le cas des tableaux 1D, les décisions d'implémentation sont 
    * purement pédagogiques pour fournir de l'expérience avec un tableau 
    * dynamique vs un tableau statique.  Des collections standards de Java
    * seraient privilégiés dans un vrai projet. 
    */
   
   // Valeur arbitraire basée sur les données fournies avec l'énoncé.
   public static final int MAX_ENGAGEMENTS = 20;

   // Tableau dynamique
   Maitre[] tabMaitres;

   // Tableau statique
   Engagement[] tabEngagements = new Engagement[MAX_ENGAGEMENTS];
   int nbEngagements;

	// Le tableau d'assignation des maîtres aux engagements.
   // (Qui fait quoi).
   int[][] assignation;

}

