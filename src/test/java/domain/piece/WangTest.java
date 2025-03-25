package domain.piece;

import domain.board.Board;
import domain.piece.character.PieceType;
import domain.piece.character.Team;
import domain.point.Point;
import fixture.BoardFixture;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class WangTest {

    private static Stream<Arguments> moveInPalaceTestCases() {
        return Stream.of(
                Arguments.arguments(Point.of(2, 5), Point.of(1, 4)),
                Arguments.arguments(Point.of(2, 5), Point.of(1, 5)),
                Arguments.arguments(Point.of(2, 5), Point.of(1, 6)),
                Arguments.arguments(Point.of(2, 5), Point.of(2, 4)),
                Arguments.arguments(Point.of(2, 5), Point.of(2, 6)),
                Arguments.arguments(Point.of(2, 5), Point.of(3, 4)),
                Arguments.arguments(Point.of(2, 5), Point.of(3, 5)),
                Arguments.arguments(Point.of(2, 5), Point.of(3, 6)),

                Arguments.arguments(Point.of(1, 4), Point.of(2, 5)),
                Arguments.arguments(Point.of(1, 5), Point.of(2, 5)),
                Arguments.arguments(Point.of(1, 6), Point.of(2, 5)),
                Arguments.arguments(Point.of(2, 4), Point.of(2, 5)),
                Arguments.arguments(Point.of(2, 6), Point.of(2, 5)),
                Arguments.arguments(Point.of(3, 4), Point.of(2, 5)),
                Arguments.arguments(Point.of(3, 5), Point.of(2, 5)),
                Arguments.arguments(Point.of(3, 6), Point.of(2, 5))
        );
    }

    private static Stream<Arguments> moveOutOfPalaceTestCases() {
        return Stream.of(
                Arguments.arguments(Point.of(1, 4), Point.of(1, 3)),
                Arguments.arguments(Point.of(2, 4), Point.of(2, 3)),
                Arguments.arguments(Point.of(3, 4), Point.of(3, 3)),
                Arguments.arguments(Point.of(1, 6), Point.of(1, 7)),
                Arguments.arguments(Point.of(2, 6), Point.of(2, 7)),
                Arguments.arguments(Point.of(3, 6), Point.of(3, 7)),

                Arguments.arguments(Point.of(3, 4), Point.of(4, 4)),
                Arguments.arguments(Point.of(3, 5), Point.of(4, 5)),
                Arguments.arguments(Point.of(3, 6), Point.of(4, 6))
        );
    }

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
        final boolean actual = wang.canMove(wangPoint, destinationPoint, board);

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
        final boolean actual = wang.canMove(wangPoint, destinationPoint, board);

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
        final boolean actual = wang.canMove(wangPoint, destinationPoint, board);

        // then
        Assertions.assertThat(actual).isFalse();
    }

    @Test
    void 왕은_궁성_안에서만_움직일_수_있다() {
        // given
        Team wangTeam = Team.CHO;
        Piece wang = new Wang(wangTeam);

        // when
        final boolean actual = wang.isOnlyMovableInPalace();

        // then
        Assertions.assertThat(actual).isTrue();
    }

    @ParameterizedTest(name = "시작: {0} 도착: {1}")
    @MethodSource("moveInPalaceTestCases")
    void 왕은_궁성_중앙에서_대각선으로_이동할_수_있다(Point source, Point destination) {
        // given
        Team wangTeam = Team.CHO;
        Piece wang = new Wang(wangTeam);

        Map<Point, Piece> pieceByPoint = new HashMap<>();
        pieceByPoint.put(source, wang);
        Board board = BoardFixture.createTestBoard(pieceByPoint);

        // when
        final boolean actual = wang.canMove(source, destination, board);

        // then
        Assertions.assertThat(actual).isTrue();
    }

    @ParameterizedTest(name = "시작: {0} 도착: {1}")
    @MethodSource("moveOutOfPalaceTestCases")
    void 왕은_궁성_밖으로_이동할_수_없다(Point source, Point destination) {
        // given
        Team wangTeam = Team.CHO;
        Piece wang = new Wang(wangTeam);

        Map<Point, Piece> pieceByPoint = new HashMap<>();
        pieceByPoint.put(source, wang);
        Board board = BoardFixture.createTestBoard(pieceByPoint);

        // when
        final boolean actual = wang.canMove(source, destination, board);

        // then
        Assertions.assertThat(actual).isFalse();
    }
}
