package domain.piece;

import domain.MoveInfos;
import domain.direction.Direction;
import domain.direction.Directions;
import domain.piece.category.Cannon;
import domain.piece.category.Chariot;
import domain.piece.category.Elephant;
import domain.piece.category.Guard;
import domain.piece.category.Horse;
import domain.piece.category.King;
import domain.piece.category.PieceCategory;
import domain.piece.category.Soldier;
import domain.spatial.Position;
import domain.spatial.Vector;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.assertAll;

class PieceTest {

    @Test
    void 기물_객체를_생성할_수_있다() {
        // given
        final Position position = new Position(1, 2);

        // when
        final TestPiece piece = new TestPiece(new Position(1, 2), new Directions(List.of(), false));

        // then
        assertThat(piece.getPosition()).isEqualTo(position);
    }

    @Test
    void 기물이_타겟_위치까지_도달하는_이동_경로를_반환한다() {
        // given
        final Position targetPosition = new Position(5, 5);
        List<Position> expected = List.of(new Position(4, 6));

        List<Vector> vectors = List.of(new Vector(0, -1), new Vector(1, -1));
        List<Direction> directionElements = List.of(new Direction(vectors, false));
        Directions directions = new Directions(directionElements, false);

        Piece piece = new TestPiece(new Position(4, 7), directions);

        // when
        List<Position> result = piece.getPaths(targetPosition);

        // then
        assertThat(result).containsAll(expected);
    }

    @Test
    void 이동_경로가_유효하지_않은_경우_예외가_발생한다() {
        // given
        Piece piece = new TestPiece(new Position(4, 1), new Directions(List.of(), false));

        Position target = new Position(6, 6);

        // when && then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> piece.getPaths(target))
                .withMessage("이동할 수 없는 좌표입니다. 다시 확인해주세요.");
    }

    @Test
    void 궁성_이동_경로가_궁성_외부인_경우_예외가_발생한다() {
        // given
        Piece piece = new TestPiece(new Position(5, 3), new Directions(List.of(), false));

        Position target = new Position(6, 4);

        // when && then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> piece.getPaths(target))
                .withMessage("궁성 이동의 경우 밖으로 이동할 수 없습니다.");
    }

    @Test
    void 위치가_같은지_판단한다() {
        // given
        final Position position = new Position(1, 2);
        final Piece piece = new TestPiece(new Position(1, 2), new Directions(List.of(), false));

        // when
        boolean result = piece.isSamePosition(position);

        // then
        assertThat(result).isTrue();
    }

    @Test
    void 위치를_변경한다() {
        // given
        final Position position = new Position(2, 2);
        final Piece piece = new TestPiece(new Position(1, 2), new Directions(List.of(), false));

        // when
        Piece result = piece.move(position, new MoveInfos(List.of()));

        // then
        assertThat(result.getPosition())
                .isEqualTo(position);
    }

    @Test
    void 왕인지_판단한다() {
        // given
        final Piece king = new King(new Position(1, 2), new Directions(List.of(), false));
        final Piece guard = new Guard(new Position(1, 2), new Directions(List.of(), false));
        final Piece cannon = new Cannon(new Position(1, 2), new Directions(List.of(), false));
        final Piece elephant = new Elephant(new Position(1, 2), new Directions(List.of(), false));
        final Piece horse = new Horse(new Position(1, 2), new Directions(List.of(), false));
        final Piece soldier = new Soldier(new Position(1, 2), new Directions(List.of(), false));
        final Piece chariot = new Chariot(new Position(1, 2), new Directions(List.of(), false));
        final Piece piece = new TestPiece(new Position(1, 2), new Directions(List.of(), false));

        // when & then
        assertAll(
                () -> assertThat(king.isKing()).isTrue(),
                () -> assertThat(guard.isKing()).isFalse(),
                () -> assertThat(cannon.isKing()).isFalse(),
                () -> assertThat(elephant.isKing()).isFalse(),
                () -> assertThat(horse.isKing()).isFalse(),
                () -> assertThat(soldier.isKing()).isFalse(),
                () -> assertThat(chariot.isKing()).isFalse(),
                () -> assertThat(piece.isKing()).isFalse()
        );
    }

    static class TestPiece extends Piece {

        public TestPiece(final Position position, final Directions directions) {
            super(position, directions);
        }

        @Override
        public PieceCategory getCategory() {
            return PieceCategory.NONE;
        }

        @Override
        public TestPiece move(final Position target, final MoveInfos moveInfos) {
            return new TestPiece(target, directions);
        }
    }
}
