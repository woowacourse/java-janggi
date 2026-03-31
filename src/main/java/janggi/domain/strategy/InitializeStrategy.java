package janggi.domain.strategy;

import janggi.domain.Position;
import janggi.domain.Space;
import java.util.Map;

public interface InitializeStrategy {
    void basicSetting(Map<Position, Space> piecesInfo);
}
