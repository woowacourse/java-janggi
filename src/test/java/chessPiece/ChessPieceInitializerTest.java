package chessPiece;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChessPieceInitializerTest {

    @DisplayName("초나라 기물의 위치를 초기화 할 수 있다.")
    @Test
    void chaInit() {
        //given
        ChessPieceInitializer chessPieceInitializer = new ChessPieceInitializer();

        //when
        List<ChessPiece> actual = chessPieceInitializer.chaInit();

        //then
        assertThat(actual).hasSize(16);
    }

    @DisplayName("한나라 기물의 위치를 초기화 할 수 있다.")
    @Test
    void hanInit() {
        //given
        ChessPieceInitializer chessPieceInitializer = new ChessPieceInitializer();

        //when
        List<ChessPiece> actual = chessPieceInitializer.hanInit();

        //then
        assertThat(actual).hasSize(16);
    }
}
