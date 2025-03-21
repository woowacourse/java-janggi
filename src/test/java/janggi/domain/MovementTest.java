package janggi.domain;

import janggi.domain.rule.Movement;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class MovementTest {

    @Test
    @DisplayName("생성 시, List 인자의 size가 2가 아닐 경우, 예외를 던진다")
    void exceptionWhenInvalidListSize() {
        //given
        //when
        //then
        assertAll(() -> {
            assertThatThrownBy(() -> new Movement(List.of()))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Movement는 2개의 원소를 가진 리스트가 필요합니다.");
            assertThatThrownBy(() -> new Movement(List.of(1, 2, 3)))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Movement는 2개의 원소를 가진 리스트가 필요합니다.");
        });
    }

    @Test
    @DisplayName("수직/수평 거리 중에서 작은 값을 가져온다")
    void getMinDistance() {
        //given
        Movement movement = new Movement(List.of(1, 2));

        //when
        int actual = movement.getMinDistance();

        //then
        assertThat(actual).isEqualTo(1);
    }

    @Test
    @DisplayName("수직/수평 거리 중에서 큰 값을 가져온다")
    void getMaxDistance() {
        //given
        Movement movement = new Movement(List.of(1, 2));

        //when
        int actual = movement.getMaxDistance();

        //then
        assertThat(actual).isEqualTo(2);
    }
}
