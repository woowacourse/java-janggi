package domain.board;

import domain.PieceType;
import domain.piece.Byeong;
import domain.piece.Po;
import domain.piece.Team;
import fixture.BoardFixture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class BoardTest {

    @Nested
    @DisplayName("예외가 발생하지 않는 테스트")
    class Success {

        @Test
        void 특정_노드에_기물이_존재하면_true를_반환한다() {
            // given
            Point point = Point.of(1, 1);
            Node node = new Node(point);
            Board board = BoardFixture.createEmptyBoard();
            board.putPiece(node, new Byeong(Team.CHO));

            // when
            final boolean actual = board.existsPieceByNode(node);

            // then
            Assertions.assertThat(actual).isTrue();
        }

        @Test
        void 특정_노드에_기물이_존재하지_않으면_false를_반환한다() {
            // given
            Point point = Point.of(1, 1);
            Node node = new Node(point);
            Board board = BoardFixture.createEmptyBoard();
            board.putPiece(node, new Byeong(Team.CHO));
            Node otherNode = board.findNodeByPoint(point.of(5, 5));

            // when
            final boolean actual = board.existsPieceByNode(otherNode);

            // then
            Assertions.assertThat(actual).isFalse();
        }

        @Test
        void 특정_노드에_존재하는_기물을_제거한다() {
            // given
            Point point = Point.of(1, 1);
            Node node = new Node(point);
            Board board = BoardFixture.createEmptyBoard();
            board.putPiece(node, new Byeong(Team.CHO));

            // when
            board.removePieceByNode(node);
            final boolean actual = board.existsPieceByNode(node);

            // then
            Assertions.assertThat(actual).isFalse();
        }

        @Test
        void 특정_노드에_특정_팀인_기물이_존재하면_true를_반환한다() {
            // given
            Team team = Team.CHO;
            Point point = Point.of(1, 1);
            Node node = new Node(point);
            Board board = BoardFixture.createEmptyBoard();
            board.putPiece(node, new Byeong(team));

            // when
            final boolean actual = board.hasTeamPieceByNode(node, team);

            // then
            Assertions.assertThat(actual).isTrue();
        }

        @Test
        void 특정_노드에_특정_팀인_기물이_존재하지_않으면_false를_반환한다() {
            // given
            Team team = Team.CHO;
            Point point = Point.of(1, 1);
            Node node = new Node(point);
            Board board = BoardFixture.createEmptyBoard();
            board.putPiece(node, new Byeong(team.inverse()));

            // when

            final boolean actual = board.hasTeamPieceByNode(node, team);

            // then
            Assertions.assertThat(actual).isFalse();
        }

        @Test
        void 특정_노드에_특정_종류의_기물이_존재하면_true를_반환한다() {
            // given
            Team team = Team.CHO;
            PieceType pieceType = PieceType.PO;
            Point point = Point.of(1, 1);
            Node node = new Node(point);
            Board board = BoardFixture.createEmptyBoard();

            board.putPiece(node, new Po(team.inverse()));

            // when
            final boolean actual = board.hasTypeByNode(node, pieceType);

            // then
            Assertions.assertThat(actual).isTrue();
        }

        @Test
        void 특정_노드에_특정_종류의_기물이_존재하지_않으면_false를_반환한다() {
            // given
            Team team = Team.CHO;
            PieceType pieceType = PieceType.PO;
            Point point = Point.of(1, 1);
            Node node = new Node(point);
            Board board = BoardFixture.createEmptyBoard();

            board.putPiece(node, new Byeong(team.inverse()));

            // when
            final boolean actual = board.hasTypeByNode(node, pieceType);

            // then
            Assertions.assertThat(actual).isFalse();
        }
    }

    @Nested
    @DisplayName("예외가 발생하는 테스트")
    class Fail {

        @Test
        void 특정_노드에_기물이_없으면_예외가_발생한다() {
            // given
            Point point = Point.of(1, 1);
            Node node = new Node(point);
            Board board = BoardFixture.createEmptyBoard();

            // when & then
            Assertions.assertThatThrownBy(() -> board.findPieceByNode(node))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}
