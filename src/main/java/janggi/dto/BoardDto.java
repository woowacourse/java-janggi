package janggi.dto;

import janggi.domain.board.Board;
import janggi.domain.piece.PieceType;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class BoardDto {
    private static final Map<PieceType, String> mapper;

    static {
        mapper = new EnumMap<>(PieceType.class);
        mapper.put(PieceType.CHA, "車");
        mapper.put(PieceType.PO, "包");
        mapper.put(PieceType.MA, "馬");
        mapper.put(PieceType.SANG, "象");
        mapper.put(PieceType.SA, "士");
        mapper.put(PieceType.HAN_GUNG, "將");
        mapper.put(PieceType.CHO_GUNG, "楚");
        mapper.put(PieceType.HAN_JOL, "兵");
        mapper.put(PieceType.CHO_JOL, "卒");
    }

    private final List<PieceDto> pieces;

    private BoardDto(List<PieceDto> pieces) {
        this.pieces = pieces;
    }

    public static BoardDto from(Board board) {
        List<PieceDto> pieces = board.getBoard().entrySet().stream()
                .map(entry -> {
                    PieceType type = entry.getValue().getPieceType();
                    String name = mapper.getOrDefault(type, "？");

                    return new PieceDto(
                            entry.getKey().getRow(),
                            entry.getKey().getColumn(),
                            name,
                            entry.getValue().getTeam().name());
                })
                .toList();

        return new BoardDto(pieces);
    }

    public List<PieceDto> getPieces() {
        return pieces;
    }
}
