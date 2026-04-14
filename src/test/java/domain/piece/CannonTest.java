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

class CannonTest {

    private static final Topology DEFAULT_TOPOLOGY = new Topology(Map.of());
    private static final Topology PALACE_TOPOLOGY = new BasicBoardInitializer().createTopology();

    private Pieces piecesFrom(Map<Position, Piece> pieces) {
        return piecesFrom(pieces, DEFAULT_TOPOLOGY);
    }

    private Pieces piecesFrom(Map<Position, Piece> pieces, Topology topology) {
        return new Pieces() {
            @Override
            public Piece getPiece(Position position) {
                return pieces.getOrDefault(position, EmptyPiece.getInstance());
            }

            @Override
            public Topology getTopology() {
                return topology;
            }
        };
    }

    @Test
    @DisplayName("포는 1개의 기물을 뛰어넘은 후 n칸 이동할 수 있다.")
    void getPossibleMovesTest() {
        // given
        Cannon cannon = new Cannon(Side.HAN);
        Position start = new Position(9, 0);
        Pieces pieces = piecesFrom(Map.of(
                start, cannon,
                new Position(2, 0), new Pawn(Side.HAN),
                new Position(9, 6), new Pawn(Side.CHU)
        ));

        // when
        List<Position> moves = cannon.getPossibleMoves(start, pieces);

        // then
        assertThat(moves).containsOnly(
                new Position(0, 0), new Position(1, 0),
                new Position(9, 7), new Position(9, 8)
        );
    }

    @Test
    @DisplayName("포는 정확히 1개의 기물만 뛰어넘을 수 있다.")
    void doesNotJumpTest() {
        // given
        Cannon cannon = new Cannon(Side.HAN);
        Position start = new Position(7, 1);
        Pieces pieces = piecesFrom(Map.of(
                start, cannon,
                new Position(1, 1), new Pawn(Side.HAN),
                new Position(4, 1), new Pawn(Side.CHU),
                new Position(7, 5), new Pawn(Side.CHU),
                new Position(7, 6), new Pawn(Side.HAN)
        ));

        // when
        List<Position> moves = cannon.getPossibleMoves(start, pieces);

        // then
        assertThat(moves).containsOnly(
                new Position(2, 1), new Position(3, 1)
        );
    }

    @Test
    @DisplayName("포는 상대 기물이 있는 위치로 이동할 수 있다.")
    void captureTest() {
        // given
        Cannon cannon = new Cannon(Side.HAN);
        Position start = new Position(8, 6);
        Pieces pieces = piecesFrom(Map.of(
                start, cannon,
                new Position(7, 6), new Pawn(Side.HAN),
                new Position(6, 6), new Pawn(Side.CHU)
        ));

        // when
        List<Position> moves = cannon.getPossibleMoves(start, pieces);

        // then
        assertThat(moves).contains(new Position(6, 6));
    }

    @Test
    @DisplayName("포는 아군과 상대 포 모두 뛰어넘거나 잡을 수 없다.")
    void doesNotCaptureAndJumpCannonTest() {
        // given
        Cannon cannon = new Cannon(Side.HAN);
        Position start = new Position(4, 4);
        Pieces pieces = piecesFrom(Map.of(
                start, cannon,
                new Position(4, 5), new Cannon(Side.CHU),
                new Position(4, 3), new Cannon(Side.HAN),
                new Position(4, 7), new Pawn(Side.CHU),
                new Position(4, 2), new Pawn(Side.CHU),
                new Position(2, 4), new Pawn(Side.HAN),
                new Position(1, 4), new Cannon(Side.CHU)
        ));

        // when
        List<Position> moves = cannon.getPossibleMoves(start, pieces);

        // then
        assertThat(moves).isEmpty();
    }

    @Test
    @DisplayName("포는 일반 기물을 여러 개 뛰어넘을 수 없다.")
    void doesNotMultiJumpTest() {
        // given
        Cannon cannon = new Cannon(Side.CHU);
        Position start = new Position(7, 4);
        Pieces pieces = piecesFrom(Map.of(
                start, cannon,
                new Position(6, 4), new Pawn(Side.CHU),
                new Position(3, 4), new Pawn(Side.HAN),
                new Position(2, 4), new King(Side.HAN)
        ));

        // when
        List<Position> moves = cannon.getPossibleMoves(start, pieces);

        // then
        assertThat(moves).doesNotContain(
                new Position(2, 4),
                new Position(1, 4),
                new Position(0, 4)
        );
    }

    @Test
    @DisplayName("포는 궁성 대각선을 따라 기물을 뛰어넘어 이동할 수 있다.")
    void canJumpDiagonallyInPalace() {
        // given
        Cannon cannon = new Cannon(Side.HAN);
        Position start = new Position(0, 3); // 상단 궁성 좌상단 꼭짓점
        Pieces pieces = piecesFrom(Map.of(
                start, cannon,
                new Position(1, 4), new Guard(Side.CHU) // 중앙에 넘을 기물
        ), PALACE_TOPOLOGY);

        // when
        List<Position> moves = cannon.getPossibleMoves(start, pieces);

        // then
        assertThat(moves).contains(new Position(2, 5)); // 대각선으로 넘어서 착지
    }

    @Test
    @DisplayName("포는 궁성 대각선에서 넘을 기물이 없으면 이동할 수 없다.")
    void cannotMoveDiagonallyWithoutJumpPiece() {
        // given
        Cannon cannon = new Cannon(Side.HAN);
        Position start = new Position(0, 3); // 상단 궁성 좌상단 꼭짓점
        Pieces pieces = piecesFrom(Map.of(start, cannon), PALACE_TOPOLOGY);

        // when
        List<Position> moves = cannon.getPossibleMoves(start, pieces);

        // then
        assertThat(moves).doesNotContain(
                new Position(1, 4),
                new Position(2, 5)
        );
    }

    @Test
    @DisplayName("포는 궁성 대각선에서 포를 넘거나 잡을 수 없다.")
    void cannotJumpCannonOnPalaceDiagonal() {
        // given
        Cannon cannon = new Cannon(Side.HAN);
        Position start = new Position(0, 3);
        Pieces pieces = piecesFrom(Map.of(
                start, cannon,
                new Position(1, 4), new Cannon(Side.CHU) // 중앙에 포
        ), PALACE_TOPOLOGY);

        // when
        List<Position> moves = cannon.getPossibleMoves(start, pieces);

        // then
        assertThat(moves).doesNotContain(
                new Position(1, 4),
                new Position(2, 5)
        );
    }
}
