
import java.util.Arrays;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JTextField;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Dans le contexte du tp1A18INF111 PARTIE 1 (voir �nonc�).
 *
 * Ce module regroupe les SP qui concernent l'application, ce qui implique la
 * gestion des ma�tres, des engagements, des assignations et la saisie.
 *
 * On n'a pas opt� pour un moduleMaitre, un moduleEngagement et un
 * moduleAssignation pour des consid�rations p�dagogiques. On voit mieux la
 * r�p�tition de code lorsque les SP sont adjacents.
 *
 * SP == sous-programme(s)
 *
 * @author Pierre B�lisle
 * @revisor Melanie Lord
 * @version A2018
 */
public class ModuleApplication {

   /**
    * Retourne si l'application a assez de donn�es pour �tre fonctionnelle : 
    * il doit y avoir au moins 1 ma�tre et 1 engagement.
    *
    * @param app un enregistrement qui regroupe les donn�es de l'application.
    * @return true si l'app est fonctionnelle, false sinon.
    */
   public static boolean appEstFonctionnellePourSauvegarde(Application app) {

      return app.tabMaitres != null && app.nbEngagements > 0;
   }
  /**
   * Methode ajoutMaitre qui permet d'ajouter un nouveau maitre
   * a la table des maitres
   * @param nouveauMaitre
   * @param app enregistement d'application
   */
   
   
   public static void ajoutMaitre(Maitre nouveauMaitre, Application app) {
       
	if (app.tabMaitres == null) {
	    app.tabMaitres = new Maitre[1]; //Creer table d'une case
	    app.tabMaitres[0] = nouveauMaitre; // ajout du nouveau maitre
		
	}else {
	    app.tabMaitres = Arrays.copyOf(app.tabMaitres, 
				app.tabMaitres.length + 1); //ajout d'une case de plus 
											// a  tabMaitre
	    app.tabMaitres[app.tabMaitres.length - 1] = nouveauMaitre;
	}
   }
   /**
    * Methode qui permet de creer et initialiser un interface de 
    * saisi. Aussi on poura choisir l'interface approprie
    * @param constanteFen recoit CONSTANTEAJOUT
    * @param codeField variable de zone de saisi du code
    * @param localField variable de zone de saisi du local
    * @param numeroField variable de zone de saisi du numero
    * @param descriptionField variable de zone de saisi de description
    * @return retourne un JPanel
    */
   
   private static JPanel initFenetreAjout(int constanteFen, JTextField  codeField,
           JTextField  localField,  JTextField  numeroField,
           JTextField  descriptionField){
           JPanel panel = new JPanel();
           
	   switch (constanteFen) {
	    case 0:
                    panel.add ( new JLabel ( "Code:" )); // ajout label code 
                    panel.add (codeField);
                    panel.add ( new JLabel ( "Local:" )); // ajout label Local
                    panel.add (localField);
                    panel.add ( new JLabel ( "Numero:" )); // ajout label Numero
                    panel.add (numeroField);
                 break;
		
	    case 1:
                    panel.add ( new JLabel ( "Description:" ));
                    panel.add (descriptionField);
                 break;
	   }
           
           return panel;
   
   }
   
   /**
    * Methode qui ouvre une fenetre de dialogue permettant de renseigner
    * un maitre
    * 
    * @param app enregistrement d'application
    */
    
   public static void fenetreAjoutMaitre(Application app) {
	    JTextField  codeField = new JTextField (5);
	    JTextField  localField = new JTextField (5);
	    JTextField  numeroField = new JTextField (5);
	    Maitre nouveauMaitre = new Maitre();
            JPanel panMaitre = new JPanel();
            int resultat;
            
            panMaitre = initFenetreAjout(Constantes.AJOUTER_MAITRE, codeField,
                                            localField, numeroField,
                                           null);

	    //confirme la saisi et enregistre les informations du nouveau maitre
	    resultat = JOptionPane.showConfirmDialog (null, panMaitre,
	         "Veuillez entrer le code (1,2,..), le local(B2524) et "
	         + "le numero " , JOptionPane.OK_CANCEL_OPTION);
	    
	    if (resultat == JOptionPane.OK_OPTION) {
	    	
	        nouveauMaitre.code = Integer.parseInt(codeField.getText());
	        nouveauMaitre.local = localField.getText ();
	        nouveauMaitre.numero = Integer.parseInt(numeroField.getText());
	        ajoutMaitre(nouveauMaitre, app);
	    } 
   }
   
