import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String numbString1 = "";
        String numbString2 = "";
        String operator = "";
        boolean finished = false;

        while (!finished){
            System.out.println("Write in the first number: ");
            numbString1 = in.nextLine();
            System.out.println("Write in the second number: ");
            numbString2 = in.nextLine();
            System.out.println("Write in the operator: ");
            operator = in.nextLine();

            finished = true;
        }
    }
}
