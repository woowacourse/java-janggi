package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.movement.Destinations;
import domain.common.Position;
import domain.common.Side;
import domain.board.Board;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChariotTest {

    @Test
    @DisplayName("차는 상하좌우 방향으로 장애물을 만날 때까지 연속해서 이동할 수 있다")
    void move() {
        // Given: 빈 보드의 (0, 0)에 차 배치
        Position current = Position.of(0, 0);
        Map<Position, Piece> pieces = Map.of(current, PieceFactory.createChariot(Side.CHO));
        Board board = new Board(pieces);

        // When
        Destinations movable = board.findDestinations(current);

        // Then: 같은 행(0, 1~9)과 같은 열(1~8, 0)의 모든 위치가 포함되어야 함 (총 9+8=17개)
        assertThat(movable.getPositions()).hasSize(17);
        assertThat(movable.getPositions()).contains(Position.of(0, 9), Position.of(8, 0));
    }

    @Test
    @DisplayName("차는 이동 경로 중 아군 기물을 만나면 그 직전 위치까지만 이동할 수 있다")
    void allyObstacle() {
        // Given: (0, 0)에 차, (0, 3)에 아군 배치
        Position current = Position.of(0, 0);
        Position ally = Position.of(0, 3);
        Map<Position, Piece> pieces = Map.of(
                current, PieceFactory.createChariot(Side.CHO),
                ally, PieceFactory.createSoldier(Side.CHO)
        );
        Board board = new Board(pieces);

        // When
        Destinations movable = board.findDestinations(current);

        // Then: (0, 1), (0, 2)는 가능하지만 (0, 3)과 그 너머(0, 4)는 불가능해야 함
        assertThat(movable.getPositions()).contains(Position.of(0, 1), Position.of(0, 2));
        assertThat(movable.getPositions()).doesNotContain(Position.of(0, 3), Position.of(0, 4));
    }

    @Test
    @DisplayName("차는 이동 경로 중 적군 기물을 만나면 해당 위치까지 이동하여 잡을 수 있다")
    void enemyCapture() {
        // Given: (0, 0)에 차, (5, 0)에 적군 배치
        Position current = Position.of(0, 0);
        Position enemy = Position.of(5, 0);
        Map<Position, Piece> pieces = Map.of(
                current, PieceFactory.createChariot(Side.CHO),
                enemy, PieceFactory.createSoldier(Side.HAN)
        );
        Board board = new Board(pieces);

        // When
        Destinations movable = board.findDestinations(current);

        // Then: 적군이 있는 (5, 0)까지는 이동 가능하지만, 그 너머(6, 0)는 불가능해야 함
        assertThat(movable.getPositions()).contains(Position.of(1, 0), Position.of(4, 0), Position.of(5, 0));
        assertThat(movable.getPositions()).doesNotContain(Position.of(6, 0));
    }

    @Test
    @DisplayName("차는 궁성 대각선으로 연속 이동할 수 있다")
    void moveDiagonalInPalace() {
        Position current = Position.of(3, 0);
        Board board = new Board(Map.of(current, PieceFactory.createChariot(Side.CHO)));

        Destinations movable = board.findDestinations(current);

        assertThat(movable.getPositions()).contains(Position.of(4, 1), Position.of(5, 2));
    }

    @Test
    @DisplayName("차는 궁성 대각선 경로에서 아군을 만나면 더 이동할 수 없다")
    void allyObstacleInPalaceDiagonal() {
        Position current = Position.of(3, 0);
        Position ally = Position.of(4, 1);
        Board board = new Board(Map.of(
                current, PieceFactory.createChariot(Side.CHO),
                ally, PieceFactory.createSoldier(Side.CHO)
        ));

        Destinations movable = board.findDestinations(current);

        assertThat(movable.getPositions()).doesNotContain(Position.of(4, 1), Position.of(5, 2));
    }
}
