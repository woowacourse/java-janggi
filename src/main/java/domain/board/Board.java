package domain.board;

import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Position;
import domain.player.Team;
import dto.PieceInfoDto;
import dto.PieceInfosDto;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {

    protected static final int MIN_ROW_RANGE = 1;
    protected static final int MAX_ROW_RANGE = 10;
    protected static final int MIN_COLUMN_RANGE = 1;
    protected static final int MAX_COLUMN_RANGE = 9;

    private static final String EMPTY_POSITION = "기물이 존재하지 않는 칸입니다.";
    private static final String GENERAL_NOWHERE = "궁이 존재하지 않습니다.";

    private final Map<Position, Piece> pieces;

    Board(final Map<Position, Piece> pieces) {
        this.pieces = pieces;
    }

    public static Board of(Map<Position, Piece> pieces) {
        return new Board(new HashMap<>(pieces));
    }


    public boolean inBoard(final Position current) {
        return (current.row() >= Board.MIN_ROW_RANGE && current.row() <= Board.MAX_ROW_RANGE)
                && (current.column() >= Board.MIN_COLUMN_RANGE && current.column() <= Board.MAX_COLUMN_RANGE);
    }

    public boolean hasPiece(Position position) {
        return pieces.containsKey(position);
    }


    public Position findGeneral(Team team) {
        return pieces.entrySet().stream()
                .filter(entry -> entry.getValue().getPieceType() == PieceType.GENERAL)
                .filter(entry -> entry.getValue().isSameTeam(team))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(GENERAL_NOWHERE));
    }

    public PieceInfosDto getPieceInfos() {
        List<PieceInfoDto> pieceInfos = pieces.entrySet().stream()
                .map(entry -> PieceInfoDto.of(entry.getValue(), entry.getKey()))
                .toList();
        return PieceInfosDto.of(pieceInfos);
    }

    public Piece getPiece(Position position) {
        if (hasPiece(position)) {
            return pieces.get(position);
        }
        throw new IllegalStateException(EMPTY_POSITION);
    }

    public int getMinRowRange() {
        return MIN_ROW_RANGE;
    }

    public int getMaxRowRange() {
        return MAX_ROW_RANGE;
    }

    public int getMinColumnRange() {
        return MIN_COLUMN_RANGE;
    }

    public int getMaxColumnRange() {
        return MAX_COLUMN_RANGE;
    }
}
