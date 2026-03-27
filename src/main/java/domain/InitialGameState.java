package domain;

import java.util.List;
import java.util.Map;

public record InitialGameState(
        Map<Position, Piece> boardState,
        List<Piece> choPieces,
        List<Piece> hanPieces
) {}
