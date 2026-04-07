package infrastructure.repository;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Column;
import domain.board.Position;
import domain.board.Row;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Team;
import domain.setup.Arrangement;
import domain.state.GameStateName;
import infrastructure.DatabaseManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("H2GameRepository 테스트")
class H2GameRepositoryTest {

    private static DatabaseManager databaseManager;
    private static H2GameRepository gameRepository;
    private static H2GameRoomRepository roomRepository;
    private long roomId;

    @BeforeAll
    static void setUpSchema() {
        databaseManager = new DatabaseManager("jdbc:h2:mem:game_test;DB_CLOSE_DELAY=-1");
        databaseManager.initSchema();
        gameRepository = new H2GameRepository(databaseManager);
        roomRepository = new H2GameRoomRepository(databaseManager);
    }

    @BeforeEach
    void setUp() throws SQLException {
        try (Connection conn = databaseManager.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM board_pieces");
            stmt.execute("DELETE FROM games");
            stmt.execute("DELETE FROM game_rooms");
        }
        roomId = roomRepository.save("테스트 방").id();
    }

    @Test
    @DisplayName("게임을 생성하면 양수 id가 반환된다")
    void saveReturnsPositiveId() {
        long gameId = gameRepository.save(roomId);

        assertThat(gameId).isPositive();
    }

    @Test
    @DisplayName("updateState로 상태와 팀이 저장된다")
    void updateStatePersistsStateAndTeam() {
        long gameId = gameRepository.save(roomId);

        gameRepository.updateState(gameId, GameStateName.PLAYING, Team.CHO);

        var snapshot = gameRepository.findLatestByRoom(roomId);
        assertThat(snapshot).isPresent();
        assertThat(snapshot.get().stateName()).isEqualTo("PLAYING");
        assertThat(snapshot.get().currentTeam()).isEqualTo(Team.CHO);
    }

    @Test
    @DisplayName("updateArrangement로 한 팀 상차림이 저장된다")
    void updateArrangementPersistsHanArrangement() {
        long gameId = gameRepository.save(roomId);

        gameRepository.updateArrangement(gameId, Team.HAN, Arrangement.MASANGMASANG);

        var snapshot = gameRepository.findLatestByRoom(roomId);
        assertThat(snapshot.get().hanArrangement()).contains(Arrangement.MASANGMASANG);
        assertThat(snapshot.get().choArrangement()).isEmpty();
    }

    @Test
    @DisplayName("updateBoard로 기물 전체가 저장되고 복원된다")
    void updateBoardPersistsAndRestoresPieces() {
        long gameId = gameRepository.save(roomId);
        Map<Position, Piece> pieces = Map.of(
                new Position(Column.E, Row.ONE), new Piece(Team.HAN, PieceType.GENERAL),
                new Position(Column.E, Row.EIGHT), new Piece(Team.CHO, PieceType.GENERAL),
                new Position(Column.A, Row.ZERO), new Piece(Team.HAN, PieceType.CHARIOT)
        );

        gameRepository.updateBoard(gameId, pieces);

        var snapshot = gameRepository.findLatestByRoom(roomId);
        assertThat(snapshot.get().pieces()).hasSize(3);
        assertThat(snapshot.get().pieces().get(new Position(Column.E, Row.ONE)).getPieceType())
                .isEqualTo(PieceType.GENERAL);
    }

    @Test
    @DisplayName("updateBoard를 두 번 호출하면 마지막 보드만 남는다")
    void updateBoardOverwritesPreviousBoard() {
        long gameId = gameRepository.save(roomId);
        Map<Position, Piece> first = Map.of(
                new Position(Column.A, Row.ZERO), new Piece(Team.HAN, PieceType.CHARIOT)
        );
        Map<Position, Piece> second = Map.of(
                new Position(Column.E, Row.ONE), new Piece(Team.HAN, PieceType.GENERAL),
                new Position(Column.E, Row.EIGHT), new Piece(Team.CHO, PieceType.GENERAL)
        );

        gameRepository.updateBoard(gameId, first);
        gameRepository.updateBoard(gameId, second);

        var snapshot = gameRepository.findLatestByRoom(roomId);
        assertThat(snapshot.get().pieces()).hasSize(2);
    }

    @Test
    @DisplayName("게임이 없으면 findLatestByRoom은 empty를 반환한다")
    void findLatestByRoomReturnsEmptyWhenNoGame() {
        var snapshot = gameRepository.findLatestByRoom(roomId);

        assertThat(snapshot).isEmpty();
    }
}
