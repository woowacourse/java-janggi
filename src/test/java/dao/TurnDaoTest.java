package dao;

import static org.assertj.core.api.Assertions.assertThat;

import dto.TurnDto;
import java.sql.SQLException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TurnDaoTest {

    private final TurnDao turnDao = new TurnDao();

    @BeforeEach
    void setUp() {
        turnDao.deleteAll();
    }

    @Test
    void connection() {
        try (var connection = turnDao.getConnection()) {
            assertThat(connection).isNotNull();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void saveAndLoadTurn() {
        TurnDto dto = new TurnDto("HAN");
        turnDao.saveTurnCountry(dto);

        TurnDto loaded = turnDao.loadTurnCountry();

        assertThat(loaded.country()).isEqualTo("HAN");
        turnDao.deleteAll();
    }
}
