package object.team;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import object.coordinate.Coordinate;
import object.piece.Piece;

public class Teams {

    private final List<Team> teams;

    public Teams(Team... teams) {
        this.teams = List.of(teams);
    }

    public boolean hasPiece(Coordinate coordinate) {
        return teams.stream()
                .anyMatch(team -> team.isMyPiece(coordinate));
    }

    public Piece findPiece(Coordinate coordinate) {
        return findTeamByCoordinate(coordinate)
                .map(team -> team.getPiece(coordinate))
                .orElseThrow(() -> new IllegalStateException("해당 좌표에 기물이 없습니다."));
    }

    public void movePiece(Country country, Piece piece, Coordinate departure, Coordinate arrival) {
        Team target = findTeamByCountry(country);
        Optional<Team> enemyOpt = findTeamByCoordinate(arrival);

        if (!target.isMyPiece(departure)) {
            throw new IllegalStateException("다른 팀의 말은 움직일 수 없습니다.");
        }

        if (enemyOpt.isPresent()) {
            Team enemy = enemyOpt.get();
            if (enemy.isSameCountry(target)) {
                throw new IllegalStateException("도착 좌표에 같은 팀 말이 있습니다.");
            }
            target.addScore(findPiece(arrival).getScore());
            enemy.removePiece(arrival);
        }

        target.removePiece(departure);
        target.putPiece(arrival, piece);
    }

    public boolean isEnd() {
        return teams.stream()
                .anyMatch(Team::isGoongDead);
    }

    public Team judgeWinner() {
        return teams.stream()
                .max(Comparator.comparingInt(Team::getScore))
                .orElseThrow(() -> new IllegalStateException("팀이 존재하지 않습니다."));
    }

    private Team findTeamByCountry(Country country) {
        return teams.stream()
                .filter(team -> team.isSameCountry(country))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 팀입니다."));
    }

    private Optional<Team> findTeamByCoordinate(Coordinate coordinate) {
        return teams.stream()
                .filter(team -> team.isMyPiece(coordinate))
                .findFirst();
    }

    public Map<Coordinate, Piece> getUnmodifiablePieces() {
        return teams.stream()
                .flatMap(team -> team.getPieces().entrySet().stream())
                .collect(Collectors.collectingAndThen(
                        Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue),
                        Collections::unmodifiableMap
                ));
    }

    public Map<Country, Integer> getUnmodifiableScores() {
        return teams.stream()
                .collect(Collectors.toMap(
                        Team::getCountry,
                        Team::getScore
                ));
    }
}
