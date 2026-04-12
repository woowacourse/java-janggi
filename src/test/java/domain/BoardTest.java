package domain;

import domain.piece.Cannon;
import domain.piece.Elephant;
import domain.piece.Guard;
import domain.piece.Horse;
import domain.piece.King;
import domain.piece.Pawn;
import domain.piece.Piece;
import domain.piece.Rook;
import domain.strategy.NoInitializeStrategy;
import domain.stub.StubBoard;

import exception.GameErrorMessage;
import exception.custom.InvalidGameInputException;

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
    private final InitializeStrategy noInitializeStrategy = new NoInitializeStrategy();
    private final InitializeStrategy noElephantHorseStrategy = new NoOpElephantHorseStrategy();

    static class NoOpElephantHorseStrategy extends InitializeStrategy {
        @Override
        protected Map<Position, Piece> initializeElephantHorseFormation(Team team) {
            return Collections.emptyMap();
        }
    }

    public Map<Team, InitializeStrategy> createStrategies(InitializeStrategy choInitializeStrategy,
                                                          InitializeStrategy hanInitializeStrategy) {
        return Map.of(Team.CHO, choInitializeStrategy, Team.HAN, hanInitializeStrategy);
    }

    /**
     * 1. 한나라 기본 기물이 올바르게 배치된다.(상,마 제외)
     */
    @Test
    void 한나라_기본_기물들이_올바르게_배치된다() {
        Board board = new Board(createStrategies(noElephantHorseStrategy, noElephantHorseStrategy));

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
        Board board = new Board(createStrategies(noElephantHorseStrategy, noElephantHorseStrategy));

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
        Board board = new Board(createStrategies(strategy, strategy));

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
        Board board = new Board(createStrategies(strategy, strategy));

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
        Board board = new Board(createStrategies(strategy, strategy));

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
        Board board = new Board(createStrategies(strategy, strategy));

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
        Board board = new Board(createStrategies(strategy, strategy));

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
        Board board = new Board(createStrategies(strategy, strategy));

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
        Board board = new Board(createStrategies(strategy, strategy));

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
        Board board = new Board(createStrategies(strategy, strategy));

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
     * 보드 이동 예외 테스트
     * 1. 출발 지점에, 원하는 피스가 아에 없는 경우 이동할 수 없다.
     * 2. 도착 지점이 보드판의 범위를 넘어서는 경우 이동할 수 없다.
     */
    @Test
    void 출발_지점에_피스가_없는_경우_이동할_수_없다() {
        // given
        StubBoard board = new StubBoard(noInitializeStrategy);

        // when
        Position from = Position.from(5, 1);
        Position to = Position.from(5, 2);

        // then
        assertThatThrownBy(() -> board.move(from, to, Team.CHO))
                .isInstanceOf(InvalidGameInputException.class)
                .hasMessage(GameErrorMessage.PIECE_NOT_FOUND.getMessage());
    }

    @Test
    void 도착_지점이_보드판의_범위를_넘어서는_경우_이동할_수_없다() {
        // given
        StubBoard board = new StubBoard(noInitializeStrategy);

        // when
        Position from = Position.from(10, 1);
        Position to = Position.from(10, 10);

        Map<Position, Piece> testPiece = new HashMap<>();
        testPiece.put(from, new Pawn(Team.CHO));

        board.putPieces(testPiece);

        // then
        assertThatThrownBy(() -> board.move(from, to, Team.CHO))
                .isInstanceOf(InvalidGameInputException.class)
                .hasMessage(GameErrorMessage.INVALID_POSITION_RANGE.getMessage());
    }

    /**
     * 보드 이동 정상 테스트
     */
    @Test
    void 포가_올바른_위치에_이동된다() {
        // given
        StubBoard board = new StubBoard(noInitializeStrategy);

        // when
        Position from = Position.from(5, 1);
        Position to = Position.from(5, 4);
        Position between = Position.from(5, 2);

        Map<Position, Piece> testPiece = new HashMap<>();
        testPiece.put(from, new Cannon(Team.CHO));
        testPiece.put(between, new Pawn(Team.CHO));

        board.putPieces(testPiece);

        // then
        assertThatCode(() -> board.move(from, to, Team.CHO))
                .doesNotThrowAnyException();
    }

    @Test
    void 차가_올바른_위치에_이동된다() {
        // given
        StubBoard board = new StubBoard(noInitializeStrategy);

        // when
        Position from = Position.from(10, 1);
        Position to = Position.from(10, 3);

        Map<Position, Piece> testPiece = new HashMap<>();
        testPiece.put(from, new Rook(Team.CHO));

        board.putPieces(testPiece);

        // then
        assertThatCode(() -> board.move(from, to, Team.CHO))
                .doesNotThrowAnyException();
    }

    @Test
    void 사가_한칸_앞으로_올바른_위치에_이동된다() {
        // given
        StubBoard board = new StubBoard(noInitializeStrategy);

        // when
        Position from = Position.from(10, 4);
        Position to = Position.from(9, 4);

        Map<Position, Piece> testPiece = new HashMap<>();
        testPiece.put(from, new Guard(Team.CHO));

        board.putPieces(testPiece);

        // then
        assertThatCode(() -> board.move(from, to, Team.CHO))
                .doesNotThrowAnyException();
    }

    @Test
    void 궁이_한칸_앞으로_올바른_위치에_이동된다() {
        // given
        StubBoard board = new StubBoard(noInitializeStrategy);

        // when
        Position from = Position.from(9, 5);
        Position to = Position.from(8, 5);

        Map<Position, Piece> testPiece = new HashMap<>();
        testPiece.put(from, new King(Team.CHO));

        board.putPieces(testPiece);

        // then
        assertThatCode(() -> board.move(from, to, Team.CHO))
                .doesNotThrowAnyException();
    }

    @Test
    void 졸이_한칸_앞으로_올바른_위치에_이동된다() {
        // given
        StubBoard board = new StubBoard(noInitializeStrategy);

        // when
        Position from = Position.from(7, 1);
        Position to = Position.from(6, 1);

        Map<Position, Piece> testPiece = new HashMap<>();
        testPiece.put(from, new Pawn(Team.CHO));

        board.putPieces(testPiece);

        // then
        assertThatCode(() -> board.move(from, to, Team.CHO))
                .doesNotThrowAnyException();
    }

    @Test
    void 말이_한칸_앞으로_올바른_위치에_이동된다() {
        // given
        StubBoard board = new StubBoard(noInitializeStrategy);

        // when
        Position from = Position.from(10, 1);
        Position to = Position.from(8, 2);

        Map<Position, Piece> testPiece = new HashMap<>();
        testPiece.put(from, new Horse(Team.CHO));

        board.putPieces(testPiece);

        // then
        assertThatCode(() -> board.move(from, to, Team.CHO))
                .doesNotThrowAnyException();
    }

    @Test
    void 상이_한칸_앞으로_올바른_위치에_이동된다() {
        // given
        StubBoard board = new StubBoard(noInitializeStrategy);

        // when
        Position from = Position.from(10, 3);
        Position to = Position.from(7, 5);

        Map<Position, Piece> testPiece = new HashMap<>();
        testPiece.put(from, new Elephant(Team.CHO));
        board.putPieces(testPiece);

        // then
        assertThatCode(() -> board.move(from, to, Team.CHO))
                .doesNotThrowAnyException();
    }

    /**
     * 보드에 궁 존재 여부 확인 테스트(게임 종료 조건에서 활용)
     */
    @Test
    void 초나라의_궁이_보드판에_존재하는_경우_정상테스트() {
        StubBoard board = new StubBoard(noInitializeStrategy);

        // when
        Position position = Position.from(9, 5);

        Map<Position, Piece> testPiece = new HashMap<>();
        testPiece.put(position, new King(Team.CHO));
        board.putPieces(testPiece);

        // then
        assertThat(board.isExistPiece(PieceType.KING, Team.CHO)).isEqualTo(true);
    }

    @Test
    void 한나라의_궁이_보드판에_존재하는_경우_정상테스트() {
        StubBoard board = new StubBoard(noInitializeStrategy);

        // when
        Position position = Position.from(2, 5);

        Map<Position, Piece> testPiece = new HashMap<>();
        testPiece.put(position, new King(Team.HAN));
        board.putPieces(testPiece);

        // then
        assertThat(board.isExistPiece(PieceType.KING, Team.HAN)).isEqualTo(true);
    }

    @Test
    void 초나라의_궁이_보드판에_존재하지_않는_경우_정상테스트() {
        StubBoard board = new StubBoard(noInitializeStrategy);

        // then
        assertThat(board.isExistPiece(PieceType.KING, Team.CHO)).isEqualTo(false);
    }

    @Test
    void 한나라의_궁이_보드판에_존재하지_않는_경우_정상테스트() {
        StubBoard board = new StubBoard(noInitializeStrategy);

        assertThat(board.isExistPiece(PieceType.KING, Team.HAN)).isEqualTo(false);
    }

    /**
     * 점수 계산 테스트
     */
    @Test
    void 초나라의_모든_기물이_있는_경우_72점이_계산된다() {
        //given
        Board board = new Board(createStrategies(new LeftElephantFormationStrategy(),
                new LeftElephantFormationStrategy()));

        //when
        assertThat(board.getCurrentScoreOfTeam(Team.CHO)).isEqualTo(72);
    }

    @Test
    void 한나라의_모든_기물이_있는_경우_72점이_계산된다() {
        //given
        Board board = new Board(createStrategies(new LeftElephantFormationStrategy(),
                new LeftElephantFormationStrategy()));

        //when
        assertThat(board.getCurrentScoreOfTeam(Team.HAN)).isEqualTo(72);
    }

    @Test
    void 궁만_남아있는_경우_0점이_계산된다() {
        StubBoard board = new StubBoard(noInitializeStrategy);

        Position position = Position.from(9, 5);

        Map<Position, Piece> testPiece = new HashMap<>();
        testPiece.put(position, new King(Team.CHO));
        board.putPieces(testPiece);

        assertThat(board.getCurrentScoreOfTeam(Team.CHO)).isEqualTo(0);
    }

    @Test
    void 기물이_남아있지_않은_경우_0점이_계산된다() {
        StubBoard board = new StubBoard(noInitializeStrategy);

        assertThat(board.getCurrentScoreOfTeam(Team.CHO)).isEqualTo(0);
    }
}
