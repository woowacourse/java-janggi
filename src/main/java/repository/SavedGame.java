package repository;

import model.board.Country;

import java.util.List;

public record SavedGame(
        Country turn,
        boolean finished,
        Country winner,
        List<SavedPiece> pieces
) {
}
