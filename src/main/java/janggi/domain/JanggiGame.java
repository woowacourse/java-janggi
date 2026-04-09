package janggi.domain;

import janggi.domain.piece.Piece;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JanggiGame {

    private final Long id;
    private final List<Turn> turns;

    private JanggiGame(Long id, List<Turn> turns) {
        this.id = id;
        this.turns = new ArrayList<>(turns);
    }

    public static JanggiGame createInitialJanggiGame(long gameId) {
        return new JanggiGame(gameId, List.of(Turn.createInitialTurn()));
    }

    public static JanggiGame loadPreviousJanggiGame(long gameId, Turn previousTurn) {
        return new JanggiGame(gameId, List.of(previousTurn));
    }

    public long getId() {
        return id;
    }

    public boolean isRunning() {
        return getLastTurn().isRunning();
    }

    public Map<Position, Piece> makeCurrentTurnBoardSnapShot() {
        Turn lastTurn = getLastTurn();
        return lastTurn.makeBoardSnapShot();
    }

    public String getCurrentTurnTeamName() {
        Turn lastTurn = getLastTurn();
        return lastTurn.nextTurnTeamName();
    }

    public void validatePieceExists(Position position) {
        Turn lastTurn = getLastTurn();
        if (!lastTurn.isNextTurnTeamPieceExists(position)) {
            throw new IllegalArgumentException("기물이 존재하는 좌표가 아닙니다.");
        }
    }

    public String getPieceName(Position position) {
        return getLastTurn().getPieceName(position);
    }

    public void validateValidEndPosition(Position start, Position end) {
        Turn lastTurn = getLastTurn();
        lastTurn.validateCanMove(start, end);
    }

    public Turn move(Position start, Position end) {
        Turn lastTurn = getLastTurn();
        return lastTurn.move(start, end);
    }

    public void addNewTurn(Turn savedTurn) {
        turns.add(savedTurn);
    }

    public String winTeamName() {
        return getLastTurn().winTeamName();
    }

    private Turn getLastTurn() {
        return turns.getLast();
    }
}
