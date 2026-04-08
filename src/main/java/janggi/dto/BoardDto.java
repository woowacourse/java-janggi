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
            pieces.add(PieceDto.from(
                    PieceMapper.from(piece),
                    DynastyColorMapper.from(piece.dynasty()),
                    PositionDto.from(position)
            ));
        }
        return new BoardDto(pieces);
    }

    public boolean isExist(int row, int column) {
        Position from = Position.from(row, column);
        PositionDto positionDto = PositionDto.from(from);

        return pieces.stream()
                .anyMatch(piece -> piece.position().equals(positionDto));
    }

    public String get(int row, int column) {
        Position from = Position.from(row, column);
        PositionDto positionDto = PositionDto.from(from);

        return pieces.stream()
                .filter(piece -> piece.position().equals(positionDto))
                .map(PieceDto::nameWithColor)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 위치에 기물이 없습니다."));
    }

}
