package controller.dto;

import domain.PieceType;
import domain.Position;
import domain.Team;

public record CurrentBoardStatus(
        int row, int column, String pieceType, String team
) {
    public static CurrentBoardStatus of(Position position, PieceType pieceType, Team team) {
        return new CurrentBoardStatus(
                position.getRow(),
                position.getColumn(),
                pieceType.getKoreanName(),
                team.getKoreanName()
        );
    }
}
