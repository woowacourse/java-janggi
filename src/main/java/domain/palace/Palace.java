package domain.palace;

import domain.Position;
import domain.movestrategy.BasicFixedMoveStrategy;
import domain.movestrategy.BasicRangeMoveStrategy;
import domain.movestrategy.FixedMoveStrategyChangeable;
import domain.movestrategy.PalaceFixedMoveStrategy;
import domain.movestrategy.PalaceRangeMoveStrategy;
import domain.movestrategy.RangeMoveStrategyChangeable;
import domain.piece.Piece;
import domain.piece.PieceType;
import java.util.List;

public class Palace {
    public List<Position> palace;

    public Palace() {
        palace = List.of(
                new Position(1, 4), new Position(1, 5), new Position(1, 6),
                new Position(2, 4), new Position(2, 5), new Position(2, 6),
                new Position(3, 4), new Position(3, 5), new Position(3, 6),
                new Position(8, 4), new Position(8, 5), new Position(8, 6),
                new Position(9, 4), new Position(9, 5), new Position(9, 6),
                new Position(10, 4), new Position(10, 5), new Position(10, 6)
        );
    }

    public void checkAndChangeStrategy(Position startPosition, Position targetPosition, Piece startPiece) {
        if (validateIsCastle(startPosition, targetPosition)) {
            changeInPalaceStrategy(startPiece);
        }
        if (palace.contains(startPosition) && !palace.contains(targetPosition)) {
            validateKingMove(startPiece);
            changeOutPalaceStrategy(startPiece);
        }
    }

    public boolean validateIsCastle(Position startPosition, Position targetPosition) {
        return palace.contains(startPosition) && palace.contains(targetPosition);
    }

    private void changeInPalaceStrategy(Piece startPiece) {
        if (startPiece.getPieceType() == PieceType.CANNON || startPiece.getPieceType() == PieceType.CHARIOT) {
            RangeMoveStrategyChangeable changedPiece = (RangeMoveStrategyChangeable) startPiece;
            changedPiece.changeStrategy(new PalaceRangeMoveStrategy());
        }
        if (startPiece.getPieceType() == PieceType.PAWN) {
            FixedMoveStrategyChangeable changedPiece = (FixedMoveStrategyChangeable) startPiece;
            changedPiece.changeStrategy(new PalaceFixedMoveStrategy());
        }
    }

    private void changeOutPalaceStrategy(Piece startPiece) {
        if (startPiece.getPieceType() == PieceType.CANNON || startPiece.getPieceType() == PieceType.CHARIOT) {
            RangeMoveStrategyChangeable changedPiece = (RangeMoveStrategyChangeable) startPiece;
            changedPiece.changeStrategy(new BasicRangeMoveStrategy());
        }
        if (startPiece.getPieceType() == PieceType.PAWN) {
            FixedMoveStrategyChangeable changedPiece = (FixedMoveStrategyChangeable) startPiece;
            changedPiece.changeStrategy(new BasicFixedMoveStrategy());
        }
    }

    private void validateKingMove(Piece startPiece) {
        if (startPiece.getPieceType() == PieceType.KING) {
            throw new IllegalArgumentException("궁은 궁궐 밖으로 나갈 수 없습니다.");
        }
        if (startPiece.getPieceType() == PieceType.GUARD) {
            throw new IllegalArgumentException("사는 궁궐 밖으로 나갈 수 없습니다.");
        }
    }


}
