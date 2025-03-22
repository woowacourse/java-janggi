package domain.piece;

import domain.board.Board;
import domain.board.Point;
import fixture.BoardFixture;
import java.util.HashMap;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class PoTest {

    @Test
    void 포는_포_타입이다() {
        // given
        Piece piece = new Po(Team.CHO);
        // when & then
        Assertions.assertThat(piece.type()).isEqualTo(PieceType.PO);
    }

    @Test
    void 포는_같은_팀_포를_뛰어넘을_수_없다() {
        // given
        Team poTeam = Team.CHO;
        Piece po = new Po(poTeam);
        Point poPoint = Point.of(8, 1);
        Point teamPoPoint = Point.of(8, 8);
        Point destinationPoint = Point.of(8, 9);

        Map<Point, Piece> pieceByNode = new HashMap<>();
        pieceByNode.put(poPoint, po);
        pieceByNode.put(teamPoPoint, new Po(poTeam));
        Board board = BoardFixture.createTestBoard(pieceByNode);

        // when
        final boolean actual = board.canMove(poPoint, destinationPoint);

        // then
        Assertions.assertThat(actual).isFalse();
    }

    @Test
    void 포는_적_팀_포를_뛰어넘을_수_없다() {
        // given
        Team poTeam = Team.CHO;
        Piece po = new Po(poTeam);
        Point poPoint = Point.of(8, 1);
        Point destinationPoint = Point.of(8, 9);
        Point opponentPoPoint = Point.of(8, 8);

        Map<Point, Piece> pieceByNode = new HashMap<>();
        pieceByNode.put(poPoint, po);
        pieceByNode.put(opponentPoPoint, new Po(poTeam.inverse()));
        Board board = BoardFixture.createTestBoard(pieceByNode);

        // when
        final boolean actual = board.canMove(poPoint, destinationPoint);

        // then
        Assertions.assertThat(actual).isFalse();
    }

    @Test
    void 포는_같은_팀_포를_먹을_수_없다() {
        // given
        Team poTeam = Team.CHO;
        Piece po = new Po(poTeam);
        Point poPoint = Point.of(8, 1);
        Point hurdlePoint = Point.of(6, 1);
        Point destinationPoint = Point.of(4, 1);

        Map<Point, Piece> pieceByNode = new HashMap<>();
        pieceByNode.put(poPoint, po);
        pieceByNode.put(hurdlePoint, new Byeong(poTeam));
        pieceByNode.put(destinationPoint, new Po(poTeam));
        Board board = BoardFixture.createTestBoard(pieceByNode);

        // when
        final boolean actual = board.canMove(poPoint, destinationPoint);

        // then
        Assertions.assertThat(actual).isFalse();
    }

    @Test
    void 포는_적_팀_포를_먹을_수_없다() {
        // given
        Team poTeam = Team.CHO;
        Piece po = new Po(poTeam);
        Point poPoint = Point.of(8, 1);
        Point hurdlePoint = Point.of(6, 1);
        Point destinationPoint = Point.of(4, 1);

        Map<Point, Piece> pieceByNode = new HashMap<>();
        pieceByNode.put(poPoint, po);
        pieceByNode.put(hurdlePoint, new Byeong(poTeam));
        pieceByNode.put(destinationPoint, new Po(poTeam.inverse()));
        Board board = BoardFixture.createTestBoard(pieceByNode);

        // when
        final boolean actual = board.canMove(poPoint, destinationPoint);

        // then
        Assertions.assertThat(actual).isFalse();
    }

    @Test
    void 포는_두_개_이상의_기물을_뛰어넘을_수_없다() {
        // given
        Team poTeam = Team.CHO;
        Piece po = new Po(poTeam);
        Point poPoint = Point.of(8, 1);
        Point hurdlePoint = Point.of(5, 1);
        Point hurdlePoint2 = Point.of(6, 1);
        Point destinationPoint = Point.of(4, 1);

        Map<Point, Piece> pieceByNode = new HashMap<>();
        pieceByNode.put(poPoint, po);
        pieceByNode.put(hurdlePoint, new Byeong(poTeam));
        pieceByNode.put(hurdlePoint2, new Byeong(poTeam));
        pieceByNode.put(destinationPoint, new Byeong(poTeam.inverse()));
        Board board = BoardFixture.createTestBoard(pieceByNode);

        // when
        final boolean actual = board.canMove(poPoint, destinationPoint);

        // then
        Assertions.assertThat(actual).isFalse();
    }

    @Test
    void 포는_포가_아닌_한_개의_기물을_뛰어넘어_빈칸으로_갈_수_있다() {
        // given
        Team poTeam = Team.CHO;
        Piece po = new Po(poTeam);
        Point poPoint = Point.of(8, 1);
        Point hurdlePoint = Point.of(6, 1);
        Point destinationPoint = Point.of(4, 1);

        Map<Point, Piece> pieceByNode = new HashMap<>();
        pieceByNode.put(poPoint, po);
        pieceByNode.put(hurdlePoint, new Byeong(poTeam));
        Board board = BoardFixture.createTestBoard(pieceByNode);

        // when
        final boolean actual = board.canMove(poPoint, destinationPoint);

        // then
        Assertions.assertThat(actual).isTrue();
    }

    @Test
    void 포는_포가_아닌_한_개의_기물을_뛰어넘어_적_팀_기물을_먹을_수_있다() {
        // given
        Team poTeam = Team.CHO;
        Piece po = new Po(poTeam);
        Point destinationPoint = Point.of(4, 1);
        Point hurdlePoint = Point.of(6, 1);
        Point poPoint = Point.of(8, 1);

        Map<Point, Piece> pieceByNode = new HashMap<>();
        pieceByNode.put(poPoint, po);
        pieceByNode.put(hurdlePoint, new Byeong(poTeam));
        pieceByNode.put(destinationPoint, new Byeong(poTeam.inverse()));
        Board board = BoardFixture.createTestBoard(pieceByNode);

        // when
        final boolean actual = board.canMove(poPoint, destinationPoint);

        // then
        Assertions.assertThat(actual).isTrue();
    }
}
