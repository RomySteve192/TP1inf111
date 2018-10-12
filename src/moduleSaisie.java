
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Ce module regroupe les m�thodes d'interaction avec l'utilisateur. Nous avons
 * opt� pour l'utilisation du module JOptionPane de Swing. Ce n'est pas
 * recommandable dans une application professionnelle mais tr�s acceptables pour
 * un travail pratique universitaire.
 *
 * La classe JOptionPane offre des m�thodes qui permettent d'afficher des
 * fen�tres surgissante pour, par exemple, afficher un message, demander �
 * l'utilisateur d'entrer une donn�e, pour afficher un menu dans une liste
 * d�roulante, etc.
 *
 * Les boites de JOptionPane sont affich�es sur la fen�tre courante mais peu
 * changer de coordonn�e en z et se retrouver en dessous d'une autre fen�tre.
 * C'est pour cela qu'on ouvre et ferme une fen�tre de dialogue modale avant
 * d'utiliser JOptionPane. La th�orie sur ces fen�tres sera donn�e plus tard
 * dans la session, ne vous y attardez pas.
 *
 * @author Pierre B�lisle
 * @revisor M�lanie Lord
 * @version A2018
 *
 */
public class moduleSaisie {

   // Fen�tre de fond
   static JFrame diag = new JFrame("Assignation ma�tre-engagement");

   /**
    * Initialise et montre une fen�tre de dialogue.
    */
   public static void initDialogue() {

      Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

      // On rapetisse un peu la fen�tre pour voir le menu du bas de Windows.
      dim.height *= .96;

      diag.setMaximumSize(dim);
      diag.setMinimumSize(dim);
      diag.setPreferredSize(dim);
      diag.setSize(dim);

      diag.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      diag.setVisible(true);
   }

   /**
    * Obtenir la s�lection de l'utilisateur pour un engagement.
    *
    * @param tabEngagements le tableau des engagements
    * @param nbEngagements le nombre d'engagements significatifs dans
    * tabEngagements
    *
    * @return la r�ponse choisie de l'utilisateur (indice dans tabEngagements)
    */
   public static int engagementChoisi(Engagement[] tabEngagements,
           int nbEngagements) {

      /*
       * On utilise JOptionPane avec option et un tableau de String pour
       * offrir tous les engagements � l'utilisateur dans un menu.
       * 
       * Il faut cr�er un tableau de String et le remplir avec les descriptions
       * du tableau d'engagements (voir JOptionPane.showInputDialog).
       * 
       * Il reste � retourner la position de ce choix.
       */
      
      String[] tabEngagementsString = new String[nbEngagements];

      convertirEngagement2String(tabEngagementsString,
              tabEngagements,
              nbEngagements);

      String reponse
              = (String) JOptionPane.showInputDialog(diag,
                      "S�lectionnez un engagement",
                      "Saisit d'un engagement",
                      JOptionPane.QUESTION_MESSAGE,
                      null,
                      tabEngagementsString,
                      tabEngagementsString[0]);

      return choix2Int(reponse, tabEngagementsString);
   }

   /**
    * Obtenir la s�lection de l'utilisateur pour un ma�tre.
    *
    * @param tabMaitres le tableau des ma�tres.
    * @return la r�ponse choisie par l'utilisateur (indice dans tabMaitre)
    */
   public static int maitreChoisi(Maitre[] tabMaitres) {

      /*
       * On utilise JOptionPane avec option avec un tableau de String.
       * Il faut obtenir les noms et cr�er ce tableau.
       */
      
      String[] tabMaitresString = new String[tabMaitres.length];

      convertirMaitre2String(tabMaitresString, tabMaitres);

      String reponse
              = (String) JOptionPane.showInputDialog(diag,
                      "S�lectionnez un ma�tre",
                      "Saisit d'un ma�tre",
                      JOptionPane.QUESTION_MESSAGE,
                      null,
                      tabMaitresString,
                      tabMaitresString[0]);

      return choix2Int(reponse, tabMaitresString);
   }

   /**
    * Permet d'obtenir l'indice d'un choix d'element dans un tableau
    * On est certain que la r�ponse existe.
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
    * @param tabEngagementsString le tableau � remplir avec la description de
    * tous les engagements pr�sents dans tabEngagements
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
    * Remplit le tableau de String avec les codes des ma�tres.
    *
    * @param tabMaitresString le tableau � remplir avec les codes des ma�tres
    * pr�sents dans tabMaitres
    *
    * @param tabMaitres le tableau des ma�tres.
    */
   private static void convertirMaitre2String(String[] tabMaitresString,
           Maitre[] tabMaitres) {

      for (int i = 0; i < tabMaitresString.length; i++) {
         tabMaitresString[i] = String.valueOf(tabMaitres[i].code);
      }

   }
   
   

   /**
    * Affiche le bon menu selon que l'application est fonctionnelle ou non, et
    * retourne l'option s�lectionn�e par l'utilisateur.
    *
    * Voir la m�thode moduleApplication.appEstFonctionnellePourSauvegarde pour
    * conna�tre les conditions d'un application dite fonctionnelle.
    *
    * @param appValide true si l'application est fonctionnelle, false sinon.
    *
    * @return le choix de l'utilisateur au menu offert
    */
   public static String obtenirChoix(boolean appValide) {

      String str;

      // Les r�gles de fonctionnalit� de l'appli ont �t� remplies.
      if (appValide) {

         str = (String) JOptionPane.showInputDialog(
                 diag,
                 "Entrez l'option du menu que vous d�sirez",
                 "Menu principal",
                 JOptionPane.QUESTION_MESSAGE,
                 null,
                 Constantes.tabMenuAvecSauvegarde,
                 Constantes.tabMenuAvecSauvegarde[
					         Constantes.AJOUTER_MAITRE]);
      } // Il ne donne rien d'avoir un menu de sauvegarde si on n'a pas de 
      // donn�es fonctionnelles.  Seules les options d'ajout et d'ouverture 
      // sont offertes alors.
      else {

         str = (String) JOptionPane.showInputDialog(
                 diag,
                 "Entrez l'option du menu que vous d�sirez",
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
    * Ferme la fen�tre de fond.
    */
   public static void fermerDialogue() {
      diag.dispose();
   }
}
