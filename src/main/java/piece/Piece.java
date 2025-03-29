package piece;

import board.Board;
import position.Position;

public abstract class Piece {

    protected Position position;
    protected final Country country;

    public Piece(final Position position, final Country country) {
        this.position = position;
        this.country = country;
    }

    public void validateMove(final Position src, final Position dest, final Board board) {
        if (board.existPieceByPosition(dest) && board.equalsTeamTypeByPosition(dest, country)) {
            throw new IllegalArgumentException("잘못된 입력입니다.");
        }
        validateMoveCondition(src, dest, board);
    }

    public abstract void validateMoveCondition(final Position src, final Position dest, final Board board);

    public abstract boolean equalsType(final Piece piece);

    public Country getTeamType() {
        return country;
    }

    public boolean equalsCountry(Country country) {
        return this.country.equals(country);
    }
}
