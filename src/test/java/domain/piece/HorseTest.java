package domain.piece;

import domain.coordination.Coordination;
import domain.piece.error.InvalidMovementException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

class HorseTest {

    @ParameterizedTest
    @CsvSource(value = {"3,7", "5,7", "4,7", "4,9"})
    public void 이동할_수_없는_위치일_경우_에러를_반환한다(int column, int row) {
        Horse horse = new Horse(Team.CHO);
        Coordination from = Coordination.of(4, 6);
        Coordination to = Coordination.of(column, row);

        assertThatThrownBy(() -> horse.validateRule(from, to))
                .isExactlyInstanceOf(InvalidMovementException.class);
    }

    @ParameterizedTest
    @CsvSource(value = {"2,5", "2,7", "3,4", "3,8", "5,4", "5,8", "6,5", "6,7"})
    public void 이동할_수_있는_위치일_경우_에러를_반환하지_않는다(int column, int row) {
        Horse horse = new Horse(Team.CHO);
        Coordination from = Coordination.of(4, 6);
        Coordination to = Coordination.of(column, row);

        assertThatCode(() -> horse.validateRule(from, to))
                .doesNotThrowAnyException();
    }
}
