package model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import model.position.Column;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ColumnTest {
    @ParameterizedTest
    @ValueSource(ints = {1, 9})
    void _1부터_9까지_범위는_생성_가능하다(int value) {
        assertDoesNotThrow(() -> Column.from(value));
    }
}
