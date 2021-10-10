package com.example;

import java.io.*;
import java.net.*;


public class Client {

    String nomeServer = "localhost"; //indirizzo server locale
    int portaServer = 6789;          //numero della porta
    Socket miosoket;
    BufferedReader tastiera;         //buffer per input da tastiera
    String stringaUtente;            //stringa inserita da utente
    String stringaRicevutaDalServer; //stringa ricevuta dal server
    DataOutputStream outVersoServer; //stream di output
    BufferedReader inDalServer;      //stream di input

    public Socket connetti(){

        System.out.println("2 CLIENT partito in esecuzione ...");

        try {
            
            tastiera = new BufferedReader(new InputStreamReader(System.in)); //input da tastiera

            miosoket = new Socket(nomeServer, portaServer); //creo un socket

            outVersoServer = new DataOutputStream(miosoket.getOutputStream());
            inDalServer = new BufferedReader(new InputStreamReader(miosoket.getInputStream()));

        } catch (UnknownHostException e) {
            System.err.println("Errore host sconosciuto");
        }
        catch(Exception e){

            System.out.println(e.getMessage());
            System.out.println("Errore durante la trasmissione");
            System.exit(1);
        }

        return miosoket;
    }

    public void comunica(){

        for(;;){

            try {

                System.out.println("4 ... inserisci la stringa da trasmettere al server:" +'\n');
                stringaUtente = tastiera.readLine();

                System.out.println("5 .. invio la stringa al server e attendo ...");
                outVersoServer.writeBytes(stringaUtente+'\n');

                stringaRicevutaDalServer = inDalServer.readLine();
                System.out.println("7 ... risposta dal server " +'\n' +stringaRicevutaDalServer);

                if(stringaUtente.equals("FINE")){
                    
                    System.out.println("8 CLIENT: termina elaborazione e chiude connessione");
                    miosoket.close();
                    break;
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Errore durante la comunicazione col server");
                System.exit(1);
            }  
        }       

    }

    public static void main(String[] args) {
        
        Client cliente = new Client();
        cliente.connetti();
        cliente.comunica();
    }
    
}
