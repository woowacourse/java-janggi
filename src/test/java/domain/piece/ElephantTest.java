package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.position.Point;
import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.PieceFactory;

public class ElephantTest {

    @DisplayName("상: (3,3) → (6,5) 이동 가능 – 수직 아래 1칸 후, 대각선 오른쪽 2칸 이동 (중간: (4,3), (5,4))")
    @Test
    void elephantMovesDownThenDiagonalRight() {
        final Elephant elephant = PieceFactory.createRedTeam(Elephant::new);
        final Point start = Point.newInstance(3, 3);
        final Point end = Point.newInstance(6, 5);
        assertThat(elephant.isMovable(start, end)).isTrue();
    }

    @DisplayName("상: (3,3) → (0,5) 이동 가능 – 수직 위 1칸 후, 대각선 오른쪽 2칸 이동 (중간: (2,3), (1,4))")
    @Test
    void elephantMovesUpThenDiagonalRight() {
        final Elephant elephant = PieceFactory.createRedTeam(Elephant::new);
        final Point start = Point.newInstance(3, 3);
        final Point end = Point.newInstance(0, 5);
        assertThat(elephant.isMovable(start, end)).isTrue();
    }

    @DisplayName("상: (3,3) → (6,1) 이동 가능 – 수직 아래 1칸 후, 대각선 왼쪽 2칸 이동 (중간: (4,3), (5,2))")
    @Test
    void elephantMovesDownThenDiagonalLeft() {
        final Elephant elephant = PieceFactory.createRedTeam(Elephant::new);
        final Point start = Point.newInstance(3, 3);
        final Point end = Point.newInstance(6, 1);
        assertThat(elephant.isMovable(start, end)).isTrue();
    }

    @DisplayName("상: (3,3) → (0,1) 이동 가능 – 수직 위 1칸 후, 대각선 왼쪽 2칸 이동 (중간: (2,3), (1,2))")
    @Test
    void elephantMovesUpThenDiagonalLeft() {
        final Elephant elephant = PieceFactory.createRedTeam(Elephant::new);
        final Point start = Point.newInstance(3, 3);
        final Point end = Point.newInstance(0, 1);
        assertThat(elephant.isMovable(start, end)).isTrue();
    }

    @DisplayName("상: (3,3) → (5,6) 이동 가능 – 수평 오른쪽 1칸 후, 대각선 아래 2칸 이동 (중간: (3,4), (4,5))")
    @Test
    void elephantMovesRightThenDiagonalDown() {
        final Elephant elephant = PieceFactory.createRedTeam(Elephant::new);
        final Point start = Point.newInstance(3, 3);
        final Point end = Point.newInstance(5, 6);
        assertThat(elephant.isMovable(start, end)).isTrue();
    }

    @DisplayName("상: (3,3) → (1,6) 이동 가능 – 수평 오른쪽 1칸 후, 대각선 위 2칸 이동 (중간: (3,4), (2,5))")
    @Test
    void elephantMovesRightThenDiagonalUp() {
        final Elephant elephant = PieceFactory.createRedTeam(Elephant::new);
        final Point start = Point.newInstance(3, 3);
        final Point end = Point.newInstance(1, 6);
        assertThat(elephant.isMovable(start, end)).isTrue();
    }

    @DisplayName("상: (3,3) → (5,0) 이동 가능 – 수평 왼쪽 1칸 후, 대각선 아래 2칸 이동 (중간: (3,2), (4,1))")
    @Test
    void elephantMovesLeftThenDiagonalDown() {
        final Elephant elephant = PieceFactory.createRedTeam(Elephant::new);
        final Point start = Point.newInstance(3, 3);
        final Point end = Point.newInstance(5, 0);
        assertThat(elephant.isMovable(start, end)).isTrue();
    }

    @DisplayName("상: (3,3) → (1,0) 이동 가능 – 수평 왼쪽 1칸 후, 대각선 위 2칸 이동 (중간: (3,2), (2,1))")
    @Test
    void elephantMovesLeftThenDiagonalUp() {
        final Elephant elephant = PieceFactory.createRedTeam(Elephant::new);
        final Point start = Point.newInstance(3, 3);
        final Point end = Point.newInstance(1, 0);
        assertThat(elephant.isMovable(start, end)).isTrue();
    }

    @DisplayName("상: (1,1) → (1,0) 이동 불가 – 이동 거리가 상의 규칙(1칸 후 대각 2칸)에 맞지 않음")
    @Test
    void elephantInvalidMoveDistanceMismatch() {
        final Elephant elephant = PieceFactory.createRedTeam(Elephant::new);
        final Point start = Point.newInstance(1, 1);
        final Point end = Point.newInstance(1, 0);
        assertThat(elephant.isMovable(start, end)).isFalse();
    }

    @DisplayName("상: (1,1) → (1,1) 이동 불가 – 시작점과 도착점이 동일함")
    @Test
    void elephantInvalidMoveSamePosition() {
        final Elephant elephant = PieceFactory.createRedTeam(Elephant::new);
        final Point start = Point.newInstance(1, 1);
        final Point end = Point.newInstance(1, 1);
        assertThat(elephant.isMovable(start, end)).isFalse();
    }

    @DisplayName("상: (1,1) → (8,8) 이동 불가 – 이동 패턴이 상의 규칙에 맞지 않음")
    @Test
    void elephantInvalidMovePatternMismatch() {
        final Elephant elephant = PieceFactory.createRedTeam(Elephant::new);
        final Point start = Point.newInstance(1, 1);
        final Point end = Point.newInstance(8, 8);
        assertThat(elephant.isMovable(start, end)).isFalse();
    }

    @DisplayName("상: (1,0)에서 (3,3)으로 이동 시, 중간 경로 (1,1)과 (2,2) 반환")
    @Test
    void elephantCalculatePossiblePath() {
        final Elephant elephant = PieceFactory.createGreenTeam(Elephant::new);
        final List<Point> possiblePoints = elephant.calculatePossiblePoint(
                Point.newInstance(1, 0),
                Point.newInstance(3, 3)
        );

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(possiblePoints).hasSize(2);
            softly.assertThat(possiblePoints.get(0)).isEqualTo(Point.newInstance(1, 1));
            softly.assertThat(possiblePoints.get(1)).isEqualTo(Point.newInstance(2, 2));
        });
    }
}
