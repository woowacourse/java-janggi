package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.Position;
import domain.Team;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PawnTest {

    @Test
    void 졸은_오른쪽으로_이동할_수_있다() {
        //given
        Pawn pawn = new Pawn(Team.CHO);

        //when
        List<Position> move = pawn.calculatePath(new Position(4, 4), new Position(4, 5));

        // then
        assertThat(move).isEqualTo(List.of());
    }

    @Test
    void 졸은_왼쪽으로_이동할_수_있다() {
        //given
        Pawn pawn = new Pawn(Team.CHO);

        //when
        List<Position> move = pawn.calculatePath(new Position(4, 4), new Position(4, 3));

        // then
        assertThat(move).isEqualTo(List.of());
    }

    @Test
    void 졸은_위으로_이동할_수_있다() {
        //given
        Pawn pawn = new Pawn(Team.CHO);

        //when
        List<Position> move = pawn.calculatePath(new Position(4, 4), new Position(3, 4));

        // then
        assertThat(move).isEqualTo(List.of());
    }

    @Test
    void 졸이_아래로_이동할_경우_예외를_발생시킨다() {
        //given
        Pawn pawn = new Pawn(Team.CHO);

        //when & then
        assertThatThrownBy(() -> pawn.calculatePath(new Position(4, 4), new Position(5, 4)))
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
    void 졸으로_두_칸_이동할_경우_예외를_발생시킨다(int movedRow, int movedColumn) {
        // given
        Pawn pawn = new Pawn(Team.CHO);
        int row = 4;
        int column = 4;
        Position src = new Position(row, column);
        Position dest = new Position(row + movedRow, column + movedColumn);

        // when & then
        Assertions.assertThatThrownBy(() -> pawn.calculatePath(src, dest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이 위치로 이동할 수 없습니다.");
    }

    @Test
    void 병은_아래로_이동할_수_있다() {
        //given
        Pawn pawn = new Pawn(Team.HAN);

        //when
        List<Position> move = pawn.calculatePath(new Position(4, 4), new Position(5, 4));

        // then
        assertThat(move).isEqualTo(List.of());
    }

    @Test
    void 병은_왼쪽으로_이동할_수_있다() {
        //given
        Pawn pawn = new Pawn(Team.HAN);

        //when
        List<Position> move = pawn.calculatePath(new Position(4, 4), new Position(4, 3));

        // then
        assertThat(move).isEqualTo(List.of());
    }

    @Test
    void 병은_오른쪽으로_이동할_수_있다() {
        //given
        Pawn pawn = new Pawn(Team.HAN);

        //when
        List<Position> move = pawn.calculatePath(new Position(4, 4), new Position(4, 5));

        // then
        assertThat(move).isEqualTo(List.of());
    }

    @Test
    void 병이_위로_이동할_경우_예외를_발생시킨다() {
        //given
        Pawn pawn = new Pawn(Team.HAN);

        //when & then
        assertThatThrownBy(() -> pawn.calculatePath(new Position(4, 4), new Position(3, 4)))
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
    void 병으로_두_칸_이동할_경우_예외를_발생시킨다(int movedRow, int movedColumn) {
        // given
        Pawn pawn = new Pawn(Team.HAN);
        int row = 4;
        int column = 4;
        Position src = new Position(row, column);
        Position dest = new Position(row + movedRow, column + movedColumn);

        // when & then
        Assertions.assertThatThrownBy(() -> pawn.calculatePath(src, dest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이 위치로 이동할 수 없습니다.");
    }
}