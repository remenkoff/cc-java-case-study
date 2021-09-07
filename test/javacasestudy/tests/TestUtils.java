package javacasestudy.tests;

import java.util.Random;

public class TestUtils {
  public static int randomIntFromZeroTo(int bound) {
    return (new Random()).nextInt(bound);
  }

  public static String randomStringLowercased() {
    int aLetter = 97;
    int bLetter = 122;
    return (new Random()).ints(aLetter, bLetter + 1)
      .limit(32)
      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
      .toString();
  }
}
