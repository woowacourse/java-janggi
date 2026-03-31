package domain.board;

import domain.Destinations;
import domain.Position;
import domain.piece.Piece;
import java.util.HashMap;
import java.util.Map;

public class Board implements BoardReader{
    private final Map<Position, Piece> board;

    public Board(Map<Position, Piece> board) {
        this.board = Map.copyOf(board);
    }

    public Destinations findMovablePositions(Position position) {
        validatePieceExists(position);
        Piece piece = board.get(position);
        return piece.findMovablePositions(position, this);
    }

    private void validatePieceExists(Position position) {
        if (!board.containsKey(position)) {
            throw new IllegalArgumentException("기물이 존재하지 않는 위치입니다.");
        }
    }

    public Board movePiece(Position source, Position target) {
        Map<Position, Piece> nextBoardMap = new HashMap<>(this.board);
        Piece movingPiece = nextBoardMap.remove(source);
        nextBoardMap.put(target, movingPiece);
        return new Board(nextBoardMap);
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
        if (isEmpty(position)) {
            throw new IllegalArgumentException("선택한 좌표에 기물이 존재하지 않습니다.");
        }
        return board.get(position);
    }

    public Map<Position, Piece> getBoard() {
        return board;
    }
}
