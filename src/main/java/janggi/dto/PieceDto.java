package janggi.dto;

import janggi.game.Team;
import janggi.piece.PieceInformation;

public record PieceDto(int pieceId,
                       PieceInformation information,
                       Team team,
                       int rowIndex, int columnIndex,
                       boolean isRunning
) {
}
