package janggi.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import janggi.dto.MoveDto;
import janggi.fixture.TestMoveJdbcDao;
import janggi.position.Column;
import janggi.position.Position;
import janggi.position.Row;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MoveJdbcDaoTest {

    private TestMoveJdbcDao moveJdbcDao;

    @BeforeEach
    void setUp() {
        moveJdbcDao = new TestMoveJdbcDao();
    }

    @AfterEach
    void endUp() throws SQLException {
        moveJdbcDao.rollBack();
    }

    @DisplayName("게임 id로 조회한 게임의 움직인 기록들을 찾는다.")
    @Test
    void testSelectAllHistory() {
        // given
        // when
        // then
        assertThat(moveJdbcDao.selectAllHistory(1)).isEqualTo(
                List.of(
                        new MoveDto(new Position(Row.NINE, Column.TWO), new Position(Row.SEVEN, Column.THREE)),
                        new MoveDto(new Position(Row.THREE, Column.FOUR), new Position(Row.FOUR, Column.FOUR)),
                        new MoveDto(new Position(Row.SEVEN, Column.ONE), new Position(Row.SEVEN, Column.FOUR))
                )
        );
    }

    @DisplayName("움직인 기록을 저장한다.")
    @Test
    void testSaveHistory() {
        // given
        // when
        // then
        final MoveDto moveDto = new MoveDto(new Position(Row.ONE, Column.FOUR), new Position(Row.ZERO, Column.FOUR));
        assertThatCode(() -> moveJdbcDao.saveHistory(moveDto, 2)).doesNotThrowAnyException();
    }
}
