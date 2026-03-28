package domain;

import domain.point.Command;
import domain.point.Point;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CommandTest {

    @Test
    @DisplayName("Command에 좌표를 2개 초과로 입력할 경우 예외가 발생한다.")
    void shouldThrowExceptionWhenPointCountIsOverThanTwo() {
        String overPoint = "1,2 3,4 5,6";

        Assertions.assertThatThrownBy(() -> {
                    Command.from(overPoint);
                }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("명령어는 '시작좌표 종료좌표' 형식으로 총 2개의 좌표를 입력해야합니다.");
    }

    @Test
    @DisplayName("Command에 좌표를 2개 초과로 입력할 경우 예외가 발생한다.")
    void shouldThrowExceptionWhenPointCountIsLessThanTwo() {
        String lessPoint = "1,2";

        Assertions.assertThatThrownBy(() -> {
                    Command.from(lessPoint);
                }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("명령어는 '시작좌표 종료좌표' 형식으로 총 2개의 좌표를 입력해야합니다.");
    }

    @Test
    @DisplayName("아무것도 입력하지 않은 경우 예외가 발생한다.")
    void shouldThrowExceptionWhenInputIsBlank() {
        String blankInput = "";

        Assertions.assertThatThrownBy(() -> {
                    Command.from(blankInput);
                }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("입력값이 비어있습니다.");
    }

    @Test
    @DisplayName("좌표 형식을 잘못 입력하면 예외가 발생한다.")
    void shouldThrowExceptionWhenPointFormatIsWrong() {
        String wrongPointFormat = "1.2 3.3";

        Assertions.assertThatThrownBy(() -> {
                    Command.from(wrongPointFormat);
                }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("좌표는 'y,x' 형식이어야 합니다.");
    }

    @Test
    @DisplayName("좌표 형식을 잘못 입력하면 예외가 발생한다.")
    void shouldThrowExceptionWhenInputIsNotNumber() {
        String notNumberInput = "ㄱ,ㄴ ㄷ,ㄹ";

        Assertions.assertThatThrownBy(() -> {
                    Command.from(notNumberInput);
                }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("좌표는 숫자여야 합니다.");
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
        Command actualCommand = Command.from(correctInput);

        Point start = new Point(startY, startX);
        Point end = new Point(endY, endX);
        Command expectedCommand = new Command(start, end);

        Assertions.assertThat(actualCommand)
                .isEqualTo(expectedCommand);
    }

}
