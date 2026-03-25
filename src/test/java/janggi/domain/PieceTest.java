package janggi.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;

import janggi.domain.piece.Camp;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Type;
import janggi.domain.piece.strategy.SoldierStrategy;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class PieceTest {

    @DisplayName("졸/병 테스트")
    @Nested
    class Soldier {
        public static Stream<Arguments> successMovePositions() {
            return Stream.of(
                    Arguments.of(new Position(6, 0), new Position(5, 0)),
                    Arguments.of(new Position(5, 1), new Position(4, 1)),
                    Arguments.of(new Position(6, 0), new Position(6, 1)),
                    Arguments.of(new Position(6, 1), new Position(6, 0))
            );
        }

        @ParameterizedTest
        @MethodSource("successMovePositions")
        void 병의_1칸_이동_여부를_확인한다(Position from, Position to) {
            //given
            Piece piece = new Piece(Type.SOLDIER, Camp.HAN, new SoldierStrategy());
            //when
            boolean result = piece.canMove(from, to);
            //then
            assertTrue(result);
        }

        public static Stream<Arguments> exceptionMovePositions() {
            return Stream.of(
                    Arguments.of(new Position(6, 0), new Position(5, 1)),
                    Arguments.of(new Position(6, 0), new Position(6, 2)),
                    Arguments.of(new Position(3, 2), new Position(2, 3))
            );
        }

        @ParameterizedTest
        @MethodSource("exceptionMovePositions")
        void 병은_1칸_이동이_아니면_예외가_발생한다(Position from, Position to) {
            Piece piece = new Piece(Type.SOLDIER, Camp.HAN, new SoldierStrategy());

            assertThatThrownBy(() -> piece.canMove(from, to))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 해당 기물이 이동할 수 없는 위치입니다.");
        }

        @Test
        void 병은_후진_시_예외가_발생한다() {
            Piece piece = new Piece(Type.SOLDIER, Camp.HAN, new SoldierStrategy());

            assertThatThrownBy(() -> piece.canMove(new Position(6, 0), new Position(7, 0)))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 해당 기물이 이동할 수 없는 위치입니다.");
        }

        @Test
        void 졸의_1칸_이동_여부를_확인한다() {
            //given
            Piece piece = new Piece(Type.SOLDIER, Camp.CHO, new SoldierStrategy());
            //when
            boolean result = piece.canMove(new Position(3, 0), new Position(4, 0));
            //then
            assertTrue(result);
        }

        @Test
        void 졸은_1칸_이동이_아니면_예외가_발생한다() {
            Piece piece = new Piece(Type.SOLDIER, Camp.CHO, new SoldierStrategy());

            assertThatThrownBy(() -> piece.canMove(new Position(3, 0), new Position(5, 0)))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 해당 기물이 이동할 수 없는 위치입니다.");
        }

        @Test
        void 졸은_후진_시_예외가_발생한다() {
            Piece piece = new Piece(Type.SOLDIER, Camp.CHO, new SoldierStrategy());

            assertThatThrownBy(() -> piece.canMove(new Position(3, 0), new Position(2, 0)))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 해당 기물이 이동할 수 없는 위치입니다.");
        }
    }
}
