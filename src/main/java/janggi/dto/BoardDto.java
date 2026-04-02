package janggi.dto;

import janggi.domain.piece.Piece;
import janggi.domain.position.Position;
import janggi.util.DynastyColorMapper;
import janggi.util.PieceMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public record BoardDto(
        List<PieceDto> pieces
) {

    public static BoardDto from(Map<Position, Piece> board) {
        List<PieceDto> pieces = new ArrayList<>();
        for (Position position : board.keySet()) {
            Piece piece = board.get(position);
            pieces.add(PieceDto.from(PieceMapper.from(piece), DynastyColorMapper.from(piece.dynasty()),
                    PositionDto.from(position))
            );
        }
        return new BoardDto(pieces);
    }

}
