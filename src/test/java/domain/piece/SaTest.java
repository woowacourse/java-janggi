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
            "0, -1"
    })
    void 궁은_상하좌우로_이동할_수_있다(int movedRow, int movedColumn) {
        //given
        Sa sa = new Sa(Team.CHO);
        int row = 4;
        int column = 4;
        Position startPosition = new Position(row, column);
        Position targetPosition = new Position(row + movedRow, column + movedColumn);

        //when
        List<Position> actual = sa.calculatePath(startPosition, targetPosition);

        // then
        Assertions.assertThat(actual).isEqualTo(List.of());
    }

    @Test
    void 궁은_두_칸_이동할_수_없다() {
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
