package piece;

import game.Board;
import java.util.List;
import position.Position;

public abstract class Piece {
    private final PieceType pieceType;
    private final Country country;


    protected Piece(final PieceType pieceType, final Country country) {
        this.pieceType = pieceType;
        this.country = country;
    }

    public void validateMove(final Position fromPosition, final Position toPosition, Board board) {
        List<Position> route = findPathForMove(fromPosition, toPosition);
        validateTarget(toPosition, board);
        validateTargetSpecialRule(toPosition, board);
        validatePath(route, board);
    }

    public abstract List<Position> findPathForMove(Position fromPosition, Position toPosition);

    public abstract void validatePath(List<Position> positions, Board board);

    public void validateTarget(Position toPosition, Board board) {
        if (board.hasPieceAt(toPosition)) {
            Country targetCountry = board.findCountryByPosition(toPosition);
            if (country == targetCountry) {
                throw new IllegalArgumentException("아군 기물이 위치해 있습니다. ");
            }
        }
    }

    public void validateTargetSpecialRule(Position toPosition, Board board) {}

    public PieceType getPieceType() {
        return pieceType;
    }

    public Country getCountry() {
        return country;
    }
}
