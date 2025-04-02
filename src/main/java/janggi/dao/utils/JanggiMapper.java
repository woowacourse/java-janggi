package janggi.dao.utils;

import janggi.dto.PieceDto;
import janggi.dto.PieceTypeDto;
import janggi.dto.TeamTypeDto;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.position.Position;
import janggi.domain.team.TeamType;

public class JanggiMapper {

    public Position toPosition(PieceDto pieceDto) {
        return new Position(pieceDto.y(), pieceDto.x());
    }

    public Piece toPiece(PieceTypeDto pieceTypeDto, TeamTypeDto teamTypeDto) {
        PieceType pieceType = toPieceType(pieceTypeDto);
        return pieceType.createPiece(toTeamType(teamTypeDto));
    }

    private PieceType toPieceType(PieceTypeDto pieceTypeDto) {
        return PieceType.of(pieceTypeDto.name());
    }

    private TeamType toTeamType(TeamTypeDto teamTypeDto) {
        return TeamType.of(teamTypeDto.getName());
    }
}
