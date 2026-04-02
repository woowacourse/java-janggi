package repository.jdbc;

import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.entity.PieceEntity;

class PieceJdbcRepositoryTest {

    private static final String CONFIG_FILE_NAME = "database.properties";

    private static final String INSERT_PIECE_SQL = "INSERT INTO piece(piece_row, piece_col, team, type ) values(?, ?, ?, ?)";
    private static final String CREATE_PIECE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS piece (" +
            "piece_id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
            "piece_row INT NOT NULL, " +
            "piece_col INT NOT NULL, " +
            "team VARCHAR(10) NOT NULL, " +
            "type VARCHAR(10) NOT NULL)";

    private JdbcTemplate template = new JdbcTemplate(JdbcConnectionGenerator.create(CONFIG_FILE_NAME));
    private PieceJdbcRepository repository = new PieceJdbcRepository(template);

    @BeforeEach
    void setUp() throws SQLException {
        template.executeCommand(CREATE_PIECE_TABLE_SQL);
    }

    @AfterEach
    void clearAll() throws SQLException {
        String sql = "DROP ALL OBJECTS";
        template.executeCommand(sql);
    }


    @Test
    @DisplayName("단일 PieceEntity에 대해 잘 저장한다")
    void save_single() {
        //given
        PieceEntity testPieceEntity = new PieceEntity(null, 1, 1, "CHO", "JOL");

        //when
        Assertions.assertDoesNotThrow(
                () -> repository.save(testPieceEntity)
        );
    }

    @Test
    @DisplayName("복수의 PieceEntity에 대해서도 저장이 잘 된다")
    void save_multiple() {
        //given
        List<PieceEntity> pieceEntities = List.of(
                new PieceEntity(null, 1, 1, "CHO", "JOL"),
                new PieceEntity(null, 2, 2, "CHO", "JOL"),
                new PieceEntity(null, 3, 3, "CHO", "JOL")
        );

        //when
        Assertions.assertDoesNotThrow(
                () -> repository.saveAll(pieceEntities)
        );
    }

    @Test
    @DisplayName("하나의 PieceEntity를 잘 찾아온다")
    void find_single_success() throws SQLException {
        int targetRow = 1;
        int targetColumn = 1;
        List<PieceEntity> pieceEntities = List.of(
                new PieceEntity(null, 1, 1, "CHO", "JOL"),
                new PieceEntity(null, 2, 2, "CHO", "CHA"),
                new PieceEntity(null, 3, 3, "CHO", "PO")
        );
        repository.saveAll(pieceEntities);

        PieceEntity findResult = repository.find(targetRow, targetColumn);

        Assertions.assertNotNull(findResult.pieceId());
    }

    @Test
    @DisplayName("다수의 PieceEntity들을 잘 찾아온다")
    void find_all_success() throws SQLException {
        List<PieceEntity> pieceEntities = List.of(
                new PieceEntity(null, 1, 1, "CHO", "JOL"),
                new PieceEntity(null, 2, 2, "CHO", "CHA"),
                new PieceEntity(null, 3, 3, "CHO", "PO")
        );
        repository.saveAll(pieceEntities);

        List<PieceEntity> result = repository.findAll();
        Assertions.assertEquals(3, result.size());
    }
}
