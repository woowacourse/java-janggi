package domain;

import domain.piece.Cannon;
import domain.piece.Elephant;
import domain.piece.Guard;
import domain.piece.Horse;
import domain.piece.King;
import domain.piece.Pawn;
import domain.piece.Piece;
import domain.piece.Rook;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

import static domain.PieceType.PAWN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

import strategy.InitializeStrategy;
import strategy.InnerElephantFormationStrategy;
import strategy.LeftElephantFormationStrategy;
import strategy.OuterElephantFormationStrategy;
import strategy.RightElephantFormationStrategy;

class BoardTest {
    private final InitializeStrategy noElephantHorseStrategy = new NoOpElephantHorseStrategy();

    static class NoOpElephantHorseStrategy extends InitializeStrategy {
        @Override
        protected Map<Position, Piece> initializeElephantHorseFormation(Team team) {
            return Collections.emptyMap();
        }
    }

    /**
     * 1. 한나라 기본 기물이 올바르게 배치된다.(상,마 제외)
     */
    @Test
    void 한나라_기본_기물들이_올바르게_배치된다() {
        Board board = new Board(noElephantHorseStrategy, noElephantHorseStrategy);

        assertThat(board.isExistSameType(Position.from(1, 1), new Rook(Team.HAN))).isEqualTo(true);
        assertThat(board.isExistSameType(Position.from(1, 9), new Rook(Team.HAN))).isEqualTo(true);
        assertThat(board.isExistSameType(Position.from(1, 4), new Guard(Team.HAN))).isEqualTo(true);
        assertThat(board.isExistSameType(Position.from(1, 6), new Guard(Team.HAN))).isEqualTo(true);
        assertThat(board.isExistSameType(Position.from(1, 5), new King(Team.HAN))).isEqualTo(true);

        assertThat(board.isExistSameType(Position.from(3, 2), new Cannon(Team.HAN))).isEqualTo(true);
        assertThat(board.isExistSameType(Position.from(3, 8), new Cannon(Team.HAN))).isEqualTo(true);

        assertThat(board.isExistSameType(Position.from(4, 1), new Pawn(Team.HAN))).isEqualTo(true);
        assertThat(board.isExistSameType(Position.from(4, 3), new Pawn(Team.HAN))).isEqualTo(true);
        assertThat(board.isExistSameType(Position.from(4, 5), new Pawn(Team.HAN))).isEqualTo(true);
        assertThat(board.isExistSameType(Position.from(4, 7), new Pawn(Team.HAN))).isEqualTo(true);
        assertThat(board.isExistSameType(Position.from(4, 9), new Pawn(Team.HAN))).isEqualTo(true);
    }

    /**
     * 2. 초나라 기본 기물이 올바르게 배치된다. (상,마 제외)
     */
    @Test
    void 초나라_기본_기물들이_올바르게_배치된다() {
        Board board = new Board(noElephantHorseStrategy, noElephantHorseStrategy);

        assertThat(board.isExistSameType(Position.from(10, 1), new Rook(Team.CHO))).isEqualTo(true);
        assertThat(board.isExistSameType(Position.from(10, 4), new Guard(Team.CHO))).isEqualTo(true);
        assertThat(board.isExistSameType(Position.from(10, 5), new King(Team.CHO))).isEqualTo(true);
        assertThat(board.isExistSameType(Position.from(10, 6), new Guard(Team.CHO))).isEqualTo(true);
        assertThat(board.isExistSameType(Position.from(10, 9), new Rook(Team.CHO))).isEqualTo(true);

        assertThat(board.isExistSameType(Position.from(8, 2), new Cannon(Team.CHO))).isEqualTo(true);
        assertThat(board.isExistSameType(Position.from(8, 8), new Cannon(Team.CHO))).isEqualTo(true);

        assertThat(board.isExistSameType(Position.from(7, 1), new Pawn(Team.CHO))).isEqualTo(true);
        assertThat(board.isExistSameType(Position.from(7, 3), new Pawn(Team.CHO))).isEqualTo(true);
        assertThat(board.isExistSameType(Position.from(7, 5), new Pawn(Team.CHO))).isEqualTo(true);
        assertThat(board.isExistSameType(Position.from(7, 7), new Pawn(Team.CHO))).isEqualTo(true);
        assertThat(board.isExistSameType(Position.from(7, 9), new Pawn(Team.CHO))).isEqualTo(true);
    }

