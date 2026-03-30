package domain;

import java.util.List;
import java.util.Map;

public class Board {
    private final Map<Position, Piece> board;
    public Board(Map<Position, Piece> board) {
        this.board = board;
    }

    public Map<Position, Piece> getBoard() {
        return board;
    }

    public List<Position> getPossibleDestinations(Position position) {
        if (!board.containsKey(position)) {
            throw new IllegalArgumentException("기물이 존재하지 않는 위치입니다.");
        }
        Piece piece = board.get(position);
        // TODO: 상대 기물인 경우 에외발생 - 현재 누구 차례인지 정보 필요
        List<Position> positions = piece.getAllPosition(position);
        Map<Position, Piece> map = func(positions);
        return piece.getPossibleDestinations(position, map);
    }

    private Map<Position, Piece> func(List<Position> positions) {
        return Map.of();
    }

    public void movePiece(Position from, Position to) {
        board.put(to, board.remove(from));
    }

    public boolean isGameOver() {
        return board.values().stream()
                .filter(piece -> piece instanceof General)
                .count() < 2;
    }
}
