
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Module utilitaire qui permet de recuperer et de sauvegarder les donnees des
 * maitres, des engagements et des assignations.
 *
 * (voir enonce tp1A18INF111).
 *
 * @author Pierre Belisle
 * @revisor Melanie Lord
 * @version Automne 2018
 */
public class ModuleFichier {

   public static final int SAUVE = 0;
   public static final int OPEN = 1;

   /**
    * Methode utilitaire privee qui permet d'obtenir un fichier selectionne par
    * l'utilisateur. L'extension ne doit pas contenir le "."
    *
    * @param description Apparait dans "type de fichier" pour guider
    * l'utilisateur.
    *
    * @param extension Les 3 lettres en suffixe au point d'un nom de fichier.
    *
    * @param type : OUVRE ou SAUVE Sert a avoir le bon bouton dans le
    * JFileChooser, selon le type on a "ouvrir" ou "enregistrer".
    *
    * @return null si le nom n'est pas valide ou si annule.
    */
   private static File obtenirFic(String description,
           String extension,
           int type) {

      /*
       * Strategie : On utilise le JFileChooser de javax.swing selon 
       * le type (SAUVE ou OPEN) recue.
       * 
       * FileNameExtensionFilter permet de filtrer les extensions.
       */
      
      //Creation du selectionneur de fichier (repertoire courant).
      JFileChooser fc = new JFileChooser(".");//parametre le point extension

      File fic = null; // declaration d'un fichier
      int reponse;

      //On filtre seulement les fichiers avec l'extension fournie
      FileNameExtensionFilter filter
              = new FileNameExtensionFilter(extension, extension);

      fc.setDialogTitle(description);
      fc.addChoosableFileFilter(filter);
      fc.setFileFilter(filter);

      //On obtient le nom du fichier a ouvrir en lecture ou en ecriture?
      if (type == OPEN) {
         reponse = fc.showOpenDialog(null);
      } else {
         reponse = fc.showSaveDialog(null);
      }

      //On obtient le fichier seulement si le fichier a ete choisi
      if (reponse == JFileChooser.APPROVE_OPTION) {
         fic = fc.getSelectedFile();
      }

      return fic;
   }

   /**
    * Le SP principal pour la sauvegarde du fichier binaire
    *
    * @param app enregistrement regroupant les donn�es de l'application.
    */
   public static void sauverAppBinaire(Application app) {

      /*
       * Strategie : On utilise  un FileOutputStream qui permet d'ecrire
       * les donn�es de l'application d'un coup.
       */
      
      File fic
              = obtenirFic("Nom du fichier binaire + l'extension (.bin)",
                      "bin",
                      SAUVE);

      if (fic != null) {
         ObjectOutputStream tampon = null;

         try {
            //Cree le fichier et ouverture du tampon d'ecriture		
            FileOutputStream tamponFic = new FileOutputStream(fic);
            tampon = new ObjectOutputStream(tamponFic);

            //	ecriture et fermeture.
            tampon.writeObject(app);
            tampon.close();

         } catch (FileNotFoundException e1) {

            e1.printStackTrace();

         // Une erreur de lecture, on detruit le fichier si on a eu
         // le temps de le creer.
         } catch (IOException e) {

            // On obtient le chemin du fichier pour le detruire.
            Path path
                    = FileSystems.getDefault().getPath(fic.getName());

            // Destruction du fichier ouvert (ou cr��) s'il y a un probl�me.
            try {
               tampon.close();
               Files.delete(path);

            } catch (IOException e1) {
               e1.printStackTrace();
            }

            e.printStackTrace();
         }
      }
   }

   /**
    * Le SP principal pour la sauvegarde des fichiers texte.
    * 
    * @param app enregistrement regroupant les donnees de l'application.
    */
   public static void sauverAppTexte(Application app) {

      /*
       * Strategie : Tout est delegue, ici on ne valide que l'annulation.
       */
      boolean annule = sauverMaitresTexte(app.tabMaitres);

      if (!annule) {

         annule = sauverEngagementsTexte(app.tabEngagements, app.nbEngagements);

         // Il se peut qu'aucune assignation n'existe.
         if (!annule && app.assignation != null) {
            sauverAssignationTexte(app.assignation);
         }
      }
   }

