import janggi.Position;
import janggi.Route;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

public class RouteTest {

    @Test
    @DisplayName("시작점과 끝나는 위치로 경로를 찾아야한다-직선-행-양수")
    void testRow1() {
        //given
        Position departure = Position.of(1, 3);
        Position destination = Position.of(4, 3);

        //when
        List<Position> expected = Route.of(departure, destination);

        //then
        List<Position> actual = List.of(Position.of(2, 3), Position.of(3, 3));
        Assertions.assertThat(expected).containsExactlyElementsOf(actual);
    }

    @Test
    @DisplayName("시작점과 끝나는 위치로 경로를 찾아야한다-직선-행-음수")
    void testRow2() {
        //given
        Position departure = Position.of(4, 3);
        Position destination = Position.of(1, 3);

        //when
        List<Position> expected = Route.of(departure, destination);

        //then
        List<Position> actual = List.of(Position.of(3, 3), Position.of(2, 3));
        Assertions.assertThat(expected).containsExactlyElementsOf(actual);
    }

    @Test
    @DisplayName("시작점과 끝나는 위치로 경로를 찾아야한다-직선-열-양수")
    void testColumn1() {
        //given
        Position departure = Position.of(3, 1);
        Position destination = Position.of(3, 4);

        //when
        List<Position> expected = Route.of(departure, destination);

        //then
        List<Position> actual = List.of(Position.of(3, 2), Position.of(3, 3));
        Assertions.assertThat(expected).containsExactlyElementsOf(actual);
    }

    @Test
    @DisplayName("시작점과 끝나는 위치로 경로를 찾아야한다-직선-열-음수")
    void testColumn2() {
        //given
        Position departure = Position.of(3, 4);
        Position destination = Position.of(3, 1);

        //when
        List<Position> expected = Route.of(departure, destination);

        //then
        List<Position> actual = List.of(Position.of(3, 3), Position.of(3, 2));
        Assertions.assertThat(expected).containsExactlyElementsOf(actual);
    }

    @Test
    @DisplayName("시작점과 끝나는 위치로 경로를 찾아야한다-곡선-양수/양수")
    void testCurve1() {
        //given
        Position departure = Position.of(1, 1);
        Position destination = Position.of(4, 3);

        //when
        List<Position> expected = Route.of(departure, destination);

        //then
        List<Position> actual = List.of(Position.of(2, 1), Position.of(3, 2));
        Assertions.assertThat(expected).containsExactlyElementsOf(actual);
    }

    @Test
    @DisplayName("시작점과 끝나는 위치로 경로를 찾아야한다-곡선-음수/음수")
    void testCurve2() {
        //given
        Position departure = Position.of(4, 3);
        Position destination = Position.of(1, 1);

        //when
        List<Position> expected = Route.of(departure, destination);

        //then
        List<Position> actual = List.of(Position.of(3, 3), Position.of(2, 2));
        Assertions.assertThat(expected).containsExactlyElementsOf(actual);
    }

    @Test
    @DisplayName("시작점과 끝나는 위치로 경로를 찾아야한다-곡선-양수/음수")
    void testCurve3() {
        //given
        Position departure = Position.of(4, 3);
        Position destination = Position.of(7, 1);

        //when
        List<Position> expected = Route.of(departure, destination);

        //then
        List<Position> actual = List.of(Position.of(5, 3), Position.of(6, 2));
        Assertions.assertThat(expected).containsExactlyElementsOf(actual);
    }

    @Test
    @DisplayName("시작점과 끝나는 위치로 경로를 찾아야한다-곡선-음수/양수")
    void testCurve4() {
        //given
        Position departure = Position.of(4, 3);
        Position destination = Position.of(7, 5);

        //when
        List<Position> expected = Route.of(departure, destination);

        //then
        List<Position> actual = List.of(Position.of(5, 3), Position.of(6, 4));
        Assertions.assertThat(expected).containsExactlyElementsOf(actual);
    }
}
