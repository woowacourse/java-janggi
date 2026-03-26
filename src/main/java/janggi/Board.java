package janggi;

import janggi.gimul.Gimul;
import janggi.position.Position;
import janggi.position.PositionPath;
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
                .skip(1)
                .filter(board::containsKey)
                .map(board::get)
                .toList();

        if (!gimulAtFrom.canPassThrough(gimulsOnPath,  board.get(to))
                || gimulsOnPath.getLast().isSameTeam(gimulAtFrom)
        ) {
            throw new IllegalArgumentException("해당 경로로 기물을 움직일 수 없습니다.");
        }

        Map<Position, Gimul> movedBoard = new HashMap<>(board);
        movedBoard.put(to, gimulAtFrom);

        return new Board(movedBoard);
    }
}
