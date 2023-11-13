package com.mindhub.HomeBanking2.utils;

import com.mindhub.HomeBanking2.repositories.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;

import static com.mindhub.HomeBanking2.utils.AccountUtils.getRandomNumber;

public  class CardUtils {
    @Autowired
    static CardRepository cardRepository;
    public static String generateCvvCard() {
        StringBuilder cvvNumber = new StringBuilder(); // Iniciar el StringBuilder para almacenar el número CVV

        // Generar 3 dígitos para el número CVV
        for (byte i = 0; i <= 2; i++) {
            cvvNumber.append(getRandomNumber(0, 9)); // Añadir un dígito aleatorio al número CVV
        }

        return cvvNumber.toString(); // Devolver el número CVV como una cadena
    }
    // Método privado para generar un número de tarjeta único
    public static String generateNumberCard() {
        StringBuilder cardNumber = new StringBuilder(); // Iniciar el StringBuilder para almacenar el número de tarjeta

        do {
            cardNumber.setLength(0); // Limpiar el contenido del StringBuilder
            // Generar 16 dígitos para el número de tarjeta
            for (int i = 0; i < 16; i++) {
                cardNumber.append(getRandomNumber(0, 9)); // Añadir un dígito aleatorio al número de tarjeta
                // Insertar un guion después de cada conjunto de 4 dígitos, excepto al final
                if ((i + 1) % 4 == 0 && i != 15) cardNumber.append("-");
            }
            // Repetir si el número de tarjeta ya existe en el repositorio
        } while (cardRepository.existsByNumber(cardNumber.toString()));

        return cardNumber.toString(); // Devolver el número de tarjeta como una cadena
    }
}
