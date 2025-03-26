package janggi.service;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

import janggi.domain.Coordinate;
import janggi.domain.Piece;
import janggi.domain.Team;
import janggi.domain.board.Board;
import janggi.repository.Repository;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;

public class GameService {

    private final Repository repository;
    private final Board board;
    private final PlayingTurn playingTurn;

    public GameService(
        final Board board,
        final Repository repository
    ) {
        this.board = board;
        this.repository = repository;
        this.playingTurn = repository.getTurn();
    }

    public void movePiece(final Coordinate departure, final Coordinate arrival) {
        Team team = playingTurn.currentTeam();
        checkDepartureIsMyPiece(team, board, departure);

        board.move(departure, arrival);

        repository.deleteByCoordinate(arrival);
        repository.update(departure, arrival);

        playingTurn.toss();
        repository.updateTurn(playingTurn);
    }

    public boolean isGameOver() {
        return playingTurn.isEnded() || board.isAnyGoongDead();
    }

    public void clearGame() {
        repository.clear();
        repository.updateTurn(new PlayingTurn());
    }

    public Team higherScoreTeam() {
        return scoreTeams().entrySet().stream()
            .max(Entry.comparingByValue())
            .map(Entry::getKey)
            .orElseThrow();
    }

    public Team currentTurn() {
        return playingTurn.currentTeam();
    }

    public Map<Coordinate, Piece> allPieces() {
        return board.getPieces();
    }

    public Map<Team, Double> scoreTeams() {
        return Arrays.stream(Team.values())
            .collect(toMap(
                identity(),
                team -> board.sumScore(team) + team.getBonusScore()
            ));
    }

    private void checkDepartureIsMyPiece(Team team, final Board board, final Coordinate departure) {
        boolean selectsMyTeam = board.findAt(departure)
            .map(p -> p.isTeam(team))
            .orElse(true);
        if (!selectsMyTeam) {
            throw new IllegalArgumentException("같은 팀 기물만 선택할 수 있습니다.");
        }
    }
}
