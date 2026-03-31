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
import java.util.Map.Entry;

public class Board {

    private static final int MIN_ROW_RANGE = 1;
    private static final int MAX_ROW_RANGE = 10;
    private static final int MIN_COLUMN_RANGE = 1;
    private static final int MAX_COLUMN_RANGE = 9;

    private static final String EMPTY_POSITION = "기물이 존재하지 않는 칸입니다.";
    private static final String GENERAL_NOWHERE = "궁이 존재하지 않습니다.";

    private final Map<Position, Piece> pieces;

    Board(final Map<Position, Piece> pieces) {
        this.pieces = pieces;
    }

    public static Board init(final ElephantSetup choSetup, final ElephantSetup hanSetup) {
        Map<Position, Piece> pieces = new HashMap<>();

        for (PieceType type : PieceType.values()) {
            initializeChoPieces(type, pieces);
            initializeHanPieces(type, pieces);
        }

        setupChoElephant(choSetup, pieces);
        setupHanElephant(hanSetup, pieces);

        return new Board(pieces);
    }

    // 테스트만이 아니라 이후 DB에서 상태를 받아올 때 필요할 것으로 판단.
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


    // FIXME : 진영별 공통 처리
    private static void initializeChoPieces(final PieceType type, final Map<Position, Piece> pieces) {
        for (Position pos : type.getInitPositions()) {
            Position mirrored = mirror(pos);
            pieces.put(mirrored, Piece.choPieceOf(type));
        }
    }

    private static void initializeHanPieces(final PieceType type, final Map<Position, Piece> pieces) {
        for (Position pos : type.getInitPositions()) {
            pieces.put(pos, Piece.hanPieceOf(type));
        }
    }

    private static void setupChoElephant(final ElephantSetup choSetup, final Map<Position, Piece> pieces) {
        for (final Entry<Position, PieceType> entry : choSetup.getPiecePositions().entrySet()) {
            pieces.put(mirror(entry.getKey()), Piece.choPieceOf(entry.getValue()));
        }
    }

    private static void setupHanElephant(final ElephantSetup hanSetup, final Map<Position, Piece> pieces) {
        for (final Entry<Position, PieceType> entry : hanSetup.getPiecePositions().entrySet()) {
            pieces.put(entry.getKey(), Piece.hanPieceOf(entry.getValue()));
        }
    }

    private static Position mirror(Position pos) {
        int mirroredRow = MAX_ROW_RANGE - pos.row() + 1;
        int mirroredColumn = MAX_COLUMN_RANGE - pos.column() + 1;
        return Position.of(mirroredRow, mirroredColumn);
    }
}