    /**
     * 상마상마 차림 검증 로직
     */
    /**
     * 1. 초나라가 상마상마를 선택한 경우, 보드판에 올바르게 배치된다.
     */
    @Test
    void 초나라의_상마상마_상차림을_올바르게_배치한다() {
        // given
        InitializeStrategy strategy = new LeftElephantFormationStrategy();

        // when
        Board board = new Board(strategy, strategy);

        // then
        Position choFirstElephant = Position.from(10, 2);
        Position choSecondElephant = Position.from(10, 7);
        Position choFirstHorse = Position.from(10, 3);
        Position choSecondHorse = Position.from(10, 8);

        assertThat(board.isExistSameType(choFirstElephant, new Elephant(Team.CHO))).isEqualTo(true);
        assertThat(board.isExistSameType(choSecondElephant, new Elephant(Team.CHO))).isEqualTo(true);
        assertThat(board.isExistSameType(choFirstHorse, new Horse(Team.CHO))).isEqualTo(true);
        assertThat(board.isExistSameType(choSecondHorse, new Horse(Team.CHO))).isEqualTo(true);
    }

    /**
     * 2. 초나라가 상마마상을 선택한 경우, 보드판에 올바르게 배치된다.
     */
    @Test
    void 초나라의_상마마상_상차림을_올바르게_배치한다() {
        // given
        InitializeStrategy strategy = new OuterElephantFormationStrategy();

        // when
        Board board = new Board(strategy, strategy);

        // then
        Position choFirstElephant = Position.from(10, 2);
        Position choSecondElephant = Position.from(10, 8);
        Position choFirstHorse = Position.from(10, 3);
        Position choSecondHorse = Position.from(10, 7);

        assertThat(board.isExistSameType(choFirstElephant, new Elephant(Team.CHO))).isEqualTo(true);
        assertThat(board.isExistSameType(choSecondElephant, new Elephant(Team.CHO))).isEqualTo(true);
        assertThat(board.isExistSameType(choFirstHorse, new Horse(Team.CHO))).isEqualTo(true);
        assertThat(board.isExistSameType(choSecondHorse, new Horse(Team.CHO))).isEqualTo(true);
    }

    /**
     * 3. 초나라가 마상마상을 선택한 경우, 보드판에 올바르게 배치된다.
     */
    @Test
    void 초나라의_마상마상_상차림을_올바르게_배치한다() {
        // given
        InitializeStrategy strategy = new RightElephantFormationStrategy();

        // when
        Board board = new Board(strategy, strategy);

        // then
        Position choFirstElephant = Position.from(10, 3);
        Position choSecondElephant = Position.from(10, 8);
        Position choFirstHorse = Position.from(10, 2);
        Position choSecondHorse = Position.from(10, 7);

        assertThat(board.isExistSameType(choFirstElephant, new Elephant(Team.CHO))).isEqualTo(true);
        assertThat(board.isExistSameType(choSecondElephant, new Elephant(Team.CHO))).isEqualTo(true);
        assertThat(board.isExistSameType(choFirstHorse, new Horse(Team.CHO))).isEqualTo(true);
        assertThat(board.isExistSameType(choSecondHorse, new Horse(Team.CHO))).isEqualTo(true);
    }

    /**
     * 4. 초나라가 마상상마를 선택한 경우, 보드판에 올바르게 배치된다.
     */
    @Test
    void 초나라의_마상상마_상차림을_올바르게_배치한다() {
        // given
        InitializeStrategy strategy = new InnerElephantFormationStrategy();

        // when
        Board board = new Board(strategy, strategy);

        // then
        Position choFirstElephant = Position.from(10, 3);
        Position choSecondElephant = Position.from(10, 7);
        Position choFirstHorse = Position.from(10, 2);
        Position choSecondHorse = Position.from(10, 8);

        assertThat(board.isExistSameType(choFirstElephant, new Elephant(Team.CHO))).isEqualTo(true);
        assertThat(board.isExistSameType(choSecondElephant, new Elephant(Team.CHO))).isEqualTo(true);
        assertThat(board.isExistSameType(choFirstHorse, new Horse(Team.CHO))).isEqualTo(true);
        assertThat(board.isExistSameType(choSecondHorse, new Horse(Team.CHO))).isEqualTo(true);
    }

