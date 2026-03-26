package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PieceTypeTest {

    @Test
    @DisplayName("HORSE의 calculatePaths는 상하좌우를 기준으로 파생된 8개의 기하학적 경로를 반환한다.")
    void calculatePaths_Horse() {
        // given
        Position current = new Position(4, 4); // 보드 중앙
        PieceType horse = PieceType.HORSE;

        // when
        Paths paths = horse.calculatePaths(current);

        // then
        int pathCount = 0;
        System.out.println("current = " + current);
        for (Path path : paths) {
            System.out.println(path.toString());
            pathCount++;
        }
        // 마(Horse)는 맵 중앙에서 총 8갈래의 경로를 가짐
        assertThat(pathCount).isEqualTo(8);
    }

    @Test
    @DisplayName("ELEPHANT의 calculatePaths는 상하좌우를 기준으로 파생된 8개의 기하학적 경로를 반환한다.")
    void calculatePaths_Elephant() {
        // given
        Position current = new Position(4, 4); // 보드 중앙
        PieceType elephant = PieceType.ELEPHANT;

        // when
        Paths paths = elephant.calculatePaths(current);

        // then
        int pathCount = 0;
        System.out.println("current = " + current);
        for (Path path : paths) {
            System.out.println(path.toString());
            pathCount++;
        }
        // 마(Horse)는 맵 중앙에서 총 8갈래의 경로를 가짐
        assertThat(pathCount).isEqualTo(8);
    }

    @Test
    @DisplayName("한 칸만 이동하는 기물의 calculatePaths는 규칙에 맞춰 한 칸 이동하는 경로들을 반환한다")
    void calculatePaths_Step() {
        Position current = new Position(4, 4);
        PieceType palace = PieceType.PALACE;

        Paths paths = palace.calculatePaths(current);

        int pathCount = 0;
        System.out.println("current = " + current);
        for (Path path : paths) {
            System.out.println(path.toString());
            pathCount++;
        }

        assertThat(pathCount).isEqualTo(4);
    }

    @Test
    @DisplayName("여러 칸 이동하는 기물의 calculatePaths는 규칙에 맞춰 위치의 경계값까지 이동하는 경로들을 반환한다")
    void calculatePaths_Slide() {
        Position current = new Position(4, 4);
        PieceType chariot = PieceType.CHARIOT;

        Paths paths = chariot.calculatePaths(current);

        System.out.println("current = " + current);
        for (Path path : paths) {
            System.out.println(path.toString());
        }

        assertThat(paths).isNotEmpty();
    }

    @Test
    @DisplayName("determineDestinations는 경로 상의 멱(경유지)에 장애물이 있으면 해당 목적지를 제외한다.")
    void determineDestinations_Horse_WithObstacle() {
        // given
        Position current = new Position(4, 4);
        PieceType horse = PieceType.HORSE;
        Paths paths = horse.calculatePaths(current);

        // 보드 상태 구성: (5, 4) 남쪽 방향 '멱'에 기물 배치
        Map<Position, PieceVO> boardState = new HashMap<>();
        Position obstacleTransit = new Position(5, 4);
        boardState.put(obstacleTransit, new PieceVO(Side.CHO, PieceType.CHO_SOLDIER, "0"));

        PieceVO movingPiece = new PieceVO(Side.HAN, PieceType.HORSE, "0");

        // when
        List<Position> destinations = horse.determineDestinations(paths, boardState, movingPiece);

        // then
        // 멱(5, 4)이 막혔으므로, 남쪽으로 출발하는 2개의 도착지 (6, 3)과 (6, 5)는 도달할 수 없어야 함
        Position blockedDest1 = new Position(6, 3);
        Position blockedDest2 = new Position(6, 5);

        assertThat(destinations).doesNotContain(blockedDest1, blockedDest2);
        // 전체 8개 경로 중 2개가 차단되었으므로, 남은 도착지는 6개여야 함 (도착지 모두 빈 공간이라고 가정)
        assertThat(destinations).hasSize(6);
    }
}
