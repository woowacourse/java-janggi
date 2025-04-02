package janggi.domain;

import janggi.domain.board.Board;
import janggi.domain.piece.Piece;
import janggi.domain.position.Position;
import janggi.domain.team.TeamType;
import janggi.domain.team.Turn;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JanggiGame {

    private final Board board;
    private final Turn turn;

    public JanggiGame(Board board, Turn turn) {
        this.board = board;
        this.turn = turn;
    }

    public void move(final List<Position> positions) {
        board.move(positions, turn.getCurrentTeam());
        turn.turnOver();
    }

    public Map<TeamType, Double> getScoreEachTeam() {
        Map<TeamType, Double> scores = new HashMap<>();

        turn.getOrders()
                .forEach(teamType -> scores.put(teamType, calculateCurrentScoreByTeam(teamType)));

        return scores;
    }

    private double calculateCurrentScoreByTeam(TeamType teamType) {
        double totalScore = board.calculateCurrentScoreByTeam(teamType);

        if (turn.isLastOrder(teamType)) {
            totalScore += 1.5;
        }

        return totalScore;
    }

    public boolean canContinueGame() {
        return board.hasEachKing();
    }

    public TeamType getWinningTeam() {
        if (board.hasEachKing()) {
            return Collections.max(getScoreEachTeam().entrySet(), Map.Entry.comparingByValue())
                    .getKey();
        }

        return board.findWinningTeam();
    }

    public TeamType getCurrentTeam() {
        return turn.getCurrentTeam();
    }

    public Map<Position, Piece> getPieces() {
        return board.getPieces();
    }
}
