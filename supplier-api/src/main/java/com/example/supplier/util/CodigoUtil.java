package com.example.supplier.util;
import java.util.logging.Logger;

public class CodigoUtil {
    private static final Logger LOGGER = Logger.getLogger(CodigoUtil.class.getName());

    public static boolean isValidCNPJ(long cnpj) {
        String cnpjStr = String.format("%014d", cnpj);
        if (cnpjStr.length() != 14) {
            return false;
        }

        int[] weight1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] weight2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

        try {
            int sum = 0;
            for (int i = 0; i < 12; i++) {
                sum += Character.getNumericValue(cnpjStr.charAt(i)) * weight1[i];
            }

            int mod = sum % 11;
            char firstDigit = (mod < 2) ? '0' : (char) ((11 - mod) + '0');

            sum = 0;
            for (int i = 0; i < 13; i++) {
                sum += Character.getNumericValue(cnpjStr.charAt(i)) * weight2[i];
            }

            mod = sum % 11;
            char secondDigit = (mod < 2) ? '0' : (char) ((11 - mod) + '0');

            return cnpjStr.charAt(12) == firstDigit && cnpjStr.charAt(13) == secondDigit;
        } catch (Exception e) {
            return false;
        }
    }

    public static void main(String[] args) {
        long cnpj = 12345678000195L; // Example CNPJ
        LOGGER.info("CNPJ is valid: " + isValidCNPJ(cnpj));
    }
}