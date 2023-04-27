package cike.chatgpt.utils;


import java.util.Random;

public final class RandomUtil {

  private static final Random RANDOM = new Random();

  private RandomUtil() {
  }

  public static boolean nextBoolean() {
    return RANDOM.nextBoolean();
  }

  public static byte[] nextBytes(int count) {
    final byte[] result = new byte[count];
    RANDOM.nextBytes(result);
    return result;
  }

  public static int nextInt(int startInclusive, int endExclusive) {
    if (startInclusive == endExclusive) {
      return startInclusive;
    }

    return startInclusive + RANDOM.nextInt(endExclusive - startInclusive);
  }

  public static int nextInt() {
    return nextInt(0, Integer.MAX_VALUE);
  }

  public static long nextLong(final long startInclusive, final long endExclusive) {
    if (startInclusive == endExclusive) {
      return startInclusive;
    }

    return (long) nextDouble(startInclusive, endExclusive);
  }

  public static long nextLong() {
    return nextLong(0, Long.MAX_VALUE);
  }

  public static double nextDouble(final double startInclusive, final double endExclusive) {
    return startInclusive + ((endExclusive - startInclusive) * RANDOM.nextDouble());
  }

  public static double nextDouble() {
    return nextDouble(0, Double.MAX_VALUE);
  }

  public static float nextFloat(final float startInclusive, final float endExclusive) {
    return startInclusive + ((endExclusive - startInclusive) * RANDOM.nextFloat());
  }

  public static float nextFloat() {
    return nextFloat(0, Float.MAX_VALUE);
  }
}
