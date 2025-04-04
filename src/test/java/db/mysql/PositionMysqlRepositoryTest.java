package db.mysql;

import static org.assertj.core.api.Assertions.assertThat;

import domain.piece.General;
import domain.piece.PieceType;
import domain.position.Point;
import domain.position.Position;
import fixture.TestContainer;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.PieceFactory;

class PositionMysqlRepositoryTest {

    private final PositionMysqlRepository repository = new PositionMysqlRepository();
    private Connection conn;

    @BeforeEach
    void setUp() throws SQLException {
        TestContainer.truncateAll();
        conn = TestContainer.getConnection();
    }


    @Test
    void 기물을_저장한다() {

        // given
        final Position position = Position.newInstance(Point.newInstance(1, 1),
                PieceFactory.createRedTeam(General::new));

        // when
        repository.savePosition(conn, position);
        final List<Position> positions = repository.getPositions(conn);

        // then
        SoftAssertions.assertSoftly(softly -> {
            assertThat(positions).hasSize(1);
            assertThat(positions).extracting(Position::getPieceType).containsExactlyInAnyOrder(PieceType.GENERAL);
        });

    }

    @Test
    void 기물_위치를_업데이트한다() {

        // given
        final Point fromPoint = Point.newInstance(1, 1);
        final Point toPoint = Point.newInstance(3, 3);
        final Position position = Position.newInstance(fromPoint, PieceFactory.createRedTeam(General::new));

        // when
        repository.savePosition(conn, position);
        repository.updatePosition(conn, fromPoint.value(), toPoint.value());
        final List<Position> positions = repository.getPositions(conn);

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(positions).hasSize(1);
            softly.assertThat(positions).extracting(Position::getPointValue).containsExactlyInAnyOrder(toPoint.value());
        });
    }

    @Test
    void 기물을_삭제한다() {

        // given
        final Point point = Point.newInstance(1, 1);
        final Position position = Position.newInstance(point, PieceFactory.createRedTeam(General::new));

        // when
        repository.savePosition(conn, position);
        repository.deletePosition(conn, position.getPointValue());
        final List<Position> positions = repository.getPositions(conn);

        // then
        assertThat(positions).isEmpty();
    }

    @Test
    void 모든_기물을_삭제한다() {

        // given
        final Point point1 = Point.newInstance(3, 3);
        final Position position1 = Position.newInstance(point1, PieceFactory.createRedTeam(General::new));
        final Point point2 = Point.newInstance(4, 1);
        final Position position2 = Position.newInstance(point2, PieceFactory.createRedTeam(General::new));

        // when
        repository.savePosition(conn, position1);
        repository.savePosition(conn, position2);
        repository.deleteAllPosition(conn);
        final List<Position> positions = repository.getPositions(conn);

        // then
        assertThat(positions).isEmpty();
    }

    @Test
    void 좌표를_업데이트_한다() {

        // given
        final Point fromPoint = Point.newInstance(2, 2);
        final Position position1 = Position.newInstance(fromPoint, PieceFactory.createRedTeam(General::new));
        final Point toPoint = Point.newInstance(1, 1);

        // when
        repository.savePosition(conn, position1);
        repository.updatePoint(conn, fromPoint.value(), toPoint.value());
        final List<Position> positions = repository.getPositions(conn);

        // then
        assertThat(positions).extracting(Position::getPointValue).containsExactlyInAnyOrder(toPoint.value());
    }
}
