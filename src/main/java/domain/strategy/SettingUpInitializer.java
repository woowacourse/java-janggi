package domain.strategy;

import domain.Player;
import domain.piece.Pieces;

public interface SettingUpInitializer {
    Pieces initPieces(final Player player);
}
