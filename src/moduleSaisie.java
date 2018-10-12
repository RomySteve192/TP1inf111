
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Ce module regroupe les méthodes d'interaction avec l'utilisateur. Nous avons
 * opté pour l'utilisation du module JOptionPane de Swing. Ce n'est pas
 * recommandable dans une application professionnelle mais très acceptables pour
 * un travail pratique universitaire.
 *
 * La classe JOptionPane offre des méthodes qui permettent d'afficher des
 * fenêtres surgissante pour, par exemple, afficher un message, demander à
 * l'utilisateur d'entrer une donnée, pour afficher un menu dans une liste
 * déroulante, etc.
 *
 * Les boites de JOptionPane sont affichées sur la fenêtre courante mais peu
 * changer de coordonnée en z et se retrouver en dessous d'une autre fenêtre.
 * C'est pour cela qu'on ouvre et ferme une fenêtre de dialogue modale avant
 * d'utiliser JOptionPane. La théorie sur ces fenêtres sera donnée plus tard
 * dans la session, ne vous y attardez pas.
 *
 * @author Pierre Bélisle
 * @revisor Mélanie Lord
 * @version A2018
 *
 */
public class moduleSaisie {

   // Fenêtre de fond
   static JFrame diag = new JFrame("Assignation maître-engagement");

   /**
    * Initialise et montre une fenêtre de dialogue.
    */
   public static void initDialogue() {

      Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

      // On rapetisse un peu la fenêtre pour voir le menu du bas de Windows.
      dim.height *= .96;

      diag.setMaximumSize(dim);
      diag.setMinimumSize(dim);
      diag.setPreferredSize(dim);
      diag.setSize(dim);

      diag.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      diag.setVisible(true);
   }

   /**
    * Obtenir la sélection de l'utilisateur pour un engagement.
    *
    * @param tabEngagements le tableau des engagements
    * @param nbEngagements le nombre d'engagements significatifs dans
    * tabEngagements
    *
    * @return la réponse choisie de l'utilisateur (indice dans tabEngagements)
    */
   public static int engagementChoisi(Engagement[] tabEngagements,
           int nbEngagements) {

      /*
       * On utilise JOptionPane avec option et un tableau de String pour
       * offrir tous les engagements à l'utilisateur dans un menu.
       * 
       * Il faut créer un tableau de String et le remplir avec les descriptions
       * du tableau d'engagements (voir JOptionPane.showInputDialog).
       * 
       * Il reste à retourner la position de ce choix.
       */
      
      String[] tabEngagementsString = new String[nbEngagements];

      convertirEngagement2String(tabEngagementsString,
              tabEngagements,
              nbEngagements);

      String reponse
              = (String) JOptionPane.showInputDialog(diag,
                      "Sélectionnez un engagement",
                      "Saisit d'un engagement",
                      JOptionPane.QUESTION_MESSAGE,
                      null,
                      tabEngagementsString,
                      tabEngagementsString[0]);

      return choix2Int(reponse, tabEngagementsString);
   }

   /**
    * Obtenir la sélection de l'utilisateur pour un maître.
    *
    * @param tabMaitres le tableau des maîtres.
    * @return la réponse choisie par l'utilisateur (indice dans tabMaitre)
    */
   public static int maitreChoisi(Maitre[] tabMaitres) {

      /*
       * On utilise JOptionPane avec option avec un tableau de String.
       * Il faut obtenir les noms et créer ce tableau.
       */
      
      String[] tabMaitresString = new String[tabMaitres.length];

      convertirMaitre2String(tabMaitresString, tabMaitres);

      String reponse
              = (String) JOptionPane.showInputDialog(diag,
                      "Sélectionnez un maître",
                      "Saisit d'un maître",
                      JOptionPane.QUESTION_MESSAGE,
                      null,
                      tabMaitresString,
                      tabMaitresString[0]);

      return choix2Int(reponse, tabMaitresString);
   }

   /**
    * Permet d'obtenir l'indice d'un choix d'element dans un tableau
    * On est certain que la réponse existe.
    * 
    * @param reponse le choix effecctue
    * @param tabString le tableau dans lequel s'effectue le choix
    * @return Retourne la position de la chaine dans le tableau
    */
     
   private static int choix2Int(String reponse,
           String[] tabString) {

      int choix = 0;

      while (!tabString[choix].equals(reponse)) {
         choix++;
      }

      return choix;
   }

   /**
    * Remplit le tableau de String avec les descriptions des engagements.
    *
    * @param tabEngagementsString le tableau à remplir avec la description de
    * tous les engagements présents dans tabEngagements
    *
    * @param tabEngagements le tableau des engagements
    *
    * @param le nombre d'engagements significatifs dans tabEngagements
    */
   private static void convertirEngagement2String(String[] tabEngagementsString,
           Engagement[] tabEngagements,
           int nbEngagements) {

      for (int i = 0; i < nbEngagements; i++) {
         tabEngagementsString[i] = tabEngagements[i].description;
      }

   }

   /**
    * Remplit le tableau de String avec les codes des maîtres.
    *
    * @param tabMaitresString le tableau à remplir avec les codes des maîtres
    * présents dans tabMaitres
    *
    * @param tabMaitres le tableau des maîtres.
    */
   private static void convertirMaitre2String(String[] tabMaitresString,
           Maitre[] tabMaitres) {

      for (int i = 0; i < tabMaitresString.length; i++) {
         tabMaitresString[i] = String.valueOf(tabMaitres[i].code);
      }

   }
   
   

   /**
    * Affiche le bon menu selon que l'application est fonctionnelle ou non, et
    * retourne l'option sélectionnée par l'utilisateur.
    *
    * Voir la méthode moduleApplication.appEstFonctionnellePourSauvegarde pour
    * connaître les conditions d'un application dite fonctionnelle.
    *
    * @param appValide true si l'application est fonctionnelle, false sinon.
    *
    * @return le choix de l'utilisateur au menu offert
    */
   public static String obtenirChoix(boolean appValide) {

      String str;

      // Les règles de fonctionnalité de l'appli ont été remplies.
      if (appValide) {

         str = (String) JOptionPane.showInputDialog(
                 diag,
                 "Entrez l'option du menu que vous désirez",
                 "Menu principal",
                 JOptionPane.QUESTION_MESSAGE,
                 null,
                 Constantes.tabMenuAvecSauvegarde,
                 Constantes.tabMenuAvecSauvegarde[
					         Constantes.AJOUTER_MAITRE]);
      } // Il ne donne rien d'avoir un menu de sauvegarde si on n'a pas de 
      // données fonctionnelles.  Seules les options d'ajout et d'ouverture 
      // sont offertes alors.
      else {

         str = (String) JOptionPane.showInputDialog(
                 diag,
                 "Entrez l'option du menu que vous désirez",
                 "Menu principal",
                 JOptionPane.QUESTION_MESSAGE,
                 null,
                 Constantes.tabMenuSansSauvegarde,
                 Constantes.tabMenuSansSauvegarde[
					         Constantes.AJOUTER_MAITRE]);
      }

      return str;
   }

   /**
    * Ferme la fenêtre de fond.
    */
   public static void fermerDialogue() {
      diag.dispose();
   }
}
