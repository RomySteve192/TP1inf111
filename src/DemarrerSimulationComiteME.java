
import javax.swing.JOptionPane;

/**
 * Ce travail est la solution du tp1A18INF111 (voir �nonc�).
 *
 * Les commentaires de strat�gie sont �crits en tenant compte que l'�nonc� du
 * travail a �t� lue.
 *
 * Pour fonctionner, vous devez avoir aussi les fichiers :
 *
 * Constantes.java, moduleApplication.java, moduleFichier.java,
 * moduleSaisie.java, Application.java, Maitre.java, Engagement.java
 *
 * Tous les autres sous-programmes sont locaux
 *
 * @author Pierre B�lisle
 * @revisor M�lanie Lord
 * @version A2018
 */
public class DemarrerSimulationComiteME {

   /**
    * Le programme principal est classique pour ce genre d'application.
    *
    * Il contient la boucle principale et fait appel aux sous-programmes qui : -
    * pr�pare l'application - affiche le menu principal - saisie et valide le
    * choix - effectue la demande - affiche un message de fin
    *
    * L'application est s�par�e en plusieurs sous-programmes qui peuvent l'�tre
    * � leur tour. Nous sugg�rons d'utiliser un environnement qui montre toutes
    * les sp�cifications tel Eclipse avec "Outline" ou BlueJ avec '.' + espace.
    *
    * @param args inutilis�
    */
   public static void main(String[] args) {

      // Sert � la saisie du choix du menu.
      String choixStr;

      // Les donn�es de l'application sont regroup�es dans un enregistrement.
      Application app = new Application();

	  // Ouvre une fen�tre en "background".  Ainsi JOptionPane montre sa
      // fen�tre en avant.  Sinon, la fen�tre de JOptionPane change de 
      // position en z selon la fen�tre active.
      moduleSaisie.initDialogue();

      do {

         /* 
          * Les r�gles de validation sont d�crites dans la m�thode.  En
          * gros c'est aucune r�f�rence � null et le nombre d'engagements > 0
          */
         boolean appValide
                 = ModuleApplication.appEstFonctionnellePourSauvegarde(app);

         // Retourne la cha�ne s�lectionn�ee par l'utilisateur.
         choixStr = moduleSaisie.obtenirChoix(appValide);

         if (choixStr != null) {
            app = effectuerDemande(choixStr, app);
         } else {
            // Ferme la fen�tre de fond.
            moduleSaisie.fermerDialogue();
         }

      } while (choixStr != null);

      JOptionPane.showMessageDialog(null, "Merci et bonne journ�e!!!");
   }

   /**
    * Convertit le choix en entier selon sa position dans le menu.
    *
    * @param choixStr Une des options de Constantes.tabMenus
    * @param app un enregistrement contenant les donn�es de l'application.
    */
   private static Application effectuerDemande(String choixStr,
           Application app) {

      // Si choixStr == null, l'utilisateur a annul�, c'est qu'il veut quitter.
      if (choixStr != null) {

         if (choixStr.equals(Constantes.tabMenuAvecSauvegarde[
			                                Constantes.OUVRIR_FICHIER_TEXTE])) {

            ModuleFichier.obtenirAppTexte(app);

         } else if (choixStr.equals(Constantes.tabMenuAvecSauvegarde[
			                              Constantes.OUVRIR_FICHIER_BINAIRE])) {

            app = ModuleFichier.obtenirAppBinaire();
            
         } else if (choixStr.equals(Constantes.tabMenuAvecSauvegarde[
			                                Constantes.SAUVER_FICHIER_TEXTE])) {

            ModuleFichier.sauverAppTexte(app);
            
         } else if (choixStr.equals(Constantes.tabMenuAvecSauvegarde[
			                              Constantes.SAUVER_FICHIER_BINAIRE])) {

            ModuleFichier.sauverAppBinaire(app);
            
         } else if (choixStr.equals(Constantes.tabMenuAvecSauvegarde[
			                                      Constantes.AJOUTER_MAITRE])) {

            ModuleApplication.traiterAjoutMaitre(app);
            
         } else if (choixStr.equals(Constantes.tabMenuAvecSauvegarde[
			                                  Constantes.AJOUTER_ENGAGEMENT])) {

            ModuleApplication.traiterAjoutEngagement(app);
            
         } else if (choixStr.equals(Constantes.tabMenuAvecSauvegarde[
			                                 Constantes.ASSIGNER_ENGAGEMENT])) {

            ModuleApplication.traiterAssignation(app);
            
         } else if (choixStr.equals(Constantes.tabMenuAvecSauvegarde[
			                               Constantes.PRESENTER_ASSIGNATION])) {

            ModuleFichier.ecrireAssignation(app);
            
         } else if (choixStr.equals(Constantes.tabMenuAvecSauvegarde[
			                                      Constantes.OBTENIR_MAITRES])) {

            // Obtenir les ma�tres d'un engagement
            ModuleApplication.montrerMaitresDunEngagement(app);
            
         } else if (choixStr.equals(Constantes.tabMenuAvecSauvegarde[
			                              Constantes.OBTENIR_ENGAGEMENTS])) {

            // Obtenir les engagements d'un ma�tre
            ModuleApplication.montrerEngagementsDunMaitre(app);
         }

      }

      return app;
   }
}
