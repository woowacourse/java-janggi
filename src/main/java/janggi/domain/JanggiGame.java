package janggi.domain;

import janggi.domain.piece.Piece;
import janggi.domain.team.TeamType;
import janggi.dto.BoardSpots;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public void doGame(Position startPosition, Position endPosition) {
        Turn lastTurn = getLastTurn();
        Turn newTurn = lastTurn.move(startPosition, endPosition);
        turns.add(newTurn);
    }

    public TeamType getCurrentTurnTeam() {
        Turn lastTurn = getLastTurn();
        return lastTurn.nextTurnTeam();
    }

    public boolean isGameOver() {
        return findWinner().isPresent();
    }

    public Optional<TeamType> findWinner() {
        return getLastTurn().findWinner();
    }

    public int getWinnerScore() {
        return getLastTurn().getWinnerScore();
    }

    public int getTurnCount() {
        return turns.size() - 1;
    }

    private Turn getLastTurn() {
        return turns.getLast();
    }
}
