package cike.chatgpt.utils;


import java.util.Random;
import java.util.UUID;

public final class RandomStringUtil {

  private static final Random RANDOM = new Random();

  private static final String RANDOM_STR  = "0123456789QWERTYUIOPASDFGHJKLZXCVBNM";

  private RandomStringUtil() {
  }

  public static String randomFromNumberAndUpLetter(int count) {
    return random(count, RANDOM_STR);
  }

  public static String random(final int count) {
    return random(count, false, false);
  }

  public static String randomAscii(final int count) {
    return random(count, 32, 127, false, false);
  }

  public static String randomAscii(final int minLengthInclusive, final int maxLengthExclusive) {
    return randomAscii(RandomUtil.nextInt(minLengthInclusive, maxLengthExclusive));
  }

  public static String randomAlphabetic(final int count) {
    return random(count, true, false);
  }

  public static String randomAlphabetic(final int minLengthInclusive,
      final int maxLengthExclusive) {
    return randomAlphabetic(RandomUtil.nextInt(minLengthInclusive, maxLengthExclusive));
  }

  public static String randomAlphanumeric(final int count) {
    return random(count, true, true);
  }

  public static String randomAlphanumeric(final int minLengthInclusive,
      final int maxLengthExclusive) {
    return randomAlphanumeric(RandomUtil.nextInt(minLengthInclusive, maxLengthExclusive));
  }

  public static String randomGraph(final int count) {
    return random(count, 33, 126, false, false);
  }

  public static String randomGraph(final int minLengthInclusive, final int maxLengthExclusive) {
    return randomGraph(RandomUtil.nextInt(minLengthInclusive, maxLengthExclusive));
  }

  public static String randomNumeric(final int count) {
    return random(count, false, true);
  }

  public static String randomNumeric(final int minLengthInclusive, final int maxLengthExclusive) {
    return randomNumeric(RandomUtil.nextInt(minLengthInclusive, maxLengthExclusive));
  }

  public static String randomPrint(final int count) {
    return random(count, 32, 126, false, false);
  }

  public static String randomPrint(final int minLengthInclusive, final int maxLengthExclusive) {
    return randomPrint(RandomUtil.nextInt(minLengthInclusive, maxLengthExclusive));
  }

  public static String random(final int count, final boolean letters, final boolean numbers) {
    return random(count, 0, 0, letters, numbers);
  }

  public static String random(final int count, final int start, final int end,
      final boolean letters, final boolean numbers) {
    return random(count, start, end, letters, numbers, null, RANDOM);
  }

  public static String random(final int count, final int start, final int end,
      final boolean letters, final boolean numbers, final char... chars) {
    return random(count, start, end, letters, numbers, chars, RANDOM);
  }

  public static String random(int count, int start, int end, final boolean letters,
      final boolean numbers,
      final char[] chars, final Random random) {
    if (count == 0) {
      return "";
    } else if (count < 0) {
      throw new IllegalArgumentException(
          "Requested random string length " + count + " is less than 0.");
    }
    if (chars != null && chars.length == 0) {
      throw new IllegalArgumentException("The chars array must not be empty");
    }

    if (start == 0 && end == 0) {
      if (chars != null) {
        end = chars.length;
      } else {
        if (!letters && !numbers) {
          end = Character.MAX_CODE_POINT;
        } else {
          end = 'z' + 1;
          start = ' ';
        }
      }
    } else {
      if (end <= start) {
        throw new IllegalArgumentException(
            "Parameter end (" + end + ") must be greater than start (" + start + ")");
      }
    }

    final int zero_digit_ascii = 48;
    final int first_letter_ascii = 65;

    if (chars == null && (numbers && end <= zero_digit_ascii
        || letters && end <= first_letter_ascii)) {
      throw new IllegalArgumentException(
          "Parameter end (" + end + ") must be greater then (" + zero_digit_ascii
              + ") for generating digits " +
              "or greater then (" + first_letter_ascii + ") for generating letters.");
    }

    final StringBuilder builder = new StringBuilder(count);
    final int gap = end - start;

    while (count-- != 0) {
      int codePoint;
      if (chars == null) {
        codePoint = random.nextInt(gap) + start;

        switch (Character.getType(codePoint)) {
          case Character.UNASSIGNED:
          case Character.PRIVATE_USE:
          case Character.SURROGATE:
            count++;
            continue;
        }

      } else {
        codePoint = chars[random.nextInt(gap) + start];
      }

      final int numberOfChars = Character.charCount(codePoint);
      if (count == 0 && numberOfChars > 1) {
        count++;
        continue;
      }

      if (letters && Character.isLetter(codePoint)
          || numbers && Character.isDigit(codePoint)
          || !letters && !numbers) {
        builder.appendCodePoint(codePoint);

        if (numberOfChars == 2) {
          count--;
        }

      } else {
        count++;
      }
    }
    return builder.toString();
  }


  public static String random(final int count, final String chars) {
    if (chars == null) {
      return random(count, 0, 0, false, false, null, RANDOM);
    }
    return random(count, chars.toCharArray());
  }

  public static String random(final int count, final char... chars) {
    if (chars == null) {
      return random(count, 0, 0, false, false, null, RANDOM);
    }
    return random(count, 0, chars.length, false, false, chars, RANDOM);
  }

  public static String randomUUIDWithoutSeparator() {
    return UUID.randomUUID().toString().replace("-", "");
  }

  public static String randomUUID() {
    return UUID.randomUUID().toString();
  }
}
