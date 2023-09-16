package org.example;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
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
            String menu = """
                    Elpriser
                    ========
                    1. Inmatning
                    2. Min, Max och Medel
                    3. Sortera
                    4. Bästa Laddningstid (4h)
                    e. Avsluta
                    """;
            System.out.print(menu + "\n");
            System.out.print("Välj ett alternativ: \n");
            String userInput = sc.next();
            Choice = userInput.charAt(0);

            switch (Choice) {
                case '1':
                    getUserInput(sc, electricPrices);
                    break;
                case '2':
                    CalculateMinMaxAverage(electricPrices);
                    break;
                case '3':
                    sortPricesHighToLow(electricPrices);
                    break;
                case '4':
                    cheapest4Hours(electricPrices);
                case 'e', 'E':
                    break;
                default:
                    System.out.print("Ogiltigt val. Försök igen \n");
                    break;
            }
        } while (Choice != 'e' && Choice != 'E');
    }

    private static void cheapest4Hours(double[] electricPrices) {
        if (electricPrices[0] == 0)
            System.out.println("Du måste först mata in elpriser");

        double minTotalPrice = Double.MAX_VALUE;
        int bestStartHour = 0;

        for (int i = 0; i <= electricPrices.length - 4; i++) {
            double totalPrice = 0;
            for (int j = i; j < i + 4; j++) {
                totalPrice += electricPrices[j];
            }
            if (totalPrice < minTotalPrice) {
                minTotalPrice = totalPrice;
                bestStartHour = i;
            }
        }
        DecimalFormat df = new DecimalFormat("#,##0.##");
        double averagePrice = minTotalPrice / 4.0;
        String startTime = (bestStartHour < 10) ? "0" + bestStartHour : String.valueOf(bestStartHour);
        System.out.print("Påbörja laddning klockan " + startTime + "\n");
        System.out.print("Medelpris 4h: " + df.format(averagePrice) + " öre/kWh\n");
    }

    private static void sortPricesHighToLow(double[] electricPrices) {
        if (electricPrices[0] == 0) {
            System.out.print("Du måste först mata in elpriser\n");
        } else {
            HourPricePair[] hourPricePairs = new HourPricePair[24];
            for (int i = 0; i < 24; i++) {
                hourPricePairs[i] = new HourPricePair(i, (int) electricPrices[i]);
            }
            Arrays.sort(hourPricePairs);
            System.out.print("Timmar sorterad efter pris (Dyrast till billigast)\n");
            for (HourPricePair pair : hourPricePairs) {
                System.out.print(pair + "\n");
            }
        }
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
        DecimalFormatSymbols customSymbols = new DecimalFormatSymbols(Locale.GERMAN);
        customSymbols.setDecimalSeparator(',');
        DecimalFormat df = new DecimalFormat("#,##0.##", customSymbols);
        DecimalFormat dF = new DecimalFormat("#,##0.00", customSymbols);
        minPrice = Math.round(minPrice * 100.0) / 100.0;
        maxPrice = Math.round(maxPrice * 100.0) / 100.0;
        averagePrice = Math.round(averagePrice * 100.0) / 100.0;

        if (minHour != -1 && maxHour != -1) {
            String minStartTime = (minHour < 10) ? "0" + minHour : String.valueOf(minHour);
            String minEndTime = ((minHour + 1) % 24 < 10) ? "0" + (minHour + 1) % 24 : String.valueOf((minHour + 1) % 24);
            String maxStartTime = (maxHour < 10) ? "0" + maxHour : String.valueOf(maxHour);
            String maxEndTime = ((maxHour + 1) % 24 < 10) ? "0" + (maxHour + 1) % 24 : String.valueOf((maxHour + 1) % 24);

            System.out.printf("Lägsta pris: " + minStartTime + "-" + minEndTime + ", " + df.format(minPrice) + " öre/kWh\n");
            System.out.printf("Högsta pris: " + maxStartTime + "-" + maxEndTime + ", " + df.format(maxPrice) + " öre/kWh\n");
        }
        System.out.printf("Medelpris: " + dF.format(averagePrice) + " öre/kWh\n");
    }

    private static void getUserInput(Scanner sc, double[] electricPrices) {
        for (int i = 0; i < 24; i++) {
            int startHour = i;
            int endHour = (i + 1) % 25;
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

    static class HourPricePair implements Comparable<HourPricePair> {
        private final int hour;
        private final int price;

        public HourPricePair(int hour, int price) {
            this.hour = hour;
            this.price = price;
        }

        public int compareTo(HourPricePair other) {
            return Double.compare(other.price, this.price);
        }

        public String toString() {
            String startTime = (hour < 10) ? "0" + hour : String.valueOf(hour);
            String endTime = (((hour + 1) % 25) < 10) ? "0" + ((hour + 1) % 24) : String.valueOf((hour + 1) % 25);
            return startTime + "-" + endTime + " " + price + " öre";
        }
    }
}