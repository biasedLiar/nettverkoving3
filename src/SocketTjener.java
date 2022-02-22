
import java.io.*;
import java.net.*;

class SocketTjener {
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
            new AddSubtract(forbindelse).start();
        }
    }
}


