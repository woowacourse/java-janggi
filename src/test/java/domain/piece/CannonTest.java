package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.position.Point;
import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.PieceFactory;

class CannonTest {

    // 기본 이동
    @DisplayName("포: (2,2) → (4,2) 이동 가능 – 수평 오른쪽 이동 시, 정확히 1개의 기물을 뛰어넘어야 함")
    @Test
    void cannonMovesOverExactlyOnePieceHorizontalRight() {
        // given
        final Cannon cannon = PieceFactory.createRedTeam(Cannon::new);
        final Point start = Point.newInstance(2, 2);
        final Point end = Point.newInstance(4, 2);

        // then
        assertThat(cannon.isMovable(start, end)).isTrue();
    }

    @DisplayName("포: (2,2) → (0,2) 이동 가능 – 수평 왼쪽 이동 시, 정확히 1개의 기물을 뛰어넘어야 함")
    @Test
    void cannonMovesOverExactlyOnePieceHorizontalLeft() {
        final Cannon cannon = PieceFactory.createRedTeam(Cannon::new);
        final Point start = Point.newInstance(2, 2);
        final Point end = Point.newInstance(0, 2);

        assertThat(cannon.isMovable(start, end)).isTrue();
    }

    @DisplayName("포: (2,2) → (2,0) 이동 가능 – 수직 위쪽 이동 시, 정확히 1개의 기물을 뛰어넘어야 함")
    @Test
    void cannonMovesOverExactlyOnePieceVerticalUp() {
        final Cannon cannon = PieceFactory.createRedTeam(Cannon::new);
        final Point start = Point.newInstance(2, 2);
        final Point end = Point.newInstance(2, 0);

        assertThat(cannon.isMovable(start, end)).isTrue();
    }

    @DisplayName("포: (2,2) → (2,4) 이동 가능 – 수직 아래쪽 이동 시, 정확히 1개의 기물을 뛰어넘어야 함")
    @Test
    void cannonMovesOverExactlyOnePieceVerticalDown() {
        final Cannon cannon = PieceFactory.createRedTeam(Cannon::new);
        final Point start = Point.newInstance(2, 2);
        final Point end = Point.newInstance(2, 4);

        assertThat(cannon.isMovable(start, end)).isTrue();
    }


    // --- 잘못된 이동
    @DisplayName("포: (1,1) → (2,1) 이동 불가 – 단순 두 칸 이동만으로는 이동할 수 없으며, 반드시 기물을 뛰어넘어야 함")
    @Test
    void cannonCannotMoveWithoutJumpingPiece_horizontalRight() {
        final Cannon cannon = PieceFactory.createRedTeam(Cannon::new);
        final Point start = Point.newInstance(1, 1);
        final Point end = Point.newInstance(2, 1);

        assertThat(cannon.isMovable(start, end)).isFalse();
    }

    @DisplayName("포: (1,1) → (0,1) 이동 불가 – 단순 두 칸 이동만으로는 이동할 수 없으며, 반드시 기물을 뛰어넘어야 함")
    @Test
    void cannonCannotMoveWithoutJumpingPiece_horizontalLeft() {
        final Cannon cannon = PieceFactory.createRedTeam(Cannon::new);
        final Point start = Point.newInstance(1, 1);
        final Point end = Point.newInstance(0, 1);

        assertThat(cannon.isMovable(start, end)).isFalse();
    }

    @DisplayName("포: (1,1) → (1,0) 이동 불가 – 수직 위쪽 단순 이동만으로는 이동할 수 없으며, 반드시 기물을 뛰어넘어야 함")
    @Test
    void cannonCannotMoveWithoutJumpingPiece_verticalUp() {
        final Cannon cannon = PieceFactory.createRedTeam(Cannon::new);
        final Point start = Point.newInstance(1, 1);
        final Point end = Point.newInstance(1, 0);

        assertThat(cannon.isMovable(start, end)).isFalse();
    }

