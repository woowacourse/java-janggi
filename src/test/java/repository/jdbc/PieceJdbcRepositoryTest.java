package repository.jdbc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.RepositoryErrorMessage;
import repository.entity.PieceEntity;

class PieceJdbcRepositoryTest {

    private static final String CONFIG_FILE_NAME = "database.properties";


    private static final String CREATE_PIECE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS piece (" +
            "piece_id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
            "piece_row INT NOT NULL, " +
            "piece_col INT NOT NULL, " +
            "team VARCHAR(10) NOT NULL, " +
            "type VARCHAR(10) NOT NULL)";

    private JdbcTemplate template = new JdbcTemplate(JdbcConnectionGenerator.create(CONFIG_FILE_NAME));
    private PieceJdbcRepository pieceRepository = new PieceJdbcRepository(template);

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
                () -> pieceRepository.save(testPieceEntity)
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
                () -> pieceRepository.saveAll(pieceEntities)
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
        pieceRepository.saveAll(pieceEntities);

        PieceEntity findResult = pieceRepository.find(targetRow, targetColumn);

        assertNotNull(findResult.pieceId());
    }

    @Test
    @DisplayName("다수의 PieceEntity들을 잘 찾아온다")
    void find_all_success() throws SQLException {
        List<PieceEntity> pieceEntities = List.of(
                new PieceEntity(null, 1, 1, "CHO", "JOL"),
                new PieceEntity(null, 2, 2, "CHO", "CHA"),
                new PieceEntity(null, 3, 3, "CHO", "PO")
        );
        pieceRepository.saveAll(pieceEntities);

        List<PieceEntity> result = pieceRepository.findAll();
        Assertions.assertEquals(3, result.size());
    }

    @Test
    @DisplayName("하나의 PieceEntity의 위치를 업데이트 잘 한다")
    void update_success() throws SQLException {
        PieceEntity origin = new PieceEntity(null, 1, 1, "CHO", "JOL");
        pieceRepository.save(origin);

        int testNewRow = 8;
        int testNewColumn = 8;

        pieceRepository.update(origin.row(), origin.col(), testNewRow, testNewColumn);

        PieceEntity result = pieceRepository.find(testNewRow, testNewColumn);
        assertNotNull(result);
    }

    @Test
    @DisplayName("PieceEntity 삭제 잘 한다")
    void delete_success() throws SQLException {
        int testRow = 8;
        int testColumn = 8;

        PieceEntity origin = new PieceEntity(null, testRow, testColumn, "CHO", "JOL");
        pieceRepository.save(origin);

        pieceRepository.delete(origin.row(), origin.col());

        assertThatThrownBy(
                () -> pieceRepository.find(testRow, testColumn)
        ).isInstanceOf(IllegalStateException.class)
                .hasMessage(RepositoryErrorMessage.NOT_FOUND.getMessage());
    }
}
