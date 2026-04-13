package janggi.domain.game;

import janggi.domain.piece.camp.CampType;

public enum GameStatus {

    PLAYING,
    CHO_WIN,
    HAN_WIN;

    public static GameStatus changeByCamp(CampType campType) {
        if (campType == CampType.CHO) {
            return CHO_WIN;
        }
        return HAN_WIN;
    }
}
