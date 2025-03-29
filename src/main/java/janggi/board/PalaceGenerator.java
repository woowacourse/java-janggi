package janggi.board;

import janggi.position.Position;

public class PalaceGenerator {

    public Palace generate() {
        final Palace palace = new Palace();

        palace.addArea(new Position(0, 3));
        palace.addArea(new Position(0, 4));
        palace.addArea(new Position(0, 5));
        palace.addArea(new Position(1, 3));
        palace.addArea(new Position(1, 4));
        palace.addArea(new Position(1, 5));
        palace.addArea(new Position(2, 3));
        palace.addArea(new Position(2, 4));
        palace.addArea(new Position(2, 5));
        palace.addArea(new Position(7, 3));
        palace.addArea(new Position(7, 4));
        palace.addArea(new Position(7, 5));
        palace.addArea(new Position(8, 3));
        palace.addArea(new Position(8, 4));
        palace.addArea(new Position(8, 5));
        palace.addArea(new Position(9, 3));
        palace.addArea(new Position(9, 4));
        palace.addArea(new Position(9, 5));

        return palace;
    }
}
