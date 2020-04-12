
import java.util.List;
import java.util.LinkedList;
import java.math.BigInteger;
import java.util.Scanner;

public class Solution {

  private static LinkedList<BigInteger> storeSeries;

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    String integer_asString = scanner.next();
    scanner.close();

    boolean result = input_isAscendingConsecutive_positiveIntegers(integer_asString);
    if (result) {
      System.out.println(result);
      System.out.println(storeSeries);
    } else {
      System.out.println(result);
    }
  }

  /**
   * Checks whether the input integer consists entirely of ascending consecutive positive integers.
   *
   * @return 'true' if such series is found. Otherwsie 'false'.
   */
  public static boolean input_isAscendingConsecutive__positiveIntegers(String str) {

    int length = str.length();
    BigInteger num = new BigInteger(str);
    if (num.toString().length() == 1) {
      return false;
    }

    boolean hasSeries = false;
    int exponent = length / 2 + 2;

    while (hasSeries == false && exponent >= 1) {
      BigInteger modulo = BigInteger.valueOf((long) 10).pow(exponent);
      hasSeries = isSeries_of_ascendingConsecutive_positiveIntegers(num, modulo);
      exponent--;
    }

    return hasSeries;
  }

  /**
   * Checks, for the value of current modulo, whether the input integer consists entirely of
   * ascending consecutive positive integers.
   */
  private static boolean isSeries_of_ascendingConsecutive_positiveIntegers(BigInteger num, BigInteger modulo) {

    BigInteger larger = num.mod(modulo);
    BigInteger previousNum = num;
    num = num.divide(modulo);

    modulo = check_andIfNeedBe_changeModulo_forNumbersWithLessDigits(previousNum, modulo);

    storeSeries = new LinkedList<BigInteger>();
    storeSeries.addFirst(larger);

    while (num.divide(modulo).compareTo(BigInteger.ZERO) > 0) {

      BigInteger smaller = num.mod(modulo);
      previousNum = num;
      num = num.divide(modulo);
      modulo = check_andIfNeedBe_changeModulo_forNumbersWithLessDigits(previousNum, modulo);
      storeSeries.addFirst(smaller);

      if (previous_and_current_numbers_areConsecutive(smaller, larger) == false) {
        return false;
      }
      larger = smaller;
    }

    // Checks, for the value of current modulo, the last two integers in the current series.
    if (previous_and_current_numbers_areConsecutive(num, larger) == false) {
      return false;
    }
    storeSeries.addFirst(num);

    return true;
  }

  private static boolean previous_and_current_numbers_areConsecutive(BigInteger smaller, BigInteger larger) {
    return smaller.add(BigInteger.ONE).equals(larger);
  }

  /**
   * If the previous and current integers are transitional integers, i.e. the total digits that
   * consitute the number change, such as (99, 100), (999, 1000), etc. then adjust the value of
   * modulo for a transition from the higher to the lower value.
   */
  private static BigInteger check_andIfNeedBe_changeModulo_forNumbersWithLessDigits(BigInteger previousNum, BigInteger modulo) {
    if (BigInteger.valueOf((long) 10).multiply(previousNum.mod(modulo)).equals(modulo)) {
      modulo = modulo.divide(BigInteger.valueOf((long) 10));
    }
    return modulo;
  }
}
