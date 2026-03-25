package janggi.domain;

import janggi.domain.side.Chu;
import janggi.domain.side.Han;
import janggi.domain.side.Team;

public class Board {

    private final Team chu;
    private final Team han;

    public Board() {
        this.chu = Chu.createInitialChu();
        this.han = Han.createInitialHan();
    }
}
