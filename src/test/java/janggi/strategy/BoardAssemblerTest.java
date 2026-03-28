package janggi.strategy;

import janggi.domain.Location;
import janggi.domain.Side;
import janggi.domain.piece.Cha;
import janggi.domain.piece.EmptyPiece;
import janggi.domain.piece.Jolbyeong;
import janggi.domain.piece.Piece;
import janggi.support.TestArrangementStrategy;
import janggi.support.TestPiece;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardAssemblerTest {

    @Test
    @DisplayName("공통 기물과 각 팀의 전략이 합쳐져 전체 보드를 생성한다.")
    void shouldAssembleFullBoard() {
        // given
        Side han = Side.HAN;
        Map<Location, Piece> customPiecesOfHan = Map.of(
                new Location(4, 0), new TestPiece(han)
        );
        ArrangementStrategy hanStrategy = new TestArrangementStrategy(customPiecesOfHan, false);

        Side cho = Side.CHO;
        Map<Location, Piece> customPiecesOfCho = Map.of(
                new Location(5, 0), new TestPiece(cho)
        );
        ArrangementStrategy choStrategy = new TestArrangementStrategy(customPiecesOfCho, false);
        BoardAssembler assembler = BoardAssembler.of(hanStrategy, choStrategy);

        // when
        Piece[][] board = assembler.assemble();

        // then
        // 1. 공통 기물 검증
        Piece chaOfHan = board[0][0];
        Assertions.assertThat(chaOfHan).isInstanceOf(Cha.class);
        Assertions.assertThat(chaOfHan.isSameSide(han)).isTrue();

        Piece chaOfCho = board[9][8];
        Assertions.assertThat(chaOfCho).isInstanceOf(Cha.class);
        Assertions.assertThat(chaOfCho.isSameSide(cho)).isTrue();

        Piece jolbyeongOfHan = board[3][0];
        Assertions.assertThat(jolbyeongOfHan).isInstanceOf(Jolbyeong.class);
        Assertions.assertThat(jolbyeongOfHan.isSameSide(han)).isTrue();

        // 2. 전략 기물 검증 (전략이 실제로 동작했는지 확인)
        Piece testPieceOfHan = board[4][0];
        Assertions.assertThat(testPieceOfHan).isInstanceOf(TestPiece.class);
        Assertions.assertThat(testPieceOfHan.isSameSide(han)).isTrue();

        Piece testPieceOfCho = board[5][0];
        Assertions.assertThat(testPieceOfCho).isInstanceOf(TestPiece.class);
        Assertions.assertThat(testPieceOfCho.isSameSide(cho)).isTrue();

        Piece emptyPiece = board[4][5];
        Assertions.assertThat(emptyPiece).isEqualTo(EmptyPiece.getInstance());
    }
}
