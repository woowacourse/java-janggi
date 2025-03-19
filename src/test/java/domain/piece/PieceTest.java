package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.direction.Direction;
import domain.direction.Directions;
import java.util.List;
import org.junit.jupiter.api.Test;

class PieceTest {

    @Test
    void 기물_객체를_생성할_수_있다() {
        // given
        final Position position = Position.of(1, 2);

        // when
        final TestPiece piece = new TestPiece(1, 2, new Directions(List.of()));

        // then
        assertThat(piece.getPosition()).isEqualTo(position);
    }

    @Test
    void 기물이_타겟_위치까지_도달하는_이동_경로를_반환한다() {
        // given
        final Position targetPosition = Position.of(5, 5);

        List<Position> expected = List.of(Position.of(4, 6));

        List<Position> positions = List.of(Position.ofDirection(0, -1), Position.ofDirection(1, -1));
        List<Direction> directionElements = List.of(new Direction(positions, false));
        Directions directions = new Directions(directionElements);

        Piece piece = new TestPiece(4, 7, directions);

        // when
        List<Position> result = piece.getPath(targetPosition);

        // then
        assertThat(result).containsAll(expected);
    }

    @Test
    void 위치가_같은지_판단한다() {
        // given
        final Position position = Position.of(1, 2);
        final Piece piece = new TestPiece(1, 2, new Directions(List.of()));

        // when
        boolean result = piece.isSamePosition(position);

        // then
        assertThat(result).isTrue();
    }

    static class TestPiece extends Piece {
        public TestPiece(int row, int column, Directions directions) {
            super(row, column, directions);
        }

        @Override
        public String getName() {
            return "";
        }
    }
}
