package domain.board;

import domain.piece.Byeong;
import domain.piece.PieceType;
import domain.piece.Po;
import domain.piece.Sang;
import domain.piece.Team;
import domain.piece.Wang;
import domain.score.Score;
import domain.score.ScoreCalculator;
import fixture.BoardFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("보드 테스트")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class BoardTest {

    @Nested
    @DisplayName("예외가 발생하지 않는 테스트")
    class Success {

        @Test
        void 특정_위치에_기물이_존재하면_true를_반환한다() {
            // given
            Point point = Point.of(1, 1);
            Node node = new Node(point);
            Board board = BoardFixture.createEmptyBoard();
            board.putPiece(node, new Byeong(Team.CHO));

            // when
            final boolean actual = board.existsPieceByNode(node);

            // then
            assertThat(actual).isTrue();
        }

        @Test
        void 특정_위치에_기물이_존재하지_않으면_false를_반환한다() {
            // given
            Point point = Point.of(1, 1);
            Node node = new Node(point);
            Board board = BoardFixture.createEmptyBoard();
            board.putPiece(node, new Byeong(Team.CHO));
            Node otherNode = board.findNodeByPoint(Point.of(5, 5));

            // when
            final boolean actual = board.existsPieceByNode(otherNode);

            // then
            assertThat(actual).isFalse();
        }

        @Test
        void 특정_위치에_존재하는_기물을_제거한다() {
            // given
            Point point = Point.of(1, 1);
            Node node = new Node(point);
            Board board = BoardFixture.createEmptyBoard();
            board.putPiece(node, new Byeong(Team.CHO));

            // when
            board.removePieceByNode(node);
            final boolean actual = board.existsPieceByNode(node);

            // then
            assertThat(actual).isFalse();
        }

        @Test
        void 특정_위치에_특정_팀인_기물이_존재하면_true를_반환한다() {
            // given
            Team team = Team.CHO;
            Point point = Point.of(1, 1);
            Node node = new Node(point);
            Board board = BoardFixture.createEmptyBoard();
            board.putPiece(node, new Byeong(team));

            // when
            final boolean actual = board.hasPieceTeamByNode(node, team);

            // then
            assertThat(actual).isTrue();
        }

        @Test
        void 특정_위치에_특정_팀인_기물이_존재하지_않으면_false를_반환한다() {
            // given
            Team team = Team.CHO;
            Point point = Point.of(1, 1);
            Node node = new Node(point);
            Board board = BoardFixture.createEmptyBoard();
            board.putPiece(node, new Byeong(team.inverse()));

            // when

            final boolean actual = board.hasPieceTeamByNode(node, team);

            // then
            assertThat(actual).isFalse();
        }

        @Test
        void 특정_위치에_특정_종류의_기물이_존재하면_true를_반환한다() {
            // given
            Team team = Team.CHO;
            PieceType pieceType = PieceType.PO;
            Point point = Point.of(1, 1);
            Node node = new Node(point);
            Board board = BoardFixture.createEmptyBoard();

            board.putPiece(node, new Po(team.inverse()));

            // when
            final boolean actual = board.hasPieceTypeByNode(node, pieceType);

            // then
            assertThat(actual).isTrue();
        }

        @Test
        void 특정_위치에_특정_종류의_기물이_존재하지_않으면_false를_반환한다() {
            // given
            Team team = Team.CHO;
            PieceType pieceType = PieceType.PO;
            Point point = Point.of(1, 1);
            Node node = new Node(point);
            Board board = BoardFixture.createEmptyBoard();

            board.putPiece(node, new Byeong(team.inverse()));

            // when
            final boolean actual = board.hasPieceTypeByNode(node, pieceType);

            // then
            assertThat(actual).isFalse();
        }

        @Test
        void 보드에_상대팀의_왕이_없다면_true를_반환한다() {
            // given
            Team team = Team.CHO;
            Point point = Point.of(2, 5);
            Node node = new Node(point);
            Board board = BoardFixture.createEmptyBoard();

            board.putPiece(node, new Byeong(team.inverse()));

            // when
            final boolean actual = board.isOpponentWangDead(team);

            // then
            assertThat(actual).isTrue();
        }

        @Test
        void 보드에_상대팀의_왕이_있다면_false를_반환한다() {
            // given
            Team team = Team.CHO;
            Point point = Point.of(2, 5);
            Node node = new Node(point);
            Board board = BoardFixture.createEmptyBoard();

            board.putPiece(node, new Wang(team.inverse()));

            // when
            final boolean actual = board.isOpponentWangDead(team);

            // then
            assertThat(actual).isFalse();
        }

        @Test
        void 해당_위치가_궁성_내부라면_true를_반환한다() {
            // given
            Point point = Point.of(1, 4);
            Node node = new Node(point);
            Board board = BoardFixture.createEmptyBoard();

            // when
            final boolean actual = board.isPalaceArea(node);

            // then
            assertThat(actual).isTrue();
        }

        @Test
        void 해당_위치가_궁성_내부가_아니라면_false를_반환한다() {
            // given
            Point point = Point.of(1, 3);
            Node node = new Node(point);
            Board board = BoardFixture.createEmptyBoard();

            // when
            final boolean actual = board.isPalaceArea(node);

            // then
            assertThat(actual).isFalse();
        }

        @Test
        void 팀별_기물들의_총점수를_반환한다() {
            // given
            ScoreCalculator scoreCalculator = new ScoreCalculator();
            Board board = BoardFixture.createEmptyBoard();

            Point pointOfHan = Point.of(1, 1);
            Node nodeOfHan = new Node(pointOfHan);
            board.putPiece(nodeOfHan, new Po(Team.HAN));

            Point pointOfCho = Point.of(10, 1);
            Node nodeOfCho = new Node(pointOfCho);
            board.putPiece(nodeOfCho, new Sang(Team.CHO));

            // when
            final Map<Team, Score> actual = board.calculateTotalScoreByTeam(scoreCalculator);

            // then
            assertThat(actual).isEqualTo(Map.ofEntries(
                    Map.entry(Team.HAN, PieceType.PO.score().plus(ScoreCalculator.HAN_BONUS_SCORE)),
                    Map.entry(Team.CHO, PieceType.SANG.score())
            ));
        }
    }

    @Nested
    @DisplayName("예외가 발생하는 테스트")
    class Fail {

        @Test
        void 특정_위치에_기물이_없으면_예외가_발생한다() {
            // given
            Point point = Point.of(1, 1);
            Node node = new Node(point);
            Board board = BoardFixture.createEmptyBoard();

            // when & then
            assertThatThrownBy(() -> board.findPieceByNode(node))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 같은_위치로_이동하면_예외가_발생한다() {
            // given
            Team team = Team.CHO;
            Point point = Point.of(10, 1);
            Node sourceNode = new Node(point);
            Board board = BoardFixture.createEmptyBoard();

            board.putPiece(sourceNode, new Byeong(team));

            // when
            Node destinationNode = new Node(point);

            // then
            assertThatThrownBy(() -> board.movePiece(sourceNode, destinationNode, board))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}
