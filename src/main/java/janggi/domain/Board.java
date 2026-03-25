package janggi.domain;

import janggi.domain.strategy.InitializeStrategy;
import java.util.HashMap;
import java.util.Map;

public class Board {

    private final Map<Position, Space> piecesInfo;

    public Board(InitializeStrategy initializeStrategy) {
        piecesInfo = generateBlankBoard();
        initializeStrategy.basicSetting(piecesInfo);
    }

    private Map<Position, Space> generateBlankBoard() {
        Map<Position, Space> blankBoard = new HashMap<>();

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 9; j++) {
                blankBoard.put(new Position(j, i), new Blank());
            }
        }

        return blankBoard;
    }
}
