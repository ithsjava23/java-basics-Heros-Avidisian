package org.example;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;
public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[] electricPrices = new int[24];
        boolean running = true;

        while (running) {
            String meny = """
                    Elpriser
                    =======
                    1. Inmatning
                    2. Min, Max och Medel
                    3. Sortera
                    4. Bästa Laddningstid (4h)
                    e. Avsluta
                    """;
            System.out.println(meny);
            System.out.println("Välj ett alternativ: ");
            String Choice = sc.next();

            switch (Choice) {
                case "1":
                    for (int i = 0; i < 24; i++) {
                        System.out.println("Ange elpriset för timme " + i + "-" + (i + 1) + " ( kr per kw/h): ");
                        try {
                            electricPrices[i] = sc.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("ogiltig inmatning. ange ett heltal.");
                            sc.next();
                            i--;
                        }
                    }
                    break;
                case "2":
                    int minPris = Arrays.stream(electricPrices).min().orElse(0);
                    int maxPris = Arrays.stream(electricPrices).max().orElse(0);
                    double medelPris = Arrays.stream(electricPrices).average().orElse(0);
                    System.out.println("Minsta pris: " + minPris + " kr per kW/h");
                    System.out.println("Största pris: " + maxPris + " kr per kW/h");
                    System.out.println("Medelpris: " + medelPris + " kr per kW/h");
                    break;
            }
        }
    }
}