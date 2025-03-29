package piece;

import game.Board;
import java.util.List;
import position.Position;

public class Cannon extends Piece {
    public Cannon(final Country country) {
        super(PieceType.CANNON, country);
    }

    public List<Position> findPathForMove(Position fromPosition, Position toPosition) {
        if (!fromPosition.isStraight(toPosition)) {
            throw new IllegalArgumentException("해당 위치로 이동할 수 없습니다.");
        }
        return fromPosition.findStraightPositions(toPosition);
    }

    @Override
    public void validatePath(final List<Position> path, Board board) {
        int pieceCount = 0;
        for (Position position : path) {
            if (!board.hasPieceAt(position)) {
                continue;
            }
            PieceType pieceType = board.findPieceTypeByPosition(position);
            throwIfJumpingOverCannon(pieceType);
            pieceCount++;
        }
        validateJump(pieceCount);
    }

    private static void validateJump(final int pieceCount) {
        if (pieceCount != 1) {
            throw new IllegalArgumentException("포는 해당 위치로 이동할 수 없습니다.");
        }
    }

    private static void throwIfJumpingOverCannon(final PieceType pieceType) {
        CannonSpecialRule(pieceType);
    }

    @Override
    public void validateTargetSpecialRule(Position toPosition, Board board) {
        if (board.hasPieceAt(toPosition)) {
            PieceType pieceType =  board.findPieceTypeByPosition(toPosition);
            CannonSpecialRule(pieceType);
        }

    }

    private static void CannonSpecialRule(final PieceType pieceType) {
        if (pieceType == PieceType.CANNON) {
            throw new IllegalArgumentException("포는 포를 먹거나 넘을 수 없습니다. ");
        }
    }
}
