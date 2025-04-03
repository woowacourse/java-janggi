package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import janggi.domain.ReplaceUnderBar;
import janggi.domain.piece.movement.MovementStrategy;
import janggi.domain.piece.movement.MovementStrategyContext;
import janggi.domain.piece.pieces.Pieces;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;

@ReplaceUnderBar
class PieceTest {

    private static final Side ALLY_SIDE = Side.HAN;
    private static final PieceType PIECE_TYPE = PieceType.ELEPHANT;
    private static final Position DEFAULT_POSITION = new Position(1, 2);
    private static final FakeMovementStrategyContext MOVEMENT_STRATEGY = new FakeMovementStrategyContext(true);

    @ParameterizedTest
    @EnumSource(value = Side.class)
    void 진영을_가진다(Side side) {
        Piece piece = new Piece(PIECE_TYPE, MOVEMENT_STRATEGY, side, DEFAULT_POSITION);
        assertThat(piece.getSide()).isEqualTo(side);
    }

    @Test
    void 위치를_가진다() {
        Piece piece = new Piece(PIECE_TYPE, MOVEMENT_STRATEGY, ALLY_SIDE, DEFAULT_POSITION);
        assertThat(piece.getPosition()).isEqualTo(new Position(1, 2));
    }

    @Test
    void 움직일_수_없는_위치로_움직일_수_없다() {
        Piece piece = new Piece(PIECE_TYPE, MOVEMENT_STRATEGY, ALLY_SIDE, DEFAULT_POSITION);
        MOVEMENT_STRATEGY.setIsMoveable(false);

        assertThatIllegalArgumentException()
            .isThrownBy(
                () -> piece.move(new Pieces(Map.of()), DEFAULT_POSITION.plus(1, 1)))
            .withMessage("해당 위치로 이동할 수 없습니다.");
    }

    @ParameterizedTest
    @CsvSource(value = {"1,2,1,2,true", "1,4,1,2,false"})
    void 같은_위치인지_판별한다(int x, int y, int compareX, int compareY, boolean expected) {
        Piece piece = new Piece(PIECE_TYPE, MOVEMENT_STRATEGY, ALLY_SIDE, new Position(x, y));

        assertThat(piece.isSamePosition(new Position(compareX, compareY))).isEqualTo(expected);
    }

    private static class FakeMovementStrategyContext extends MovementStrategyContext {

        private boolean isMoveable;

        public FakeMovementStrategyContext(boolean isMoveable) {
            super(null, null);
            this.isMoveable = isMoveable;
        }

        @Override
        public MovementStrategy getMovementStrategy(Position position) {
            return (map, position1, side, destination) -> isMoveable;
        }

        public void setIsMoveable(boolean moveable) {
            isMoveable = moveable;
        }
    }
}
