package test;

import static org.junit.jupiter.api.Assertions.*;

import main.StringCalculator;
import main.exception.NegativeNumberException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StringCalculatorTest {

    private StringCalculator stringCalculator;

    @BeforeEach
    void setUp() { stringCalculator = new StringCalculator(); }

    @Test
    void addMethodReturnsZeroForEmptyString() throws NegativeNumberException {
        assertEquals(0, stringCalculator.add("0"));
    }

    @Test
    void addMethodReturnsOneForNumberOneString() throws NegativeNumberException {
        assertEquals(1, stringCalculator.add("1"));
    }

    @Test
    void addMethodReturnsTwoForNumberTwoString() throws NegativeNumberException {
        assertEquals(2, stringCalculator.add("2"));
    }

    @Test
    void addMethodReturnsThreeForNumberOneAndTwoString() throws NegativeNumberException {
        assertEquals(3, stringCalculator.add("1,2"));
    }

    @Test
    void addMethodReturnsCorrectSumForManyNumbersInString() throws NegativeNumberException {
        assertEquals(225, stringCalculator.add(
                "1,2,3,4,5,6,7,8,9," +
                "1,2,3,4,5,6,7,8,9," +
                "1,2,3,4,5,6,7,8,9," +
                "1,2,3,4,5,6,7,8,9," +
                "1,2,3,4,5,6,7,8,9,")
        );
    }

    @Test
    void addMethodReturnsCorrectlyWhenNewLineIsWithinString() throws NegativeNumberException {
        assertEquals(6, stringCalculator.add("1\n2,3"));
    }

    @Test
    void addMethodReturnsCorrectlyWhenNewDelimiterIsWithinString() throws NegativeNumberException {
        assertEquals(3, stringCalculator.add("//;\n1;2"));
    }

    @Test
    void addMethodThrowsNegativeNumberExceptionWithMessageWhenNegativeNumbersIsWithinString() {
        NegativeNumberException negativeNumberException = assertThrows(
                NegativeNumberException.class,
                () -> stringCalculator.add("-1,2")
        );

        assertTrue(negativeNumberException.getMessage().contains("Negatives not allowed: -1"));
    }

    @Test
    void addMethodThrowsNegativeNumberExceptionWithMessageWhenNegativeNumbersLongerListIsWithinString() {
        NegativeNumberException negativeNumberException = assertThrows(
                NegativeNumberException.class,
                () -> stringCalculator.add("2,-4,3,-5")
        );

        assertTrue(negativeNumberException.getMessage().contains("Negatives not allowed: -4,-5"));
    }

    @Test
    void addMethodReturnsCorrectlyWhenNumbersOver1000IsWithinString() throws NegativeNumberException {
        assertEquals(2, stringCalculator.add("1001,2"));
    }

    @Test
    void addMethodReturnsCorrectlyWhenNewDelimiterOfAnyLengthIsWithinString() throws NegativeNumberException {
        assertEquals(6, stringCalculator.add("//[|||]\n1|||2|||3"));
    }
}