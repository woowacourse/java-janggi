package domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import domain.constant.PieceType;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import testUtil.BoardTestUtil;

class BoardTest {

    @Test
    void 마상상마_마상상마_정상테스트(){
        List<PieceType> pieces = BoardTestUtil.createMaSangSangMa();
        Board board = new Board(pieces, pieces);

        PieceType ma= PieceType.MA;
        PieceType sang= PieceType.SANG;


        assertThat(board.getPieceType(Position.create(1,2))).isEqualTo(ma);
        assertThat(board.getPieceType(Position.create(1,3))).isEqualTo(sang);
        assertThat(board.getPieceType(Position.create(1,7))).isEqualTo(sang);
        assertThat(board.getPieceType(Position.create(1,8))).isEqualTo(ma);
        assertThat(board.getPieceType(Position.create(10,2))).isEqualTo(ma);
        assertThat(board.getPieceType(Position.create(10,3))).isEqualTo(sang);
        assertThat(board.getPieceType(Position.create(10,7))).isEqualTo(sang);
        assertThat(board.getPieceType(Position.create(10,8))).isEqualTo(ma);
    }

    @Test
    void 상마상마_상마상마_정상테스트(){
        List<PieceType> pieces = BoardTestUtil.createSangMaSangMa();
        Board board = new Board(pieces, pieces);

        PieceType ma= PieceType.MA;
        PieceType sang= PieceType.SANG;

        assertThat(board.getPieceType(Position.create(1,2))).isEqualTo(sang);
        assertThat(board.getPieceType(Position.create(1,3))).isEqualTo(ma);
        assertThat(board.getPieceType(Position.create(1,7))).isEqualTo(sang);
        assertThat(board.getPieceType(Position.create(1,8))).isEqualTo(ma);
        assertThat(board.getPieceType(Position.create(10,2))).isEqualTo(sang);
        assertThat(board.getPieceType(Position.create(10,3))).isEqualTo(ma);
        assertThat(board.getPieceType(Position.create(10,7))).isEqualTo(sang);
        assertThat(board.getPieceType(Position.create(10,8))).isEqualTo(ma);
    }

    @DisplayName("도착좌표에 아무것도 없을때 말의 이동 정상 테스트")
    @Test
    void 도착_좌표_아무것도_없을때_이동_정상_테스트(){
        List<PieceType> pieces = BoardTestUtil.createSangMaSangMa();
        Board board = new Board(pieces, pieces);

        board.move(Position.create(1,3), Position.create(3,4));

        assertThat(board.getPieceType(Position.create(3,4))).isEqualTo(PieceType.MA);
    }

    @DisplayName("이동할 수 없는 도착 좌표 이동 예외 테스트 - 이동 규칙 위반")
    @Test
    void 이동_규칙_위반_예외_테스트(){
        List<PieceType> pieces = BoardTestUtil.createSangMaSangMa();
        Board board = new Board(pieces, pieces);

        assertThatThrownBy(() -> board.move(Position.create(1,3), Position.create(3,3)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("이동할 수 없는 도착 좌표 이동 예외 테스트 - 도착지에 같은 팀 말이 존재")
    @Test
    void 도착지에_같은_팀말_존재_예외_테스트(){
        List<PieceType> pieces = BoardTestUtil.createSangMaSangMa();
        Board board = new Board(pieces, pieces);

        assertThatThrownBy(() -> board.move(Position.create(1,1), Position.create(1,3)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("이동할 수 없는 도착 좌표 이동 예외 테스트 - 졸이 뒤로 이동 (초나라)")
    @Test
    void 졸_뒤로_이동_초나라_예외_테스트(){
        List<PieceType> pieces = BoardTestUtil.createSangMaSangMa();
        Board board = new Board(pieces, pieces);

        assertThatThrownBy(() -> board.move(Position.create(4,1), Position.create(3,1)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 장_궁성_영역_외부_이동_예외_테스트() {
        List<PieceType> pieces = BoardTestUtil.createMaSangSangMa();
        Board board = new Board(pieces, pieces);
        board.move(Position.create(2, 5), Position.create(2, 4));
        assertThatThrownBy(() -> board.move(Position.create(2, 4), Position.create(2, 3)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("장과 사는 궁성 내부에서만 이동 가능합니다.");
    }

    @Test
    void 사_궁성_영역_외부_이동_예외_테스트() {
        List<PieceType> pieces = BoardTestUtil.createMaSangSangMa();
        Board board = new Board(pieces, pieces);
        board.move(Position.create(1, 4), Position.create(2, 4));
        assertThatThrownBy(() -> board.move(Position.create(2, 4), Position.create(2, 3)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("장과 사는 궁성 내부에서만 이동 가능합니다.");
    }

    @Test
    void 장_궁성_영역_대각선_이동_테스트() {
        List<PieceType> pieces = BoardTestUtil.createMaSangSangMa();
        Board board = new Board(pieces, pieces);
        assertThatCode(() -> board.move(Position.create(2, 5), Position.create(3, 4)))
                .doesNotThrowAnyException();
    }

    @Test
    void 사_궁성_영역_대각선_이동_테스트() {
        List<PieceType> pieces = BoardTestUtil.createMaSangSangMa();
        Board board = new Board(pieces, pieces);
        board.move(Position.create(2, 5), Position.create(3, 5));
        assertThatCode(() -> board.move(Position.create(1, 4), Position.create(2, 5)))
                .doesNotThrowAnyException();
    }
}
