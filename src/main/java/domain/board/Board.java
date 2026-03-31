package domain.board;

import domain.MovablePositions;
import domain.piece.Piece;
import domain.Position;
import java.util.HashMap;
import java.util.Map;

public class Board implements BoardReader{
    private final Map<Position, Piece> board;

    public Board(Map<Position, Piece> board) {
        this.board = Map.copyOf(board);
    }

    public MovablePositions findMovablePositions(Position position) {
        validatePieceExists(position);
        Piece piece = board.get(position);
        return piece.findMovablePositions(position, this);
    }

    private void validatePieceExists(Position position) {
        if (!board.containsKey(position)) {
            throw new IllegalArgumentException("기물이 존재하지 않는 위치입니다.");
        }
    }

    public Board movePiece(Position from, Position to) {
        Map<Position, Piece> nextBoardMap = new HashMap<>(this.board);
        Piece movingPiece = nextBoardMap.remove(from);
        if (movingPiece == null) {
            throw new IllegalArgumentException("출발지에 기물이 없습니다.");
        }
        nextBoardMap.put(to, movingPiece);
        return new Board(nextBoardMap);
    }

    public boolean isGameOver() {
        return board.values().stream()
                .filter(Piece::isGeneral)
                .count() < 2;
    }

    @Override
    public boolean isWithinRange(Position position) {
        return Position.isWithinRange(position.getX(), position.getY());
    }

    @Override
    public boolean isEmpty(Position position) {
        return !board.containsKey(position);
    }

    @Override
    public Piece getPiece(Position position) {
        return board.get(position);
    }

    public Map<Position, Piece> getBoard() {
        return board;
    }
}
