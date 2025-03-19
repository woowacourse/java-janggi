package domain.board;

import domain.piece.Byeong;
import domain.piece.Team;
import fixture.BoardFixture;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class BoardTest {

    @Test
    void 해당_좌표에_기물이_존재하면_true를_반환한다() {
        // given
        Point point = Point.of(1, 1);
        Node node = new Node(point, List.of());
        Board board = BoardFixture.createEmptyBoard();
        board.putPiece(node, new Byeong(Team.CHO));

        // when
        final boolean actual = board.existsPiece(node);

        // then
        Assertions.assertThat(actual).isTrue();
    }

    @Test
    void 해당_좌표에_기물이_존재하지_않으면_false를_반환한다() {
        // given
        Point point = Point.of(1, 1);
        Node node = new Node(point, List.of());
        Board board = BoardFixture.createEmptyBoard();
        board.putPiece(node, new Byeong(Team.CHO));
        Node otherNode = board.findNodeByPoint(point.of(5, 5));

        // when
        final boolean actual = board.existsPiece(otherNode);

        // then
        Assertions.assertThat(actual).isFalse();
    }
}