package org.example.task2;

import org.fusesource.jansi.Ansi;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static org.example.utils.StringMassageUtils.customizeString;

public class Application2 {
    private static final List<Integer> numberList = new ArrayList<>();
    private static final Object lock = new Object();
    
    public static void main(String[] args) {
        var writerThread = new Thread(Application2::writeRandomNumber);
        var sumThread = new Thread(Application2::printSum);
        var sqrtThread = new Thread(Application2::printSquareRoot);

        writerThread.start();
        sumThread.start();
        sqrtThread.start();
    }

    private static void writeRandomNumber() {
        Random random = ThreadLocalRandom.current();

        while (true) {
            synchronized (lock) {
                int randomNumber = random.nextInt(100);
                numberList.add(randomNumber);
                System.out.println(customizeString("Added: ", Ansi.Color.CYAN) + randomNumber);
            }
            sleepThread(1000);
        }
    }

    private static void printSum() {
        while (true) {
            synchronized (lock) {
                int sum = numberList.stream().mapToInt(Integer::intValue).sum();
                System.out.println(customizeString("Sum: ", Ansi.Color.MAGENTA) + sum);
            }
            sleepThread(2000);
        }
    }

    private static void printSquareRoot() {
        while (true) {
            synchronized (lock) {
                double sumOfSquares = numberList.stream().mapToDouble(num -> num * num).sum();
                double squareRoot = Math.sqrt(sumOfSquares);
                System.out.println(customizeString("Square Root of Sum of Squares: ", Ansi.Color.GREEN) + squareRoot);
            }

            sleepThread(3000);
        }
    }

    private static void sleepThread(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
