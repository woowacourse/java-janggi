import static org.assertj.core.api.Assertions.assertThat;

import domain.Board;
import domain.BoardFactory;
import domain.Formation;
import domain.Position;
import domain.piece.Chariot;
import domain.piece.Empty;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class BoardTest {

    @Nested
    @DisplayName("기물 이동")
    class MovePiece {

        @DisplayName("기물을 이동하면 목적지에 기물이 위치한다.")
        @Test
        void 기물을_이동하면_목적지에_기물이_위치한다() {
            Board board = BoardFactory.createBoard(Formation.from("1"), Formation.from("1"));
            Position source = Position.of(1, 1);
            Position target = Position.of(1, 3);

            board.movePiece(source, target);

            assertThat(board.getBoard().get(target)).isInstanceOf(Chariot.class);
        }

        @DisplayName("기물을 이동하면 출발지는 빈칸이 된다.")
        @Test
        void 기물을_이동하면_출발지는_빈칸이_된다() {
            Board board = BoardFactory.createBoard(Formation.from("1"), Formation.from("1"));
            Position source = Position.of(1, 1);
            Position target = Position.of(1, 3);

            board.movePiece(source, target);

            assertThat(board.getBoard().get(source)).isInstanceOf(Empty.class);
        }
    }
}
