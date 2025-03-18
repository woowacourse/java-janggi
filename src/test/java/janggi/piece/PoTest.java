package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.Movable;
import janggi.TeamColor;
import janggi.point.Point;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class PoTest {

    @Nested
    @DisplayName("이동 가능 확인 테스트")
    class CheckMovableTest {

        @Test
        @DisplayName("좌로 이동할 수 있다면 true를 반환한다.")
        void checkLeftMovable() {
            Po po = new Po(TeamColor.BLUE, new Point(6, 6));
            List<Movable> horizontalPieces = List.of(
                    new Byeong(TeamColor.BLUE, new Point(6, 4)),
                    new Byeong(TeamColor.BLUE, new Point(6, 2))
            );
            List<Movable> verticalPieces = new ArrayList<>();

            Point targetPoint = new Point(6, 3);

            assertThat(po.isMovable(targetPoint, horizontalPieces, verticalPieces)).isTrue();
        }

        @Test
        @DisplayName("우로 이동할 수 있다면 true를 반환한다.")
        void checkRightMovable() {
            Po po = new Po(TeamColor.BLUE, new Point(6, 2));
            List<Movable> horizontalPieces = List.of(
                    new Byeong(TeamColor.BLUE, new Point(6, 4)),
                    new Byeong(TeamColor.BLUE, new Point(6, 6))
            );
            List<Movable> verticalPieces = new ArrayList<>();

            Point targetPoint = new Point(6, 5);

            assertThat(po.isMovable(targetPoint, horizontalPieces, verticalPieces)).isTrue();
        }

        @Test
        @DisplayName("상으로 이동할 수 있다면 true를 반환한다.")
        void checkUpMovable() {
            Po po = new Po(TeamColor.BLUE, new Point(6, 4));
            List<Movable> horizontalPieces = new ArrayList<>();
            List<Movable> verticalPieces = List.of(
                    new Byeong(TeamColor.RED, new Point(3, 4))
            );

            Point targetPoint = new Point(2, 4);

            assertThat(po.isMovable(targetPoint, horizontalPieces, verticalPieces)).isTrue();
        }

        @Test
        @DisplayName("하으로 이동할 수 있다면 true를 반환한다.")
        void checkDownMovable() {
            Po po = new Po(TeamColor.BLUE, new Point(0, 7));
            List<Movable> horizontalPieces = new ArrayList<>();
            List<Movable> verticalPieces = List.of(
                    new Byeong(TeamColor.RED, new Point(2, 7)),
                    new Byeong(TeamColor.RED, new Point(7, 7))
            );

            Point targetPoint = new Point(5, 7);

            assertThat(po.isMovable(targetPoint, horizontalPieces, verticalPieces)).isTrue();
        }
    }
}
