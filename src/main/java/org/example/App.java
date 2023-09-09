package org.example;

import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Locale swedishLocale = new Locale("sv", "SE");
        Locale.setDefault(swedishLocale);
        Scanner sc = new Scanner(System.in);
        double[] electricPrices = new double[24];

        int Choice;
        do {
            String meny = """
                    Elpriser
                    ========
                    1. Inmatning
                    2. Min, Max och Medel
                    3. Sortera
                    4. Bästa Laddningstid (4h)
                    e. Avsluta
                    """;
            System.out.print(meny + "\n");
            //  System.out.print("Välj ett alternativ: \n");
            String userInput = sc.next();
            Choice = userInput.charAt(0);

            switch (Choice) {
                case '1':
                    getUserInput(sc, electricPrices);
                    break;
                case '2':
                    CalculateMinMaxAverage(electricPrices);
                    break;

                case 'e', 'E':
                    break;
                default:
                    System.out.print("Ogiltigt val. Försök igen \n");
                    break;
            }
        } while (Choice != 'e' && Choice != 'E');
    }

    private static void CalculateMinMaxAverage(double[] electricPrices) {
        double minPrice = Double.MAX_VALUE;
        double maxPrice = Double.MIN_VALUE;
        int minHour = -1;
        int maxHour = -1;

        for (int i = 0; i < electricPrices.length; i++) {
            if (electricPrices[i] < minPrice) {
                minPrice = electricPrices[i];
                minHour = i;
            }
            if (electricPrices[i] > maxPrice) {
                maxPrice = electricPrices[i];
                maxHour = i;
            }
        }

        double averagePrice = Arrays.stream(electricPrices).average().orElse(0);

        minPrice = Math.round(minPrice * 100.0) / 100.0;
        maxPrice = Math.round(maxPrice * 100.0) / 100.0;
        averagePrice = Math.round(averagePrice * 100.0) / 100.0;

        if (minHour != -1 && maxHour != -1) {
            String minStartTime = (minHour < 10) ? "0" + minHour : String.valueOf(minHour);
            String minEndTime = ((minHour + 1) % 24 < 10) ? "0" + (minHour + 1) % 24 : String.valueOf((minHour + 1) % 24);
            String maxStartTime = (maxHour < 10) ? "0" + maxHour : String.valueOf(maxHour);
            String maxEndTime = ((maxHour + 1) % 24 < 10) ? "0" + (maxHour + 1) % 24 : String.valueOf((maxHour + 1) % 24);

            System.out.print("Lägsta pris: " + minStartTime + "-" + minEndTime + ", " + minPrice + " öre/kwh\n");
            System.out.print("Högsta pris: " + maxStartTime + "-" + maxEndTime + ", " + maxPrice + " öre/kwh\n");
        }

        System.out.print("Medelpris: " + averagePrice + " öre/kWh\n");
    }

    private static void getUserInput(Scanner sc, double[] electricPrices) {
        for (int i = 0; i < 24; i++) {
            int startHour = i;
            int endHour = (i + 1) % 24;
            String startTime = (startHour < 10) ? "0" + startHour : String.valueOf(startHour);
            String endTime = (endHour < 10) ? "0" + endHour : String.valueOf(endHour);
            System.out.print("Ange elpriset för timme " + startTime + "-" + endTime + " ( kr per kw/h): \n");
            if (sc.hasNextDouble()) {
                electricPrices[i] = sc.nextDouble();
            } else {
                System.out.print("Ogiltig inmatning. Ange ett decimalvärde");
                sc.nextLine();
                i--;
            }
        }
    }
}