package domain;

import domain.piece.Piece;
import domain.piece.Position;
import domain.piece.Team;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class JanggiGame {

    public static final double HAN_TEAM_BONUS_SCORE = 1.5;

    private final JanggiBoard janggiBoard;
    private final Long gameId;
    private Team turn = Team.CHO;

    private JanggiGame(Long gameId, JanggiBoard board, Team turn) {
        this.janggiBoard = board;
        this.gameId = gameId;
        this.turn = turn;
    }

    public static JanggiGame init(Long gameId, JanggiBoard board) {
        return new JanggiGame(gameId, board, Team.getFirstTeam());
    }

    public static JanggiGame create(Long gameId, JanggiBoard board, Team turn) {
        return new JanggiGame(gameId, board, turn);
    }


    public void move(List<Integer> startRowAndColumn, List<Integer> targetRowAndColumn) {
        Position startPosition = new Position(startRowAndColumn.getFirst(), startRowAndColumn.getLast());
        Position targetPosition = new Position(targetRowAndColumn.getFirst(), targetRowAndColumn.getLast());
        validateSelectedPiece(startPosition, targetPosition);
        janggiBoard.move(startPosition, targetPosition);
        nextTurn();
    }

    private void nextTurn() {
        this.turn = turn.getEnemy();
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
        return turn;
    }

    public Map<Team, Double> calculateScore() {
        Map<Team, Double> scores = Arrays.stream(Team.values())
                .collect(Collectors.toMap(
                        team -> team,
                        team -> (double) janggiBoard.calculateTeamScore(team)
                ));
        scores.put(Team.HAN, scores.get(Team.HAN) + HAN_TEAM_BONUS_SCORE);

        return scores;
    }

    public Long getGameId() {
        return gameId;
    }

    public JanggiBoard getBoard() {
        return janggiBoard;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JanggiGame game = (JanggiGame) o;
        return Objects.equals(janggiBoard, game.janggiBoard) && Objects.equals(gameId, game.gameId)
                && turn == game.turn;
    }

    @Override
    public int hashCode() {
        return Objects.hash(janggiBoard, gameId, turn);
    }
}
