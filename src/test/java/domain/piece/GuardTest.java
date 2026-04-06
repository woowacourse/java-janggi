package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.Destinations;
import domain.Position;
import domain.Side;
import domain.board.Board;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GuardTest {
    @Test
    @DisplayName("사는 상하좌우 1칸 이동하며 아군 기물이 있으면 이동할 수 없다")
    void move() {
        Position current = Position.of(3, 1);
        Map<Position, Piece> pieces = Map.of(
                current, PieceFactory.createGuard(Side.CHO),
                Position.of(3, 2), PieceFactory.createChariot(Side.CHO)
        );
        Board board = new Board(pieces);

        Destinations movable = board.findDestinations(current);

        assertThat(movable.getPositions()).containsExactlyInAnyOrder(
                Position.of(4, 1), Position.of(2, 1), Position.of(3,0)
        );
    }

    @DisplayName("사는 이동 경로 탐색 시 자신의 궁성 영역을 벗어날 수 없다.")
    @Test
    void findDestinations_generalAtBoundary() {
        // given
        Position current = Position.of(3, 1);
        Map<Position, Piece> pieces = Map.of(current, PieceFactory.createGuard(Side.CHO));
        Board board = new Board(pieces);

        // when
        Destinations actual = board.findDestinations(current);

        // then
        assertThat(actual.getPositions()).containsExactlyInAnyOrder(
                Position.of(3, 0),
                Position.of(3, 2),
                Position.of(4, 1)
        );
    }

    @DisplayName("사는 궁성 내 대각선 경로가 존재하는 위치에서 해당 대각선 방향의 1칸 이동 경로를 추가한다.")
    @Test
    void getDestinations_includeDiagonals() {
        // given
        Position current = Position.of(3, 0);
        Map<Position, Piece> pieces = Map.of(current, PieceFactory.createGeneral(Side.CHO));
        Board board = new Board(pieces);

        // when
        Destinations actual = board.findDestinations(current);

        // then
        assertThat(actual.getPositions()).containsExactlyInAnyOrder(
                Position.of(3, 1),
                Position.of(4, 0),
                Position.of(4, 1)
        );
    }
}
