package domain.piece.rule;

import domain.Moves;
import domain.piece.Position;

public interface Rule {

    void validate(Moves moves, Position src, Position dest);
}
