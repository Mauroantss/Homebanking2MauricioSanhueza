package com.mindhub.HomeBanking2.utils;

// La clase AccountUtils proporciona utilidades relacionadas con las cuentas.
// Al ser una clase final con un constructor privado, no se puede instanciar ni heredar.

public final class AccountUtils {

    // Constructor privado para evitar instanciación.
    private AccountUtils() {
    }

    // Método estático que genera un número de cuenta utilizando la combinación "VIN" y un número aleatorio generado por CardUtils.
    public static String generateNumber() {
        String number = "VIN";
        return number + CardUtils.generateRandomNumber(100, 99999999);
    }
}


