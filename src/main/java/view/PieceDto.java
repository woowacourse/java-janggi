package view;

import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Team;
import domain.position.Position;
import java.util.Map;
import java.util.Optional;

public class PieceDto {
    private static final Map<Team, Map<PieceType, String>> SYMBOLS = Map.of(
            Team.CHO, Map.of(
                    PieceType.CHA, "차",
                    PieceType.SANG, "상",
                    PieceType.MA, "마",
                    PieceType.SA, "사",
                    PieceType.JANG, "장",
                    PieceType.PO, "포",
                    PieceType.JOL, "졸"
            ),
            Team.HAN, Map.of(
                    PieceType.CHA, "車",
                    PieceType.SANG, "象",
                    PieceType.MA, "馬",
                    PieceType.SA, "士",
                    PieceType.JANG, "漢",
                    PieceType.PO, "包",
                    PieceType.BYEONG, "兵"
            )
    );

    private final int row;
    private final int column;
    private final String description;

    public PieceDto(int row, int column, String description) {
        this.row = row;
        this.column = column;
        this.description = description;
    }

    public static PieceDto toDto(Position position, Piece piece) {
        return new PieceDto(
                position.getRow().value(),
                position.getColumn().value(),
                getSymbol(piece.getTeam(), piece.getPieceType())
        );
    }

    private static String getSymbol(Team team, PieceType type) {
        return Optional.ofNullable(SYMBOLS.get(team))
                .map(map -> map.get(type))
                .orElseThrow(() -> new IllegalArgumentException(ViewErrorMessage.NOT_MATCH_PIECE.getMessage()));
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public String getDescription() {
        return description;
    }
}
