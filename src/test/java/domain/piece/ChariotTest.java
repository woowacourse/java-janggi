package domain.piece;

import domain.Side;
import domain.coordinate.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ChariotTest {

private Pieces piecesFrom(Map<Position, Piece> pieces) {
        return position -> pieces.getOrDefault(position, EmptyPiece.getInstance());
    }

    @Test
    @DisplayName("차는 상/하/좌/우 4방향으로 n칸 이동할 수 있다.")
    void getPossibleMovesTest() {
        // given
        Chariot chariot = new Chariot(Side.HAN);
        Position start = new Position(2, 1);
        Pieces pieces = piecesFrom(Map.of(start, chariot));

        // when
        List<Position> moves = chariot.getPossibleMoves(start, pieces);

        // then
        assertThat(moves).containsOnly(
                new Position(2, 0),
                new Position(2, 2), new Position(2, 3), new Position(2, 4),
                new Position(2, 5), new Position(2, 6), new Position(2, 7), new Position(2, 8),
                new Position(1, 1), new Position(0, 1),
                new Position(3, 1), new Position(4, 1), new Position(5, 1),
                new Position(6, 1), new Position(7, 1), new Position(8, 1), new Position(9, 1)
        );
    }

    @Test
    @DisplayName("차는 아군 기물에 막히면 더 이상 이동할 수 없다.")
    void blockedByFriendlyTest() {
        // given
        Chariot chariot = new Chariot(Side.HAN);
        Position start = new Position(4, 4);
        Pieces pieces = piecesFrom(Map.of(
                start, chariot,
                new Position(3, 4), new Guard(Side.HAN),
                new Position(5, 4), new Guard(Side.HAN),
                new Position(4, 3), new Guard(Side.HAN),
                new Position(4, 5), new Guard(Side.HAN)
        ));

        // when
        List<Position> moves = chariot.getPossibleMoves(start, pieces);

        // then
        assertThat(moves).isEmpty();
    }

    @Test
    @DisplayName("차는 적 기물을 잡으면 멈춰야 한다.")
    void captureAndStopTest() {
        // given
        Chariot chariot = new Chariot(Side.HAN);
        Position start = new Position(4, 8);
        Pieces pieces = piecesFrom(Map.of(
                start, chariot,
                new Position(6, 8), new Guard(Side.CHU),
                new Position(8, 8), new Guard(Side.CHU)
        ));

        // when
        List<Position> moves = chariot.getPossibleMoves(start, pieces);

        // then
        assertThat(moves).contains(new Position(5, 8), new Position(6, 8));
        assertThat(moves).doesNotContain(new Position(7, 8));
    }
}