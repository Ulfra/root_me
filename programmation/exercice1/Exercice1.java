/* Exercice 1.java
 * Pas de copyright
 */

package programmation.exercice1;

import java.net.*;
import java.io.*;
import java.util.*;

/**
 * Etudier les connection IRC en java, exercice de root me
 * @author Ulfra
 */
 public class Exercice1 {

     /**
      * Envoie un string au serveur IRC
      * @param bw BufferedWriter
      * @param msg Chaine de charactere a envoyé
      */
      static void envoieString(BufferedWriter bw, String str) {
          
          try {
              bw.write(str +"\r\n");
              bw.flush();
          } catch (Exception e) {
              System.out.println("Exception: "+e);
          }
      }
    
    /**
     * Etablie la connection avec un serveur IRC
     */
     public static void main(String[] args) {

         try {

            String hote    = "irc.root-me.org", // Adresse du serveur
                   pseudo  = "Ulfra",           // pseudo
                   canal   = "#bot-test-c",     // canal ou l'on souhaite comuniquer
                   message = "!ep1";            // message d'arrivée

            int port    = 6667;                 // port de connection

            /* Nouvelle connection au serveur */
            Socket socket = new Socket(hote,port);
            System.out.println("*** Connecter au serveur IRC.");

            /* Ouverture du canal d'envoie de mesage */
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
            System.out.println("*** Opened OutputStreamWriter.");

            /* Preparation du tempon pour ecrire le message */
            BufferedWriter bwriter = new BufferedWriter(outputStreamWriter);
            System.out.println("*** Opened BufferedWriter.");

            /* Envoie le message */
            envoieString(bwriter, message);

            envoieString(bwriter,"NICK "+pseudo);
            envoieString(bwriter,"JOIN "+canal);

            /* TODO Reception des nombre du bot de root me */

            /* Reçois le message */
            InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
            BufferedReader breader = new BufferedReader(inputStreamReader);
            String line = null;
            int tries = 1;
            while ((line = breader.readLine()) != null) {
                System.out.println(">>> "+line);
                int firstSpace = line.indexOf(" ");
                int secondSpace = line.indexOf(" ", firstSpace + 1);
                if (secondSpace >= 0) {
                    String code = line.substring(firstSpace+1, secondSpace);
                       if (code.equals("004")) {
                        break;
                     }
                }
             }
            /*************************************************************************/
              

        }catch (Exception e) {
            e.printStackTrace();
        }

     }

 }