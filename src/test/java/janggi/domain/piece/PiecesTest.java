package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.piece.Cha;
import janggi.domain.piece.Gung;
import janggi.domain.piece.Jol;
import janggi.domain.piece.Ma;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.Pieces;
import janggi.domain.piece.Po;
import janggi.domain.piece.Sa;
import janggi.domain.piece.Sang;
import janggi.domain.value.JanggiPosition;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class PiecesTest {

    private static final JanggiPosition STANDARD = new JanggiPosition(4, 8);

    @DisplayName("장기말을 이동시킬 수 있다.")
    @Test
    void test1() {
        //given
        Pieces pieces = new Pieces(List.of(Gung.from(STANDARD)));

        JanggiPosition destination = new JanggiPosition(5, 8);

        //when
        pieces.movePiece(new Pieces(List.of()), STANDARD, destination);

        Piece gung = pieces.getPieces().getFirst();
        assertThat(gung.getPosition()).isEqualTo(destination);
    }

    @DisplayName("이동시킬 좌표에 장기말이 존재하지 않는 경우 예외를 발생시킨다.")
    @Test
    void test2() {
        //given
        Pieces pieces = new Pieces(List.of(Gung.from(STANDARD)));

        JanggiPosition invalidJanggiPosition = new JanggiPosition(5, 8);

        assertThatThrownBy(() -> pieces.movePiece(new Pieces(List.of()), invalidJanggiPosition, new JanggiPosition(6, 8)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 해당 위치에 이동할 말이 존재하지 않습니다.");
    }

    @DisplayName("장기말이 목적지로 이동할 수 없는 경우 예외를 발생시킨다.")
    @Test
    void test3() {
        Pieces pieces = new Pieces(List.of(Gung.from(STANDARD)));
        JanggiPosition destination = new JanggiPosition(7, 8);

        assertThatThrownBy(() -> pieces.movePiece(new Pieces(List.of()), STANDARD, destination))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이동이 불가능합니다.");
    }

    @DisplayName("전달받은 피스의 이름의 피스가 살아있는지 확인")
    @Test
    void test4() {
        //given
        Piece gung = Gung.from(STANDARD);
        Pieces pieces = new Pieces(List.of(gung));

        //when
        boolean alive = pieces.isPieceAlive(PieceType.GUNG.getName());

        //then
        Assertions.assertThat(alive).isFalse();
    }

    @DisplayName("전달받은 피스의 이름의 피스가 죽어있는지 확인")
    @Test
    void test5() {
        //given
        Piece gung = Gung.from(STANDARD);
        Pieces pieces = new Pieces(List.of(gung));
        pieces.beAttackedAt(new JanggiPosition(4,8));

        //when
        boolean alive = pieces.isPieceAlive(PieceType.GUNG.getName());

        //then
        Assertions.assertThat(alive).isTrue();
    }

    @DisplayName("장기말들 점수 계산")
    @Test
    void test6() {
        //given
        Pieces pieces = new Pieces(List.of(
                Gung.from(STANDARD),
                Cha.from(STANDARD),
                Ma.from(STANDARD),
                Sang.from(STANDARD),
                Sa.from(STANDARD),
                Po.from(STANDARD),
                Jol.from(STANDARD)
        ));

        //when
        int totalScore = pieces.calculateTotalScore();

        //then
        Assertions.assertThat(totalScore).isEqualTo(33);
    }
}