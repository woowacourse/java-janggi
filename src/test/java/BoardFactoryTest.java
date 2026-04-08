import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Board;
import domain.board.BoardFactory;
import domain.board.Formation;
import domain.vo.Position;
import domain.piece.Side;
import domain.piece.Elephant;
import domain.piece.Horse;
import domain.piece.Piece;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class BoardFactoryTest {

    @Nested
    @DisplayName("장기판 생성")
    class CreateBoard {

        @DisplayName("장기판의 모든 좌표를 초기화한다.")
        @Test
        void 장기판의_모든_좌표를_초기화한다() {
            Board board = BoardFactory.createBoard(Formation.from("1"), Formation.from("1"));
            Map<Position, Piece> pieces = board.getBoard();

            assertThat(pieces).hasSize(90);
            for (int x = 1; x <= 9; x++) {
                for (int y = 1; y <= 10; y++) {
                    assertThat(pieces.get(Position.of(x, y))).isNotNull();
                }
            }
        }

        @DisplayName("초나라 포진을 상마마상으로 배치한다.")
        @Test
        void 초나라_포진을_상마마상으로_배치한다() {
            Board board = BoardFactory.createBoard(Formation.from("1"), Formation.from("2"));
            Map<Position, Piece> pieces = board.getBoard();

            assertThat(pieces.get(Position.of(2, 10))).isInstanceOf(Elephant.class);
            assertThat(pieces.get(Position.of(3, 10))).isInstanceOf(Horse.class);
            assertThat(pieces.get(Position.of(7, 10))).isInstanceOf(Horse.class);
            assertThat(pieces.get(Position.of(8, 10))).isInstanceOf(Elephant.class);
        }

        @DisplayName("한나라 포진을 마상마상으로 배치한다.")
        @Test
        void 한나라_포진을_마상마상으로_배치한다() {
            Board board = BoardFactory.createBoard(Formation.from("1"), Formation.from("4"));
            Map<Position, Piece> pieces = board.getBoard();

            assertThat(pieces.get(Position.of(2, 1))).isInstanceOf(Horse.class);
            assertThat(pieces.get(Position.of(3, 1))).isInstanceOf(Elephant.class);
            assertThat(pieces.get(Position.of(7, 1))).isInstanceOf(Horse.class);
            assertThat(pieces.get(Position.of(8, 1))).isInstanceOf(Elephant.class);
        }

        @DisplayName("각 궁이 올바른 진영에 배치된다.")
        @Test
        void 각_궁이_올바른_진영에_배치된다() {
            Board board = BoardFactory.createBoard(Formation.from("1"), Formation.from("2"));
            Map<Position, Piece> pieces = board.getBoard();

            assertThat(pieces.get(Position.of(5, 2)).isSameSide(Side.HAN)).isTrue();
            assertThat(pieces.get(Position.of(5, 9)).isSameSide(Side.CHO)).isTrue();
        }
    }
}
