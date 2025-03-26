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

@DisplayName("상 테스트")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class SangTest {

    @Nested
    @DisplayName("예외가 발생하지 않는 테스트")
    class Success {

        @Test
        void 상은_상_타입이다() {
            // given
            Piece piece = new Sang(Team.CHO);

            // when & then
            assertThat(piece.type()).isEqualTo(PieceType.SANG);
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

            // when & then
            assertThatCode(() -> sang.validateMove(sourceNode, destinationNode, board))
                    .doesNotThrowAnyException();
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

            // when & then
            assertThatCode(() -> sang.validateMove(sourceNode, destinationNode, board))
                    .doesNotThrowAnyException();
        }
    }

    @Nested
    @DisplayName("에외가 발생하는 테스트")
    class Fail {

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

            // when & then
            assertThatThrownBy(() -> sang.validateMove(sourceNode, destinationNode, board))
                    .isInstanceOf(IllegalArgumentException.class);
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

            // when & then
            assertThatThrownBy(() -> sang.validateMove(sourceNode, destinationNode, board))
                    .isInstanceOf(IllegalArgumentException.class);
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

            // when & then
            assertThatThrownBy(() -> sang.validateMove(sourceNode, destinationNode, board))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}
