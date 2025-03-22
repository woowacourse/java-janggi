package domain.piece;

import domain.board.Board;
import domain.board.Point;
import fixture.BoardFixture;
import java.util.HashMap;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ByeongTest {

    @Test
    void 병은_병_타입이다() {
        // given
        Piece piece = new Byeong(Team.CHO);
        // when & then
        Assertions.assertThat(piece.type()).isEqualTo(PieceType.BYEONG);
    }

    @Test
    void 초나라_병은_왼쪽_빈칸으로_갈_수_있다() {
        // given
        Team byeongTeam = Team.CHO;
        Piece byeong = new Byeong(byeongTeam);
        Point byeongPoint = Point.of(9, 5);
        Point destinationPoint = Point.of(9, 4);

        Map<Point, Piece> pieceByPoint = new HashMap<>();
        pieceByPoint.put(byeongPoint, byeong);
        Board board = BoardFixture.createTestBoard(pieceByPoint);

        // when
        final boolean actual = board.canMove(byeongPoint, destinationPoint);

        // then
        Assertions.assertThat(actual).isTrue();
    }

    @Test
    void 초나라_병은_위쪽_빈칸으로_갈_수_있다() {
        // given
        Team byeongTeam = Team.CHO;
        Piece byeong = new Byeong(byeongTeam);
        Point byeongPoint = Point.of(9, 5);
        Point destinationPoint = Point.of(8, 5);

        Map<Point, Piece> pieceByPoint = new HashMap<>();
        pieceByPoint.put(byeongPoint, byeong);
        Board board = BoardFixture.createTestBoard(pieceByPoint);

        // when
        final boolean actual = board.canMove(byeongPoint, destinationPoint);

        // then
        Assertions.assertThat(actual).isTrue();
    }

    @Test
    void 초나라_병은_오른쪽_빈칸으로_갈_수_있다() {
        // given
        Team byeongTeam = Team.CHO;
        Piece byeong = new Byeong(byeongTeam);
        Point byeongPoint = Point.of(9, 5);
        Point destinationPoint = Point.of(9, 6);

        Map<Point, Piece> pieceByNode = new HashMap<>();
        pieceByNode.put(byeongPoint, byeong);
        Board board = BoardFixture.createTestBoard(pieceByNode);

        // when
        final boolean actual = board.canMove(byeongPoint, destinationPoint);

        // then
        Assertions.assertThat(actual).isTrue();
    }

    @Test
    void 초나라_병은_아래쪽으로_갈_수_없다() {
        // given
        Team byeongTeam = Team.CHO;
        Piece byeong = new Byeong(byeongTeam);
        Point byeongPoint = Point.of(9, 5);
        Point destinationPoint = Point.of(10, 5);

        Map<Point, Piece> pieceByNode = new HashMap<>();
        pieceByNode.put(byeongPoint, byeong);
        Board board = BoardFixture.createTestBoard(pieceByNode);

        // when
        final boolean actual = board.canMove(byeongPoint, destinationPoint);

        // then
        Assertions.assertThat(actual).isFalse();
    }

    @Test
    void 한나라_병은_왼쪽_빈칸으로_갈_수_있다() {
        // given
        Team byeongTeam = Team.HAN;
        Piece byeong = new Byeong(byeongTeam);
        Point byeongPoint = Point.of(3, 5);
        Point destinationPoint = Point.of(3, 4);

        Map<Point, Piece> pieceByNode = new HashMap<>();
        pieceByNode.put(byeongPoint, byeong);
        Board board = BoardFixture.createTestBoard(pieceByNode);

        // when
        final boolean actual = board.canMove(byeongPoint, destinationPoint);

        // then
        Assertions.assertThat(actual).isTrue();
    }

    @Test
    void 한나라_병은_아래쪽_빈칸으로_갈_수_있다() {
        // given
        Team byeongTeam = Team.HAN;
        Piece byeong = new Byeong(byeongTeam);
        Point byeongPoint = Point.of(3, 5);
        Point destinationPoint = Point.of(4, 5);

        Map<Point, Piece> pieceByNode = new HashMap<>();
        pieceByNode.put(byeongPoint, byeong);
        Board board = BoardFixture.createTestBoard(pieceByNode);

        // when
        final boolean actual = board.canMove(byeongPoint, destinationPoint);

        // then
        Assertions.assertThat(actual).isTrue();
    }

    @Test
    void 한나라_병은_오른쪽_빈칸으로_갈_수_있다() {
        // given
        Team byeongTeam = Team.HAN;
        Piece byeong = new Byeong(byeongTeam);
        Point byeongPoint = Point.of(3, 5);
        Point destinationPoint = Point.of(3, 6);

        Map<Point, Piece> pieceByNode = new HashMap<>();
        pieceByNode.put(byeongPoint, byeong);
        Board board = BoardFixture.createTestBoard(pieceByNode);

        // when
        final boolean actual = board.canMove(byeongPoint, destinationPoint);

        // then
        Assertions.assertThat(actual).isTrue();
    }

    @Test
    void 한나라_병은_위쪽으로_갈_수_없다() {
        // given
        Team byeongTeam = Team.HAN;
        Piece byeong = new Byeong(byeongTeam);
        Point byeongPoint = Point.of(3, 5);
        Point destinationPoint = Point.of(2, 5);

        Map<Point, Piece> pieceByNode = new HashMap<>();
        pieceByNode.put(byeongPoint, byeong);
        Board board = BoardFixture.createTestBoard(pieceByNode);

        // when
        final boolean actual = board.canMove(byeongPoint, destinationPoint);

        // then
        Assertions.assertThat(actual).isFalse();
    }

    @Test
    void 병은_적_기물이_있는_위치로_갈_수_있다() {
        // given
        Team byeongTeam = Team.CHO;
        Piece byeong = new Byeong(byeongTeam);
        Point byeongPoint = Point.of(9, 5);
        Point destinationPoint = Point.of(9, 4);

        Map<Point, Piece> pieceByNode = new HashMap<>();
        pieceByNode.put(byeongPoint, byeong);
        pieceByNode.put(destinationPoint, new Byeong(byeongTeam.inverse()));
        Board board = BoardFixture.createTestBoard(pieceByNode);

        // when
        final boolean actual = board.canMove(byeongPoint, destinationPoint);

        // then
        Assertions.assertThat(actual).isTrue();
    }

    @Test
    void 병은_빈칸이_있는_위치로_갈_수_있다() {
        // given
        Team byeongTeam = Team.CHO;
        Piece byeong = new Byeong(byeongTeam);
        Point byeongPoint = Point.of(9, 5);
        Point destinationPoint = Point.of(9, 4);

        Map<Point, Piece> pieceByPoint = new HashMap<>();
        pieceByPoint.put(byeongPoint, byeong);
        Board board = BoardFixture.createTestBoard(pieceByPoint);

        // when
        final boolean actual = board.canMove(byeongPoint, destinationPoint);

        // then
        Assertions.assertThat(actual).isTrue();
    }

    @Test
    void 병은_본인_팀의_기물이_있는_위치로_갈_수_없다() {
        // given
        Team byeongTeam = Team.CHO;
        Piece byeong = new Byeong(byeongTeam);
        Point byeongPoint = Point.of(9, 5);
        Point destinationPoint = Point.of(9, 4);
        Map<Point, Piece> pieceByPoint = new HashMap<>();

        Map<Point, Piece> pieceByNode = new HashMap<>();
        pieceByNode.put(byeongPoint, byeong);
        pieceByNode.put(destinationPoint, new Byeong(byeongTeam));
        Board board = BoardFixture.createTestBoard(pieceByNode);

        // when
        final boolean actual = board.canMove(byeongPoint, destinationPoint);

        // then
        Assertions.assertThat(actual).isFalse();
    }
}