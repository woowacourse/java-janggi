package domain.player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import common.JanggiException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class NameTest {

    @ParameterizedTest
    @ValueSource(strings = {"일이", "일이삼", "일이삼사오육칠팔", "ab", "abc", "abcdefg", "abcdefgh"})
    void 이름이_올바르게_생성된다(String value) {
        Name name = new Name(value);
        assertEquals(value, name.value());
    }

    @ParameterizedTest
    @ValueSource(strings = {"일", "일이삼사오육칠팔구", "a", "abcdefghi", "봉구!", "po bi"})
    void 조건에_맞지_않는_입력인_경우에는_에러를_던진다(String value) {
        assertThrows(JanggiException.class, () -> new Name(value));
    }
}
