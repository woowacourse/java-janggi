package domain.piece;

import domain.Position;
import domain.Team;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class SaTest {

    @ParameterizedTest
    @CsvSource({
            "1, 0",
            "0, 1",
            "-1, 0",
            "0, -1",
            "1, 1",
            "1, -1",
            "-1, 1",
            "-1, -1",
    })
    void 사은_초나라_궁성_중앙에서_상하좌우대각선으로_이동할_수_있다(int movedRow, int movedColumn) {
        //given
        Sa sa = new Sa(Team.CHO);
        int row = 9;
        int column = 5;
        Position startPosition = new Position(row, column);
        Position targetPosition = new Position(row + movedRow, column + movedColumn);

        //when
        List<Position> actual = sa.calculatePath(startPosition, targetPosition);

        // then
        Assertions.assertThat(actual).isEqualTo(List.of());
    }

    @ParameterizedTest
    @CsvSource({
            "1, 0",
            "0, 1",
            "-1, 0",
            "0, -1",
            "1, 1",
            "1, -1",
            "-1, 1",
            "-1, -1",
    })
    void 사은_한나라_궁성_중앙에서_상하좌우대각선으로_이동할_수_있다(int movedRow, int movedColumn) {
        //given
        Sa sa = new Sa(Team.HAN);
        int row = 2;
        int column = 5;
        Position startPosition = new Position(row, column);
        Position targetPosition = new Position(row + movedRow, column + movedColumn);

        //when
        List<Position> actual = sa.calculatePath(startPosition, targetPosition);

        // then
        Assertions.assertThat(actual).isEqualTo(List.of());
    }

    @ParameterizedTest
    @CsvSource({
            "1, 0",
            "0, 1",
            "1, 1",
    })
    void 사은_한나라_궁성_왼쪽위에서는_궁성안으로만_이동할_수_있다(int movedRow, int movedColumn) {
        //given
        Sa sa = new Sa(Team.HAN);
        int row = 1;
        int column = 4;
        Position startPosition = new Position(row, column);
        Position targetPosition = new Position(row + movedRow, column + movedColumn);

        //when
        List<Position> actual = sa.calculatePath(startPosition, targetPosition);

        // then
        Assertions.assertThat(actual).isEqualTo(List.of());
    }

    @ParameterizedTest
    @CsvSource({
            "1, 4, 0, -1",
            "1, 6, 0, 1",
            "2, 6, 0, 1",
            "3, 6, 0, 1",
            "3, 6, 1, 0",
            "3, 4, 0, -1",
            "3, 4, 1, 0",
            "2, 4, 0, -1"
    })
    void 사가_한나라_궁성에서_궁성_밖으로_이동하면_예외를_발생시킨다() {
        //given
        Sa sa = new Sa(Team.HAN);
        Position startPosition = new Position(1, 4);
        Position targetPosition = new Position(1, 3);

        //when & then
        Assertions.assertThatThrownBy(() -> sa.calculatePath(startPosition, targetPosition))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @CsvSource({
            "1, 5, 2, 6",
            "1, 5, 2, 4",
            "2, 4, 1, 5",
            "2, 4, 3, 5",
            "3, 5, 2, 4",
            "3, 5, 2, 6",
            "2, 6, 1, 5",
            "2, 6, 3, 5",
    })
    void 사가_한나라_궁성_가장자리중앙에서_다른가장자리중앙으로_이동하면_예외를_발생시킨다(int row, int column, int newRow, int newColumn) {
        //given
        Sa sa = new Sa(Team.HAN);
        Position startPosition = new Position(row, column);
        Position targetPosition = new Position(newRow, newColumn);

        //when & then
        Assertions.assertThatThrownBy(() -> sa.calculatePath(startPosition, targetPosition))
                .isInstanceOf(IllegalArgumentException.class);
    }


    @Test
    void 사는_두_칸_이동할_수_없다() {
        // given
        Sa sa = new Sa(Team.HAN);
        Position src = new Position(4, 1);
        Position dest = new Position(4, 3);

        // when & then
        Assertions.assertThatThrownBy(() -> sa.calculatePath(src, dest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이 위치로 이동할 수 없습니다.");
    }
}
