/**
 * 
 */
package name.tower;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * This class generates a name tower. So, "First Middle Last" becomes:
 * 
 * <pre>
 *         F
 *       I R S
 *     T * M I D
 *   D L E * L A S
 * T * * * * * * * *
 * </pre>
 * 
 * Here are some considerations:
 * <ul>
 * <li>The first row number is one, not zero.</li>
 * <li>The number of characters in each row is: row * 2 - 1.</li>
 * <li>Spaces <em>within</em> the name are replaced by asterisks.</li>
 * <li>Names are converted to uppercase.</li>
 * <li>The last row is lengthened to the correct length by asterisks if
 * necessary.</li>
 * <li>Characters in each row are separated by spaces.</li>
 * <li>Characters in each row are centered in the row.</li>
 * </ul>
 * 
 * The above rules are repeatedly applied to each row. This kind of "assembly
 * line" approach lends itself to a solution using Java Streams. Therefore a
 * design goal is to use Streams as much as possible.
 * 
 * Note that there is no main method as the code execution is executed by a unit
 * test.
 * 
 * @author Promineo
 *
 */
public class NameTower {

  /**
   * This method generates the name tower from the given name as described in
   * the class introduction.
   * 
   * @param name The name from which to generate the tower.
   * @return The name tower as a String.
   */
  public String generateTower(String name) {
    Objects.requireNonNull(name, "Name must not be null!");

    List<String> rawRows = extractRawRows(name);
    List<String> enhanced = enhanceRawRows(rawRows);
    List<String> padded = centerCharactersInRows(enhanced);

    return convertToStringWithLinefeeds(padded);
  }

  /**
   * Convert the list to a single String with linefeed characters at the end of
   * each list element.
   * 
   * @param list The list to convert.
   * @return The list elements concatenated together separated by linefeed
   *         characters.
   */
  private String convertToStringWithLinefeeds(List<String> list) {
    return list.stream().collect(Collectors.joining("\n"));
  }

  /**
   * If the last row in the list is not the correct length, lengthen it by
   * adding asterisks.
   * 
   * @param rows The list to manage.
   */
  private void padLastRow(List<String> rows) {
    String lastRow = rows.get(rows.size() - 1);
    int padLen = rowLength(rows.size()) - lastRow.length();
    rows.set(rows.size() - 1, lastRow + "*".repeat(padLen));
  }

  /**
   * This method centers the characters in each row by adding spaces at the
   * start of each row as necessary. It turns this:
   * 
   * <pre>
   * F
   * I R S
   * T * M I D
   * D L E * L A S
   * T * * * * * * * *
   * </pre>
   * 
   * to this:
   * 
   * <pre>
   *         F
   *       I R S
   *     T * M I D
   *   D L E * L A S
   * T * * * * * * * *
   * </pre>
   * 
   * @param rows The list of rows.
   * @return The list with characters centered in each row.
   */
  private List<String> centerCharactersInRows(List<String> rows) {
    int maxLength = rowLength(rows.size());

    /*
     * The IntStream acts as a traditional for loop with a counter. It generates
     * integers ranging from 1 through the size of the list.
     */
    // @formatter:off
    return IntStream.range(1, rows.size() + 1)
        .mapToObj(rowNum -> " ".repeat(maxLength - rowLength(rowNum))
            + rows.get(rowNum - 1))
        .toList();
    // @formatter:on
  }

  /**
   * This method uses a Stream to enhance the raw rows. This performs the
   * following on each row:
   * <ol>
   * <li>The row text is converted to uppercase.</li>
   * <li>Any spaces are replaced with asterisks.</li>
   * <li>Spaces are added between the row characters.</li>
   * </ol>
   * Note the last map() method. This method call:
   * <ul>
   * <li>Converts the row String to a Stream of integers (IntStream). The
   * integers represent the ordinal value of each character in the row. The
   * integers can be cast to a char if necessary.</li>
   * <li>Each character in the Stream is converted a a String. So ['F', 'I',
   * 'R'] becomes ["F", "I", "R"].</li>
   * <li>The Stream is reassembled as a String with spaces between each
   * character.</li>
   * </ul>
   * 
   * @param rows
   * @return
   */
  private List<String> enhanceRawRows(List<String> rows) {
    // @formatter:off
    return rows.stream()                        // Stream of String
        .map(String::toUpperCase)               // Convert to uppercase
        .map(row -> row.replace(' ', '*'))      // Replace spaces with *
        .map(row -> row.chars()                 // Convert to IntStream (chars)
            .mapToObj(Character::toString)      // Convert to Stream of String (chars)
            .collect(Collectors.joining(" ")))  // Add space between each char
        .toList();                              // Return List of String (rows)
    // @formatter:on
  }

