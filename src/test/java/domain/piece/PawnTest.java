package domain.piece;

import domain.Side;
import domain.board.BoardBounds;
import domain.coordinate.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class PawnTest {

    private static final BoardBounds BOUNDS = BoardBounds.JANGGI;

    private Pieces piecesFrom(Map<Position, Piece> pieces) {
        return position -> pieces.getOrDefault(position, EmptyPiece.getInstance());
    }

    @Test
    @DisplayName("한나라 졸은 하/좌/우 3방향으로 1칸 이동할 수 있다.")
    void getHanPossibleMovesTest() {
        // given
        Pawn pawn = new Pawn(Side.HAN);
        Position start = new Position(3, 4);
        Pieces pieces = piecesFrom(Map.of(start, pawn));

        // when
        List<Position> moves = pawn.getPossibleMoves(start, BOUNDS, pieces);

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
        List<Position> moves = pawn.getPossibleMoves(start, BOUNDS, pieces);

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
        List<Position> moves = pawn.getPossibleMoves(start, BOUNDS, pieces);

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
        List<Position> moves = pawn.getPossibleMoves(start, BOUNDS, pieces);

        // then
        assertThat(moves).contains(new Position(4, 4));
    }
}