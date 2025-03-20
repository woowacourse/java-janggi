package domain.piece;

import domain.board.Board;
import domain.board.Node;
import domain.board.Point;
import fixture.BoardFixture;
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
        Board board = BoardFixture.createEmptyBoard();

        Team poTeam = Team.CHO;
        Piece po = new Po(poTeam);

        Point poPoint = Point.of(8, 1);
        Node sourceNode = board.findNodeByPoint(poPoint);

        Point destinationPoint = Point.of(8, 9);
        Node destinationNode = board.findNodeByPoint(destinationPoint);

        Point otherPoPoint = Point.of(8, 8);
        Node otherPoNode = board.findNodeByPoint(otherPoPoint);

        board.putPiece(sourceNode, po);
        board.putPiece(otherPoNode, new Po(poTeam));

        // when
        final boolean actual = po.canMove(sourceNode, destinationNode, board);

        // then
        Assertions.assertThat(actual).isFalse();
    }

    @Test
    void 포는_적_팀_포를_뛰어넘을_수_없다() {
        // given
        Board board = BoardFixture.createEmptyBoard();

        Team poTeam = Team.CHO;
        Piece po = new Po(poTeam);

        Point poPoint = Point.of(8, 1);
        Node sourceNode = board.findNodeByPoint(poPoint);

        Point destinationPoint = Point.of(8, 9);
        Node destinationNode = board.findNodeByPoint(destinationPoint);

        Point otherPoPoint = Point.of(8, 8);
        Node otherPoNode = board.findNodeByPoint(otherPoPoint);

        board.putPiece(sourceNode, po);
        board.putPiece(otherPoNode, new Po(poTeam.inverse()));

        // when
        final boolean actual = po.canMove(sourceNode, destinationNode, board);

        // then
        Assertions.assertThat(actual).isFalse();
    }

    @Test
    void 포는_적_팀_포를_먹을_수_없다() {
        // given
        Board board = BoardFixture.createEmptyBoard();

        Team poTeam = Team.CHO;
        Piece po = new Po(poTeam);

        Point destinationPoint = Point.of(4, 1);
        Node destinationNode = board.findNodeByPoint(destinationPoint);

        Point hurdlePoint = Point.of(6, 1);
        Node hurdleNode = board.findNodeByPoint(hurdlePoint);

        Point poPoint = Point.of(8, 1);
        Node sourceNode = board.findNodeByPoint(poPoint);

        board.putPiece(destinationNode, new Po(poTeam.inverse()));
        board.putPiece(hurdleNode, new Byeong(poTeam));
        board.putPiece(sourceNode, po);

        // when
        final boolean actual = po.canMove(sourceNode, destinationNode, board);

        // then
        Assertions.assertThat(actual).isFalse();
    }

    @Test
    void 포는_두_개_이상의_기물을_뛰어넘을_수_없다() {
        // given
        Board board = BoardFixture.createEmptyBoard();

        Team poTeam = Team.CHO;
        Piece po = new Po(poTeam);

        Point destinationPoint = Point.of(4, 1);
        Node destinationNode = board.findNodeByPoint(destinationPoint);

        Point hurdlePoint = Point.of(5, 1);
        Node hurdleNode = board.findNodeByPoint(hurdlePoint);

        Point hurdlePoint2 = Point.of(6, 1);
        Node hurdleNode2 = board.findNodeByPoint(hurdlePoint2);

        Point poPoint = Point.of(8, 1);
        Node sourceNode = board.findNodeByPoint(poPoint);

        board.putPiece(destinationNode, new Po(poTeam.inverse()));
        board.putPiece(hurdleNode, new Byeong(poTeam));
        board.putPiece(hurdleNode2, new Sang(poTeam));
        board.putPiece(sourceNode, po);

        // when
        final boolean actual = po.canMove(sourceNode, destinationNode, board);

        // then
        Assertions.assertThat(actual).isFalse();
    }

    @Test
    void 포는_포가_아닌_한_개의_기물을_뛰어넘어_빈칸으로_갈_수_있다() {
        // given
        Board board = BoardFixture.createEmptyBoard();

        Team poTeam = Team.CHO;
        Piece po = new Po(poTeam);

        Point destinationPoint = Point.of(4, 1);
        Node destinationNode = board.findNodeByPoint(destinationPoint);

        Point hurdlePoint = Point.of(6, 1);
        Node hurdleNode = board.findNodeByPoint(hurdlePoint);

        Point poPoint = Point.of(8, 1);
        Node sourceNode = board.findNodeByPoint(poPoint);

        board.putPiece(hurdleNode, new Byeong(poTeam));
        board.putPiece(sourceNode, po);

        // when
        final boolean actual = po.canMove(sourceNode, destinationNode, board);

        // then
        Assertions.assertThat(actual).isTrue();
    }

    @Test
    void 포는_포가_아닌_한_개의_기물을_뛰어넘어_적_팀_기물을_먹을_수_있다() {
        // given
        Board board = BoardFixture.createEmptyBoard();

        Team poTeam = Team.CHO;
        Piece po = new Po(poTeam);

        Point destinationPoint = Point.of(4, 1);
        Node destinationNode = board.findNodeByPoint(destinationPoint);

        Point hurdlePoint = Point.of(6, 1);
        Node hurdleNode = board.findNodeByPoint(hurdlePoint);

        Point poPoint = Point.of(8, 1);
        Node sourceNode = board.findNodeByPoint(poPoint);

        board.putPiece(hurdleNode, new Byeong(poTeam));
        board.putPiece(sourceNode, po);
        board.putPiece(hurdleNode, new Cha(poTeam.inverse()));

        // when
        final boolean actual = po.canMove(sourceNode, destinationNode, board);

        // then
        Assertions.assertThat(actual).isTrue();
    }
}
