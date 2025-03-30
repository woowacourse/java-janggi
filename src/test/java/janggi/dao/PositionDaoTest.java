package janggi.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import janggi.domain.piece.direction.Position;
import java.util.Optional;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PositionDaoTest {

    private final PositionDao positionDao = new PositionDao();
    private final PieceDao pieceDao = new PieceDao();

    @BeforeAll
    static void setUpClass() {
        DatabaseConnection.setTestMode(true);
    }

    @BeforeEach
    void setUp() {
        pieceDao.deleteAllPieces();
        positionDao.deleteAllPositions();
    }

    @AfterAll
    static void tearDownClass() {
        DatabaseConnection.setTestMode(false);
    }

    @DisplayName("데이터베이스에 포지션을 추가한다.")
    @Test
    void addPositionTest() {

        // given
        final Position position = new Position(1, 1);

        // when & then
        assertThatCode(() -> {
            positionDao.addPosition(position);
        }).doesNotThrowAnyException();
    }

    @DisplayName("포지션을 찾는다.")
    @Test
    void findPositionTest() {

        // given
        final Position position = new Position(1, 1);

        // when
        positionDao.addPosition(position);
        final Optional<Integer> positionId = positionDao.findIdByPosition(position);
        final Position findPosition = positionDao.findPositionById(positionId.get());

        // then
        assertThat(findPosition).isEqualTo(position);
    }

    @DisplayName("포지션을 삭제한다.")
    @Test
    void deletePositionTest() {

        // given
        final Position position = new Position(1, 1);
        positionDao.addPosition(position);

        // when
        positionDao.deletePosition(position);

        // then
        assertThat(positionDao.findIdByPosition(position)).isEmpty();
    }
}
