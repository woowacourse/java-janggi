package persistence;

import domain.Game;
import domain.board.Board;
import domain.coordinate.Position;
import domain.piece.Chariot;
import domain.piece.EmptyPiece;
import domain.piece.Piece;
import domain.state.ChuSide;
import domain.state.Side;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class GameDaoTest {

    private final GameDao gameDao = new GameDao();
    private static final Long TEST_GAME_ID = 999L;

    @BeforeEach
    void setUp() {
        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM board_state WHERE game_id = " + TEST_GAME_ID);
            stmt.executeUpdate("DELETE FROM game_room WHERE id = " + TEST_GAME_ID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("게임 상태를 저장하고 다시 로드했을 때 데이터가 일치해야 한다")
    void saveAndLoadTest() {
        // given
        Map<Position, Piece> pieceMap = new HashMap<>();
        Position chariotPos = Position.of(0, 0);
        pieceMap.put(chariotPos, new Chariot(Side.CHU));

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 9; j++) {
                pieceMap.putIfAbsent(Position.of(i, j), EmptyPiece.getInstance());
            }
        }

        Game originalGame = new Game(new Board(pieceMap), new ChuSide());
        originalGame.assignId(TEST_GAME_ID);

        // when
        gameDao.save(originalGame);
        Game loadedGame = gameDao.load(TEST_GAME_ID);

        // then
        assertThat(loadedGame.getId()).isEqualTo(originalGame.getId());
        assertThat(loadedGame.getSide()).isEqualTo(Side.CHU);

        Piece piece = loadedGame.getBoard().get(chariotPos);
        assertThat(piece).isInstanceOf(Chariot.class);
        assertThat(piece.getSide()).isEqualTo(Side.CHU);
    }

    @Test
    @DisplayName("기물을 이동시킨 후 다시 저장하면 DB 값이 업데이트되어야 한다")
    void updateTest() {
        // given
        Game game = new Game(new Board(new HashMap<>()), new ChuSide());
        game.assignId(TEST_GAME_ID);
        gameDao.save(game);

        // when
        Game updatedGame = new Game(new Board(game.getBoard()), new domain.state.HanSide());
        updatedGame.assignId(TEST_GAME_ID);
        gameDao.save(updatedGame);

        // then
        Game loadedGame = gameDao.load(TEST_GAME_ID);
        assertThat(loadedGame.getSide()).isEqualTo(Side.HAN);
    }
}
