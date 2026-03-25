package domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import domain.piece.King;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class BoardTest {
    private final BoardInitializer boardInitializer = new BoardInitializer();

    @Test
    @DisplayName("장기판을 생성한다.")
    void BoardTest() {
        // given - when - then
        assertDoesNotThrow(() -> new Board(boardInitializer));
    }

    @Test
    @DisplayName("정해진 위치에 있는지 확인한다.")
    void isPlaceAt_HanKing_Test() {
        // given
        Board board = new Board(boardInitializer);

        // when
        Position hanKingPosition = new Position(1, 4);
        King king = new King(Side.HAN);

        // then
        assertThat(board.isPieceAt(hanKingPosition, king)).isTrue();
    }

    @Test
    @DisplayName("기물이 정해진 위치에 있는지 확인한다.")
    void isPlaceAt_ChuKing_Test() {
        // given
        Board board = new Board(boardInitializer);

        // when
        Position chuKingPosition = new Position(8, 4);
        King king = new King(Side.CHU);

        // then
        assertThat(board.isPieceAt(chuKingPosition, king)).isTrue();
    }

}
