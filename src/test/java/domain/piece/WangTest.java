package domain.piece;

import domain.board.Board;
import domain.board.Point;
import fixture.BoardFixture;
import java.util.HashMap;
import java.util.Map;
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
        Team wangTeam = Team.CHO;
        Piece wang = new Wang(wangTeam);
        Point wangPoint = Point.of(9, 5);
        Point destinationPoint = Point.of(9, 4);

        Map<Point, Piece> pieceByPoint = new HashMap<>();
        pieceByPoint.put(wangPoint, wang);
        pieceByPoint.put(destinationPoint, new Cha(wangTeam.inverse()));
        Board board = BoardFixture.createTestBoard(pieceByPoint);

        // when
        final boolean actual = board.canMove(wangPoint, destinationPoint);

        // then
        Assertions.assertThat(actual).isTrue();
    }

    @Test
    void 왕은_빈칸이_있는_위치로_갈_수_있다() {
        // given
        Team wangTeam = Team.CHO;
        Piece wang = new Wang(wangTeam);
        Point wangPoint = Point.of(9, 5);
        Point destinationPoint = Point.of(9, 4);

        Map<Point, Piece> pieceByPoint = new HashMap<>();
        pieceByPoint.put(wangPoint, wang);
        Board board = BoardFixture.createTestBoard(pieceByPoint);

        // when
        final boolean actual = board.canMove(wangPoint, destinationPoint);

        // then
        Assertions.assertThat(actual).isTrue();
    }

    @Test
    void 왕은_본인_팀의_기물이_있는_위치로_갈_수_없다() {
        // given
        Team wangTeam = Team.CHO;
        Piece wang = new Wang(wangTeam);
        Point wangPoint = Point.of(9, 5);
        Point destinationPoint = Point.of(9, 4);

        Map<Point, Piece> pieceByPoint = new HashMap<>();
        pieceByPoint.put(wangPoint, wang);
        pieceByPoint.put(destinationPoint, new Cha(wangTeam));
        Board board = BoardFixture.createTestBoard(pieceByPoint);

        // when
        final boolean actual = board.canMove(wangPoint, destinationPoint);

        // then
        Assertions.assertThat(actual).isFalse();
    }
}
