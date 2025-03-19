package janggi.domain.piece;

import static org.assertj.core.api.Assertions.*;

import janggi.domain.Dynasty;
import janggi.domain.board.JanggiBoard;
import janggi.domain.board.point.ChuPoint;
import janggi.domain.board.point.DefaultPoint;
import janggi.domain.board.point.HanPoint;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CannonTest {

    /*
    1. 출발지에서 목적지까지 포가 아닌 기물이 한개 있어야함
    2. 목적지에 자신의 편이나 상대편의 포가 있으면 안됨
     */

    @DisplayName("출발지에서 목적지까지 포가 아닌 기물이 한개 있어야한다.")
    @Test
    void moveCannonTest() {
        //given
        JanggiBoard janggiBoard = new JanggiBoard(Set.of(
                new BoardPiece(new HanPoint(4, 4), Pawn.newInstance(), Dynasty.HAN)
        ));
        Cannon cannon = Cannon.newInstance();

        //when
        boolean movable = cannon.isMovable(janggiBoard, new HanPoint(4, 1), new DefaultPoint(4, 9));

        //then
        assertThat(movable).isTrue();
    }

    @DisplayName("출발지에서 목적지까지 포가 아닌 기물이 두개 이상이면 움직일 수 없다.")
    @Test
    void moveCannonTest_WhenTwoPieceInPath() {
        //given
        JanggiBoard janggiBoard = new JanggiBoard(Set.of(
                new BoardPiece(new HanPoint(4, 4), Pawn.newInstance(), Dynasty.HAN),
                new BoardPiece(new HanPoint(4, 6), Pawn.newInstance(), Dynasty.HAN)
        ));
        Cannon cannon = Cannon.newInstance();

        //when
        boolean movable = cannon.isMovable(janggiBoard, new HanPoint(4, 1), new DefaultPoint(4, 9));

        //then
        assertThat(movable).isFalse();
    }

    @DisplayName("포는 포를 뛰어넘을 수 없다.")
    @Test
    void notJumpCannon() {
        //given
        JanggiBoard janggiBoard = new JanggiBoard(Set.of(
                new BoardPiece(new HanPoint(4, 4), Cannon.newInstance(), Dynasty.HAN)
        ));
        Cannon cannon = Cannon.newInstance();

        //when
        boolean movable = cannon.isMovable(janggiBoard, new HanPoint(4, 1), new DefaultPoint(4, 9));

        //then
        assertThat(movable).isFalse();
    }

    @DisplayName("목적지에 포가 있다면 움직일 수 없다.")
    @Test
    void notJumpCannon_WhenEndPositionExistCannon() {
        //given
        JanggiBoard janggiBoard = new JanggiBoard(Set.of(
                new BoardPiece(new ChuPoint(4, 9), Cannon.newInstance(), Dynasty.CHU)
        ));
        Cannon cannon = Cannon.newInstance();

        //when
        boolean movable = cannon.isMovable(janggiBoard, new HanPoint(4, 1), new DefaultPoint(4, 9));

        //then
        assertThat(movable).isFalse();
    }

    @DisplayName("포가 규칙 상 갈 수 없는 목적지는 갈 수 없다.")
    @Test
    void notMove_WhenImpossibleDirection() {
        // given
        JanggiBoard janggiBoard = new JanggiBoard(Set.of());
        Cannon cannon = Cannon.newInstance();

        // when
        boolean isMovable = cannon.isMovable(janggiBoard, new HanPoint(1, 1), new DefaultPoint(2, 2));

        // then
        assertThat(isMovable)
                .isFalse();
    }
}