package janggi.persistence.entity.vo;

import janggi.domain.Camp;
import janggi.domain.Janggi;

public enum Status {
    PLAYING,
    CHO_WIN,
    HAN_WIN;

    public static Status of(Janggi janggi) {
        if (janggi.isRunning()) {
            return Status.PLAYING;
        }
        if (janggi.winner().isCho()) {
            return Status.CHO_WIN;
        }
        return Status.HAN_WIN;
    }

    public boolean isRunning(){
        return this == PLAYING;
    }


}
