package janggi.domain;

import janggi.domain.piece.Piece;
import janggi.dto.BoardSpot;
import java.util.ArrayList;
import java.util.List;

public class JanggiGame {

    private final List<Turn> turns;

    private JanggiGame(List<Turn> turns) {
        this.turns = new ArrayList<>(turns);
    }

    public static JanggiGame createInitialJanggiGame() {
        return new JanggiGame(List.of(Turn.createInitialTurn()));
    }

    public List<BoardSpot> makeCurrentTurnBoardSnapShot() {
        Turn lastTurn = getLastTurn();
        return lastTurn.makeBoardSnapShot();
    }

    public void validatePieceExist(Position position) {
        Turn lastTurn = getLastTurn();
        if (!lastTurn.isMyTeamPieceExist(position)) {
            throw new IllegalArgumentException("기물이 존재하는 좌표가 아닙니다.");
        }
    }

    public void validateValidEndPosition(Position startPosition, Position endPosition) {
        Turn lastTurn = getLastTurn();
        if (lastTurn.isMyTeamPieceExist(endPosition)) {
            throw new IllegalArgumentException("아군이 존재하는 좌표로 이동할 수 없습니다.");
        }
        lastTurn.canMove(startPosition, endPosition);
    }

    public Piece findPiece(Position position) {
        return getLastTurn().findPiece(position);
    }

    private Turn getLastTurn() {
        return turns.getLast();
    }

    public void doGame(Position startPosition, Position endPosition) {
        Turn lastTurn = getLastTurn();
        Turn newTurn = lastTurn.move(startPosition, endPosition);
        turns.add(newTurn);
    }

    public String getCurrentTurnTeamName() {
        Turn lastTurn = getLastTurn();
        return lastTurn.nextTurnTeam();
    }
}
