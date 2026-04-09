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
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.BoardRepository;
import repository.GameRepository;

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

        when(gameRepositoryMock.save(Team.CHO, title)).thenReturn(expectGameId);

        janggiService.createGame(title, choSettingType, hanSettingType);

        // then
        verify(gameRepositoryMock, times(1)).save(Team.CHO, title);
    }

    @Test
    void 모든_정보를_조회할_수_있으면_예외가_발생하지_않는다() {
        Board board = Board.of(SettingType.LEFT, SettingType.LEFT);
        Map<Position, Piece> mockMap = board.getPieces();
        long gameId = 1L;

        when(boardRepository.load(gameId)).thenReturn(mockMap);
        when(gameRepositoryMock.getCurrentTeam(gameId)).thenReturn(Team.CHO);

        JanggiGame janggiGame = janggiService.loadGame(gameId);

        // then
        Assertions.assertThat(janggiGame).isNotNull();

        verify(boardRepository, times(1)).load(gameId);
        verify(gameRepositoryMock, times(1)).getCurrentTeam(gameId);
    }

    @Test
    void 턴_정보를_조회할_수_없으면_예외가_발생해야_한다() {
        Board board = Board.of(SettingType.LEFT, SettingType.LEFT);
        Map<Position, Piece> mockMap = board.getPieces();
        long expectGameId = 1L;
        when(boardRepository.load(expectGameId)).thenReturn(mockMap);
        when(gameRepositoryMock.getCurrentTeam(expectGameId)).thenReturn(null);

        // then
        Assertions.assertThatThrownBy(() -> janggiService.loadGame(expectGameId))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 기물_정보를_조회할_수_없으면_예외가_발생해야_한다() {
        long expectGameId = 1L;
        when(boardRepository.load(expectGameId)).thenReturn(null);
        when(gameRepositoryMock.getCurrentTeam(expectGameId)).thenReturn(Team.CHO);

        // then
        Assertions.assertThatThrownBy(() -> janggiService.loadGame(expectGameId))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void move를_호출하면_메서드가_적절히_수행되어야_한다() {
        JanggiGame game = GameInitializer.init(SettingType.LEFT, SettingType.LEFT);
        long gameId = 1L;
        Position from = Position.of(1, 1);
        Position to = Position.of(2, 1);

        // when
        when(boardRepository.load(gameId)).thenReturn(game.getBoard());
        when(gameRepositoryMock.getCurrentTeam(gameId)).thenReturn(Team.CHO);

        janggiService.move(gameId, from, to);

        // then
        verify(boardRepository, times(1)).delete(conn, gameId, to);
        verify(boardRepository, times(1)).updatePosition(conn, gameId, from, to);
        verify(gameRepositoryMock, times(1)).updateGame(conn, eq(gameId), any(JanggiGame.class));
    }

    @Test
    void pass하면_메서드가_적절히_수행되어야_한다() {
        JanggiGame game = GameInitializer.init(SettingType.LEFT, SettingType.LEFT);
        long gameId = 1L;
        when(boardRepository.load(gameId)).thenReturn(game.getBoard());
        when(gameRepositoryMock.getCurrentTeam(gameId)).thenReturn(Team.CHO);
        // when
        janggiService.pass(1);

        // then
        verify(gameRepositoryMock, times(1)).updateGame(conn, eq(gameId), any(JanggiGame.class));
    }
}
