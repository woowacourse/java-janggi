package model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import model.piece.Elephant;
import model.position.Column;
import model.position.Position;
import model.position.Row;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class ElephantTest {

    private final Elephant elephant = new Elephant(Team.RED);
    private final Position departure = new Position(Column.FIVE, Row.FIVE);

    @Nested
    @DisplayName("horse의 이동 가능한 경로를 구한다.")
    class FindDirectionOfHorse {

        @Test
        @DisplayName("위 + (우측 상단 대각x2)인 경우")
        void up_and_up_right_up_right() {
            Position arrival = new Position(Column.TWO, Row.SEVEN);
            List<Position> findDirection = elephant.calculateAllDirection(departure, arrival);
            List<Position> expectedPosition = List.of(
                new Position(Column.FOUR, Row.FIVE),
                new Position(Column.THREE, Row.SIX),
                new Position(Column.TWO, Row.SEVEN));
            assertThat(findDirection).containsExactlyInAnyOrderElementsOf(expectedPosition);
        }

        @Test
        @DisplayName("위 + (좌측 상단 대각x2)인 경우 ")
        void up_and_up_left_up_left() {
            Position arrival = new Position(Column.TWO, Row.THREE);
            List<Position> findDirection = elephant.calculateAllDirection(departure, arrival);
            List<Position> expectedPosition = List.of(
                new Position(Column.FOUR, Row.FIVE),
                new Position(Column.THREE, Row.FOUR),
                new Position(Column.TWO, Row.THREE));
            assertThat(findDirection).containsExactlyInAnyOrderElementsOf(expectedPosition);
        }

        @Test
        @DisplayName("아래 + (우측 하단 대각x2)인 경우")
        void down_and_down_right_down_right() {
            Position arrival = new Position(Column.EIGHT, Row.SEVEN);
            List<Position> findDirection = elephant.calculateAllDirection(departure, arrival);
            List<Position> expectedPosition = List.of(
                new Position(Column.SIX, Row.FIVE),
                new Position(Column.SEVEN, Row.SIX),
                new Position(Column.EIGHT, Row.SEVEN));
            assertThat(findDirection).containsExactlyInAnyOrderElementsOf(expectedPosition);
        }

        @Test
        @DisplayName("아래 + (좌측 하단 대각x2)인 경우")
        void down_and_down_left_down_left() {
            Position arrival = new Position(Column.EIGHT, Row.THREE);
            List<Position> findDirection = elephant.calculateAllDirection(departure, arrival);
            List<Position> expectedPosition = List.of(
                new Position(Column.SIX, Row.FIVE),
                new Position(Column.SEVEN, Row.FOUR),
                new Position(Column.EIGHT, Row.THREE));
            assertThat(findDirection).containsExactlyInAnyOrderElementsOf(expectedPosition);
        }

        @Test
        @DisplayName("좌측 + (좌측 상단 대각x2)인 경우")
        void left_and_up_left_up_left() {
            Position arrival = new Position(Column.THREE, Row.TWO);
            List<Position> findDirection = elephant.calculateAllDirection(departure, arrival);
            List<Position> expectedPosition = List.of(
                new Position(Column.FIVE, Row.FOUR),
                new Position(Column.FOUR, Row.THREE),
                new Position(Column.THREE, Row.TWO));
            assertThat(findDirection).containsExactlyInAnyOrderElementsOf(expectedPosition);
        }

        @Test
        @DisplayName("좌측 + (좌측 하단 대각x2)인 경우")
        void left_and_down_left_down_left() {
            Position arrival = new Position(Column.SEVEN, Row.TWO);
            List<Position> findDirection = elephant.calculateAllDirection(departure, arrival);
            List<Position> expectedPosition = List.of(
                new Position(Column.FIVE, Row.FOUR),
                new Position(Column.SIX, Row.THREE),
                new Position(Column.SEVEN, Row.TWO));
            assertThat(findDirection).containsExactlyInAnyOrderElementsOf(expectedPosition);
        }

        @Test
        @DisplayName("우측 + (우측 상단 대각x2)인 경우")
        void right_and_up_right_up_right() {
            Position arrival = new Position(Column.THREE, Row.EIGHT);
            List<Position> findDirection = elephant.calculateAllDirection(departure, arrival);
            List<Position> expectedPosition = List.of(
                new Position(Column.FIVE, Row.SIX),
                new Position(Column.FOUR, Row.SEVEN),
                new Position(Column.THREE, Row.EIGHT));
            assertThat(findDirection).containsExactlyInAnyOrderElementsOf(expectedPosition);
        }

        @Test
        @DisplayName("우측 + (우측 하단 대각x2)인 경우")
        void right_and_down_right() {
            Position arrival = new Position(Column.SEVEN, Row.EIGHT);
            List<Position> findDirection = elephant.calculateAllDirection(departure, arrival);
            List<Position> expectedPosition = List.of(
                new Position(Column.FIVE, Row.SIX),
                new Position(Column.SIX, Row.SEVEN),
                new Position(Column.SEVEN, Row.EIGHT));
            assertThat(findDirection).containsExactlyInAnyOrderElementsOf(expectedPosition);
        }
    }

    @DisplayName("elephant가 갈 수 없는 경로라면, 예외를 던져야 한다")
    @Test
    void cannot_go_position_then_throw_exception() {
        Position arrival = new Position(Column.FIVE, Row.TWO);
        assertThatThrownBy(() -> elephant.calculateAllDirection(departure, arrival))
            .isInstanceOf(IllegalArgumentException.class);
    }

}
