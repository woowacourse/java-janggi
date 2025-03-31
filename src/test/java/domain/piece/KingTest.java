package domain.piece;

import domain.Position;
import domain.Team;
import domain.player.Player;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class KingTest {

    @DisplayName("궁은 대각선,상하좌우로 이동할 수 있다.")
    @ParameterizedTest
    @CsvSource({
            "2,6", "2,4", "1,5", "3,5",
            "3,4", "1,4", "3,6", "1,6",
    })
    void test(int row, int column) {

        //given
        Player player = new Player(1, "짱구", Team.RED);
        King king = new King(player, 0);
        Position startPosition = new Position(2, 5);
        Position targetPosition = new Position(row, column);

        //when
        List<Position> resultMove = king.calculatePath(startPosition, targetPosition);

        // then
        Assertions.assertThat(resultMove).isEqualTo(List.of());
    }

    @DisplayName("궁은 꼭지점에서 중앙으로 이동할 수 있다.")
    @ParameterizedTest
    @CsvSource({
            "2,5",
    })
    void tes2(int row, int column) {

        //given
        Player player = new Player(1, "짱구", Team.RED);
        King king = new King(player, 0);
        Position startPosition = new Position(3, 6);
        Position targetPosition = new Position(row, column);

        //when
        List<Position> resultMove = king.calculatePath(startPosition, targetPosition);

        // then
        Assertions.assertThat(resultMove).isEqualTo(List.of());
    }

    @DisplayName("궁이 이동할 수없는 대각선이라면 예외가 발생한다.")
    @Test
    void tes3() {

        //given
        Player player = new Player(1, "짱구", Team.RED);
        King king = new King(player, 0);
        // when & then
        Assertions.assertThatThrownBy(() -> king.calculatePath(new Position(3, 5), new Position(2, 6)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이 위치로 이동할 수 없습니다.");
    }


    @DisplayName("킹이 이동할 수 없는 위치라면 예외가 발생한다.")
    @Test
    void test4() {

        //given
        Player player = new Player(1, "짱구", Team.RED);
        King king = new King(player, 0);

        // when & then
        Assertions.assertThatThrownBy(() -> king.calculatePath(new Position(4, 1), new Position(4, 3)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이 위치로 이동할 수 없습니다.");
    }
}
