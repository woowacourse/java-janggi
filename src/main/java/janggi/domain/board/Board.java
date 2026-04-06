package janggi.domain.board;

import janggi.domain.Position;
import janggi.domain.Turn;
import janggi.domain.piece.Camp;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceRule;
import janggi.exception.ExceptionMessage;
import java.util.HashMap;
import java.util.Map;

public class Board implements BoardChecker {

    private final Map<Position, Piece> board;

    public Board(Map<Position, Piece> board) {
        this.board = new HashMap<>(board);
    }

    @Override
    public boolean hasPieceAt(Position position) {
        return board.containsKey(position);
    }


    @Override
    public boolean hasSamePieceRuleAt(Position position, PieceRule pieceRule) {
        if (hasPieceAt(position)) {
            Piece piece = board.get(position);
            return piece.isSamePieceRule(pieceRule);
        }
        return false;
    }

    public void movePiece(Position source, Position destination, Camp camp) {
        validateSource(source, camp);
        validateDestination(destination, source, camp);

        Piece piece = board.get(source);
        piece.validateMove(source, destination, this);

        board.put(destination, piece);
        board.remove(source);
    }


    public void validateSource(Position source, Camp camp) {
        if (!hasPieceAt(source)) {
            throw new IllegalArgumentException(ExceptionMessage.SOURCE_NOT_EXISTS.getMessage());
        }
        if (!hasSameCampPieceAt(source, camp)) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_CAMP_PIECE.getMessage());
        }
    }

    public void validateDestination(Position destination, Position source, Camp camp) {
        if (destination.equals(source)) {
            throw new IllegalArgumentException(ExceptionMessage.PIECE_MUST_MOVE.getMessage());
        }
        if (hasSameCampPieceAt(destination, camp)) {
            throw new IllegalArgumentException(ExceptionMessage.SAME_CAMP_PIECE_AT_DESTINATION.getMessage());
        }
    }

    private boolean hasSameCampPieceAt(Position position, Camp camp) {
        if (hasPieceAt(position)) {
            Piece piece = board.get(position);
            return piece.isSameCamp(camp);
        }
        return false;
    }

    public Map<Position, Piece> getBoard() {
        return Map.copyOf(board);
    }

    public boolean isRivalGeneralKilled(Turn turn) {
        Camp camp = turn.peekNextTurn();
        return board.values().stream()
                .noneMatch(piece -> piece.isSameCamp(camp) && piece.isSamePieceRule(PieceRule.GENERAL));
    }
}
