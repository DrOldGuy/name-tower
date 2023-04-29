package name.tower;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.Test;

class NameTowerTest {

  private NameTower nameTower = new NameTower();

  /**
   * Test that the name tower is created correctly. Note the use of a text block
   * for the variable expected. The text block removes leading spaces and
   * maintains linefeeds so that this:
   * 
   * <pre>
   * String expected = """
   *             F
   *           I R S
   *         T * M I D
   *       D L E * L A S
   *     T * * * * * * * *""";
   * </pre>
   * 
   * becomes this:
   * 
   * <pre>
   * String expected = "" + "        F\n" + "      I R S\n" + "    T * M I D\n"
   *     + "  D L E * L A S\n" + "T * * * * * * * *";
   * </pre>
   */
  @Test
  void testThatNameTowerIsCreated() {
    // Given: a name
    String name = "First Middle Last";

    String expected = """
                F
              I R S
            T * M I D
          D L E * L A S
        T * * * * * * * *""";

    // When: the tower is built
    String tower = nameTower.generateTower(name);

    // Then: the tower is correct
    assertThat(tower).isEqualTo(expected);
  }

  /**
   * 
   */
  @Test
  void testThatNullNameThrowsException() {
    // Given: a null name
    String name = null;

    // When: the tower is built
    // Then: an exception is thrown
    assertThatThrownBy(() -> nameTower.generateTower(name))
        .isInstanceOf(NullPointerException.class);
  }
}
