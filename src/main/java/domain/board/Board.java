package domain.board;

import domain.piece.Delta;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Position;
import domain.player.Team;

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
    private final Palace palace;

    Board(final Map<Position, Piece> pieces) {
        this.pieces = pieces;
        this.palace = PalaceInitializer.initialize();
    }

    public static Board of(final Map<Position, Piece> pieces) {
        return new Board(new HashMap<>(pieces));
    }


    public void move(final Position from, final Position to) {
        final Piece piece = pieces.remove(from);
        pieces.put(to, piece);
    }


    public boolean inBoard(final Position current) {
        return (current.row() >= Board.MIN_ROW_RANGE && current.row() <= Board.MAX_ROW_RANGE)
                && (current.column() >= Board.MIN_COLUMN_RANGE && current.column() <= Board.MAX_COLUMN_RANGE);
    }

    public boolean hasPiece(final Position position) {
        return pieces.containsKey(position);
    }

    public boolean inPalace(final Position position) {
        return palace.inAnyPalace(position);
    }

    public boolean inAllyPalace(final Position position, final Team team) {
        return palace.inAllyPalace(position, team);
    }


    public Position findGeneral(final Team team) {
        return pieces.entrySet().stream()
                .filter(entry -> entry.getValue().getPieceType() == PieceType.GENERAL)
                .filter(entry -> entry.getValue().isSameTeam(team))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(GENERAL_NOWHERE));
    }

    public List<Position> findPositionsByTeam(final Team team) {
        return pieces.entrySet().stream()
                .filter(entry -> entry.getValue().isSameTeam(team))
                .map(Map.Entry::getKey)
                .toList();
    }

    public Piece getPiece(final Position position) {
        if (hasPiece(position)) {
            return pieces.get(position);
        }
        throw new IllegalStateException(EMPTY_POSITION);
    }

    public List<Delta> getPalaceDeltas(final Position position) {
        return palace.getDiagonalDeltas(position);
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
