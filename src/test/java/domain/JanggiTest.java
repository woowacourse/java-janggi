package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class JanggiTest {

    @Nested
    class ValidCases {

        @DisplayName("기물 이동을 성공하면 턴이 바뀐다.")
        @Test
        void processTurn() {
            // given
            Board board = new Board(Map.of(
                new BoardPosition(0, 0), new Piece(PieceType.쭈, Team.GREEN)
            ));
            Janggi janggi = new Janggi(board, Team.GREEN);

            // when
            janggi.processTurn(new BoardPosition(0, 0), new BoardPosition(0, 1));

            // then
            assertThat(janggi.getCurrentTeam()).isEqualTo(Team.RED);
        }
    }
}
