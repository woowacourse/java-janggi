package janggi.domain.board;

import janggi.domain.Position;
import janggi.domain.piece.Camp;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceRule;
import janggi.exception.ExceptionMessage;
import java.util.HashMap;
import java.util.Map;

public class Board implements BoardChecker {

    private final Map<Position, Piece> board = new HashMap<>();

    public Board(BoardInitializer boardInitializer) {
        board.putAll(boardInitializer.initialize());
    }

    @Override
    public boolean hasPieceAt(Position position) {
        return board.containsKey(position);
    }

    @Override
    public boolean isSameCampPieceAt(Position position, Camp camp) {
        if (board.containsKey(position)) {
            return board.get(position).isSameCamp(camp);
        }
        return false;
    }

    @Override
    public boolean hasSamePieceRuleAt(Position position, PieceRule pieceRule) {
        if (board.containsKey(position)) {
            Piece foundPiece = board.get(position);
            return foundPiece.isSamePieceRule(pieceRule);
        }
        return false;
    }

    public void movePiece(Position source, Position destination, Camp turn) {
        validateCampTurn(source, turn);
        Piece piece = board.get(source);
        piece.validateMove(source, destination, this);
        board.put(destination, piece);
        board.remove(source);
    }

    public void validateCampTurn(Position source, Camp turn) {
        validateSource(source);
        Piece piece = board.get(source);
        if (!piece.isSameCamp(turn)) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_CAMP_PIECE.getMessage());
        }
    }

    private void validateSource(Position source) {
        if (!board.containsKey(source)) {
            throw new IllegalArgumentException(ExceptionMessage.SOURCE_NOT_EXISTS.getMessage());
        }
    }

    public Map<Position, Piece> getBoard() {
        return Map.copyOf(board);
    }
}
