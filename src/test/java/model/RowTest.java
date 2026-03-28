package model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import model.position.Row;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class RowTest {

    @ParameterizedTest
    @ValueSource(ints = {1, 10})
    void _1부터_10까지_범위는_생성_가능하다(int value) {
        assertDoesNotThrow(() -> Row.from(value));
    }
}
