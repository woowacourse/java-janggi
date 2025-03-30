package piece;

import game.Board;
import position.Path;
import position.Position;

public abstract class Piece {
    private final PieceType pieceType;
    private final Country country;

    protected Piece(final PieceType pieceType, final Country country) {
        this.pieceType = pieceType;
        this.country = country;
    }

    public void validateMove(final Position fromPosition, final Position toPosition, Board board) {
        Path path = findPathForMove(fromPosition, toPosition);
        validateTarget(toPosition, board);
        validateTargetSpecialRule(toPosition, board);
        validatePath(path, board);
    }

    public abstract Path findPathForMove(Position fromPosition, Position toPosition);

    public abstract void validatePath(Path path, Board board);

    public void validateTarget(Position toPosition, Board board) {
        board.findCountryByPosition(toPosition).ifPresent(targetCountry -> {
            if (country == targetCountry) {
                throw new IllegalArgumentException("아군 기물이 위치해 있습니다.");
            }
        });
    }

    public void validateTargetSpecialRule(Position toPosition, Board board) {}

    public PieceType getPieceType() {
        return pieceType;
    }

    public Country getCountry() {
        return country;
    }
}
