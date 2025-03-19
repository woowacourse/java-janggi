package domain.piece;

import domain.Position;
import domain.Team;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class GuardTest {


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
        Guard guard = new Guard(Team.BLUE);
        Position startPosition = new Position(4, 4);
        Position targetPosition = new Position(row, column);

        //when
        List<Position> resultMove = guard.calculatePath(startPosition, targetPosition);

        // then
        Assertions.assertThat(resultMove).isEqualTo(List.of());
    }
}
