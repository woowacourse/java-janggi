package domain.piece;

import domain.Side;
import domain.coordinate.Position;
import domain.coordinate.Topology;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class HorseTest {

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
    @DisplayName("마는 상/하/좌/우 1칸 이동 후 대각선으로 이동한다.")
    void getPossibleMovesTest() {
        // given
        Horse horse = new Horse(Side.HAN);
        Position start = new Position(4, 4);
        Pieces pieces = piecesFrom(Map.of(start, horse));

        // when
        List<Position> moves = horse.getPossibleMoves(start, pieces);

        // then
        assertThat(moves).containsOnly(
                new Position(2, 3), new Position(2, 5),
                new Position(3, 2), new Position(3, 6),
                new Position(5, 2), new Position(5, 6),
                new Position(6, 3), new Position(6, 5)
        );
    }

    @Test
    @DisplayName("마는 1차 경로에 기물이 있으면 뛰어넘을 수 없다.")
    void firstMoveBlockTest() {
        // given
        Horse horse = new Horse(Side.HAN);
        Position start = new Position(1, 1);
        Pieces pieces = piecesFrom(Map.of(
                start, horse,
                new Position(0, 1), new Horse(Side.CHU),
                new Position(1, 0), new Horse(Side.CHU),
                new Position(2, 1), new Horse(Side.HAN),
                new Position(1, 2), new Horse(Side.HAN)
        ));

        // when
        List<Position> moves = horse.getPossibleMoves(start, pieces);

        // then
        assertThat(moves).isEmpty();
    }

    @Test
    @DisplayName("마는 아군 기물이 있는 위치로 이동할 수 없다.")
    void blockedByFriendlyTest() {
        // given
        Horse horse = new Horse(Side.HAN);
        Position start = new Position(9, 0);
        Pieces pieces = piecesFrom(Map.of(
                start, horse,
                new Position(7, 1), new Horse(Side.HAN)
        ));

        // when
        List<Position> moves = horse.getPossibleMoves(start, pieces);

        // then
        assertThat(moves).doesNotContain(new Position(7, 1));
    }

    @Test
    @DisplayName("마는 상대 기물이 있는 위치로 이동할 수 있다.")
    void captureTest() {
        // given
        Horse horse = new Horse(Side.HAN);
        Position start = new Position(9, 0);
        Pieces pieces = piecesFrom(Map.of(
                start, horse,
                new Position(8, 2), new Horse(Side.CHU)
        ));

        // when
        List<Position> moves = horse.getPossibleMoves(start, pieces);

        // then
        assertThat(moves).contains(new Position(8, 2));
    }
}