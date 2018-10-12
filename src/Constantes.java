/**
 * Definitions des constantes globales au projet.
 * 
 * Il peut y avoir d'autres constantes sp�cifiques d�finies ailleurs. 
 * 
 * @author Pierre B�lisle
 * @revisor M�lanie Lord
 * @version A2018
 */
public class Constantes {
	
	/*
	 * Strat�gie : Comme on utilise JOptionPane, il faut un tableau d'objets
	 * pour offrir des options de menu dans showInputDialog.
	 * 
	 * Ici les objets sont des String.  Il y a deux menus : avec des options
	 * de sauvegarde et sans option de sauvegarde.
	 * 
	 * La s�lection gr�ce � appEstFonctionnellePourSauvegarde dans obtenirChoix
	 * du menu principal.  
	 * 
	 * Pour ajouter des options au menu, il suffit d'ajouter l'option et de 
	 * s'assurer que les num�ros d'options suivent. 
	 * 
	 */

	// Les options de menu (menu avec options de sauvegarde)
	public static String[] tabMenuAvecSauvegarde = {
			"Ajouter un-e ma�tre",
			"Ajouter un engagement",
			"activer/d�sactiver un engagement � un ma�tre",			
			"Ouvrir les fichiers texte",
			"Sauver les fichiers en texte",
			"Ouvrir le fichier binaire",
			"Sauver le fichier en binaire",
			"Pr�senter les assignations dans Excel",
			"Obtenir les engagements d'un maitre",
			"Obtenir les ma�tres d'un engagement"			
	};
	
   // Les options de menu (menu sans les options de sauvegarde)
	public static String[] tabMenuSansSauvegarde = {
			"Ajouter un-e ma�tre",
			"Ajouter un engagement",
			"Ouvrir les fichiers texte",
			"Ouvrir le fichier binaire",
	};

	// Les num�ros des options de menu
	public static final int AJOUTER_MAITRE = 0;
	public static final int AJOUTER_ENGAGEMENT = 1;
	public static final int ASSIGNER_ENGAGEMENT = 2;
	public static final int OUVRIR_FICHIER_TEXTE = 3;
	public static final int SAUVER_FICHIER_TEXTE = 4;
	public static final int OUVRIR_FICHIER_BINAIRE = 5;
	public static final int SAUVER_FICHIER_BINAIRE = 6;
	public static final int PRESENTER_ASSIGNATION = 7;
	public static final int OBTENIR_ENGAGEMENTS = 8;	
	public static final int OBTENIR_MAITRES = 9;
	
	//le caract�re de tabulation
	public static final String TAB = "\t";
}
