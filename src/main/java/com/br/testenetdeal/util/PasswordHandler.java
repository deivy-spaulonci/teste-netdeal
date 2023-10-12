package com.br.testenetdeal.util;

import java.util.function.Predicate;

public class PasswordHandler {

    public int getScore(String password) {
        return calculateAdditions(password) + calculateDeductions(password);
    }

    public boolean isValidPassword(String password){
        return password.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$");
    }

    private int calculateAdditions(String password) {
        int score = 0;
        int upperCaseLetters = 0;
        int lowerCaseLetters = 0;
        int numbers = 0;
        int symbols = 0;
        int middleNumberSymbols = 0;
        for (int i = 0; i < password.length(); i++) {
            Character character = password.charAt(i);
            switch (character) {
                case Character c when Character.isUpperCase(c) -> upperCaseLetters++;
                case Character c when Character.isLowerCase(c) -> lowerCaseLetters++;
                case Character c when Character.isDigit(c) -> numbers++;
                case Character c when !Character.isLetterOrDigit(c) -> symbols++;
                default -> {
                    break;
                }
            }
            if (i != 0 && i != password.length() - 1 && !Character.isLetter(character)) {
                middleNumberSymbols++;
            }
        }
        score += password.length() * 4;
        if (upperCaseLetters > 0) {
            score += (password.length() - upperCaseLetters) * 2;
        }
        if (lowerCaseLetters > 0) {
            score += (password.length() - lowerCaseLetters) * 2;
        }
        if (numbers < password.length()) {
            score += numbers * 4;
        }
        score += symbols * 6;
        score += middleNumberSymbols * 2;
        score += calculateRequirements(password, upperCaseLetters, lowerCaseLetters, numbers, symbols) * 2;
        return score;
    }

    private int calculateRequirements(String password, int upperCaseLetters, int lowerCaseLetters, int numbers, int symbols) {
        if (password.length() >= 8
                && upperCaseLetters > 0
                && lowerCaseLetters > 0
                && numbers > 0
                && symbols > 0) {
            return 5;
        }
        return 0;
    }

    private int calculateDeductions(String password) {
        int score = 0;
        if (isNumeric(password))
            score -= password.length();
        if (isLettersOnly(password))
            score -= password.length();
        score -= (repeatCharactersScore(password));
        score -= (consecutiveLength(password, Character::isUpperCase) * 2);
        score -= (consecutiveLength(password, Character::isLowerCase) * 2);
        score -= (consecutiveLength(password, Character::isDigit) * 2);
        score -= (sequenceLength(password, new StringBuilder("abcdefghijklmnopqrstuvwxyz")) * 3);
        score -= (sequenceLength(password, new StringBuilder("01234567890")) * 3);
        score -= (sequenceLength(password, new StringBuilder(")!@#$%^&*()")) * 3);

        return score;
    }

    public boolean isNumeric(String password) {
        return password.matches("\\d+");
    }

    public static boolean isLettersOnly(String password) {
        return password.matches("^[a-zA-Z]+$");
    }

    private int consecutiveLength(String password, Predicate<Character> condition) {
        int result = 0;
        int currentCount = 0;

        for (char character : password.toCharArray()) {
            if (condition.test(character)) {
                currentCount++;
            } else if (currentCount > 0){
                result += currentCount - 1;
                currentCount = 0;
            }
        }
        if (currentCount > 0){
            result += currentCount - 1;
        }
        return result;
    }

    private int sequenceLength(String password, StringBuilder charSet) {
        int result = 0;
        String lowerCasePassword = password.toLowerCase();
        for (int i = 0; i < charSet.length() - 2; i++) {
            String strForward = charSet.substring(i, i + 3);
            String strReverse = new StringBuilder(strForward).reverse().toString();

            if (lowerCasePassword.contains(strForward) || lowerCasePassword.contains(strReverse)) {
                result++;
            }
        }
        return result;
    }

    private int repeatCharactersScore(String password) {
        char[] passwordArray = password.toCharArray();
        double numberRepeated = 0.0;
        int numberRepetedChar = 0;

        for (int i = 0; i < passwordArray.length; i++) {
            boolean foundChar = false;

            for (int j = 0; j < passwordArray.length; j++) {
                if (passwordArray[i] == passwordArray[j] && i != j) {
                    foundChar = true;
                    numberRepeated += Math.abs(passwordArray.length / (j - i));
                }
            }
            if (foundChar) {
                numberRepetedChar++;
                int numberUniqueChar = passwordArray.length - numberRepetedChar;
                numberRepeated = (numberUniqueChar > 0) ? Math.ceil(numberRepeated / numberUniqueChar) : Math.ceil(numberRepeated);
            }
        }

        return Double.valueOf(numberRepeated).intValue();
    }
}
