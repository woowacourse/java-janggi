package domain.piece;

import domain.board.Board;
import domain.board.Point;
import fixture.BoardFixture;
import java.util.HashMap;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class MaTest {

    @Test
    void 마는_마_타입이다() {
        // given
        Piece piece = new Ma(Team.CHO);
        // when & then
        Assertions.assertThat(piece.type()).isEqualTo(PieceType.MA);
    }

    @Test
    void 마는_장애물에_막혀있으면_이동할_수_없다() {
        // given
        Team maTeam = Team.CHO;
        Piece ma = new Ma(maTeam);
        Point maPoint = Point.of(4, 5);
        Point obstaclePoint = Point.of(3, 5);
        Point destinationPoint = Point.of(2, 6);

        Map<Point, Piece> pieceByNode = new HashMap<>();
        pieceByNode.put(maPoint, ma);
        pieceByNode.put(obstaclePoint, new Byeong(maTeam));
        Board board = BoardFixture.createTestBoard(pieceByNode);

        // when
        final boolean actual = board.canMove(maPoint, destinationPoint);

        // then
        Assertions.assertThat(actual).isFalse();
    }

    @Test
    void 마는_도착_지점에_본인_팀_기물이_있으면_이동할_수_없다() {
        // given
        Team maTeam = Team.CHO;
        Piece ma = new Ma(maTeam);
        Point maPoint = Point.of(4, 5);
        Point destinationPoint = Point.of(2, 6);

        Map<Point, Piece> pieceByNode = new HashMap<>();
        pieceByNode.put(maPoint, ma);
        pieceByNode.put(destinationPoint, new Byeong(maTeam));
        Board board = BoardFixture.createTestBoard(pieceByNode);

        // when
        final boolean actual = board.canMove(maPoint, destinationPoint);

        // then
        Assertions.assertThat(actual).isFalse();
    }

    @Test
    void 마는_도착_지점이_빈칸이면_이동할_수_있다() {
        // given
        Team maTeam = Team.CHO;
        Piece ma = new Ma(maTeam);
        Point maPoint = Point.of(4, 5);
        Point destinationPoint = Point.of(2, 6);

        Map<Point, Piece> pieceByNode = new HashMap<>();
        pieceByNode.put(maPoint, ma);
        Board board = BoardFixture.createTestBoard(pieceByNode);

        // when
        final boolean actual = board.canMove(maPoint, destinationPoint);

        // then
        Assertions.assertThat(actual).isTrue();
    }

    @Test
    void 마는_도착_지점에_상대_팀_기물이_있으면_이동할_수_있다() {
        // given
        Team maTeam = Team.CHO;
        Piece ma = new Ma(maTeam);
        Point maPoint = Point.of(4, 5);
        Point destinationPoint = Point.of(2, 6);

        Map<Point, Piece> pieceByNode = new HashMap<>();
        pieceByNode.put(maPoint, ma);
        pieceByNode.put(destinationPoint, new Byeong(maTeam.inverse()));
        Board board = BoardFixture.createTestBoard(pieceByNode);

        // when
        final boolean actual = board.canMove(maPoint, destinationPoint);

        // then
        Assertions.assertThat(actual).isTrue();
    }
}