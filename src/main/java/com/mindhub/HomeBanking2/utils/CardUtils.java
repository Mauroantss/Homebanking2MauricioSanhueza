package com.mindhub.HomeBanking2.utils;

import com.mindhub.HomeBanking2.repositories.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;

import static com.mindhub.HomeBanking2.utils.AccountUtils.getRandomNumber;

public final class CardUtils {
    // Método para generar un número de tarjeta de crédito aleatorio
    public static String generateRandomCardNumber() {
        StringBuilder cardNumber = new StringBuilder(); // Usamos StringBuilder para construir el número de tarjeta

        for (int i = 0; i < 16; i++) { // Generamos 16 dígitos para el número de tarjeta
            int digit = (int) (Math.random() * 10); // Generamos un dígito aleatorio entre 0 y 9
            cardNumber.append(digit); // Agregamos el dígito al número de tarjeta

            if ((i + 1) % 4 == 0 && i != 15) {
                cardNumber.append("-"); // Agregamos un guión después de cada 4 dígitos (excepto al final)
            }
        }

        return cardNumber.toString(); // Devolvemos el número de tarjeta como una cadena
    }

    // Método para generar un número de CVV (Código de Seguridad) aleatorio
    public static String generateRandomCvvNumber() {
        StringBuilder cardCvvNumber = new StringBuilder(); // Usamos StringBuilder para construir el CVV

        for (int i = 0; i < 3; i++) { // Generamos 3 dígitos para el CVV
            int digit = (int) (Math.random() * 10); // Generamos un dígito aleatorio entre 0 y 9
            cardCvvNumber.append(digit); // Agregamos el dígito al CVV
        }

        return cardCvvNumber.toString(); // Devolvemos el CVV como una cadena
    }
}

