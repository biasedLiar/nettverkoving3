import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Locale;

public class SocketTjenerWeb {
    public static void main(String[] args) throws IOException {
        final int PORTNR = 1250;
        Socket forbindelse = null;

        ServerSocket tjener = new ServerSocket(PORTNR);
        System.out.println("Logg for tjenersiden. Nå venter vi...");


        while (true){
            try{
                forbindelse = tjener.accept();
            } catch (IOException e){
                e.printStackTrace();
            }
            /* Åpner strømmer for kommunikasjon med klientprogrammet */
            InputStreamReader leseforbindelse
                    = null;
            try {
                leseforbindelse = new InputStreamReader(forbindelse.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            BufferedReader leseren = new BufferedReader(leseforbindelse);
            PrintWriter skriveren = null;
            try {
                skriveren = new PrintWriter(forbindelse.getOutputStream(), true);
            } catch (IOException e) {
                e.printStackTrace();
            }

            ArrayList<String> messages = new ArrayList<>();
            String newLine = " ";
            while (!newLine.equals("")){
                newLine = leseren.readLine();
                messages.add(newLine);
            }
            String myString = "";
            myString += "HTTP/1.0 200 OK\n";
            myString += "Content-Type: text/html; charset=utf-8" + "\n\n";
            myString += "<HTML><BODY>" + "\n";
            myString += "<H1> Hilsen. Du har koblet deg opp til min enkle web-tjener </h1>" + "\n";
            myString += "<ul>" + "\n";
            for (String string :
                    messages) {
                myString += "<li>" + string + "</li>\n";
            }
            myString += "</ul>" + "\n";

            myString += "</BODY></HTML>" + "\n";

            /* Sender innledning til klienten */
            skriveren.println(myString);


            /* Lukker forbindelsen */
            try {
                leseren.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            skriveren.close();
            System.out.println("skriver lukket");
        }
    }
}
