package janggi.domain.piece;

import janggi.domain.Camp;

public class PieceName {
    private final String choName;
    private final String hanName;

    public PieceName(String choName, String hanName) {
        this.choName = choName;
        this.hanName = hanName;
    }

    public String of(Camp camp) {
        if (camp.isCho()) {
            return choName;
        }
        return hanName;
    }
}
