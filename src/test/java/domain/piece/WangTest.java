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

import static org.assertj.core.api.Assertions.*;

@DisplayName("왕 테스트")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class WangTest {

    @Nested
    @DisplayName("예외가 발생하지 않는 테스트")
    class Success {

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

            // when & then
            assertThatCode(() -> wang.validateMove(sourceNode, destinationNode, board))
                    .doesNotThrowAnyException();
        }

        @Test
        void 왕은_궁성_내에서_위로_직진할_수_있다() {
            // given
            Board board = BoardFixture.createEmptyBoard();

            Team wangTeam = Team.CHO;
            Piece wang = new Wang(wangTeam);

            Point wangPoint = Point.of(9, 5);
            Node sourceNode = board.findNodeByPoint(wangPoint);

            Point destinationPoint = Point.of(8, 5);
            Node destinationNode = board.findNodeByPoint(destinationPoint);

            // when & then
            assertThatCode(() -> wang.validateMove(sourceNode, destinationNode, board))
                    .doesNotThrowAnyException();
        }

        @Test
        void 왕은_궁성_내에서_아래로_직진할_수_있다() {
            // given
            Board board = BoardFixture.createEmptyBoard();

            Team wangTeam = Team.CHO;
            Piece wang = new Wang(wangTeam);

            Point wangPoint = Point.of(9, 5);
            Node sourceNode = board.findNodeByPoint(wangPoint);

            Point destinationPoint = Point.of(10, 5);
            Node destinationNode = board.findNodeByPoint(destinationPoint);

            // when & then
            assertThatCode(() -> wang.validateMove(sourceNode, destinationNode, board))
                    .doesNotThrowAnyException();
        }

        @Test
        void 왕은_궁성_내에서_왼쪽으로_직진할_수_있다() {
            // given
            Board board = BoardFixture.createEmptyBoard();

            Team wangTeam = Team.CHO;
            Piece wang = new Wang(wangTeam);

            Point wangPoint = Point.of(9, 5);
            Node sourceNode = board.findNodeByPoint(wangPoint);

            Point destinationPoint = Point.of(9, 4);
            Node destinationNode = board.findNodeByPoint(destinationPoint);

            // when & then
            assertThatCode(() -> wang.validateMove(sourceNode, destinationNode, board))
                    .doesNotThrowAnyException();
        }

        @Test
        void 왕은_궁성_내에서_오른쪽으로_직진할_수_있다() {
            // given
            Board board = BoardFixture.createEmptyBoard();

            Team wangTeam = Team.CHO;
            Piece wang = new Wang(wangTeam);

            Point wangPoint = Point.of(9, 5);
            Node sourceNode = board.findNodeByPoint(wangPoint);

            Point destinationPoint = Point.of(9, 6);
            Node destinationNode = board.findNodeByPoint(destinationPoint);

            // when & then
            assertThatCode(() -> wang.validateMove(sourceNode, destinationNode, board))
                    .doesNotThrowAnyException();
        }

        @Test
        void 왕은_궁성_내에서_왼쪽_위로_이동할_수_있다() {
            // given
            Board board = BoardFixture.createEmptyBoard();

            Team wangTeam = Team.CHO;
            Piece wang = new Wang(wangTeam);

            Point wangPoint = Point.of(9, 5);
            Node sourceNode = board.findNodeByPoint(wangPoint);

            Point destinationPoint = Point.of(8, 4);
            Node destinationNode = board.findNodeByPoint(destinationPoint);

            // when & then
            assertThatCode(() -> wang.validateMove(sourceNode, destinationNode, board))
                    .doesNotThrowAnyException();
        }

        @Test
        void 왕은_궁성_내에서_오른쪽_위로_이동할_수_있다() {
            // given
            Board board = BoardFixture.createEmptyBoard();

            Team wangTeam = Team.CHO;
            Piece wang = new Wang(wangTeam);

            Point wangPoint = Point.of(9, 5);
            Node sourceNode = board.findNodeByPoint(wangPoint);

            Point destinationPoint = Point.of(8, 6);
            Node destinationNode = board.findNodeByPoint(destinationPoint);

            // when & then
            assertThatCode(() -> wang.validateMove(sourceNode, destinationNode, board))
                    .doesNotThrowAnyException();
        }

        @Test
        void 왕은_궁성_내에서_왼쪽_아래로_이동할_수_있다() {
            // given
            Board board = BoardFixture.createEmptyBoard();

            Team wangTeam = Team.CHO;
            Piece wang = new Wang(wangTeam);

            Point wangPoint = Point.of(9, 5);
            Node sourceNode = board.findNodeByPoint(wangPoint);

            Point destinationPoint = Point.of(10, 4);
            Node destinationNode = board.findNodeByPoint(destinationPoint);

            // when & then
            assertThatCode(() -> wang.validateMove(sourceNode, destinationNode, board))
                    .doesNotThrowAnyException();
        }

        @Test
        void 왕은_궁성_내에서_오른쪽_아래로_이동할_수_있다() {
            // given
            Board board = BoardFixture.createEmptyBoard();

            Team wangTeam = Team.CHO;
            Piece wang = new Wang(wangTeam);

            Point wangPoint = Point.of(9, 5);
            Node sourceNode = board.findNodeByPoint(wangPoint);

            Point destinationPoint = Point.of(10, 6);
            Node destinationNode = board.findNodeByPoint(destinationPoint);

            // when & then
            assertThatCode(() -> wang.validateMove(sourceNode, destinationNode, board))
                    .doesNotThrowAnyException();
        }
    }

    @Nested
    @DisplayName("예외가 발생하는 테스트")
    class Fail {

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

            // when & then
            assertThatThrownBy(() -> wang.validateMove(sourceNode, destinationNode, board))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 왕은_궁성_밖으로_이동할_수_없다() {
            // given
            Board board = BoardFixture.createEmptyBoard();

            Piece wang = new Wang(Team.CHO);

            Point wangPoint = Point.of(9, 4);
            Node sourceNode = board.findNodeByPoint(wangPoint);

            Point destinationPoint = Point.of(9, 3);
            Node destinationNode = board.findNodeByPoint(destinationPoint);

            // when & then
            assertThatThrownBy(() -> wang.validateMove(sourceNode, destinationNode, board))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}
