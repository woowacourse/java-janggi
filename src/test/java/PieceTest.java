import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PieceTest {

    private Board board = new Board();

/*
    @Test
    @DisplayName("기물 종류에는 6가지가 있다")
    void newPieceTest() {
        assertThatCode(() -> {
            Piece p1 = new Palace(0, 0);
            Piece p2 = new Chariot(0, 0);
            Piece p3 = new Pao(0, 0);
            Piece p4 = new Horse(0, 0);
            Piece p5 = new Elephant(0, 0);
            Piece p6 = new Soldier(0, 0);
            Piece p7 = new Pawn(0, 0);
        }).doesNotThrowAnyException();
    }

*/
    @Test
    @DisplayName("궁은 적절한 이동이 가능하다")
    void palaceMoveTest() {
        Piece p = new Palace(5,5);
        p.move(board, 1,0);
        assertThat(p.getPosition().x()).isEqualTo(6);
        assertThat(p.getPosition().y()).isEqualTo(5);
    }

    @Test
    @DisplayName("궁의 이동 범위를 벗어나면 예외를 반환한다")
    void palaceMoveExceptionTest() {
        Piece p = new Palace(5,5);
        assertThatThrownBy(() -> p.move(board, 2, 0))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("차는 적절한 이동이 가능하다")
    void chariotMoveTest() {
        Piece p = new Chariot(5,5);
        p.move(board, 3,0);
        assertThat(p.getPosition().x()).isEqualTo(8);
        assertThat(p.getPosition().y()).isEqualTo(5);
    }

    @Test
    @DisplayName("차의 이동 범위를 벗어나면 예외를 반환한다")
    void chariotMoveExceptionTest() {
        Piece p = new Palace(5,5);
        assertThatThrownBy(() -> p.move(board, 2,2))
            .isInstanceOf(IllegalArgumentException.class);
    }


}
