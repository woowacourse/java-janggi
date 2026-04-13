package dto;

import domain.Board;
import domain.Piece;
import domain.Team;
import domain.Type;
import domain.vo.Position;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record PieceDto(int row, int col, String team, String type) {

    public static List<PieceDto> fromBoard(Map<Position, Piece> boardStatus) {
        List<PieceDto> pieceDtos = new ArrayList<>();
        for (Map.Entry<Position, Piece> entry : boardStatus.entrySet()) {
            Position position = entry.getKey();
            Piece piece = entry.getValue();
            pieceDtos.add(new PieceDto(position.getRow(), position.getCol(), piece.getTeamName(), piece.getTypeName()));
        }
        return pieceDtos;
    }

    public static Board toBoard(List<PieceDto> pieceDtos) {
        Map<Position, Piece> boardMap = new HashMap<>();
        for (PieceDto dto : pieceDtos) {
            Position position = Position.of(dto.row(), dto.col());
            Piece piece = Piece.of(Team.fromName(dto.team()), Type.fromName(dto.type()));
            boardMap.put(position, piece);
        }
        return Board.of(boardMap);
    }
}