package janggi.strategy;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Side;
import janggi.domain.piece.Cha;
import janggi.domain.piece.Gung;
import janggi.domain.piece.Jolbyeong;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Po;
import janggi.domain.piece.Sa;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ArrangementStrategyTest {

    private final ArrangementStrategy dummyStrategy = new ArrangementStrategy(StrategyLabel.MSMS) {
        @Override
        protected void placeVariablePieces(Piece[][] arrangement, Side side) {
            return;
        }
    };

    @Test
    @DisplayName("전략의 place를 호출하면 차, 포, 궁, 사, 졸 등 '기본 기물'이 올바른 위치에 배치된다.")
    void shouldPlaceDefaultPieces() {
        // given
        Piece[][] board = new Piece[10][9];
        Side han = Side.HAN;
        Side cho = Side.CHO;

        // when
        dummyStrategy.place(board, han);
        dummyStrategy.place(board, cho);

        // then: 한팀
        Piece chaOfHan = board[0][0];
        assertThat(chaOfHan).isInstanceOf(Cha.class);
        assertThat(chaOfHan.isSameSide(han)).isTrue();

        Piece saOfHan = board[0][3];
        assertThat(saOfHan).isInstanceOf(Sa.class);
        assertThat(saOfHan.isSameSide(han)).isTrue();

        Piece gungOfHan = board[1][4];
        assertThat(gungOfHan).isInstanceOf(Gung.class);
        assertThat(gungOfHan.isSameSide(han)).isTrue();

        Piece poOfHan = board[2][1];
        assertThat(poOfHan).isInstanceOf(Po.class);
        assertThat(poOfHan.isSameSide(han)).isTrue();

        Piece jolbyeongOfHan = board[3][0];
        assertThat(jolbyeongOfHan).isInstanceOf(Jolbyeong.class);
        assertThat(jolbyeongOfHan.isSameSide(han)).isTrue();


        // then: 초팀
        Piece chaOfCho = board[9][8];
        assertThat(chaOfCho).isInstanceOf(Cha.class);
        assertThat(chaOfCho.isSameSide(cho)).isTrue();

        Piece gungOfCho = board[8][4];
        assertThat(gungOfCho).isInstanceOf(Gung.class);
        assertThat(gungOfCho.isSameSide(cho)).isTrue();

        Piece poOfCho = board[7][7];
        assertThat(poOfCho).isInstanceOf(Po.class);
        assertThat(poOfCho.isSameSide(cho)).isTrue();

        Piece jolbyeongOfCho = board[6][8];
        assertThat(jolbyeongOfCho).isInstanceOf(Jolbyeong.class);
        assertThat(jolbyeongOfCho.isSameSide(cho)).isTrue();
    }
}
