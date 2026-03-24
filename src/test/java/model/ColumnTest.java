package model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class ColumnTest {
    @ParameterizedTest
    @ValueSource(ints={1,9})
    void _1부터_9까지_범위는_생성_가능하다(int value){
        assertDoesNotThrow(()->Column.from(value));
    }

    @ParameterizedTest
    @ValueSource(ints={0,10})
    void 범위에_맞지_않는건_실패(int value){
        assertThatThrownBy(() -> Column.from(value))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
