package org.example.data;

import org.example.data.exception.UserDataException;
import org.example.enums.ArgumentToStatistics;
import org.example.enums.ArgumentsToSort;
import org.example.model.enums.CarBodyType;
import org.example.model.enums.Type;

import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public final class UserData {

    private final static Scanner sc = new Scanner(System.in);

    public static String getString(String message) {
        System.out.println(message);
        return sc.nextLine();
    }

    public static int getInt(String message) {
        System.out.println(message);
        var line = sc.nextLine();
        if (!line.matches("\\d+")) {
            throw new UserDataException("not an integer value");
        }
        return Integer.parseInt(line);
    }

    public static double getDouble(String message) {
        System.out.println(message);
        var line = sc.nextLine();
        if (!line.matches("\\d+")) {
            throw new UserDataException("not an double value");
        }
        return Double.parseDouble(line);
    }

    public static boolean getBoolean(String message) {
        System.out.println(message + " Press 'y' to say yes");
        var line = sc.nextLine();
        return line.equalsIgnoreCase("y");
    }

    public static ArgumentsToSort getArgumentToSort() {
        var counter = new AtomicInteger(0);
        Arrays
                .stream(ArgumentsToSort.values())
                .forEach(argumentToSort -> System.out.println(counter.incrementAndGet() + ". " + argumentToSort));
        int choice;
        do {
            choice = getInt("Choose sort item [1-" + ArgumentsToSort.values().length + "]:");
        } while (choice < 1 || choice > ArgumentsToSort.values().length);
        return ArgumentsToSort.values()[choice - 1];
    }

    public static CarBodyType getCarBodyType() {
        var counter = new AtomicInteger(1);
        Arrays
                .stream(CarBodyType.values())
                .forEach(carBodyType -> System.out.println(counter.incrementAndGet() + ". " + carBodyType));
        int choice;
        do {
            choice = getInt("Choose car body type [1-" + CarBodyType.values().length + "]:");
        } while (choice < 1 || choice > CarBodyType.values().length);
        return CarBodyType.values()[choice - 1];
    }

    public static Type getEngineType() {
        var counter = new AtomicInteger(1);
        Arrays
                .stream(Type.values())
                .forEach(type -> System.out.println(counter.incrementAndGet() + ". " + type));
        int choice;
        do {
            choice = getInt("Choose engine type [1-" + Type.values().length + "]:");
        } while (choice < 1 || choice > Type.values().length);
        return Type.values()[choice - 1];
    }

    public static ArgumentToStatistics getArgumentForStatistics() {
        var counter = new AtomicInteger(1);
        Arrays
                .stream(ArgumentToStatistics.values())
                .forEach(sortItem -> System.out.println(counter.incrementAndGet() + ". " + sortItem));
        int choice;
        do {
            choice = getInt("Choose statistics item [1-" + ArgumentToStatistics.values().length + "]:");
        } while (choice < 1 || choice > ArgumentToStatistics.values().length);
        return ArgumentToStatistics.values()[choice - 1];
    }

    public static void close() {
        if (sc != null) {
            sc.close();
        }
    }
}
