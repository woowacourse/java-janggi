package domain.point;

import domain.command.MoveCommand;
import domain.command.exception.CommandException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static domain.command.exception.CommandError.*;

class MoveCommandTest {

    @Test
    @DisplayName("Command에 좌표를 2개 초과로 입력할 경우 예외가 발생한다.")
    void shouldThrowExceptionWhenPointCountIsOverThanTwo() {
        String overPoint = "1,2 3,4 5,6";

        Assertions.assertThatThrownBy(() -> MoveCommand.from(overPoint))
                .isInstanceOf(CommandException.class)
                .hasMessage(MOVE_COMMAND_FORMAT_IS_WRONG.getMessage());
    }

    @Test
    @DisplayName("Command에 좌표를 2개 초과로 입력할 경우 예외가 발생한다.")
    void shouldThrowExceptionWhenPointCountIsLessThanTwo() {
        String lessPoint = "1,2";

        Assertions.assertThatThrownBy(() -> MoveCommand.from(lessPoint))
                .isInstanceOf(CommandException.class)
                .hasMessage(MOVE_COMMAND_FORMAT_IS_WRONG.getMessage());
    }

    @Test
    @DisplayName("아무것도 입력하지 않은 경우 예외가 발생한다.")
    void shouldThrowExceptionWhenInputIsBlank() {
        String blankInput = "";

        Assertions.assertThatThrownBy(() -> MoveCommand.from(blankInput))
                .isInstanceOf(CommandException.class)
                .hasMessage(MOVE_COMMAND_INPUT_IS_BLANK.getMessage());
    }

    @Test
    @DisplayName("좌표 형식을 잘못 입력하면 예외가 발생한다.")
    void shouldThrowExceptionWhenPointFormatIsWrong() {
        String wrongPointFormat = "1.2 3.3";

        Assertions.assertThatThrownBy(() -> MoveCommand.from(wrongPointFormat))
                .isInstanceOf(CommandException.class)
                .hasMessage(MOVE_COMMAND_FORMAT_IS_WRONG.getMessage());
    }

    @Test
    @DisplayName("좌표 형식을 잘못 입력하면 예외가 발생한다.")
    void shouldThrowExceptionWhenInputIsNotNumber() {
        String notNumberInput = "ㄱ,ㄴ ㄷ,ㄹ";

        Assertions.assertThatThrownBy(() -> MoveCommand.from(notNumberInput))
                .isInstanceOf(CommandException.class)
                .hasMessage(MOVE_COMMAND_IS_NOT_NUMERIC.getMessage());
    }

    @Test
    @DisplayName("형식에 맞춰 좌표를 입력한 경우, 정상적으로 동작한다.")
    void returnCommandWhenFormatIsCorrect() {
        int startX = 0;
        int startY = 0;
        int endX = 1;
        int endY = 1;

        String format = "%d,%d %d,%d";
        String correctInput = String.format(format, startY, startX, endY, endX);
        MoveCommand actualCommand = MoveCommand.from(correctInput);

        Point start = new Point(startY, startX);
        Point end = new Point(endY, endX);
        MoveCommand expectedCommand = new MoveCommand(start, end);

        Assertions.assertThat(actualCommand)
                .isEqualTo(expectedCommand);
    }

}
