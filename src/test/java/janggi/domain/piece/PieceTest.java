package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import janggi.domain.Position;
import janggi.domain.board.Board;
import janggi.domain.board.BoardChecker;
import janggi.exception.ExceptionMessage;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PieceTest {

    @Test
    void 같은_룰이_적용되는_기물인지_확인한다() {
        // given
        Piece piece = new Piece(PieceType.CANNON, Camp.CHO);
        // when
        boolean result = piece.isSamePieceRule(PieceType.CANNON);
        // then
        assertThat(result).isTrue();
    }

    @Test
    void 기물이_같은_진영인지_확인한다() {
        // given
        Piece piece = new Piece(PieceType.CANNON, Camp.CHO);
        // when
        boolean result = piece.isSameCamp(Camp.CHO);
        // then
        assertThat(result).isTrue();
    }

    @DisplayName("궁 행마법 테스트")
    @Nested
    class General {
        @Test
        void 궁은_이동_경로에_기물이_없으면_정상_이동한다() {
            // given
            Piece piece = new Piece(PieceType.GENERAL, Camp.CHO);
            BoardChecker board = new Board(Map::of);
            // when & then
            assertDoesNotThrow(() ->
                    piece.validateMove(new Position(1, 4), new Position(2, 4), board)
            );
        }

        @Test
        void 궁은_행마법을_따르지_않으면_예외가_발생한다() {
            // given
            Piece piece = new Piece(PieceType.GENERAL, Camp.CHO);
            BoardChecker board = new Board(Map::of);
            // when & then
            Assertions.assertThatThrownBy(
                            () -> piece.validateMove(new Position(1, 4), new Position(3, 4), board))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ExceptionMessage.INVALID_SINGLE_STEP_STRAIGHT_MOVE.getMessage());
        }
    }

    @DisplayName("사 행마법 테스트")
    @Nested
    class Guard {
        @Test
        void 사는_이동_경로에_기물이_없으면_정상_이동한다() {
            // given
            Piece piece = new Piece(PieceType.GUARD, Camp.CHO);
            BoardChecker board = new Board(Map::of);
            // when & then
            assertDoesNotThrow(() ->
                    piece.validateMove(new Position(0, 3), new Position(0, 4), board)
            );
        }

        @Test
        void 사는_행마법을_따르지_않으면_예외가_발생한다() {
            // given
            Piece piece = new Piece(PieceType.GUARD, Camp.CHO);
            BoardChecker board = new Board(Map::of);
            // when & then
            Assertions.assertThatThrownBy(
                            () -> piece.validateMove(new Position(0, 3), new Position(0, 5), board))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ExceptionMessage.INVALID_SINGLE_STEP_STRAIGHT_MOVE.getMessage());
        }
    }

    @DisplayName("마 행마법 테스트")
    @Nested
    class Horse {
        @Test
        void 마는_이동_경로에_기물이_없으면_정상_이동한다() {
            // given
            Piece piece = new Piece(PieceType.HORSE, Camp.CHO);
            BoardChecker board = new Board(Map::of);
            // when & then
            assertDoesNotThrow(() ->
                    piece.validateMove(new Position(0, 1), new Position(2, 2), board)
            );
        }

        @Test
        void 마는_이동_경로에_기물이_있으면_예외가_발생한다() {
            // given
            Piece piece = new Piece(PieceType.HORSE, Camp.CHO);
            BoardChecker board = new Board(() -> Map.of(
                    new Position(1, 1), new Piece(PieceType.CHARIOT, Camp.HAN)
            ));
            // when & then
            Assertions.assertThatThrownBy(
                            () -> piece.validateMove(new Position(0, 1), new Position(2, 2), board))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ExceptionMessage.PATH_NOT_EMPTY.getMessage());
        }

        @Test
        void 마는_행마법을_따르지_않으면_예외가_발생한다() {
            // given
            Piece piece = new Piece(PieceType.HORSE, Camp.CHO);
            BoardChecker board = new Board(Map::of);
            // when & then
            Assertions.assertThatThrownBy(
                            () -> piece.validateMove(new Position(0, 1), new Position(0, 7), board))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ExceptionMessage.INVALID_HORSE_MOVE.getMessage());
        }
    }

    @DisplayName("상 행마법 테스트")
    @Nested
    class Elephant {
        @Test
        void 상은_이동_경로에_기물이_없으면_정상_이동한다() {
            // given
            Piece piece = new Piece(PieceType.ELEPHANT, Camp.CHO);
            BoardChecker board = new Board(Map::of);
            // when & then
            assertDoesNotThrow(() ->
                    piece.validateMove(new Position(0, 6), new Position(3, 4), board)
            );
        }

        @Test
        void 상은_행마법을_따르지_않으면_예외가_발생한다() {
            //given
            Piece piece = new Piece(PieceType.ELEPHANT, Camp.CHO);
            BoardChecker board = new Board(Map::of);
            //when & then
            Assertions.assertThatThrownBy(
                            () -> piece.validateMove(new Position(0, 6), new Position(3, 3), board))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ExceptionMessage.INVALID_ELEPHANT_MOVE.getMessage());
        }
    }

    @DisplayName("포 행마법 테스트")
    @Nested
    class Cannon {
        @Test
        void 포는_이동_경로에_기물이_없으면_예외가_발생한다() {
            //given
            Piece piece = new Piece(PieceType.CANNON, Camp.CHO);
            BoardChecker board = new Board(Map::of);
            //when & then
            Assertions.assertThatThrownBy(
                            () -> piece.validateMove(new Position(2, 1), new Position(8, 1), board))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ExceptionMessage.INVALID_JUMPED_PIECE_COUNT.getMessage());
        }

        @Test
        void 포는_이동_경로에_기물이_2개_이상_있으면_예외가_발생한다() {
            //given
            Piece piece = new Piece(PieceType.CANNON, Camp.CHO);
            BoardChecker board = new Board(() -> Map.of(
                    new Position(3, 1), new Piece(PieceType.CHARIOT, Camp.HAN),
                    new Position(5, 1), new Piece(PieceType.CHARIOT, Camp.CHO)
            ));
            //when & then
            Assertions.assertThatThrownBy(
                            () -> piece.validateMove(new Position(2, 1), new Position(8, 1), board))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ExceptionMessage.INVALID_JUMPED_PIECE_COUNT.getMessage());
        }

        @Test
        void 포는_이동_경로에_기물이_1개만_있으면_정상_이동한다() {
            // given
            Piece piece = new Piece(PieceType.CANNON, Camp.CHO);
            BoardChecker board = new Board(() -> Map.of(
                    new Position(3, 1), new Piece(PieceType.CHARIOT, Camp.HAN)
            ));
            // when & then
            assertDoesNotThrow(() ->
                    piece.validateMove(new Position(2, 1), new Position(8, 1), board)
            );
        }
    }

    @DisplayName("차 행마법 테스트")
    @Nested
    class Chariot {
        @Test
        void 차는_이동_경로에_기물이_없으면_정상_이동한다() {
            // given
            Piece piece = new Piece(PieceType.CHARIOT, Camp.CHO);
            BoardChecker board = new Board(Map::of);
            // when & then
            assertDoesNotThrow(() ->
                    piece.validateMove(new Position(0, 0), new Position(9, 0), board)
            );
        }

        @Test
        void 차는_이동_경로에_기물이_1개_이상_있으면_예외가_발생한다() {
            //given
            Piece piece = new Piece(PieceType.CHARIOT, Camp.CHO);
            BoardChecker board = new Board(() -> Map.of(
                    new Position(1, 0), new Piece(PieceType.CHARIOT, Camp.HAN),
                    new Position(5, 1), new Piece(PieceType.CHARIOT, Camp.CHO)

            ));
            //when & then
            Assertions.assertThatThrownBy(
                            () -> piece.validateMove(new Position(0, 0), new Position(9, 0), board))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ExceptionMessage.PATH_NOT_EMPTY.getMessage());
        }

        @Test
        void 차는_행마법을_따르지_않으면_예외가_발생한다() {
            //given
            Piece piece = new Piece(PieceType.CHARIOT, Camp.CHO);
            BoardChecker board = new Board(Map::of);
            //when & then
            Assertions.assertThatThrownBy(
                            () -> piece.validateMove(new Position(0, 0), new Position(3, 3), board))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ExceptionMessage.ONLY_STRAIGHT_MOVE_ALLOWED.getMessage());
        }
    }

    @DisplayName("졸 행마법 테스트")
    @Nested
    class SoldierCho {
        @Test
        void 졸은_이동_경로에_기물이_없으면_정상_이동한다() {
            // given
            Piece piece = new Piece(PieceType.SOLDIER, Camp.CHO);
            BoardChecker board = new Board(Map::of);
            // when & then
            assertDoesNotThrow(() ->
                    piece.validateMove(new Position(3, 0), new Position(4, 0), board)
            );
        }

        @Test
        void 졸은_후진할_시_예외가_발생한다() {
            // given
            Piece piece = new Piece(PieceType.SOLDIER, Camp.CHO);
            BoardChecker board = new Board(Map::of);
            // when & then
            Assertions.assertThatThrownBy(
                            () -> piece.validateMove(new Position(3, 0), new Position(2, 0), board))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ExceptionMessage.INVALID_BACKWARD_MOVEMENT.getMessage());
        }

        @Test
        void 졸은_행마법을_따르지_않으면_예외가_발생한다() {
            // given
            Piece piece = new Piece(PieceType.SOLDIER, Camp.CHO);
            BoardChecker board = new Board(Map::of);
            // when & then
            Assertions.assertThatThrownBy(
                            () -> piece.validateMove(new Position(3, 0), new Position(5, 0), board))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ExceptionMessage.INVALID_SINGLE_STEP_STRAIGHT_MOVE.getMessage());
        }
    }

    @DisplayName("병 행마법 테스트")
    @Nested
    class SoldierHan {
        @Test
        void 병은_이동_경로에_기물이_없으면_정상_이동한다() {
            // given
            Piece piece = new Piece(PieceType.SOLDIER, Camp.HAN);
            BoardChecker board = new Board(Map::of);
            // when & then
            assertDoesNotThrow(() ->
                    piece.validateMove(new Position(6, 0), new Position(5, 0), board)
            );
        }

        @Test
        void 병은_후진할_시_예외가_발생한다() {
            // given
            Piece piece = new Piece(PieceType.SOLDIER, Camp.HAN);
            BoardChecker board = new Board(Map::of);
            // when & then
            Assertions.assertThatThrownBy(
                            () -> piece.validateMove(new Position(6, 0), new Position(7, 0), board))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ExceptionMessage.INVALID_BACKWARD_MOVEMENT.getMessage());
        }

        @Test
        void 병은_행마법을_따르지_않으면_예외가_발생한다() {
            // given
            Piece piece = new Piece(PieceType.SOLDIER, Camp.HAN);
            BoardChecker board = new Board(Map::of);
            // when & then
            Assertions.assertThatThrownBy(
                            () -> piece.validateMove(new Position(6, 0), new Position(1, 0), board))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ExceptionMessage.INVALID_SINGLE_STEP_STRAIGHT_MOVE.getMessage());
        }
    }
}
