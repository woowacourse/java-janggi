package service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import domain.Board;
import domain.piece.Piece;
import domain.piece.Team;
import domain.position.Position;
import domain.settingType.SettingType;
import domain.state.GameInitializer;
import domain.state.JanggiGame;
import domain.state.State;
import java.sql.Connection;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.BoardRepository;
import repository.GameRepository;
import repository.GameRoomCreateInfo;
import repository.GameRoomInfo;

public class JanggiServiceTest {
    private GameRepository gameRepositoryMock;
    private BoardRepository boardRepository;
    private JanggiService janggiService;

    @BeforeEach
    void init() {
        gameRepositoryMock = mock(GameRepository.class);
        boardRepository = mock(BoardRepository.class);

        janggiService = new JanggiService(boardRepository, gameRepositoryMock);
    }

    @Test
    void 방을_조회할_수_있다() {
        janggiService.getRoomList();
        verify(gameRepositoryMock, times(1)).getAll();
    }

    @Test
    void 방을_생성할_수_있다() {
        String title = "살살좀 해주세요";
        SettingType choSettingType = SettingType.LEFT;
        SettingType hanSettingType = SettingType.LEFT;
        long expectGameId = 1L;

        GameRoomCreateInfo gameRoomInfo = new GameRoomCreateInfo(title, State.PLAYING, Team.CHO);
        when(gameRepositoryMock.save(any(Connection.class), eq(gameRoomInfo))).thenReturn(expectGameId);

        janggiService.createGame(title, choSettingType, hanSettingType);

        // then
        verify(gameRepositoryMock, times(1)).save(any(Connection.class), eq(gameRoomInfo));
    }

    @Test
    void 모든_정보를_조회할_수_있으면_예외가_발생하지_않는다() {
        Board board = Board.of(SettingType.LEFT, SettingType.LEFT);
        Map<Position, Piece> mockMap = board.getPieces();
        long gameId = 1L;

        GameRoomInfo gameRoomInfo = new GameRoomInfo(gameId, null, State.PLAYING, Team.CHO);
        when(boardRepository.load(gameId)).thenReturn(mockMap);
        when(gameRepositoryMock.getGameInfo(gameId)).thenReturn(gameRoomInfo);

        JanggiGame janggiGame = janggiService.loadGame(gameId);

        // then
        Assertions.assertThat(janggiGame).isNotNull();

        verify(boardRepository, times(1)).load(gameId);
        verify(gameRepositoryMock, times(1)).getGameInfo(gameId);
    }

    @Test
    void 턴_정보를_조회할_수_없으면_예외가_발생해야_한다() {
        Board board = Board.of(SettingType.LEFT, SettingType.LEFT);
        Map<Position, Piece> mockMap = board.getPieces();
        long expectGameId = 1L;
        when(boardRepository.load(expectGameId)).thenReturn(mockMap);
        when(gameRepositoryMock.getGameInfo(expectGameId)).thenReturn(null);

        // then
        Assertions.assertThatThrownBy(() -> janggiService.loadGame(expectGameId))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 기물_정보를_조회할_수_없으면_예외가_발생해야_한다() {
        long gameId = 1L;
        when(boardRepository.load(gameId)).thenReturn(null);
        GameRoomInfo gameRoomInfo = new GameRoomInfo(gameId, null, State.PLAYING, Team.CHO);
        when(gameRepositoryMock.getGameInfo(gameId)).thenReturn(gameRoomInfo);

        // then
        Assertions.assertThatThrownBy(() -> janggiService.loadGame(gameId))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void move를_호출하면_메서드가_적절히_수행되어야_한다() {
        JanggiGame game = GameInitializer.init(SettingType.LEFT, SettingType.LEFT);
        long gameId = 1L;
        Position from = Position.of(1, 1);
        Position to = Position.of(2, 1);
        GameRoomInfo gameRoomInfo = new GameRoomInfo(gameId, null, State.PLAYING, Team.CHO);

        // when
        when(boardRepository.load(gameId)).thenReturn(game.getBoard());
        when(gameRepositoryMock.getGameInfo(gameId)).thenReturn(gameRoomInfo);

        janggiService.move(gameId, from, to);

        // then
        verify(boardRepository, times(1)).delete(any(Connection.class), eq(gameId), eq(to));
        verify(boardRepository, times(1)).updatePosition(any(Connection.class), eq(gameId), eq(from), eq(to));
        verify(gameRepositoryMock, times(1)).updateGame(any(Connection.class), eq(gameId), any(JanggiGame.class));
    }

    @Test
    void pass하면_메서드가_적절히_수행되어야_한다() {
        JanggiGame game = GameInitializer.init(SettingType.LEFT, SettingType.LEFT);
        long gameId = 1L;
        GameRoomInfo gameRoomInfo = new GameRoomInfo(gameId, null, State.PLAYING, Team.CHO);
        when(gameRepositoryMock.getGameInfo(gameId)).thenReturn(gameRoomInfo);
        when(boardRepository.load(gameId)).thenReturn(game.getBoard());

        // when
        janggiService.pass(1);

        // then
        verify(gameRepositoryMock, times(1)).updateGame(any(Connection.class), eq(gameId), any(JanggiGame.class));
    }
}
