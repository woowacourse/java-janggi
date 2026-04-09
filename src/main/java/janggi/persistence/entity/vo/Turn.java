package janggi.persistence.entity.vo;

import janggi.domain.Camp;

public enum Turn {
    CHO,
    HAN;

    public static Turn of(Camp camp) {
        if (camp.isCho()) {
            return Turn.CHO;
        }
        return Turn.HAN;
    }

    public boolean isCho() {
        return this == CHO;
    }
}
