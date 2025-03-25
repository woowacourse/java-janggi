package janggi.domain.rule.block;

import janggi.domain.Board;
import janggi.domain.Route;

public interface BlockStrategy {
    void validateIsBlock(final Board board, final Route route);
}
