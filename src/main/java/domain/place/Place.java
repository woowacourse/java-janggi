package domain.place;

import domain.place.piece.Side;

public interface Place {
    boolean isEmpty();

    boolean isSameSide(Side side);

    String getFormat();

    Side getSide();
}
