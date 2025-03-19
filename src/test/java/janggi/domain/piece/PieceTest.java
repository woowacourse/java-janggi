package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.ReplaceUnderBar;
import janggi.domain.Side;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ReplaceUnderBar
class PieceTest {

    private static final Side ALLY_SIDE = Side.HAN;
    private static final Position DEFAULT_POSITION = new Position(1, 2);

    @ParameterizedTest
    @EnumSource(value = Side.class)
    void 진영을_가진다(Side side) {
        Piece piece = new FakePiece(side, DEFAULT_POSITION);
        assertThat(piece.getSide()).isEqualTo(side);
    }

    @Test
    void 위치를_가진다() {
        Piece piece = new FakePiece(ALLY_SIDE, new Position(1, 2));
        assertThat(piece.getPosition()).isEqualTo(new Position(1, 2));
    }

    @Test
    void 현재_위치로_움직일_수_없다() {
        FakePiece piece = new FakePiece(ALLY_SIDE, DEFAULT_POSITION);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> piece.move(List.of(), DEFAULT_POSITION.getX(), DEFAULT_POSITION.getY()))
                .withMessage("현재 위치로 이동할 수 없습니다.");
    }

    @Test
    void 움직일_수_없는_위치로_움직일_수_없다() {
        FakePiece piece = new FakePiece(ALLY_SIDE, DEFAULT_POSITION);
        piece.setIsMoveablePosition(false);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> piece.move(List.of(), DEFAULT_POSITION.getX() + 1, DEFAULT_POSITION.getY() + 1))
                .withMessage("해당 위치로 이동할 수 없습니다.");
    }

    @Test
    void 움직일_수_없는_경로로_움직일_수_없다() {
        FakePiece piece = new FakePiece(ALLY_SIDE, DEFAULT_POSITION);
        piece.setIsMoveablePath(false);

        assertThat(piece.isMoveablePath).isFalse();
    }

    @Test
    void 움직일_수_있는_위치_경로일_경우_움직일_수_있다() {
        FakePiece piece = new FakePiece(ALLY_SIDE, DEFAULT_POSITION);

        piece.move(List.of(), 3, 4);

        assertThat(piece.getPosition()).isEqualTo(new Position(3, 4));
    }

    @ParameterizedTest
    @CsvSource(value = {"1,2,1,2,true", "1,4,1,2,false"})
    void 같은_위치인지_판별한다(int x, int y, int compareX, int compareY, boolean expected) {
        Piece piece = new FakePiece(Side.HAN, new Position(x, y));

        assertThat(piece.isSamePosition(new Position(compareX, compareY))).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {"0,10", "9,0", "-1,0", "0,-1"})
    void 포지션을_벗어난_위치로_이동할_수_없다(int x, int y) {
        FakePiece piece = new FakePiece(Side.CHO, DEFAULT_POSITION);

        assertThatIllegalArgumentException()
            .isThrownBy(() -> piece.move(List.of(), x, y))
            .withMessage("이동할 수 없는 좌표입니다.");
    }

    private static class FakePiece extends Piece {

        private boolean isMoveablePosition;
        private boolean isMoveablePath;

        private FakePiece(Side side, Position position) {
            super(side, position);
            this.isMoveablePosition = true;
            this.isMoveablePath = true;
        }

        public void setIsMoveablePosition(boolean isMoveablePosition) {
            this.isMoveablePosition = isMoveablePosition;
        }

        public void setIsMoveablePath(boolean isMoveablePath) {
            this.isMoveablePath = isMoveablePath;
        }

        @Override
        protected boolean isMoveablePosition(Position destination) {
            return this.isMoveablePosition;
        }

        @Override
        protected boolean isMoveablePath(List<Piece> existingPieces, Position destination) {
            return this.isMoveablePath;
        }
    }
}
