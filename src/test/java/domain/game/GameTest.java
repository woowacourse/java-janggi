package domain.game;

import domain.board.Position;
import domain.piece.Camp;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class GameTest {

    @Test
    @DisplayName("게임이 시작되면 첫 턴은 무조건 초나라이다.")
    void gameStart_Then_FirstTurnIsCho() {
        Game game = new Game(1, 1);

        assertThat(game.currentTurn()).isEqualTo(Camp.CHO);
    }

    @Test
    @DisplayName("pass시 현재 턴이 반대 진영으로 넘어간다.")
    void passTurn_Then_CurrentTurnIsChange() {
        Game game = new Game(1, 1);

        game.passTurn();

        assertThat(game.currentTurn()).isEqualTo(Camp.HAN);
    }

    @Test
    @DisplayName("현재 턴이 아닌 상대방의 기물을 움직이려 하면 예외가 발생한다.")
    void throwException_When_MoveOppositePiece() {
        Game game = new Game(1, 1);

        Position hanPiecePosition = new Position(1, 1);
        Position targetPosition = new Position(1, 2);

        assertThatThrownBy(() -> game.move(hanPiecePosition, targetPosition))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("자신의 기물을 정상적으로 움직이면 턴이 상대방에게 넘어간다.")
    void moveSuccess_Then_ChangeTurn() {
        Game game = new Game(1, 1);
        Position choPiecePosition = new Position(1, 10);
        Position targetPosition = new Position(1, 9);

        game.move(choPiecePosition, targetPosition);

        assertThat(game.currentTurn()).isEqualTo(Camp.HAN);
    }
}