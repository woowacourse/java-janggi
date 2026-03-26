package domain.place;

import domain.place.piece.Side;

public interface Place {
    boolean isEmpty();

    boolean isSameSide(Side side);

    boolean isCannon();

    String getFormat();

    Side getSide();
}
