package com.mindhub.HomeBanking2.utils;

public final class AccountUtils {
    // Genera un número aleatorio dentro del rango especificado.
    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    // Genera un número de cuenta aleatorio como una cadena de dígitos.
    public static String generateAccountNumber() {
        int accountNumber = getRandomNumber(1, 99999999);
        return String.valueOf(accountNumber);
    }
}

