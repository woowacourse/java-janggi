package domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BoardTest {

    @Test
    @DisplayName("보드 객체를 생성한다.")
    void test1() {
        //given
        final ChessPiecePositions chessPiecePositions = ChessPiecePositions.empty();

        //when
        final Board board = new Board(chessPiecePositions);

        //then
        assertThat(board).isInstanceOf(Board.class);
    }

}
