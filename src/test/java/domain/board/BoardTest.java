package domain.board;

import domain.piece.Piece;
import domain.piece.Po;
import domain.piece.Team;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class BoardTest {

    @Test
    void 해당_좌표에_기물이_존재하면_true를_반환한다() {
        // given
        Point point = Point.of(1, 1);
        Node node = new Node(point, List.of());
        Map<Point, Node> nodeByPoint = Map.ofEntries(
                Map.entry(point, node)
        );
        Map<Node, Piece> pieceByNode = Map.ofEntries(
                Map.entry(node, new Po(Team.CHO))
        );
        Board board = new Board(pieceByNode, nodeByPoint);

        // when
        final boolean actual = board.existsPiece(point);

        // then
        Assertions.assertThat(actual).isTrue();
    }

    @Test
    void 해당_좌표에_기물이_존재하지_않으면_false를_반환한다() {
        // given
        Point point = Point.of(1, 1);
        Node node = new Node(point, List.of());
        Map<Point, Node> nodeByPoint = Map.ofEntries(
                Map.entry(point, node)
        );
        Map<Node, Piece> pieceByNode = Map.ofEntries(
                Map.entry(node, new Po(Team.CHO))
        );
        Board board = new Board(pieceByNode, nodeByPoint);

        // when
        final boolean actual = board.existsPiece(point.of(5, 5));

        // then
        Assertions.assertThat(actual).isFalse();
    }
}