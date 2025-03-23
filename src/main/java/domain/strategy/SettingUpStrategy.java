package domain.strategy;

import domain.Player;
import domain.piece.Pieces;

public interface SettingUpStrategy {
    Pieces initPieces(final Player player);
}
