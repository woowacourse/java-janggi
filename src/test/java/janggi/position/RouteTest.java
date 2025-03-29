package janggi.position;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RouteTest {
    @Test
    @DisplayName("빈 경로의 루트는 생성할 수 없다")
    void test1() {
        // given
        Assertions.assertThatThrownBy(() -> Route.of(List.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("빈 경로는 존재할 수 없습니다.");
    }

    @Test
    @DisplayName("")
    void test2() {
        // given

        // when

        // then

    }
}