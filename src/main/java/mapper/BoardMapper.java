package mapper;

import domain.coordinate.Position;
import domain.piece.Piece;
import dto.BoardDto;
import dto.PieceDto;
import dto.PositionDto;

import java.util.HashMap;
import java.util.Map;

public class BoardMapper {

    public static BoardDto toDto(Map<Position, Piece> board) {
        Map<PositionDto, PieceDto> boardReader = new HashMap<>();

        for (Map.Entry<Position, Piece> entry : board.entrySet()) {
            PositionDto positionDto = toPositionDto(entry.getKey());
            PieceDto pieceDto = toPieceDto(entry.getValue());

            boardReader.put(positionDto, pieceDto);
        }

        return new BoardDto(boardReader);
    }

    private static PositionDto toPositionDto(Position position) {
        return new PositionDto(position.col(), position.row());
    }

    private static PieceDto toPieceDto(Piece piece) {
        return new PieceDto(piece);
    }
}
