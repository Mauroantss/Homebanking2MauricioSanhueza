package com.mindhub.HomeBanking2.utils;

public class AccountUtils {
    // Definición de un método para obtener un número aleatorio en un rango
    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
    public static String generateAccountNumber(){
        int accountNumber = getRandomNumber(1, 99999999);
        return String.valueOf(accountNumber);
    }
}
