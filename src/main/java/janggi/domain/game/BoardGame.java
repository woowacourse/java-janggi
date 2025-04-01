package janggi.domain.game;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

import janggi.domain.Coordinate;
import janggi.domain.Piece;
import janggi.domain.Team;
import janggi.domain.board.Board;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;

public class BoardGame {

    private final Board board;
    private final PlayingTurn playingTurn;

    public BoardGame(final Board board, final PlayingTurn playingTurn) {
        this.board = board;
        this.playingTurn = playingTurn;
    }

    public void movePiece(Coordinate departure, Coordinate arrival) {
        if (isGameOver()) {
            throw new IllegalStateException("종료된 게임입니다.");
        }

        if (!isCurrentTeamPiece(departure)) {
            throw new IllegalArgumentException("같은 팀 기물만 선택할 수 있습니다.");
        }

        board.move(departure, arrival);
        playingTurn.toss();
    }

    public boolean isGameOver() {
        return playingTurn.isEnded() || board.isAnyGoongDead();
    }

    private boolean isCurrentTeamPiece(final Coordinate departure) {
        return board.findAt(departure)
            .map(p -> p.isTeam(playingTurn.currentTeam()))
            .orElse(true);
    }

    public PlayingTurn playingTurn() {
        return playingTurn;
    }

    public Team higherScoreTeam() {
        return scoreTeams().entrySet().stream()
            .max(Entry.comparingByValue())
            .map(Entry::getKey)
            .orElseThrow();
    }

    public Map<Team, Double> scoreTeams() {
        return Arrays.stream(Team.values())
            .collect(toMap(
                identity(),
                team -> board.sumScore(team) + team.getBonusScore()
            ));
    }

    public Map<Coordinate, Piece> allPieces() {
        return board.getPieces();
    }
}
