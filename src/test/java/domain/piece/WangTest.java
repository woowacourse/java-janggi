package domain.piece;

import domain.board.Board;
import domain.board.Node;
import domain.board.Point;
import fixture.BoardFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("왕 테스트")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class WangTest {

    @Test
    void 왕은_왕_타입이다() {
        // given
        Piece piece = new Wang(Team.CHO);
        // when & then
        assertThat(piece.type()).isEqualTo(PieceType.WANG);
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
        final boolean actual = wang.canMove(sourceNode, destinationNode, board);

        // then
        assertThat(actual).isTrue();
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
        final boolean actual = wang.canMove(sourceNode, destinationNode, board);

        // then
        assertThat(actual).isTrue();
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
        final boolean actual = wang.canMove(sourceNode, destinationNode, board);

        // then
        assertThat(actual).isFalse();
    }
}
