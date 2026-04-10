package janggi.domain.piece;

import janggi.exception.business.InvalidPieceTypeException;

public enum Team {
    CHO(),
    HAN();

    public Team switchTeam() {
        if (this == Team.CHO) {
            return Team.HAN;
        }
        return Team.CHO;
    }

    public static Team from(String name) {
        try {
            return Team.valueOf(name.trim().toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new InvalidPieceTypeException();
        }
    }
}
