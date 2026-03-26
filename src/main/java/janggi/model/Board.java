package janggi.model;

import janggi.model.gimul.Gimul;
import janggi.model.position.Column;
import janggi.model.position.Position;
import janggi.model.position.PositionPath;
import janggi.model.position.Row;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {

    private final Map<Position, Gimul> board;

    public Board(Map<Position, Gimul> board) {
        this.board = board;
    }

    public Board move(
            Team team,
            Position from,
            Position to
    ) {
        if (!board.containsKey(from)) {
            throw new IllegalArgumentException("해당 위치에 기물이 존재하지 않습니다.");
        }

        Gimul gimulAtFrom = board.get(from);
        if (!gimulAtFrom.isSameTeam(team)) {
            throw new IllegalArgumentException("상대편 기물을 움직일 수 없습니다.");
        }

        PositionPath legalPath = gimulAtFrom.getLegalPath(from, to);

        List<Gimul> gimulsOnPath = legalPath.stream()
                .filter(board::containsKey)
                .map(board::get)
                .toList();

        Gimul gimulAtTo = board.get(to);

        if (!gimulAtFrom.canPassThrough(gimulsOnPath, gimulAtTo)) {
            throw new IllegalArgumentException("해당 경로로 기물을 움직일 수 없습니다.");
        }

        Map<Position, Gimul> movedBoard = new HashMap<>(board);
        movedBoard.put(to, gimulAtFrom);

        return new Board(movedBoard);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("    0  1  2  3  4  5  6  7  8\n");
        sb.append("  ┌───────────────────────────┐\n");

        for (int row = 0; row < 10; row++) {
            sb.append(row).append(" │");

            for (int col = 0; col < 9; col++) {
                Position position = new Position(Row.of(row), Column.of(col));
                Gimul gimul = board.get(position);

                String symbol = (gimul == null) ? "·" : gimul.getSymbol();
                sb.append(" ").append(String.format("%-2s", symbol));
            }

            sb.append("│\n");
        }

        sb.append("  └───────────────────────────┘\n");
        return sb.toString();
    }
}
