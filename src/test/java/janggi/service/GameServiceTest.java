package janggi.service;

import static org.assertj.core.api.Assertions.assertThat;

import javax.sql.DataSource;
import janggi.config.TestDataInitializer;
import janggi.config.TestDataSourceConfig;
import janggi.domain.Position;
import janggi.domain.board.Board;
import janggi.domain.game.Game;
import janggi.domain.game.GameStatus;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceRule;
import janggi.domain.piece.camp.CampType;
import janggi.repository.GameRoomRepository;
import janggi.repository.PieceRepository;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameServiceTest {

    private GameService gameService;

    private GameRoomRepository gameRoomRepository;
    private PieceRepository pieceRepository;

    @BeforeEach
    void setUp() {
        DataSource dataSource = TestDataSourceConfig.getDataSource();
        TestDataInitializer.initialize(dataSource);
        gameRoomRepository = new GameRoomRepository(dataSource);
        pieceRepository = new PieceRepository(dataSource);
        gameService = new GameService(gameRoomRepository, pieceRepository);
    }

    @Test
    void 새로운_게임방을_생성한다() {
        // given
        Board board = new Board(Map.of(
                new Position(0, 0), new Piece(PieceRule.CHARIOT, CampType.CHO),
                new Position(9, 8), new Piece(PieceRule.CHARIOT, CampType.HAN)
        ));
        // when
        Game game = gameService.createGame(board);
        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(game.getGameRoomId()).isNotNull();
            softly.assertThat(game.getCurrentTurn()).isEqualTo(CampType.CHO);
            softly.assertThat(game.getGameStatus()).isEqualTo(GameStatus.PLAYING);
            softly.assertThat(game.getPiecePositions()).hasSize(2);
        });
    }

    @Test
    void 게임방을_조회한다() {
        // given
        Board board = new Board(Map.of(
                new Position(0, 0), new Piece(PieceRule.CHARIOT, CampType.CHO)
        ));
        Game savedGame = gameService.createGame(board);
        // when
        Game loadedGame = gameService.loadGame(savedGame.getGameRoomId());
        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(loadedGame.getGameRoomId()).isEqualTo(savedGame.getGameRoomId());
            softly.assertThat(loadedGame.getPiecePositions()).hasSize(1);
            softly.assertThat(loadedGame.getPiecePositions().get(new Position(0, 0)))
                    .isEqualTo(new Piece(PieceRule.CHARIOT, CampType.CHO));
        });
    }

    @Test
    void 진행_중인_모든_게임방의_아이디를_조회한다() {
        // when
        List<Long> playingGameRoomIds = gameService.findPlayingGameRoomIds();
        // then
        assertThat(playingGameRoomIds).hasSizeGreaterThanOrEqualTo(1);
    }

    @Test
    void 기물을_이동시킨다() {
        // given
        Position source = new Position(0, 0);
        Position destination = new Position(1, 0);

        Piece piece = new Piece(PieceRule.CHARIOT, CampType.CHO);
        Board board = new Board(Map.of(
                source, piece,
                new Position(0, 4), new Piece(PieceRule.GENERAL, CampType.CHO),
                new Position(9, 4), new Piece(PieceRule.GENERAL, CampType.HAN)
        ));
        Game game = gameService.createGame(board);
        // when
        gameService.move(game, source, destination);
        // then
        Game loadedGame = gameService.loadGame(game.getGameRoomId());
        assertThat(loadedGame.getPiecePositions()).containsEntry(destination, piece);
        assertThat(loadedGame.getPiecePositions()).doesNotContainKey(source);
    }

    @Test
    void 기물을_포획하며_이동시킨다() {
        // given
        Position source = new Position(0, 0);
        Position destination = new Position(1, 0);

        Board board = new Board(Map.of(
                source, new Piece(PieceRule.CHARIOT, CampType.CHO),
                destination, new Piece(PieceRule.SOLDIER, CampType.HAN),
                new Position(0, 4), new Piece(PieceRule.GENERAL, CampType.CHO),
                new Position(9, 4), new Piece(PieceRule.GENERAL, CampType.HAN)
        ));
        Game game = gameService.createGame(board);
        // when
        gameService.move(game, source, destination);
        // then
        Game loadedGame = gameService.loadGame(game.getGameRoomId());
        assertThat(loadedGame.getPiecePositions()).hasSize(3);
        assertThat(loadedGame.getPiecePositions()).containsEntry(destination, new Piece(PieceRule.CHARIOT, CampType.CHO));
    }

    @Test
    void 상태를_동기화한다() {
        // given
        Board board = new Board(Map.of(
                new Position(0, 4), new Piece(PieceRule.GENERAL, CampType.CHO),
                new Position(9, 4), new Piece(PieceRule.GENERAL, CampType.HAN),
                new Position(0, 0), new Piece(PieceRule.CHARIOT, CampType.CHO)
        ));
        Game game = gameService.createGame(board);
        // when
        gameService.move(game, new Position(0, 0), new Position(1, 0));
        // then
        Game loadedGame = gameService.loadGame(game.getGameRoomId());
        assertThat(loadedGame.getCurrentTurn()).isEqualTo(CampType.HAN);
    }

    @Test
    void 게임종료_상태를_동기화한다() {
        // given
        Board board = new Board(Map.of(
                new Position(9, 4), new Piece(PieceRule.GUARD, CampType.CHO),
                new Position(8, 4), new Piece(PieceRule.GENERAL, CampType.HAN)
        ));
        Game game = gameService.createGame(board);
        // when
        gameService.move(game, new Position(9, 4), new Position(8, 4));
        // then
        Game loadedGame = gameService.loadGame(game.getGameRoomId());
        assertThat(loadedGame.getGameStatus()).isEqualTo(GameStatus.CHO_WIN);
    }
}
