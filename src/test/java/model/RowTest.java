package model;

import model.position.Row;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class RowTest {

    @ParameterizedTest
    @ValueSource(ints={1,10})
    void _1부터_10까지_범위는_생성_가능하다(int value){
        assertDoesNotThrow(()-> Row.from(value));
    }

    @ParameterizedTest
    @ValueSource(ints={0,11})
    void 범위에_맞지_않는건_실패(int value){
        assertThatThrownBy(() -> Row.from(value))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
