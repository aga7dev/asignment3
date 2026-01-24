package utils;

import exception.InvalidInputException;

import java.time.LocalDate;
import java.util.Scanner;

public class InputHelper {
    private final Scanner sc;

    public InputHelper(Scanner sc) {
        this.sc = sc;
    }

    public int readInt(String label) {
        System.out.print(label);
        String s = sc.nextLine();
        try {
            return Integer.parseInt(s.trim());
        } catch (Exception e) {
            throw new InvalidInputException("Invalid number: " + s);
        }
    }

    public double readDouble(String label) {
        System.out.print(label);
        String s = sc.nextLine();
        try {
            return Double.parseDouble(s.trim());
        } catch (Exception e) {
            throw new InvalidInputException("Invalid decimal number: " + s);
        }
    }

    public String readString(String label) {
        System.out.print(label);
        return sc.nextLine();
    }

    public LocalDate readDate(String label) {
        System.out.print(label + " (yyyy-mm-dd): ");
        String s = sc.nextLine();
        try {
            return LocalDate.parse(s.trim());
        } catch (Exception e) {
            throw new InvalidInputException("Invalid date: " + s + ". Expected yyyy-mm-dd");
        }
    }
}
