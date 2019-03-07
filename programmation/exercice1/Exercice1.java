/* Exercice 1.java
 * Pas de copyright
 */

package programmation.exercice1;

import java.net.*;
import java.io.*;
import java.util.*;
import java.math.*;

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

            int port = 6667;                 // port de connection
            double chiffre1 = 0,
                   chiffre2 = 0,
                   arrondi  = 0;
            int    aEnvoyer = 0;

            String aTraiter = null;                    //messagea traité

            /* Nouvelle connection au serveur */
            Socket socket = new Socket(hote,port);
            System.out.println("*** Connecter au serveur IRC.");

            /* Ouverture du canal d'envoie de mesage */
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
            System.out.println("*** Opened OutputStreamWriter.");

            /* Preparation du tempon pour ecrire le message */
            BufferedWriter bwriter = new BufferedWriter(outputStreamWriter);
            System.out.println("*** Opened BufferedWriter.");

            /* S'enregistre */
            envoieString(bwriter,"NICK "+pseudo);
            envoieString(bwriter,"USER "+pseudo+ ' '+hote+ " candy JC");
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
            
            /*Envoie le message au bot pour commancer l'exercice */
            envoieString(bwriter, "PRIVMSG candy !ep1");

            /* Reçois le message */
            line = null;
            tries = 1;
            while ((line = breader.readLine()) != null) {
                System.out.println(">>> "+line);
                int firstSpace = line.indexOf(" ");
                int secondSpace = line.indexOf(" ", firstSpace + 1);
                
                /* Reception des nombre */
                if (line.charAt(1) == 'C') {
                    aTraiter = line;
                    break;
                }
                    
               /* if (secondSpace >= 0) {
                    String code = line.substring(firstSpace+1, secondSpace);
                       if (code.equals("004")) {
                        break;
                }
                } */
             }
            /*************************************************************************/

            /* Conversion du premier nombre */
            System.out.println(aTraiter);
            chiffre1 = (aTraiter.charAt(40)-'0')*100 + (aTraiter.charAt(41)-'0')*10 + aTraiter.charAt(42)-'0';
            System.out.println(chiffre1);
            
            /*Conversion du chiffre 2 */
            chiffre2 = (aTraiter.charAt(46)-'0')*1000 + (aTraiter.charAt(47)-'0')*100 + (aTraiter.charAt(48)-'0')*10  + aTraiter.charAt(49)-'0';
            System.out.println(chiffre2);
            
            /* Calcul et renvoi */
            chiffre1 = Math.sqrt(chiffre1);
            arrondi=chiffre1*chiffre2;
            
            /* Arrondi */
            System.out.println(arrondi);
            arrondi=(double)Math.round(arrondi * 100) / 100;
            System.out.println(arrondi);

            envoieString(bwriter, "PRIVMSG candy !ep1 -rep " +arrondi);
            
            /* Reçois le message */
            line = null;
            tries = 1;
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
            

        }catch (Exception e) {
            e.printStackTrace();
        }

     }

 }
