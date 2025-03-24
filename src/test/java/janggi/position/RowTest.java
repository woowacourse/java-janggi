package janggi.position;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class RowTest {
    @ParameterizedTest
    @ValueSource(ints = {0,11})
    @DisplayName("Row가 영역 밖의 값을 가지면 True")
    void outOfBoundsTrueTest(int value) {
        //given
        //when
        //then
        Assertions.assertThat(new Row(value).isOutOfBounds()).isTrue();
    }

    @ParameterizedTest
    @ValueSource(ints = {1,10})
    @DisplayName("Row가 영역 안의 값을 가지만 False")
    void outOfBoundsFalseTest(int value) {
        //given
        //when
        //then
        Assertions.assertThat(new Row(value).isOutOfBounds()).isFalse();
    }
}
