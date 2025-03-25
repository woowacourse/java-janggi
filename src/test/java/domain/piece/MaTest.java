package domain.piece;

import domain.Position;
import domain.Team;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class MaTest {

    @Test
    void 마가_왼쪽_왼쪽위_대각선으로_이동하는_경로를_계산할_수_있다() {
        // given
        Ma ma = new Ma(Team.HAN);
        Position src = new Position(4, 4);
        Position dest = new Position(3, 2);

        // when
        List<Position> moves = ma.calculatePath(src, dest);

        // then
        List<Position> expected = List.of(new Position(4, 3));
        Assertions.assertThat(moves).isEqualTo(expected);
    }

    @Test
    void 마가_왼쪽_왼쪽아래_대각선으로_이동하는_경로를_계산할_수_있다() {
        // given
        Ma ma = new Ma(Team.HAN);
        Position src = new Position(4, 4);
        Position dest = new Position(5, 2);

        // when
        List<Position> moves = ma.calculatePath(src, dest);

        // then
        List<Position> expected = List.of(new Position(4, 3));
        Assertions.assertThat(moves).isEqualTo(expected);
    }

    @Test
    void 마가_위_왼쪽위_대각선으로_이동하는_경로를_계산할_수_있다() {
        // given
        Ma ma = new Ma(Team.HAN);
        Position src = new Position(4, 4);
        Position dest = new Position(2, 3);

        // when
        List<Position> moves = ma.calculatePath(src, dest);

        // then
        List<Position> expected = List.of(new Position(3, 4));
        Assertions.assertThat(moves).isEqualTo(expected);
    }

    @Test
    void 마가_위_오른쪽위_대각선으로_이동하는_경로를_계산할_수_있다() {
        // given
        Ma ma = new Ma(Team.HAN);
        Position src = new Position(4, 4);
        Position dest = new Position(2, 5);

        // when
        List<Position> moves = ma.calculatePath(src, dest);

        // then
        List<Position> expected = List.of(new Position(3, 4));
        Assertions.assertThat(moves).isEqualTo(expected);
    }

    @Test
    void 마가_오른쪽_오른쪽위_대각선으로_이동하는_경로를_계산할_수_있다() {
        // given
        Ma ma = new Ma(Team.HAN);
        Position src = new Position(4, 4);
        Position dest = new Position(3, 6);

        // when
        List<Position> moves = ma.calculatePath(src, dest);

        // then
        List<Position> expected = List.of(new Position(4, 5));
        Assertions.assertThat(moves).isEqualTo(expected);
    }

    @Test
    void 마가_오른쪽_오른쪽아래_대각선으로_이동하는_경로를_계산할_수_있다() {
        // given
        Ma ma = new Ma(Team.HAN);
        Position src = new Position(4, 4);
        Position dest = new Position(5, 6);

        // when
        List<Position> moves = ma.calculatePath(src, dest);

        // then
        List<Position> expected = List.of(new Position(4, 5));
        Assertions.assertThat(moves).isEqualTo(expected);
    }

    @Test
    void 마가_아래_오른쪽아래_대각선으로_이동하는_경로를_계산할_수_있다() {
        // given
        Ma ma = new Ma(Team.HAN);
        Position src = new Position(4, 4);
        Position dest = new Position(6, 5);

        // when
        List<Position> moves = ma.calculatePath(src, dest);

        // then
        List<Position> expected = List.of(new Position(5, 4));
        Assertions.assertThat(moves).isEqualTo(expected);
    }

    @Test
    void 마가_아래_왼쪽아래_대각선으로_이동하는_경로를_계산할_수_있다() {
        // given
        Ma ma = new Ma(Team.HAN);
        Position src = new Position(4, 4);
        Position dest = new Position(6, 3);

        // when
        List<Position> moves = ma.calculatePath(src, dest);

        // then
        List<Position> expected = List.of(new Position(5, 4));
        Assertions.assertThat(moves).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "1, 1",
            "1, -1",
            "-1, 1",
            "-1, -1",
            "1, 0",
            "0, 1",
            "-1, 0",
            "0, -1"
    })
    void 마로_한_칸_이동할_경우_예외를_발생시킨다(int movedRow, int movedColumn) {
        // given
        Ma ma = new Ma(Team.HAN);
        int row = 4;
        int column = 4;
        Position src = new Position(row, column);
        Position dest = new Position(row + movedRow, column + movedColumn);

        // when & then
        Assertions.assertThatThrownBy(() -> ma.calculatePath(src, dest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이 위치로 이동할 수 없습니다.");
    }

    @ParameterizedTest
    @CsvSource({
            "2, 2",
            "2, -2",
            "-2, 2",
            "-2, -2",
            "2, 0",
            "0, 2",
            "-2, 0",
            "0, -2"
    })
    void 마로_두_칸_이동할_경우_예외를_발생시킨다(int movedRow, int movedColumn) {
        // given
        Ma ma = new Ma(Team.HAN);
        int row = 4;
        int column = 4;
        Position src = new Position(row, column);
        Position dest = new Position(row + movedRow, column + movedColumn);

        // when & then
        Assertions.assertThatThrownBy(() -> ma.calculatePath(src, dest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이 위치로 이동할 수 없습니다.");
    }
}
