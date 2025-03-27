package dao;

import dao.init.DatabaseSetting;
import domain.piece.character.PieceType;
import domain.piece.character.Team;
import domain.point.Point;
import fixture.DatabaseConnectionFixture;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PieceDAOTest {

    private static final String GAME_ROOM_NAME = "room1";

    private Connection testConnection;
    private PieceDAO pieceDAO;
    private GameRoomDAO gameRoomDAO;

    @BeforeEach
    void setupConnection() throws SQLException {
        testConnection = DatabaseConnectionFixture.getTestConnection();
        testConnection.setAutoCommit(false);
        DatabaseSetting.settingTable(testConnection);
        pieceDAO = new PieceDAO(testConnection);
        gameRoomDAO = new GameRoomDAO(testConnection);
        gameRoomDAO.insert(new GameRoomEntity(GAME_ROOM_NAME, Team.CHO));
    }

    @AfterEach
    void rollbackConnection() throws SQLException {
        testConnection.rollback();
        testConnection.close();
    }

    @Test
    void PieceEntity를_추가한다() {
        // given
        final PieceEntity piece = new PieceEntity(1, 1, PieceType.CHA, Team.HAN, GAME_ROOM_NAME);

        // when
        boolean actual = pieceDAO.insert(piece);

        // then
        Assertions.assertThat(actual).isTrue();
    }

    @Test
    void PieceEntity를_불러온다() {
        // given
        final PieceEntity piece1 = new PieceEntity(1, 1, PieceType.CHA, Team.HAN, GAME_ROOM_NAME);
        final PieceEntity piece2 = new PieceEntity(1, 2, PieceType.CHA, Team.HAN, GAME_ROOM_NAME);
        pieceDAO.insert(piece1);
        pieceDAO.insert(piece2);

        // when
        final List<PieceEntity> pieces = pieceDAO.findByGameRoomName(GAME_ROOM_NAME);

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(pieces).hasSize(2);
            softly.assertThat(pieces).contains(piece1, piece2);
        });
    }

    @Test
    void PieceEntity를_업데이트한다() {
        // given
        final Point oldPoint = Point.of(1, 1);
        final Point newPoint = Point.of(3, 3);
        final PieceEntity piece = new PieceEntity(oldPoint.row(), oldPoint.column(),
                PieceType.CHA, Team.HAN, GAME_ROOM_NAME);
        pieceDAO.insert(piece);

        // when
        boolean actual = pieceDAO.updatePointByGameRoomNameAndPoint(GAME_ROOM_NAME, oldPoint, newPoint);

        // then
        Assertions.assertThat(actual).isTrue();
    }

    @Test
    void PieceEntity를_삭제한다() {
        // given
        final Point point = Point.of(1, 1);
        final PieceEntity piece = new PieceEntity(point.row(), point.column(),
                PieceType.CHA, Team.HAN, GAME_ROOM_NAME);
        pieceDAO.insert(piece);

        // when
        boolean actual = pieceDAO.deleteByGameRoomNameAndPoint(GAME_ROOM_NAME, point);
        // then
        Assertions.assertThat(actual).isTrue();
    }
}