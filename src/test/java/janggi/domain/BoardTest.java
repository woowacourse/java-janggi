package janggi.domain;

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

}