   /**
    * Sauve les maitres, un par ligne, dans un fichier texte selectionne par
    * l'utiliateur. Si le nom de fichier choisit n'existe pas, le fichier sera
    * cree.
    *
    * S'il existe deja, il sera ecrase.
    * 
    * @param tabMaitres le tableau des maitres
    */
   private static boolean sauverMaitresTexte(Maitre[] tabMaitres) {

      /*
       * Strategie : On utilise un printWriter pour ecrire dans un fichier
       * ouvert par obtenirFic.  L'ecriture de la liste. 
       */
      PrintWriter printer = null;

      File fic = obtenirFic("Nom du fichier texte + l'extension (.txt)", "txt",
            SAUVE);

      if (fic != null) {

         try {

            printer = new PrintWriter(fic);

            ecrireListeMaitres(tabMaitres, printer);

         } catch (FileNotFoundException e) {
            e.printStackTrace();
         }

         printer.close();
      }

      return fic == null;
   }

   /**
    * Sauve les engagements, un par ligne, dans un fichier texte selectionne par
    * l'utiliateur. Si le nom de fichier choisi n'existe pas, le fichier sera
    * cree.
    *
    * S'il existe deja, il sera ecrase.
    * 
    * @param tabEngagements le tableau des engagements
    * @param nbEngagement le nombre d'engagements significatifs
    */
   private static boolean sauverEngagementsTexte(Engagement[] tabEngagements,
           int nbEngagements) {

      /*
       * Strategie : On fait la boucle d'affichage ici plutot que dans un SP.
       */
      
      PrintWriter printer = null;

      File fic
              = obtenirFic("Nom du fichier texte + l'extension (.txt)",
                      "txt",
                      SAUVE);

      if (fic != null) {

         try {
            printer = new PrintWriter(fic);

            for (int i = 0; i < nbEngagements; i++) {
               printer.println(tabEngagements[i].description);
            }
         } catch (FileNotFoundException e) {
            e.printStackTrace();
         }

         printer.close();
      }

      return fic == null;
   }

   /**
    * Presente les assignations dans un PrintWriter pr�alablement ouvert et
    * selectionnne par l'utilisateur du programme.
    *
    * Pour afficher a l'ecran, mettre System.out en parametre effectif
    *
    * @param app enregistrement regroupant les donnees de l'application
    * @param printer pour ecrire dans le fichier ouvert correspondant
    */
   public static void ecrireAssignation(Application app,
           PrintWriter printer) {

      /*
       * Strat�gie :Chaque �l�ment est affich� par son SP dans le printer.  
       */
      
      ecrireEnteteEngagements(app.nbEngagements, printer);

      printer.println();

      ecrireRelationMaitreAssignation(app, printer);

      ecrireListeEngagements(app.tabEngagements, app.nbEngagements, printer);

      ecrireListeMaitres(app.tabMaitres, printer);

      printer.close();

   }

   /**
    * ecrit les engagements sur une ligne dans le printer re�u.
    * 
    * @param nbEngagement le nombre d'engagements significatifs
    * @param printer pour ecrire dans le fichier ouvert correspondant
    */
   private static void ecrireEnteteEngagements(int nbEngagements,
         PrintWriter printer) {

      /*
       * Strategie : Simple boucle for de tous les engagements.
       */
      
      // Les entetes de colonne sont des entiers de 0 a nbEngagements - 1.
      for (int i = 0; i < nbEngagements; i++) {

         printer.print(Constantes.TAB);
         printer.print(i);
      }
   }

   /**
    * ecrit la liste des engagements et leur position, sur plusieurs lignes, 
    * dans le printer re�u.
    * 
    * @param tabEngagements le tableau des engagements
    * @param nbEngagement le nombre d'engagements significatifs
    * @param printer pour �crire dans le fichier ouvert correspondant
    */
   private static void ecrireListeEngagements(Engagement[] tabEngagements,
           int nbEngagements,
           PrintWriter printer) {

      /*
       * Strategie : ecrit les engagements et leur position en colonne,
       * on servira d' une boucle for.
       * 
       */
      
      printer.println();
      printer.println();

      for (int i = 0; i < nbEngagements; i++) {

         printer.print(tabEngagements[i].description);
         printer.print(Constantes.TAB);
         printer.println(i);
      }
   }

   /**
    * ecrit les maitres dans le fichier sur plusieurs lignes, dans le printer
    * recu.
    * 
    * @param tabMaitres le tableau des maitres
    * @param printer pour ecrire dans le fichier ouvert correspondant
    */
   private static void ecrireListeMaitres(Maitre[] tabMaitres,
           PrintWriter printer) {

      /*
       * Strategie : ecrit tout sur un maitre, sur une ligne, a chaque tour 
       * de boucle.
       */
      
      printer.println();
      printer.println();

      for (int i = 0; i < tabMaitres.length; i++) {

         printer.println(tabMaitres[i].code + Constantes.TAB
                 + tabMaitres[i].local + Constantes.TAB
                 + tabMaitres[i].numero);
      }
   }

