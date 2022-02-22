import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Locale;

public class AddSubtract extends Thread {
    String numbString1;
    String numbString2;
    String operator;
    Socket forbindelse;

    public AddSubtract(Socket forbindelse){
        this.forbindelse = forbindelse;
    }

    private double addSubtract(double numb1, double numb2, String operator){
        if (operator.equals("+")){
            return numb1 + numb2;
        } else{
            return numb1 - numb2;
        }
    }

    public void run(){
        boolean cont = true;
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

        /* Sender innledning til klienten */
        skriveren.println("Hei, du har kontakt med tjenersiden!");
        skriveren.println("Svar på responsene for å løse regnestykke, avslutt med linjeskift.");

        while (cont){
            try {

                skriveren.println("Velg tall 1");  // sender svar til klienten
                numbString1 = leseren.readLine();

                System.out.println("En klient skrev: " + numbString1);
                skriveren.println("Velg tall 2");  // sender svar til klienten
                numbString2 = leseren.readLine();

                System.out.println("En klient skrev: " + numbString2);
                skriveren.println("Velg operator (+/-)");  // sender svar til klienten
                operator = leseren.readLine();

                System.out.println("En klient skrev: " + operator);
                double numb1 = Double.parseDouble(numbString1);
                double numb2 = Double.parseDouble(numbString2);
                if (!operator.equals("+") && !operator.equals("-")){
                    throw new IllegalArgumentException("Operator is not \"+\" og \"-\"");
                }
                double ans = addSubtract(numb1, numb2, operator);
                System.out.println("Svaret blir: " + ans + ". Spør om klienten vil prøve på nytt");

                System.out.println("En klient skrev: " + numbString2);
                skriveren.println("Svaret blir: " + ans + ". Vil du prøve på nytt? (y/n)");  // sender svar til klienten
                if(!leseren.readLine().substring(0, 1).toLowerCase(Locale.ROOT).equals("y")){
                    cont = false;
                    System.out.println("Klienten sa nei. Avslutter sesjon");
                }
            } catch (IOException e){
                System.out.println("IOException: " + e);
                skriveren.println("IOException: " + e + ", press enter to exit");
                cont = false;
            }catch (IllegalArgumentException e){
                System.out.println("IllegalArgumentexception: " + e);
                skriveren.println("IllegalArgumentexception: " + e + ", press enter to exit");
                cont = false;
            }
        }

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
