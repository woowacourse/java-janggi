package dto;
import java.util.Map;
import java.util.stream.Collectors;

import model.Team;
import model.board.Board;
import model.piece.Piece;

public record BoardDto(
    Map<InnerPosition, String> pieces
) {

    private static final String HAN_COLOR_CODE = "\u001B[31m";
    private static final String CHO_COLOR_CODE = "\u001B[34m";
    private static final String EXIT_COLOR_CODE = "\u001B[0m";

    public static BoardDto from(Board board) {
        return new BoardDto(
            board.getPieces().stream()
                .collect(Collectors.toMap(
                    piece -> new InnerPosition(piece.getPosition().x(), piece.getPosition().y()),
                    BoardDto::toName)));
    }

    private static String toName(Piece piece) {
        PieceDto dto = PieceDto.from(piece);
        StringBuilder name = new StringBuilder();
        if (piece.getTeam() == Team.HAN) {
            name.append(HAN_COLOR_CODE);
        }
        if (piece.getTeam() == Team.CHO) {
            name.append(CHO_COLOR_CODE);
        }
        name.append(dto.getName());
        name.append(EXIT_COLOR_CODE);
        return name.toString();
    }

    public record InnerPosition(
        int x,
        int y
    ) {

    }
}
