package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.game.Position;
import domain.game.Side;
import domain.board.Board;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SoldierTest {
    @Test
    @DisplayName("초나라 졸은 상/좌/우 이동이 가능하고 한나라 졸은 하/좌/우 이동이 가능하다")
    void moveBySide() {
        Position choPos = Position.of(4, 3);
        Position hanPos = Position.of(4, 6);
        Board board = new Board(Map.of(
                choPos, PieceFactory.createSoldier(Side.CHO),
                hanPos, PieceFactory.createSoldier(Side.HAN)
        ));

        assertThat(board.findDestinations(choPos).getPositions()).containsExactlyInAnyOrder(
                Position.of(4, 4), Position.of(3, 3), Position.of(5, 3)
        );
        assertThat(board.findDestinations(hanPos).getPositions()).containsExactlyInAnyOrder(
                Position.of(4, 5), Position.of(3, 6), Position.of(5, 6)
        );
    }

    @Test
    @DisplayName("졸은 궁성에서 전진 대각선으로 이동할 수 있다")
    void moveDiagonalInPalace() {
        Position choPos = Position.of(4, 1);
        Position hanPos = Position.of(4, 8);
        Board board = new Board(Map.of(
                choPos, PieceFactory.createSoldier(Side.CHO),
                hanPos, PieceFactory.createSoldier(Side.HAN)
        ));

        assertThat(board.findDestinations(choPos).getPositions()).contains(
                Position.of(3, 2), Position.of(5, 2)
        );
        assertThat(board.findDestinations(hanPos).getPositions()).contains(
                Position.of(3, 7), Position.of(5, 7)
        );
    }
}
