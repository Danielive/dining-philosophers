/*
 * Developed by Daniel Chuev.
 * Last modified 24.12.18 1:37.
 * Copyright (c) 2018. All Right Reserved.
 */

import java.util.Scanner;
import java.util.function.Supplier;

/**
 * @author Daniel Chuev
 */
class Main {

    private static Supplier<Integer> scanText = () -> {
        System.out.print("Enter number of philosophers: ");
        return Integer.parseInt(new Scanner(System.in).nextLine());
    };

    public static void main(String args[]) throws InterruptedException {
        new Manager().execute(scanText.get());
    }
}