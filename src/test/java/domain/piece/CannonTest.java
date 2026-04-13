package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.movement.Destinations;
import domain.common.Position;
import domain.common.Side;
import domain.board.Board;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CannonTest {

    @Test
    @DisplayName("포는 상하좌우 방향으로 기물 하나를 뛰어넘어 빈칸으로 이동할 수 있다")
    void jumpOverBridge() {
        // Given: (1, 1)에 포, (1, 3)에 다리(졸) 배치
        Position current = Position.of(1, 1);
        Position bridge = Position.of(1, 3);
        Map<Position, Piece> pieces = Map.of(
                current, PieceFactory.createCannon(Side.CHO),
                bridge, PieceFactory.createSoldier(Side.CHO)
        );
        Board board = new Board(pieces);

        // When
        Destinations movable = board.findDestinations(current);

        // Then: 다리(1, 3) 이전인 (1, 2)는 못 가고, 다리 너머인 (1, 4)부터 끝까지 이동 가능
        assertThat(movable.getPositions()).doesNotContain(Position.of(1, 2));
        assertThat(movable.getPositions()).contains(Position.of(1, 4), Position.of(1, 9));
    }

    @Test
    @DisplayName("포는 이동 경로에서 포 기물을 뛰어넘을 수 없다")
    void cannotJumpOverAnotherCannon() {
        // Given: (1, 1)에 포, (1, 3)에 다른 포(다리 역할 시도) 배치
        Position current = Position.of(1, 1);
        Position cannonBridge = Position.of(1, 3);
        Map<Position, Piece> pieces = Map.of(
                current, PieceFactory.createCannon(Side.CHO),
                cannonBridge, PieceFactory.createCannon(Side.HAN)
        );
        Board board = new Board(pieces);

        // When & Then 이동할 목적지가 없어서 예외가 발생
        assertThatThrownBy(() -> board.findDestinations(current))
                .isInstanceOf(IllegalArgumentException.class);

    }

    @Test
    @DisplayName("포는 기물을 뛰어넘은 후 적군 기물을 만나면 해당 위치까지 이동하여 잡을 수 있다")
    void captureEnemy() {
        // Given: (1, 1)에 포, (1, 3)에 다리, (1, 5)에 적군(졸) 배치
        Position current = Position.of(1, 1);
        Position bridge = Position.of(1, 3);
        Position enemy = Position.of(1, 5);
        Map<Position, Piece> pieces = Map.of(
                current, PieceFactory.createCannon(Side.CHO),
                bridge, PieceFactory.createSoldier(Side.CHO),
                enemy, PieceFactory.createSoldier(Side.HAN)
        );
        Board board = new Board(pieces);

        // When
        Destinations movable = board.findDestinations(current);

        // Then: 적군(1, 5)까지는 갈 수 있지만, 그 너머(1, 6)는 갈 수 없음
        assertThat(movable.getPositions()).contains(Position.of(1, 4), Position.of(1, 5));
        assertThat(movable.getPositions()).doesNotContain(Position.of(1, 6));
    }

    @Test
    @DisplayName("포는 뛰어넘은 후 만난 적군 기물이 포일 경우 잡을 수 없다")
    void cannotCaptureEnemyCannon() {
        // Given: (1, 1)에 포, (1, 3)에 다리, (1, 5)에 적군 포 배치
        Position current = Position.of(1, 1);
        Position bridge = Position.of(1, 3);
        Position enemyCannon = Position.of(1, 5);
        Map<Position, Piece> pieces = Map.of(
                current, PieceFactory.createCannon(Side.CHO),
                bridge, PieceFactory.createSoldier(Side.CHO),
                enemyCannon, PieceFactory.createCannon(Side.HAN)
        );
        Board board = new Board(pieces);

        // When
        Destinations movable = board.findDestinations(current);

        // Then: 적군 포(1, 5) 직전인 (1, 4)까지만 갈 수 있고 (1, 5)는 포함되지 않음
        assertThat(movable.getPositions()).contains(Position.of(1, 4));
        assertThat(movable.getPositions()).doesNotContain(Position.of(1, 5));
    }

    @Test
    @DisplayName("포는 궁성 대각선에서 다리를 뛰어넘어 이동할 수 있다")
    void jumpOverBridgeInPalaceDiagonal() {
        Position current = Position.of(3, 0);
        Position bridge = Position.of(4, 1);
        Board board = new Board(Map.of(
                current, PieceFactory.createCannon(Side.CHO),
                bridge, PieceFactory.createSoldier(Side.CHO)
        ));

        Destinations movable = board.findDestinations(current);

        assertThat(movable.getPositions()).contains(Position.of(5, 2));
    }

    @Test
    @DisplayName("포는 궁성 대각선에서 다리가 없으면 이동할 수 없다")
    void cannotMoveWithoutBridgeInPalaceDiagonal() {
        Position current = Position.of(3, 0);
        Board board = new Board(Map.of(current, PieceFactory.createCannon(Side.CHO)));

        assertThatThrownBy(() -> board.findDestinations(current))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
