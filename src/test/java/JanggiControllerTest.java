import domain.board.BoardFactory;
import domain.board.Formation;
import domain.board.Team;
import domain.game.Game;
import domain.game.GameType;
import domain.game.Status;
import entity.GameEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import view.InputView;
import view.OutputView;

import java.util.List;

import static org.mockito.Mockito.*;

class JanggiControllerTest {

    private InputView inputView;
    private OutputView outputView;
    private JanggiService janggiService;
    private JanggiController janggiController;

    @BeforeEach
    void setUp() {
        inputView = mock(InputView.class);
        outputView = mock(OutputView.class);
        janggiService = mock(JanggiService.class);
        janggiController = new JanggiController(inputView, outputView, janggiService);
    }

    @Test
    @DisplayName("종료를 입력하면 프로그램을 종료한다")
    void shouldExitWhenGameTypeIsExit() {
        // given
        // when
        when(inputView.readGameType()).thenReturn(GameType.EXIT);

        janggiController.run();

        // then
        verify(inputView, times(1)).readGameType();
    }

    @Test
    @DisplayName("불러올 게임이 없으면 안내 메시지를 출력한다")
    void shouldPrintMessageWhenNoSavedGamesExist() {
        // given
        // when
        when(inputView.readGameType()).thenReturn(GameType.LOAD, GameType.EXIT);
        when(janggiService.findAllGames()).thenReturn(List.of());

        janggiController.run();

        // then
        verify(outputView).printMessage("기존에 진행하던 게임이 없습니다.");
    }

    @Test
    @DisplayName("종료된 저장 게임을 불러오면 결과만 출력한다")
    void shouldPrintResultWhenLoadedGameIsFinished() {
        // given
        GameEntity gameEntity = new GameEntity("CHU", "CHU_WIN");

        // when
        when(inputView.readGameType()).thenReturn(GameType.LOAD, GameType.EXIT);
        when(janggiService.findAllGames()).thenReturn(List.of(gameEntity));
        when(inputView.readGameNumber(anyList())).thenReturn(1);

        janggiController.run();

        // then
        verify(outputView).printGameResult(Status.CHU_WIN);
    }

    @Test
    @DisplayName("중단 명령을 입력하면 게임을 종료한다")
    void shouldEndGameWhenStopCommandIsEntered() {
        // given
        GameEntity gameEntity = new GameEntity("CHU", "PLAYING");
        Game game = Game.loadGame(
                BoardFactory.setUp(Formation.LEFT_ELEPHANT_RIGHT_ELEPHANT, Formation.LEFT_ELEPHANT_RIGHT_ELEPHANT),
                Team.HAN,
                Status.PLAYING
        );
        when(inputView.readGameType()).thenReturn(GameType.LOAD, GameType.EXIT);
        when(janggiService.findAllGames()).thenReturn(List.of(gameEntity));
        when(inputView.readGameNumber(anyList())).thenReturn(1);
        when(janggiService.loadGame(gameEntity)).thenReturn(game);

        when(inputView.readPosition(anyString())).thenReturn("n");

        // when
        janggiController.run();

        // then
        verify(outputView, times(1)).printBoard(game.getBoard().getBoard());
        verify(outputView, times(1)).printScore(game.calculateScore(Team.CHU), game.calculateScore(Team.HAN));
        verify(outputView, times(1)).printGameResult(game.getStatus());
    }

    @Test
    @DisplayName("기권 명령을 입력하면 게임을 종료한다")
    void shouldEndGameWhenQuitCommandIsEntered() {
        // given
        GameEntity gameEntity = new GameEntity("CHU", "PLAYING");
        Game game = Game.loadGame(
                BoardFactory.setUp(Formation.LEFT_ELEPHANT_RIGHT_ELEPHANT, Formation.LEFT_ELEPHANT_RIGHT_ELEPHANT),
                Team.HAN,
                Status.PLAYING
        );
        when(inputView.readGameType()).thenReturn(GameType.LOAD, GameType.EXIT);
        when(janggiService.findAllGames()).thenReturn(List.of(gameEntity));
        when(inputView.readGameNumber(anyList())).thenReturn(1);
        when(janggiService.loadGame(gameEntity)).thenReturn(game);

        when(inputView.readPosition(anyString())).thenReturn("r");

        // when
        janggiController.run();

        // then
        Assertions.assertEquals(Status.CHU_WIN, game.getStatus());
        verify(outputView, times(1)).printGameResult(game.getStatus());
    }

    @Test
    @DisplayName("잘못된 위치를 입력하면 에러 메시지를 출력하고 다시 입력받는다")
    void shouldRetryWhenInvalidPositionIsEntered() {
        // given
        GameEntity gameEntity = new GameEntity("CHU", "PLAYING");
        Game game = Game.loadGame(
                BoardFactory.setUp(Formation.LEFT_ELEPHANT_RIGHT_ELEPHANT, Formation.LEFT_ELEPHANT_RIGHT_ELEPHANT),
                Team.HAN,
                Status.PLAYING
        );
        when(inputView.readGameType()).thenReturn(GameType.LOAD, GameType.EXIT);
        when(janggiService.findAllGames()).thenReturn(List.of(gameEntity));
        when(inputView.readGameNumber(anyList())).thenReturn(1);
        when(janggiService.loadGame(gameEntity)).thenReturn(game);

        when(inputView.readPosition(anyString())).thenReturn("1 0", "n");

        // when
        janggiController.run();

        // then
        verify(outputView).printMessage("[ERROR] 해당 위치에 기물이 존재하지 않습니다.");
        verify(inputView, times(2)).readPosition(anyString());
    }

    @Test
    @DisplayName("상대 팀 기물을 선택하면 에러 메시지를 출력하고 다시 입력받는다")
    void shouldRetryWhenOpponentPieceIsSelected() {
        // given
        GameEntity gameEntity = new GameEntity("CHU", "PLAYING");
        Game game = Game.loadGame(
                BoardFactory.setUp(Formation.LEFT_ELEPHANT_RIGHT_ELEPHANT, Formation.LEFT_ELEPHANT_RIGHT_ELEPHANT),
                Team.HAN,
                Status.PLAYING
        );
        when(inputView.readGameType()).thenReturn(GameType.LOAD, GameType.EXIT);
        when(janggiService.findAllGames()).thenReturn(List.of(gameEntity));
        when(inputView.readGameNumber(anyList())).thenReturn(1);
        when(janggiService.loadGame(gameEntity)).thenReturn(game);

        when(inputView.readPosition(anyString())).thenReturn("0 0", "n");

        // when
        janggiController.run();

        // then
        verify(outputView).printMessage(contains("해당 기물은 상대편 기물이기 떄문에 움직일 수 없습니다."));
        verify(inputView, times(2)).readPosition(anyString());
    }
}
