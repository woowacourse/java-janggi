package domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import domain.enums.PieceType;
import testUtil.BoardTestUtil;

class BoardTest {

    @Test
    void 마상상마_마상상마_정상테스트(){
        List<PieceType> pieces = BoardTestUtil.createMasangSangMa();
        Board board = new Board(pieces);

        PieceType ma= PieceType.MA;
        PieceType sang= PieceType.SANG;


        assertThat(board.getPiece(Position.create(1,2)).getPieceType()).isEqualTo(ma);
        assertThat(board.getPiece(Position.create(1,3)).getPieceType()).isEqualTo(sang);
        assertThat(board.getPiece(Position.create(1,7)).getPieceType()).isEqualTo(sang);
        assertThat(board.getPiece(Position.create(1,8)).getPieceType()).isEqualTo(ma);
        assertThat(board.getPiece(Position.create(10,2)).getPieceType()).isEqualTo(ma);
        assertThat(board.getPiece(Position.create(10,3)).getPieceType()).isEqualTo(sang);
        assertThat(board.getPiece(Position.create(10,7)).getPieceType()).isEqualTo(sang);
        assertThat(board.getPiece(Position.create(10,8)).getPieceType()).isEqualTo(ma);
    }

    @Test
    void 상마상마_상마상마_정상테스트(){
        List<PieceType> pieces = BoardTestUtil.createSangMaSangMa();
        Board board = new Board(pieces);

        PieceType ma= PieceType.MA;
        PieceType sang= PieceType.SANG;


        assertThat(board.getPiece(Position.create(1,2)).getPieceType()).isEqualTo(sang);
        assertThat(board.getPiece(Position.create(1,3)).getPieceType()).isEqualTo(ma);
        assertThat(board.getPiece(Position.create(1,7)).getPieceType()).isEqualTo(sang);
        assertThat(board.getPiece(Position.create(1,8)).getPieceType()).isEqualTo(ma);
        assertThat(board.getPiece(Position.create(10,2)).getPieceType()).isEqualTo(sang);
        assertThat(board.getPiece(Position.create(10,3)).getPieceType()).isEqualTo(ma);
        assertThat(board.getPiece(Position.create(10,7)).getPieceType()).isEqualTo(sang);
        assertThat(board.getPiece(Position.create(10,8)).getPieceType()).isEqualTo(ma);
    }

    @DisplayName("도착좌표에 아무것도 없을때 말의 이동 정상 테스트")
    @Test
    void 도착_좌표_아무것도_없을때_이동_정상_테스트(){
        List<PieceType> pieces = BoardTestUtil.createSangMaSangMa();
        Board board = new Board(pieces);

        board.move(Position.create(1,3), Position.create(3,4));

        assertThat(board.getPiece(Position.create(3,4)).getPieceType()).isEqualTo(PieceType.MA);
    }

    @DisplayName("이동할 수 없는 도착 좌표 이동 예외 테스트 - 이동 규칙 위반")
    @Test
    void 이동_규칙_위반_예외_테스트(){
        List<PieceType> pieces = BoardTestUtil.createSangMaSangMa();
        Board board = new Board(pieces);

        assertThatThrownBy(() -> board.move(Position.create(1,3), Position.create(3,3)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("이동할 수 없는 도착 좌표 이동 예외 테스트 - 도착지에 같은 팀 말이 존재")
    @Test
    void 도착지에_같은_팀말_존재_예외_테스트(){
        List<PieceType> pieces = BoardTestUtil.createSangMaSangMa();
        Board board = new Board(pieces);

        assertThatThrownBy(() -> board.move(Position.create(1,1), Position.create(1,3)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("이동할 수 없는 도착 좌표 이동 예외 테스트 - 졸이 뒤로 이동 (초나라)")
    @Test
    void 졸_뒤로_이동_초나라_예외_테스트(){
        List<PieceType> pieces = BoardTestUtil.createSangMaSangMa();
        Board board = new Board(pieces);

        assertThatThrownBy(() -> board.move(Position.create(4,1), Position.create(3,1)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("이동할 수 없는 도착 좌표 이동 예외 테스트 - 차 이동 시 중간에 장애물 있음 (초나라)")
    @Test
    void 차_이동_중간_장애물_초나라_예외_테스트(){
        List<PieceType> pieces = BoardTestUtil.createSangMaSangMa();
        Board board = new Board(pieces);

        assertThatThrownBy(() -> board.move(Position.create(1,1), Position.create(5,1)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("이동할 수 없는 도착 좌표 제외 여부 테스트 - 차 이동 시 중간에 장애물 있음 (초나라)")
    @Test
    void 차_이동_중간_장애물_도착좌표_예외_테스트(){
        List<PieceType> pieces = BoardTestUtil.createSangMaSangMa();
        Board board = new Board(pieces);

        assertThat(board.findAvailablePositions(Position.create(1,1)))
                .doesNotContain(Position.create(5,1));
    }

    @DisplayName("보드 위에 다른 기물 있을 때 - 졸 이동 테스트")
    @Test
    void 졸_이동가능한_좌표_반환(){
        List<PieceType> pieces = BoardTestUtil.createSangMaSangMa();
        Board board = new Board(pieces);

        List<Position> positions = board.findAvailablePositions(Position.create(4,3));

        assertThat(positions)
                .containsExactlyInAnyOrderElementsOf(List.of(Position.create(4,2),Position.create(4,4),Position.create(5,3)));

    }

    @DisplayName("보드 위에 다른 기물 있을 때 - 상 이동 테스트")
    @Test
    void 상_이동가능한_좌표_반환(){
        List<PieceType> pieces = BoardTestUtil.createSangMaSangMa();
        Board board = new Board(pieces);

        List<Position> positions = board.findAvailablePositions(Position.create(1,2));

        assertThat(positions)
                .containsExactlyInAnyOrderElementsOf(List.of(Position.create(4,4)));

    }

    @DisplayName("보드 위에 다른 기물 있을 때 - 차 이동 테스트")
    @Test
    void 차_이동가능한_좌표_반환(){
        List<PieceType> pieces = BoardTestUtil.createSangMaSangMa();
        Board board = new Board(pieces);

        List<Position> positions = board.findAvailablePositions(Position.create(1,1));

        assertThat(positions)
                .containsExactlyInAnyOrderElementsOf(List.of(Position.create(2,1),Position.create(3,1)));

    }
}