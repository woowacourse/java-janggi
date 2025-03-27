package janggi.domain.rule.block;

import janggi.domain.Placement;
import janggi.domain.Route;

public interface BlockStrategy {
    void validateIsBlock(Placement placement, Route route);
}
