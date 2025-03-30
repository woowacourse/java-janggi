package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.position.Point;
import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.PieceFactory;

public class ChariotTest {

    @DisplayName("차: (1,1) → (1,2) 이동 가능 – 수평 오른쪽 이동")
    @Test
    void chariotMoveRight() {
        final Chariot chariot = PieceFactory.createRedTeam(Chariot::new);
        final Point start = Point.newInstance(1, 1);
        final Point end = Point.newInstance(1, 2);

        assertThat(chariot.isMovable(start, end)).isTrue();
    }

    @DisplayName("차: (1,1) → (2,1) 이동 가능 – 수직 아래쪽 이동")
    @Test
    void chariotMoveDown() {
        final Chariot chariot = PieceFactory.createRedTeam(Chariot::new);
        final Point start = Point.newInstance(1, 1);
        final Point end = Point.newInstance(2, 1);

        assertThat(chariot.isMovable(start, end)).isTrue();
    }

    @DisplayName("차: (1,1) → (0,1) 이동 가능 – 수직 위쪽 이동")
    @Test
    void chariotMoveUp() {
        final Chariot chariot = PieceFactory.createRedTeam(Chariot::new);
        final Point start = Point.newInstance(1, 1);
        final Point end = Point.newInstance(0, 1);

        assertThat(chariot.isMovable(start, end)).isTrue();
    }

    @DisplayName("차: (1,1) → (1,0) 이동 가능 – 수평 왼쪽 이동")
    @Test
    void chariotMoveLeft() {
        final Chariot chariot = PieceFactory.createRedTeam(Chariot::new);
        final Point start = Point.newInstance(1, 1);
        final Point end = Point.newInstance(1, 0);

        assertThat(chariot.isMovable(start, end)).isTrue();
    }

    @DisplayName("차: (1,1) → (1,1) 이동 불가 – 시작점과 도착점이 동일함")
    @Test
    void chariotCannotMoveToSamePosition() {
        final Chariot chariot = PieceFactory.createRedTeam(Chariot::new);
        final Point start = Point.newInstance(1, 1);
        final Point end = Point.newInstance(1, 1);

        assertThat(chariot.isMovable(start, end)).isFalse();
    }

    @DisplayName("차: (1,1) → (8,8) 이동 불가 – 대각선 이동은 기본 규칙에 어긋남")
    @Test
    void chariotCannotMoveDiagonalOutsidePalace() {
        final Chariot chariot = PieceFactory.createRedTeam(Chariot::new);
        final Point start = Point.newInstance(1, 1);
        final Point end = Point.newInstance(8, 8);

        assertThat(chariot.isMovable(start, end)).isFalse();
    }

