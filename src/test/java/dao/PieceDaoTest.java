package dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertAll;

import dao.fixture.JanggiGameTestFixture;
import domain.TeamType;
import domain.piece.Cannon;
import domain.piece.Chariot;
import domain.piece.Horse;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Soldier;
import domain.position.Position;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.DBConnectionUtil;

class PieceDaoTest {

    private PieceDao pieceDao;
    private Connection connection;

    @BeforeEach
    void setup() throws SQLException {
        connection = DBConnectionUtil.getConnection();
        connection.setAutoCommit(false);
        pieceDao = new PieceDao(connection);
    }

    @AfterEach
    void rollback() throws SQLException {
        connection.rollback();
        connection.close();
    }

    @Test
    @DisplayName("기물의 좌표와 정보를 저장한다")
    void savePieceTest() throws SQLException {
        // given
        Piece piece = new Soldier(TeamType.HAN);
        Position position = Position.of(3, 2);
        Long gameId = JanggiGameTestFixture.saveNewJanggiGame(connection);

        // when & then
        assertThatCode(() -> pieceDao.savePiece(piece, position, gameId))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("저장된 보드의 기물과 좌표를 반환한다")
    void findBoardPiecesByGameIdTest() throws SQLException {
        // given
        long gameId = JanggiGameTestFixture.saveNewJanggiGame(connection);
        Piece soldier = new Soldier(TeamType.CHO);
        Position soldierPosition = Position.of(4, 5);
        pieceDao.savePiece(soldier, soldierPosition, gameId);

        Piece horse = new Horse(TeamType.CHO);
        Position horsePosition = Position.of(6, 5);
        pieceDao.savePiece(horse, horsePosition, gameId);

        Piece chariot = new Chariot(TeamType.HAN);
        Position chariotPosition = Position.of(1, 2);
        pieceDao.savePiece(chariot, chariotPosition, gameId);

        Piece cannon = new Cannon(TeamType.HAN);
        Position cannonPosition = Position.of(2, 7);
        pieceDao.savePiece(cannon, cannonPosition, gameId);

        // when
        Map<Position, Piece> pieces = pieceDao.findBoardPiecesByGameId(gameId);

        // then
        assertAll(
                () -> assertThat(pieces.get(Position.of(4, 5)).isSameType(PieceType.SOLDIER)),
                () -> assertThat(pieces.get(Position.of(6, 5)).isSameType(PieceType.HORSE)),
                () -> assertThat(pieces.get(Position.of(1, 2)).isSameType(PieceType.CHARIOT)),
                () -> assertThat(pieces.get(Position.of(2, 7)).isSameType(PieceType.CANNON)),
                () -> assertThat(pieces.get(Position.of(4, 5)).isSameTeam(TeamType.CHO)),
                () -> assertThat(pieces.get(Position.of(6, 5)).isSameTeam(TeamType.CHO)),
                () -> assertThat(pieces.get(Position.of(1, 2)).isSameTeam(TeamType.HAN)),
                () -> assertThat(pieces.get(Position.of(2, 7)).isSameTeam(TeamType.HAN))
        );
    }

    @Test
    @DisplayName("저장된 기물의 좌표를 변경한다")
    void updatePieceTest() throws SQLException {
        // given
        long gameId = JanggiGameTestFixture.saveNewJanggiGame(connection);
        Piece savePiece = new Soldier(TeamType.HAN);
        Position from = Position.of(3, 5);
        Position to = Position.of(3, 6);
        pieceDao.savePiece(savePiece, from, gameId);

        // when
        int result = pieceDao.updatePiecePosition(gameId, from, to);

        // then
        Map<Position, Piece> pieces = pieceDao.findBoardPiecesByGameId(gameId);
        assertAll(
                () -> assertThat(result).isEqualTo(1L),
                () -> assertThat(pieces.get(to).isSameType(PieceType.SOLDIER)).isTrue()
        );

    }

    @Test
    @DisplayName("저장된 기물을 제거한다")
    void removePieceTest() throws SQLException {
        // given
        long gameId = JanggiGameTestFixture.saveNewJanggiGame(connection);
        Piece savePiece = new Soldier(TeamType.HAN);
        Position position = Position.of(3, 5);
        pieceDao.savePiece(savePiece, position, gameId);

        // when
        int result = pieceDao.removePiece(gameId, position);

        // then
        Map<Position, Piece> pieces = pieceDao.findBoardPiecesByGameId(gameId);
        assertAll(
                () -> assertThat(result).isEqualTo(1L),
                () -> assertThat(pieces).isEmpty()
        );
    }
}
