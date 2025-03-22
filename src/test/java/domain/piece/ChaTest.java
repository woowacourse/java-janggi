package domain.piece;

import domain.board.Board;
import domain.board.Point;
import fixture.BoardFixture;
import java.util.HashMap;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ChaTest {

    @Test
    void 차는_차_타입이다() {
        // given
        Piece piece = new Cha(Team.CHO);
        // when & then
        Assertions.assertThat(piece.type()).isEqualTo(PieceType.CHA);
    }

    @Test
    void 차는_위쪽에_있는_적_기물의_위치로_갈_수_있다() {
        // given
        Team chaTeam = Team.CHO;
        Piece cha = new Cha(chaTeam);
        Point chaPoint = Point.of(10, 1);
        Point destinationPoint = Point.of(1, 1);

        Map<Point, Piece> pieceByPoint = new HashMap<>();
        pieceByPoint.put(chaPoint, cha);
        pieceByPoint.put(destinationPoint, new Cha(chaTeam.inverse()));
        Board board = BoardFixture.createTestBoard(pieceByPoint);

        // when
        final boolean actual = board.canMove(chaPoint, destinationPoint);

        // then
        Assertions.assertThat(actual).isTrue();
    }

    @Test
    void 차는_위쪽에_있는_본인_팀_기물의_위치로_갈_수_없다() {
        // given
        Team chaTeam = Team.CHO;
        Piece cha = new Cha(chaTeam);
        Point chaPoint = Point.of(10, 1);
        Point destinationPoint = Point.of(1, 1);

        Map<Point, Piece> pieceByPoint = new HashMap<>();
        pieceByPoint.put(chaPoint, cha);
        pieceByPoint.put(destinationPoint, new Cha(chaTeam));
        Board board = BoardFixture.createTestBoard(pieceByPoint);

        // when
        final boolean actual = board.canMove(chaPoint, destinationPoint);

        // then
        Assertions.assertThat(actual).isFalse();
    }

    @Test
    void 차는_위쪽에_있는_빈칸으로_갈_수_있다() {
        // given
        Team chaTeam = Team.CHO;
        Piece cha = new Cha(chaTeam);
        Point chaPoint = Point.of(10, 1);
        Point destinationPoint = Point.of(1, 1);

        Map<Point, Piece> pieceByPoint = new HashMap<>();
        pieceByPoint.put(chaPoint, cha);
        Board board = BoardFixture.createTestBoard(pieceByPoint);

        // when
        final boolean actual = board.canMove(chaPoint, destinationPoint);

        // then
        Assertions.assertThat(actual).isTrue();
    }

    @Test
    void 차는_오른쪽에_있는_적_기물의_위치로_갈_수_있다() {
        // given
        Team chaTeam = Team.CHO;
        Piece cha = new Cha(chaTeam);
        Point chaPoint = Point.of(10, 1);
        Point destinationPoint = Point.of(10, 9);

        Map<Point, Piece> pieceByPoint = new HashMap<>();
        pieceByPoint.put(chaPoint, cha);
        pieceByPoint.put(destinationPoint, new Cha(chaTeam.inverse()));
        Board board = BoardFixture.createTestBoard(pieceByPoint);

        // when
        final boolean actual = board.canMove(chaPoint, destinationPoint);

        // then
        Assertions.assertThat(actual).isTrue();
    }

    @Test
    void 차는_오른쪽에_있는_본인_팀_기물의_위치로_갈_수_없다() {
        // given
        Team chaTeam = Team.CHO;
        Piece cha = new Cha(chaTeam);
        Point chaPoint = Point.of(10, 1);
        Point destinationPoint = Point.of(10, 9);

        Map<Point, Piece> pieceByPoint = new HashMap<>();
        pieceByPoint.put(chaPoint, cha);
        pieceByPoint.put(destinationPoint, new Cha(chaTeam));
        Board board = BoardFixture.createTestBoard(pieceByPoint);

        // when
        final boolean actual = board.canMove(chaPoint, destinationPoint);

        // then
        Assertions.assertThat(actual).isFalse();
    }

    @Test
    void 차는_오른쪽에_있는_빈칸으로_갈_수_있다() {
        // given
        Team chaTeam = Team.CHO;
        Piece cha = new Cha(chaTeam);
        Point chaPoint = Point.of(10, 1);
        Point destinationPoint = Point.of(10, 9);

        Map<Point, Piece> pieceByPoint = new HashMap<>();
        pieceByPoint.put(chaPoint, cha);
        Board board = BoardFixture.createTestBoard(pieceByPoint);

        // when
        final boolean actual = board.canMove(chaPoint, destinationPoint);

        // then
        Assertions.assertThat(actual).isTrue();
    }

    @Test
    void 차는_아래쪽에_있는_적_기물의_위치로_갈_수_있다() {
        // given
        Team chaTeam = Team.CHO;
        Piece cha = new Cha(chaTeam);
        Point chaPoint = Point.of(1, 1);
        Point destinationPoint = Point.of(10, 1);

        Map<Point, Piece> pieceByPoint = new HashMap<>();
        pieceByPoint.put(chaPoint, cha);
        pieceByPoint.put(destinationPoint, new Cha(chaTeam.inverse()));
        Board board = BoardFixture.createTestBoard(pieceByPoint);

        // when
        final boolean actual = board.canMove(chaPoint, destinationPoint);

        // then
        Assertions.assertThat(actual).isTrue();
    }

    @Test
    void 차는_아래쪽에_있는_본인_팀_기물의_위치로_갈_수_없다() {
        // given
        Team chaTeam = Team.CHO;
        Piece cha = new Cha(chaTeam);
        Point chaPoint = Point.of(1, 1);
        Point destinationPoint = Point.of(10, 1);

        Map<Point, Piece> pieceByPoint = new HashMap<>();
        pieceByPoint.put(chaPoint, cha);
        pieceByPoint.put(destinationPoint, new Cha(chaTeam));
        Board board = BoardFixture.createTestBoard(pieceByPoint);

        // when
        final boolean actual = board.canMove(chaPoint, destinationPoint);

        // then
        Assertions.assertThat(actual).isFalse();
    }

    @Test
    void 차는_아래쪽에_있는_빈칸으로_갈_수_있다() {
        // given
        Team chaTeam = Team.CHO;
        Piece cha = new Cha(chaTeam);
        Point chaPoint = Point.of(1, 1);
        Point destinationPoint = Point.of(10, 1);

        Map<Point, Piece> pieceByPoint = new HashMap<>();
        pieceByPoint.put(chaPoint, cha);
        Board board = BoardFixture.createTestBoard(pieceByPoint);

        // when
        final boolean actual = board.canMove(chaPoint, destinationPoint);

        // then
        Assertions.assertThat(actual).isTrue();
    }

    @Test
    void 차는_왼쪽에_있는_적_기물의_위치로_갈_수_있다() {
        // given
        Team chaTeam = Team.CHO;
        Piece cha = new Cha(chaTeam);
        Point chaPoint = Point.of(10, 9);
        Point destinationPoint = Point.of(10, 1);

        Map<Point, Piece> pieceByPoint = new HashMap<>();
        pieceByPoint.put(chaPoint, cha);
        pieceByPoint.put(destinationPoint, new Cha(chaTeam.inverse()));
        Board board = BoardFixture.createTestBoard(pieceByPoint);

        // when
        final boolean actual = board.canMove(chaPoint, destinationPoint);

        // then
        Assertions.assertThat(actual).isTrue();
    }

    @Test
    void 차는_왼쪽에_있는_본인_팀_기물의_위치로_갈_수_없다() {
        // given
        Team chaTeam = Team.CHO;
        Piece cha = new Cha(chaTeam);
        Point chaPoint = Point.of(10, 9);
        Point destinationPoint = Point.of(10, 1);

        Map<Point, Piece> pieceByPoint = new HashMap<>();
        pieceByPoint.put(chaPoint, cha);
        pieceByPoint.put(destinationPoint, new Cha(chaTeam));
        Board board = BoardFixture.createTestBoard(pieceByPoint);

        // when
        final boolean actual = board.canMove(chaPoint, destinationPoint);

        // then
        Assertions.assertThat(actual).isFalse();
    }

    @Test
    void 차는_왼쪽에_있는_빈칸으로_갈_수_있다() {
        // given
        Team chaTeam = Team.CHO;
        Piece cha = new Cha(chaTeam);
        Point chaPoint = Point.of(10, 9);
        Point destinationPoint = Point.of(10, 1);

        Map<Point, Piece> pieceByPoint = new HashMap<>();
        pieceByPoint.put(chaPoint, cha);
        Board board = BoardFixture.createTestBoard(pieceByPoint);

        // when
        final boolean actual = board.canMove(chaPoint, destinationPoint);

        // then
        Assertions.assertThat(actual).isTrue();
    }
}