package janggi.infrastructure.repository;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Game;
import janggi.domain.Side;
import janggi.domain.board.Board;
import janggi.domain.board.BoardFactory;
import janggi.domain.board.Formation;
import janggi.domain.piece.Piece;
import janggi.domain.player.Name;
import janggi.domain.player.Players;
import janggi.domain.space.Position;
import janggi.infrastructure.dao.GameDao;
import janggi.infrastructure.dao.PieceDao;
import janggi.infrastructure.db.DatabaseConnection;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JdbcGameRepositoryTest {
    private JdbcGameRepository gameRepository;

    @BeforeEach
    void setUp() throws Exception {
        GameDao gameDao = new GameDao();
        PieceDao pieceDao = new PieceDao();
        gameRepository = new JdbcGameRepository(gameDao, pieceDao);

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             InputStream inputStream = getClass().getClassLoader().getResourceAsStream("schema.sql")) {
            String schemaSql = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            statement.execute(schemaSql);
        }
    }

    @Test
    void 게임_전체_상태를_저장하고_다시_조회하여_완벽히_복원한다() {
        // given
        Name choName = new Name("고래");
        Name hanName = new Name("제이콥");
        Board board = BoardFactory.create(Formation.LEFT_ELEPHANT, Formation.RIGHT_ELEPHANT);
        Game originalGame = new Game(board, Players.createInitial(choName, hanName));

        Position source = Position.of(0, 3);
        Position target = Position.of(0, 4);
        originalGame.move(source, target);

        // when
        Long savedId = gameRepository.save(originalGame);
        Game restoredGame = gameRepository.findById(savedId)
                .orElseThrow(() -> new IllegalArgumentException("게임을 찾을 수 없습니다."));

        // then
        assertThat(restoredGame.getCurrentSide()).isEqualTo(Side.HAN);
        assertThat(restoredGame.getPlayerNameBySide(Side.CHO)).isEqualTo(choName);
        assertThat(restoredGame.getPlayerNameBySide(Side.HAN)).isEqualTo(hanName);

        // then
        Map<Position, Piece> restoredBoard = restoredGame.getBoard();
        assertThat(restoredBoard).hasSize(32);
        assertThat(restoredBoard.get(target).getType().name()).isEqualTo("SOLDIER");
        assertThat(restoredBoard.containsKey(source)).isFalse();
    }
}
