package persistence;

import static org.assertj.core.api.Assertions.assertThat;

import domain.GameSnapshot;
import domain.Piece;
import domain.PieceType;
import domain.Position;
import domain.TeamColor;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JdbcGameStateRepositoryTest {

    private Connection connection;
    private JdbcGameStateRepository repository;

    @BeforeEach
    void setUp() throws SQLException, IOException {
        connection = DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        SchemaInitializer.apply(connection);
        repository = new JdbcGameStateRepository(() -> connection);
    }

    @AfterEach
    void tearDown() throws SQLException {
        connection.close();
    }

    @Test
    void 저장된_게임이_없으면_빈_결과를_반환한다() {
        Optional<SavedGameState> loaded = repository.load();

        assertThat(loaded).isEmpty();
    }

    @Test
    void 게임_상태를_저장하고_다시_불러온다() {
        GameSnapshot snapshot = sampleSnapshot();
        TeamColor turn = TeamColor.HAN;

        repository.save(snapshot, turn);
        Optional<SavedGameState> loaded = repository.load();

        assertThat(loaded).isPresent();
        assertThat(loaded.get().currentTurn()).isEqualTo(turn);
        assertSnapshotsEqual(snapshot, loaded.get().snapshot());
    }

    @Test
    void 다시_저장하면_이전_기물_배치는_남지_않는다() {
        GameSnapshot first = GameSnapshot.from(
                Map.of(Position.of(0, 0), Piece.of(TeamColor.CHO, PieceType.ROOK)));
        GameSnapshot second = GameSnapshot.from(
                Map.of(Position.of(9, 8), Piece.of(TeamColor.HAN, PieceType.KING)));

        repository.save(first, TeamColor.CHO);
        repository.save(second, TeamColor.HAN);
        Optional<SavedGameState> loaded = repository.load();

        assertThat(loaded).isPresent();
        assertThat(loaded.get().snapshot().pieces()).hasSize(1);
        assertThat(loaded.get().snapshot().pieces()).containsKey(Position.of(9, 8));
    }

    private static GameSnapshot sampleSnapshot() {
        Map<Position, Piece> pieces =
                Map.of(
                        Position.of(0, 0), Piece.of(TeamColor.CHO, PieceType.ROOK),
                        Position.of(9, 8), Piece.of(TeamColor.HAN, PieceType.KING));
        return GameSnapshot.from(pieces);
    }

    private static void assertSnapshotsEqual(GameSnapshot expected, GameSnapshot actual) {
        assertThat(actual.pieces()).hasSameSizeAs(expected.pieces());
        for (Map.Entry<Position, Piece> entry : expected.pieces().entrySet()) {
            Piece actualPiece = actual.pieces().get(entry.getKey());
            assertThat(actualPiece).isNotNull();
            assertThat(actualPiece.getTeamColor()).isEqualTo(entry.getValue().getTeamColor());
            assertThat(actualPiece.getPieceType()).isEqualTo(entry.getValue().getPieceType());
        }
    }
}
