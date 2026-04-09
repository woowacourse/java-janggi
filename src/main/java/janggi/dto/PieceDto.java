package janggi.dto;

import janggi.domain.PieceFactory;
import janggi.domain.Position;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.team.TeamType;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record PieceDto(Long id, Long turnId, PieceType pieceType, TeamType teamType, int x, int y) {

    public static PieceDto of(long id, long turnId, String pieceType, String teamType, int x, int y) {
        return new PieceDto(id, turnId, PieceType.from(pieceType), TeamType.from(teamType), x, y);
    }

    public static PieceDto from(long turnId, Position position, Piece piece) {
        return new PieceDto(null, turnId, piece.getPieceType(), piece.getTeamType(), position.getX(), position.getY());
    }

    public static Map<Position, Piece> getPiecesByTeamType(List<PieceDto> pieceDtos, TeamType teamType) {
        return pieceDtos.stream()
                .filter(pieceDto -> pieceDto.teamType == teamType)
                .collect(Collectors.toMap(
                        pieceDto -> Position.of(pieceDto.x, pieceDto.y),
                        pieceDto -> PieceFactory.create(pieceDto.pieceType, pieceDto.teamType)
                ));
    }
}
