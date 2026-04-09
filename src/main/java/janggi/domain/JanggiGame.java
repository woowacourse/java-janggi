package janggi.domain;

import janggi.domain.piece.Piece;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JanggiGame {

    private final List<Turn> turns;

    private JanggiGame(List<Turn> turns) {
        this.turns = new ArrayList<>(turns);
    }

    public static JanggiGame createInitialJanggiGame() {
        return new JanggiGame(List.of(Turn.createInitialTurn()));
    }

    public static JanggiGame loadPreviousJanggiGame(Turn previousTurn) {
        return new JanggiGame(List.of(previousTurn));
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
        return lastTurn.nextTurnTeam();
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

    public void doGame(Position start, Position end) {
        Turn lastTurn = getLastTurn();
        Turn newTurn = lastTurn.move(start, end);
        turns.add(newTurn);
    }

    public String winTeamName() {
        return getLastTurn().winTeamName();
    }

    private Turn getLastTurn() {
        return turns.getLast();
    }
}
