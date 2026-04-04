package repository.jdbc;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.entity.Piece;

class PieceJdbcRepositoryTest {

    private static final String CONFIG_FILE_NAME = "database.properties";


    private static final String CREATE_PIECE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS pieces (" +
            "piece_id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
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
    void save_single() throws SQLException {
        //given
        Piece testPiece = new Piece(null, "CHO", "JOL");

        //when
        Long savedEntityId = pieceRepository.save(testPiece);

        //then
        assertNotNull(savedEntityId);
    }

    @Test
    @DisplayName("복수의 PieceEntity에 대해서도 저장이 잘 된다")
    void save_multiple() throws SQLException {
        //given
        int expectCreatedIdSize = 3;
        List<Piece> pieceEntities = List.of(
                new Piece(null, "CHO", "JOL"),
                new Piece(null, "CHO", "JOL"),
                new Piece(null, "CHO", "JOL")
        );

        //when
        List<Long> savedEntitiesIds = pieceRepository.saveAll(pieceEntities);

        //then
        Assertions.assertEquals(expectCreatedIdSize, savedEntitiesIds.size());
    }

    @Test
    @DisplayName("하나의 PieceEntity를 잘 찾아온다")
    void find_single_success() throws SQLException {
        List<Piece> pieceEntities = List.of(
                new Piece(null, "CHO", "JOL"),
                new Piece(null, "CHO", "CHA"),
                new Piece(null, "CHO", "PO")
        );
        List<Long> savedEntitiesIds = pieceRepository.saveAll(pieceEntities);

        Piece findResult = pieceRepository.find(savedEntitiesIds.getFirst());

        assertNotNull(findResult.pieceId());
    }

    @Test
    @DisplayName("다수의 PieceEntity들을 잘 찾아온다")
    void find_all_success() throws SQLException {
        List<Piece> pieceEntities = List.of(
                new Piece(null, "CHO", "JOL"),
                new Piece(null, "CHO", "CHA"),
                new Piece(null, "CHO", "PO")
        );
        pieceRepository.saveAll(pieceEntities);

        List<Piece> result = pieceRepository.findAll();
        Assertions.assertEquals(3, result.size());
    }
}
