package janggi.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import janggi.domain.board.Board;
import janggi.domain.piece.Cannon;
import janggi.domain.piece.Piece;
import janggi.domain.team.TeamType;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class BoardTest {

    @Nested
    @DisplayName("빈칸 여부 테스트")
    class isBlank {

        @Test
        @DisplayName("빈칸인 경우")
        void success_1() {
            Board board = new Board(new LinkedHashMap<>());
            Position position = Position.valueOf(1, 1);
            boolean expected = true;

            boolean actual = board.isBlank(position);

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        @DisplayName("빈칸이 아닌 경우")
        void success_2() {
            Position position = Position.valueOf(1, 1);
            Map<Position, Piece> positionPieceMap = Map.of(
                position, new Cannon(TeamType.RED));
            Board board = new Board(positionPieceMap);
            boolean expected = false;

            boolean actual = board.isBlank(position);

            assertThat(actual).isEqualTo(expected);
        }
    }
}
