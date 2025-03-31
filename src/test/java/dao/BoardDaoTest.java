package dao;

import static org.assertj.core.api.Assertions.assertThat;

import dto.PieceDto;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BoardDaoTest {

    private final BoardDao boardDao = new BoardDao();

    @BeforeEach
    void setUp() {
        boardDao.deleteAll();
    }

    @Test
    void connection() {
        try (var connection = boardDao.getConnection()) {
            assertThat(connection).isNotNull();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void saveAndLoadAll() {
        List<PieceDto> pieces = List.of(
                new PieceDto("A", "1", "CHO", "SOLDIER"),
                new PieceDto("B", "2", "HAN", "ELEPHANT")
        );

        boardDao.saveAll(pieces);

        List<PieceDto> loaded = boardDao.loadAll();

        assertThat(loaded).hasSize(2);
        assertThat(loaded).extracting(PieceDto::column).containsExactly("A", "B");
        assertThat(loaded).extracting(PieceDto::row).containsExactly("1", "2");
        assertThat(loaded).extracting(PieceDto::country).containsExactly("CHO", "HAN");
        assertThat(loaded).extracting(PieceDto::pieceType).containsExactly("SOLDIER", "ELEPHANT");
        boardDao.deleteAll();
    }


}
