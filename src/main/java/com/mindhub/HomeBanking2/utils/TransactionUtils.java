package com.mindhub.HomeBanking2.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class TransactionUtils {
    // Método para obtener la fecha y hora actual formateada como "yyyy-MM-dd HH:mm:ss"
    public static LocalDateTime dateTime() {
        LocalDateTime now = LocalDateTime.now(); // Obtenemos la fecha y hora actual
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); // Creamos un formateador de fecha y hora
        String formattedDateTime = now.format(formatter); // Formateamos la fecha y hora actual como una cadena
        return LocalDateTime.parse(formattedDateTime, formatter); // Convertimos la cadena formateada de nuevo a LocalDateTime y la devolvemos
    }
}

