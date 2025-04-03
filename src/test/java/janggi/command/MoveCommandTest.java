package janggi.command;

import janggi.GameContext;
import janggi.board.Board;
import janggi.coordinate.Position;
import janggi.player.Player;
import janggi.service.JanggiService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MoveCommandTest {

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

        final Command command = MoveCommand.of("1", "2", "3", "4");

        // when
        command.execute(context, service);

        // then
        verify(service).movePiece(
                eq(board),
                eq(player),
                eq(Position.of(1, 2)),
                eq(Position.of(3, 4)));
    }

    @Test
    @DisplayName("MOVE는 종료 명령이 아니다")
    void isNotExitCommand() {
        // given
        final Command command = MoveCommand.of("1", "1", "1", "1");

        // when
        // then
        assertThat(command.isExitCommand()).isFalse();
    }
}
