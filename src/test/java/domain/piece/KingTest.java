package domain.piece;

import domain.Position;
import domain.Team;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class KingTest {

    @DisplayName("사는 상하좌우로 이동할 수 있다.")
    @ParameterizedTest
    @CsvSource({
            "5,4,BACK",
            "3,4,FRONT",
            "4,3,LEFT",
            "4,5,RIGHT"
    })
    void test(int row, int column, Move move) {

        //given
        King king = new King(Team.BLUE);
        Position startPosition = new Position(4, 4);
        Position targetPosition = new Position(row, column);

        //when
        List<Move> resultMove = king.calculatePath(startPosition, targetPosition);

        // then
        Assertions.assertThat(resultMove).isEqualTo(List.of(move));
    }

    @DisplayName("킹이 이동할 수 없는 위치라면 예외가 발생한다.")
    @Test
    void test1() {

        //given
        King king = new King(Team.RED);

        // when & then
        Assertions.assertThatThrownBy(() -> king.calculatePath(new Position(4, 1), new Position(4, 3)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이 위치로 이동할 수 없습니다.");
    }
}
