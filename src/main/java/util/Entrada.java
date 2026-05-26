package util;

import java.util.Scanner;

public class Entrada {
    private static final Scanner sc = new Scanner(System.in);

    public static String lerString() {
        return sc.nextLine().trim();
    }

    public static int lerInt() {
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Valor inválido. Digite um número inteiro:");
            return lerInt();
        }
    }

    public static double lerDouble() {
        try {
            return Double.parseDouble(sc.nextLine().trim().replace(",", "."));
        } catch (NumberFormatException e) {
            System.out.println("Valor inválido. Digite um número decimal:");
            return lerDouble();
        }
    }

    public static java.time.LocalDate lerData(String label) {
        System.out.print(label + " (dd/MM/yyyy): ");
        String input = sc.nextLine().trim();
        try {
            java.time.format.DateTimeFormatter fmt = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return java.time.LocalDate.parse(input, fmt);
        } catch (Exception e) {
            System.out.println("Data inválida. Use o formato dd/MM/yyyy.");
            return lerData(label);
        }
    }
}
