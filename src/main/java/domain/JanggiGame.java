package domain;

import domain.dao.GameDao;
import domain.piece.Piece;
import domain.piece.Position;
import domain.piece.Team;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JanggiGame {

    private final GameDao gameDao;
    private final JanggiBoard janggiBoard;

    public JanggiGame(GameDao gamesDao, JanggiBoard board) {
        this.janggiBoard = board;
        this.gameDao = gamesDao;
    }

    public void move(List<Integer> startRowAndColumn, List<Integer> targetRowAndColumn) {
        Position startPosition = new Position(startRowAndColumn.getFirst(), startRowAndColumn.getLast());
        Position targetPosition = new Position(targetRowAndColumn.getFirst(), targetRowAndColumn.getLast());
        validateSelectedPiece(startPosition, targetPosition);
        janggiBoard.move(startPosition, targetPosition);
        nextTurn();
    }

    private void nextTurn() {
        Team thisTurn = gameDao.findTurn();
        gameDao.changeTurn(thisTurn.getEnemy());
    }

    public boolean isEnd() {
        boolean isChoGungDead = !janggiBoard.existGung(Team.CHO);
        boolean isHanGungDead = !janggiBoard.existGung(Team.HAN);

        return isChoGungDead || isHanGungDead;
    }

    private void validateSelectedPiece(Position startPosition, Position targetPosition) {
        Piece selectedPiece = janggiBoard.findSelectedPiece(startPosition);
        if (!selectedPiece.isTeam(getThisTurnTeam())) {
            throw new IllegalArgumentException("자신의 말만 움직일 수 있습니다.");
        }
        if (startPosition.equals(targetPosition)) {
            throw new IllegalArgumentException("말을 움직여 주세요");
        }
    }

    public Team getThisTurnTeam() {
        return gameDao.findTurn();
    }

    public Map<Team, Double> calculateScore() {
        Map<Team, Double> scores = Arrays.stream(Team.values())
                .collect(Collectors.toMap(
                        team -> team,
                        team -> (double) janggiBoard.calculateTeamScore(team)
                ));
        scores.put(Team.HAN, scores.get(Team.HAN) + 1.5);

        return scores;
    }

    public JanggiBoard getBoard() {
        return janggiBoard;
    }
}
