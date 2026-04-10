package janggi.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import janggi.domain.board.Board;
import janggi.domain.position.Position;
import janggi.domain.space.Space;
import janggi.domain.space.piece.Byeong;
import janggi.domain.space.piece.Cha;
import janggi.domain.space.piece.King;
import janggi.domain.space.piece.Ma;
import janggi.domain.space.piece.Pho;
import janggi.domain.space.piece.Piece;
import janggi.domain.space.piece.Sa;
import janggi.domain.space.piece.Sang;
import janggi.domain.space.piece.Team;
import janggi.domain.strategy.BasicPlacementStrategy;
import janggi.domain.strategy.InitializeStrategy;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BoardTest {

    InitializeStrategy strategy;
    Map<Position, Space> board;

    @BeforeEach
    public void setUp() {
        strategy = new BasicPlacementStrategy();
        board = new Board(strategy).getPiecesInfo();
    }

    @Test
    void 보드_초기화_정상_테스트() {

        assertDoesNotThrow(() -> new Board(strategy));
    }


    @Test
    void 차_위치_정상_테스트() {

        Piece cha = new Cha(Team.HAN);

        Piece piece = (Piece) board.get(new Position(0, 0));
        boolean actual = piece.isSameType(cha);
        assertThat(actual).isTrue();

        piece = (Piece) board.get(new Position(8, 0));
        actual = piece.isSameType(cha);
        assertThat(actual).isTrue();

        piece = (Piece) board.get(new Position(0, 9));
        actual = piece.isSameType(cha);
        assertThat(actual).isTrue();

        piece = (Piece) board.get(new Position(8, 9));
        actual = piece.isSameType(cha);
        assertThat(actual).isTrue();
    }

    @Test
    void 포_위치_정상_테스트() {

        Piece pho = new Pho(Team.HAN);

        Piece piece = (Piece) board.get(new Position(1, 2));
        boolean actual = piece.isSameType(pho);
        assertThat(actual).isTrue();

        piece = (Piece) board.get(new Position(7, 2));
        actual = piece.isSameType(pho);
        assertThat(actual).isTrue();

        piece = (Piece) board.get(new Position(1, 7));
        actual = piece.isSameType(pho);
        assertThat(actual).isTrue();

        piece = (Piece) board.get(new Position(7, 7));
        actual = piece.isSameType(pho);
        assertThat(actual).isTrue();
    }

    @Test
    void 마_위치_정상_테스트() {

        Piece ma = new Ma(Team.HAN);

        Piece piece = (Piece) board.get(new Position(1, 0));
        boolean actual = piece.isSameType(ma);
        assertThat(actual).isTrue();

        piece = (Piece) board.get(new Position(7, 0));
        actual = piece.isSameType(ma);
        assertThat(actual).isTrue();

        piece = (Piece) board.get(new Position(1, 9));
        actual = piece.isSameType(ma);
        assertThat(actual).isTrue();

        piece = (Piece) board.get(new Position(7, 9));
        actual = piece.isSameType(ma);
        assertThat(actual).isTrue();
    }

    @Test
    void 상_위치_정상_테스트() {

        Piece sang = new Sang(Team.HAN);

        Piece piece = (Piece) board.get(new Position(2, 0));
        boolean actual = piece.isSameType(sang);
        assertThat(actual).isTrue();

        piece = (Piece) board.get(new Position(6, 0));
        actual = piece.isSameType(sang);
        assertThat(actual).isTrue();

        piece = (Piece) board.get(new Position(2, 9));
        actual = piece.isSameType(sang);
        assertThat(actual).isTrue();

        piece = (Piece) board.get(new Position(6, 9));
        actual = piece.isSameType(sang);
        assertThat(actual).isTrue();
    }

    @Test
    void 사_위치_정상_테스트() {

        Piece sa = new Sa(Team.HAN);

        Piece piece = (Piece) board.get(new Position(3, 0));
        boolean actual = piece.isSameType(sa);
        assertThat(actual).isTrue();

        piece = (Piece) board.get(new Position(5, 0));
        actual = piece.isSameType(sa);
        assertThat(actual).isTrue();

        piece = (Piece) board.get(new Position(3, 9));
        actual = piece.isSameType(sa);
        assertThat(actual).isTrue();

        piece = (Piece) board.get(new Position(5, 9));
        actual = piece.isSameType(sa);
        assertThat(actual).isTrue();
    }

    @Test
    void 궁_위치_정상_테스트() {

        Piece king = new King(Team.HAN);

        Piece piece = (Piece) board.get(new Position(4, 1));
        boolean actual = piece.isSameType(king);
        assertThat(actual).isTrue();

        piece = (Piece) board.get(new Position(4, 8));
        actual = piece.isSameType(king);
        assertThat(actual).isTrue();
    }

    @Test
    void 병_위치_정상_테스트() {

        Piece byeong = new Byeong(Team.HAN);

        Piece piece = (Piece) board.get(new Position(0, 3));
        boolean actual = piece.isSameType(byeong);
        assertThat(actual).isTrue();

        piece = (Piece) board.get(new Position(2, 3));
        actual = piece.isSameType(byeong);
        assertThat(actual).isTrue();

        piece = (Piece) board.get(new Position(4, 3));
        actual = piece.isSameType(byeong);
        assertThat(actual).isTrue();

        piece = (Piece) board.get(new Position(6, 3));
        actual = piece.isSameType(byeong);
        assertThat(actual).isTrue();

        piece = (Piece) board.get(new Position(8, 3));
        actual = piece.isSameType(byeong);
        assertThat(actual).isTrue();

        piece = (Piece) board.get(new Position(0, 6));
        actual = piece.isSameType(byeong);
        assertThat(actual).isTrue();

        piece = (Piece) board.get(new Position(2, 6));
        actual = piece.isSameType(byeong);
        assertThat(actual).isTrue();

        piece = (Piece) board.get(new Position(4, 6));
        actual = piece.isSameType(byeong);
        assertThat(actual).isTrue();

        piece = (Piece) board.get(new Position(6, 6));
        actual = piece.isSameType(byeong);
        assertThat(actual).isTrue();

        piece = (Piece) board.get(new Position(8, 6));
        actual = piece.isSameType(byeong);
        assertThat(actual).isTrue();
    }

    @Test
    void 기물_점수_정상_테스트() {
        Board initBoard = new Board(strategy);

        double actual = initBoard.calculatePieceScore(Team.CHO);

        double expected = 72;

        assertThat(actual).isEqualTo(expected);
    }
}