    /**
     * 5. 한나라가 상마상마를 선택한 경우, 보드판에 올바르게 배치된다.
     */
    @Test
    void 한나라의_상마상마_상차림을_올바르게_배치한다() {
        // given
        InitializeStrategy strategy = new LeftElephantFormationStrategy();

        // when
        Board board = new Board(strategy, strategy);

        // then
        Position hanFirstElephant = Position.from(1, 3);
        Position hanSecondElephant = Position.from(1, 8);
        Position hanFirstHorse = Position.from(1, 2);
        Position hanSecondHorse = Position.from(1, 7);

        assertThat(board.isExistSameType(hanFirstElephant, new Elephant(Team.HAN))).isEqualTo(true);
        assertThat(board.isExistSameType(hanSecondElephant, new Elephant(Team.HAN))).isEqualTo(true);
        assertThat(board.isExistSameType(hanFirstHorse, new Horse(Team.HAN))).isEqualTo(true);
        assertThat(board.isExistSameType(hanSecondHorse, new Horse(Team.HAN))).isEqualTo(true);
    }

    /**
     * 6. 한나라가 상마마상을 선택한 경우, 보드판에 올바르게 배치된다.
     */
    @Test
    void 한나라의_상마마상_상차림을_올바르게_배치한다() {
        // given
        InitializeStrategy strategy = new OuterElephantFormationStrategy();

        // when
        Board board = new Board(strategy, strategy);

        // then
        Position hanFirstElephant = Position.from(1, 2);
        Position hanSecondElephant = Position.from(1, 8);
        Position hanFirstHorse = Position.from(1, 3);
        Position hanSecondHorse = Position.from(1, 7);

        assertThat(board.isExistSameType(hanFirstElephant, new Elephant(Team.HAN))).isEqualTo(true);
        assertThat(board.isExistSameType(hanSecondElephant, new Elephant(Team.HAN))).isEqualTo(true);
        assertThat(board.isExistSameType(hanFirstHorse, new Horse(Team.HAN))).isEqualTo(true);
        assertThat(board.isExistSameType(hanSecondHorse, new Horse(Team.HAN))).isEqualTo(true);
    }

    /**
     * 7. 한나라가 마상마상을 선택한 경우, 보드판에 올바르게 배치된다.
     */
    @Test
    void 한나라의_마상마상_상차림을_올바르게_배치한다() {
        // given
        InitializeStrategy strategy = new RightElephantFormationStrategy();

        // when
        Board board = new Board(strategy, strategy);

        // then
        Position hanFirstElephant = Position.from(1, 2);
        Position hanSecondElephant = Position.from(1, 7);
        Position hanFirstHorse = Position.from(1, 3);
        Position hanSecondHorse = Position.from(1, 8);

        assertThat(board.isExistSameType(hanFirstElephant, new Elephant(Team.HAN))).isEqualTo(true);
        assertThat(board.isExistSameType(hanSecondElephant, new Elephant(Team.HAN))).isEqualTo(true);
        assertThat(board.isExistSameType(hanFirstHorse, new Horse(Team.HAN))).isEqualTo(true);
        assertThat(board.isExistSameType(hanSecondHorse, new Horse(Team.HAN))).isEqualTo(true);
    }

