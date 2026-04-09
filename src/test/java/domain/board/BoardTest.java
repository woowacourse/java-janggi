package domain.board;

import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Side;
import domain.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class BoardTest {

    @Test
    @DisplayName("한 진영이 외부 코끼리 상차림을 배치할 수 있다.")
    void 한진영_코끼리_상차림_테스트_1() {
        // given
        BoardStateFactory boardStateFactory = new BoardStateFactory(
                new OuterElephantSetup(),
                new OuterElephantSetup()
        );

        // when
        Board board = Board.of(boardStateFactory.create());

        // then
        assertThat(board.findBy(Position.of(10, 8))).isEqualTo(Piece.of(Side.HAN, PieceType.ELEPHANT));
        assertThat(board.findBy(Position.of(10, 7))).isEqualTo(Piece.of(Side.HAN, PieceType.HORSE));
        assertThat(board.findBy(Position.of(10, 3))).isEqualTo(Piece.of(Side.HAN, PieceType.HORSE));
        assertThat(board.findBy(Position.of(10, 2))).isEqualTo(Piece.of(Side.HAN, PieceType.ELEPHANT));
    }

    @Test
    @DisplayName("한 진영이 내부 코끼리 상차림을 배치할 수 있다.")
    void 한진영_코끼리_상차림_테스트_2() {
        // given
        BoardStateFactory boardStateFactory = new BoardStateFactory(
                new InnerElephantSetup(),
                new OuterElephantSetup()
        );

        // when
        Board board = Board.of(boardStateFactory.create());

        // then
        assertThat(board.findBy(Position.of(10, 8))).isEqualTo(Piece.of(Side.HAN, PieceType.HORSE));
        assertThat(board.findBy(Position.of(10, 7))).isEqualTo(Piece.of(Side.HAN, PieceType.ELEPHANT));
        assertThat(board.findBy(Position.of(10, 3))).isEqualTo(Piece.of(Side.HAN, PieceType.ELEPHANT));
        assertThat(board.findBy(Position.of(10, 2))).isEqualTo(Piece.of(Side.HAN, PieceType.HORSE));
    }

    @Test
    @DisplayName("한 진영이 오른쪽 코끼리 상차림을 배치할 수 있다.")
    void 한진영_코끼리_상차림_테스트_3() {
        // given
        BoardStateFactory boardStateFactory = new BoardStateFactory(
                new RightElephantSetup(),
                new OuterElephantSetup()
        );

        // when
        Board board = Board.of(boardStateFactory.create());

        // then
        assertThat(board.findBy(Position.of(10, 8))).isEqualTo(Piece.of(Side.HAN, PieceType.HORSE));
        assertThat(board.findBy(Position.of(10, 7))).isEqualTo(Piece.of(Side.HAN, PieceType.ELEPHANT));
        assertThat(board.findBy(Position.of(10, 3))).isEqualTo(Piece.of(Side.HAN, PieceType.HORSE));
        assertThat(board.findBy(Position.of(10, 2))).isEqualTo(Piece.of(Side.HAN, PieceType.ELEPHANT));
    }

    @Test
    @DisplayName("한 진영이 왼쪽 코끼리 상차림을 배치할 수 있다.")
    void 한진영_코끼리_상차림_테스트_4() {
        // given
        BoardStateFactory boardStateFactory = new BoardStateFactory(
                new LeftElephantSetup(),
                new OuterElephantSetup()
        );

        // when
        Board board = Board.of(boardStateFactory.create());

        // then
        assertThat(board.findBy(Position.of(10, 8))).isEqualTo(Piece.of(Side.HAN, PieceType.ELEPHANT));
        assertThat(board.findBy(Position.of(10, 7))).isEqualTo(Piece.of(Side.HAN, PieceType.HORSE));
        assertThat(board.findBy(Position.of(10, 3))).isEqualTo(Piece.of(Side.HAN, PieceType.ELEPHANT));
        assertThat(board.findBy(Position.of(10, 2))).isEqualTo(Piece.of(Side.HAN, PieceType.HORSE));
    }

    @Test
    @DisplayName("장기판에서 해당 포지션의 기물을 찾을 수 있다.")
    void 장기판_포지션의_기물_조회_테스트() {
        // given
        BoardStateFactory boardStateFactory = new BoardStateFactory(
                new OuterElephantSetup(),
                new OuterElephantSetup()
        );
        Board board = Board.of(boardStateFactory.create());

        // when
        Piece piece = board.findBy(Position.of(10, 2));

        // then
        assertThat(piece.getPieceType()).isEqualTo(PieceType.ELEPHANT);
        assertThat(piece.getSide()).isEqualTo(Side.HAN);
    }

    @Test
    @DisplayName("졸이 기물의 이동했을 때 상대 진영의 기물을 포획할 수 있다.")
    void 졸_기물_포획_성공() {
        // given
        BoardStateFactory boardStateFactory = new BoardStateFactory(
                new InnerElephantSetup(),
                new InnerElephantSetup()
        );
        Board board = Board.of(boardStateFactory.create());

        // when
        board.move(Position.of(7, 1), Position.of(6, 1), Side.HAN);
        board.move(Position.of(6, 1), Position.of(5, 1), Side.HAN);

        // when
        Position startPosition = Position.of(4, 1);
        Position endPosition = Position.of(5, 1);
        board.move(startPosition, endPosition, Side.CHO);

        // then
        Piece piece = board.findBy(endPosition);
        assertThat(piece).isEqualTo(Piece.of(Side.CHO, PieceType.PAWN));
    }

    @Test
    @DisplayName("졸 기물은 뒤로 이동할 수 없다.")
    void 졸_뒤로_이동_실패() {
        // given
        BoardStateFactory boardStateFactory = new BoardStateFactory(
                new InnerElephantSetup(),
                new InnerElephantSetup()
        );
        Board board = Board.of(boardStateFactory.create());

        // when, then
        assertThatThrownBy(() -> {
            board.move(Position.of(7, 1), Position.of(8, 1), Side.HAN);

        }).isInstanceOf(IllegalArgumentException.class);
        Piece piece = board.findBy(Position.of(8, 1));
        assertThat(piece).isNotEqualTo(Piece.of(Side.HAN, PieceType.PAWN));
    }

    @Test
    @DisplayName("모든 기물이 이동했을 때 상대 진영의 기물을 포획할 수 있다.")
    void 기물_포획_성공() {
        // given
        BoardStateFactory boardStateFactory = new BoardStateFactory(
                new InnerElephantSetup(),
                new InnerElephantSetup()
        );
        Board board = Board.of(boardStateFactory.create());
        board.move(Position.of(7, 9), Position.of(7, 8), Side.HAN);

        // when
        board.move(Position.of(10, 9), Position.of(4, 9), Side.HAN);

        // then
        Piece piece = board.findBy(Position.of(4, 9));
        assertThat(piece).isEqualTo(Piece.of(Side.HAN, PieceType.CHARIOT));

    }

    @Test
    @DisplayName("모든 기물은 도착 지점에 같은 진영 기물이 있다면 움직일 수 없다.")
    void 기물_이동_실패() {
        // given
        BoardStateFactory boardStateFactory = new BoardStateFactory(
                new InnerElephantSetup(),
                new InnerElephantSetup()
        );
        Board board = Board.of(boardStateFactory.create());

        // when, then
        assertThatThrownBy(() -> {
            board.move(Position.of(10, 9), Position.of(7, 9), Side.HAN);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("마 기물은 상대 기물을 포획할 수 있다.")
    void 마_기물_이동_성공() {
        // given
        BoardStateFactory boardStateFactory = new BoardStateFactory(
                new RightElephantSetup(),
                new RightElephantSetup()
        );
        Board board = Board.of(boardStateFactory.create());

        board.move(Position.of(7, 5), Position.of(6, 5), Side.HAN);
        board.move(Position.of(1, 7), Position.of(3, 6), Side.CHO);
        board.move(Position.of(3, 6), Position.of(4, 4), Side.CHO);
        // when
        board.move(Position.of(4, 4), Position.of(6, 5), Side.CHO);

        // then
        assertThat(board.findBy(Position.of(6, 5))).isEqualTo(Piece.of(Side.CHO, PieceType.HORSE));
    }

    @Test
    @DisplayName("상 기물을 움직여서 상대 기물 위치에 도착할 시 상대 기물을 포획할 수 있다.")
    void 상_포획_성공_테스트() {
        // given
        BoardStateFactory boardStateFactory = new BoardStateFactory(
                new RightElephantSetup(),
                new RightElephantSetup()
        );
        Board board = Board.of(boardStateFactory.create());
        board.move(Position.of(7, 5), Position.of(7, 4), Side.HAN);
        board.move(Position.of(1, 8), Position.of(4, 6), Side.CHO);

        // when
        board.move(Position.of(4, 6), Position.of(7, 4), Side.CHO);

        // then
        Piece piece = board.findBy(Position.of(7, 4));
        assertThat(piece).isEqualTo(Piece.of(Side.CHO, PieceType.ELEPHANT));
    }

    @Test
    @DisplayName("사 기물을 움직여서 상대 기물 위치에 도착할 시 상대 기물을 포획할 수 있다.")
    void 사_포획_성공_테스트() {
        // given
        BoardStateFactory boardStateFactory = new BoardStateFactory(
                new RightElephantSetup(),
                new RightElephantSetup()
        );
        // when
        Board board = Board.of(boardStateFactory.create());
        board.move(Position.of(7, 5), Position.of(7, 4), Side.HAN);
        board.move(Position.of(7, 4), Position.of(6, 4), Side.HAN);
        board.move(Position.of(6, 4), Position.of(5, 4), Side.HAN);
        board.move(Position.of(5, 4), Position.of(4, 4), Side.HAN);
        board.move(Position.of(4, 4), Position.of(3, 4), Side.HAN);
        board.move(Position.of(3, 4), Position.of(2, 4), Side.HAN);

        // when
        board.move(Position.of(1, 4), Position.of(2, 4), Side.CHO);

        // then
        Piece piece = board.findBy(Position.of(2, 4));
        assertThat(piece).isEqualTo(Piece.of(Side.CHO, PieceType.COUNSELOR));
    }

    @Test
    @DisplayName("장 기물을 움직여서 상대 기물 위치에 도착할 시 상대 기물을 포획할 수 있다.")
    void 장_포획_성공_테스트() {
        // given
        BoardStateFactory boardStateFactory = new BoardStateFactory(
                new RightElephantSetup(),
                new RightElephantSetup()
        );
        Board board = Board.of(boardStateFactory.create());
        board.move(Position.of(4, 5), Position.of(4, 4), Side.CHO);
        board.move(Position.of(7, 5), Position.of(6, 5), Side.HAN);
        board.move(Position.of(6, 5), Position.of(5, 5), Side.HAN);
        board.move(Position.of(5, 5), Position.of(4, 5), Side.HAN);
        board.move(Position.of(4, 5), Position.of(3, 5), Side.HAN);

        // when
        board.move(Position.of(2, 5), Position.of(3, 5), Side.CHO);

        // then
        Piece piece = board.findBy(Position.of(3, 5));
        assertThat(piece).isEqualTo(Piece.of(Side.CHO, PieceType.GENERAL));
    }

    @Test
    @DisplayName("포 기물을 움직여서 상대 기물 위치에 도착할 시 상대 기물을 포획할 수 있다.")
    void 포_포획_성공_테스트() {
        // given
        BoardStateFactory boardStateFactory = new BoardStateFactory(
                new LeftElephantSetup(),
                new LeftElephantSetup()
        );
        Board board = Board.of(boardStateFactory.create());
        board.move(Position.of(10, 7), Position.of(8, 6), Side.HAN);
        board.move(Position.of(8, 8), Position.of(8, 5), Side.HAN);
        board.move(Position.of(7, 9), Position.of(7, 8), Side.HAN);

        // when
        board.move(Position.of(3, 8), Position.of(10, 8), Side.CHO);

        // then
        Piece piece = board.findBy(Position.of(10, 8));
        assertThat(piece).isEqualTo(Piece.of(Side.CHO, PieceType.CANON));
    }

    @Test
    @DisplayName("초기 장기판 초기화 후 해당 진영에 대한 점수 계산할 수 있다.")
    void 진영_점수_계산_테스트() {
        // given
        BoardStateFactory boardStateFactory = new BoardStateFactory(
                new LeftElephantSetup(),
                new LeftElephantSetup()
        );
        Board board = Board.of(boardStateFactory.create());

        // when then
        assertThat(board.calculateScoreBy(Side.CHO)).isEqualTo(72);
        assertThat(board.calculateScoreBy(Side.HAN)).isEqualTo(73.5);
    }

    @Test
    @DisplayName("장기 게임 중 궁 포획시 종료 되었는지 판단할 수 있다.")
    void 게임_종료_판단_테스트() {
        // given:
        Map<Position, Piece> customState = new HashMap<>();
        Position hanGeneralPos = Position.of(9, 4);
        Position choChariotPos = Position.of(2, 4);
        Position choGeneralPos = Position.of(2, 5);

        customState.put(hanGeneralPos, Piece.of(Side.HAN, PieceType.GENERAL));
        customState.put(choChariotPos, Piece.of(Side.CHO, PieceType.CHARIOT));
        customState.put(choGeneralPos, Piece.of(Side.CHO, PieceType.GENERAL));

        Board board = Board.of(customState);

        // when:
        board.move(choChariotPos, hanGeneralPos, Side.CHO);
        assertThat(board.isFinished()).isTrue();
        assertThat(board.getWinner()).isEqualTo(Side.CHO);
    }
}
