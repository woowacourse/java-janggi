package service;

import static domain.player.Team.CHO;
import static domain.player.Team.HAN;
import static org.assertj.core.api.Assertions.assertThat;

import dao.BoardRepository;
import dao.GameLoadResult;
import dao.GamePersistence;
import dao.GameRoom;
import domain.board.Board;
import domain.board.BoardFactory;
import domain.board.Formation;
import common.GameStatus;
import domain.player.Name;
import domain.player.Player;
import domain.player.Team;
import db.DbBootstrap;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JanggiGameSetupServiceTest {
    private final GameRoom gameRoom = new GameRoom();
    private final BoardRepository boardRepository = new BoardRepository();
    private final GamePersistence gamePersistence = new GamePersistence(gameRoom, boardRepository);
    private final JanggiGameSetupService janggiGameSetupService = new JanggiGameSetupService(gamePersistence);

    @BeforeEach
    void setUp() {
        DbBootstrap.initializeForTest();
    }

    @Test
    void 새_게임을_생성하면_세션을_반환하고_진행_상태로_저장한다() {
        JanggiGameSession session = janggiGameSetupService.createNewGame(
            createPlayer("CHO Player", CHO),
            createPlayer("HAN Player", HAN),
            Formation.from(1),
            Formation.from(1)
        );

        assertThat(session.gameId()).isPositive();
        assertThat(session.janggiGameManager().getCurrentPlayer().getProfile().team()).isEqualTo(CHO);
        assertThat(janggiGameSetupService.findProgressGames())
            .anyMatch(gameInfo -> gameInfo.gameId() == session.gameId());
    }

    @Test
    void 저장된_게임을_세션으로_불러온다() {
        long gameId = gameRoom.createGame("CHO Player", "HAN Player");
        Board board = BoardFactory.createWithFormation(Formation.from(1), Formation.from(1));
        boardRepository.save(gameId, board);

        Optional<JanggiGameSession> loaded = janggiGameSetupService.loadSessionById(gameId);

        assertThat(loaded).isPresent();
        assertThat(loaded.get().gameId()).isEqualTo(gameId);
        assertThat(loaded.get().janggiGameManager().getCurrentPlayer().getProfile().team()).isEqualTo(CHO);
    }

    @Test
    void loadProgress_진행중인_게임이_있으면_복원한다() {
        long gameId = gameRoom.createGame("CHO Player", "HAN Player");
        Board board = BoardFactory.createWithFormation(Formation.from(1), Formation.from(1));
        boardRepository.save(gameId, board);

        Optional<GameLoadResult> result = janggiGameSetupService.loadProgress();

        assertThat(result).isPresent();
        assertThat(result.get().gameId()).isEqualTo(gameId);
        assertThat(result.get().choName()).isEqualTo("CHO Player");
        assertThat(result.get().hanName()).isEqualTo("HAN Player");
        assertThat(result.get().currentTeam()).isEqualTo(Team.CHO);
        assertThat(result.get().boardMap()).isNotNull();
    }

    @Test
    void loadProgress_진행중인_게임이_없으면_Empty를_반환한다() {
        Optional<GameLoadResult> existingGame = janggiGameSetupService.loadProgress();
        while (existingGame.isPresent()) {
            gameRoom.updateGameState(existingGame.get().gameId(), Team.CHO, GameStatus.CHO_WIN);
            existingGame = janggiGameSetupService.loadProgress();
        }

        long gameId = gameRoom.createGame("CHO Player", "HAN Player");
        gameRoom.updateGameState(gameId, Team.CHO, GameStatus.CHO_WIN);

        Optional<GameLoadResult> result = janggiGameSetupService.loadProgress();

        assertThat(result).isEmpty();
    }

    @Test
    void loadProgress_여러_게임_중_최신_게임을_반환한다() {
        gameRoom.createGame("CHO Player1", "HAN Player1");
        long gameId2 = gameRoom.createGame("CHO Player2", "HAN Player2");
        Board board = BoardFactory.createWithFormation(Formation.from(1), Formation.from(1));
        boardRepository.save(gameId2, board);

        Optional<GameLoadResult> result = janggiGameSetupService.loadProgress();

        assertThat(result).isPresent();
        assertThat(result.get().gameId()).isEqualTo(gameId2);
    }

    private Player createPlayer(String name, Team team) {
        return new Player(new Name(name), team);
    }
}
