package position;

import static janggi.piece.Team.CHO;
import static janggi.piece.Team.HAN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static position.PositionFixtures.E0;
import static position.PositionFixtures.E1;
import static position.PositionFixtures.E2;
import static position.PositionFixtures.E3;
import static position.PositionFixtures.E5;

import janggi.piece.Piece;
import janggi.piece.PieceType;
import janggi.piece.normalPiece.Palace;
import janggi.position.Board;
import janggi.position.Position;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BoardTest {

    @Test
    @DisplayName("위치에 기물이 존재하는지 확인할 수 있다.")
    void isBlankTest_1() {
        // given
        Piece palace = new Palace(HAN, E1);
        Board board = new Board(Set.of(palace));

        // when - then
        assertThat(board.isBlank(E0)).isTrue();
    }

    @Test
    @DisplayName("위치에 기물이 존재하는지 확인할 수 있다.")
    void isBlankTest_2() {
        // given
        Piece palace = new Palace(HAN, E1);
        Board board = new Board(Set.of(palace));

        // when - then
        assertThat(board.isBlank(E1)).isFalse();
    }

    @Test
    @DisplayName("같은 팀 기물을 움직이려 하면 예외가 발생한다.")
    void validateTeamTest() {
        // given
        Piece palace = new Palace(HAN, E1);
        Board board = new Board(Set.of(palace));

        // when - then
        assertThatThrownBy(() -> board.validateTeam(HAN))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 같은 팀 기물만 움직일 수 있습니다.");
    }

    @Test
    @DisplayName("이동할 수 없는 좌표를 입력하면 예외가 발생한다.")
    void validateCanMovePositionTest(){
        // given
        Piece palace = new Palace(CHO, E1);
        Board board = new Board(Set.of(palace));

        // when - then
        assertThatThrownBy(() -> board.move(E1, E5))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 잘못된 좌표입니다.");
    }

    @Test
    @DisplayName("기물을 이동시킬 수 있다.")
    void moveTest(){
        // given
        Piece palace = new Palace(CHO, E1);
        Board board = new Board(Set.of(palace));

        // when
        Board movedBoard = board.move(E1, E2);

        // then
        assertThat(movedBoard.get(E2).type()).isEqualTo(PieceType.PALACE);
    }

    @Test
    @DisplayName("턴을 넘길 수 있다.")
    void nextTurnTest(){
        // given
        Board board = new Board(HAN, Set.of());

        // when - then
        assertThat(board.nextTurn()).isEqualTo(CHO);
    }

    @Test
    @DisplayName("궁이 잡혔는지 확인할 수 있다.")
    void catchPalaceTest(){
        // given
        Board board = new Board(HAN, Set.of(new Palace(CHO, E1)));

        // when - then
        assertThat(board.catchPalace()).isTrue();
    }

    @Test
    @DisplayName("어느 팀이 승리했는지 확인할 수 있다.")
    void findWinnerTest(){
        // given
        Board board = new Board(HAN, Set.of(new Palace(CHO, E1)));

        // when - then
        assertThat(board.findWinner()).isEqualTo(CHO);
    }

}