    @DisplayName("차: (0,0)에서 (0,9)로 이동 시, 이동 경로에 (0,1), (0,5), (0,8) 등이 포함되어야 함")
    @Test
    void chariotCalculatePossiblePath() {
        final Chariot chariot = PieceFactory.createGreenTeam(Chariot::new);
        final List<Point> possiblePoints = chariot.calculatePossiblePoint(
                Point.newInstance(0, 0),
                Point.newInstance(0, 9)
        );

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(possiblePoints.size()).isEqualTo(8);
            softly.assertThat(possiblePoints)
                    .contains(Point.newInstance(0, 1),
                            Point.newInstance(0, 5),
                            Point.newInstance(0, 8));
        });
    }

    @DisplayName("차: 궁성 내 (3,0) → (4,1) 이동 가능 – 인접 대각선 이동 허용")
    @Test
    void chariotPalaceMoveAdjacent() {
        final Chariot chariot = PieceFactory.createGreenTeam(Chariot::new);
        final Point start = Point.newInstance(3, 0);
        final Point end = Point.newInstance(4, 1);

        assertThat(chariot.isMovable(start, end)).isTrue();
    }

    @DisplayName("차: 궁성 내 (3,0) → (5,2) 이동 가능 – 궁성 내 대각선 경로 이동")
    @Test
    void chariotPalaceMoveLonger() {
        final Chariot chariot = PieceFactory.createGreenTeam(Chariot::new);
        final Point start = Point.newInstance(3, 0);
        final Point end = Point.newInstance(5, 2);

        assertThat(chariot.isMovable(start, end)).isTrue();
    }

    @DisplayName("차: 궁성 내 (4,1) → (3,0) 이동 가능 – 역방향 대각선 이동 허용")
    @Test
    void chariotPalaceMoveReverse1() {
        final Chariot chariot = PieceFactory.createGreenTeam(Chariot::new);
        final Point start = Point.newInstance(4, 1);
        final Point end = Point.newInstance(3, 0);

        assertThat(chariot.isMovable(start, end)).isTrue();
    }

    @DisplayName("차: 궁성 내 (4,1) → (5,2) 이동 가능 – 인접 대각선 이동 허용")
    @Test
    void chariotPalaceMoveReverse2() {
        final Chariot chariot = PieceFactory.createGreenTeam(Chariot::new);
        final Point start = Point.newInstance(4, 1);
        final Point end = Point.newInstance(5, 2);

        assertThat(chariot.isMovable(start, end)).isTrue();
    }

    @DisplayName("차: 궁성 내 (3,0) → (6,3) 이동 불가 – 이동 경로가 궁성 규칙에 부합하지 않음")
    @Test
    void chariotPalaceInvalidMove1() {
        final Chariot chariot = PieceFactory.createGreenTeam(Chariot::new);
        final Point start = Point.newInstance(3, 0);
        final Point end = Point.newInstance(6, 3);

        assertThat(chariot.isMovable(start, end)).isFalse();
    }

    @DisplayName("차: 궁성 내 (4,1) → (6,3) 이동 불가 – 경로상의 위치가 유효하지 않음")
    @Test
    void chariotPalaceInvalidMove2() {
        final Chariot chariot = PieceFactory.createGreenTeam(Chariot::new);
        final Point start = Point.newInstance(4, 1);
        final Point end = Point.newInstance(6, 3);

        assertThat(chariot.isMovable(start, end)).isFalse();
    }

    @DisplayName("차: 궁성 내 (3,2) → (1,4) 이동 불가 – 궁성 내 대각선 이동 규칙 위반")
    @Test
    void chariotPalaceInvalidMove3() {
        final Chariot chariot = PieceFactory.createGreenTeam(Chariot::new);
        final Point start = Point.newInstance(3, 2);
        final Point end = Point.newInstance(1, 4);

        assertThat(chariot.isMovable(start, end)).isFalse();
    }

    @DisplayName("차: 궁성 내 (4,1) → (2,3) 이동 불가 – 이동 경로상의 장애물 또는 규칙 위반")
    @Test
    void chariotPalaceInvalidMove4() {
        final Chariot chariot = PieceFactory.createGreenTeam(Chariot::new);
        final Point start = Point.newInstance(4, 1);
        final Point end = Point.newInstance(2, 3);

        assertThat(chariot.isMovable(start, end)).isFalse();
    }

    @DisplayName("차: 궁성 내 (4,0) → (3,1) 이동 불가 – 인접 이동으로는 궁성 규칙 미충족")
    @Test
    void chariotPalaceInvalidMove5() {
        final Chariot chariot = PieceFactory.createGreenTeam(Chariot::new);
        final Point start = Point.newInstance(4, 0);
        final Point end = Point.newInstance(3, 1);

        assertThat(chariot.isMovable(start, end)).isFalse();
    }

    @DisplayName("차: 궁성 내 (3,1) → (4,2) 이동 불가 – 규칙에 맞지 않는 이동")
    @Test
    void chariotPalaceInvalidMove6() {
        final Chariot chariot = PieceFactory.createGreenTeam(Chariot::new);
        final Point start = Point.newInstance(3, 1);
        final Point end = Point.newInstance(4, 2);

        assertThat(chariot.isMovable(start, end)).isFalse();
    }

    @DisplayName("차: 궁성 내 (4,2) → (5,1) 이동 불가 – 잘못된 이동 경로")
    @Test
    void chariotPalaceInvalidMove7() {
        final Chariot chariot = PieceFactory.createGreenTeam(Chariot::new);
        final Point start = Point.newInstance(4, 2);
        final Point end = Point.newInstance(5, 1);

        assertThat(chariot.isMovable(start, end)).isFalse();
    }

    @DisplayName("차: 궁성 내 (5,1) → (4,0) 이동 불가 – 이동 규칙 위반")
    @Test
    void chariotPalaceInvalidMove8() {
        final Chariot chariot = PieceFactory.createGreenTeam(Chariot::new);
        final Point start = Point.newInstance(5, 1);
        final Point end = Point.newInstance(4, 0);

        assertThat(chariot.isMovable(start, end)).isFalse();
    }

    @DisplayName("차: 궁성 내 (3,8) → (4,9) 이동 불가 – 경로상의 위치가 올바르지 않음")
    @Test
    void chariotPalaceInvalidMove9() {
        final Chariot chariot = PieceFactory.createGreenTeam(Chariot::new);
        final Point start = Point.newInstance(3, 8);
        final Point end = Point.newInstance(4, 9);

        assertThat(chariot.isMovable(start, end)).isFalse();
    }

    // --- 궁성 내 이동 시, 올바른 중간 경로 반환 테스트 ---
    @DisplayName("차: 궁성 내 (3,0) → (5,2) 이동 시, 중간 경로로 (4,1) 반환")
    @Test
    void chariotPalacePossiblePathTest1() {
        final Chariot chariot = PieceFactory.createGreenTeam(Chariot::new);
        final Point start = Point.newInstance(3, 0);
        final Point end = Point.newInstance(5, 2);
        final List<Point> points = chariot.calculatePossiblePoint(start, end);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(points).hasSize(1);
            softly.assertThat(points.get(0)).isEqualTo(Point.newInstance(4, 1));
        });
    }

    @DisplayName("차: 궁성 내 (3,2) → (5,0) 이동 시, 중간 경로로 (4,1) 반환")
    @Test
    void chariotPalacePossiblePathTest2() {
        final Chariot chariot = PieceFactory.createGreenTeam(Chariot::new);
        final Point start = Point.newInstance(3, 2);
        final Point end = Point.newInstance(5, 0);
        final List<Point> points = chariot.calculatePossiblePoint(start, end);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(points).hasSize(1);
            softly.assertThat(points.get(0)).isEqualTo(Point.newInstance(4, 1));
        });
    }

    @DisplayName("차: 궁성 내 (5,9) → (3,7) 이동 시, 중간 경로로 (4,8) 반환")
    @Test
    void chariotPalacePossiblePathTest3() {
        final Chariot chariot = PieceFactory.createGreenTeam(Chariot::new);
        final Point start = Point.newInstance(5, 9);
        final Point end = Point.newInstance(3, 7);
        final List<Point> points = chariot.calculatePossiblePoint(start, end);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(points).hasSize(1);
            softly.assertThat(points.get(0)).isEqualTo(Point.newInstance(4, 8));
        });
    }

    @DisplayName("차: 궁성 내 (5,7) → (3,9) 이동 시, 중간 경로로 (4,8) 반환")
    @Test
    void chariotPalacePossiblePathTest4() {
        final Chariot chariot = PieceFactory.createGreenTeam(Chariot::new);
        final Point start = Point.newInstance(5, 7);
        final Point end = Point.newInstance(3, 9);
        final List<Point> points = chariot.calculatePossiblePoint(start, end);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(points).hasSize(1);
            softly.assertThat(points.get(0)).isEqualTo(Point.newInstance(4, 8));
        });
    }

    // --- 궁성 내 인접 대각선 이동 시, 중간 경로가 없음 ---
    @DisplayName("차: 궁성 내 인접 대각선 이동 시, 중간 경로 없음")
    @Test
    void chariotPalaceAdjacentMoveNoIntermediatePath() {
        final Chariot chariot = PieceFactory.createGreenTeam(Chariot::new);

        Point start, end;

        // case 1: (3,0) → (4,1)
        start = Point.newInstance(3, 0);
        end = Point.newInstance(4, 1);
        assertThat(chariot.calculatePossiblePoint(start, end)).isEmpty();

        // case 2: (4,1) → (5,0)
        start = Point.newInstance(4, 1);
        end = Point.newInstance(5, 0);
        assertThat(chariot.calculatePossiblePoint(start, end)).isEmpty();

        // case 3: (3,9) → (4,8)
        start = Point.newInstance(3, 9);
        end = Point.newInstance(4, 8);
        assertThat(chariot.calculatePossiblePoint(start, end)).isEmpty();

        // case 4: (4,8) → (5,9)
        start = Point.newInstance(4, 8);
        end = Point.newInstance(5, 9);
        assertThat(chariot.calculatePossiblePoint(start, end)).isEmpty();
    }
}
