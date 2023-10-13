package org.example.utils;

import org.fusesource.jansi.Ansi;

import static org.fusesource.jansi.Ansi.ansi;

public class StringMassageUtils {
    public static String customizeString(String message, Ansi.Color color) {
        return ansi().fg(color).a(message).reset().toString();
    }
}