  /**
   * This method calculates the length of each row. Note that the first row
   * number is 1, not 0.
   * 
   * @param rowNum The row number.
   * @return The number of characters on each row. This is rowNum * 2 - 1.
   */
  private int rowLength(int rowNum) {
    return rowNum * 2 - 1;
  }

  /**
   * This method returns a raw name tower with the correct number of characters
   * on each row. Asterisks are added to the last row as needed to make the row
   * the correct length. So, for the name "First Middle Last" this is returned:
   * 
   * <pre>
   * F
   * irs
   * t mid
   * dle las
   * t********
   * </pre>
   * 
   * This uses a trick to get the compiler to allow the operation. It would be
   * more straightforward to remove characters from the start of the name as
   * each row is built. So, given the name "First Middle Last" rows would be
   * built as follows:
   * <table>
   * <tr>
   * <th>Row Num</th>
   * <th>Starting name String</th>
   * <th>Row</th>
   * <th>Ending Name String</th>
   * </tr>
   * <tr>
   * <td>1</td>
   * <td>First Middle Last</td>
   * <td>F</td>
   * <td>irst Middle Last</td>
   * </tr>
   * <tr>
   * <td>2</td>
   * <td>irst Middle Last</td>
   * <td>irs</td>
   * <td>t Middle Last</td>
   * </tr>
   * <tr>
   * <td>3</td>
   * <td>t Middle Last</td>
   * <td>t Mid</td>
   * <td>dle Last</td>
   * </tr>
   * <tr>
   * <td>4</td>
   * <td>dle Last</td>
   * <td>dle Las</td>
   * <td>t</td>
   * </tr>
   * <tr>
   * <td>5</td>
   * <td>t</td>
   * <td>t</td>
   * <td></td>
   * </tr>
   * </table>
   * 
   * The problem for us is that the compiler requires that the variable that
   * contains the name be final (or "effectively" final -- which means that the
   * compiler makes it final whether you use the "final" keyword or not). This
   * means that the variable reference cannot be reassigned. So, you can't do
   * this:
   * 
   * <pre>
   * name = name.substring(3);
   * </pre>
   * 
   * The trick is to turn the name String into a StringBuilder. This will allow
   * the content to change without changing the reference in the local variable.
   * So, to remove the first 3 characters from the StringBuilder, call:
   * 
   * <pre>
   * builder.delete(0, 3);
   * </pre>
   * 
   * What can I say? It's an ugly hack but it works. The reason it's a hack is
   * that Java's implementation of functional programming with streams allows
   * the stream to reference a variable outside the stream. This is not "pure"
   * functional programming -- but it <em>is</em> Java.
   * 
   * @param name The name as a String.
   * @return A list of raw rows as described above.
   */
  private List<String> extractRawRows(String name) {
    StringBuilder buffer = new StringBuilder(name);
    int numRows = (int)Math.ceil(Math.sqrt(name.length()));

    /*
     * The termination method (collect()) is passed Collectors.toCollection().
     * That method is passed a reference to the LinkedList constructor, thereby
     * creating a new (modifiable) LinkedList. If toList() or
     * collect(Collectors.toList()) is used for the termination method, an
     * unmodifiable list is returned. If an unmodifiable list is created,
     * padLastRow will throw an exception.
     */
    // @formatter:off
    List<String> rows = IntStream.range(1, numRows + 1)
        .mapToObj(rowNum -> extractRowChars(buffer, rowNum))
        .collect(Collectors.toCollection(LinkedList::new));
    // @formatter:on

    padLastRow(rows);

    return rows;
  }

  /**
   * Extract the characters from the StringBuilder and return them as a String.
   * 
   * @param buffer The StringBuilder containing the remaining name characters.
   * @param rowNum The 1-based row number. This is used to calculate the number
   *        of characters to be extracted from the StringBuilder to form the
   *        row.
   * @return The row characters.
   */
  private String extractRowChars(StringBuilder buffer, int rowNum) {
    /* Don't copy past the end of the StringBuilder. */
    int copyLen = Math.min(buffer.length(), rowLength(rowNum));
    String row = buffer.substring(0, copyLen);

    buffer.delete(0, copyLen);

    return row;
  }

}
