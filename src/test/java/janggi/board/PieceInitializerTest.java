package janggi.board;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.piece.Piece;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PieceInitializerTest {

    @DisplayName("초나라 기물의 위치를 초기화 할 수 있다.")
    @Test
    void choInit() {
        //given
        PieceInitializer pieceInitializer = new PieceInitializer();

        //when
        List<Piece> actual = pieceInitializer.choInit();

        //then
        assertThat(actual).hasSize(16);
    }

    @DisplayName("한나라 기물의 위치를 초기화 할 수 있다.")
    @Test
    void hanInit() {
        //given
        PieceInitializer pieceInitializer = new PieceInitializer();

        //when
        List<Piece> actual = pieceInitializer.hanInit();

        //then
        assertThat(actual).hasSize(16);
    }
}
