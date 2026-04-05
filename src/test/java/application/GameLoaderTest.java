package application;

import static org.assertj.core.api.Assertions.assertThat;

import dao.BoardRepository;
import dao.GameLoadResult;
import dao.GameRoom;
import domain.board.Board;
import domain.board.BoardFactory;
import domain.board.Formation;
import domain.game.GameStatus;
import domain.player.Team;
import infra.db.DbBootstrap;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameLoaderTest {
    private final GameRoom gameRoom = new GameRoom();
    private final BoardRepository boardRepository = new BoardRepository();
    private final GameLoader gameLoader = new GameLoader(gameRoom, boardRepository);

    @BeforeEach
    void setUp() {
        DbBootstrap.initializeForTest();
    }

    @Test
    void loadProgress_진행중인_게임이_있으면_복원한다() {
        long gameId = gameRoom.createGame("CHO Player", "HAN Player");
        Board board = BoardFactory.createWithFormation(Formation.from(1), Formation.from(1));
        boardRepository.save(gameId, board);

        Optional<GameLoadResult> result = gameLoader.loadProgress();

        assertThat(result).isPresent();
        assertThat(result.get().gameId()).isEqualTo(gameId);
        assertThat(result.get().choName()).isEqualTo("CHO Player");
        assertThat(result.get().hanName()).isEqualTo("HAN Player");
        assertThat(result.get().currentTeam()).isEqualTo(Team.CHO);
        assertThat(result.get().boardMap()).isNotNull();
    }

    @Test
    void loadProgress_진행중인_게임이_없으면_Empty를_반환한다() {
        // 기존에 있을 수 있는 PROGRESS 게임들 종료
        Optional<GameLoadResult> existingGame = gameLoader.loadProgress();
        while (existingGame.isPresent()) {
            gameRoom.updateGameState(existingGame.get().gameId(), Team.CHO, GameStatus.CHO_WIN);
            existingGame = gameLoader.loadProgress();
        }

        // 새 게임 생성 후 바로 종료
        long gameId = gameRoom.createGame("CHO Player", "HAN Player");
        gameRoom.updateGameState(gameId, Team.CHO, GameStatus.CHO_WIN);

        Optional<GameLoadResult> result = gameLoader.loadProgress();

        assertThat(result).isEmpty();
    }

    @Test
    void loadProgress_여러_게임_중_최신_게임을_반환한다() {
        long gameId1 = gameRoom.createGame("CHO Player1", "HAN Player1");
        long gameId2 = gameRoom.createGame("CHO Player2", "HAN Player2");
        Board board = BoardFactory.createWithFormation(Formation.from(1), Formation.from(1));
        boardRepository.save(gameId2, board);

        Optional<GameLoadResult> result = gameLoader.loadProgress();

        assertThat(result).isPresent();
        assertThat(result.get().gameId()).isEqualTo(gameId2);
    }
}

