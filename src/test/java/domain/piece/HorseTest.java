package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.game.Destinations;
import domain.game.Position;
import domain.game.Side;
import domain.board.Board;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HorseTest {

    @Test
    @DisplayName("마는 직선 1칸 이동 후 대각선 1칸 방향으로 이동할 수 있다")
    void move() {
        // Given: (4, 4)에 마 배치 (장애물 없는 상태)
        Position current = Position.of(4, 4);
        Map<Position, Piece> pieces = Map.of(current, PieceFactory.createHorse(Side.CHO));
        Board board = new Board(pieces);

        // When
        Destinations movable = board.findDestinations(current);

        // Then: 8방향의 L자형 목적지 확인
        assertThat(movable.getPositions()).containsExactlyInAnyOrder(
                Position.of(3, 6), Position.of(5, 6), // 북쪽 방향 2개
                Position.of(6, 5), Position.of(6, 3), // 동쪽 방향 2개
                Position.of(5, 2), Position.of(3, 2), // 남쪽 방향 2개
                Position.of(2, 3), Position.of(2, 5)  // 서쪽 방향 2개
        );
    }

    @Test
    @DisplayName("마는 직선 1칸 경로(멱)에 기물이 존재하면 해당 방향으로 이동할 수 없다")
    void bridge() {
        // Given: (4, 4)에 마 배치, 북쪽 멱(4, 5)에 장애물 배치
        Position current = Position.of(4, 4);
        Position northBridge = Position.of(4, 5);
        Map<Position, Piece> pieces = Map.of(
                current, PieceFactory.createHorse(Side.CHO),
                northBridge, PieceFactory.createSoldier(Side.HAN)
        );
        Board board = new Board(pieces);

        // When
        Destinations movable = board.findDestinations(current);

        // Then: 북쪽 멱이 막혔으므로 북쪽 대각선 목적지인 (3, 6)과 (5, 6)은 제외되어야 함
        assertThat(movable.getPositions()).doesNotContain(
                Position.of(3, 6),
                Position.of(5, 6)
        );

        // 나머지 6개 방향은 정상 이동 가능해야 함
        assertThat(movable.getPositions()).hasSize(6);
    }
}
