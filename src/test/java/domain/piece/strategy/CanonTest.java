package domain.piece.strategy;

import domain.board.FakeBoard;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Side;
import domain.position.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CanonTest {

    FakeBoard fakeBoard;
    MovingCondition movingCondition;

    @BeforeEach
    void setUp() {
        fakeBoard = new FakeBoard();
        movingCondition = new CanonMovingCondition();
    }

    @Test
    @DisplayName("본인 진영의 기물을 띄어넘어 빈칸으로 이동")
    void canMove_이동성공_포_기물_움직임_여부_판단_1() {
        // given
        Piece choCanon = Piece.of(Side.CHO, PieceType.CANON);
        Piece choPawn = Piece.of(Side.CHO, PieceType.PAWN);
        fakeBoard.put(Position.of(3, 8), choCanon);
        fakeBoard.put(Position.of(4, 8), choPawn);

        Position startPosition = Position.of(3, 8);
        Position endPosition = Position.of(6, 8);

        // when
        boolean result = movingCondition.canMove(fakeBoard, startPosition, endPosition);
        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("본인 진영의 기물을 띄어넘어 상대방 말로 이동")
    void canMove_이동성공_포_기물_움직임_여부_판단_2() {
        // given
        Piece choCanon = Piece.of(Side.CHO, PieceType.CANON);
        Piece choPawn = Piece.of(Side.CHO, PieceType.PAWN);
        Piece hanHorse = Piece.of(Side.HAN, PieceType.PAWN);
        fakeBoard.put(Position.of(3, 8), choCanon);
        fakeBoard.put(Position.of(4, 8), choPawn);
        fakeBoard.put(Position.of(10, 8), hanHorse);

        Position startPosition = Position.of(3, 8);
        Position endPosition = Position.of(10, 8);

        // when
        boolean result = movingCondition.canMove(fakeBoard, startPosition, endPosition);
        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("상대방 진영 기물을 띄어넘어 빈칸으로 이동")
    void canMove_이동성공_포_기물_움직임_여부_판단_3() {
        // given
        FakeBoard fakeBoard = new FakeBoard();
        Piece choCanon = Piece.of(Side.CHO, PieceType.CANON);
        Piece hanPawn = Piece.of(Side.HAN, PieceType.PAWN);
        fakeBoard.put(Position.of(3, 8), choCanon);
        fakeBoard.put(Position.of(5, 8), hanPawn);

        Position startPosition = Position.of(3, 8);
        Position endPosition = Position.of(10, 8);

        // when
        boolean result = movingCondition.canMove(fakeBoard, startPosition, endPosition);
        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("상대방 진영의 기물을 띄어넘어 상대방 말로 이동")
    void canMove_이동성공_포_기물_움직임_여부_판단_4() {
        // given
        FakeBoard fakeBoard = new FakeBoard();
        Piece choCanon = Piece.of(Side.CHO, PieceType.CANON);
        Piece hanPawn = Piece.of(Side.HAN, PieceType.PAWN);
        Piece hanHorse = Piece.of(Side.HAN, PieceType.HORSE);
        fakeBoard.put(Position.of(3, 8), choCanon);
        fakeBoard.put(Position.of(4, 8), hanPawn);
        fakeBoard.put(Position.of(10, 8), hanHorse);

        Position startPosition = Position.of(3, 8);
        Position endPosition = Position.of(10, 8);

        // when
        boolean result = movingCondition.canMove(fakeBoard, startPosition, endPosition);
        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("직선의 경로가 아닌 경우")
    void canMove_이동실패_포_기물_움직임_여부_판단_1() {
        // given
        FakeBoard fakeBoard = new FakeBoard();
        Piece choCanon = Piece.of(Side.CHO, PieceType.CANON);
        fakeBoard.put(Position.of(3, 8), choCanon);

        Position startPosition = Position.of(3, 8);
        Position endPosition = Position.of(5, 7);

        // when
        boolean result = movingCondition.canMove(fakeBoard, startPosition, endPosition);
        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("가는 경로에 기물이 1개 초과일 경우")
    void canMove_이동실패_포_기물_움직임_여부_판단_2() {
        // given
        FakeBoard fakeBoard = new FakeBoard();
        Piece choCanon = Piece.of(Side.CHO, PieceType.CANON);
        Piece choPawn1 = Piece.of(Side.CHO, PieceType.PAWN);
        Piece choPawn2 = Piece.of(Side.CHO, PieceType.PAWN);
        fakeBoard.put(Position.of(3, 8), choCanon);
        fakeBoard.put(Position.of(4, 8), choPawn1);
        fakeBoard.put(Position.of(5, 8), choPawn2);

        Position startPosition = Position.of(3, 8);
        Position endPosition = Position.of(7, 8);

        // when
        boolean result = movingCondition.canMove(fakeBoard, startPosition, endPosition);
        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("가는 경로의 기물이 1개 있는데 해당 기물이 포일 경우")
    void canMove_이동실패_포_기물_움직임_여부_판단_3() {
        // given
        FakeBoard fakeBoard = new FakeBoard();
        Piece choCanon = Piece.of(Side.CHO, PieceType.CANON);
        Piece hanCanon = Piece.of(Side.HAN, PieceType.CANON);
        fakeBoard.put(Position.of(3, 8), choCanon);
        fakeBoard.put(Position.of(8, 8), hanCanon);

        Position startPosition = Position.of(3, 8);
        Position endPosition = Position.of(10, 8);

        // when
        boolean result = movingCondition.canMove(fakeBoard, startPosition, endPosition);
        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("도착 지점이 상대방 포일 경우")
    void canMove_이동실패_포_기물_움직임_여부_판단_4() {
        // given
        FakeBoard fakeBoard = new FakeBoard();
        Piece choCanon = Piece.of(Side.CHO, PieceType.CANON);
        Piece choPawn = Piece.of(Side.CHO, PieceType.PAWN);
        Piece hanCanon = Piece.of(Side.HAN, PieceType.CANON);
        fakeBoard.put(Position.of(3, 8), choCanon);
        fakeBoard.put(Position.of(4, 8), choPawn);
        fakeBoard.put(Position.of(8, 8), hanCanon);

        Position startPosition = Position.of(3, 8);
        Position endPosition = Position.of(8, 8);

        // when
        boolean result = movingCondition.canMove(fakeBoard, startPosition, endPosition);
        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("가는 경로에 기물이 1개도 없는 경우")
    void canMove_이동실패_포_기물_움직임_여부_판단_5() {
        // given
        FakeBoard fakeBoard = new FakeBoard();
        Piece choCanon = Piece.of(Side.CHO, PieceType.CANON);
        fakeBoard.put(Position.of(3, 8), choCanon);

        Position startPosition = Position.of(3, 8);
        Position endPosition = Position.of(4, 8);

        // when
        boolean result = movingCondition.canMove(fakeBoard, startPosition, endPosition);
        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("궁성 영역 중 대각선으로 이동할 수 있는 좌표에 포가 있다면 대각선으로 이동 가능하다.")
    void canMove_포_궁성_내_움직임_테스트_1() {
        // given
        Piece choCanon = Piece.of(Side.CHO, PieceType.CANON);
        Piece choGeneral = Piece.of(Side.CHO, PieceType.GENERAL);
        fakeBoard.put(Position.of(1, 4), choCanon);
        fakeBoard.put(Position.of(2, 5), choGeneral);

        Position startPosition = Position.of(1, 4);
        Position endPosition = Position.of(3, 6);

        // when
        boolean result = movingCondition.canMove(fakeBoard, startPosition, endPosition);
        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("궁성 영역 중 대각선으로 이동할 수 없는 좌표에 포가 있다면 대각선으로 이동 불가능하다.")
    void canMove_포_궁성_내_움직임_테스트_2() {
        // given
        Piece choCanon = Piece.of(Side.CHO, PieceType.CANON);
        Piece choGeneral = Piece.of(Side.CHO, PieceType.GENERAL);
        fakeBoard.put(Position.of(1, 5), choCanon);
        fakeBoard.put(Position.of(2, 5), choGeneral);

        Position startPosition = Position.of(1, 4);
        Position endPosition = Position.of(2, 6);

        // when
        boolean result = movingCondition.canMove(fakeBoard, startPosition, endPosition);
        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("궁성 영역에서 궁성영역 밖으로 이동할 수 있다.")
    void canMove_이동성공_포_궁성_내_움직임_테스트_3() {
        // given
        Piece choCanon = Piece.of(Side.CHO, PieceType.CANON);
        Piece choGeneral = Piece.of(Side.CHO, PieceType.GENERAL);
        fakeBoard.put(Position.of(1, 4), choCanon);
        fakeBoard.put(Position.of(1, 5), choGeneral);

        Position startPosition = Position.of(1, 4);
        Position endPosition = Position.of(1, 9);

        // when
        boolean result = movingCondition.canMove(fakeBoard, startPosition, endPosition);
        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("궁성 영역에서 대각선 이동 시 궁성영역 밖으로 이동할 수 없다.")
    void canMove_이동실패_포_궁성_내_움직임_테스트_4() {
        // given
        Piece choCanon = Piece.of(Side.CHO, PieceType.CANON);
        Piece choGeneral = Piece.of(Side.CHO, PieceType.GENERAL);
        fakeBoard.put(Position.of(1, 4), choCanon);
        fakeBoard.put(Position.of(2, 5), choGeneral);

        Position startPosition = Position.of(1, 4);
        Position endPosition = Position.of(4, 7);

        // when
        boolean result = movingCondition.canMove(fakeBoard, startPosition, endPosition);
        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("궁성 영역에서 상대편 궁성영역으로 이동할 수 있다.")
    void canMove_이동성공_포_궁성_내_움직임_테스트_5() {
        // given
        Piece choCanon = Piece.of(Side.CHO, PieceType.CANON);
        Piece choGeneral = Piece.of(Side.CHO, PieceType.GENERAL);
        fakeBoard.put(Position.of(1, 4), choCanon);
        fakeBoard.put(Position.of(2, 4), choGeneral);

        Position startPosition = Position.of(1, 4);
        Position endPosition = Position.of(10, 4);

        // when
        boolean result = movingCondition.canMove(fakeBoard, startPosition, endPosition);
        // then
        assertThat(result).isTrue();
    }
}