    @DisplayName("포: (1,1) → (1,2) 이동 불가 – 수직 아래쪽 단순 이동만으로는 이동할 수 없으며, 반드시 기물을 뛰어넘어야 함")
    @Test
    void cannonCannotMoveWithoutJumpingPiece_verticalDown() {
        final Cannon cannon = PieceFactory.createRedTeam(Cannon::new);
        final Point start = Point.newInstance(1, 1);
        final Point end = Point.newInstance(1, 2);

        assertThat(cannon.isMovable(start, end)).isFalse();
    }

    @DisplayName("포: (1,1) → (2,0) 이동 불가 – 올바른 중간 기물이 없으므로 점프 규칙에 위배됨")
    @Test
    void cannonCannotMoveWithoutProperJumpPiece_diagonalLike() {
        final Cannon cannon = PieceFactory.createRedTeam(Cannon::new);
        final Point start = Point.newInstance(1, 1);
        final Point end = Point.newInstance(2, 0);

        assertThat(cannon.isMovable(start, end)).isFalse();
    }

    @DisplayName("포: (1,1) → (1,1) 이동 불가 – 시작점과 도착점이 동일함")
    @Test
    void cannonCannotMoveToSamePosition() {
        final Cannon cannon = PieceFactory.createRedTeam(Cannon::new);
        final Point start = Point.newInstance(1, 1);
        final Point end = Point.newInstance(1, 1);

        assertThat(cannon.isMovable(start, end)).isFalse();
    }

    @DisplayName("포: (1,1) → (8,8) 이동 불가 – 이동 경로가 규칙에 부합하지 않음")
    @Test
    void cannonCannotMoveInvalidPath() {
        final Cannon cannon = PieceFactory.createRedTeam(Cannon::new);
        final Point start = Point.newInstance(1, 1);
        final Point end = Point.newInstance(8, 8);

        assertThat(cannon.isMovable(start, end)).isFalse();
    }


