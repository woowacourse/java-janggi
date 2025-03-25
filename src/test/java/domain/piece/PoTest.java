package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.Position;
import domain.Team;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PoTest {

    @Test
    void 포가_위로_이동하는_경로를_계산할_수_있다() {
        // given
        Po po = new Po(Team.HAN);
        Position src = new Position(4, 1);
        Position dest = new Position(1, 1);

        // when
        List<Position> path = po.calculatePath(src, dest);

        // then
        List<Position> expected = List.of(new Position(3, 1), new Position(2, 1));
        assertThat(path).isEqualTo(expected);
    }

    @Test
    void 포가_아래로_이동하는_경로를_계산할_수_있다() {
        // given
        Po po = new Po(Team.HAN);
        Position src = new Position(1, 1);
        Position dest = new Position(4, 1);

        // when
        List<Position> path = po.calculatePath(src, dest);

        // then
        List<Position> expected = List.of(new Position(2, 1), new Position(3, 1));
        assertThat(path).isEqualTo(expected);
    }

    @Test
    void 포가_왼쪽으로_이동하는_경로를_계산할_수_있다() {
        // given
        Po po = new Po(Team.HAN);
        Position src = new Position(4, 4);
        Position dest = new Position(1, 4);

        // when
        List<Position> path = po.calculatePath(src, dest);

        // then
        List<Position> expected = List.of(new Position(3, 4), new Position(2, 4));
        assertThat(path).isEqualTo(expected);
    }

    @Test
    void 포가_오른쪽으로_이동하는_경로를_계산할_수_있다() {
        // given
        Po po = new Po(Team.HAN);
        Position src = new Position(1, 1);
        Position dest = new Position(1, 4);

        // when
        List<Position> path1 = po.calculatePath(src, dest);

        // then
        List<Position> expected1 = List.of(new Position(1, 2), new Position(1, 3));
        assertThat(path1).isEqualTo(expected1);
    }

    @ParameterizedTest
    @CsvSource({
            "1, 1",
            "1, -1",
            "-1, 1",
            "-1, -1"
    })
    void 포로_이동할_수_없는_위치인_경우_예외를_발생시킨다(int movedRow, int movedColumn) {
        // given
        Po po = new Po(Team.HAN);
        int row = 5;
        int column = 5;
        Position src = new Position(row, column);
        Position dest = new Position(row + movedRow, column + movedColumn);
        // when & then
        assertThatThrownBy(() -> po.calculatePath(src, dest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이 위치로는 움직일 수 없습니다.");
    }
}