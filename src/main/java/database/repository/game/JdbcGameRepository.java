package database.repository.game;

import object.team.Country;

public interface JdbcGameRepository {

    void initializeTable();

    GameDto loadGame();

    void saveGame(Country turnTeam, boolean isEnd, int scoreHan, int scoreCho);
}
