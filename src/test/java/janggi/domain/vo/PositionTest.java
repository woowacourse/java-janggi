package janggi.domain.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

import janggi.domain.Direction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

public class PositionTest {

    @ParameterizedTest
    @CsvSource({
            "0, 0",
            "9, 8",
            "1,2 "
    })
    void 올바른_위치가_생성된다(int row, int col) {
        assertDoesNotThrow(() -> new Position(row,col));
    }

@ParameterizedTest
    @CsvSource({
            "-1, 0",
            "10, 0",
            "0, -1",
            "0, 9"
    })
    void 범위_밖_좌표_입력시_예외가_발생한다(int row, int col) {
        assertThatThrownBy(() -> new Position(row,col))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("범위 밖");
    }

    @Test
    void 좌표_값이_같으면_동일한_객체로_판단한다(){
        Position position1 = new Position(1, 2);
        Position position2 = new Position(1, 2);

        assertThat(position1).isEqualTo(position2);
    }

    @Test
    void 길이_있는_경우_generatePath는_경로_리스트를_반환한다() {
        // given
        Position from = new Position(0, 0);
        List<Direction> directions = List.of(Direction.EAST, Direction.EAST);

        // when
        List<Position> path = from.generatePath(directions);

        // then
        assertThat(path).hasSize(2);
        assertThat(path).containsExactly(new Position(0, 1), new Position(0, 2));
    }

    @Test
    void 길_구하는_중간에_보드를_이탈하면_generatePath는_빈_리스트를_반환한다() {
        // given
        Position from = new Position(0, 0);
        List<Direction> directions = List.of(Direction.WEST);

        // when
        List<Position> path = from.generatePath(directions);

        // then
        assertThat(path).isEmpty();
    }

    @ParameterizedTest
    @CsvSource({
            "0, 0, 0, 5, true",
            "0, 0, 5, 0, true",
            "0, 0, 1, 1, false",
            "1, 2, 3, 4, false"
    })
    void isStraightLine_직선_확인_테스트(int fromRow, int fromCol, int toRow, int toCol, boolean expected) {
        // given
        Position from = new Position(fromRow, fromCol);
        Position to = new Position(toRow, toCol);

        // when, then
        assertThat(from.isStraightLine(to)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "0, 4, NORTH, SOUTH",
            "9, 4, SOUTH, NORTH",
            "4, 8, EAST, WEST",
            "4, 0, WEST, EAST"
    })
    void hasNext_경계값_테스트(int row, int col, Direction outDirection, Direction inDirection) {
        Position edge = new Position(row, col);

        assertThat(edge.hasNext(outDirection)).isFalse();
        assertThat(edge.hasNext(inDirection)).isTrue();
    }
}
