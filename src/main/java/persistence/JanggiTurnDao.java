package persistence;

import java.util.Optional;
import piece.player.Team;

public interface JanggiTurnDao {

    void addTurnScore(Team team, int turn, int score);

    Optional<Integer> findLatestTurnId();

    Optional<Integer> findLatestTurn();

    void deleteAll();
}
