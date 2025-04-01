package position;

import static janggi.piece.Team.CHO;
import static janggi.piece.Team.HAN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static position.PositionFixtures.E0;
import static position.PositionFixtures.E1;
import static position.PositionFixtures.E2;
import static position.PositionFixtures.E3;
import static position.PositionFixtures.E4;
import static position.PositionFixtures.E5;
import static position.PositionFixtures.E6;

import janggi.piece.Piece;
import janggi.piece.PieceType;
import janggi.piece.jumpingPiece.Cannon;
import janggi.piece.pawnPiece.ChoPawn;
import janggi.piece.normalPiece.Elephant;
import janggi.piece.pawnPiece.HanPawn;
import janggi.piece.normalPiece.Horse;
import janggi.piece.palacePiece.King;
import janggi.piece.palacePiece.Soldier;
import janggi.piece.straightPiece.Chariot;
import janggi.position.Board;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.ConnectH2;

public class BoardTest {

    @Test
    @DisplayName("위치에 기물이 존재하는지 확인할 수 있다.")
    void isBlankTest_1() {
        // given
        Piece king = new King(HAN, E1);
        Board board = new Board(Set.of(king));

        // when - then
        assertThat(board.isBlank(E0)).isTrue();
    }

    @Test
    @DisplayName("위치에 기물이 존재하는지 확인할 수 있다.")
    void isBlankTest_2() {
        // given
        Piece king = new King(HAN, E1);
        Board board = new Board(Set.of(king));

        // when - then
        assertThat(board.isBlank(E1)).isFalse();
    }

    @Test
    @DisplayName("같은 팀 기물을 움직이려 하면 예외가 발생한다.")
    void validateTeamTest() {
        // given
        Piece king = new King(HAN, E1);
        Board board = new Board(Set.of(king));

        // when - then
        assertThatThrownBy(() -> board.validateTeam(HAN))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 같은 팀 기물만 움직일 수 있습니다.");
    }

    @Test
    @DisplayName("이동할 수 없는 좌표를 입력하면 예외가 발생한다.")
    void validateCanMovePositionTest(){
        // given
        Piece king = new King(CHO, E1);
        Board board = new Board(Set.of(king));

        // when - then
        assertThatThrownBy(() -> board.move(E1, E5, new ConnectH2()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 잘못된 좌표입니다.");
    }

    @Test
    @DisplayName("기물을 이동시킬 수 있다.")
    void moveTest(){
        // given
        Piece king = new King(CHO, E1);
        Board board = new Board(Set.of(king));

        // when
        Board movedBoard = board.move(E1, E2, new ConnectH2());

        // then
        assertThat(movedBoard.get(E2).type()).isEqualTo(PieceType.KING);
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
    void catchKingTest(){
        // given
        Board board = new Board(HAN, Set.of(new King(CHO, E1)));

        // when - then
        assertThat(board.catchKing()).isTrue();
    }

    @Test
    @DisplayName("어느 팀이 승리했는지 확인할 수 있다.")
    void findWinnerTest(){
        // given
        Board board = new Board(HAN, Set.of(new King(CHO, E1)));

        // when - then
        assertThat(board.findWinner()).isEqualTo(CHO);
    }

    @Test
    @DisplayName("한나라의 점수를 계산할 수 있다.")
    void hanScoreTest(){
        // given
        Piece chariot = new Chariot(HAN, E1);
        Piece cannon = new Cannon(HAN, E2);
        Piece horse = new Horse(HAN, E3);
        Piece elephant = new Elephant(HAN, E4);
        Piece soldier = new Soldier(HAN, E5);
        Piece hanPawn = new HanPawn(E6);

        Board board = new Board(HAN, Set.of(chariot, cannon, horse, elephant, soldier, hanPawn));

        // when - then
        assertThat(board.hanScore()).isEqualTo(34.5);
    }

    @Test
    @DisplayName("초나라의 점수를 계산할 수 있다.")
    void choScoreTest(){
        // given
        Piece chariot = new Chariot(CHO, E1);
        Piece cannon = new Cannon(CHO, E2);
        Piece horse = new Horse(CHO, E3);
        Piece elephant = new Elephant(CHO, E4);
        Piece soldier = new Soldier(CHO, E5);
        Piece choPawn = new ChoPawn(E6);

        Board board = new Board(CHO, Set.of(chariot, cannon, horse, elephant, soldier, choPawn));

        // when - then
        assertThat(board.choScore()).isEqualTo(33);
    }

}
