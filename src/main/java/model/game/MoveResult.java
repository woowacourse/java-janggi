package model.game;

import model.coordinate.Position;
import model.piece.Piece;

import java.util.Map;
import java.util.Optional;

public record MoveResult(Map<Position, Piece> board, Optional<Team> winner) {
}
