package janggi.domain.strategy;

import janggi.domain.board.Position;
import janggi.domain.board.Space;
import java.util.Map;

public interface InitializeStrategy {
    void basicSetting(Map<Position, Space> piecesInfo);
}
