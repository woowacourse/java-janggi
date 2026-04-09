package janggi.model.board;

import janggi.model.Score;
import janggi.model.Team;
import janggi.model.gimul.AbstractGimul;
import janggi.model.position.Position;
import janggi.model.position.PositionPath;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Board {

    private final Map<Position, AbstractGimul> board;

    public Board(Map<Position, AbstractGimul> board) {
        this.board = board;
    }

    public Board move(Team team, Position from, Position to) {
        validateFrom(team, from);
        AbstractGimul gimulAtFrom = board.get(from);
        PositionPath legalPath = gimulAtFrom.getLegalPath(from, to);
        List<AbstractGimul> gimulsOnPath = getGimulsOnPath(legalPath);
        validateMovePathAndDestination(to, gimulAtFrom, gimulsOnPath);
        return createMovedBoard(from, to, gimulAtFrom);
    }

    public Map<Position, AbstractGimul> snapshot() {
        return Collections.unmodifiableMap(board);
    }

    private void validateFrom(Team team, Position from) {
        if (!board.containsKey(from)) {
            throw new IllegalArgumentException("해당 위치에 기물이 존재하지 않습니다.");
        }
        if (!board.get(from).isSameTeam(team)) {
            throw new IllegalArgumentException("상대편 기물을 움직일 수 없습니다.");
        }
    }

    private List<AbstractGimul> getGimulsOnPath(PositionPath legalPath) {
        return legalPath.stream()
                .filter(board::containsKey)
                .map(board::get)
                .toList();
    }

    private Board createMovedBoard(Position from, Position to, AbstractGimul gimulAtFrom) {
        Map<Position, AbstractGimul> movedBoard = new HashMap<>(board);
        movedBoard.put(to, gimulAtFrom);
        movedBoard.remove(from);
        return new Board(movedBoard);
    }

    private void validateMovePathAndDestination(Position to, AbstractGimul gimulAtFrom,
                                                List<AbstractGimul> gimulsOnPath) {
        Optional<AbstractGimul> gimulAtTo = Optional.ofNullable(board.get(to));
        if (gimulAtFrom.canPassThrough(gimulsOnPath, gimulAtTo)) {
            return;
        }
        throw new IllegalArgumentException("해당 경로로 기물을 움직일 수 없습니다.");
    }

    public boolean isGameOver() {
        return isKingCaptured(Team.CHO) || isKingCaptured(Team.HAN);
    }

    private boolean isKingCaptured(Team team) {
        return board.values().stream()
                .noneMatch(gimul -> gimul.isKing() && gimul.isSameTeam(team));
    }

    public Score calculateScore(Team team) {
        Score sumOfScore = board.values().stream()
                .filter(gimul -> gimul.isSameTeam(team))
                .map(AbstractGimul::getScore)
                .reduce(Score.zero(), Score::add);
        return sumOfScore.add(team.bonusScore());
    }
}
