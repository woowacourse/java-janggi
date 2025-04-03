package janggi.command;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class QuitCommandTest {

    @Test
    @DisplayName("QUIT는 종료 명령이다")
    void isNotExitCommand() {
        // given
        final Command command = new QuitCommand();

        // when
        // then
        assertThat(command.isExitCommand()).isTrue();
    }
}
