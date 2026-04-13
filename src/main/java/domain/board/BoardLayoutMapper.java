package domain.board;

import domain.game.Side;
import java.util.List;
import java.util.Map;

public class BoardLayoutMapper {
    private static final Map<Side, BoardLayout> LAYOUTS = Map.of(
            Side.CHO, new BoardLayout(0, 1, 2, 3, List.of(1, 2, 6, 7)),
            Side.HAN, new BoardLayout(9, 8, 7, 6, List.of(7, 6, 2, 1))
    );

    public BoardLayout get(Side side) {
        BoardLayout boardLayout = LAYOUTS.get(side);
        if (boardLayout == null) {
            throw new IllegalArgumentException("지원하지 않는 진영입니다.");
        }
        return boardLayout;
    }
}