   /**
    * ecrit tout dans un fichier Excel, et a L'ecran.
    * 
    * @param app enregistrement regroupant les donnees de l'application.
    */
   public static void ecrireAssignation(Application app) {

      /*
       * Strategie : On sait que le printer est ferme dans ecrireAssignation
       * pour eviter la repetition de printer.close().
       */
      
      //Fichier a ouvrir
      File fic = obtenirFic("Fichier Excel", "xls", SAUVE);

      try {
         
         PrintWriter printer = new PrintWriter(fic);
         ModuleFichier.ecrireAssignation(app, printer);

         PrintWriter ecran = new PrintWriter(System.out);
         ModuleFichier.ecrireAssignation(app, ecran);
         
      } catch (FileNotFoundException e) {
         JOptionPane.showMessageDialog(null,
                 "Verifier que le fichier n'est pas ouvert dans"
                 + "une autre application tel Excel");
      }
   }

   /**
    * Methode qui prmet de suvegarder les assignations sous forme 
    * de fichier texte
    * 
    * @param assignation le tableau d'assignation
    */
	 
   private static void sauverAssignationTexte(int[][] assignation) {

      /*
       * Strategie : ecrire dans un fichier texte choisi si il existe, 
       * sinon on le cree
       */
      PrintWriter printer = null;
      String Ligne = "";

      File fic = obtenirFic("Nom du fichier texte + l'extension (.txt)",
                      "txt",
                      SAUVE);

      if (fic != null) {

         try {
            printer = new PrintWriter(fic);

            for (int i = 0; i < assignation.length; i++) {
                for(int j = 0; j < assignation[i].length; j++){
                   Ligne += assignation[i][j];
                }
               printer.println(Ligne);
               Ligne = "";
            }
         } catch (FileNotFoundException e) {
            e.printStackTrace();
         }
         printer.close();
      }
   }

   /**
    * Methode qui permet d'obtenir les donnees d'enregistrement application
    * sous forme de texte.
    * 
    * @param app enregistrement regroupant les donnees de l'application.
    */
	 
   public static void obtenirAppTexte(Application app) {

      /*
       * Strategie :L'utilisateur doit valider les 3 informations 
       * avant l'obtention des donnees enregistrees. 
       * Des que il y a annuler a une etape la demande est refaire.
       */
      boolean annuler1;
      boolean annuler2;
      boolean annuler3;
      Application app2 = new Application();//objet de copie
   
      app2.tabMaitres = app.tabMaitres;
      app2.tabEngagements = app.tabEngagements;
      app2.assignation  = app.assignation;
      app2.nbEngagements = app.nbEngagements;
    
      annuler1 = false;
      annuler2 = false;
      annuler3 = false;
      
      if(obtenirMaitresTexte(app)){
         annuler1 = true;
      }
      
      if(annuler1 && obtenirEngagementsTexte(app)){
         annuler2 = true;
      }
      
      if(annuler1 && annuler2 && obtenirAssignationTexte(app)){
         annuler3 = true;
      }
     
      if(!annuler1 || !annuler2 || !annuler3){
          app.tabMaitres = app2.tabMaitres;
          app.tabEngagements = app2.tabEngagements;
          app.assignation  = app2.assignation;
          app.nbEngagements = app2.nbEngagements;
      }
   }

   /**
    * Methode qui permet d'obtenir les informations sous forme binaire
    * 
    * @return retourne des donnees d'application
    */
	
   public static Application obtenirAppBinaire() {

      /*
       * Strategie : ecrire dans un fichier binaire choisi si il existe, 
       * sinon on le cree
       */
      Application app = new Application();
      File fic = obtenirFic("Obtenir le fichier binaire", "bin", SAUVE);
      
      if(fic != null){
          try{
              
              FileInputStream inFile = new FileInputStream(fic);
              ObjectInputStream inStream = new ObjectInputStream(inFile);
              app = (Application)inStream.readObject();
              
          }catch(FileNotFoundException e){
             e.printStackTrace();
          }catch(Exception e){
             JOptionPane.showMessageDialog(null, "Format de fichier invalide");
             e.printStackTrace();
          }
      }
      return app;
   }

   /**
    * Methode qui prmet d'obtenir les assignations sous forme 
    * de fichier texte
    * @param app
    * @return retourne vrai si tout a fonctionner
    */
	 
