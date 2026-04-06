package repository;

import domain.Team;

public interface GameRepository {
    void save(Team currentTurn, boolean isFinished, double choScore, double hanScore);

    Team findCurrentTurn();

    void deleteAll();

    boolean isNotFinished();
}
