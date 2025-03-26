package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Dynasty;
import janggi.domain.board.JanggiBoard;
import janggi.domain.board.Point;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("포 테스트")
class CannonTest {

    @DisplayName("출발지에서 목적지까지 포가 아닌 기물이 한개 있어야한다.")
    @Test
    void moveCannonTest() {
        //given
        JanggiBoard janggiBoard = new JanggiBoard(Map.of(
                new Point(4, 4), new BoardPiece(new HanSoldier(), Dynasty.HAN)
        ));
        Cannon cannon = new Cannon();

        //when
        boolean movable = cannon.isMovable(janggiBoard, new Point(4, 1), new Point(4, 9));

        //then
        assertThat(movable).isTrue();
    }

    @DisplayName("출발지에서 목적지까지 포가 아닌 기물이 두개 이상이면 움직일 수 없다.")
    @Test
    void moveCannonTest_WhenTwoPieceInPath() {
        //given
        JanggiBoard janggiBoard = new JanggiBoard(Map.of(
                new Point(4, 4), new BoardPiece(new HanSoldier(), Dynasty.HAN),
                new Point(4, 6), new BoardPiece(new HanSoldier(), Dynasty.HAN)
        ));
        Cannon cannon = new Cannon();

        //when
        boolean movable = cannon.isMovable(janggiBoard, new Point(4, 1), new Point(4, 9));

        //then
        assertThat(movable).isFalse();
    }

    @DisplayName("포는 포를 뛰어넘을 수 없다.")
    @Test
    void notJumpCannon() {
        //given
        JanggiBoard janggiBoard = new JanggiBoard(Map.of(
                new Point(4, 4), new BoardPiece(new Cannon(), Dynasty.HAN)
        ));
        Cannon cannon = new Cannon();

        //when
        boolean movable = cannon.isMovable(janggiBoard, new Point(4, 1), new Point(4, 9));

        //then
        assertThat(movable).isFalse();
    }

    @DisplayName("목적지에 포가 있다면 움직일 수 없다.")
    @Test
    void notJumpCannon_WhenEndPositionExistCannon() {
        //given
        JanggiBoard janggiBoard = new JanggiBoard(Map.of(
                new Point(4, 9), new BoardPiece(new Cannon(), Dynasty.CHU)
        ));
        Cannon cannon = new Cannon();

        //when
        boolean movable = cannon.isMovable(janggiBoard, new Point(4, 1), new Point(4, 9));

        //then
        assertThat(movable).isFalse();
    }

    @DisplayName("포가 규칙 상 갈 수 없는 목적지는 갈 수 없다.")
    @Test
    void notMove_WhenImpossibleDirection() {
        // given
        JanggiBoard janggiBoard = new JanggiBoard(Map.of());
        Cannon cannon = new Cannon();

        // when
        boolean isMovable = cannon.isMovable(janggiBoard, new Point(1, 1), new Point(2, 2));

        // then
        assertThat(isMovable)
                .isFalse();
    }

    @DisplayName("목적지에 상대편의 기물이 있는 경우에는 갈 수 있다.")
    @Test
    void isNotMovable_WhenOtherPieceInEndPoint() {
        // given
        JanggiBoard janggiBoard = new JanggiBoard(Map.of(
                new Point(1, 8), new BoardPiece(new Horse(), Dynasty.CHU),
                new Point(1, 4), new BoardPiece(new Horse(), Dynasty.CHU)
        ));
        Cannon cannon = new Cannon();

        // when
        boolean isMovable = cannon.isMovable(janggiBoard, new Point(1, 1), new Point(1, 8));

        // then
        assertThat(isMovable)
                .isTrue();
    }
}