package janggi.domain.board;

import janggi.domain.piece.Camp;

public interface BoardGenerator {

    Board generate(Camp baseCamp);
}
