package janggi.domain.board;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import janggi.domain.Location;
import janggi.domain.piece.Piece;
import janggi.strategy.BoardAssembler;
import janggi.strategy.MaSangMaSang;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class BoardTest {

    @Test
    @DisplayName("보드가 전략에 맞춰 정상적으로 생성된다.")
    void shouldReturnBoardWithSelectedArrangementStrategy() {
        BoardAssembler assembler = BoardAssembler.of(new MaSangMaSang(), new MaSangMaSang());
        Board board = Board.create(assembler);
        List<List<Piece>> pieces = board.to2DArray();

        Piece[][] assemble = assembler.assemble();

        for (int row = 0; row < assemble.length; row++) {
            for (int col = 0; col < assemble[row].length; col++) {
                Piece result = assemble[row][col];
                Piece expectedPiece = pieces.get(row).get(col);
                Assertions.assertThat(result).isInstanceOf(expectedPiece.getClass());
                Assertions.assertThat(result.isSameSide(expectedPiece)).isTrue();
            }
        }
    }

    @Nested
    class ValidateLocationTest {
        @Test
        @DisplayName("보드에 입력받은 좌표가 존재하면 예외를 발생시키지 않는다.")
        void shouldNotThrowExceptionWhenLocationExists() {
            // given
            BoardAssembler assembler = BoardAssembler.of(new MaSangMaSang(), new MaSangMaSang());
            Board board = Board.create(assembler);
            Location location = Location.from(List.of(1,1));

            // when & then
            assertDoesNotThrow(() -> board.validateLocation(location));
        }

        @Test
        @DisplayName("보드에 입력받은 좌표가 존재하지 않으면 예외를 발생시킨다.")
        void shouldThrowExceptionWhenLocationDoesNotExist() {
            // given
            BoardAssembler assembler = BoardAssembler.of(new MaSangMaSang(), new MaSangMaSang());
            Board board = Board.create(assembler);
            Location location = Location.from(List.of(11,11));

            // when & then
            Assertions.assertThatThrownBy(() -> board.validateLocation(location))
                            .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    class ValidatePieceExistTest {
        @Test
        @DisplayName("입력받은 좌표에 기물이 존재하면 예외를 발생시키지 않는다.")
        void shouldNotThrowExceptionWhenPieceExistsAtLocation() {
            // given
            BoardAssembler assembler = BoardAssembler.of(new MaSangMaSang(), new MaSangMaSang());
            Board board = Board.create(assembler);
            Location location = Location.from(List.of(0,1));

            // when & then
            assertDoesNotThrow(() -> board.validatePieceExist(location));
        }

        @Test
        @DisplayName("입력받은 좌표에 기물이 존재하지 않으면 예외를 발생시킨다.")
        void shouldThrowExceptionWhenPieceDoesNotExistsAtLocation() {
            // given
            BoardAssembler assembler = BoardAssembler.of(new MaSangMaSang(), new MaSangMaSang());
            Board board = Board.create(assembler);
            Location location = Location.from(List.of(5,5));

            // when & then
            Assertions.assertThatThrownBy(() -> board.validatePieceExist(location))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}
