package janggi.view;

import janggi.piece.Cannon;
import janggi.piece.Chariot;
import janggi.piece.Elephant;
import janggi.piece.General;
import janggi.piece.Guard;
import janggi.piece.Horse;
import janggi.piece.Piece;
import janggi.piece.Soldier;
import java.util.Map;

public final class ViewConstant {
    public static final Map<Class<? extends Piece>, String> pieceNotations = Map.of(
            General.class, "k",
            Chariot.class, "c",
            Cannon.class, "p",
            Horse.class, "h",
            Elephant.class, "e",
            Guard.class, "g",
            Soldier.class, "s");
}
