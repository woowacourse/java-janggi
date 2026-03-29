package janggi.position;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.model.position.Column;
import janggi.model.position.Position;
import janggi.model.position.PositionPath;
import janggi.model.position.Row;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PositionPathTest {


    @DisplayName("가장 마지막에 있는 위치를 반환한다.")
    @Test
    void getLast() {
        //given
        PositionPath positionPath = new PositionPath(List.of(
                new Position(Row.ZERO, Column.EIGHT),
                new Position(Row.ONE, Column.EIGHT),
                new Position(Row.ONE, Column.SEVEN)
        ));

        //when & then
        assertThat(positionPath.getLast())
                .isEqualTo(new Position(Row.ONE, Column.SEVEN));
    }

    @DisplayName("앞뒤에 새로운 위치를 삽입한 경로를 반환한다.")
    @Test
    void addFirstAndLast() {
        //given
        PositionPath path = new PositionPath(List.of(
                new Position(Row.ZERO, Column.EIGHT),
                new Position(Row.ONE, Column.EIGHT),
                new Position(Row.ONE, Column.SEVEN)
        ));

        //when
        PositionPath added = path.addFirstAndLast(
                new Position(Row.ZERO, Column.SEVEN),
                new Position(Row.TWO, Column.SEVEN)
        );

        //then
        assertThat(added.getFirst())
                .isEqualTo(new Position(Row.ZERO, Column.SEVEN));
        assertThat(added.getLast())
                .isEqualTo(new Position(Row.TWO, Column.SEVEN));
    }

    @DisplayName("기존의 맨 뒤 위치와 한 칸 거리가 아니면 예외가 발생한다.")
    @Test
    void addFirstAndLast_wrong_last() {
        //given
        PositionPath path = new PositionPath(List.of(
                new Position(Row.ZERO, Column.EIGHT),
                new Position(Row.ONE, Column.EIGHT),
                new Position(Row.ONE, Column.SEVEN)
        ));

        //when & then
        assertThatThrownBy(() ->
                path.addFirstAndLast(
                        new Position(Row.ZERO, Column.SEVEN),
                        new Position(Row.SEVEN, Column.NINE))
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("연결할 수 없습니다.");
    }


    @DisplayName("기존의 맨 앞 위치와 한칸 거리가 아니면 예외가 발생한다.")
    @Test
    void addFirstAndLast_wrong_first() {
        //given
        PositionPath path = new PositionPath(List.of(
                new Position(Row.ZERO, Column.EIGHT),
                new Position(Row.ONE, Column.EIGHT),
                new Position(Row.ONE, Column.SEVEN)
        ));

        //when & then
        assertThatThrownBy(() ->
                path.addFirstAndLast(
                        new Position(Row.SEVEN, Column.NINE),
                        new Position(Row.TWO, Column.SEVEN))
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("연결할 수 없습니다.");
    }

    @DisplayName("다른 경로와 연결 시 뒷 부분에 붙는다.")
    @Test
    void concatenate() {
        //given
        PositionPath path = new PositionPath(List.of(
                new Position(Row.ONE, Column.EIGHT),
                new Position(Row.ONE, Column.SEVEN)
        ));
        PositionPath other = new PositionPath(List.of(
                new Position(Row.ONE, Column.SEVEN),
                new Position(Row.TWO, Column.SEVEN)
        ));

        //when& then
        assertThat(path.concatenate(other).getLast())
                .isEqualTo(new Position(Row.TWO, Column.SEVEN));
    }

    @DisplayName("앞 부분의 마지막과 뒷 부분의 시작 위치가 같지 않으면 예외가 발생한다.")
    @Test
    void concatenate_can_not() {
        //given
        PositionPath path = new PositionPath(List.of(
                new Position(Row.ONE, Column.EIGHT),
                new Position(Row.ONE, Column.SEVEN)
        ));
        PositionPath other = new PositionPath(List.of(
                new Position(Row.EIGHT, Column.SEVEN),
                new Position(Row.TWO, Column.SEVEN)
        ));

        //when& then
        assertThatThrownBy(() -> path.concatenate(other))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("연결할 수 없습니다.");
    }
}