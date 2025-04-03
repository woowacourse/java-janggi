package janggi.command;

import janggi.GameContext;
import janggi.board.Board;
import janggi.player.Player;
import janggi.service.JanggiService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SaveCommandTest {

    @Test
    @DisplayName("정상 호출 확인")
    void execute() {
        // given
        final GameContext context = mock(GameContext.class);
        final JanggiService service = mock(JanggiService.class);
        final Board board = mock(Board.class);
        final Player player = mock(Player.class);

        when(context.getBoard()).thenReturn(board);
        when(context.getCurrentPlayer()).thenReturn(player);

        final Command command = new SaveCommand();

        // when
        command.execute(context, service);

        // then
        verify(service).saveGameWithPieces(
                eq(context));
    }

    @Test
    @DisplayName("SAVE는 종료 명령이다")
    void isNotExitCommand() {
        // given
        final Command command = new SaveCommand();

        // when
        // then
        assertThat(command.isExitCommand()).isTrue();
    }
}
