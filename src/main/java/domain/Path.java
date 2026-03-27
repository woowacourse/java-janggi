package domain;

import domain.board.Position;
import domain.piece.PieceType;
import domain.piece.Team;

public record Path(Position position, PieceType pieceType, Team team) {
}
