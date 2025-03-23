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

@DisplayName("마 테스트")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class MaTest {

    @Test
    void 마는_마_타입이다() {
        // given
        Piece piece = new Ma(Team.CHO);
        // when & then
        assertThat(piece.type()).isEqualTo(PieceType.MA);
    }

    @Test
    void 마는_장애물에_막혀있으면_이동할_수_없다() {
        // given
        Board board = BoardFixture.createEmptyBoard();

        Team maTeam = Team.CHO;
        Piece ma = new Ma(maTeam);

        Point maPoint = Point.of(4, 5);
        Node sourceNode = board.findNodeByPoint(maPoint);

        // 위 위 오른쪽
        Point destinationPoint = Point.of(2, 6);
        Node destinationNode = board.findNodeByPoint(destinationPoint);
        Point obstaclePoint = Point.of(3, 5);
        Node obstacleNode = board.findNodeByPoint(obstaclePoint);
        board.putPiece(obstacleNode, new Cha(maTeam));

        // when
        final boolean actual = ma.canMove(sourceNode, destinationNode, board);

        // then
        assertThat(actual).isFalse();
    }

    @Test
    void 마는_도착_지점에_본인_팀_기물이_있으면_이동할_수_없다() {
        // given
        Board board = BoardFixture.createEmptyBoard();

        Team maTeam = Team.CHO;
        Piece ma = new Ma(maTeam);

        Point maPoint = Point.of(4, 5);
        Node sourceNode = board.findNodeByPoint(maPoint);

        // 위 위 오른쪽
        Point destinationPoint = Point.of(2, 6);
        Node destinationNode = board.findNodeByPoint(destinationPoint);
        board.putPiece(destinationNode, new Cha(maTeam));

        // when
        final boolean actual = ma.canMove(sourceNode, destinationNode, board);

        // then
        assertThat(actual).isFalse();
    }

    @Test
    void 마는_도착_지점이_빈칸이면_이동할_수_있다() {
        // given
        Board board = BoardFixture.createEmptyBoard();

        Team maTeam = Team.CHO;
        Piece ma = new Ma(maTeam);

        Point maPoint = Point.of(4, 5);
        Node sourceNode = board.findNodeByPoint(maPoint);

        // 위 위 오른쪽
        Point destinationPoint = Point.of(2, 6);
        Node destinationNode = board.findNodeByPoint(destinationPoint);

        // when
        final boolean actual = ma.canMove(sourceNode, destinationNode, board);

        // then
        assertThat(actual).isTrue();
    }

    @Test
    void 마는_도착_지점에_상대_팀_기물이_있으면_이동할_수_있다() {
        // given
        Board board = BoardFixture.createEmptyBoard();

        Team maTeam = Team.CHO;
        Piece ma = new Ma(maTeam);

        Point maPoint = Point.of(4, 5);
        Node sourceNode = board.findNodeByPoint(maPoint);

        // 위 위 오른쪽
        Point destinationPoint = Point.of(2, 6);
        Node destinationNode = board.findNodeByPoint(destinationPoint);
        board.putPiece(destinationNode, new Cha(maTeam.inverse()));

        // when
        final boolean actual = ma.canMove(sourceNode, destinationNode, board);

        // then
        assertThat(actual).isTrue();
    }
}