   private static boolean obtenirAssignationTexte(Application app){

      /*
       * Strategie : une boucle qui test a chaque fois
       * si on n'est pas a la fin de fichier et que la prochaine 
       * ligne n'est pas vide
       */
      
     String ligne;
     Boolean ass = false;
    
     File fic = obtenirFic ("Obtenir fichier Assignation", "txt", OPEN);
     
     if(fic != null){
         app.assignation 
         	= new int[app.tabMaitres.length][app.tabEngagements.length];
         try{
             Scanner fichier = new Scanner(fic);
             
             while(fichier.hasNextLine()){
                if((ligne = fichier.nextLine())!= null){
                    for(int j = 0; j < app.tabMaitres.length; j++){
                            for(int i = 0; i < app.nbEngagements; i++){
                            	
                                if(i < ligne.length() && ligne.length() 
                                		< app.tabEngagements.length){
                                	
                                    app.assignation[j][i] 
                                    		= Character.getNumericValue(
                                    				ligne.charAt(i));
                                }else if(i < app.nbEngagements){
                                	
                                    app.assignation[j][i] = 0;
                                }
                            }
                    }
                }
             }
             ass = true;
          fichier.close();
         }catch(FileNotFoundException e){
             e.printStackTrace();
         }
   
     
     }
     return ass;
   }

   /**
    * Methode qui permet d'obtenir les donnees du maitre sous forme 
    * de fichier texte
    * @param app enregistrement d'application
    * @return retourne vrai si l'operation a bien fonctionne
    */
	
   private static boolean obtenirMaitresTexte(Application app) {

      /*
       * Strategie : une boucle qui test a chaque fois
       * si on n'est pas a la fin de fichier et que la prochaine 
       * ligne n'est pas vide
       */
    
     String ligne;//stocke les donnees du maitre en une chaine de caractere
     String[] tab; // tableau pour ranger les donnees du maitre
     Boolean mait = false;
     File fic = obtenirFic ("Obtenir fichier maître", "txt", OPEN);
     
     if(fic != null){
        app.tabMaitres = null; 
        try{
             Scanner fichier = new Scanner(fic);
             while(fichier.hasNextLine()){
                 if((ligne = fichier.nextLine())!= null){
                     Maitre maitre = new Maitre();
                     tab =ligne.split("\\s+");
                     maitre.code = Integer.parseInt(tab[0]);
                     maitre.local = tab[1];
                     maitre.numero = Integer.parseInt(tab[2]);
                     ModuleApplication.ajoutMaitre(maitre, app);
                 }
             }
             mait = true;
             
          fichier.close();
         }catch(FileNotFoundException e){
             e.printStackTrace();
         }
     }
      return mait;
   }

   /**
    * Methode qui permet d'obtenir les donnees des engagements sous forme 
    * de fichier texte
    * @param app enregistrement d'application
    * @return retourne vrai si l'operation a bien fonctionne
    */
	
   private static boolean obtenirEngagementsTexte(Application app) {

      /*
       * Strategie : une boucle qui test a chaque fois
       * si on n'est pas a la fin de fichier et que la prochaine 
       * ligne n'est pas vide
       * 
       */
     
     String ligne;
     int nbreLigne = 0;
     int i = 0;
     Boolean eng = false;
     File fic = obtenirFic ("Obtenir fichier Engagement", "txt", OPEN);
    
     if(fic != null){
         
         try{
             app.tabEngagements = new Engagement[Application.MAX_ENGAGEMENTS];
             Scanner fichier = new Scanner(fic);
             while(fichier.hasNextLine()){
                 if((ligne = fichier.nextLine())!= null){
                     if(i < Application.MAX_ENGAGEMENTS){
                        Engagement enga = new Engagement();
                        enga.description = ligne;
                        app.tabEngagements[i] = enga;
                        nbreLigne++;
                        i++;
                     }
                 }
             }
          eng = true;
          app.nbEngagements = nbreLigne;
          fichier.close();
         }catch(FileNotFoundException e){
             e.printStackTrace();
         }
     }
     return eng;
   }

   /**
    * Methode qui permet d'écrire les données de l’application 
    * dans un fichier Excel
    * 
    * @param app enregistrement application
    * @param printer pour ecrire dans un fichier
    */
	 
   private static void ecrireRelationMaitreAssignation(Application app,
           PrintWriter printer) {

      /*
       * Strategie :imbriquer 2 boucles et écrire les assignations dans 
       * le printer
       */
      for(int i = 0; i < app.tabMaitres.length; i++){
          
         printer.print(app.tabMaitres[i].code);
         printer.print(Constantes.TAB);
         
         if(app.assignation != null){
             
             for(int j = 0; j < app.nbEngagements; j++){
                 if(app.assignation[i][j] == 1){
                    printer.print("X");
                    printer.print(Constantes.TAB);
                 }
             }
             printer.println();
         }
      }
   }
   
  
}