package domain.game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.board.Position;
import domain.board.SetUp;
import domain.piece.Camp;
import domain.piece.Piece;
import domain.piece.PieceType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GameTest {

    @Test
    @DisplayName("게임 시작 시 초나라 턴으로 시작한다.")
    void startWithChoTurn() {
        Game game = new Game(SetUp.LEFT_ELEPHANT, SetUp.RIGHT_ELEPHANT);

        assertThat(game.currentTurn()).isEqualTo(Camp.CHO);
    }

    @Test
    @DisplayName("현재 턴의 기물을 이동하면 보드 상태가 변경되고 턴이 넘어간다.")
    void movePieceAndChangeTurn() {
        Game game = new Game(SetUp.LEFT_ELEPHANT, SetUp.RIGHT_ELEPHANT);
        Position from = new Position(1, 7);
        Position to = new Position(1, 6);

        game.move(from, to);

        assertThat(game.currentTurn()).isEqualTo(Camp.HAN);
        assertThat(game.board().findPiece(from)).isEmpty();

        Piece movedPiece = game.board().findBy(to);
        assertThat(movedPiece.camp()).isEqualTo(Camp.CHO);
        assertThat(movedPiece.type()).isEqualTo(PieceType.SOLDIER);
    }

    @Test
    @DisplayName("현재 턴이 아닌 기물을 이동하면 예외가 발생하고 턴은 유지된다.")
    void throwExceptionWhenMoveOpponentPiece() {
        Game game = new Game(SetUp.LEFT_ELEPHANT, SetUp.RIGHT_ELEPHANT);
        Position from = new Position(1, 4);
        Position to = new Position(1, 5);

        assertThatThrownBy(() -> game.move(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 현재 턴의 기물만 움직일 수 있습니다.");

        assertThat(game.currentTurn()).isEqualTo(Camp.CHO);
        assertThat(game.board().findBy(from).camp()).isEqualTo(Camp.HAN);
    }

    @Test
    @DisplayName("한 턴을 넘기면 상대 진영의 기물을 이동할 수 있다.")
    void moveOpponentPieceAfterPassTurn() {
        Game game = new Game(SetUp.LEFT_ELEPHANT, SetUp.RIGHT_ELEPHANT);
        Position from = new Position(1, 4);
        Position to = new Position(1, 5);

        game.passTurn();
        game.move(from, to);

        assertThat(game.currentTurn()).isEqualTo(Camp.CHO);
        assertThat(game.board().findPiece(from)).isEmpty();

        Piece movedPiece = game.board().findBy(to);
        assertThat(movedPiece.camp()).isEqualTo(Camp.HAN);
        assertThat(movedPiece.type()).isEqualTo(PieceType.SOLDIER);
    }
}
