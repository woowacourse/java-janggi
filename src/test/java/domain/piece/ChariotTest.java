package domain.piece;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.Position;
import domain.Team;
import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ChariotTest {

    @DisplayName("차의 이동 경로를 계산할 수 있다")
    @Test
    void test1() {
        Chariot chariot = new Chariot(Team.RED);

        List<Position> path1 = chariot.calculatePath(new Position(1, 1), new Position(1, 4));
        List<Position> path2 = chariot.calculatePath(new Position(1, 1), new Position(4, 1));
        List<Position> path3 = chariot.calculatePath(new Position(6, 1), new Position(3, 1));
        List<Position> expected1 = List.of(new Position(1, 2), new Position(1, 3));
        List<Position> expected2 = List.of(new Position(2, 1), new Position(3, 1));
        List<Position> expected3 = List.of(new Position(5, 1), new Position(4, 1));

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(path1).isEqualTo(expected1);
            softAssertions.assertThat(path2).isEqualTo(expected2);
            softAssertions.assertThat(path3).isEqualTo(expected3);

        });
    }

    @DisplayName("차로 이동할 수 없는 위치인 경우 예외를 발생시킨다")
    @Test
    void test3() {
        Chariot chariot = new Chariot(Team.RED);

        assertThatThrownBy(() -> chariot.calculatePath(new Position(1, 1), new Position(2, 2)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이 위치로는 움직일 수 없습니다.");
    }
}
