package repository;

import config.DatabaseConfig;
import config.DatabaseInitializer;
import model.board.Country;
import model.pieces.PieceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class JdbcGameRepositoryTest {
    @BeforeEach
    void setUp() throws SQLException {
        DatabaseInitializer.initialize();
        clearTables();
    }

    @Test
    void 게임_메타_정보를_game_테이블에_저장할_수_있다() throws SQLException {
        GameRepository repository = new JdbcGameRepository();

        SavedGame savedGame = new SavedGame(
                Country.CHO,
                false,
                null,
                List.of()
        );

        repository.save(savedGame);

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT turn,finished,winner FROM game"
             );
             ResultSet resultSet = statement.executeQuery();
        ) {
            assertThat(resultSet.next()).isTrue();
            assertThat(resultSet.getString("turn")).isEqualTo("CHO");
            assertThat(resultSet.getBoolean("finished")).isFalse();
            assertThat(resultSet.getString("winner")).isNull();
            assertThat(resultSet.next()).isFalse();
        }
    }

    @Test
    void 기물_목록을_piece_테이블에_저장할_수_있다() throws SQLException {
        GameRepository repository = new JdbcGameRepository();

        SavedGame savedGame = new SavedGame(
                Country.CHO,
                false,
                null,
                List.of(
                        new SavedPiece(10, 1, Country.CHO, PieceType.CHARIOT),
                        new SavedPiece(2, 5, Country.HAN, PieceType.GENERAL)
                )
        );

        repository.save(savedGame);

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT game_id, row_number, column_number, country,piece_type FROM piece ORDER BY id"
             );
             ResultSet resultSet = statement.executeQuery();
        ) {
            assertThat(resultSet.next()).isTrue();
            assertThat(resultSet.getLong("game_id")).isPositive();
            assertThat(resultSet.getInt("row_number")).isEqualTo(10);
            assertThat(resultSet.getInt("column_number")).isEqualTo(1);
            assertThat(resultSet.getString("country")).isEqualTo("CHO");
            assertThat(resultSet.getString("piece_type")).isEqualTo("CHARIOT");

            assertThat(resultSet.next()).isTrue();
            assertThat(resultSet.getLong("game_id")).isPositive();
            assertThat(resultSet.getInt("row_number")).isEqualTo(2);
            assertThat(resultSet.getInt("column_number")).isEqualTo(5);
            assertThat(resultSet.getString("country")).isEqualTo("HAN");
            assertThat(resultSet.getString("piece_type")).isEqualTo("GENERAL");

            assertThat(resultSet.next()).isFalse();
        }
    }

    private void clearTables() throws SQLException {
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement pieceStatement = connection.prepareStatement("DELETE FROM piece");
             PreparedStatement gameStatement = connection.prepareStatement("DELETE FROM game");
        ) {
            pieceStatement.executeUpdate();
            gameStatement.executeUpdate();
        }
    }
}
