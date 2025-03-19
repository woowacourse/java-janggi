package domain.piece;

import domain.PieceType;
import domain.board.Board;
import domain.board.Node;
import domain.board.Point;
import fixture.BoardFixture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class SangTest {

    @Test
    void 상은_상_타입이다() {
        // given
        Piece piece = new Sang(Team.CHO);
        // when & then
        Assertions.assertThat(piece.type()).isEqualTo(PieceType.SANG);
    }

    @Test
    void 상은_한칸_떨어진_장애물에_막혀있으면_이동할_수_없다() {
        // given
        Board board = BoardFixture.createEmptyBoard();

        Team sangTeam = Team.CHO;
        Piece sang = new Sang(sangTeam);

        Point sangPoint = Point.of(7, 5);
        Node sourceNode = board.findNodeByPoint(sangPoint);

        Point destinationPoint = Point.of(4, 3);
        Node destinationNode = board.findNodeByPoint(destinationPoint);
        Point obstaclePoint = Point.of(6, 5);
        Node obstacleNode = board.findNodeByPoint(obstaclePoint);
        board.putPiece(obstacleNode, new Cha(sangTeam));

        // when
        final boolean actual = sang.canMove(sourceNode, destinationNode, board);

        // then
        Assertions.assertThat(actual).isFalse();
    }

    @Test
    void 상은_세칸_떨어진_장애물에_막혀있으면_이동할_수_없다() {
        // given
        Board board = BoardFixture.createEmptyBoard();

        Team sangTeam = Team.CHO;
        Piece sang = new Sang(sangTeam);

        Point sangPoint = Point.of(7, 5);
        Node sourceNode = board.findNodeByPoint(sangPoint);

        Point destinationPoint = Point.of(4, 3);
        Node destinationNode = board.findNodeByPoint(destinationPoint);
        Point obstaclePoint = Point.of(5, 4);
        Node obstacleNode = board.findNodeByPoint(obstaclePoint);
        board.putPiece(obstacleNode, new Cha(sangTeam));

        // when
        final boolean actual = sang.canMove(sourceNode, destinationNode, board);

        // then
        Assertions.assertThat(actual).isFalse();
    }

    @Test
    void 상은_도착_지점에_본인_팀_기물이_있으면_이동할_수_없다() {
        // given
        Board board = BoardFixture.createEmptyBoard();

        Team sangTeam = Team.CHO;
        Piece sang = new Sang(sangTeam);

        Point sangPoint = Point.of(7, 5);
        Node sourceNode = board.findNodeByPoint(sangPoint);

        Point destinationPoint = Point.of(4, 3);
        Node destinationNode = board.findNodeByPoint(destinationPoint);
        board.putPiece(destinationNode, new Cha(sangTeam));

        // when
        final boolean actual = sang.canMove(sourceNode, destinationNode, board);

        // then
        Assertions.assertThat(actual).isFalse();
    }

    @Test
    void 상은_도착_지점이_빈칸이면_이동할_수_있다() {
        // given
        Board board = BoardFixture.createEmptyBoard();

        Team sangTeam = Team.CHO;
        Piece sang = new Sang(sangTeam);

        Point sangPoint = Point.of(7, 5);
        Node sourceNode = board.findNodeByPoint(sangPoint);

        Point destinationPoint = Point.of(4, 3);
        Node destinationNode = board.findNodeByPoint(destinationPoint);

        // when
        final boolean actual = sang.canMove(sourceNode, destinationNode, board);

        // then
        Assertions.assertThat(actual).isTrue();
    }

    @Test
    void 상은_도착_지점에_상대_팀_기물이_있으면_이동할_수_있다() {
        // given
        Board board = BoardFixture.createEmptyBoard();

        Team sangTeam = Team.CHO;
        Piece sang = new Sang(sangTeam);

        Point sangPoint = Point.of(7, 5);
        Node sourceNode = board.findNodeByPoint(sangPoint);

        Point destinationPoint = Point.of(4, 3);
        Node destinationNode = board.findNodeByPoint(destinationPoint);
        board.putPiece(destinationNode, new Cha(sangTeam.inverse()));

        // when
        final boolean actual = sang.canMove(sourceNode, destinationNode, board);

        // then
        Assertions.assertThat(actual).isTrue();
    }
}
