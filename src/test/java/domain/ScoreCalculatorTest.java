package domain;

import domain.board.Board;
import domain.board.BoardStatus;
import domain.piece.Byeong;
import domain.piece.Cha;
import domain.piece.Jang;
import domain.piece.Jol;
import domain.piece.Ma;
import domain.piece.Piece;
import domain.piece.Po;
import domain.piece.Sa;
import domain.piece.Sang;
import domain.piece.Team;
import domain.piece.strategy.ByeongMoveStrategy;
import domain.piece.strategy.JolMoveStrategy;
import domain.piece.strategy.MaMoveStrategy;
import domain.piece.strategy.SangMoveStrategy;
import domain.piece.strategy.SingleStepMoveStrategy;
import domain.piece.strategy.SlidingMoveStrategy;
import domain.piece.strategy.component.PalaceMoveRule;
import domain.position.Position;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ScoreCalculatorTest {

    private final ScoreCalculator scoreCalculator = new ScoreCalculator();

    @Test
    @DisplayName("총 점수를 잘 구한다")
    void calculateGood() {
        //given
        double choMaximumScore = 72;
        double hanMaximumScore = 73.5;
        Board testBoard = Board.of(SettingType.LEFT, SettingType.LEFT);
        BoardStatus testBoardStatus = testBoard.getBoardStatus();

        //when
        JanggiScore result = scoreCalculator.calculate(testBoardStatus);

        //then
        Assertions.assertThat(result.choScore()).isEqualTo(choMaximumScore);
        Assertions.assertThat(result.hanScore()).isEqualTo(hanMaximumScore);
    }

    @ParameterizedTest
    @MethodSource("expectScoresAndPiece")
    @DisplayName("BoardStatus에 특정 기물만 있는 경우에도 점수를 잘 구한다")
    void calculateSuccessOnlyOnePiece(double choExpectedScore, double hanExpectedScore, Piece testPiece) {
        //given
        Map<Position, Piece> testPieces = new HashMap<>();
        testPieces.put(
                Position.of(1, 1),
                testPiece
        );
        BoardStatus testBoardStatus = new BoardStatus(testPieces);

        //when
        JanggiScore result = scoreCalculator.calculate(testBoardStatus);

        //then
        Assertions.assertThat(result.choScore()).isEqualTo(choExpectedScore);
        Assertions.assertThat(result.hanScore()).isEqualTo(hanExpectedScore);
    }

    static Stream<Arguments> expectScoresAndPiece() {
        PalaceMoveRule palaceMoveRule = new PalaceMoveRule();

        return Stream.of(
                // 초(CHO) 진영 기물 - 기물 점수, 덤(0.0), 기물 객체
                Arguments.arguments(13.0, 1.5, new Cha(new SlidingMoveStrategy(palaceMoveRule), Team.CHO)),
                Arguments.arguments(7.0, 1.5, new Po(new SlidingMoveStrategy(palaceMoveRule), Team.CHO)),
                Arguments.arguments(5.0, 1.5, new Ma(new MaMoveStrategy(), Team.CHO)),
                Arguments.arguments(3.0, 1.5, new Sang(new SangMoveStrategy(), Team.CHO)),
                Arguments.arguments(3.0, 1.5, new Sa(new SingleStepMoveStrategy(palaceMoveRule), Team.CHO)),
                Arguments.arguments(2.0, 1.5, new Jol(new JolMoveStrategy(palaceMoveRule), Team.CHO)),
                Arguments.arguments(0.0, 1.5, new Jang(new SingleStepMoveStrategy(palaceMoveRule), Team.CHO)),

                // 한(HAN) 진영 기물 - 기물 점수, 덤(1.5), 기물 객체
                Arguments.arguments(0.0, 14.5, new Cha(new SlidingMoveStrategy(palaceMoveRule), Team.HAN)),
                Arguments.arguments(0.0, 8.5, new Po(new SlidingMoveStrategy(palaceMoveRule), Team.HAN)),
                Arguments.arguments(0.0, 6.5, new Ma(new MaMoveStrategy(), Team.HAN)),
                Arguments.arguments(0.0, 4.5, new Sang(new SangMoveStrategy(), Team.HAN)),
                Arguments.arguments(0.0, 4.5, new Sa(new SingleStepMoveStrategy(palaceMoveRule), Team.HAN)),
                Arguments.arguments(0.0, 3.5, new Byeong(new ByeongMoveStrategy(palaceMoveRule), Team.HAN)),
                Arguments.arguments(0.0, 1.5, new Jang(new SingleStepMoveStrategy(palaceMoveRule), Team.HAN))
        );
    }
}
