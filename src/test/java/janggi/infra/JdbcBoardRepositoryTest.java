package janggi.infra;

import janggi.domain.JanggiGame;
import janggi.domain.board.Board;
import janggi.domain.piece.Implementation.Cha;
import janggi.domain.piece.Implementation.Ma;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.point.Point;
import janggi.domain.status.ChoTurn;
import janggi.domain.status.Team;
import janggi.infra.dao.GameRoomDao;
import janggi.infra.dao.PiecesDao;
import janggi.infra.datasource.H2DataSourceFactory;
import janggi.infra.transaction.ConnectionContext;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JdbcBoardRepositoryTest {

    private JdbcBoardRepository repository;
    private Connection connection;

    @BeforeEach
    void setUp() throws SQLException {
        DataSource dataSource = H2DataSourceFactory.create();
        repository = new JdbcBoardRepository(new GameRoomDao(), new PiecesDao());
        connection = dataSource.getConnection();
        connection.setAutoCommit(false);
    }

    @AfterEach
    void tearDown() throws SQLException {
        connection.rollback();
        connection.close();
    }

    @Test
    @DisplayName("새로운 게임을 저장하고, 불러오면 두 게임 상태가 일치 해야한다.")
    void same_status() {
        Map<Point, Piece> pieces = new LinkedHashMap<>();
        pieces.put(Point.of(0, 0), new Cha(Team.CHO));
        pieces.put(Point.of(0, 1), new Ma(Team.CHO));
        Board board = new Board(pieces);
        JanggiGame game = new JanggiGame(board, new ChoTurn());
        ConnectionContext.setConnection(connection);
        long roomId = repository.save(game);
        JanggiGame loadedGame = repository.loadGame(roomId);
        Assertions.assertThat(game.getTeam()).isEqualTo(loadedGame.getTeam());
        Assertions.assertThat(game.getChoScore()).isEqualTo(loadedGame.getChoScore());
        Assertions.assertThat(game.getHanScore()).isEqualTo(loadedGame.getHanScore());
    }

    @Test
    @DisplayName("기물을 이동하고, 불러오면 이동된 기물의 위치가 반영되어 있어야한다.")
    void play_point() {
        Map<Point, Piece> pieces = new LinkedHashMap<>();
        pieces.put(Point.of(0, 0), new Cha(Team.CHO));
        pieces.put(Point.of(1, 1), new Ma(Team.CHO));
        Board board = new Board(pieces);
        JanggiGame game = new JanggiGame(board, new ChoTurn());
        ConnectionContext.setConnection(connection);
        long roomId = repository.save(game);

        Point from = Point.of(0,0);
        Point to = Point.of(0,2);
        game.play(from, to);
        repository.update(roomId, from, to, game);

        JanggiGame loadedGame = repository.loadGame(roomId);
        Assertions.assertThat(loadedGame.getBoardStatus().get(from)).isNull();
        Assertions.assertThat(loadedGame.getBoardStatus().get(to)).isNotNull();
        Assertions.assertThat(loadedGame.getBoardStatus().get(to).getType()).isEqualTo(PieceType.CHA);
    }

    @Test
    @DisplayName("존재하지 않는 방번호로 조회하면 예외가 발생한다.")
    void not_found_room() {
        ConnectionContext.setConnection(connection);
        Assertions.assertThatThrownBy(() -> repository.loadGame(-1))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
