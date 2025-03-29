package janggi.domain;

import janggi.domain.position.Position;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

@ReplaceUnderBar
public class PositionTest {

    @Test
    void 가로_세로_2차원_좌표를_가진다() {
        Position position = new Position(1, 2);
        assertThat(position.getX()).isEqualTo(1);
        assertThat(position.getY()).isEqualTo(2);
    }

    @ParameterizedTest
    @CsvSource(value = {"-1, 1", "1,-1", "9,1", "1,10"})
    void 초기화_시_좌표를_검증한다(int x, int y) {

        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Position(x, y));
    }

    @ParameterizedTest
    @CsvSource(value = {"1,2,false", "2,2,true"})
    void X_좌표가_같은지_확인한다(int x, int comparer, boolean expected) {
        Position position = new Position(x, 2);

        assertThat(position.hasSameX(new Position(comparer, 3))).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {"1,2,false", "2,2,true"})
    void Y_좌표가_같은지_확인한다(int y, int comparer, boolean expected) {
        Position position = new Position(1, y);

        assertThat(position.hasSameY(new Position(3, comparer))).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"3,0, true", "3,1, true", "3,2, true",
        "4,0, true", "4,1, true", "4,2, true",
        "5,0, true", "5,1, true", "5,2, true",
        "3,7, true", "3,8, true", "3,9, true",
        "4,7, true", "4,8, true", "4,9, true",
        "5,7, true", "5,8, true", "5,9, true",
        "2,1, false", "6,1, false", "4,3, false",
        "3,6, false", "5,6, false", "6,7, false"})
    void 궁성_좌표인지_확인한다(int x , int y , boolean isPalace) {
        Position position = new Position(x, y);
        assertThat(position.isPalace()).isEqualTo(isPalace);
    }
}
