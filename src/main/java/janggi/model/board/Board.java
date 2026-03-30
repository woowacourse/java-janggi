package janggi.model.board;

import janggi.model.Team;
import janggi.model.board.moveResult.MoveResult;
import janggi.model.board.position.Position;
import janggi.model.gimul.AbstractGimul;
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

        MoveResult moveResult = gimulAtFrom.getLegalPath(from, to);

        List<AbstractGimul> gimulsOnPath = moveResult.getPath().stream()
                .filter(board::containsKey)
                .map(board::get)
                .toList();

        validateMovePathAndDestination(to, gimulAtFrom, gimulsOnPath);

        Map<Position, AbstractGimul> movedBoard = new HashMap<>(board);
        movedBoard.put(to, gimulAtFrom);
        movedBoard.remove(from);

        return new Board(movedBoard);
    }

    private void validateMovePathAndDestination(
            Position to, AbstractGimul gimulAtFrom,
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

    public boolean isGameOver() {
        return !isHanAlive() || !isChoAlive();
    }

    private boolean isChoAlive() {
        return board.values().stream().anyMatch(gimul -> gimul.isSameTeam(Team.CHO));
    }

    private boolean isHanAlive() {
        return board.values().stream().anyMatch(gimul -> gimul.isSameTeam(Team.HAN));
    }

    public Map<Position, AbstractGimul> getBoard() {
        return Map.copyOf(board);
    }
}
