package domain.piece;

import domain.BoardStatus;
import domain.PieceExceptionMessage;
import domain.piece.strategy.MoveStrategy;
import domain.position.Position;
import java.util.List;

public class Po extends Piece {

    public static final int REQUIRED_JUMP_COUNT = 1;

    public Po(MoveStrategy moveStrategy, Team team) {
        super(moveStrategy, PieceType.PO, team);
    }

    @Override
    public void check(BoardStatus boardStatus, Position start, Position destination) {
        Piece destinationPiece = boardStatus.getBoardStatus().get(destination);

        checkPoLocatesAtDestination(destinationPiece);
        checkPathHasOnlyOnePiece(boardStatus, start, destination);
    }

    private void checkPathHasOnlyOnePiece(BoardStatus boardStatus, Position start, Position destination) {
        List<Position> movablePath = moveStrategy.findMovablePath(start, destination);
        int jumpedPieces = 0;
        for (Position position : movablePath) {
            Piece pieceToCheck = boardStatus.getBoardStatus().get(position);
            if (pieceToCheck == null) {
                continue;
            }
            if (isPo(pieceToCheck)) {
                throw new IllegalArgumentException(PieceExceptionMessage.CANT_JUMP_OVER_PO.getMessage());
            }
            jumpedPieces += 1;
        }
        checkIsInvalidJumpedPieces(jumpedPieces);
    }

    private boolean isPo(Piece target) {
        return target.getPieceType() == PieceType.PO;
    }

    private void checkIsInvalidJumpedPieces(int jumpedPieces) {
        if (jumpedPieces > REQUIRED_JUMP_COUNT) {
            throw new IllegalArgumentException(PieceExceptionMessage.CANT_JUMP_OVER_THAN_TWO_PIECES.getMessage());
        }
        if (jumpedPieces < REQUIRED_JUMP_COUNT) {
            throw new IllegalArgumentException(PieceExceptionMessage.PO_SHOULD_JUMP_ONE_PIECE.getMessage());
        }
    }

    private void checkPoLocatesAtDestination(Piece destinationPiece) {
        if (destinationPiece != null && isPo(destinationPiece)) {
            throw new IllegalArgumentException(PieceExceptionMessage.CANT_JUMP_OVER_PO.getMessage());
        }
    }

}
