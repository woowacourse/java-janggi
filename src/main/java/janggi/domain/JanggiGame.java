package janggi.domain;

import janggi.domain.piece.Piece;
import janggi.dto.BoardSpots;
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

    public BoardSpots makeCurrentTurnBoardSnapShot() {
        Turn lastTurn = getLastTurn();
        return lastTurn.makeBoardSnapShot();
    }

    public void validatePieceExist(Position position) {
        Turn lastTurn = getLastTurn();
        if (!lastTurn.isCurrentTeamPieceExist(position)) {
            throw new IllegalArgumentException("기물이 존재하는 좌표가 아닙니다.");
        }
    }

    public void validateValidEndPosition(Position start, Position end) {
        Turn lastTurn = getLastTurn();
        if (lastTurn.isCurrentTeamPieceExist(end)) {
            throw new IllegalArgumentException("아군이 존재하는 좌표로 이동할 수 없습니다.");
        }
        lastTurn.validateCanMove(start, end);
    }

    public Piece findPiece(Position position) {
        return getLastTurn().findPiece(position);
    }

    public String getPieceName(Position position) {
        return getLastTurn().getPieceName(position);
    }

    public void doGame(Position start, Position end) {
        Turn lastTurn = getLastTurn();
        Turn newTurn = lastTurn.move(start, end);
        turns.add(newTurn);
    }

    public String getCurrentTurnTeamName() {
        Turn lastTurn = getLastTurn();
        return lastTurn.nextTurnTeam();
    }

    private Turn getLastTurn() {
        return turns.getLast();
    }
}
