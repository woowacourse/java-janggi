package janggi.strategy;


import static janggi.domain.rule.route.Direction.BACK;
import static janggi.domain.rule.route.Direction.BACK_LEFT;
import static janggi.domain.rule.route.Direction.BACK_RIGHT;
import static janggi.domain.rule.route.Direction.FRONT;
import static janggi.domain.rule.route.Direction.FRONT_LEFT;
import static janggi.domain.rule.route.Direction.FRONT_RIGHT;
import static janggi.domain.rule.route.Direction.LEFT;
import static janggi.domain.rule.route.Direction.RIGHT;

import janggi.domain.board.Intersection;
import janggi.domain.board.Vector;
import janggi.domain.strategy.intersection.IntersectionInitializer;
import janggi.domain.strategy.intersection.PalaceIntersectionInitializer;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PalaceIntersectionInitializerTest {

    @Test
    @DisplayName("궁성 초기화 시 지정된 좌표가 궁성으로 설정된다.")
    void ShouldSetIsPalaceTrue_ForPalaceCoordinates() {
        // given
        Intersection[][] intersections = new Intersection[10][10];
        IntersectionInitializer intersectionInitializer = new PalaceIntersectionInitializer();

        // when
        intersectionInitializer.initialize(intersections);
        List<Intersection> palaceIntersections = List.of(intersections[0][3], intersections[1][4], intersections[1][5]);

        // then
        Assertions.assertThat(palaceIntersections)
                .isNotEmpty()
                .allMatch(Intersection::isPalace);
    }

    @Test
    @DisplayName("궁성 중앙 위치는 대각선 방향을 포함한 8개의 이동 벡터를 가진다.")
    void ShouldHaveEightVectors_AtPalaceCenter() {
        // given
        Intersection[][] intersections = new Intersection[10][10];
        IntersectionInitializer intersectionInitializer = new PalaceIntersectionInitializer();
        List<Vector> expectedVectors = List.of(
                new Vector(FRONT, intersections.length),
                new Vector(BACK, intersections.length),
                new Vector(LEFT, intersections[0].length),
                new Vector(RIGHT, intersections[0].length),
                new Vector(BACK_RIGHT, 1),
                new Vector(BACK_LEFT, 1),
                new Vector(FRONT_LEFT, 1),
                new Vector(FRONT_RIGHT, 1)
        );

        // when
        intersectionInitializer.initialize(intersections);
        Intersection center = intersections[1][4];

        // then
        Assertions.assertThat(center.getVectors()).isEqualTo(expectedVectors);
    }

    @Test
    @DisplayName("궁성 좌측 상단 위치는 기본 4방향과 대각선 벡터를 가진다.")
    void ShouldHaveFiveVectors_AtPalaceTopLeft() {
        // given
        Intersection[][] intersections = new Intersection[10][10];
        IntersectionInitializer intersectionInitializer = new PalaceIntersectionInitializer();
        List<Vector> expectedVectors = List.of(
                new Vector(FRONT, intersections.length),
                new Vector(BACK, intersections.length),
                new Vector(LEFT, intersections[0].length),
                new Vector(RIGHT, intersections[0].length),
                new Vector(BACK_RIGHT, 2)
        );

        // when
        intersectionInitializer.initialize(intersections);
        Intersection center = intersections[7][3];

        // then
        Assertions.assertThat(center.getVectors()).isEqualTo(expectedVectors);
    }

    @Test
    @DisplayName("궁성 우측 하단 위치는 기본 4방향과 대각선 벡터를 가진다.")
    void ShouldHaveFiveVectors_AtPalaceBottomRight() {
        // given
        Intersection[][] intersections = new Intersection[10][10];
        IntersectionInitializer intersectionInitializer = new PalaceIntersectionInitializer();
        List<Vector> expectedVectors = List.of(
                new Vector(FRONT, intersections.length),
                new Vector(BACK, intersections.length),
                new Vector(LEFT, intersections[0].length),
                new Vector(RIGHT, intersections[0].length),
                new Vector(FRONT_LEFT, 2)
        );

        // when
        intersectionInitializer.initialize(intersections);
        Intersection center = intersections[2][5];

        // then
        Assertions.assertThat(center.getVectors()).isEqualTo(expectedVectors);
    }

    @Test
    @DisplayName("궁성 사이드 위치는 대각선을 제외한 기본 4방향 벡터를 가진다.")
    void ShouldHaveFourVectors_AtPalaceSide() {
        // given
        Intersection[][] intersections = new Intersection[10][10];
        IntersectionInitializer intersectionInitializer = new PalaceIntersectionInitializer();
        List<Vector> expectedVectors = List.of(
                new Vector(FRONT, intersections.length),
                new Vector(BACK, intersections.length),
                new Vector(LEFT, intersections[0].length),
                new Vector(RIGHT, intersections[0].length)
        );

        // when
        intersectionInitializer.initialize(intersections);
        Intersection center = intersections[1][3];

        // then
        Assertions.assertThat(center.getVectors()).isEqualTo(expectedVectors);
    }
}
