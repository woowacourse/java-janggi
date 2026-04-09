package view.dto;

import domain.piece.Piece;
import view.PieceView;
import view.TeamColor;

public record PieceViewDto(
        PieceView pieceView,
        TeamColor teamColor
) {

    private static final String RESET = "\u001B[0m";

    public static PieceViewDto from(Piece piece) {
        return new PieceViewDto(PieceView.valueOf(piece), TeamColor.valueOf(piece.team()));
    }

    public String getColoredMessage() {
        return teamColor.getColor() + pieceView.getViewMessage() + RESET;
    }

}
