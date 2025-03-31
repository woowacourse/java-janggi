package janggi.piece;

import janggi.board.Position;
import janggi.team.TeamName;

public class PieceFactory {
    public static Piece createPiece(
            String pieceName,
            String teamName,
            String pieceStatus,
            int positionX,
            int positionY
    ) {
        if (pieceName.equalsIgnoreCase(PieceName.KING.getName())) {
            return new King(
                    TeamName.from(teamName),
                    new Position(positionX, positionY),
                    PieceStatus.valueOf(pieceStatus)
            );
        }
        if (pieceName.equalsIgnoreCase(PieceName.GUARD.getName())) {
            return new Guard(
                    TeamName.from(teamName),
                    new Position(positionX, positionY),
                    PieceStatus.valueOf(pieceStatus)
            );
        }
        if (pieceName.equalsIgnoreCase(PieceName.HORSE.getName())) {
            return new Horse(
                    TeamName.from(teamName),
                    new Position(positionX, positionY),
                    PieceStatus.valueOf(pieceStatus)
            );
        }
        if (pieceName.equalsIgnoreCase(PieceName.ELEPHANT.getName())) {
            return new Elephant(
                    TeamName.from(teamName),
                    new Position(positionX, positionY),
                    PieceStatus.valueOf(pieceStatus)
            );
        }
        if (pieceName.equalsIgnoreCase(PieceName.CHARIOT.getName())) {
            return new Chariot(
                    TeamName.from(teamName),
                    new Position(positionX, positionY),
                    PieceStatus.valueOf(pieceStatus)
            );
        }
        if (pieceName.equalsIgnoreCase(PieceName.CANNON.getName())) {
            return new Cannon(
                    TeamName.from(teamName),
                    new Position(positionX, positionY),
                    PieceStatus.valueOf(pieceStatus)
            );
        }
        return new Soldier(
                TeamName.from(teamName),
                new Position(positionX, positionY),
                PieceStatus.valueOf(pieceStatus)
        );
    }
}
