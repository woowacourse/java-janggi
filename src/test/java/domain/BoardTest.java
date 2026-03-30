package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class BoardTest {

    @Test
    void 장기판은_올바른_위치에_기물을_초기화한다() {
        Board board = InitialBoardFactory.create(Formation.LEFT_ELEPHANT, Formation.RIGHT_ELEPHANT);
        Map<Position, Piece> actual = board.getBoard();

        assertThat(actual).hasSize(32);
        assertThat(actual.get(new Position(4, 1))).isInstanceOf(General.class);
        assertThat(actual.get(new Position(4, 8))).isInstanceOf(General.class);

        assertThat(actual.get(new Position(0, 0))).isInstanceOf(Chariot.class);
        assertThat(actual.get(new Position(8, 0))).isInstanceOf(Chariot.class);
        assertThat(actual.get(new Position(0, 9))).isInstanceOf(Chariot.class);
        assertThat(actual.get(new Position(8, 9))).isInstanceOf(Chariot.class);

        assertThat(actual.get(new Position(1, 0))).isInstanceOf(domain.Elephant.class);
        assertThat(actual.get(new Position(2, 0))).isInstanceOf(domain.Horse.class);
        assertThat(actual.get(new Position(6, 0))).isInstanceOf(domain.Elephant.class);
        assertThat(actual.get(new Position(7, 0))).isInstanceOf(domain.Horse.class);

        assertThat(actual.get(new Position(7, 9))).isInstanceOf(domain.Horse.class);
        assertThat(actual.get(new Position(6, 9))).isInstanceOf(domain.Elephant.class);
        assertThat(actual.get(new Position(2, 9))).isInstanceOf(domain.Horse.class);
        assertThat(actual.get(new Position(1, 9))).isInstanceOf(domain.Elephant.class);
    }

    @Test
    void 기물을_이동하면_출발_위치는_비고_도착_위치로_옮겨진다() {
        Map<Position, Piece> pieces = new HashMap<>();
        Position from = new Position(0, 3);
        Position to = new Position(0, 4);
        Piece soldier = new Soldier(Side.CHO);
        pieces.put(from, soldier);
        Board board = new Board(pieces);

        board.movePiece(from, to);

        assertThat(board.getBoard()).doesNotContainKey(from);
        assertThat(board.getBoard().get(to)).isSameAs(soldier);
    }

    @Test
    void 양쪽_장군이_모두_있으면_게임은_종료되지_않는다() {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(4, 1), new General(Side.CHO));
        pieces.put(new Position(4, 8), new General(Side.HAN));
        Board board = new Board(pieces);

        assertThat(board.isGameOver()).isFalse();
    }

    @Test
    void 장군이_하나만_남으면_게임이_종료된다() {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(4, 1), new General(Side.CHO));
        Board board = new Board(pieces);

        assertThat(board.isGameOver()).isTrue();
    }
}
