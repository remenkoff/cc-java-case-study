package javacasestudy.tests;

import java.util.Random;

public class TestUtils {
  public static int randomInt(int bound) {
    return (new Random()).nextInt(bound);
  }
}
