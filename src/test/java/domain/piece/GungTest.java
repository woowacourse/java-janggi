package domain.piece;

import domain.Position;
import domain.Team;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class GungTest {

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
    void 궁은_초나라_궁성_중앙에서_상하좌우대각선으로_이동할_수_있다(int movedRow, int movedColumn) {
        //given
        int row = 9;
        int column = 5;
        Position startPosition = new Position(row, column);
        Position targetPosition = new Position(row + movedRow, column + movedColumn);
        Gung gung = new Gung(Team.CHO, startPosition);

        //when
        List<Position> actual = gung.calculatePath(startPosition, targetPosition);

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
    void 궁은_한나라_궁성_중앙에서_상하좌우대각선으로_이동할_수_있다(int movedRow, int movedColumn) {
        //given
        int row = 2;
        int column = 5;
        Position startPosition = new Position(row, column);
        Position targetPosition = new Position(row + movedRow, column + movedColumn);
        Gung gung = new Gung(Team.HAN, startPosition);

        //when
        List<Position> actual = gung.calculatePath(startPosition, targetPosition);

        // then
        Assertions.assertThat(actual).isEqualTo(List.of());
    }

    @ParameterizedTest
    @CsvSource({
            "1, 0",
            "0, 1",
            "1, 1",
    })
    void 궁은_한나라_궁성_왼쪽위에서는_궁성안으로만_이동할_수_있다(int movedRow, int movedColumn) {
        //given
        int row = 1;
        int column = 4;
        Position startPosition = new Position(row, column);
        Position targetPosition = new Position(row + movedRow, column + movedColumn);
        Gung gung = new Gung(Team.HAN, startPosition);

        //when
        List<Position> actual = gung.calculatePath(startPosition, targetPosition);

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
    void 궁이_한나라_궁성에서_궁성_밖으로_이동하면_예외를_발생시킨다() {
        //given
        Position startPosition = new Position(1, 4);
        Position targetPosition = new Position(1, 3);
        Gung gung = new Gung(Team.HAN, startPosition);

        //when & then
        Assertions.assertThatThrownBy(() -> gung.calculatePath(startPosition, targetPosition))
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
    void 궁이_한나라_궁성_가장자리중앙에서_다른가장자리중앙으로_이동하면_예외를_발생시킨다(int row, int column, int newRow, int newColumn) {
        //given
        Position startPosition = new Position(row, column);
        Position targetPosition = new Position(newRow, newColumn);
        Gung gung = new Gung(Team.HAN, startPosition);

        //when & then
        Assertions.assertThatThrownBy(() -> gung.calculatePath(startPosition, targetPosition))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 궁은_두_칸_이동할_수_없다() {
        // given
        Position src = new Position(4, 1);
        Position dest = new Position(4, 3);
        Gung gung = new Gung(Team.HAN, src);

        // when & then
        Assertions.assertThatThrownBy(() -> gung.calculatePath(src, dest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이 위치로 이동할 수 없습니다.");
    }
}
