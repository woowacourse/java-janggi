package board;

import static pieces.PieceType.MA;
import static pieces.PieceType.SANG;

import pieces.Piece;
import pieces.PieceType;
import pieces.Side;

public enum SangSetupType {

    LEFT_SANG_SETUP(SANG, MA, SANG, MA),
    RIGHT_SANG_SETUP(MA, SANG, MA, SANG),
    INNER_SANG_SETUP(MA, SANG, SANG, MA),
    OUTER_SANG_SETUP(SANG, MA, MA, SANG),
    ;

    private final PieceType first;
    private final PieceType second;
    private final PieceType third;
    private final PieceType fourth;

    SangSetupType(PieceType first, PieceType second, PieceType third, PieceType fourth) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.fourth = fourth;
    }

    public Piece createFirst(final Side side) {
        return first.create(side);
    }

    public Piece createSecond(final Side side) {
        return second.create(side);
    }

    public Piece createThird(final Side side) {
        return third.create(side);
    }

    public Piece createFourth(final Side side) {
        return fourth.create(side);
    }
}
