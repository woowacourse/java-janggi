package janggi.database.dao;

import janggi.database.entity.PieceEntity;
import janggi.domain.Team;
import janggi.domain.piece.Cannon;
import janggi.domain.piece.Chariot;
import janggi.domain.piece.Elephant;
import janggi.domain.piece.General;
import janggi.domain.piece.Guard;
import janggi.domain.piece.Horse;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.Soldier;
import janggi.domain.piece.direction.Position;

public class PieceFactory {

    public static Piece createPiece(final PieceEntity pieceEntity) {
        return switch (PieceType.valueOf(pieceEntity.getType())) {
            case GENERAL -> new General(createPosition(pieceEntity.getX(), pieceEntity.getY()),
                    createTeam(pieceEntity.getTeam()));
            case SOLDIER -> new Soldier(createPosition(pieceEntity.getX(), pieceEntity.getY()),
                    createTeam(pieceEntity.getTeam()));
            case HORSE -> new Horse(createPosition(pieceEntity.getX(), pieceEntity.getY()),
                    createTeam(pieceEntity.getTeam()));
            case GUARD -> new Guard(createPosition(pieceEntity.getX(), pieceEntity.getY()),
                    createTeam(pieceEntity.getTeam()));
            case ELEPHANT -> new Elephant(createPosition(pieceEntity.getX(), pieceEntity.getY()),
                    createTeam(pieceEntity.getTeam()));
            case CHARIOT -> new Chariot(createPosition(pieceEntity.getX(), pieceEntity.getY()),
                    createTeam(pieceEntity.getTeam()));
            case CANNON -> new Cannon(createPosition(pieceEntity.getX(), pieceEntity.getY()),
                    createTeam(pieceEntity.getTeam()));
            default -> throw new IllegalArgumentException("알 수 없는 기물: " + pieceEntity.getType());
        };
    }

    private static Position createPosition(final int x, final int y) {
        return new Position(x, y);
    }

    private static Team createTeam(final String team) {
        return Team.valueOf(team);
    }
}
