package dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import domain.piece.MovementRule;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.player.Player;
import domain.player.Team;
import domain.position.Position;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PieceDaoTest {

    private final TestDatabaseConnectionManager connectionManager = new TestDatabaseConnectionManager();
    private final PieceDao pieceDao = new PieceDao(connectionManager);

    @BeforeEach
    void setUp() {
        try (PreparedStatement statement = connectionManager.getConnection().prepareStatement("DELETE FROM piece")) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 테스트 데이터 초기화를 실패했습니다.");
        }
    }

    @Test
    void 기물을_저장한다() {
        // given
        Piece piece = new Piece(Position.of(1, 2), PieceType.HORSE, MovementRule.HORSE);
        Player player = new Player("name", Team.HAN);

        // when & then
        assertThatCode(() -> pieceDao.save(piece, player))
                .doesNotThrowAnyException();
    }

    @Test
    void 팀에_해당하는_모든_기물을_가져온다() {
        // given
        Piece piece1 = new Piece(Position.of(1, 2), PieceType.HORSE, MovementRule.HORSE);
        Piece piece2 = new Piece(Position.of(2, 2), PieceType.CANNON, MovementRule.CANNON);
        Piece piece3 = new Piece(Position.of(3, 2), PieceType.ELEPHANT, MovementRule.ELEPHANT);
        Piece piece4 = new Piece(Position.of(4, 2), PieceType.GENERAL, MovementRule.CHO_GENERAL);
        Piece piece5 = new Piece(Position.of(5, 2), PieceType.SOLDIER, MovementRule.CHO_SOLDIER);

        Player hanPlayer = new Player("name", Team.HAN);
        Player choPlayer = new Player("name", Team.CHO);

        pieceDao.save(piece1, hanPlayer);
        pieceDao.save(piece2, hanPlayer);
        pieceDao.save(piece3, hanPlayer);
        pieceDao.save(piece4, choPlayer);
        pieceDao.save(piece5, choPlayer);

        // when
        List<Piece> result = pieceDao.findAllByTeam(Team.HAN);

        // then
        assertThat(result).hasSize(3);
    }

    @Test
    void 기물의_위치를_업데이트한다() {
        // given
        Piece piece = new Piece(Position.of(1, 2), PieceType.HORSE, MovementRule.HORSE);
        Player player = new Player("name", Team.HAN);
        pieceDao.save(piece, player);

        // when & then
        assertThatCode(() -> pieceDao.updatePosition(piece, Position.of(2, 3)))
                .doesNotThrowAnyException();
    }

    @Test
    void 기물을_삭제한다() {
        // given
        Piece piece = new Piece(Position.of(1, 2), PieceType.HORSE, MovementRule.HORSE);
        Player player = new Player("name", Team.HAN);
        pieceDao.save(piece, player);

        // when & then
        assertThatCode(() -> pieceDao.delete(piece, Team.HAN))
                .doesNotThrowAnyException();
    }

    @Test
    void 기물_데이터를_초기화한다() {
        // given
        Piece piece = new Piece(Position.of(1, 2), PieceType.HORSE, MovementRule.HORSE);
        Player player = new Player("name", Team.HAN);
        pieceDao.save(piece, player);

        // when & then
        assertThatCode(pieceDao::clear)
                .doesNotThrowAnyException();
    }
}
