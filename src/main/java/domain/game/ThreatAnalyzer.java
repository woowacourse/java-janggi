package domain.game;

import domain.board.Board;
import domain.board.BoardPiece;
import domain.board.Position;
import domain.piece.Camp;
import domain.piece.Piece;
import domain.piece.PieceType;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ThreatAnalyzer {
    private static final int MIN_X = 1;
    private static final int MAX_X = 9;
    private static final int MIN_Y = 1;
    private static final int MAX_Y = 10;

    public boolean isInCheck(Board board, Camp generalCamp) {
        Position generalPosition = board.findPositionOf(generalCamp, PieceType.GENERAL);
        Set<Position> threatenedPositions = findThreatenedPositions(board, generalCamp.opponent());

        return threatenedPositions.contains(generalPosition);
    }

    private Set<Position> findThreatenedPositions(Board board, Camp attackerCamp) {
        Set<Position> threatenedPositions = new HashSet<>();
        List<BoardPiece> boardPieces = board.pieces();

        for (BoardPiece boardPiece : boardPieces) {
            if (boardPiece.camp() != attackerCamp) {
                continue;
            }

            collectThreatenedPositions(board, threatenedPositions, boardPiece.position());
        }

        return threatenedPositions;
    }

    private void collectThreatenedPositions(Board board, Set<Position> threatenedPositions, Position from) {
        Piece piece = board.findBy(from);

        for (int x = MIN_X; x <= MAX_X; x++) {
            for (int y = MIN_Y; y <= MAX_Y; y++) {
                Position target = new Position(x, y);

                if (canAttack(board, piece, from, target)) {
                    threatenedPositions.add(target);
                }
            }
        }
    }

    private boolean canAttack(Board board, Piece piece, Position from, Position target) {
        try {
            piece.validateMove(from, target, board);
            return true;
        } catch (IllegalArgumentException exception) {
            return false;
        }
    }
}
