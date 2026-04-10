package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import janggi.domain.board.Board;
import janggi.domain.board.BoardChecker;
import janggi.domain.board.Position;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PieceTest {

    @Test
    void 같은_룰이_적용되는_기물인지_확인한다() {
        // given
        Piece piece = new Piece(Camp.CHO, PieceType.CANNON);
        // when
        boolean result = piece.isSamePieceType(PieceType.CANNON);
        // then
        assertThat(result).isTrue();
    }

    @Test
    void 기물이_같은_진영인지_확인한다() {
        // given
        Piece piece = new Piece(Camp.CHO, PieceType.CANNON);
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
            Piece piece = new Piece(Camp.CHO, PieceType.GENERAL);
            BoardChecker board = new Board(Map::of);
            // when & then
            assertDoesNotThrow(() ->
                    piece.validateMove(new Position(1, 4), new Position(2, 4), board)
            );
        }

        @Test
        void 궁은_행마법을_따르지_않으면_예외가_발생한다() {
            // given
            Piece piece = new Piece(Camp.CHO, PieceType.GENERAL);
            BoardChecker board = new Board(Map::of);
            // when & then
            Assertions.assertThatThrownBy(
                            () -> piece.validateMove(new Position(0, 4), new Position(2, 4), board))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 해당 기물은 궁성 내에서 연결된 1칸만 이동할 수 있습니다.");
        }
    }

    @DisplayName("사 행마법 테스트")
    @Nested
    class Guard {
        @Test
        void 사는_이동_경로에_기물이_없으면_정상_이동한다() {
            // given
            Piece piece = new Piece(Camp.CHO, PieceType.GUARD);
            BoardChecker board = new Board(Map::of);
            // when & then
            assertDoesNotThrow(() ->
                    piece.validateMove(new Position(0, 3), new Position(0, 4), board)
            );
        }

        @Test
        void 사는_행마법을_따르지_않으면_예외가_발생한다() {
            // given
            Piece piece = new Piece(Camp.CHO, PieceType.GUARD);
            BoardChecker board = new Board(Map::of);
            // when & then
            Assertions.assertThatThrownBy(
                            () -> piece.validateMove(new Position(0, 3), new Position(0, 5), board))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 해당 기물은 궁성 내에서 연결된 1칸만 이동할 수 있습니다.");
        }
    }

    @DisplayName("마 행마법 테스트")
    @Nested
    class Horse {
        @Test
        void 마는_이동_경로에_기물이_없으면_정상_이동한다() {
            // given
            Piece piece = new Piece(Camp.CHO, PieceType.HORSE);
            BoardChecker board = new Board(Map::of);
            // when & then
            assertDoesNotThrow(() ->
                    piece.validateMove(new Position(0, 1), new Position(2, 2), board)
            );
        }

        @Test
        void 마는_이동_경로에_기물이_있으면_예외가_발생한다() {
            // given
            Piece piece = new Piece(Camp.CHO, PieceType.HORSE);
            BoardChecker board = new Board(() -> Map.of(
                    new Position(1, 1), new Piece(Camp.HAN, PieceType.CHARIOT)
            ));
            // when & then
            Assertions.assertThatThrownBy(
                            () -> piece.validateMove(new Position(0, 1), new Position(2, 2), board))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 경로 상에 기물이 존재합니다.");
        }

        @Test
        void 마는_행마법을_따르지_않으면_예외가_발생한다() {
            // given
            Piece piece = new Piece(Camp.CHO, PieceType.HORSE);
            BoardChecker board = new Board(Map::of);
            // when & then
            Assertions.assertThatThrownBy(
                            () -> piece.validateMove(new Position(0, 1), new Position(0, 7), board))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 해당 기물은 직선 1칸 이동 후 대각선 1칸 이동만 가능합니다.");
        }
    }

    @DisplayName("상 행마법 테스트")
    @Nested
    class Elephant {
        @Test
        void 상은_이동_경로에_기물이_없으면_정상_이동한다() {
            // given
            Piece piece = new Piece(Camp.CHO, PieceType.ELEPHANT);
            BoardChecker board = new Board(Map::of);
            // when & then
            assertDoesNotThrow(() ->
                    piece.validateMove(new Position(0, 6), new Position(3, 4), board)
            );
        }

        @Test
        void 상은_행마법을_따르지_않으면_예외가_발생한다() {
            //given
            Piece piece = new Piece(Camp.CHO, PieceType.ELEPHANT);
            BoardChecker board = new Board(Map::of);
            //when & then
            Assertions.assertThatThrownBy(
                            () -> piece.validateMove(new Position(0, 6), new Position(3, 3), board))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 해당 기물은 직선 1칸 이동 후 대각선 2칸 이동만 가능합니다.");
        }
    }

    @DisplayName("포 행마법 테스트")
    @Nested
    class Cannon {
        @Test
        void 포는_이동_경로에_기물이_없으면_예외가_발생한다() {
            //given
            Piece piece = new Piece(Camp.CHO, PieceType.CANNON);
            BoardChecker board = new Board(Map::of);
            //when & then
            Assertions.assertThatThrownBy(
                            () -> piece.validateMove(new Position(2, 1), new Position(8, 1), board))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 해당 기물은 정확히 1개의 기물만 뛰어넘을 수 있습니다.");
        }

        @Test
        void 포는_이동_경로에_기물이_2개_이상_있으면_예외가_발생한다() {
            //given
            Piece piece = new Piece(Camp.CHO, PieceType.CANNON);
            BoardChecker board = new Board(() -> Map.of(
                    new Position(3, 1), new Piece(Camp.HAN, PieceType.CHARIOT),
                    new Position(5, 1), new Piece(Camp.CHO, PieceType.CHARIOT)
            ));
            //when & then
            Assertions.assertThatThrownBy(
                            () -> piece.validateMove(new Position(2, 1), new Position(8, 1), board))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 해당 기물은 정확히 1개의 기물만 뛰어넘을 수 있습니다.");
        }

        @Test
        void 포는_이동_경로에_기물이_1개만_있으면_정상_이동한다() {
            // given
            Piece piece = new Piece(Camp.CHO, PieceType.CANNON);
            BoardChecker board = new Board(() -> Map.of(
                    new Position(3, 1), new Piece(Camp.HAN, PieceType.CHARIOT)
            ));
            // when & then
            assertDoesNotThrow(() ->
                    piece.validateMove(new Position(2, 1), new Position(8, 1), board)
            );
        }

        @Test
        void 포는_궁성_내에서_대각선으로_이동할_수_있다() {
            // given
            Piece piece = new Piece(Camp.CHO, PieceType.CANNON);
            BoardChecker board = new Board(() -> Map.of(
                    new Position(1, 4), new Piece(Camp.HAN, PieceType.CHARIOT)
            ));
            // when & then
            assertDoesNotThrow(() ->
                    piece.validateMove(new Position(0, 3), new Position(2, 5), board)
            );
        }
    }

    @DisplayName("차 행마법 테스트")
    @Nested
    class Chariot {
        @Test
        void 차는_이동_경로에_기물이_없으면_정상_이동한다() {
            // given
            Piece piece = new Piece(Camp.CHO, PieceType.CHARIOT);
            BoardChecker board = new Board(Map::of);
            // when & then
            assertDoesNotThrow(() ->
                    piece.validateMove(new Position(0, 0), new Position(9, 0), board)
            );
        }

        @Test
        void 차는_이동_경로에_기물이_1개_이상_있으면_예외가_발생한다() {
            //given
            Piece piece = new Piece(Camp.CHO, PieceType.CHARIOT);
            BoardChecker board = new Board(() -> Map.of(
                    new Position(1, 0), new Piece(Camp.HAN, PieceType.CHARIOT),
                    new Position(5, 1), new Piece(Camp.CHO, PieceType.CHARIOT)

            ));
            //when & then
            Assertions.assertThatThrownBy(
                            () -> piece.validateMove(new Position(0, 0), new Position(9, 0), board))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 경로 상에 기물이 존재합니다.");
        }

        @Test
        void 차는_행마법을_따르지_않으면_예외가_발생한다() {
            //given
            Piece piece = new Piece(Camp.CHO, PieceType.CHARIOT);
            BoardChecker board = new Board(Map::of);
            //when & then
            Assertions.assertThatThrownBy(
                            () -> piece.validateMove(new Position(0, 0), new Position(3, 3), board))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 해당 기물은 직선 이동만 가능합니다.");
        }

        @Test
        void 차는_궁성_내에서_대각선으로_이동할_수_있다() {
            // given
            Piece piece = new Piece(Camp.CHO, PieceType.CHARIOT);
            BoardChecker board = new Board(Map::of);
            // when & then
            assertDoesNotThrow(() ->
                    piece.validateMove(new Position(0, 3), new Position(2, 5), board)
            );
        }
    }

    @DisplayName("졸 행마법 테스트")
    @Nested
    class SoldierCho {
        @Test
        void 졸은_이동_경로에_기물이_없으면_정상_이동한다() {
            // given
            Piece piece = new Piece(Camp.CHO, PieceType.SOLDIER);
            BoardChecker board = new Board(Map::of);
            // when & then
            assertDoesNotThrow(() ->
                    piece.validateMove(new Position(3, 0), new Position(4, 0), board)
            );
        }

        @Test
        void 졸은_후진할_시_예외가_발생한다() {
            // given
            Piece piece = new Piece(Camp.CHO, PieceType.SOLDIER);
            BoardChecker board = new Board(Map::of);
            // when & then
            Assertions.assertThatThrownBy(
                            () -> piece.validateMove(new Position(3, 0), new Position(2, 0), board))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 해당 기물은 후진할 수 없습니다.");
        }

        @Test
        void 졸은_행마법을_따르지_않으면_예외가_발생한다() {
            // given
            Piece piece = new Piece(Camp.CHO, PieceType.SOLDIER);
            BoardChecker board = new Board(Map::of);
            // when & then
            Assertions.assertThatThrownBy(
                            () -> piece.validateMove(new Position(3, 0), new Position(5, 0), board))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 해당 기물은 직선으로 1칸 이동해야 합니다.");
        }
    }

    @DisplayName("병 행마법 테스트")
    @Nested
    class SoldierHan {
        @Test
        void 병은_이동_경로에_기물이_없으면_정상_이동한다() {
            // given
            Piece piece = new Piece(Camp.HAN, PieceType.SOLDIER);
            BoardChecker board = new Board(Map::of);
            // when & then
            assertDoesNotThrow(() ->
                    piece.validateMove(new Position(6, 0), new Position(5, 0), board)
            );
        }

        @Test
        void 병은_후진할_시_예외가_발생한다() {
            // given
            Piece piece = new Piece(Camp.HAN, PieceType.SOLDIER);
            BoardChecker board = new Board(Map::of);
            // when & then
            Assertions.assertThatThrownBy(
                            () -> piece.validateMove(new Position(6, 0), new Position(7, 0), board))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 해당 기물은 후진할 수 없습니다.");
        }

        @Test
        void 병은_행마법을_따르지_않으면_예외가_발생한다() {
            // given
            Piece piece = new Piece(Camp.HAN, PieceType.SOLDIER);
            BoardChecker board = new Board(Map::of);
            // when & then
            Assertions.assertThatThrownBy(
                            () -> piece.validateMove(new Position(6, 0), new Position(1, 0), board))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 해당 기물은 직선으로 1칸 이동해야 합니다.");
        }
    }
}
