package janggi.model;

import janggi.model.gimul.AbstractGimul;
import janggi.model.position.Column;
import janggi.model.position.Position;
import janggi.model.position.PositionPath;
import janggi.model.position.Row;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {

    private final Map<Position, AbstractGimul> board;

    public Board(Map<Position, AbstractGimul> board) {
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

        AbstractGimul gimulAtFrom = board.get(from);
        if (!gimulAtFrom.isSameTeam(team)) {
            throw new IllegalArgumentException("상대편 기물을 움직일 수 없습니다.");
        }

        PositionPath legalPath = gimulAtFrom.getLegalPath(from, to);

        List<AbstractGimul> gimulsOnPath = legalPath.stream()
                .filter(board::containsKey)
                .map(board::get)
                .toList();

        validateMovePathAndDestination(to, gimulAtFrom, gimulsOnPath);

        Map<Position, AbstractGimul> movedBoard = new HashMap<>(board);
        movedBoard.put(to, gimulAtFrom);
        movedBoard.remove(from);

        return new Board(movedBoard);
    }

    @Override
    public String toString() {
        int rowStart = 1;
        int rowEnd = 10;

        StringBuilder sb = new StringBuilder();

        sb.append("    1  2  3  4  5  6  7  8  9\n");
        sb.append("  ┌───────────────────────────┐\n");

        for (int row = rowStart; row <= rowEnd; row++) {
            sb.append(renderBoardRow(row));
        }

        sb.append("  └───────────────────────────┘\n");
        return sb.toString();
    }

    private StringBuilder renderBoardRow(int row) {
        StringBuilder sb = new StringBuilder();
        int colStart = 1;
        int colEnd = 9;

        int displayRow = (row == 10) ? 0 : row;

        sb.append(displayRow).append(" │");

        for (int col = colStart; col <= colEnd; col++) {
            sb.append(renderBoardColumn(row, col));
        }
        sb.append("│\n");
        return sb;
    }

    private StringBuilder renderBoardColumn(int row, int col) {
        StringBuilder sb = new StringBuilder();
        Position position = new Position(Row.of(row), Column.of(col));

        String symbol = "·";
        if (board.containsKey(position)) {
            AbstractGimul gimul = board.get(position);
            symbol = gimul.getSymbol();
        }

        sb.append(" ").append(String.format("%-2s", symbol));
        return sb;
    }

    public boolean isGameOver() {
        return !isHanAlive() || !isChoAlive();
    }

    private boolean isChoAlive() {
        return board.values().stream().anyMatch(gimul -> gimul.isSameTeam(Team.CHO));
    }

    private boolean isHanAlive() {
        return board.values().stream().anyMatch(gimul -> gimul.isSameTeam(Team.HAN));
    }

    private void validateMovePathAndDestination(Position to, AbstractGimul gimulAtFrom,
                                                List<AbstractGimul> gimulsOnPath) {
        if ((!board.containsKey(to) && gimulAtFrom.canPassThrough(gimulsOnPath))) {
            return;
        }

        AbstractGimul gimulAtTo = board.get(to);
        if (gimulAtFrom.canPassThrough(gimulsOnPath, gimulAtTo)) {
            return;
        }
        throw new IllegalArgumentException("해당 경로로 기물을 움직일 수 없습니다.");
    }


}
