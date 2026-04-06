package model.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import model.board.strategy.InnerElephant;
import model.board.strategy.OuterElephant;
import model.move.Move;
import model.pieces.Piece;
import model.pieces.PieceType;
import model.position.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BoardTest {

    private Board board;

    @BeforeEach()
    void setUp() {
        board = new Board();
        Army cho = new Army(new InnerElephant());
        Army han = new Army(new OuterElephant());
        cho.deployTo(board, Country.CHO);
        han.deployTo(board, Country.HAN);
    }

    @Test
    void 보드에_맞는_위치에_잘_들어갔는지_테스트() {
        Piece expected = new Piece(Country.HAN, PieceType.CHARIOT);
        Position position = Position.of(1, 1);
        assertThat(board.isPieceAt(position, expected)).isTrue();
    }

    @Test
    void 마가_목적지에_잘_들어갔는지_테스트() {
        Piece horse = new Piece(Country.CHO, PieceType.HORSE);
        Move move = Move.of(Position.of(10, 2), Position.of(8, 3));
        board.move(move);
        assertThat(board.isPieceAt(Position.of(8, 3), horse)).isTrue();
    }

    @Test
    void 상이_목적지에_잘_들어갔는지_테스트() {
        Piece elephant = new Piece(Country.HAN, PieceType.ELEPHANT);
        Move move = Move.of(Position.of(1, 2), Position.of(4, 4));
        board.move(move);
        assertThat(board.isPieceAt(Position.of(4, 4), elephant)).isTrue();
    }

    @Test
    void 졸이_목적지에_잘_들어갔는지_테스트() {
        Piece soldier = new Piece(Country.CHO, PieceType.SOLDIER);
        Move move = Move.of(Position.of(7, 1), Position.of(7, 2));
        board.move(move);
        assertThat(board.isPieceAt(Position.of(7, 2), soldier)).isTrue();
    }

    @Test
    void 차가_목적지에_잘_들어갔는지_테스트() {
        Piece chariot = new Piece(Country.CHO, PieceType.CHARIOT);
        Move move = Move.of(Position.of(10, 1), Position.of(8, 1));
        board.move(move);
        assertThat(board.isPieceAt(Position.of(8, 1), chariot)).isTrue();
    }

    @Test
    void 기물이_이동했을_때_차가_목적지에_잘_들어갔는지_테스트() {
        Move soldierMove = Move.of(Position.of(7, 1), Position.of(7, 2));
        board.move(soldierMove);

        Piece chariot = new Piece(Country.CHO, PieceType.CHARIOT);
        Move chariotMove = Move.of(Position.of(10, 1), Position.of(6, 1));
        board.move(chariotMove);

        assertThat(board.isPieceAt(Position.of(6, 1), chariot)).isTrue();
    }

    @Test
    void 사가_목적지에_잘_들어갔는지_테스트() {
        Piece guard = new Piece(Country.CHO, PieceType.GUARD);
        Move move = Move.of(Position.of(10, 4), Position.of(9, 4));
        board.move(move);
        assertThat(board.isPieceAt(Position.of(9, 4), guard)).isTrue();
    }

    @Test
    void 장이_목적지에_잘_들어갔는지_테스트() {
        Piece general = new Piece(Country.CHO, PieceType.GENERAL);
        Move move = Move.of(Position.of(9, 5), Position.of(9, 6));
        board.move(move);
        assertThat(board.isPieceAt(Position.of(9, 6), general)).isTrue();
    }

    @Test
    void 포가_목적지에_잘_들어갔는지_테스트() {
        Move horseMove = Move.of(Position.of(1, 3), Position.of(3, 4));
        board.move(horseMove);

        Piece cannon = new Piece(Country.HAN, PieceType.CANNON);
        Move move = Move.of(Position.of(3, 2), Position.of(3, 5));
        board.move(move);
        assertThat(board.isPieceAt(Position.of(3, 5), cannon)).isTrue();
    }

    @Test
    void 기물이_없는_위치는_이동할_수_없다() {
        Move move = Move.of(Position.of(5, 5), Position.of(5, 6));
        assertThatThrownBy(() -> board.move(move))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 아군_기물이_있는_위치로는_이동할_수_없다() {
        Move move = Move.of(Position.of(10, 1), Position.of(10, 2));

        assertThatThrownBy(() -> board.move(move))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 마는_가는_길에_기물이_있으면_이동할_수_없다() {
        board.place(Position.of(9, 2), new Piece(Country.CHO, PieceType.SOLDIER));
        Move move = Move.of(Position.of(10, 2), Position.of(8, 3));

        assertThatThrownBy(() -> board.move(move))
                .isInstanceOf(IllegalArgumentException.class);
    }


    @Test
    void 상은_가는_길에_기물이_있으면_이동할_수_없다() {
        board.place(Position.of(2, 2), new Piece(Country.HAN, PieceType.SOLDIER));
        Move move = Move.of(Position.of(1, 2), Position.of(4, 4));

        assertThatThrownBy(() -> board.move(move))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 차는_가는_길에_기물이_있으면_이동할_수_없다() {
        Move move = Move.of(Position.of(10, 1), Position.of(6, 1));

        assertThatThrownBy(() -> board.move(move))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 포는_가는_길에_기물이_있으면_이동할_수_없다() {
        Move move = Move.of(Position.of(3, 2), Position.of(3, 5));

        assertThatThrownBy(() -> board.move(move))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 포는_중간_기물이_포이면_이동할_수_없다() {
        board.place(Position.of(3, 4), new Piece(Country.HAN, PieceType.CANNON));
        Move move = Move.of(Position.of(3, 2), Position.of(3, 5));

        assertThatThrownBy(() -> board.move(move))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 포는_포를_잡을_수_없다() {
        Move horseMove = Move.of(Position.of(1, 3), Position.of(3, 4));
        board.move(horseMove);
        board.place(Position.of(3, 5), new Piece(Country.CHO, PieceType.CANNON));

        Move move = Move.of(Position.of(3, 2), Position.of(3, 5));

        assertThatThrownBy(() -> board.move(move))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 포는_상대_기물을_잡을_수_있다() {
        Move horseMove = Move.of(Position.of(1, 3), Position.of(3, 4));
        board.move(horseMove);
        board.place(Position.of(3, 5), new Piece(Country.CHO, PieceType.SOLDIER));

        Piece cannon = new Piece(Country.HAN, PieceType.CANNON);
        Move move = Move.of(Position.of(3, 2), Position.of(3, 5));
        board.move(move);

        assertThat(board.isPieceAt(Position.of(3, 5), cannon)).isTrue();
    }

    @Test
    void 초나라_졸은_뒤로_이동_불가() {
        Move move = Move.of(Position.of(7, 1), Position.of(8, 1));

        assertThatThrownBy(() -> board.move(move))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 한나라_졸은_뒤로_이동_불가() {
        Move move = Move.of(Position.of(4, 1), Position.of(3, 1));

        assertThatThrownBy(() -> board.move(move))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 나라별_남아있는_기물_점수를_계산한다() {
        Board board = new Board();

        board.place(Position.of(10, 1), new Piece(Country.CHO, PieceType.CHARIOT));
        board.place(Position.of(7, 1), new Piece(Country.CHO, PieceType.SOLDIER));
        board.place(Position.of(3, 2), new Piece(Country.HAN, PieceType.CANNON));
        board.place(Position.of(2, 5), new Piece(Country.HAN, PieceType.GENERAL));

        assertThat(board.calculateScore(Country.CHO)).isEqualTo(15.0);
        assertThat(board.calculateScore(Country.HAN)).isEqualTo(8.5);
    }

    @Test
    void 초기_기물배치_초나라의_총점은_72점_한나라의_총점은_73_점_5점이다() {
        assertThat(board.calculateScore(Country.CHO)).isEqualTo(72.0);
        assertThat(board.calculateScore(Country.HAN)).isEqualTo(73.5);
    }
}
