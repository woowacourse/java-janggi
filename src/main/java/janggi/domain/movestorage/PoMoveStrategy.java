package janggi.domain.movestorage;

import janggi.domain.BoardState;
import janggi.domain.Column;
import janggi.domain.Piece;
import janggi.domain.PieceType;
import janggi.domain.Position;
import janggi.domain.Row;

import java.util.List;

public class PoMoveStrategy implements MoveStrategy {

    @Override
    public boolean canMove(Position from, Position to, BoardState boardState) {
        List<Integer> fromPosition = from.getPosition();
        List<Integer> toPosition = to.getPosition();

        int fromX = fromPosition.getFirst();
        int fromY = fromPosition.getLast();
        int toX = toPosition.getFirst();
        int toY = toPosition.getLast();

        if (fromX != toX && fromY != toY) {
            return false;
        }

        int jumpCount = 0;

        if (fromX == toX) {
            int start = Math.min(fromY, toY) + 1;
            int end = Math.max(fromY, toY);

            for (int i = start; i < end; i++) {
                Position position = Position.of(Row.of(fromX), Column.of(i));
                if (boardState.hasPieceAt(position)) {
                    Piece jumpPiece = boardState.getPieceAt(position);
                    if (jumpPiece.getPieceType() == PieceType.PO) {
                        return false;
                    }
                    jumpCount++;
                }
            }
        }

        if (fromY == toY) {
            int start = Math.min(fromX, toX) + 1;
            int end = Math.max(fromX, toX);

            for (int i = start; i < end; i++) {
                Position position = Position.of(Row.of(i), Column.of(fromY));
                if (boardState.hasPieceAt(position)) {
                    Piece jumpPiece = boardState.getPieceAt(position);
                    if (jumpPiece.getPieceType() == PieceType.PO) {
                        return false;
                    }
                    jumpCount++;
                }
            }
        }

        if (jumpCount != 1) {
            return false;
        }

        if (boardState.hasPieceAt(to)) {
            Piece targetPiece = boardState.getPieceAt(to);
            if (targetPiece.getPieceType() == PieceType.PO) {
                return false;
            }
        }
        return true;
    }
}
