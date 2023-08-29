package org.example;
import java.util.Objects;
import java.util.Scanner;
public class App {
    public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
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
    }
}