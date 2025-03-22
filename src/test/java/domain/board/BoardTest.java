package domain.board;

import domain.piece.Byeong;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Po;
import domain.piece.Team;
import fixture.BoardFixture;
import java.util.HashMap;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class BoardTest {

    @Nested
    @DisplayName("예외가 발생하지 않는 테스트")
    class Success {

        @Test
        void 특정_위치에_기물이_존재하면_true를_반환한다() {
            // given
            Point point = Point.of(1, 1);

            Map<Point, Piece> pieceByPoint = new HashMap<>();
            pieceByPoint.put(point, new Byeong(Team.CHO));
            Board board = BoardFixture.createTestBoard(pieceByPoint);

            // when
            final boolean actual = board.existsPieceByPoint(point);

            // then
            Assertions.assertThat(actual).isTrue();
        }

        @Test
        void 특정_위치에_기물이_존재하지_않으면_false를_반환한다() {
            // given
            Point point = Point.of(1, 1);

            Map<Point, Piece> pieceByPoint = new HashMap<>();
            pieceByPoint.put(point, new Byeong(Team.CHO));
            Board board = BoardFixture.createTestBoard(pieceByPoint);

            Point otherPoint = Point.of(5, 5);

            // when
            final boolean actual = board.existsPieceByPoint(otherPoint);

            // then
            Assertions.assertThat(actual).isFalse();
        }

        @Test
        void 특정_위치에_존재하는_기물을_제거한다() {
            // given
            Point point = Point.of(1, 1);
            Map<Point, Piece> pieceByPoint = new HashMap<>();
            pieceByPoint.put(point, new Byeong(Team.CHO));
            Board board = BoardFixture.createTestBoard(pieceByPoint);

            // when
            board.removePiece(point);
            final boolean actual = board.existsPieceByPoint(point);

            // then
            Assertions.assertThat(actual).isFalse();
        }

        @Test
        void 특정_위치에_특정_팀인_기물이_존재하면_true를_반환한다() {
            // given
            Team team = Team.CHO;
            Point point = Point.of(1, 1);

            Map<Point, Piece> pieceByPoint = new HashMap<>();
            pieceByPoint.put(point, new Byeong(team));
            Board board = BoardFixture.createTestBoard(pieceByPoint);

            // when
            final boolean actual = board.hasTeamOfPiece(point, team);

            // then
            Assertions.assertThat(actual).isTrue();
        }

        @Test
        void 특정_위치에_특정_팀인_기물이_존재하지_않으면_false를_반환한다() {
            // given
            Team team = Team.CHO;
            Point point = Point.of(1, 1);

            Map<Point, Piece> pieceByPoint = new HashMap<>();
            pieceByPoint.put(point, new Byeong(team.inverse()));
            Board board = BoardFixture.createTestBoard(pieceByPoint);

            // when

            final boolean actual = board.hasTeamOfPiece(point, team);

            // then
            Assertions.assertThat(actual).isFalse();
        }

        @Test
        void 특정_위치에_특정_종류의_기물이_존재하면_true를_반환한다() {
            // given
            Team team = Team.CHO;
            PieceType pieceType = PieceType.PO;
            Point point = Point.of(1, 1);

            Map<Point, Piece> pieceByPoint = new HashMap<>();
            pieceByPoint.put(point, new Po(team.inverse()));
            Board board = BoardFixture.createTestBoard(pieceByPoint);

            // when
            final boolean actual = board.hasPieceType(point, pieceType);

            // then
            Assertions.assertThat(actual).isTrue();
        }

        @Test
        void 특정_위치에_특정_종류의_기물이_존재하지_않으면_false를_반환한다() {
            // given
            Team team = Team.CHO;
            PieceType pieceType = PieceType.PO;
            Point point = Point.of(1, 1);

            Map<Point, Piece> pieceByPoint = new HashMap<>();
            pieceByPoint.put(point, new Byeong(team.inverse()));
            Board board = BoardFixture.createTestBoard(pieceByPoint);

            // when
            final boolean actual = board.hasPieceType(point, pieceType);

            // then
            Assertions.assertThat(actual).isFalse();
        }
    }
}