    // --- 이동 가능 경로 반환 테스트 ---
    @DisplayName("포: (1,2)에서 (1,9)로 이동 시, 중간 경로에 (1,3), (1,5), (1,8) 등이 포함되어야 함")
    @Test
    void cannonCalculatePossiblePathInLine() {
        final Cannon cannon = PieceFactory.createGreenTeam(Cannon::new);
        final List<Point> possiblePoints = cannon.calculatePossiblePoint(
                Point.newInstance(1, 2),
                Point.newInstance(1, 9)
        );

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(possiblePoints.size()).isEqualTo(6);
            softly.assertThat(possiblePoints)
                    .contains(Point.newInstance(1, 3),
                            Point.newInstance(1, 5),
                            Point.newInstance(1, 8));
        });
    }


    // --- 궁성 내 이동 (포는 궁성 내에서도 점프 규칙을 따름) ---
    @DisplayName("포: 궁성 내 (3,0) → (5,2) 이동 가능 – 반드시 중간에 (4,1)을 뛰어넘어야 함")
    @Test
    void cannonMovesInPalaceWithJumpOverCorrectPiece1() {
        final Cannon cannon = PieceFactory.createGreenTeam(Cannon::new);
        final Point start = Point.newInstance(3, 0);
        final Point end = Point.newInstance(5, 2);

        assertThat(cannon.isMovable(start, end)).isTrue();
    }

    @DisplayName("포: 궁성 내 (3,2) → (5,0) 이동 가능 – 반드시 중간에 (4,1)을 뛰어넘어야 함")
    @Test
    void cannonMovesInPalaceWithJumpOverCorrectPiece2() {
        final Cannon cannon = PieceFactory.createGreenTeam(Cannon::new);
        final Point start = Point.newInstance(3, 2);
        final Point end = Point.newInstance(5, 0);

        assertThat(cannon.isMovable(start, end)).isTrue();
    }

    @DisplayName("포: 궁성 내 (5,2) → (3,0) 이동 가능 – 반드시 중간에 (4,1)을 뛰어넘어야 함")
    @Test
    void cannonMovesInPalaceWithJumpOverCorrectPiece3() {
        final Cannon cannon = PieceFactory.createGreenTeam(Cannon::new);
        final Point start = Point.newInstance(5, 2);
        final Point end = Point.newInstance(3, 0);

        assertThat(cannon.isMovable(start, end)).isTrue();
    }

    @DisplayName("포: 궁성 내 (5,0) → (3,2) 이동 가능 – 반드시 중간에 (4,1)을 뛰어넘어야 함")
    @Test
    void cannonMovesInPalaceWithJumpOverCorrectPiece4() {
        final Cannon cannon = PieceFactory.createGreenTeam(Cannon::new);
        final Point start = Point.newInstance(5, 0);
        final Point end = Point.newInstance(3, 2);

        assertThat(cannon.isMovable(start, end)).isTrue();
    }


    // --- 궁성 내 잘못된 이동 (점프 규칙 미준수) ---
    @DisplayName("포: 궁성 내 (3,0) → (6,3) 이동 불가 – 중간에 정확히 1개의 기물이 있어야 함")
    @Test
    void cannonCannotMoveInPalaceWithImproperJump1() {
        final Cannon cannon = PieceFactory.createGreenTeam(Cannon::new);
        final Point start = Point.newInstance(3, 0);
        final Point end = Point.newInstance(6, 3);

        assertThat(cannon.isMovable(start, end)).isFalse();
    }

    @DisplayName("포: 궁성 내 (4,1) → (6,3) 이동 불가 – 중간에 올바른 기물이 없으므로 이동 불가")
    @Test
    void cannonCannotMoveInPalaceWithImproperJump2() {
        final Cannon cannon = PieceFactory.createGreenTeam(Cannon::new);
        final Point start = Point.newInstance(4, 1);
        final Point end = Point.newInstance(6, 3);

        assertThat(cannon.isMovable(start, end)).isFalse();
    }

    @DisplayName("포: 궁성 내 (5,2) → (6,3) 이동 불가 – 중간에 올바른 기물이 없으므로 이동 불가")
    @Test
    void cannonCannotMoveInPalaceWithImproperJump3() {
        final Cannon cannon = PieceFactory.createGreenTeam(Cannon::new);
        final Point start = Point.newInstance(5, 2);
        final Point end = Point.newInstance(6, 3);

        assertThat(cannon.isMovable(start, end)).isFalse();
    }

    @DisplayName("포: 궁성 내 (3,2) → (1,4) 이동 불가 – 이동 경로상의 중간 기물이 올바르지 않음")
    @Test
    void cannonCannotMoveInPalaceWithImproperJump4() {
        final Cannon cannon = PieceFactory.createGreenTeam(Cannon::new);
        final Point start = Point.newInstance(3, 2);
        final Point end = Point.newInstance(1, 4);

        assertThat(cannon.isMovable(start, end)).isFalse();
    }

    @DisplayName("포: 궁성 내 (4,1) → (2,3) 이동 불가 – 중간에 정확한 기물이 존재해야 함")
    @Test
    void cannonCannotMoveInPalaceWithImproperJump5() {
        final Cannon cannon = PieceFactory.createGreenTeam(Cannon::new);
        final Point start = Point.newInstance(4, 1);
        final Point end = Point.newInstance(2, 3);

        assertThat(cannon.isMovable(start, end)).isFalse();
    }

    @DisplayName("포: 궁성 내 (3,0) → (4,1) 이동 불가 – 단순 인접 이동은 불가하며 반드시 점프 규칙을 따라야 함")
    @Test
    void cannonCannotMoveSimpleAdjacentInPalace1() {
        final Cannon cannon = PieceFactory.createGreenTeam(Cannon::new);
        final Point start = Point.newInstance(3, 0);
        final Point end = Point.newInstance(4, 1);

        assertThat(cannon.isMovable(start, end)).isFalse();
    }

    @DisplayName("포: 궁성 내 (4,0) → (3,1) 이동 불가 – 단순 인접 이동은 불가함")
    @Test
    void cannonCannotMoveSimpleAdjacentInPalace2() {
        final Cannon cannon = PieceFactory.createGreenTeam(Cannon::new);
        final Point start = Point.newInstance(4, 0);
        final Point end = Point.newInstance(3, 1);

        assertThat(cannon.isMovable(start, end)).isFalse();
    }

    @DisplayName("포: 궁성 내 (3,1) → (4,2) 이동 불가 – 점프 규칙 미준수")
    @Test
    void cannonCannotMoveSimpleAdjacentInPalace3() {
        final Cannon cannon = PieceFactory.createGreenTeam(Cannon::new);
        final Point start = Point.newInstance(3, 1);
        final Point end = Point.newInstance(4, 2);

        assertThat(cannon.isMovable(start, end)).isFalse();
    }

    @DisplayName("포: 궁성 내 (4,2) → (5,1) 이동 불가 – 이동 경로상의 중간 기물이 올바르지 않음")
    @Test
    void cannonCannotMoveSimpleAdjacentInPalace4() {
        final Cannon cannon = PieceFactory.createGreenTeam(Cannon::new);
        final Point start = Point.newInstance(4, 2);
        final Point end = Point.newInstance(5, 1);

        assertThat(cannon.isMovable(start, end)).isFalse();
    }

    @DisplayName("포: 궁성 내 (5,1) → (4,0) 이동 불가 – 단순 인접 이동은 불가함")
    @Test
    void cannonCannotMoveSimpleAdjacentInPalace5() {
        final Cannon cannon = PieceFactory.createGreenTeam(Cannon::new);
        final Point start = Point.newInstance(5, 1);
        final Point end = Point.newInstance(4, 0);

        assertThat(cannon.isMovable(start, end)).isFalse();
    }

    @DisplayName("포: 궁성 내 (3,8) → (4,9) 이동 불가 – 중간에 올바른 기물이 존재하지 않음")
    @Test
    void cannonCannotMoveSimpleAdjacentInPalace6() {
        final Cannon cannon = PieceFactory.createGreenTeam(Cannon::new);
        final Point start = Point.newInstance(3, 8);
        final Point end = Point.newInstance(4, 9);

        assertThat(cannon.isMovable(start, end)).isFalse();
    }


    // --- 궁성 내 이동 시, 올바른 점프 경로 반환 테스트 ---
    @DisplayName("포: 궁성 내 (3,0) → (5,2) 이동 시, 중간 경로로 (4,1)만 반환")
    @Test
    void cannonPalaceJumpPathTest1() {
        final Cannon cannon = PieceFactory.createGreenTeam(Cannon::new);
        final Point start = Point.newInstance(3, 0);
        final Point end = Point.newInstance(5, 2);
        final List<Point> points = cannon.calculatePossiblePoint(start, end);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(points).hasSize(1);
            softly.assertThat(points.get(0)).isEqualTo(Point.newInstance(4, 1));
        });
    }

    @DisplayName("포: 궁성 내 (3,2) → (5,0) 이동 시, 중간 경로로 (4,1)만 반환")
    @Test
    void cannonPalaceJumpPathTest2() {
        final Cannon cannon = PieceFactory.createGreenTeam(Cannon::new);
        final Point start = Point.newInstance(3, 2);
        final Point end = Point.newInstance(5, 0);
        final List<Point> points = cannon.calculatePossiblePoint(start, end);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(points).hasSize(1);
            softly.assertThat(points.get(0)).isEqualTo(Point.newInstance(4, 1));
        });
    }

    @DisplayName("포: 궁성 내 (5,9) → (3,7) 이동 시, 중간 경로로 (4,8)만 반환")
    @Test
    void cannonPalaceJumpPathTest3() {
        final Cannon cannon = PieceFactory.createGreenTeam(Cannon::new);
        final Point start = Point.newInstance(5, 9);
        final Point end = Point.newInstance(3, 7);
        final List<Point> points = cannon.calculatePossiblePoint(start, end);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(points).hasSize(1);
            softly.assertThat(points.get(0)).isEqualTo(Point.newInstance(4, 8));
        });
    }

    @DisplayName("포: 궁성 내 (5,7) → (3,9) 이동 시, 중간 경로로 (4,8)만 반환")
    @Test
    void cannonPalaceJumpPathTest4() {
        final Cannon cannon = PieceFactory.createGreenTeam(Cannon::new);
        final Point start = Point.newInstance(5, 7);
        final Point end = Point.newInstance(3, 9);
        final List<Point> points = cannon.calculatePossiblePoint(start, end);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(points).hasSize(1);
            softly.assertThat(points.get(0)).isEqualTo(Point.newInstance(4, 8));
        });
    }
}
