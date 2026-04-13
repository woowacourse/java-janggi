package domain.board;

import domain.game.Destinations;
import domain.game.Position;
import domain.piece.Piece;
import java.util.HashMap;
import java.util.Map;

public class Board implements BoardReader{
    private final Map<Position, Piece> board;

    public Board(Map<Position, Piece> board) {
        this.board = Map.copyOf(board);
    }

    public Destinations findDestinations(Position position) {
        validatePieceExists(position);
        Piece piece = board.get(position);
        return piece.findDestinations(position, this);
    }

    public Board movePiece(Position source, Position target) {
        validatePieceExists(source);
        Map<Position, Piece> nextBoardMap = new HashMap<>(this.board);
        Piece movingPiece = nextBoardMap.remove(source);
        nextBoardMap.put(target, movingPiece);
        return new Board(nextBoardMap);
    }

    private void validatePieceExists(Position position) {
        if (isEmpty(position)) {
            throw new IllegalArgumentException("기물이 존재하지 않는 위치입니다.");
        }
    }

    public boolean isGameOver() {
        return board.values().stream()
                .filter(Piece::isGeneral)
                .count() < 2;
    }

    @Override
    public boolean isEmpty(Position position) {
        return !board.containsKey(position);
    }

    @Override
    public Piece getPiece(Position position) {
        validatePieceExists(position);
        return board.get(position);
    }

    public Map<Position, Piece> getBoard() {
        return board;
    }
}
