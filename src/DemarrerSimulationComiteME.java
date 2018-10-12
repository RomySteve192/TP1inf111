
import javax.swing.JOptionPane;

/**
 * Ce travail est la solution du tp1A18INF111 (voir énoncé).
 *
 * Les commentaires de stratégie sont écrits en tenant compte que l'énoncé du
 * travail a été lue.
 *
 * Pour fonctionner, vous devez avoir aussi les fichiers :
 *
 * Constantes.java, moduleApplication.java, moduleFichier.java,
 * moduleSaisie.java, Application.java, Maitre.java, Engagement.java
 *
 * Tous les autres sous-programmes sont locaux
 *
 * @author Pierre Bélisle
 * @revisor Mélanie Lord
 * @version A2018
 */
public class DemarrerSimulationComiteME {

   /**
    * Le programme principal est classique pour ce genre d'application.
    *
    * Il contient la boucle principale et fait appel aux sous-programmes qui : -
    * prépare l'application - affiche le menu principal - saisie et valide le
    * choix - effectue la demande - affiche un message de fin
    *
    * L'application est séparée en plusieurs sous-programmes qui peuvent l'être
    * à leur tour. Nous suggérons d'utiliser un environnement qui montre toutes
    * les spécifications tel Eclipse avec "Outline" ou BlueJ avec '.' + espace.
    *
    * @param args inutilisé
    */
   public static void main(String[] args) {

      // Sert à la saisie du choix du menu.
      String choixStr;

      // Les données de l'application sont regroupées dans un enregistrement.
      Application app = new Application();

	  // Ouvre une fenêtre en "background".  Ainsi JOptionPane montre sa
      // fenêtre en avant.  Sinon, la fenêtre de JOptionPane change de 
      // position en z selon la fenêtre active.
      moduleSaisie.initDialogue();

      do {

         /* 
          * Les règles de validation sont décrites dans la méthode.  En
          * gros c'est aucune référence à null et le nombre d'engagements > 0
          */
         boolean appValide
                 = ModuleApplication.appEstFonctionnellePourSauvegarde(app);

         // Retourne la chaîne sélectionnéee par l'utilisateur.
         choixStr = moduleSaisie.obtenirChoix(appValide);

         if (choixStr != null) {
            app = effectuerDemande(choixStr, app);
         } else {
            // Ferme la fenêtre de fond.
            moduleSaisie.fermerDialogue();
         }

      } while (choixStr != null);

      JOptionPane.showMessageDialog(null, "Merci et bonne journée!!!");
   }

   /**
    * Convertit le choix en entier selon sa position dans le menu.
    *
    * @param choixStr Une des options de Constantes.tabMenus
    * @param app un enregistrement contenant les données de l'application.
    */
   private static Application effectuerDemande(String choixStr,
           Application app) {

      // Si choixStr == null, l'utilisateur a annulé, c'est qu'il veut quitter.
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

            // Obtenir les maîtres d'un engagement
            ModuleApplication.montrerMaitresDunEngagement(app);
            
         } else if (choixStr.equals(Constantes.tabMenuAvecSauvegarde[
			                              Constantes.OBTENIR_ENGAGEMENTS])) {

            // Obtenir les engagements d'un maître
            ModuleApplication.montrerEngagementsDunMaitre(app);
         }

      }

      return app;
   }
}
