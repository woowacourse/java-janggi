package view;

import domain.game.Team;
import domain.piece.PieceDefinition;

public interface PieceAppearance {
    String colorize(Team team, PieceDefinition type);

    String colorizeEmpty();
}
