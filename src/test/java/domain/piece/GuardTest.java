package domain.piece;

import domain.Side;
import domain.coordinate.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class GuardTest {

private Pieces piecesFrom(Map<Position, Piece> pieces) {
        return position -> pieces.getOrDefault(position, EmptyPiece.getInstance());
    }

    @Test
    @DisplayName("사는 상/하/좌/우 4방향으로 1칸 이동할 수 있다.")
    void getPossibleMovesTest() {
        // given
        Guard guard = new Guard(Side.HAN);
        Position start = new Position(4, 4);
        Pieces pieces = piecesFrom(Map.of(start, guard));

        // when
        List<Position> moves = guard.getPossibleMoves(start, pieces);

        // then
        assertThat(moves).containsOnly(
                new Position(3, 4),
                new Position(5, 4),
                new Position(4, 3),
                new Position(4, 5)
        );
    }

    @Test
    @DisplayName("사는 아군 기물이 있는 위치로 이동할 수 없다.")
    void blockedByFriendlyTest() {
        // given
        Guard guard = new Guard(Side.HAN);
        Position start = new Position(4, 4);
        Pieces pieces = piecesFrom(Map.of(
                start, guard,
                new Position(3, 4), new Guard(Side.HAN),
                new Position(5, 4), new Guard(Side.HAN),
                new Position(4, 3), new Guard(Side.HAN),
                new Position(4, 5), new Guard(Side.HAN)
        ));

        // when
        List<Position> moves = guard.getPossibleMoves(start, pieces);

        // then
        assertThat(moves).isEmpty();
    }

    @Test
    @DisplayName("사는 상대 기물이 있는 위치로 이동할 수 있다.")
    void captureTest() {
        // given
        Guard guard = new Guard(Side.HAN);
        Position start = new Position(4, 4);
        Pieces pieces = piecesFrom(Map.of(
                start, guard,
                new Position(3, 4), new Guard(Side.CHU)
        ));

        // when
        List<Position> moves = guard.getPossibleMoves(start, pieces);

        // then
        assertThat(moves).contains(new Position(3, 4));
    }

    @Test
    @DisplayName("사는 보드 가장자리에서 범위를 벗어나는 방향으로 이동할 수 없다.")
    void edgeTest() {
        // given
        Guard guard = new Guard(Side.HAN);
        Position start = new Position(0, 0);
        Pieces pieces = piecesFrom(Map.of(start, guard));

        // when
        List<Position> moves = guard.getPossibleMoves(start, pieces);

        // then
        assertThat(moves).containsOnly(
                new Position(1, 0),
                new Position(0, 1)
        );
    }
}