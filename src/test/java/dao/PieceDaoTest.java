package dao;

import static org.assertj.core.api.Assertions.assertThat;
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
import util.H2ConnectionFactory;

class PieceDaoTest {

    private PieceDao pieceDao;
    private Connection connection;

    @BeforeEach
    void setup() throws SQLException {
        H2ConnectionFactory h2Connection = new H2ConnectionFactory();
        h2Connection.initializeTable();
        connection = h2Connection.getConnection();
        connection.setAutoCommit(false);
        pieceDao = new PieceDao();
    }

    @AfterEach
    void rollback() throws SQLException {
        connection.rollback();
        connection.close();
    }


    @Test
    @DisplayName("저장된 보드의 기물과 좌표를 반환한다")
    void findBoardPiecesByGameIdTest() {
        // given
        long gameId = JanggiGameTestFixture.saveNewJanggiGame(connection);
        Map<Position, Piece> savePieces = Map.of(
                Position.of(4, 5), new Soldier(TeamType.CHO),
                Position.of(6, 5), new Horse(TeamType.CHO),
                Position.of(1, 2), new Chariot(TeamType.HAN),
                Position.of(2, 7), new Cannon(TeamType.HAN)
        );
        pieceDao.savePieces(savePieces, gameId, connection);

        // when
        Map<Position, Piece> pieces = pieceDao.findBoardPiecesByGameId(gameId, connection);

        // then
        assertAll(
                () -> assertThat(pieces.get(Position.of(4, 5)).isSameType(PieceType.SOLDIER)).isTrue(),
                () -> assertThat(pieces.get(Position.of(6, 5)).isSameType(PieceType.HORSE)).isTrue(),
                () -> assertThat(pieces.get(Position.of(1, 2)).isSameType(PieceType.CHARIOT)).isTrue(),
                () -> assertThat(pieces.get(Position.of(2, 7)).isSameType(PieceType.CANNON)).isTrue(),
                () -> assertThat(pieces.get(Position.of(4, 5)).isSameTeam(TeamType.CHO)).isTrue(),
                () -> assertThat(pieces.get(Position.of(6, 5)).isSameTeam(TeamType.CHO)).isTrue(),
                () -> assertThat(pieces.get(Position.of(1, 2)).isSameTeam(TeamType.HAN)).isTrue(),
                () -> assertThat(pieces.get(Position.of(2, 7)).isSameTeam(TeamType.HAN)).isTrue()
        );
    }

    @Test
    @DisplayName("저장된 기물의 좌표를 변경한다")
    void updatePieceTest() {
        // given
        long gameId = JanggiGameTestFixture.saveNewJanggiGame(connection);
        Position from = Position.of(3, 5);
        Position to = Position.of(3, 6);
        Map<Position, Piece> savePiece = Map.of(from, new Soldier(TeamType.HAN));
        pieceDao.savePieces(savePiece, gameId, connection);

        // when
        int result = pieceDao.updatePiecePosition(gameId, from, to, connection);

        // then
        Map<Position, Piece> pieces = pieceDao.findBoardPiecesByGameId(gameId, connection);
        assertAll(
                () -> assertThat(result).isEqualTo(1L),
                () -> assertThat(pieces.get(to).isSameType(PieceType.SOLDIER)).isTrue()
        );

    }

    @Test
    @DisplayName("저장된 기물을 제거한다")
    void removePieceTest() {
        // given
        long gameId = JanggiGameTestFixture.saveNewJanggiGame(connection);
        Map<Position, Piece> savePiece = Map.of(Position.of(3, 5), new Soldier(TeamType.HAN));
        pieceDao.savePieces(savePiece, gameId, connection);
        Position removePosition = Position.of(3, 5);

        // when
        int result = pieceDao.removePiece(gameId, removePosition, connection);

        // then
        Map<Position, Piece> pieces = pieceDao.findBoardPiecesByGameId(gameId, connection);
        assertAll(
                () -> assertThat(result).isEqualTo(1L),
                () -> assertThat(pieces).isEmpty()
        );
    }
}
