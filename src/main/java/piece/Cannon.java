package piece;

import game.Board;
import position.Path;
import position.Position;

public class Cannon extends Piece {
    public Cannon(final Country country) {
        super(PieceType.CANNON, country);
    }

    @Override
    public Path findPathForMove(Position fromPosition, Position toPosition) {
        if (fromPosition.isStraight(toPosition)) {
            return fromPosition.findStraightPath(toPosition);
        }
        if (fromPosition.onPalace()&&toPosition.onPalace()) {
            if (fromPosition.isDiagonal(toPosition)) {
                return fromPosition.findPalaceDiagonalPath(toPosition);
            }
        }

        throw new IllegalArgumentException("해당 위치로 이동할 수 없습니다.");
    }

    @Override
    public void validatePath(final Path path, Board board) {
        int pieceCount = 0;
        for (Position position : path.positions()) {
            if (!board.hasPieceAt(position)) continue;
            PieceType pieceType = board.findPieceTypeByPosition(position);
            validateCannonOver(pieceType);
            pieceCount++;
        }
        if (pieceCount != 1) {
            throw new IllegalArgumentException("포는 해당 위치로 이동할 수 없습니다.");
        }
    }

    @Override
    public void validateTargetSpecialRule(Position toPosition, Board board) {
        if (board.hasPieceAt(toPosition)) {
            PieceType pieceType = board.findPieceTypeByPosition(toPosition);
            validateCannonOver(pieceType);
        }
    }

    private static void validateCannonOver(final PieceType pieceType) {
        if (pieceType == PieceType.CANNON) {
            throw new IllegalArgumentException("포는 포를 먹거나 넘을 수 없습니다.");
        }
    }
}
