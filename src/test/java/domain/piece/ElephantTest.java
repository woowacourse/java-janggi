package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.game.Destinations;
import domain.game.Position;
import domain.game.Side;
import domain.board.Board;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ElephantTest {

    @Test
    @DisplayName("상은 직선 1칸 이동 후 같은 방향 대각선으로 2칸 이동할 수 있다")
    void move() {
        // Given: (4, 4)에 상 배치 (장애물 없는 상태)
        Position current = Position.of(4, 4);
        Map<Position, Piece> pieces = Map.of(current, PieceFactory.createElephant(Side.CHO));
        Board board = new Board(pieces);

        // When
        Destinations movable = board.findDestinations(current);

        // Then: 8방향의 최종 목적지 확인
        assertThat(movable.getPositions()).containsExactlyInAnyOrder(
                Position.of(6, 7), Position.of(2, 7), // 북쪽 기반
                Position.of(7, 6), Position.of(7, 2), // 동쪽 기반
                Position.of(6, 1), Position.of(2, 1), // 남쪽 기반
                Position.of(1, 6), Position.of(1, 2)  // 서쪽 기반
        );
    }

    @Test
    @DisplayName("상은 직선 1칸 또는 대각선 1칸 경로(멱) 중 하나라도 기물이 존재하면 이동할 수 없다")
    void bridge() {
        // Given: (4, 4)에 상 배치
        Position current = Position.of(4, 4);

        // 1. 직선 1칸 멱(4, 5)에 장애물 배치 -> 북쪽 기반 (6, 7)과 (2, 7) 경로 차단
        // 2. 대각선 1칸 멱(5, 2)에 장애물 배치 -> 남동쪽 목적지 (6, 1) 경로 차단
        Map<Position, Piece> pieces = Map.of(
                current, PieceFactory.createElephant(Side.CHO),
                Position.of(4, 5), PieceFactory.createSoldier(Side.HAN), // 북쪽 직진 멱
                Position.of(5, 2), PieceFactory.createSoldier(Side.HAN)  // 남동쪽 대각선 멱 (수정됨)
        );
        Board board = new Board(pieces);

        // When
        Destinations movable = board.findDestinations(current);

        // Then
        // 1. 북쪽 직진 멱이 막혔으므로 (6, 7)과 (2, 7)은 없어야 함
        assertThat(movable.getPositions()).doesNotContain(
                Position.of(6, 7),
                Position.of(2, 7)
        );

        // 2. 대각선 멱(5, 2)이 막혔으므로 (6, 1)은 없어야 함
        assertThat(movable.getPositions()).doesNotContain(
                Position.of(6, 1)
        );

        // 장애물이 없는 다른 방향(예: 서쪽 기반 1, 6 등)은 유지되어야 함
        assertThat(movable.getPositions()).contains(Position.of(1, 6));
    }
}
