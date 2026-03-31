package janggi.domain.strategy;

import janggi.domain.position.Position;
import janggi.domain.space.Space;
import java.util.Map;

public interface InitializeStrategy {
    void basicSetting(Map<Position, Space> piecesInfo);
}
