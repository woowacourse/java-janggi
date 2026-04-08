package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.space.Destinations;
import janggi.domain.space.Position;
import janggi.domain.Side;
import janggi.domain.board.Board;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GeneralTest {

    @Test
    @DisplayName("궁은 상하좌우 1칸 이동하며, 범위를 벗어나거나 아군이 있으면 이동할 수 없다")
    void move() {
        // given
        Position current = Position.of(4, 1);
        Map<Position, Piece> pieces = Map.of(
                current, PieceFactory.createGeneral(Side.CHO),
                Position.of(4, 2), PieceFactory.createGuard(Side.CHO)
        );
        Board board = new Board(pieces);

        // when
        Destinations actual = board.findDestinations(current);

        // then
        assertThat(actual.getPositions()).containsExactlyInAnyOrder(
                Position.of(4, 0),
                Position.of(3, 1),
                Position.of(5, 1),
                Position.of(3, 0),
                Position.of(5, 0),
                Position.of(3, 2),
                Position.of(5, 2)
        );
    }

    @DisplayName("궁은 이동 경로 탐색 시 자신의 궁성 영역 안의 좌표를 목적지에 포함한다.")
    @Test
    void findDestinations_generalAtBoundary() {
        // given
        Position current = Position.of(3, 1);
        Map<Position, Piece> pieces = Map.of(current, PieceFactory.createGeneral(Side.CHO));
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

    @DisplayName("궁은 궁성 내 대각선 경로가 존재하는 위치에서 해당 대각선 방향의 1칸 이동 경로를 추가한다.")
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
