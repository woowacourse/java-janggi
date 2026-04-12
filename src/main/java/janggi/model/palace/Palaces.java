package janggi.model.palace;

import janggi.model.position.absolute.Position;

public class Palaces {

    private final Palace choPalace;
    private final Palace hanPalace;

    public Palaces(Palace choPalace, Palace hanPalace) {
        this.choPalace = choPalace;
        this.hanPalace = hanPalace;
    }

    public boolean areInSamePalace(Position from, Position to) {
        return samePalace(choPalace, from, to)
                || samePalace(hanPalace, from, to);
    }

    public boolean isAdjacentInSamePalace(Position from, Position to) {
        return adjacentIn(choPalace, from, to)
                || adjacentIn(hanPalace, from, to);
    }

    private boolean adjacentIn(
            Palace palace,
            Position from,
            Position to
    ) {
        return samePalace(palace, from, to)
                && palace.isAdjacent(from, to);
    }

    private boolean samePalace(
            Palace palace,
            Position from,
            Position to
    ) {
        return palace.contains(from) && palace.contains(to);
    }
}
