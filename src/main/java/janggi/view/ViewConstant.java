package janggi.view;

import janggi.piece.Type;
import java.util.Map;

public final class ViewConstant {
    public static final Map<Type, String> pieceNotations = Map.of(
            Type.GENERAL, "k",
            Type.CHARIOT, "c",
            Type.CANNON, "p",
            Type.HORSE, "h",
            Type.ELEPHANT, "e",
            Type.GUARD, "g",
            Type.SOLDIER, "s");
}
