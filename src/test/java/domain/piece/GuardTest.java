package domain.piece;

import domain.Position;
import domain.Team;
import domain.player.Player;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class GuardTest {


    @DisplayName("사는 대각선,상하좌우로 이동할 수 있다.")
    @ParameterizedTest
    @CsvSource({
            "2,6", "2,4", "1,5", "3,5",
            "3,4", "1,4", "3,6", "1,6",
    })
    void test(int row, int column) {

        //given
        Player player = new Player(1, "짱구", Team.RED);
        Guard guard = new Guard(player, 3);
        Position startPosition = new Position(2, 5);
        Position targetPosition = new Position(row, column);

        //when
        List<Position> resultMove = guard.calculatePath(startPosition, targetPosition);

        // then
        Assertions.assertThat(resultMove).isEqualTo(List.of());
    }
}
