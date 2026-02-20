package com.mine.manager.util;

import java.math.BigDecimal;

public class NumberToLetters {

    private static final String[] UNIDADES = {"", "UN ", "DOS ", "TRES ", "CUATRO ", "CINCO ", "SEIS ", "SIETE ", "OCHO ", "NUEVE ", "DIEZ ", "ONCE ", "DOCE ", "TRECE ", "CATORCE ", "QUINCE ", "DIECISEIS ", "DIECISIETE ", "DIECIOCHO ", "DIECINUEVE ", "VEINTE "};
    private static final String[] DECENAS = {"VEINTI", "TREINTA ", "CUARENTA ", "CINCUENTA ", "SESENTA ", "SETENTA ", "OCHENTA ", "NOVENTA "};
    private static final String[] CENTENAS = {"", "CIENTO ", "DOSCIENTOS ", "TRESCIENTOS ", "CUATROCIENTOS ", "QUINIENTOS ", "SEISCIENTOS ", "SETECIENTOS ", "OCHOCIENTOS ", "NOVECIENTOS "};

    /**
     * Convierte la parte entera de un BigDecimal a texto en español.
     */
    public static String convertToLetters(BigDecimal amount) {
        if (amount == null) return "CERO";

        long value = amount.longValue();
        if (value == 0) return "CERO";
        if (value == 100) return "CIEN";

        return convertNumber(value).trim();
    }

    private static String convertNumber(long number) {
        if (number <= 20) {
            return UNIDADES[(int) number];
        }
        if (number < 100) {
            if (number % 10 == 0) {
                return DECENAS[(int) (number / 10) - 2];
            }
            if (number > 20 && number < 30) {
                return DECENAS[0] + UNIDADES[(int) (number % 10)];
            }
            return DECENAS[(int) (number / 10) - 2] + "Y " + UNIDADES[(int) (number % 10)];
        }
        if (number < 1000) {
            return CENTENAS[(int) (number / 100)] + convertNumber(number % 100);
        }
        if (number < 1000000) {
            if (number / 1000 == 1) {
                return "MIL " + convertNumber(number % 1000);
            }
            return convertNumber(number / 1000) + "MIL " + convertNumber(number % 1000);
        }
        if (number < 1000000000000L) {
            if (number / 1000000 == 1) {
                return "UN MILLON " + convertNumber(number % 1000000);
            }
            return convertNumber(number / 1000000) + "MILLONES " + convertNumber(number % 1000000);
        }
        return "";
    }
}
