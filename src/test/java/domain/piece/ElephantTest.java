package domain.piece;

import domain.coordination.Coordination;
import domain.piece.error.InvalidMovementException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

class ElephantTest {

    @ParameterizedTest
    @CsvSource(value = {"2,9", "3,9", "4,9", "3,8", "4,8", "4,7"})
    void 이동할_수_없는_위치일_경우_에러를_반환한다(int column, int row) {
        Elephant elephant = new Elephant(Team.CHO);
        Coordination from = Coordination.of(3, 10);
        Coordination to = Coordination.of(column, row);

        assertThatThrownBy(() -> elephant.validateRule(from, to))
                .isExactlyInstanceOf(InvalidMovementException.class);
    }

    @ParameterizedTest
    @CsvSource(value = {"5,7", "1,7", "6,8"})
    void 이동할_수_있는_위치일_경우_에러를_반환하지_않는다(int column, int row) {
        Elephant elephant = new Elephant(Team.CHO);
        Coordination from = Coordination.of(3, 10);
        Coordination to = Coordination.of(column, row);

        assertThatCode(() -> elephant.validateRule(from, to))
                .doesNotThrowAnyException();
    }
}
