package domain.piece;

import domain.board.Board;
import domain.board.Point;
import fixture.BoardFixture;
import java.util.HashMap;
import java.util.Map;
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
        Team saTeam = Team.CHO;
        Piece sa = new Sa(saTeam);
        Point saPoint = Point.of(9, 5);
        Point destinationPoint = Point.of(9, 4);

        Map<Point, Piece> pieceByPoint = new HashMap<>();
        pieceByPoint.put(saPoint, sa);
        pieceByPoint.put(destinationPoint, new Cha(saTeam.inverse()));
        Board board = BoardFixture.createTestBoard(pieceByPoint);

        // when
        final boolean actual = board.canMove(saPoint, destinationPoint);

        // then
        Assertions.assertThat(actual).isTrue();
    }

    @Test
    void 사는_빈칸이_있는_위치로_갈_수_있다() {
        // given
        Team saTeam = Team.CHO;
        Piece sa = new Sa(saTeam);
        Point saPoint = Point.of(9, 5);
        Point destinationPoint = Point.of(9, 4);

        Map<Point, Piece> pieceByPoint = new HashMap<>();
        pieceByPoint.put(saPoint, sa);
        Board board = BoardFixture.createTestBoard(pieceByPoint);

        // when
        final boolean actual = board.canMove(saPoint, destinationPoint);

        // then
        Assertions.assertThat(actual).isTrue();
    }

    @Test
    void 사는_본인_팀의_기물이_있는_위치로_갈_수_없다() {
        // given
        Team saTeam = Team.CHO;
        Piece sa = new Sa(saTeam);
        Point saPoint = Point.of(9, 5);
        Point destinationPoint = Point.of(9, 4);

        Map<Point, Piece> pieceByPoint = new HashMap<>();
        pieceByPoint.put(saPoint, sa);
        pieceByPoint.put(destinationPoint, new Cha(saTeam));
        Board board = BoardFixture.createTestBoard(pieceByPoint);

        // when
        final boolean actual = board.canMove(saPoint, destinationPoint);

        // then
        Assertions.assertThat(actual).isFalse();
    }
}