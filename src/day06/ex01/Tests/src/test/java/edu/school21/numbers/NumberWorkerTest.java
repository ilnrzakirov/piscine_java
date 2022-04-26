package edu.school21.numbers;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class NumberWorkerTest {
    private NumberWorker numberWorker = new NumberWorker();

    @ParameterizedTest
    @ValueSource (ints = {2, 3, 113})
    void isPrimeForPrimes(int number) {
        assertTrue(numberWorker.isPrime(number));
    }

    @ParameterizedTest
    @ValueSource (ints = {22, 44, 169})
    void isPrimeForNotPrimes(int number) {
        assertFalse(numberWorker.isPrime(number));
    }

    @ParameterizedTest
    @ValueSource (ints = {-1, 0, -169})
    void isPrimeForIncorrectNumbers(int number) {
        assertThrows(IllegalNumberException.class, () -> {
            numberWorker.isPrime(number);
        }, "Not thrown exception");
    }

    @ParameterizedTest
    @CsvFileSource (resources = "/data.csv")
    void isDigitsSumCorrect(int number, int sum) {
        assertEquals(numberWorker.digitsSum(number), sum);
    }

    @ParameterizedTest
    @CsvFileSource (resources = "/data1.csv")
    void isDigitsSumIncorrect(int number, int sum) {
        assertNotEquals(numberWorker.digitsSum(number), sum);
    }
}