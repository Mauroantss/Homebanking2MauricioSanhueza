package com.mindhub.HomeBanking2.utils;



// La clase CardUtils proporciona utilidades relacionadas con las tarjetas.
// Al ser una clase final con un constructor privado, no se puede instanciar ni heredar.

public final class CardUtils {

    // Constructor privado para evitar instanciación.
    private CardUtils() {
    }

    // Método estático que genera un número aleatorio dentro del rango especificado.
    public static int generateRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    // Método estático que genera un número de tarjeta compuesto por cuatro bloques de cuatro dígitos cada uno.
    public static String generateNumber() {
        String number = "";
        for (int i = 0; i < 3; i++) {
            number += generateRandomNumber(1000, 9999) + " ";
        }
        number += generateRandomNumber(1000, 9999);
        return number;
    }

    // Método estático que genera un número CVV de tres dígitos.
    public static int generateCvv() {
        return generateRandomNumber(100, 999);
    }
}


