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

class KingTest {

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
    @DisplayName("장은 해당 좌표에서 이동 가능한 모든 방향으로 1칸 이동할 수 있다.")
    void getPossibleMovesTest() {
        // given
        King king = new King(Side.HAN);
        Position start = new Position(1, 4);
        Pieces pieces = piecesFrom(Map.of(start, king));

        // when
        List<Position> moves = king.getPossibleMoves(start, pieces);

        // then
        assertThat(moves).containsOnly(
                new Position(0, 4),
                new Position(2, 4),
                new Position(1, 3),
                new Position(1, 5),
                new Position(0, 3),
                new Position(0, 5),
                new Position(2, 3),
                new Position(2, 5)
        );
    }

    @Test
    @DisplayName("장은 아군 기물이 있는 위치로 이동할 수 없다.")
    void blockedByFriendlyTest() {
        // given
        King king = new King(Side.HAN);
        Position start = new Position(1, 4);
        Pieces pieces = piecesFrom(Map.of(
                start, king,
                new Position(0, 4), new Guard(Side.HAN),
                new Position(2, 4), new Guard(Side.HAN),
                new Position(1, 3), new Guard(Side.HAN),
                new Position(1, 5), new Guard(Side.HAN),
                new Position(0, 3), new Guard(Side.HAN),
                new Position(0, 5), new Guard(Side.HAN),
                new Position(2, 3), new Guard(Side.HAN),
                new Position(2, 5), new Guard(Side.HAN)
        ));

        // when
        List<Position> moves = king.getPossibleMoves(start, pieces);

        // then
        assertThat(moves).isEmpty();
    }

    @Test
    @DisplayName("장은 상대 기물이 있는 위치로 이동할 수 있다.")
    void captureTest() {
        // given
        King king = new King(Side.HAN);
        Position start = new Position(1, 4);
        Pieces pieces = piecesFrom(Map.of(
                start, king,
                new Position(0, 4), new Guard(Side.CHU)
        ));

        // when
        List<Position> moves = king.getPossibleMoves(start, pieces);

        // then
        assertThat(moves).contains(new Position(0, 4));
    }

    @Test
    @DisplayName("장은 궁성 밖으로 이동할 수 없다.")
    void cannotMoveOutsidePalace() {
        // given
        King king = new King(Side.CHU);
        Position start = new Position(7, 3); // 하단 궁성 좌상단 모서리
        Pieces pieces = piecesFrom(Map.of(start, king));

        // when
        List<Position> moves = king.getPossibleMoves(start, pieces);

        // then
        assertThat(moves).doesNotContain(
                new Position(7, 2),
                new Position(6, 3)
        );
    }
}