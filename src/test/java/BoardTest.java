import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Board;
import domain.board.BoardFactory;
import domain.board.Formation;
import domain.vo.Position;
import domain.piece.Side;
import domain.piece.Chariot;
import domain.piece.Empty;
import domain.piece.Piece;
import domain.piece.PieceType;
import java.util.HashMap;
import java.util.Map;
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

    @Nested
    @DisplayName("점수 계산")
    class CalculateScore {

        @DisplayName("초나라의 점수를 계산한다.")
        @Test
        void 초나라의_점수를_계산한다() {
            Board board = BoardFactory.createBoard(Formation.from("1"), Formation.from("1"));

            double score = board.calculateTotalScore(Side.CHO);

            assertThat(score).isEqualTo(72);
        }

        @DisplayName("한나라의 점수를 계산할 때 1.5점을 추가한다.")
        @Test
        void 한나라의_점수를_계산할_때_일점오점을_추가한다() {
            Board board = BoardFactory.createBoard(Formation.from("1"), Formation.from("1"));

            double score = board.calculateTotalScore(Side.HAN);

            assertThat(score).isEqualTo(73.5);
        }

        @DisplayName("점수 계산 시 지정한 진영 기물만 합산한다.")
        @Test
        void 점수_계산시_지정한_진영_기물만_합산한다() {
            Map<Position, Piece> pieces = new HashMap<>();
            pieces.put(Position.of(1, 1), PieceType.CHARIOT.create(Side.HAN)); // 13
            pieces.put(Position.of(2, 2), PieceType.CANNON.create(Side.CHO)); // 7
            pieces.put(Position.of(3, 3), PieceType.SOLDIER.create(Side.CHO)); // 2
            Board board = new Board(pieces);

            double choScore = board.calculateTotalScore(Side.CHO);
            double hanScore = board.calculateTotalScore(Side.HAN);

            assertThat(choScore).isEqualTo(9);
            assertThat(hanScore).isEqualTo(14.5);
        }
    }
}
