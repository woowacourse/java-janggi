package janggi.domain.board;

import janggi.domain.Position;
import janggi.domain.board.initializer.BoardInitializer;
import janggi.domain.piece.Camp;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import java.util.HashMap;
import java.util.Map;

public class Board implements BoardChecker {

    private static final String INVALID_CAMP_PIECE = "[ERROR] 상대 진영의 기물은 이동할 수 없습니다.";
    private static final String SOURCE_NOT_EXISTS = "[ERROR] 출발지에 기물이 존재하지 않습니다.";
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
    public boolean hasSamePieceRuleAt(Position position, PieceType pieceType) {
        if (board.containsKey(position)) {
            Piece foundPiece = board.get(position);
            return foundPiece.isSamePieceRule(pieceType);
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
            throw new IllegalArgumentException(INVALID_CAMP_PIECE);
        }
    }

    private void validateSource(Position source) {
        if (!board.containsKey(source)) {
            throw new IllegalArgumentException(SOURCE_NOT_EXISTS);
        }
    }

    public Map<Position, Piece> getBoard() {
        return Map.copyOf(board);
    }
}
