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

        List<Move> moves1 = chariot.calculatePath(new Position(1, 1), new Position(1, 6));
        List<Move> moves2 = chariot.calculatePath(new Position(1, 1), new Position(7, 1));
        List<Move> moves3 = chariot.calculatePath(new Position(6, 1), new Position(1, 1));
        List<Move> expected1 = List.of(Move.RIGHT, Move.RIGHT, Move.RIGHT, Move.RIGHT, Move.RIGHT);
        List<Move> expected2 = List.of(Move.BACK, Move.BACK, Move.BACK, Move.BACK, Move.BACK, Move.BACK);
        List<Move> expected3 = List.of(Move.FRONT, Move.FRONT, Move.FRONT, Move.FRONT, Move.FRONT);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(moves1).isEqualTo(expected1);
            softAssertions.assertThat(moves2).isEqualTo(expected2);
            softAssertions.assertThat(moves3).isEqualTo(expected3);

        });
    }

    @DisplayName("동일한 위치로 움직일 경우 예외를 발생시킨다")
    @Test
    void test2() {
        Chariot chariot = new Chariot(Team.RED);

        assertThatThrownBy(() -> chariot.calculatePath(new Position(1, 1), new Position(1, 1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("말을 움직여 주세요");
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
