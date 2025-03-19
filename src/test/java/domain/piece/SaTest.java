package domain.piece;

import domain.PieceType;
import domain.board.Board;
import domain.board.Node;
import domain.board.Point;
import fixture.BoardFixture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class SaTest {

    @Test
    void 사는_사_타입이다() {
        // given
        Piece piece = new Sa(Team.CHO);
        // when & then
        Assertions.assertThat(piece.type()).isEqualTo(PieceType.SA);
    }

    @Test
    void 사는_적_기물이_있는_위치로_갈_수_있다() {
        // given
        Board board = BoardFixture.createEmptyBoard();

        Team saTeam = Team.CHO;
        Piece sa = new Sa(saTeam);

        Point saPoint = Point.of(9, 5);
        Node sourceNode = board.findNodeByPoint(saPoint);

        Point destinationPoint = Point.of(9, 4);
        Node destinationNode = board.findNodeByPoint(destinationPoint);

        board.putPiece(destinationNode, new Byeong(saTeam.inverse()));

        // when
        final boolean actual = sa.canMove(sourceNode, destinationNode, board);

        // then
        Assertions.assertThat(actual).isTrue();
    }

    @Test
    void 사는_빈칸이_있는_위치로_갈_수_있다() {
        // given
        Board board = BoardFixture.createEmptyBoard();

        Team saTeam = Team.CHO;
        Piece sa = new Sa(saTeam);

        Point saPoint = Point.of(9, 5);
        Node sourceNode = board.findNodeByPoint(saPoint);

        Point destinationPoint = Point.of(9, 4);
        Node destinationNode = board.findNodeByPoint(destinationPoint);

        // when
        final boolean actual = sa.canMove(sourceNode, destinationNode, board);

        // then
        Assertions.assertThat(actual).isTrue();
    }

    @Test
    void 사는_본인_팀의_기물이_있는_위치로_갈_수_없다() {
        // given
        Board board = BoardFixture.createEmptyBoard();

        Team saTeam = Team.CHO;
        Piece sa = new Sa(saTeam);

        Point saPoint = Point.of(9, 5);
        Node sourceNode = board.findNodeByPoint(saPoint);

        Point destinationPoint = Point.of(9, 4);
        Node destinationNode = board.findNodeByPoint(destinationPoint);

        board.putPiece(destinationNode, new Byeong(saTeam));
        // when
        final boolean actual = sa.canMove(sourceNode, destinationNode, board);

        // then
        Assertions.assertThat(actual).isFalse();
    }
}