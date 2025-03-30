package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.board.Dynasty;
import janggi.domain.board.JanggiBoard;
import janggi.domain.board.Point;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("졸 테스트")
public class SoldierTest {

    @DisplayName("한나라 폰은 상좌우 한칸 움직일 수 있다")
    @ParameterizedTest
    @CsvSource({
            "1, 1, 2, 1",
            "1, 2, 1, 1",
            "1, 2, 1, 3",
    })
    void isMovable_Han(int x1, int y1, int x2, int y2) {
        //givenR
        JanggiBoard janggiBoard = new JanggiBoard(Map.of());
        Soldier soldier = new HanSoldier(Dynasty.HAN);

        //when
        boolean result = soldier.canMove(janggiBoard, Dynasty.HAN, new Point(x1, y1), new Point(x2, y2));

        //then
        assertThat(result).isTrue();
    }

    @DisplayName("초나라 폰은 상좌우 한칸 움직일 수 있다")
    @ParameterizedTest
    @CsvSource({
            "2, 1, 1, 1",
            "1, 2, 1, 1",
            "1, 2, 1, 3",
    })
    void isMovable_Chu(int x1, int y1, int x2, int y2) {
        //givenR
        JanggiBoard janggiBoard = new JanggiBoard(Map.of());
        Soldier soldier = new ChuSoldier(Dynasty.CHU);

        //when
        boolean result = soldier.canMove(janggiBoard, Dynasty.CHU, new Point(x1, y1), new Point(x2, y2));

        //then
        assertThat(result).isTrue();
    }

    @DisplayName("졸이 규칙 상 갈 수 없는 목적지는 갈 수 없다.")
    @Test
    void isNotMovable_WhenImpossibleEndPoint() {
        // given
        JanggiBoard janggiBoard = new JanggiBoard(Map.of());
        Piece soldier = new ChuSoldier(Dynasty.CHU);

        // then
        assertThatThrownBy(() -> soldier.canMove(janggiBoard, Dynasty.CHU, new Point(1, 1), new Point(2, 2)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치로 이동할 수 없습니다.");
    }

    @DisplayName("목적지에 상대편의 기물이 있는 경우에는 갈 수 있다.")
    @Test
    void isNotMovable_WhenOtherPieceInEndPoint() {
        // given
        JanggiBoard janggiBoard = new JanggiBoard(Map.of(
                new Point(1, 2), new ChuSoldier(Dynasty.CHU)
        ));
        Piece soldier = new ChuSoldier(Dynasty.CHU);

        // when
        boolean canMove = soldier.canMove(janggiBoard, Dynasty.CHU, new Point(1, 1), new Point(1, 2));

        // then
        assertThat(canMove)
                .isTrue();
    }

    @DisplayName("같은 나라인지 확인한다")
    @ParameterizedTest
    @CsvSource({
            "HAN, true",
            "CHU, false"
    })
    void isSameDynasty(Dynasty compareDynasty, boolean expected) {
        // given
        Soldier dynastySoldier = new HanSoldier(Dynasty.HAN);

        // when
        assertThat(dynastySoldier.isSameDynasty(compareDynasty))
                .isSameAs(expected);
    }

    @DisplayName("같은 피스인지 확인한다")
    @Test
    void isEqualPieceType_True() {
        Piece soldier = new ChuSoldier(Dynasty.CHU);

        assertThat(soldier.isEqualPieceType(PieceType.CHU_SOLIDER))
                .isTrue();
    }

    @DisplayName("다른 피스인지 확인한다")
    @Test
    void isEqualPieceType_False() {
        Piece soldier = new ChuSoldier(Dynasty.CHU);

        assertThat(soldier.isEqualPieceType(PieceType.CANNON))
                .isFalse();
    }
}
