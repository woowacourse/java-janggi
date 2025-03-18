package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.Position;
import janggi.Side;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class HorseTest {

    @ParameterizedTest
    @DisplayName("경로 상에 말이 있는 경우, true 반환한다.")
    @CsvSource(value = {
            "4,7",
            "6,7",
            "3,6",
            "3,4",
            "4,3",
            "6,3",
            "7,4",
            "7,6"
    })
    void shouldReturnTrueWhenRouteEmpty() {
        // given
        Horse horse = new Horse(Side.RED);
        Position horseCurrentPosition = new Position(3, 10);
        Position horseDestination = new Position(2, 8);
        List<Position> piecePositions = List.of(horseCurrentPosition);

        // when
        boolean canMove = horse.movable(horseCurrentPosition, horseDestination, piecePositions);

        // then
        assertThat(canMove).isTrue();
    }

    @ParameterizedTest
    @DisplayName("상하좌우 경로 상에 말이 있는 경우, false를 반환한다.")
    @CsvSource(value = {
            "4,7",
            "6,7",
            "3,6",
            "3,4",
            "4,3",
            "6,3",
            "7,4",
            "7,6"
    })
    void shouldReturnFalseWhenRouteExists(int destX, int destY) {
        // given
        Horse horse = new Horse(Side.RED);
        Position horseCurrentPosition = new Position(5, 5);
        Position horseDestination = new Position(destX, destY);
        List<Position> disturbPositions = List.of(
                new Position(5, 6),
                new Position(6, 5),
                new Position(4, 5),
                new Position(5, 4)
        );

        // when
        boolean canMove = horse.movable(horseCurrentPosition, horseDestination, disturbPositions);

        // then
        assertThat(canMove).isFalse();
    }
}
