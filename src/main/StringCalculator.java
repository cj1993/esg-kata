package main;

import main.exception.NegativeNumberException;

import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class StringCalculator {
    public int add(String numbers) throws NegativeNumberException {

        if (numbers.isBlank()) return 0;

        checkForNegatives(numbers);

        String[] numbersList = parseNumbersInput(numbers);

        List<Integer> numbersListIntegers = new ArrayList<>();

        for (String number : numbersList) {
            if (Integer.parseInt(number) <= 1000) numbersListIntegers.add(Integer.valueOf(number));
        }

        return numbersListIntegers.stream().mapToInt(Integer::intValue).sum();
    }

    private String[] parseNumbersInput(String numbers) {
        String delimiter = ",";

        if (Pattern.compile("//.*\n").matcher(numbers).find()) {
            delimiter = getDelimiterValue(numbers);
            String replacementDelimiter = delimiter.length() > 1 ? "[" + delimiter + "]" : delimiter;
            numbers = numbers.replace("//" + replacementDelimiter + "\n", "");
        } else if (numbers.contains("\n")) {
            numbers = numbers.replace("\n", ",");
        }

        String numbersSeparated = numbers.replace(delimiter, ",");

        return numbersSeparated.split(",");
    }

    private void checkForNegatives(String numbers) throws NegativeNumberException {
        if (numbers.contains("-")) {
            String[] numbersList = numbers.split(",");
            StringBuilder negativeNumbers = new StringBuilder();

            for (String number : numbersList) {
                if (Integer.parseInt(number) < 0) negativeNumbers.append(number).append(",");
            }

            String formattedNegatives = negativeNumbers.substring(0, negativeNumbers.length() -1);

            throw new NegativeNumberException("Negatives not allowed: " + formattedNegatives);
        }
    }

    private String getDelimiterValue(String numbers) {
        if (numbers.contains("[") && numbers.contains("]")) {
            String[] numbersSplit = numbers.split("//\\[|]");
            Optional<String> delimiter = Arrays.stream(numbersSplit)
                    .filter(Predicate.not(String::isBlank))
                    .findFirst();
            return delimiter.get();
        } else {
            String[] numbersSplit = numbers.split("//");
            Optional<String> delimiter = Arrays.stream(numbersSplit)
                    .filter(Predicate.not(String::isBlank))
                    .findFirst();
            return delimiter.get().substring(0, 1);
        }
    }
}
