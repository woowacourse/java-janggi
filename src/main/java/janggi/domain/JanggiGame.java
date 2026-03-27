package janggi.domain;

import janggi.domain.piece.Piece;
import janggi.domain.side.TeamType;
import java.util.ArrayList;
import java.util.List;

public class JanggiGame {

    private final List<Turn> value;

    private JanggiGame(List<Turn> value) {
        this.value = value;
    }

    public static JanggiGame createInitialJanggiGame(Board board) {
        return new JanggiGame(List.of(new Turn(TeamType.HAN, board)));
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
        return value.getLast();
    }

    public JanggiGame doGame(Position startPosition, Position endPosition) {
        Turn lastTurn = getLastTurn();
        Turn newTurn = lastTurn.move(startPosition, endPosition);
        List<Turn> updatedTurns = new ArrayList<>(value);
        updatedTurns.add(newTurn);
        return new JanggiGame(updatedTurns);
    }

    public String getCurrentTurnTeamName() {
        Turn lastTurn = getLastTurn();
        return lastTurn.nextTurnTeam();
    }
}
