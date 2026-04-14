package domain.piece;

import domain.Side;
import domain.coordinate.Position;
import domain.coordinate.Topology;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ElephantTest {

    private static final Topology DEFAULT_TOPOLOGY = new Topology(Map.of());

    private Pieces piecesFrom(Map<Position, Piece> pieces) {
        return new Pieces() {
            @Override
            public Piece getPiece(Position position) {
                return pieces.getOrDefault(position, EmptyPiece.getInstance());
            }

            @Override
            public Topology getTopology() {
                return DEFAULT_TOPOLOGY;
            }
        };
    }

    @Test
    @DisplayName("상은 상/하/좌/우 1칸 이동 후 대각선으로 2칸 이동한다.")
    void getPossibleMovesTest() {
        // given
        Elephant elephant = new Elephant(Side.HAN);
        Position start = new Position(4, 4);
        Pieces pieces = piecesFrom(Map.of(start, elephant));

        // when
        List<Position> moves = elephant.getPossibleMoves(start, pieces);

        // then
        assertThat(moves).containsOnly(
                new Position(1, 2), new Position(1, 6),
                new Position(2, 7), new Position(6, 7),
                new Position(7, 6), new Position(7, 2),
                new Position(6, 1), new Position(2, 1)
        );
    }

    @Test
    @DisplayName("상은 1차 경로에 기물이 있으면 뛰어넘을 수 없다.")
    void firstMoveBlockTest() {
        // given
        Elephant elephant = new Elephant(Side.HAN);
        Position start = new Position(0, 0);
        Pieces pieces = piecesFrom(Map.of(
                start, elephant,
                new Position(0, 1), new Horse(Side.CHU),
                new Position(1, 0), new Horse(Side.HAN)
        ));

        // when
        List<Position> moves = elephant.getPossibleMoves(start, pieces);

        // then
        assertThat(moves).isEmpty();
    }

    @Test
    @DisplayName("상은 2차 경로에 기물이 있으면 뛰어넘을 수 없다.")
    void secondMoveBlockTest() {
        // given
        Elephant elephant = new Elephant(Side.HAN);
        Position start = new Position(9, 8);
        Pieces pieces = piecesFrom(Map.of(
                start, elephant,
                new Position(7, 7), new Horse(Side.CHU),
                new Position(8, 6), new Horse(Side.HAN)
        ));

        // when
        List<Position> moves = elephant.getPossibleMoves(start, pieces);

        // then
        assertThat(moves).isEmpty();
    }

    @Test
    @DisplayName("상은 아군 기물이 있는 위치로 이동할 수 없다.")
    void blockedByFriendlyTest() {
        // given
        Elephant elephant = new Elephant(Side.HAN);
        Position start = new Position(9, 0);
        Pieces pieces = piecesFrom(Map.of(
                start, elephant,
                new Position(6, 2), new Horse(Side.HAN)
        ));

        // when
        List<Position> moves = elephant.getPossibleMoves(start, pieces);

        // then
        assertThat(moves).doesNotContain(new Position(6, 2));
    }

    @Test
    @DisplayName("상은 상대 기물이 있는 위치로 이동할 수 있다.")
    void captureTest() {
        // given
        Elephant elephant = new Elephant(Side.HAN);
        Position start = new Position(9, 0);
        Pieces pieces = piecesFrom(Map.of(
                start, elephant,
                new Position(7, 3), new Horse(Side.CHU)
        ));

        // when
        List<Position> moves = elephant.getPossibleMoves(start, pieces);

        // then
        assertThat(moves).contains(new Position(7, 3));
    }
}
