package infrastructure.dao;

import static org.assertj.core.api.Assertions.assertThat;

import infrastructure.H2Connector;
import infrastructure.entity.GameEntity;
import infrastructure.entity.PieceEntity;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PieceDaoTest {

    private static final DbConnector dbConnector = new H2Connector();
    private static final Connection connection = dbConnector.getConnection();

    private static GameDao gameDao;
    private static PieceDao pieceDao;

    @BeforeAll
    static void setUpDatabase() throws SQLException {
        gameDao = new GameDao(dbConnector);
        pieceDao = new PieceDao(dbConnector);

        String createGameTable = """
                CREATE TABLE IF NOT EXISTS games (
                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(64) NOT NULL UNIQUE,
                    current_turn VARCHAR(64) NOT NULL
                );
                """;

        String createPieceTable = """
                CREATE TABLE pieces (
                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                    piece_name VARCHAR(64) NOT NULL,
                    x INT NOT NULL,
                    y INT NOT NULL,
                    country VARCHAR(64) NOT NULL,
                    game_id BIGINT NOT NULL,
                    FOREIGN KEY (game_id) REFERENCES games(id)
                );
                """;

        connection.prepareStatement(createGameTable).execute();
        connection.prepareStatement(createPieceTable).execute();
    }

    @BeforeEach
    void clearTables() throws SQLException {
        connection.prepareStatement("DELETE FROM pieces").execute();
        connection.prepareStatement("DELETE FROM games").execute();
    }

    @AfterAll
    static void closeConnection() throws SQLException {
        connection.close();
    }

    @Test
    @DisplayName("기물을 저장하고 조회한다.")
    void saveAndFindTest() {
        GameEntity game = gameDao.save(new GameEntity("방1", "CHO"));

        PieceEntity pieceEntity1 = new PieceEntity("CHA", 0, 0, "CHO");
        PieceEntity pieceEntity2 = new PieceEntity("MA", 1, 0, "CHO");

        pieceDao.save(List.of(pieceEntity1, pieceEntity2), game);

        List<PieceEntity> pieceEntities = pieceDao.findByGame(game);
        assertThat(pieceEntities).hasSize(2);
    }

    @Test
    @DisplayName("게임 ID로 기물을 삭제한다.")
    void deleteByGameTest() {
        GameEntity game = gameDao.save(new GameEntity("삭제방", "HAN"));

        PieceEntity piece = new PieceEntity(null, "PO", 2, 2, "HAN");
        pieceDao.save(List.of(piece), game);

        pieceDao.deleteByGame(game);
        List<PieceEntity> remaining = pieceDao.findByGame(game);
        assertThat(remaining).isEmpty();
    }
}
