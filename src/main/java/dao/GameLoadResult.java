package dao;

import domain.piece.BasicPiece;
import domain.player.Team;
import domain.position.Position;

import java.util.Map;

public record GameLoadResult(
        long gameId,
        String choName,
        String hanName,
        Team currentTeam,
        Map<Position, BasicPiece> boardMap
) {
}

