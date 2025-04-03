package janggi.repository.mysql;

import fixture.TestContainer;
import fixture.TestContainerSupport;
import janggi.GameId;
import janggi.piece.Piece;
import janggi.piece.PieceType;
import janggi.player.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
class PieceMysqlRepositoryTest extends TestContainerSupport {

    private final PieceMysqlRepository repository = new PieceMysqlRepository();
    private Connection conn;

    @BeforeEach
    void setUp() throws Exception {
        conn = TestContainer.getConnection();
    }

    @Test
    @DisplayName("기물을 저장하고 조회할 수 있다")
    void saveAndFindAllByGameId() {
        // given
        final GameId gameId = GameId.from(1L);
        final List<Piece> pieces = List.of(
                Piece.of(1, 2, PieceType.GENERAL.name(), Team.CHO.name()),
                Piece.of(3, 4, PieceType.SOLDIER.name(), Team.HAN.name())
        );

        // when
        repository.saveAll(conn, gameId, pieces);
        final List<Piece> found = repository.findAllByGameId(conn, gameId);

        // then
        assertThat(found).hasSize(2);
        assertThat(found).extracting(p -> p.getPosition().row().value()).containsExactlyInAnyOrder(1, 3);
        assertThat(found).extracting(Piece::getType).containsExactlyInAnyOrder(PieceType.GENERAL, PieceType.SOLDIER);
    }

    @Test
    @DisplayName("특정 게임 ID의 기물을 삭제할 수 있다")
    void deleteByGameId() {
        // given
        final GameId gameId = GameId.from(2L);
        final List<Piece> pieces = List.of(
                Piece.of(5, 6, PieceType.HORSE.name(), Team.CHO.name())
        );
        repository.saveAll(conn, gameId, pieces);

        // when
        repository.deleteByGameId(conn, gameId);
        final List<Piece> found = repository.findAllByGameId(conn, gameId);

        // then
        assertThat(found).isEmpty();
    }
}
