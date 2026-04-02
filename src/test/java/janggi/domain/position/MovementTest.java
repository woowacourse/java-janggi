package janggi.domain.position;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class MovementTest {

    @Test
    void 출발_좌표와_도착_좌표가_같으면_예외가_발생한다() {
        assertThatThrownBy(() -> new Movement(Position.from("11"), Position.from("11")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 출발 좌표와 도착 좌표는 같을 수 없습니다.");
    }

    @Test
    void 잘못된_좌표로_출발_및_도착_좌표를_입력하면_예외가_발생한다() {
        assertAll(
                () -> assertThatThrownBy(() -> new Movement(Position.from("101"), Position.from("11")))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("[ERROR] 올바른 좌표값이 아닙니다."),
                () -> assertThatThrownBy(() -> new Movement(Position.from("10"), Position.from("11")))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("[ERROR] 열 좌표는 1~9까지 사용 가능 합니다"),
                () -> assertThatThrownBy(() -> new Movement(Position.from("1a"), Position.from("11")))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("[ERROR] 열 좌표는 1~9까지 사용 가능 합니다"),
                () -> assertThatThrownBy(() -> new Movement(Position.from("a0"), Position.from("11")))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("[ERROR] 행 좌표는 1~10까지 사용 가능 합니다")
        );
    }
}