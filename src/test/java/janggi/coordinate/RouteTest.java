package janggi.coordinate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RouteTest {

    @Test
    @DisplayName("시작점과 끝나는 위치로 경로를 찾아야한다-직선-행-양수")
    void testRow1() {
        //given
        final Position departure = Position.of(1, 3);
        final Position destination = Position.of(4, 3);

        //when
        final List<Position> actual = Route.of(departure, destination).calculate();

        //then
        final List<Position> expected = List.of(Position.of(2, 3), Position.of(3, 3));
        assertThat(actual).containsExactlyElementsOf(expected);
    }

    @Test
    @DisplayName("시작점과 끝나는 위치로 경로를 찾아야한다-직선-행-음수")
    void testRow2() {
        //given
        final Position departure = Position.of(4, 3);
        final Position destination = Position.of(1, 3);

        //when
        final List<Position> actual = Route.of(departure, destination).calculate();

        //then
        final List<Position> expected = List.of(Position.of(3, 3), Position.of(2, 3));
        assertThat(actual).containsExactlyElementsOf(expected);
    }

    @Test
    @DisplayName("시작점과 끝나는 위치로 경로를 찾아야한다-직선-열-양수")
    void testColumn1() {
        //given
        final Position departure = Position.of(3, 1);
        final Position destination = Position.of(3, 4);

        //when
        final List<Position> actual = Route.of(departure, destination).calculate();

        //then
        final List<Position> expected = List.of(Position.of(3, 2), Position.of(3, 3));
        assertThat(actual).containsExactlyElementsOf(expected);
    }

    @Test
    @DisplayName("시작점과 끝나는 위치로 경로를 찾아야한다-직선-열-음수")
    void testColumn2() {
        //given
        final Position departure = Position.of(3, 4);
        final Position destination = Position.of(3, 1);

        //when
        final List<Position> actual = Route.of(departure, destination).calculate();

        //then
        final List<Position> expected = List.of(Position.of(3, 3), Position.of(3, 2));
        assertThat(actual).containsExactlyElementsOf(expected);
    }

    @Test
    @DisplayName("시작점과 끝나는 위치로 경로를 찾아야한다-곡선-양수/양수")
    void testCurve1() {
        //given
        final Position departure = Position.of(1, 1);
        final Position destination = Position.of(4, 3);

        //when
        final List<Position> actual = Route.of(departure, destination).calculate();

        //then
        final List<Position> expected = List.of(Position.of(2, 1), Position.of(3, 2));
        assertThat(actual).containsExactlyElementsOf(expected);
    }

    @Test
    @DisplayName("시작점과 끝나는 위치로 경로를 찾아야한다-곡선-음수/음수")
    void testCurve2() {
        //given
        final Position departure = Position.of(4, 3);
        final Position destination = Position.of(1, 1);

        //when
        final List<Position> actual = Route.of(departure, destination).calculate();

        //then
        final List<Position> expected = List.of(Position.of(3, 3), Position.of(2, 2));
        assertThat(actual).containsExactlyElementsOf(expected);
    }

    @Test
    @DisplayName("시작점과 끝나는 위치로 경로를 찾아야한다-곡선-양수/음수")
    void testCurve3() {
        //given
        final Position departure = Position.of(4, 3);
        final Position destination = Position.of(7, 1);

        //when
        final List<Position> actual = Route.of(departure, destination).calculate();

        //then
        final List<Position> expected = List.of(Position.of(5, 3), Position.of(6, 2));
        assertThat(actual).containsExactlyElementsOf(expected);
    }

    @Test
    @DisplayName("시작점과 끝나는 위치로 경로를 찾아야한다-곡선-음수/양수")
    void testCurve4() {
        //given
        final Position departure = Position.of(4, 3);
        final Position destination = Position.of(7, 5);

        //when
        final List<Position> actual = Route.of(departure, destination).calculate();

        //then
        final List<Position> expected = List.of(Position.of(5, 3), Position.of(6, 4));
        assertThat(actual).containsExactlyElementsOf(expected);
    }

    @Test
    @DisplayName("경로를 계산할 때, 출발지와 도착지를 포함할 수 있다")
    void canIncludeDepartureAndDestination() {
        // given
        final Position departure = Position.of(1, 1);
        final Position destination = Position.of(5, 5);
        final Route route = Route.of(departure, destination);

        // when
        final List<Position> withoutDepartureAndDestination = route.calculate();
        final List<Position> withoutDeparture = route.calculateWithDestination();
        final List<Position> all = route.calculateWithDepartureAndDestination();

        // then
        assertThat(withoutDepartureAndDestination).doesNotContain(departure, destination);
        assertThat(withoutDeparture).doesNotContain(departure);
        assertThat(all).contains(departure, destination);
    }

    @Test
    @DisplayName("출발지와 도착지가 같은 경우와 같이 경로 길이가 짧을 때, 시작지와 도착지를 제외하려 해도 안전하게 처리된다")
    void canExcludeDepartureAndDestinationWhenShortRoute() {
        // given
        final Position departure = Position.of(1, 1);
        final Position destination = Position.of(1, 1);
        final Route route = Route.of(departure, destination);

        // when
        final List<Position> withoutDepartureAndDestination = route.calculate();
        final List<Position> withoutDeparture = route.calculateWithDestination();
        final List<Position> all = route.calculateWithDepartureAndDestination();

        // then
        assertThat(withoutDepartureAndDestination).doesNotContain(departure, destination);
        assertThat(withoutDeparture).doesNotContain(departure);
        assertThat(all).contains(departure, destination);
    }
}
