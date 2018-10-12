import java.io.Serializable;

/**
 * Enregistrement qui regroupe les données d'un-e maître dans le contexte du
 * tp1A18INF111 (voir énoncé).
 *
 * @author Pierre Bélisle
 * @revisor Mélanie Lord
 * @version A20198
 */
public class Maitre implements Serializable {

   // Un code pour représenter un maître
   int code;

   // nom du local Exemple : B2524
   String local;

   // Extension téléphonique
   int numero;
}