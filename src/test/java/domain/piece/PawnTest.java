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

class PawnTest {

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
    @DisplayName("한나라 졸은 하/좌/우 3방향으로 1칸 이동할 수 있다.")
    void getHanPossibleMovesTest() {
        // given
        Pawn pawn = new Pawn(Side.HAN);
        Position start = new Position(3, 4);
        Pieces pieces = piecesFrom(Map.of(start, pawn));

        // when
        List<Position> moves = pawn.getPossibleMoves(start, pieces);

        // then
        assertThat(moves).containsOnly(
                new Position(4, 4),
                new Position(3, 3),
                new Position(3, 5)
        );
    }

    @Test
    @DisplayName("초나라 졸은 상/좌/우 3방향으로 1칸 이동할 수 있다.")
    void getChuPossibleMovesTest() {
        // given
        Pawn pawn = new Pawn(Side.CHU);
        Position start = new Position(6, 4);
        Pieces pieces = piecesFrom(Map.of(start, pawn));

        // when
        List<Position> moves = pawn.getPossibleMoves(start, pieces);

        // then
        assertThat(moves).containsOnly(
                new Position(5, 4),
                new Position(6, 3),
                new Position(6, 5)
        );
    }

    @Test
    @DisplayName("졸은 아군 기물이 있는 위치로 이동할 수 없다.")
    void blockedByFriendlyTest() {
        // given
        Pawn pawn = new Pawn(Side.HAN);
        Position start = new Position(3, 4);
        Pieces pieces = piecesFrom(Map.of(
                start, pawn,
                new Position(4, 4), new Pawn(Side.HAN),
                new Position(3, 3), new Pawn(Side.HAN),
                new Position(3, 5), new Pawn(Side.HAN)
        ));

        // when
        List<Position> moves = pawn.getPossibleMoves(start, pieces);

        // then
        assertThat(moves).isEmpty();
    }

    @Test
    @DisplayName("졸은 상대 기물이 있는 위치로 이동할 수 있다.")
    void captureTest() {
        // given
        Pawn pawn = new Pawn(Side.HAN);
        Position start = new Position(3, 4);
        Pieces pieces = piecesFrom(Map.of(
                start, pawn,
                new Position(4, 4), new Pawn(Side.CHU)
        ));

        // when
        List<Position> moves = pawn.getPossibleMoves(start, pieces);

        // then
        assertThat(moves).contains(new Position(4, 4));
    }

    @Test
    @DisplayName("한나라 졸은 궁성 꼭짓점에서 전진 방향 대각선으로 이동할 수 있다.")
    void hanPawnPalaceCornerDiagonalTest() {
        // given
        Pawn pawn = new Pawn(Side.HAN);
        Position start = new Position(7, 3); // 하단 궁성 좌상단 꼭짓점
        Pieces pieces = piecesFrom(Map.of(start, pawn), PALACE_TOPOLOGY);

        // when
        List<Position> moves = pawn.getPossibleMoves(start, pieces);

        // then
        assertThat(moves).contains(new Position(8, 4)); // 전진 방향 대각선
    }

    @Test
    @DisplayName("초나라 졸은 궁성 꼭짓점에서 전진 방향 대각선으로 이동할 수 있다.")
    void chuPawnPalaceCornerDiagonalTest() {
        // given
        Pawn pawn = new Pawn(Side.CHU);
        Position start = new Position(2, 5); // 상단 궁성 우하단 꼭짓점
        Pieces pieces = piecesFrom(Map.of(start, pawn), PALACE_TOPOLOGY);

        // when
        List<Position> moves = pawn.getPossibleMoves(start, pieces);

        // then
        assertThat(moves).contains(new Position(1, 4)); // 전진 방향 대각선
    }

    @Test
    @DisplayName("한나라 졸은 궁성 중앙에서 전진 방향 대각선만 이동 가능하고 후진 대각선은 불가하다.")
    void hanPawnPalaceCenterForwardOnlyTest() {
        // given
        Pawn pawn = new Pawn(Side.HAN);
        Position start = new Position(8, 4); // 하단 궁성 중앙
        Pieces pieces = piecesFrom(Map.of(start, pawn), PALACE_TOPOLOGY);

        // when
        List<Position> moves = pawn.getPossibleMoves(start, pieces);

        // then
        assertThat(moves).contains(
                new Position(9, 3),  // 전진 방향 대각선
                new Position(9, 5)   // 전진 방향 대각선
        );
        assertThat(moves).doesNotContain(
                new Position(7, 3),  // 후진 방향 대각선
                new Position(7, 5)   // 후진 방향 대각선
        );
    }

    @Test
    @DisplayName("초나라 졸은 궁성 중앙에서 전진 방향 대각선만 이동 가능하고 후진 대각선은 불가하다.")
    void chuPawnPalaceCenterForwardOnlyTest() {
        // given
        Pawn pawn = new Pawn(Side.CHU);
        Position start = new Position(1, 4); // 상단 궁성 중앙
        Pieces pieces = piecesFrom(Map.of(start, pawn), PALACE_TOPOLOGY);

        // when
        List<Position> moves = pawn.getPossibleMoves(start, pieces);

        // then
        assertThat(moves).contains(
                new Position(0, 3),  // 전진 방향 대각선
                new Position(0, 5)   // 전진 방향 대각선
        );
        assertThat(moves).doesNotContain(
                new Position(2, 3),  // 후진 방향 대각선
                new Position(2, 5)   // 후진 방향 대각선
        );
    }
}