   /**
    * Affiche une boite de dialogue qui permet de decrire un engagement
    * @param tabEngagements tableau d'engagement
    * @param nbreEng nombre d'engagement effectif
    * @param maxEnga nombre d'engagement qu'on ne peut pas depasser
    */
   public static void fenetreAjoutEngagement(Application app, int nbreEng, 
		   int maxEnga) {
	   
	   JTextField  descriptionField = new JTextField (15);
	   Engagement newEnga = new Engagement();
	   JPanel panEngagement = new JPanel ();
           int resultat;
	  
       panEngagement = initFenetreAjout(Constantes.AJOUTER_ENGAGEMENT,  
                                           null, null, null,
                                           descriptionField);
	   
	   resultat = JOptionPane.showConfirmDialog (null, panEngagement,
		         "Veuillez entrer la description de l'engagement", 
		          JOptionPane.OK_CANCEL_OPTION);
           
	   if (resultat == JOptionPane.OK_OPTION) {
		newEnga.description = descriptionField.getText();
		ajoutEngagement(newEnga,  app, nbreEng,  maxEnga);
	   }
   }

   /**
    * Methode qui permet de reseigner les champs d'un maitre
    * 
    * @param app enregistrement d'application
    */
    
   public static void traiterAjoutMaitre(Application app) {

      /*
       * Strategie :Faire appel a une methode:
       * Nous utilisons ici une methode FenetreDesAjouts qui ouvre 
       * l'interface de saisi.
       */
       FenetreDesAjouts(Constantes.AJOUTER_MAITRE,  app);
   }

   /**
    * Permet acceder a l'interface d'ajout d'un engagement
    * @param app enregistrement d'application
    */
   public static void traiterAjoutEngagement(Application app) {

      /*
       * Strategie :Faire appel a une methode:
       * Nous utilisons ici une methode FenetreDesAjouts qui ouvre 
       * l'interface de saisi. Avant la validation de la saisi, 
       * on test si le nombre maximal d'engagement est atteint et 
       * on renvoie un message a l'utilisateur.
       */
       FenetreDesAjouts(Constantes.AJOUTER_ENGAGEMENT,  app);
       if(app.nbEngagements < app.MAX_ENGAGEMENTS) {
	    app.nbEngagements++;
        }else if(app.nbEngagements == app.MAX_ENGAGEMENTS) {
		   JOptionPane.showMessageDialog (null
				        , "Vous ne pouvez plus ajouter d engagement "
				        , "Engagement"
				        , JOptionPane.INFORMATION_MESSAGE);
        }
   }

   /**
    * Methode qui permet d'assigner un maitre a des engagements
    * @param app enregistrement d'application
    */
    
   public static void traiterAssignation(Application app) {

       int IndiceMaitre;//indice du maitre choisi 
       int IndiceEngagement;//indice de l'engagement choisi
       
       IndiceMaitre = moduleSaisie.maitreChoisi(app.tabMaitres);
       IndiceEngagement = moduleSaisie.engagementChoisi(app.tabEngagements, 
    		   app.nbEngagements);
       
       //Faire une assignation
       if(assignationMaitreEngagement(app, IndiceEngagement, 
    		   IndiceMaitre) == 1) {
    	   JOptionPane.showMessageDialog (null
			        , "Assignation reussie!!!"
			        , "Assignation"
			        , JOptionPane.INFORMATION_MESSAGE);
       }
   }
   
   /**
    * Methode qui permet de lier un maitre a un engagement
    * @param app enregistrement d'application
    * @param indEnga indice d'un engagement
    * @param indMait indice d'un maitre
    * @return retourne 1
    */
   public static int assignationMaitreEngagement(Application app, int indEnga,
		   int indMait) {
	   
	   if(app.assignation == null) {
		   app.assignation = 
				   new int[app.tabMaitres.length][app.tabEngagements.length];
	   }
	   
	   app.assignation[indMait][indEnga] = 1;
	   return app.assignation[indMait][indEnga];
   }
   /**
    * Methode qui permet d'obtenir les engagement d'un maitre
    * @param indMaitre indice du maitre choisi
    * @param app enregistrement d'application
    * @return une chaine caractere des engagements
    */

