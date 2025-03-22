package domain.piece;

import domain.board.Board;
import domain.board.Point;
import fixture.BoardFixture;
import java.util.HashMap;
import java.util.Map;
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
        Team sangTeam = Team.CHO;
        Piece sang = new Sang(sangTeam);
        Point sangPoint = Point.of(7, 5);
        Point obstaclePoint = Point.of(6, 5);
        Point destinationPoint = Point.of(4, 3);

        Map<Point, Piece> pieceByPoint = new HashMap<>();
        pieceByPoint.put(sangPoint, sang);
        pieceByPoint.put(obstaclePoint, new Byeong(sangTeam));
        Board board = BoardFixture.createTestBoard(pieceByPoint);

        // when
        final boolean actual = board.canMove(sangPoint, destinationPoint);

        // then
        Assertions.assertThat(actual).isFalse();
    }

    @Test
    void 상은_세칸_떨어진_장애물에_막혀있으면_이동할_수_없다() {
        // given
        Team sangTeam = Team.CHO;
        Piece sang = new Sang(sangTeam);
        Point sangPoint = Point.of(7, 5);
        Point obstaclePoint = Point.of(5, 4);
        Point destinationPoint = Point.of(4, 3);

        Map<Point, Piece> pieceByPoint = new HashMap<>();
        pieceByPoint.put(sangPoint, sang);
        pieceByPoint.put(obstaclePoint, new Byeong(sangTeam));
        Board board = BoardFixture.createTestBoard(pieceByPoint);

        // when
        final boolean actual = board.canMove(sangPoint, destinationPoint);

        // then
        Assertions.assertThat(actual).isFalse();
    }

    @Test
    void 상은_도착_지점에_본인_팀_기물이_있으면_이동할_수_없다() {
        // given
        Team sangTeam = Team.CHO;
        Piece sang = new Sang(sangTeam);
        Point sangPoint = Point.of(7, 5);
        Point destinationPoint = Point.of(4, 3);

        Map<Point, Piece> pieceByPoint = new HashMap<>();
        pieceByPoint.put(sangPoint, sang);
        pieceByPoint.put(destinationPoint, new Cha(sangTeam));
        Board board = BoardFixture.createTestBoard(pieceByPoint);

        // when
        final boolean actual = board.canMove(sangPoint, destinationPoint);

        // then
        Assertions.assertThat(actual).isFalse();
    }

    @Test
    void 상은_도착_지점이_빈칸이면_이동할_수_있다() {
        // given
        Team sangTeam = Team.CHO;
        Piece sang = new Sang(sangTeam);
        Point sangPoint = Point.of(7, 5);
        Point destinationPoint = Point.of(4, 3);

        Map<Point, Piece> pieceByPoint = new HashMap<>();
        pieceByPoint.put(sangPoint, sang);
        Board board = BoardFixture.createTestBoard(pieceByPoint);

        // when
        final boolean actual = board.canMove(sangPoint, destinationPoint);

        // then
        Assertions.assertThat(actual).isTrue();
    }

    @Test
    void 상은_도착_지점에_상대_팀_기물이_있으면_이동할_수_있다() {
        // given
        Team sangTeam = Team.CHO;
        Piece sang = new Sang(sangTeam);
        Point sangPoint = Point.of(7, 5);
        Point destinationPoint = Point.of(4, 3);

        Map<Point, Piece> pieceByPoint = new HashMap<>();
        pieceByPoint.put(sangPoint, sang);
        pieceByPoint.put(destinationPoint, new Cha(sangTeam.inverse()));
        Board board = BoardFixture.createTestBoard(pieceByPoint);

        // when
        final boolean actual = board.canMove(sangPoint, destinationPoint);

        // then
        Assertions.assertThat(actual).isTrue();
    }
}
