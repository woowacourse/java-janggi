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
        if (pieceName.equalsIgnoreCase("K")) {
            return new King(
                    TeamName.from(teamName),
                    new Position(positionX, positionY),
                    PieceStatus.valueOf(pieceStatus)
            );
        }
        if (pieceName.equalsIgnoreCase("G")) {
            return new Guard(
                    TeamName.from(teamName),
                    new Position(positionX, positionY),
                    PieceStatus.valueOf(pieceStatus)
            );
        }
        if (pieceName.equalsIgnoreCase("H")) {
            return new Horse(
                    TeamName.from(teamName),
                    new Position(positionX, positionY),
                    PieceStatus.valueOf(pieceStatus)
            );
        }
        if (pieceName.equalsIgnoreCase("E")) {
            return new Elephant(
                    TeamName.from(teamName),
                    new Position(positionX, positionY),
                    PieceStatus.valueOf(pieceStatus)
            );
        }
        if (pieceName.equalsIgnoreCase("C")) {
            return new Chariot(
                    TeamName.from(teamName),
                    new Position(positionX, positionY),
                    PieceStatus.valueOf(pieceStatus)
            );
        }
        if (pieceName.equalsIgnoreCase("P")) {
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
