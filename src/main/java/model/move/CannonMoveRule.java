package model.move;

import model.board.Board;
import model.board.Country;
import model.pieces.Piece;
import model.pieces.PieceType;

import java.util.List;


public class CannonMoveRule extends MoveRule {

    @Override
    public boolean matches(Move move, Board board, Country country) {
        if (!move.isStraight()) {
            return false;
        }

        List<Piece> betweenPieces = board.findBetweenPieces(move);
        if (!isValidBridge(betweenPieces)) {
            return false;
        }
        Piece from = board.findPiece(move.from());
        Piece target = board.findPiece(move.to());
        return isValidTarget(from, target);
    }


    private boolean isValidBridge(List<Piece> betweenPieces) {
        return betweenPieces.size() == 1
                && betweenPieces.getFirst().pieceType() != PieceType.CANNON;
    }

    private boolean isValidTarget(Piece from, Piece target) {
        if (target == null) {
            return true;
        }
        return target.pieceType() != PieceType.CANNON
                && from.country() != target.country();
    }

}
