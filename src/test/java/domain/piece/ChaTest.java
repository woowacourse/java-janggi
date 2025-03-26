package domain.piece;

import domain.board.Board;
import domain.board.Node;
import domain.board.Point;
import fixture.BoardFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

@DisplayName("차 테스트")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ChaTest {

    @Nested
    @DisplayName("예외가 발생하지 않는 테스트")
    class Success {

        @Test
        void 차는_차_타입이다() {
            // given
            Piece piece = new Cha(Team.CHO);

            // when & then
            assertThat(piece.type()).isEqualTo(PieceType.CHA);
        }

        @Test
        void 차는_위쪽에_있는_적_기물의_위치로_갈_수_있다() {
            // given
            Board board = BoardFixture.createEmptyBoard();

            Team chaTeam = Team.CHO;
            Piece cha = new Cha(chaTeam);

            Point chaPoint = Point.of(10, 1);
            Node sourceNode = board.findNodeByPoint(chaPoint);

            Point destinationPoint = Point.of(1, 1);
            Node destinationNode = board.findNodeByPoint(destinationPoint);

            board.putPiece(destinationNode, new Cha(chaTeam.inverse()));

            // when & then
            assertThatCode(() -> cha.validateMove(sourceNode, destinationNode, board))
                    .doesNotThrowAnyException();
        }

        @Test
        void 차는_위쪽에_있는_빈칸으로_갈_수_있다() {
            // given
            Board board = BoardFixture.createEmptyBoard();

            Team chaTeam = Team.CHO;
            Piece cha = new Cha(chaTeam);

            Point chaPoint = Point.of(10, 1);
            Node sourceNode = board.findNodeByPoint(chaPoint);

            Point destinationPoint = Point.of(1, 1);
            Node destinationNode = board.findNodeByPoint(destinationPoint);

            // when & then
            assertThatCode(() -> cha.validateMove(sourceNode, destinationNode, board))
                    .doesNotThrowAnyException();
        }

        @Test
        void 차는_오른쪽에_있는_적_기물의_위치로_갈_수_있다() {
            // given
            Board board = BoardFixture.createEmptyBoard();

            Team chaTeam = Team.CHO;
            Piece cha = new Cha(chaTeam);

            Point chaPoint = Point.of(10, 1);
            Node sourceNode = board.findNodeByPoint(chaPoint);

            Point destinationPoint = Point.of(10, 9);
            Node destinationNode = board.findNodeByPoint(destinationPoint);

            board.putPiece(destinationNode, new Cha(chaTeam.inverse()));

            // when & then
            assertThatCode(() -> cha.validateMove(sourceNode, destinationNode, board))
                    .doesNotThrowAnyException();
        }

        @Test
        void 차는_오른쪽에_있는_빈칸으로_갈_수_있다() {
            // given
            Board board = BoardFixture.createEmptyBoard();

            Team chaTeam = Team.CHO;
            Piece cha = new Cha(chaTeam);

            Point chaPoint = Point.of(10, 1);
            Node sourceNode = board.findNodeByPoint(chaPoint);

            Point destinationPoint = Point.of(10, 9);
            Node destinationNode = board.findNodeByPoint(destinationPoint);

            // when & then
            assertThatCode(() -> cha.validateMove(sourceNode, destinationNode, board))
                    .doesNotThrowAnyException();
        }

        @Test
        void 차는_아래쪽에_있는_적_기물의_위치로_갈_수_있다() {
            // given
            Board board = BoardFixture.createEmptyBoard();

            Team chaTeam = Team.CHO;
            Piece cha = new Cha(chaTeam);

            Point chaPoint = Point.of(1, 1);
            Node sourceNode = board.findNodeByPoint(chaPoint);

            Point destinationPoint = Point.of(10, 1);
            Node destinationNode = board.findNodeByPoint(destinationPoint);

            board.putPiece(destinationNode, new Cha(chaTeam.inverse()));

            // when & then
            assertThatCode(() -> cha.validateMove(sourceNode, destinationNode, board))
                    .doesNotThrowAnyException();
        }

        @Test
        void 차는_아래쪽에_있는_빈칸으로_갈_수_있다() {
            // given
            Board board = BoardFixture.createEmptyBoard();

            Team chaTeam = Team.CHO;
            Piece cha = new Cha(chaTeam);

            Point chaPoint = Point.of(1, 1);
            Node sourceNode = board.findNodeByPoint(chaPoint);

            Point destinationPoint = Point.of(10, 1);
            Node destinationNode = board.findNodeByPoint(destinationPoint);

            // when & then
            assertThatCode(() -> cha.validateMove(sourceNode, destinationNode, board))
                    .doesNotThrowAnyException();
        }

        @Test
        void 차는_왼쪽에_있는_적_기물의_위치로_갈_수_있다() {
            // given
            Board board = BoardFixture.createEmptyBoard();

            Team chaTeam = Team.CHO;
            Piece cha = new Cha(chaTeam);

            Point chaPoint = Point.of(10, 9);
            Node sourceNode = board.findNodeByPoint(chaPoint);

            Point destinationPoint = Point.of(10, 1);
            Node destinationNode = board.findNodeByPoint(destinationPoint);

            board.putPiece(destinationNode, new Cha(chaTeam.inverse()));

            // when & then
            assertThatCode(() -> cha.validateMove(sourceNode, destinationNode, board))
                    .doesNotThrowAnyException();
        }

        @Test
        void 차는_왼쪽에_있는_빈칸으로_갈_수_있다() {
            // given
            Board board = BoardFixture.createEmptyBoard();

            Team chaTeam = Team.CHO;
            Piece cha = new Cha(chaTeam);

            Point chaPoint = Point.of(10, 9);
            Node sourceNode = board.findNodeByPoint(chaPoint);

            Point destinationPoint = Point.of(10, 1);
            Node destinationNode = board.findNodeByPoint(destinationPoint);

            // when & then
            assertThatCode(() -> cha.validateMove(sourceNode, destinationNode, board))
                    .doesNotThrowAnyException();
        }

        @ParameterizedTest(name = "{index} : {2}")
        @MethodSource("getDiagonalPoints")
        void 차는_궁성_내에서_대각선으로_직진할_수_있다(Point chaPoint, Point destinationPoint, String testName) {
            // given
            Board board = BoardFixture.createEmptyBoard();

            Piece cha = new Cha(Team.CHO);

            Node sourceNode = board.findNodeByPoint(chaPoint);
            Node destinationNode = board.findNodeByPoint(destinationPoint);

            // when & then
            assertThatCode(() -> cha.validateMove(sourceNode, destinationNode, board))
                    .doesNotThrowAnyException();
        }

        static Stream<Arguments> getDiagonalPoints() {
            return Stream.of(
                    Arguments.of(Point.of(10, 6), Point.of(8, 4), "왼쪽 위"),
                    Arguments.of(Point.of(10, 4), Point.of(8, 6), "오른쪽 위"),
                    Arguments.of(Point.of(8, 6), Point.of(10, 4), "왼쪽 아래"),
                    Arguments.of(Point.of(8, 4), Point.of(10, 6), "오른쪽 아래")
            );
        }
    }


    @Nested
    @DisplayName("에외가 발생하는 테스트")
    class Fail {

        @Test
        void 차는_위쪽에_있는_본인_팀_기물의_위치로_갈_수_없다() {
            // given
            Board board = BoardFixture.createEmptyBoard();

            Team chaTeam = Team.CHO;
            Piece cha = new Cha(chaTeam);

            Point chaPoint = Point.of(10, 1);
            Node sourceNode = board.findNodeByPoint(chaPoint);

            Point destinationPoint = Point.of(1, 1);
            Node destinationNode = board.findNodeByPoint(destinationPoint);

            board.putPiece(destinationNode, new Cha(chaTeam));

            // when & then
            assertThatThrownBy(() -> cha.validateMove(sourceNode, destinationNode, board))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 차는_오른쪽에_있는_본인_팀_기물의_위치로_갈_수_없다() {
            // given
            Board board = BoardFixture.createEmptyBoard();

            Team chaTeam = Team.CHO;
            Piece cha = new Cha(chaTeam);

            Point chaPoint = Point.of(10, 1);
            Node sourceNode = board.findNodeByPoint(chaPoint);

            Point destinationPoint = Point.of(10, 9);
            Node destinationNode = board.findNodeByPoint(destinationPoint);

            board.putPiece(destinationNode, new Cha(chaTeam));

            // when & then
            assertThatThrownBy(() -> cha.validateMove(sourceNode, destinationNode, board))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 차는_아래쪽에_있는_본인_팀_기물의_위치로_갈_수_없다() {
            // given
            Board board = BoardFixture.createEmptyBoard();

            Team chaTeam = Team.CHO;
            Piece cha = new Cha(chaTeam);

            Point chaPoint = Point.of(1, 1);
            Node sourceNode = board.findNodeByPoint(chaPoint);

            Point destinationPoint = Point.of(10, 1);
            Node destinationNode = board.findNodeByPoint(destinationPoint);

            board.putPiece(destinationNode, new Cha(chaTeam));

            // when & then
            assertThatThrownBy(() -> cha.validateMove(sourceNode, destinationNode, board))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 차는_왼쪽에_있는_본인_팀_기물의_위치로_갈_수_없다() {
            // given
            Board board = BoardFixture.createEmptyBoard();

            Team chaTeam = Team.CHO;
            Piece cha = new Cha(chaTeam);

            Point chaPoint = Point.of(10, 9);
            Node sourceNode = board.findNodeByPoint(chaPoint);

            Point destinationPoint = Point.of(10, 1);
            Node destinationNode = board.findNodeByPoint(destinationPoint);

            board.putPiece(destinationNode, new Cha(chaTeam));

            // when & then
            assertThatThrownBy(() -> cha.validateMove(sourceNode, destinationNode, board))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @ParameterizedTest(name = "{index} : {2}")
        @MethodSource("getDiagonalPoints")
        void 차는_궁성_밖에서_대각선으로_직진할_수_없다(Point chaPoint, Point destinationPoint, String testName) {
            // given
            Board board = BoardFixture.createEmptyBoard();

            Piece cha = new Cha(Team.CHO);

            Node sourceNode = board.findNodeByPoint(chaPoint);
            Node destinationNode = board.findNodeByPoint(destinationPoint);

            // when & then
            assertThatThrownBy(() -> cha.validateMove(sourceNode, destinationNode, board))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        static Stream<Arguments> getDiagonalPoints() {
            return Stream.of(
                    Arguments.of(Point.of(10, 9), Point.of(1, 1), "왼쪽 위"),
                    Arguments.of(Point.of(10, 1), Point.of(1, 9), "오른쪽 위"),
                    Arguments.of(Point.of(1, 1), Point.of(10, 9), "왼쪽 아래"),
                    Arguments.of(Point.of(1, 9), Point.of(10, 1), "오른쪽 아래")
            );
        }
    }
}