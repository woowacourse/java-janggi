package dto;

import java.util.Map;
import java.util.stream.Collectors;

import janggi.board.Board;
import janggi.piece.Piece;
import console.util.Color;

public record BoardDto(
    Map<PositionDto, String> pieces
) {

    public static BoardDto from(Board board) {
        return new BoardDto(
            board.getPieces().stream()
                .collect(Collectors.toMap(
                    piece -> PositionDto.from(piece.getPosition()),
                    BoardDto::toName)));
    }

    private static String toName(Piece piece) {
        PieceDto pieceDto = PieceDto.from(piece);
        TeamDto teamDto = TeamDto.from(piece.getTeam());

        return Color.apply(teamDto, pieceDto.getName());
    }
}
