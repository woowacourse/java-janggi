package repository;

import static org.assertj.core.api.Assertions.assertThat;

import config.DatabaseInitializer;
import domain.board.Board;
import domain.board.Position;
import domain.game.JanggiGame;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Team;
import domain.state.FinishedState;
import domain.state.HanPlayingState;
import dto.JanggiGameDto;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JdbcJanggiGameRepositoryTest {

    JdbcJanggiGameRepository janggiGameRepository;

    @BeforeEach
    void setUp() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        dataSource.setUser("sa");
        dataSource.setPassword("");

        dropTable(dataSource);
        DatabaseInitializer.initialize(dataSource);

        JdbcGameDao jdbcGameDao = new JdbcGameDao();
        JdbcPieceDao jdbcPieceDao = new JdbcPieceDao();

        janggiGameRepository = new JdbcJanggiGameRepository(dataSource, jdbcGameDao, jdbcPieceDao);
    }

    void dropTable(DataSource dataSource) {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS piece");
            statement.execute("DROP TABLE IF EXISTS game");
        } catch (SQLException e) {
            throw new RuntimeException("테이블 초기화 실패");
        }
    }

    @Test
    @DisplayName("save 테스트")
    void saveTest() {
        // given
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(Position.of(2, 5), Piece.choPieceOf(PieceType.GENERAL));
        pieces.put(Position.of(9, 2), Piece.hanPieceOf(PieceType.GENERAL));

        Board board = Board.init(pieces);

        JanggiGame janggiGame = JanggiGame.of(board, new HanPlayingState());

        // when
        Long savedId = janggiGameRepository.save(janggiGame);

        // then
        assertThat(savedId).isNotNull();

        Optional<JanggiGame> foundGame = janggiGameRepository.findById(savedId);

        assertThat(foundGame).isPresent();
        assertThat(foundGame.get().getPieces()).hasSize(2);
        assertThat(foundGame.get().getCurrentTeam()).isEqualTo(janggiGame.getCurrentTeam());
    }

    @Test
    @DisplayName("findById 테스트")
    void findByIdTest() {
        // given
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(Position.of(1, 1), Piece.choPieceOf(PieceType.GENERAL));
        pieces.put(Position.of(2, 2), Piece.hanPieceOf(PieceType.GENERAL));

        Board board = Board.init(pieces);
        JanggiGame game = JanggiGame.of(board, new HanPlayingState());
        Long savedId = janggiGameRepository.save(game);

        // when
        Optional<JanggiGame> foundGame = janggiGameRepository.findById(savedId);

        // then
        assertThat(foundGame).isPresent();
        assertThat(foundGame.get().getPieces()).hasSize(2);
        assertThat(foundGame.get().getCurrentTeam()).isEqualTo(game.getCurrentTeam());
    }

    @Test
    @DisplayName("종료된 게임 findById 테스트")
    void findByIdFinishedGameTest() {
        // given
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(Position.of(1, 1), Piece.choPieceOf(PieceType.GENERAL));
        pieces.put(Position.of(2, 2), Piece.hanPieceOf(PieceType.GENERAL));

        Board board = Board.init(pieces);
        JanggiGame originalGame = JanggiGame.of(board, new FinishedState(Team.HAN));
        Long savedId = janggiGameRepository.save(originalGame);

        // when
        Optional<JanggiGame> foundGame = janggiGameRepository.findById(savedId);

        // then
        assertThat(foundGame).isPresent();
        assertThat(foundGame.get().isFinished()).isTrue();
        assertThat(foundGame.get().getWinnerTeam()).isEqualTo(Team.HAN);
    }

    @Test
    @DisplayName("존재하지 않는 ID 조회 테스트")
    void findByIdNotFoundTest() {
        // when
        Optional<JanggiGame> foundGame = janggiGameRepository.findById(999L);

        // then
        assertThat(foundGame).isEmpty();
    }

    @Test
    @DisplayName("findAll 테스트")
    void findAllTest() {
        // given
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(Position.of(1, 1), Piece.choPieceOf(PieceType.GENERAL));
        Board board = Board.init(pieces);

        janggiGameRepository.save(JanggiGame.of(board, new HanPlayingState()));
        janggiGameRepository.save(JanggiGame.of(board, new HanPlayingState()));

        // when
        List<JanggiGameDto> games = janggiGameRepository.findAll();

        // then
        assertThat(games).hasSize(2);
        assertThat(games.get(0).id()).isEqualTo(1L);
        assertThat(games.get(1).id()).isEqualTo(2L);
    }

    @Test
    @DisplayName("update 테스트")
    void updateTest() {
        // given
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(Position.of(1, 1), Piece.choPieceOf(PieceType.GENERAL));
        Board board = Board.init(pieces);
        JanggiGame game = JanggiGame.of(board, new HanPlayingState());
        Long savedId = janggiGameRepository.save(game);

        // when
        Map<Position, Piece> newPieces = new HashMap<>();
        newPieces.put(Position.of(3, 3), Piece.choPieceOf(PieceType.GENERAL));
        Board newBoard = Board.init(newPieces);
        JanggiGame updatedGame = JanggiGame.of(newBoard, new FinishedState(Team.CHO));

        janggiGameRepository.update(savedId, updatedGame);

        // then
        Optional<JanggiGame> foundGame = janggiGameRepository.findById(savedId);
        assertThat(foundGame).isPresent();
        assertThat(foundGame.get().isFinished()).isTrue();
        assertThat(foundGame.get().getWinnerTeam()).isEqualTo(Team.CHO);
        assertThat(foundGame.get().getPieces()).hasSize(1);
        assertThat(foundGame.get().getPieces()).containsKey(Position.of(3, 3));
    }
}
