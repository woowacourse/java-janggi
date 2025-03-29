package dao;

import dao.init.ConnectionFactory;
import domain.piece.character.PieceType;
import domain.piece.character.Team;
import domain.point.Point;
import fixture.TestMySQLConnectionFactory;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PieceDaoTest {

    private static final String GAME_ROOM_NAME = "room1";

    private final ConnectionFactory connectionFactory = new TestMySQLConnectionFactory();
    private Connection testConnection;
    private PieceDao pieceDAO;
    private GameRoomDao gameRoomDAO;

    @BeforeEach
    void setupConnection() throws SQLException {
        testConnection = connectionFactory.createConnection();
        testConnection.setAutoCommit(false);
        pieceDAO = new PieceDao();
        gameRoomDAO = new GameRoomDao();
        gameRoomDAO.insert(testConnection, new GameRoomEntity(GAME_ROOM_NAME, Team.CHO));
    }

    @AfterEach
    void rollbackConnection() throws SQLException {
        testConnection.rollback();
        testConnection.close();
    }

    @Test
    void row를_추가한다() {
        // given
        final PieceEntity piece = new PieceEntity(null, 1, 1, PieceType.CHA, Team.HAN, GAME_ROOM_NAME);

        // when
        boolean actual = pieceDAO.insert(testConnection, piece);

        // then
        Assertions.assertThat(actual).isTrue();
    }

    @Test
    void 방_이름으로_모든_row를_조회한다() {
        // given
        final PieceEntity piece1 = new PieceEntity(null, 1, 1, PieceType.CHA, Team.HAN, GAME_ROOM_NAME);
        final PieceEntity piece2 = new PieceEntity(null, 1, 2, PieceType.CHA, Team.HAN, GAME_ROOM_NAME);
        pieceDAO.insert(testConnection, piece1);
        pieceDAO.insert(testConnection, piece2);

        // when
        final List<PieceEntity> pieces = pieceDAO.findByGameRoomName(testConnection, GAME_ROOM_NAME);

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(pieces).hasSize(2);
            softly.assertThat(pieces).contains(piece1, piece2);
        });
    }

    @Test
    void 방_이름과_좌표로_조회하여_좌표_정보를_업데이트한다() {
        // given
        final Point oldPoint = Point.of(1, 1);
        final Point newPoint = Point.of(3, 3);
        final PieceEntity piece = new PieceEntity(null, oldPoint.row(), oldPoint.column(),
                PieceType.CHA, Team.HAN, GAME_ROOM_NAME);
        pieceDAO.insert(testConnection, piece);

        // when
        boolean actual = pieceDAO.updatePointByGameRoomNameAndPoint(testConnection, GAME_ROOM_NAME, oldPoint, newPoint);

        // then
        Assertions.assertThat(actual).isTrue();
    }

    @Test
    void 방_이름과_좌표로_조회하여_row를_삭제한다() {
        // given
        final Point point = Point.of(1, 1);
        final PieceEntity piece = new PieceEntity(null, point.row(), point.column(),
                PieceType.CHA, Team.HAN, GAME_ROOM_NAME);
        pieceDAO.insert(testConnection, piece);

        // when
        boolean actual = pieceDAO.deleteByGameRoomNameAndPoint(testConnection, GAME_ROOM_NAME, point);

        // then
        Assertions.assertThat(actual).isTrue();
    }
}