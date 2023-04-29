# Java project name-tower

This project creates a name tower from a name. So, for the name "First Middle Last", the application will create the tower:

```
        F
      I R S
    T * M I D
  D L E * L A S
T * * * * * * * *
```

The rules of a name tower are as follows:

 * The first row number is one, not zero.
 * The number of characters in each row is: row * 2 - 1.
 * Spaces  _within_  the name are replaced by asterisks.
 * Names are converted to uppercase.
 * The last row is lengthened to the correct length by asterisks if
 * necessary.
 * Characters in each row are separated by spaces.
 * Characters in each row are centered in the row.

The above rules are repeatedly applied to each row. This kind of "assembly line" approach lends itself to a solution using Java Streams. Therefore a design goal is to use Streams as much as possible.

 # Where to start
 
Start with the class name.tower.NameTower.java. It is well commented. Unit tests are in src/test/java name.tower.NameTowerTest.java.

Enjoy!