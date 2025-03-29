package game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertAll;
import static piece.Country.Cho;
import static piece.PieceType.ELEPHANT;
import static piece.PieceType.HORSE;
import static testutil.TestConstant.B1;
import static testutil.TestConstant.B9;
import static testutil.TestConstant.C1;
import static testutil.TestConstant.C9;
import static testutil.TestConstant.E1;
import static testutil.TestConstant.E5;
import static testutil.TestConstant.G1;
import static testutil.TestConstant.G9;
import static testutil.TestConstant.H1;
import static testutil.TestConstant.H9;

import java.util.HashMap;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import piece.Piece;
import piece.Rook;
import position.Position;

public class BoardTest {

    @Nested
    class 상차림에_따라_말들이_배치된다 {
        @Test
        void MA_SANG_MA_SANG_초나라_상차림이_정확하게_배치된다() {
            // given
            Board board = new Board(StartPosition.MA_SANG_MA_SANG, StartPosition.MA_SANG_MA_SANG);
            Map<Position, Piece> boardMap = board.getBoard();

            // when & then
            assertAll(
                    () -> assertThat(boardMap.get(B1).getPieceType()).isEqualTo(HORSE),
                    () -> assertThat(boardMap.get(C1).getPieceType()).isEqualTo(ELEPHANT),
                    () -> assertThat(boardMap.get(G1).getPieceType()).isEqualTo(HORSE),
                    () -> assertThat(boardMap.get(H1).getPieceType()).isEqualTo(ELEPHANT)
            );
        }

        @Test
        void MA_SANG_MA_SANG_한나라_상차림이_정확하게_배치된다() {
            // given
            Board board = new Board(StartPosition.MA_SANG_MA_SANG, StartPosition.MA_SANG_MA_SANG);
            Map<Position, Piece> boardMap = board.getBoard();

            // when & then
            assertAll(
                    () -> assertThat(boardMap.get(H9).getPieceType()).isEqualTo(HORSE),
                    () -> assertThat(boardMap.get(G9).getPieceType()).isEqualTo(ELEPHANT),
                    () -> assertThat(boardMap.get(C9).getPieceType()).isEqualTo(HORSE),
                    () -> assertThat(boardMap.get(B9).getPieceType()).isEqualTo(ELEPHANT)
            );
        }

        @Test
        void SANG_MA_SANG_MA_초나라_상차림이_정확하게_배치된다() {
            Board board = new Board(StartPosition.SANG_MA_SANG_MA, StartPosition.SANG_MA_SANG_MA);
            Map<Position, Piece> boardMap = board.getBoard();

            assertAll(
                    () -> assertThat(boardMap.get(B1).getPieceType()).isEqualTo(ELEPHANT),
                    () -> assertThat(boardMap.get(C1).getPieceType()).isEqualTo(HORSE),
                    () -> assertThat(boardMap.get(G1).getPieceType()).isEqualTo(ELEPHANT),
                    () -> assertThat(boardMap.get(H1).getPieceType()).isEqualTo(HORSE)
            );
        }

        @Test
        void SANG_MA_SANG_MA_한나라_상차림이_정확하게_배치된다() {
            Board board = new Board(StartPosition.SANG_MA_SANG_MA, StartPosition.SANG_MA_SANG_MA);
            Map<Position, Piece> boardMap = board.getBoard();

            assertAll(
                    () -> assertThat(boardMap.get(H9).getPieceType()).isEqualTo(ELEPHANT),
                    () -> assertThat(boardMap.get(G9).getPieceType()).isEqualTo(HORSE),
                    () -> assertThat(boardMap.get(C9).getPieceType()).isEqualTo(ELEPHANT),
                    () -> assertThat(boardMap.get(B9).getPieceType()).isEqualTo(HORSE)
            );
        }

        @Test
        void SANG_MA_MA_SANG_초나라_상차림이_정확하게_배치된다() {
            Board board = new Board(StartPosition.SANG_MA_MA_SANG, StartPosition.SANG_MA_MA_SANG);
            Map<Position, Piece> boardMap = board.getBoard();

            assertAll(
                    () -> assertThat(boardMap.get(B1).getPieceType()).isEqualTo(ELEPHANT),
                    () -> assertThat(boardMap.get(C1).getPieceType()).isEqualTo(HORSE),
                    () -> assertThat(boardMap.get(G1).getPieceType()).isEqualTo(HORSE),
                    () -> assertThat(boardMap.get(H1).getPieceType()).isEqualTo(ELEPHANT)
            );
        }

        @Test
        void SANG_MA_MA_SANG_한나라_상차림이_정확하게_배치된다() {
            Board board = new Board(StartPosition.SANG_MA_MA_SANG, StartPosition.SANG_MA_MA_SANG);
            Map<Position, Piece> boardMap = board.getBoard();

            assertAll(
                    () -> assertThat(boardMap.get(H9).getPieceType()).isEqualTo(ELEPHANT),
                    () -> assertThat(boardMap.get(G9).getPieceType()).isEqualTo(HORSE),
                    () -> assertThat(boardMap.get(C9).getPieceType()).isEqualTo(HORSE),
                    () -> assertThat(boardMap.get(B9).getPieceType()).isEqualTo(ELEPHANT)
            );
        }

        @Test
        void MA_SANG_SANG_MA_초나라_상차림이_정확하게_배치된다() {
            Board board = new Board(StartPosition.MA_SANG_SANG_MA, StartPosition.MA_SANG_SANG_MA);
            Map<Position, Piece> boardMap = board.getBoard();

            assertAll(
                    () -> assertThat(boardMap.get(B1).getPieceType()).isEqualTo(HORSE),
                    () -> assertThat(boardMap.get(C1).getPieceType()).isEqualTo(ELEPHANT),
                    () -> assertThat(boardMap.get(G1).getPieceType()).isEqualTo(ELEPHANT),
                    () -> assertThat(boardMap.get(H1).getPieceType()).isEqualTo(HORSE)
            );
        }

        @Test
        void MA_SANG_SANG_MA_한나라_상차림이_정확하게_배치된다() {
            Board board = new Board(StartPosition.MA_SANG_SANG_MA, StartPosition.MA_SANG_SANG_MA);
            Map<Position, Piece> boardMap = board.getBoard();

            assertAll(
                    () -> assertThat(boardMap.get(H9).getPieceType()).isEqualTo(HORSE),
                    () -> assertThat(boardMap.get(G9).getPieceType()).isEqualTo(ELEPHANT),
                    () -> assertThat(boardMap.get(C9).getPieceType()).isEqualTo(ELEPHANT),
                    () -> assertThat(boardMap.get(B9).getPieceType()).isEqualTo(HORSE)
            );
        }

        @Test
        void 보드의_기물을_움직인다() {
            // given
            Map<Position, Piece> boardMap = new HashMap<>();
            boardMap.put(E5, new Rook(Cho));
            Board board = new Board(boardMap);

            // then
            assertThatCode(() -> board.movePiece(E5, E1))
                    .doesNotThrowAnyException();
        }

        @Test
        void 보드의_기물을_움직일_때_기물이_없으면_예외가_발생한다() {
            // given
            Map<Position, Piece> boardMap = new HashMap<>();
            Board board = new Board(boardMap);

            // when

            // then
            Assertions.assertThatThrownBy(() -> board.movePiece(E5, E1))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("해당 위치에 기물이 없습니다.");
        }


    }

}
