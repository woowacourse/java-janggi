package domain.piece;

import domain.game.Team;

public interface PieceAppearance {
    String colorize(Team team, PieceDefinition type);

    String colorizeEmpty();
}