    /**
     * 8. 한나라가 마상상마를 선택한 경우, 보드판에 올바르게 배치된다.
     */
    @Test
    void 한나라의_마상상마_상차림을_올바르게_배치한다() {
        // given
        InitializeStrategy strategy = new InnerElephantFormationStrategy();

        // when
        Board board = new Board(strategy, strategy);

        // then
        Position hanFirstElephant = Position.from(1, 3);
        Position hanSecondElephant = Position.from(1, 7);
        Position hanFirstHorse = Position.from(1, 2);
        Position hanSecondHorse = Position.from(1, 8);

        assertThat(board.isExistSameType(hanFirstElephant, new Elephant(Team.HAN))).isEqualTo(true);
        assertThat(board.isExistSameType(hanSecondElephant, new Elephant(Team.HAN))).isEqualTo(true);
        assertThat(board.isExistSameType(hanFirstHorse, new Horse(Team.HAN))).isEqualTo(true);
        assertThat(board.isExistSameType(hanSecondHorse, new Horse(Team.HAN))).isEqualTo(true);
    }

    /**
     * 보드 이동 예외 테스트 1. 출발 지점에, 원하는 피스가 아에 없는 경우 이동할 수 없다. 2. 출발 지점에, 원하는 피스 타입이 아닌 다른 피스가 있는 경우 이동 할 수 없다. 3. 도착 지점이
     * 보드판의 범위를 넘어서는 경우 이동할 수 없다.
     */
    @Test
    void 출발_지점에_원하는_피스가_없는_경우_이동할_수_없다() {
        // given
        Board board = new Board();
        PieceType targetType = PAWN;

        // when
        Position from = Position.from(5, 1);
        Position to = Position.from(5, 2);

        // then
        assertThatThrownBy(() -> board.move(from, to, targetType, Team.CHO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치에 피스가 없습니다.");
    }

    @Test
    void 출발_지점에_원하는_피스_타입이_아닌_다른_피스가_있는_경우_이동_할_수_없다() {
        // given
        PieceType targetType = PAWN;

        // when
        Position from = Position.from(5, 1);
        Position to = Position.from(5, 2);

        Map<Position, Piece> testPiece = new HashMap<>();
        testPiece.put(from, new Cannon(Team.CHO));

        Board board = new Board(testPiece);

        // then
        assertThatThrownBy(() -> board.move(from, to, targetType, Team.CHO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치에 해당 타입이 없습니다.");
    }

    @Test
    void 출발_지점에_다른팀_피스가_있는_경우_이동_할_수_없다() {
        // given
        PieceType targetType = PAWN;

        // when
        Position from = Position.from(5, 1);
        Position to = Position.from(5, 2);

        Map<Position, Piece> testPiece = new HashMap<>();
        testPiece.put(from, new Cannon(Team.CHO));

        Board board = new Board(testPiece);

        // then
        assertThatThrownBy(() -> board.move(from, to, targetType, Team.HAN))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치에 해당 타입이 없습니다.");
    }

    @Test
    void 도착_지점이_보드판의_범위를_넘어서는_경우_이동할_수_없다() {
        // given
        PieceType targetType = PAWN;

        // when
        Position from = Position.from(10, 1);
        Position to = Position.from(10, 10);

        Map<Position, Piece> testPiece = new HashMap<>();
        testPiece.put(from, new Pawn(Team.CHO));

        Board board = new Board(testPiece);

        // then
        assertThatThrownBy(() -> board.move(from, to, targetType, Team.CHO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("기물의 도착지점이 판 범위를 넘어섰습니다.");
    }

    /**
     * 보드 이동 정상 테스트
     */
    @Test
    void 포가_올바른_위치에_이동된다() {
        Position from = Position.from(5, 1);
        Position to = Position.from(5, 4);
        Position between = Position.from(5, 2);

        Map<Position, Piece> testPiece = new HashMap<>();
        testPiece.put(from, new Cannon(Team.CHO));
        testPiece.put(between, new Pawn(Team.CHO));

        Board board = new Board(testPiece);

        // then
        assertThatCode(() -> board.move(from, to, PieceType.CANNON, Team.CHO))
                .doesNotThrowAnyException();
    }

    @Test
    void 차가_올바른_위치에_이동된다() {
        Position from = Position.from(10, 1);
        Position to = Position.from(10, 3);

        Map<Position, Piece> testPiece = new HashMap<>();
        testPiece.put(from, new Rook(Team.CHO));

        Board board = new Board(testPiece);

        // then
        assertThatCode(() -> board.move(from, to, PieceType.ROOK, Team.CHO))
                .doesNotThrowAnyException();
    }

    @Test
    void 사가_한칸_앞으로_올바른_위치에_이동된다() {
        Position from = Position.from(10, 4);
        Position to = Position.from(9, 4);

        Map<Position, Piece> testPiece = new HashMap<>();
        testPiece.put(from, new Guard(Team.CHO));

        Board board = new Board(testPiece);

        // then
        assertThatCode(() -> board.move(from, to, PieceType.GUARD, Team.CHO))
                .doesNotThrowAnyException();
    }

    @Test
    void 궁이_한칸_앞으로_올바른_위치에_이동된다() {
        Position from = Position.from(9, 5);
        Position to = Position.from(8, 5);

        Map<Position, Piece> testPiece = new HashMap<>();
        testPiece.put(from, new King(Team.CHO));

        Board board = new Board(testPiece);

        // then
        assertThatCode(() -> board.move(from, to, PieceType.KING, Team.CHO))
                .doesNotThrowAnyException();
    }

    @Test
    void 졸이_한칸_앞으로_올바른_위치에_이동된다() {
        Position from = Position.from(7, 1);
        Position to = Position.from(6, 1);

        Map<Position, Piece> testPiece = new HashMap<>();
        testPiece.put(from, new Pawn(Team.CHO));

        Board board = new Board(testPiece);

        // then
        assertThatCode(() -> board.move(from, to, PAWN, Team.CHO))
                .doesNotThrowAnyException();
    }

    @Test
    void 말이_한칸_앞으로_올바른_위치에_이동된다() {
        Position from = Position.from(10, 1);
        Position to = Position.from(8, 2);

        Map<Position, Piece> testPiece = new HashMap<>();
        testPiece.put(from, new Horse(Team.CHO));

        Board board = new Board(testPiece);

        // then
        assertThatCode(() -> board.move(from, to, PieceType.HORSE, Team.CHO))
                .doesNotThrowAnyException();
    }

    @Test
    void 상이_한칸_앞으로_올바른_위치에_이동된다() {
        Position from = Position.from(10, 3);
        Position to = Position.from(7, 5);

        Map<Position, Piece> testPiece = new HashMap<>();
        testPiece.put(from, new Elephant(Team.CHO));

        Board board = new Board(testPiece);

        // then
        assertThatCode(() -> board.move(from, to, PieceType.ELEPHANT, Team.CHO))
                .doesNotThrowAnyException();
    }

    @Test
    void 킹이_잡힐경우_게임이_종료된다() {
        Position from = Position.from(10, 4);
        Position to = Position.from(10, 5);

        Map<Position, Piece> testPiece = new HashMap<>();
        testPiece.put(from, new Pawn(Team.HAN));
        testPiece.put(to, new King(Team.CHO));

        Board board = new Board(testPiece);
        board.move(from, to, PieceType.PAWN, Team.HAN);

        assertThat(board.canNextTurn()).isFalse();
    }

    @Test
    void 보드_초기_생성시_점수_확인() {
        // given
        InitializeStrategy strategy = new OuterElephantFormationStrategy();

        // when
        Board board = new Board(strategy, strategy);

        // then
        assertThat(board.calculateScore(Team.CHO)).isEqualTo(72);
        assertThat(board.calculateScore(Team.HAN)).isEqualTo(73.5);
    }

    @Test
    void 상대편_기물을_잡았을때_점수_반영_테스트() {
        // given
        Map<Position, Piece> testPieces = new HashMap<>();
        Position from = Position.from(6,1);
        Position to = Position.from(5,1);

        testPieces.put(from, new Pawn(Team.CHO));
        testPieces.put(to, new Pawn(Team.HAN));
        Board testBoard = new Board(testPieces);

        // when
        testBoard.move(from, to, PieceType.PAWN, Team.CHO);

        // then
        assertThat(testBoard.calculateScore(Team.CHO)).isEqualTo(2);
        assertThat(testBoard.calculateScore(Team.HAN)).isEqualTo(1.5);
    }
}
