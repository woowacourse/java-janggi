package domain;

import domain.boardgenerator.BoardGenerator;
import domain.piece.Piece;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JanggiGame {

    private final JanggiBoard janggiBoard;
    private Team turn;

    public JanggiGame(BoardGenerator boardGenerator) {
        this.janggiBoard = new JanggiBoard(boardGenerator);
        this.turn = Team.CHO;
    }

    public void move(List<Integer> startRowAndColumn, List<Integer> targetRowAndColumn) {
        Position startPosition = new Position(startRowAndColumn.getFirst(), startRowAndColumn.getLast());
        Position targetPosition = new Position(targetRowAndColumn.getFirst(), targetRowAndColumn.getLast());
        validateSelectedPiece(startPosition, targetPosition);
        janggiBoard.move(startPosition, targetPosition);
        nextTurn();
    }

    private void nextTurn() {
        turn = this.turn.getEnemy();
    }

    public boolean isEnd() {
        boolean isChoGungDead = !janggiBoard.existGung(Team.CHO);
        boolean isHanGungDead = !janggiBoard.existGung(Team.HAN);

        return isChoGungDead || isHanGungDead;
    }

    private void validateSelectedPiece(Position startPosition, Position targetPosition) {
        Piece selectedPiece = janggiBoard.findSelectedPiece(startPosition);
        if (!selectedPiece.isTeam(turn)) {
            throw new IllegalArgumentException("자신의 말만 움직일 수 있습니다.");
        }
        if (startPosition.equals(targetPosition)) {
            throw new IllegalArgumentException("말을 움직여 주세요");
        }
    }

    public Team getThisTurnTeam() {
        return turn;
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
