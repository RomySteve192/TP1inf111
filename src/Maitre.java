import java.io.Serializable;

/**
 * Enregistrement qui regroupe les donn�es d'un-e ma�tre dans le contexte du
 * tp1A18INF111 (voir �nonc�).
 *
 * @author Pierre B�lisle
 * @revisor M�lanie Lord
 * @version A20198
 */
public class Maitre implements Serializable {

   // Un code pour repr�senter un ma�tre
   int code;

   // nom du local Exemple : B2524
   String local;

   // Extension t�l�phonique
   int numero;
}