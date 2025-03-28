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

    public void canMove(final Position fromPosition, final Position toPosition, Board board) {
        List<Position> route = getPathForMoving(fromPosition, toPosition);
        validateRoute(route, board);
        validateNormalTargetPosition(toPosition, board);
    }

    public abstract List<Position> getPathForMoving(Position fromPosition, Position toPosition);

    public abstract void validateRoute(List<Position> positions, Board board);


    public void validateNormalTargetPosition(Position toPosition, Board board) {
        if (board.getBoard().containsKey(toPosition)) {
            Piece toPiece = board.getBoard().get(toPosition);
            if (country == toPiece.getCountry()) {
                throw new IllegalArgumentException("아군 기물이 위치해 있습니다. ");
            }
        }
    }

    public void validateSpecialPieceTargetPosition(Position toPosition, Board board) {
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public Country getCountry() {
        return country;
    }
}
