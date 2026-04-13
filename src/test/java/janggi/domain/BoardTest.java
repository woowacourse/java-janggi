package janggi.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import janggi.domain.strategy.BasicPlacementStrategy;
import janggi.domain.strategy.InitializeStrategy;
import org.junit.jupiter.api.Test;

class BoardTest {

    @Test
    void 보드_초기화_정상_테스트() {

        InitializeStrategy strategy = new BasicPlacementStrategy();

        assertDoesNotThrow(() -> new Board(strategy));
    }

    @Test
    void 보드_초기화시_크기_확인_테스트() {
        InitializeStrategy strategy = new BasicPlacementStrategy();
        Board board = new Board(strategy);


        int actualHeight = board.getBoardHeight();
        int actualWidth = board.getBoardWidth();

        int expectHeight = 10;
        int expectWidth = 9;

        assertThat(actualHeight).isEqualTo(expectHeight);
        assertThat(actualWidth).isEqualTo(expectWidth);
    }

    @Test
    void 보드_초기화시_초팀_점수_계산_정상_테스트(){
        InitializeStrategy strategy = new BasicPlacementStrategy();
        Board board = new Board(strategy);

        double actual = board.getChoScore();

        double expect = 73.5;
        assertThat(actual).isEqualTo(expect);
    }

    @Test
    void 보드_초기화시_한팀_점수_계산_정상_테스트(){
        InitializeStrategy strategy = new BasicPlacementStrategy();
        Board board = new Board(strategy);

        double actual = board.getHanScore();

        double expect = 72;
        assertThat(actual).isEqualTo(expect);
    }
}