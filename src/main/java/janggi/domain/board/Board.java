package janggi.domain.board;

import janggi.domain.Position;
import janggi.domain.piece.Camp;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceRule;
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

    public Map<Position, Piece> movePiece(Position source, Position destination) {
        Piece piece = board.get(source);
        piece.validateMove(source, destination, this);
        board.put(destination, piece);
        board.remove(source);

        return Map.copyOf(board);
    }

    public Map<Position, Piece> getBoard() {
        return Map.copyOf(board);
    }
}
