package com.bt;

import java.nio.charset.StandardCharsets;

public class Utils {

  public static String unescape_perl_string(String oldstr) {

    StringBuilder newstr = new StringBuilder(oldstr.length());

    boolean saw_backslash = false;

    for (int i = 0; i < oldstr.length(); i++) {
      int cp = oldstr.codePointAt(i);
      if (oldstr.codePointAt(i) > Character.MAX_VALUE) {
        i++;
      }

      if (!saw_backslash) {
        if (cp == '\\') {
          saw_backslash = true;
        } else {
          newstr.append(Character.toChars(cp));
        }
        continue; /* switch */
      }

      if (cp == '\\') {
        saw_backslash = false;
        newstr.append('\\');
        newstr.append('\\');
        continue; /* switch */
      }

      switch (cp) {

        case 'r':
          newstr.append('\r');
          break; /* switch */

        case 'n':
          newstr.append('\n');
          break; /* switch */

        case 'f':
          newstr.append('\f');
          break; /* switch */

        /* PASS a \b THROUGH!! */
        case 'b':
          newstr.append("\\b");
          break; /* switch */

        case 't':
          newstr.append('\t');
          break; /* switch */

        case 'a':
          newstr.append('\007');
          break; /* switch */

        case 'e':
          newstr.append('\033');
          break; /* switch */


        case 'c': {
          if (++i == oldstr.length()) {
            die("trailing \\c");
          }
          cp = oldstr.codePointAt(i);
          /*
           * don't need to grok surrogates, as next line blows them up
           */
          if (cp > 0x7f) {
            die("expected ASCII after \\c");
          }
          newstr.append(Character.toChars(cp ^ 64));
          break; /* switch */
        }

        case '8':
        case '9':
          die("illegal octal digit");

        case '1':
        case '2':
        case '3':
        case '4':
        case '5':
        case '6':
        case '7':
          --i;

        case '0': {
          if (i + 1 == oldstr.length()) {
            /* found \0 at end of string */
            newstr.append(Character.toChars(0));
            break; /* switch */
          }
          i++;
          int digits = 0;
          int j;
          for (j = 0; j <= 2; j++) {
            if (i + j == oldstr.length()) {
              break; /* for */
            }
            /* safe because will unread surrogate */
            int ch = oldstr.charAt(i + j);
            if (ch < '0' || ch > '7') {
              break; /* for */
            }
            digits++;
          }
          if (digits == 0) {
            --i;
            newstr.append('\0');
            break; /* switch */
          }
          int value = 0;
          try {
            value = Integer.parseInt(
                oldstr.substring(i, i + digits), 8);
          } catch (NumberFormatException nfe) {
            die("invalid octal value for \\0 escape");
          }
          newstr.append(Character.toChars(value));
          i += digits - 1;
          break; /* switch */
        } /* end case '0' */

        case 'x': {
          if (i + 2 > oldstr.length()) {
            die("string too short for \\x escape");
          }
          i++;
          boolean saw_brace = false;
          if (oldstr.charAt(i) == '{') {
            /* ^^^^^^ ok to ignore surrogates here */
            i++;
            saw_brace = true;
          }
          int j;
          for (j = 0; j < 8; j++) {

            if (!saw_brace && j == 2) {
              break;  /* for */
            }

            /*
             * ASCII test also catches surrogates
             */
            int ch = oldstr.charAt(i + j);
            if (ch > 127) {
              die("illegal non-ASCII hex digit in \\x escape");
            }

            if (saw_brace && ch == '}') {
              break; /* for */
            }

            if (!((ch >= '0' && ch <= '9')
                ||
                (ch >= 'a' && ch <= 'f')
                ||
                (ch >= 'A' && ch <= 'F')
            )
            ) {
              die(String.format(
                  "illegal hex digit #%d '%c' in \\x", ch, ch));
            }

          }
          if (j == 0) {
            die("empty braces in \\x{} escape");
          }
          int value = 0;
          try {
            value = Integer.parseInt(oldstr.substring(i, i + j), 16);
          } catch (NumberFormatException nfe) {
            die("invalid hex value for \\x escape");
          }
          newstr.append(Character.toChars(value));
          if (saw_brace) {
            j++;
          }
          i += j - 1;
          break; /* switch */
        }

        case 'u': {
          if (i + 4 > oldstr.length()) {
            die("string too short for \\u escape");
          }
          i++;
          int j;
          for (j = 0; j < 4; j++) {
            /* this also handles the surrogate issue */
            if (oldstr.charAt(i + j) > 127) {
              die("illegal non-ASCII hex digit in \\u escape");
            }
          }
          int value = 0;
          try {
            value = Integer.parseInt(oldstr.substring(i, i + j), 16);
          } catch (NumberFormatException nfe) {
            die("invalid hex value for \\u escape");
          }
          newstr.append(Character.toChars(value));
          i += j - 1;
          break; /* switch */
        }

        case 'U': {
          if (i + 8 > oldstr.length()) {
            die("string too short for \\U escape");
          }
          i++;
          int j;
          for (j = 0; j < 8; j++) {
            /* this also handles the surrogate issue */
            if (oldstr.charAt(i + j) > 127) {
              die("illegal non-ASCII hex digit in \\U escape");
            }
          }
          int value = 0;
          try {
            value = Integer.parseInt(oldstr.substring(i, i + j), 16);
          } catch (NumberFormatException nfe) {
            die("invalid hex value for \\U escape");
          }
          newstr.append(Character.toChars(value));
          i += j - 1;
          break; /* switch */
        }

        default:
          newstr.append('\\');
          newstr.append(Character.toChars(cp));
          /*
           * say(String.format(
           *       "DEFAULT unrecognized escape %c passed through",
           *       cp));
           */
          break; /* switch */

      }
      saw_backslash = false;
    }

    /* weird to leave one at the end */
    if (saw_backslash) {
      newstr.append('\\');
    }

    return newstr.toString();
  }

  public static String uniplus(String s) {
    if (s.length() == 0) {
      return "";
    }
    /* This is just the minimum; sb will grow as needed. */
    StringBuilder sb = new StringBuilder(2 + 3 * s.length());
    sb.append("U+");
    for (int i = 0; i < s.length(); i++) {
      sb.append(String.format("%X", s.codePointAt(i)));
      if (s.codePointAt(i) > Character.MAX_VALUE) {
        i++; /****WE HATES UTF-16! WE HATES IT FOREVERSES!!!****/
      }
      if (i + 1 < s.length()) {
        sb.append(".");
      }
    }
    return sb.toString();
  }

  private static void die(String foa) {
    throw new IllegalArgumentException(foa);
  }

  /**
   * Encode a String like äöü to \u00e4\u00f6\u00fc
   *
   */
  public static String native2ascii(String text) {
    if (text == null)
      return text;
    StringBuilder sb = new StringBuilder();
    for (char ch : text.toCharArray()) {
      sb.append(native2ascii(ch));
    }
    return sb.toString();
  }

  /**
   * Encode a Character like ä to \u00e4
   *
   */
  public static String native2ascii(char ch) {
    if (ch > '\u007f') {
      StringBuilder sb = new StringBuilder();
      // write \udddd
      sb.append("\\u");
      StringBuilder hex = new StringBuilder(Integer.toHexString(ch));
      hex.reverse();
      int length = 4 - hex.length();
      for (int j = 0; j < length; j++) {
        hex.append('0');
      }
      for (int j = 0; j < 4; j++) {
        sb.append(hex.charAt(3 - j));
      }
      return sb.toString();
    } else {
      return Character.toString(ch);
    }
  }

  public static String ascii2native(String s) {
    return new String(s.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
  }
}
