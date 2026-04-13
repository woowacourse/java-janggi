package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.domain.board.Position;
import janggi.domain.game.Side;
import janggi.domain.route.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PieceTypeTest {
    private static final Side DEFAULT_SIDE = Side.CHO;

    @DisplayName("마(HORSE)는 보드 중앙에서 장애물이 없는 경우, 8개의 이동 경로를 생성한다.")
    @Test
    void 마_중앙_경로_생성_테스트() {
        // given
        Position center = new Position(4, 4);

        // when
        Paths paths = PieceType.HORSE.calculatePaths(center, DEFAULT_SIDE);

        // then
        assertThat(paths).hasSize(8);
    }

    @DisplayName("마(HORSE)는 진행 방향의 멱이 막히는 경우, 해당 목적지로 이동할 수 없다.")
    @Test
    void 마_멱_차단_이동_검증_테스트() {
        // given
        Position current = new Position(4, 4);
        PieceType horseType = PieceType.HORSE;
        Paths paths = horseType.calculatePaths(current, DEFAULT_SIDE);

        // 남쪽 멱(5, 4)에 장애물 배치
        Map<Position, Piece> boardState = new HashMap<>();
        boardState.put(new Position(5, 4), createPiece(Side.CHO, PieceType.SOLDIER));

        Piece movingPiece = createPiece(Side.HAN, PieceType.HORSE);

        // when
        List<Position> destinations = horseType.determineDestinations(paths, boardState, movingPiece);

        // then
        assertAll(
                () -> assertThat(destinations).doesNotContain(new Position(6, 3), new Position(6, 5)),
                () -> assertThat(destinations).hasSize(6)
        );
    }

    @DisplayName("차(CHARIOT)는 장애물이 없는 경우, 보드 끝까지 이동할 수 있다.")
    @Test
    void 차_정상_이동_테스트() {
        // given
        Position current = new Position(0, 0);
        PieceType chariotType = PieceType.CHARIOT;
        Paths paths = chariotType.calculatePaths(current, DEFAULT_SIDE);

        Map<Position, Piece> boardState = new HashMap<>();
        Piece movingPiece = createPiece(Side.CHO, PieceType.CHARIOT);

        // when
        List<Position> destinations = chariotType.determineDestinations(paths, boardState, movingPiece);

        // then
        // (0, 0)에서 가로 8칸, 세로 9칸 총 17칸 이동 가능
        assertThat(destinations).hasSize(17);
    }

    @DisplayName("차(CHARIOT)가 보드 구석(0, 0)에 있는 경우, 판 안쪽으로만 경로를 생성한다.")
    @Test
    void 차_구석_경계_경로_테스트() {
        // given
        Position corner = new Position(0, 0);

        // when
        Paths paths = PieceType.CHARIOT.calculatePaths(corner, DEFAULT_SIDE);

        // then
        assertThat(paths).hasSize(2);
    }

    @DisplayName("졸(SOLDIER)은 장애물이 없는 경우, 정해진 방향으로 한 칸 이동할 수 있다.")
    @Test
    void 졸_정상_이동_테스트() {
        // given
        Position current = new Position(4, 4);
        PieceType soldierType = PieceType.SOLDIER;
        Paths paths = soldierType.calculatePaths(current, DEFAULT_SIDE);

        Map<Position, Piece> boardState = new HashMap<>();
        Piece movingPiece = createPiece(Side.CHO, PieceType.SOLDIER);

        // when
        List<Position> destinations = soldierType.determineDestinations(paths, boardState, movingPiece);

        // then
        assertThat(destinations).contains(new Position(3, 4), new Position(4, 5), new Position(4, 3));
    }

    @DisplayName("졸(SOLDIER)은 이동하려는 칸에 아군 기물이 있는 경우, 이동할 수 없다.")
    @Test
    void 졸_아군_차단_검증_테스트() {
        // given
        Position current = new Position(4, 4);
        PieceType soldierType = PieceType.SOLDIER;
        Paths paths = soldierType.calculatePaths(current, DEFAULT_SIDE);

        // 북쪽(3, 4)에 아군 기물 배치
        Map<Position, Piece> boardState = new HashMap<>();
        boardState.put(new Position(3, 4), createPiece(Side.CHO, PieceType.SOLDIER));

        Piece movingPiece = createPiece(Side.CHO, PieceType.SOLDIER);

        // when
        List<Position> destinations = soldierType.determineDestinations(paths, boardState, movingPiece);

        // then
        assertThat(destinations).doesNotContain(new Position(3, 4));
    }

    @DisplayName("졸(SOLDIER)은 상대 진영 궁성 입구에서, 대각선 전진 경로를 생성한다.")
    @Test
    void 졸_상대_궁성_입구_대각선_테스트() {
        // given
        // 초 진영의 졸이 한 진영의 궁성 입구에 위치
        Position current = new Position(2, 5);
        PieceType soldierType = PieceType.SOLDIER;

        // when
        Paths paths = soldierType.calculatePaths(current, Side.CHO);
        Map<Position, Piece> boardState = new HashMap<>();
        Piece movingPiece = createPiece(Side.CHO, PieceType.SOLDIER);
        List<Position> destinations = soldierType.determineDestinations(paths, boardState, movingPiece);

        // then
        // 직진(1, 5), 좌(2, 4), 우(2, 6) + 대각선(1, 4)
        assertThat(destinations).contains(new Position(1, 4));
        assertThat(destinations).hasSize(4);
    }

    @DisplayName("졸(SOLDIER)은 상대 진영 궁성 중앙에서, 대각선 전진 경로를 생성한다.")
    @Test
    void 졸_상대_궁성_중앙_대각선_테스트() {
        // given
        // 초 진영의 졸이 한 진영의 궁성 중앙에 위치
        Position current = new Position(1, 4);
        PieceType soldierType = PieceType.SOLDIER;

        // when
        Paths paths = soldierType.calculatePaths(current, Side.CHO);
        Map<Position, Piece> boardState = new HashMap<>();
        Piece movingPiece = createPiece(Side.CHO, PieceType.SOLDIER);
        List<Position> destinations = soldierType.determineDestinations(paths, boardState, movingPiece);

        // then
        // 직진(0, 4), 좌(1, 3), 우(1, 5) + 대각선 2방향(0, 3, 0, 5)
        assertThat(destinations).contains(new Position(0, 3), new Position(0, 5));
        assertThat(destinations).hasSize(5);
    }

    @DisplayName("졸(SOLDIER)은 어떠한 상황에서도 후퇴할 수 없다.")
    @Test
    void 졸_후퇴_불가_검증_테스트() {
        // given
        Position current = new Position(4, 4);
        PieceType soldierType = PieceType.SOLDIER;

        // when
        Paths paths = soldierType.calculatePaths(current, Side.CHO);
        Map<Position, Piece> boardState = new HashMap<>();
        Piece movingPiece = createPiece(Side.CHO, PieceType.SOLDIER);
        List<Position> destinations = soldierType.determineDestinations(paths, boardState, movingPiece);

        // then
        assertThat(destinations).doesNotContain(new Position(5, 4));
    }

    @DisplayName("포(CANNON)는 다리가 하나 있는 경우, 그 너머로 이동할 수 있다.")
    @Test
    void 포_정상_이동_테스트() {
        // given
        Position current = new Position(4, 0);
        PieceType cannonType = PieceType.CANNON;
        Paths paths = cannonType.calculatePaths(current, DEFAULT_SIDE);

        Map<Position, Piece> boardState = new HashMap<>();
        boardState.put(new Position(4, 2), createPiece(Side.CHO, PieceType.SOLDIER));
        Piece movingPiece = createPiece(Side.CHO, PieceType.CANNON);

        // when
        List<Position> destinations = cannonType.determineDestinations(paths, boardState, movingPiece);

        // then
        // (4, 2)를 다리로 삼아, (4, 3)부터 보드 끝(4, 8)까지 이동 가능
        assertThat(destinations).contains(new Position(4, 3), new Position(4, 8));
    }

    @Test
    @DisplayName("포(CANNON)는 뛰어넘을 다리가 없는 경우, 이동할 수 없다.")
    void 포_다리_없음_이동_불가_검증_테스트() {
        // given
        Position current = new Position(0, 0);
        PieceType cannonType = PieceType.CANNON;
        Paths paths = cannonType.calculatePaths(current, DEFAULT_SIDE);

        Map<Position, Piece> boardState = new HashMap<>();
        Piece movingPiece = createPiece(Side.CHO, PieceType.CANNON);

        // when
        List<Position> destinations = cannonType.determineDestinations(paths, boardState, movingPiece);

        // then
        assertThat(destinations).isEmpty();
    }

    @Test
    @DisplayName("포(CANNON)는 상대방의 포를 잡을 수 없다.")
    void 포_상대_포_포획_불가_검증_테스트() {
        // given
        Position current = new Position(4, 0);
        PieceType cannonType = PieceType.CANNON;
        Paths paths = cannonType.calculatePaths(current, DEFAULT_SIDE);

        Map<Position, Piece> boardState = new HashMap<>();
        boardState.put(new Position(4, 2), createPiece(Side.HAN, PieceType.SOLDIER)); // 다리
        boardState.put(new Position(4, 4), createPiece(Side.HAN, PieceType.CANNON));      // 적군 포

        Piece movingPiece = createPiece(Side.CHO, PieceType.CANNON);

        // when
        List<Position> destinations = cannonType.determineDestinations(paths, boardState, movingPiece);

        // then
        assertThat(destinations).doesNotContain(new Position(4, 4));
    }

    @DisplayName("기물은 목적지에 적군이 있는 경우, 그 기물을 잡고 멈춘다.")
    @Test
    void 적군_포획_테스트() {
        // given
        Position current = new Position(0, 0);
        PieceType chariotType = PieceType.CHARIOT;
        Paths paths = chariotType.calculatePaths(current, DEFAULT_SIDE);

        Map<Position, Piece> boardState = new HashMap<>();
        Position enemyPos = new Position(0, 3);
        boardState.put(enemyPos, createPiece(Side.HAN, PieceType.SOLDIER));

        Piece movingPiece = createPiece(Side.CHO, PieceType.CHARIOT);

        // when
        List<Position> destinations = chariotType.determineDestinations(paths, boardState, movingPiece);

        // then
        assertAll(
                () -> assertThat(destinations).contains(enemyPos), // 적군 자리는 갈 수 있음
                () -> assertThat(destinations).doesNotContain(new Position(0, 4)) // 적군 너머로는 못 감
        );
    }

    @DisplayName("상(ELEPHANT)은 보드 중앙에서 장애물이 없는 경우, 8개의 이동 경로를 생성한다.")
    @Test
    void 상_정상_이동_테스트() {
        // given
        Position current = new Position(4, 4);
        PieceType elephantType = PieceType.ELEPHANT;
        Paths paths = elephantType.calculatePaths(current, Side.CHO);

        Map<Position, Piece> boardState = new HashMap<>();
        Piece movingPiece = createPiece(Side.CHO, PieceType.ELEPHANT);

        // when
        List<Position> destinations = elephantType.determineDestinations(paths, boardState, movingPiece);

        // then
        assertThat(destinations).hasSize(8);
    }

    @DisplayName("궁(PALACE)은 궁성 중앙에서 장애물이 없는 경우, 8개의 이동 경로를 생성한다.")
    @Test
    void 궁_정상_이동_테스트() {
        // given
        Position current = new Position(1, 4);
        PieceType palaceType = PieceType.GENERAL;
        Paths paths = palaceType.calculatePaths(current, Side.HAN);

        Map<Position, Piece> boardState = new HashMap<>();
        Piece movingPiece = createPiece(Side.HAN, PieceType.GENERAL);

        // when
        List<Position> destinations = palaceType.determineDestinations(paths, boardState, movingPiece);

        // then
        // 상하좌우 + 대각선 4곳
        assertThat(destinations).hasSize(8)
                .containsExactlyInAnyOrder(
                        new Position(0, 4), new Position(2, 4), new Position(1, 3), new Position(1, 5),
                        new Position(0, 3), new Position(0, 5), new Position(2, 3), new Position(2, 5)
                );
    }

    @DisplayName("궁(PALACE)은 궁성 내에서 아군 기물이 있는 곳으로는 이동할 수 없다.")
    @Test
    void 궁_아군_차단_검증_테스트() {
        // given
        Position current = new Position(1, 4);
        PieceType palaceType = PieceType.GENERAL;
        Paths paths = palaceType.calculatePaths(current, Side.HAN);

        // 북쪽(0, 4)에 아군(사) 배치
        Map<Position, Piece> boardState = new HashMap<>();
        boardState.put(new Position(0, 4), createPiece(Side.HAN, PieceType.GUARD));

        Piece movingPiece = createPiece(Side.HAN, PieceType.GENERAL);

        // when
        List<Position> destinations = palaceType.determineDestinations(paths, boardState, movingPiece);

        // then
        assertThat(destinations).doesNotContain(new Position(0, 4));
        assertThat(destinations).hasSize(7); // 아군 제외 나머지 7방향 가능
    }

    @DisplayName("사(GUARD)는 궁성 꼭짓점에서 장애물이 없는 경우, 3방향으로 정상 이동한다.")
    @Test
    void 사_정상_이동_테스트() {
        // given
        Position current = new Position(9, 3);
        PieceType guardType = PieceType.GUARD;
        Paths paths = guardType.calculatePaths(current, Side.CHO);

        Map<Position, Piece> boardState = new HashMap<>();
        Piece movingPiece = createPiece(Side.CHO, PieceType.GUARD);

        // when
        List<Position> destinations = guardType.determineDestinations(paths, boardState, movingPiece);

        // then
        assertThat(destinations).hasSize(3)
                .containsExactlyInAnyOrder(
                        new Position(8, 3), new Position(9, 4), new Position(8, 4)
                );
    }

    @DisplayName("사(GUARD)는 궁성 내에서 적군 기물이 있는 곳으로 이동하여 포획할 수 있다.")
    @Test
    void 사_적군_포획_검증_테스트() {
        // given
        Position current = new Position(8, 4);
        PieceType guardType = PieceType.GUARD;
        Paths paths = guardType.calculatePaths(current, Side.CHO);

        // 동쪽(8, 5)에 적군(차) 배치
        Map<Position, Piece> boardState = new HashMap<>();
        Position enemyPos = new Position(8, 5);
        boardState.put(enemyPos, createPiece(Side.HAN, PieceType.CHARIOT));

        Piece movingPiece = createPiece(Side.CHO, PieceType.GUARD);

        // when
        List<Position> destinations = guardType.determineDestinations(paths, boardState, movingPiece);

        // then
        assertThat(destinations).contains(enemyPos);
    }

    private Piece createPiece(Side side, PieceType type) {
        return new Piece(side, type, "0");
    }
}
