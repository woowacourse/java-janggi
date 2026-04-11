package team.janggi.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import team.janggi.domain.JanggiFormation;
import team.janggi.domain.Position;
import team.janggi.domain.Team;
import team.janggi.domain.board.Board;
import team.janggi.domain.board.BoardFactory;
import team.janggi.domain.piece.Piece;
import team.janggi.domain.piece.PieceType;
import team.janggi.entity.Game;
import team.janggi.infra.ConnectionManager;
import team.janggi.infra.TestH2Connection;
import team.janggi.infra.TransactionManager;
import team.janggi.repository.BoardPieceRepository;
import team.janggi.repository.GameRepository;

public class GameServiceTest {
    private GameRepository gameRepository;
    private BoardPieceRepository boardPieceRepository;
    private TransactionManager transactionManager;

    @BeforeEach
    public void setUp() {
        gameRepository = new GameRepository();
        boardPieceRepository = new BoardPieceRepository();
        ConnectionManager connectionManager = new TestH2Connection();
        transactionManager = new TransactionManager(connectionManager);
    }

    @Test
    public void 현재_보드_상태와_게임_정보를_DB에_저장한다() {
        // given
        GameService gameService = new GameService(gameRepository, boardPieceRepository,
                transactionManager);
        String gameName = "test_game";
        Team team = Team.CHO;
        Board board = new Board(new BoardFactory(JanggiFormation.EHHE, JanggiFormation.EHHE));

        // when & then
        Assertions.assertDoesNotThrow(() -> gameService.saveGame(gameName, team, board));
    }

    @Test
    public void DB에_기록된_게임_정보를_DB에서_조회한다() {
        // given
        GameService gameService = new GameService(gameRepository, boardPieceRepository,
                transactionManager);

        // when
        List<Game> games = gameService.getGames();

        // then
        assertAll(
                () -> assertEquals(2, games.size(), "저장된 전체 게임 개수가 일치해야 합니다."),
                () -> {
                    Game firstGame = games.get(0);
                    assertEquals(1, firstGame.getId());
                    assertEquals("테스트 게임입니다.", firstGame.getGameName());
                    assertEquals("CHO", firstGame.getCurrentTurn());
                },
                () -> {
                    Game secondGame = games.get(1);
                    assertEquals(2, secondGame.getId());
                    assertEquals("보드가 저장되지 않은 게임입니다.", secondGame.getGameName());
                    assertEquals("HAN", secondGame.getCurrentTurn());
                }
        );
    }

    @Test
    void 특정_ID의_게임_보드_상태를_정상적으로_복원한다() {
        // given
        int gameId = 1;
        GameService gameService = new GameService(gameRepository, boardPieceRepository,
                transactionManager);

        // when
        Board loadedBoard = gameService.loadBoard(gameId);
        Map<Position, Piece> boardStatus = loadedBoard.getStatus();
        // then
        assertAll(
                () -> Assertions.assertNotNull(loadedBoard),
                () -> {
                    Piece piece = boardStatus.get(Position.of(4, 8));
                    assertEquals(PieceType.KING, piece.getPieceType());
                }
        );
    }

    @Test
    void 특정_ID_게임의_현재_차례_정보를_가져온다() {
        // given
        int gameId = 1;
        GameService gameService = new GameService(gameRepository, boardPieceRepository,
                transactionManager);

        // when
        Team currentTurn = gameService.loadTurn(gameId);

        // then
        assertEquals(Team.CHO, currentTurn);
    }
}