   private static String obtenirEngagementDunMaitre(int indMaitre, 
		   Application app) {
	   
	   String ListeEnga; //Liste d'engagement
	   ListeEnga = "";
	   
	   for(int i = 0; i < app.MAX_ENGAGEMENTS; i++) {
		  //parcour la ligne du maitre dans le tableau 2D assignation 
		  if(app.assignation[indMaitre][i] == 1) {
			  ListeEnga += app.tabEngagements[i].description + " \t\n";
		  }
	   }
	   
	   return ListeEnga;
   }
   /**
    * Methode privee qui permet d'obtenir les maitres lies a un engagement
    *  
    * @param indEnga indice de l'engagement choisi
    * @param app enregistrement d'application
    * @return retourne la liste des codes des maitres
    */
   private static String obtenirMaitresDunEngagement(int indEnga, 
		   Application app) {
	   
	   String ListeMaitre;
	   ListeMaitre = "";
	   
	 //parcour la colonne de l'engagement dans le tableau 2D assignation 
	   for(int i = 0; i < app.tabMaitres.length; i++) {
		  if(app.assignation[i][indEnga] == 1) {
			ListeMaitre += app.tabMaitres[i].code + " \t\n";
		  }
	   }
	   return ListeMaitre;
   }
   /**
    * Methode qui permet de montrer les engagements lies a un maitre
    * 
    * @param app enregistement d'application
    */
    
   public static void montrerEngagementsDunMaitre(Application app) {
      /*
       * Strategie :On se servira de l'indice retourner par la methode 
       * maitreChoisi() pour obtenir les engagements avec 
       * obtenirEngagementDunMaitre()
       */
       String AfficherEnga;
       int IndiceMaitre;
       
       IndiceMaitre = moduleSaisie.maitreChoisi(app.tabMaitres);
       AfficherEnga = obtenirEngagementDunMaitre(IndiceMaitre, app);
	   
       JOptionPane.showMessageDialog (null
		        , AfficherEnga
		        , "Liste Engagement du maitre donc le code est: " 
		            + app.tabMaitres[IndiceMaitre].code
		        , JOptionPane.INFORMATION_MESSAGE);
	   
   }

   /**
    * Methode qui permet de montrer les maitres lies a un engagement
    * 
    * @param app enregistement d'application
    */
    
   public static void montrerMaitresDunEngagement(Application app) {

      /*
       * Strategie :On se servira de l'indice retourner par la methode 
       * engagementChoisi() pour obtenir les maitres avec
       * obtenirMaitresDunEngagement()
       */
       String AfficherMait;
       int IndiceEnga;
       IndiceEnga = moduleSaisie.engagementChoisi(app.tabEngagements, 
    		   app.nbEngagements);
       
       //obtenir la liste des maitres lies a l'engagement d'IndiceEnga
       AfficherMait = obtenirMaitresDunEngagement(IndiceEnga, app);
       
	   //Affichage de la liste a partir de JOptionPane
       JOptionPane.showMessageDialog (null
		        , AfficherMait
		        , "Liste Maitre de l' engagement " 
		           + app.tabEngagements[IndiceEnga].description
		        , JOptionPane.INFORMATION_MESSAGE);
   }
   /**
    * Methode qui permet de decrire et enregistrer un engagement 
    * dans tabEngagement
    * 
    * @param newEngagement un nouveau engagement 
    * @param tabEngagements tableau dea engagements
    * @param nbreEnga nombre d'engagement
    * @param maxEnga nombre maximal d'engagement
    * @return retourne tabEngament 
    */
   public static void ajoutEngagement(Engagement newEngagement, 
		   Application app, int nbreEnga, int maxEnga) {
		 
       //Verifier le nombre d'engagement avant l'ajout du nouveau 
	   if(nbreEnga < maxEnga) {
		  app.tabEngagements[nbreEnga] = newEngagement;
	   }
   }
   /**
    * Methode qui permet de choix des interfaces de saisi d'un maitre
    * ou un engagement.
    * @param constanteAjout constante identifiant des ajouts
    * @param app enregistrements d'application
    */
   //Ceci est une idee d'optimisation du code, on peut le faire autrement
   public static void FenetreDesAjouts(int constanteAjout, Application app) {
	   
	   switch (constanteAjout) {
	    case 0:
		  //Appel methode ajout maitre
	    	fenetreAjoutMaitre(app);
	        break;
		
	    case 1:
	         //Appel methode ajout engagement
	    	fenetreAjoutEngagement(app, app.nbEngagements, app.MAX_ENGAGEMENTS);
		break;
	   }
	   
   }
   
 
}
