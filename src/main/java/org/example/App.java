package org.example;
import java.util.Scanner;
public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[] inputs = new int[24];
        String meny = """
                Elpriser
                ==============
                1. Inmatning
                2. Min, Max och Medel
                3. Sortera
                4. Bästa Laddningstid (4h)
                5. Visualisering
                e. Avsluta
                """;
        System.out.println(meny);
        int option1 = sc.nextInt();
        int option2 = sc.nextInt();
        if (option1 == 1) {
            System.out.println("Skriv in elpriser under det senaste dygnet");
            for (int i = 0; i < 24; i++)
                inputs[i] = sc.nextInt();
        }
        else if (option2==2)



    }
}