package domain.piece;

import domain.PieceType;
import domain.board.Board;
import domain.board.Node;
import domain.board.Point;
import fixture.BoardFixture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class WangTest {

    @Test
    void 왕은_왕_타입이다() {
        // given
        Piece piece = new Wang(Team.CHO);
        // when & then
        Assertions.assertThat(piece.type()).isEqualTo(PieceType.WANG);
    }

    @Test
    void 왕은_적_기물이_있는_위치로_갈_수_있다() {
        // given
        Board board = BoardFixture.createEmptyBoard();

        Team wangTeam = Team.CHO;
        Piece wang = new Wang(wangTeam);

        Point wangPoint = Point.of(9, 5);
        Node sourceNode = board.findNodeByPoint(wangPoint);

        Point destinationPoint = Point.of(9, 4);
        Node destinationNode = board.findNodeByPoint(destinationPoint);

        board.putPiece(destinationNode, new Byeong(wangTeam.inverse()));

        // when
        final boolean actual = wang.canMove(wangTeam, sourceNode, destinationNode, board);

        // then
        Assertions.assertThat(actual).isTrue();
    }

    @Test
    void 왕은_빈칸이_있는_위치로_갈_수_있다() {
        // given
        Board board = BoardFixture.createEmptyBoard();

        Team wangTeam = Team.CHO;
        Piece wang = new Wang(wangTeam);

        Point wangPoint = Point.of(9, 5);
        Node sourceNode = board.findNodeByPoint(wangPoint);

        Point destinationPoint = Point.of(9, 4);
        Node destinationNode = board.findNodeByPoint(destinationPoint);

        // when
        final boolean actual = wang.canMove(wangTeam, sourceNode, destinationNode, board);

        // then
        Assertions.assertThat(actual).isTrue();
    }

    @Test
    void 왕은_본인_팀의_기물이_있는_위치로_갈_수_없다() {
        // given
        Board board = BoardFixture.createEmptyBoard();

        Team wangTeam = Team.CHO;
        Piece wang = new Wang(wangTeam);

        Point wangPoint = Point.of(9, 5);
        Node sourceNode = board.findNodeByPoint(wangPoint);

        Point destinationPoint = Point.of(9, 4);
        Node destinationNode = board.findNodeByPoint(destinationPoint);

        board.putPiece(destinationNode, new Byeong(wangTeam));
        // when
        final boolean actual = wang.canMove(wangTeam, sourceNode, destinationNode, board);

        // then
        Assertions.assertThat(actual).isFalse();
    }
}
