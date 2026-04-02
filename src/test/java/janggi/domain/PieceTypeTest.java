package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.domain.board.Position;
import janggi.domain.game.Side;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.route.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PieceTypeTest {
    private static final Side DEFAULT_SIDE = Side.CHO;

    @DisplayName("마(HORSE)는 보드 중앙에서 장애물이 없을 때, 8개의 이동 경로를 생성한다.")
    @Test
    void 마_중앙_경로_생성_테스트() {
        // given
        Position center = new Position(4, 4);

        // when
        Paths paths = PieceType.HORSE.calculatePaths(center, DEFAULT_SIDE);

        // then
        assertThat(paths).hasSize(8);
    }

    @DisplayName("마(HORSE)는 진행 방향의 멱이 막히면, 해당 목적지로 이동할 수 없다.")
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

    @DisplayName("차(CHARIOT)가 보드 구석(0, 0)에 있을 때, 판 안쪽으로만 경로를 생성한다.")
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

    @DisplayName("졸(SOLDIER)은 이동하려는 칸에 아군 기물이 있으면, 이동할 수 없다.")
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
    @DisplayName("포(CANNON)는 뛰어넘을 다리가 없으면, 이동할 수 없다.")
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

    private Piece createPiece(Side side, PieceType type) {
        return new Piece(side, type, "0");
    }
}
