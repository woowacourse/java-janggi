package dao.dto;

import domain.board.BoardLocation;
import domain.piece.Piece;

public record CreatePieceDto(
        int x,
        int y,
        String type,
        String team,
        double score,
        Long janggiGameId
) {

    public static CreatePieceDto of(BoardLocation boardLocation, Piece piece, Long janggiGameId) {
        return new CreatePieceDto(
                boardLocation.x(),
                boardLocation.y(),
                piece.getType().name(),
                piece.getTeam().name(),
                piece.getScore().score(),
                janggiGameId
        );
    }
}
