package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Dynasty;
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
        Soldier soldier = new HanSoldier();

        //when
        boolean result = soldier.isMovable(janggiBoard, new Point(x1, y1), new Point(x2, y2));

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
        Soldier soldier = new ChuSoldier();

        //when
        boolean result = soldier.isMovable(janggiBoard, new Point(x1, y1), new Point(x2, y2));

        //then
        assertThat(result).isTrue();
    }

    @DisplayName("졸이 규칙 상 갈 수 없는 목적지는 갈 수 없다.")
    @Test
    void isNotMovable_WhenImpossibleEndPoint() {
        // given
        JanggiBoard janggiBoard = new JanggiBoard(Map.of());
        Piece soldier = new ChuSoldier();

        // when
        boolean isMovable = soldier.isMovable(janggiBoard, new Point(1, 1), new Point(2, 2));

        // then
        assertThat(isMovable)
                .isFalse();
    }

    @DisplayName("목적지에 상대편의 기물이 있는 경우에는 갈 수 있다.")
    @Test
    void isNotMovable_WhenOtherPieceInEndPoint() {
        // given
        JanggiBoard janggiBoard = new JanggiBoard(Map.of(
                new Point(1, 2), new BoardPiece(new ChuSoldier(), Dynasty.CHU)
        ));
        Piece soldier = new ChuSoldier();

        // when
        boolean isMovable = soldier.isMovable(janggiBoard, new Point(1, 1), new Point(1, 2));

        // then
        assertThat(isMovable)
                .isTrue();
    }
}
