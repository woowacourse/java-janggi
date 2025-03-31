package db.dao;

import static org.assertj.core.api.Assertions.assertThat;

import fixture.TestDBConnectionFixture;
import janggiGame.piece.Dynasty;
import janggiGame.piece.Piece;
import janggiGame.piece.PieceFactory;
import janggiGame.piece.Type;
import janggiGame.position.Position;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JanggiPieceDaoTest {
    private final JanggiPieceDao pieceDao = new JanggiPieceDao(TestDBConnectionFixture.getInstance());
    private final JanggiGameDao gameDao = new JanggiGameDao(TestDBConnectionFixture.getInstance());

    private Long gameId;

    JanggiPieceDaoTest() throws SQLException {
    }

    @BeforeEach
    void clearTables() throws SQLException {
        try (Connection connection = TestDBConnectionFixture.getInstance().getConnection()) {
            try (PreparedStatement ps1 = connection.prepareStatement("DELETE FROM janggi_piece");
                 PreparedStatement ps2 = connection.prepareStatement("DELETE FROM janggi_game")) {
                ps1.executeUpdate();
                ps2.executeUpdate();
            }
        }
        gameId = gameDao.save("HAN");
    }

    @DisplayName("기물을 저장하고 불러온다")
    @Test
    void saveAllAndFindByGameId() {
        // given
        Position position = Position.getInstanceBy(1, 1);
        Piece piece = PieceFactory.createPieceOf(Type.CHARIOT, Dynasty.HAN);
        Map<Position, Piece> pieces = Map.of(position, piece);

        // when
        pieceDao.saveAll(gameId, pieces);
        Map<Position, Piece> loaded = pieceDao.findByGameId(gameId);

        // then
        assertThat(loaded).hasSize(1);
        assertThat(loaded.get(position)).isNotNull();
        assertThat(loaded.get(position).getType()).isEqualTo(Type.CHARIOT);
    }

    @DisplayName("기물 위치를 수정한다")
    @Test
    void updatePiecePosition() {
        // given
        Position origin = Position.getInstanceBy(1, 1);
        Position destination = Position.getInstanceBy(3, 3);
        Piece piece = PieceFactory.createPieceOf(Type.CANNON, Dynasty.CHO);
        pieceDao.saveAll(gameId, Map.of(origin, piece));

        // when
        pieceDao.updatePiecePosition(gameId, origin, destination);
        Map<Position, Piece> loaded = pieceDao.findByGameId(gameId);

        // then
        assertThat(loaded)
                .doesNotContainKey(origin)
                .containsKey(destination);
        assertThat(loaded.get(destination).getType()).isEqualTo(Type.CANNON);
    }

    @DisplayName("기물을 삭제한다")
    @Test
    void deletePieceAt() {
        // given
        Position pos = Position.getInstanceBy(5, 5);
        Piece piece = PieceFactory.createPieceOf(Type.KING, Dynasty.CHO);
        pieceDao.saveAll(gameId, Map.of(pos, piece));

        // when
        pieceDao.deletePieceAt(gameId, pos);
        Map<Position, Piece> loaded = pieceDao.findByGameId(gameId);

        // then
        assertThat(loaded).doesNotContainKey(pos);
    }
}
