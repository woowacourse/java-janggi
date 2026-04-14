package domain.piece;

import domain.Side;
import domain.board.BasicBoardInitializer;
import domain.coordinate.Position;
import domain.coordinate.Topology;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class GuardTest {

    private static final Topology TOPOLOGY = new BasicBoardInitializer().createTopology();

    private Pieces piecesFrom(Map<Position, Piece> pieces) {
        return new Pieces() {
            @Override
            public Piece getPiece(Position position) {
                return pieces.getOrDefault(position, EmptyPiece.getInstance());
            }

            @Override
            public Topology getTopology() {
                return TOPOLOGY;
            }
        };
    }

    @Test
    @DisplayName("사는 해당 좌표에서 이동 가능한 모든 방향으로 1칸 이동할 수 있다.")
    void getPossibleMovesTest() {
        // given
        Guard guard = new Guard(Side.HAN);
        Position start = new Position(0, 3); // 궁성 좌상단 꼭짓점 (초기 위치)
        Pieces pieces = piecesFrom(Map.of(start, guard));

        // when
        List<Position> moves = guard.getPossibleMoves(start, pieces);

        // then
        assertThat(moves).containsOnly(
                new Position(1, 3),
                new Position(0, 4),
                new Position(1, 4)  // 대각선 (궁성 꼭짓점 → 중앙)
        );
    }

    @Test
    @DisplayName("사는 아군 기물이 있는 위치로 이동할 수 없다.")
    void blockedByFriendlyTest() {
        // given
        Guard guard = new Guard(Side.HAN);
        Position start = new Position(0, 3);
        Pieces pieces = piecesFrom(Map.of(
                start, guard,
                new Position(1, 3), new Guard(Side.HAN),
                new Position(0, 4), new Guard(Side.HAN),
                new Position(1, 4), new Guard(Side.HAN)
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
        Position start = new Position(0, 3);
        Pieces pieces = piecesFrom(Map.of(
                start, guard,
                new Position(0, 4), new Guard(Side.CHU)
        ));

        // when
        List<Position> moves = guard.getPossibleMoves(start, pieces);

        // then
        assertThat(moves).contains(new Position(0, 4));
    }

    @Test
    @DisplayName("사는 궁성 밖으로 이동할 수 없다.")
    void cannotMoveOutsidePalace() {
        // given
        Guard guard = new Guard(Side.HAN);
        Position start = new Position(0, 3);
        Pieces pieces = piecesFrom(Map.of(start, guard));

        // when
        List<Position> moves = guard.getPossibleMoves(start, pieces);

        // then
        assertThat(moves).doesNotContain(
                new Position(0, 2)
        );
    }
}
