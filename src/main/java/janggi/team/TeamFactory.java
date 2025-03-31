package janggi.team;

import janggi.board.BoardSetup;
import janggi.board.Position;
import janggi.palace.PalaceFactory;
import janggi.piece.Cannon;
import janggi.piece.Chariot;
import janggi.piece.Guard;
import janggi.piece.King;
import janggi.piece.Piece;
import janggi.piece.Soldier;
import java.util.ArrayList;
import java.util.List;

public enum TeamFactory {
    TEAM_CHO {
        @Override
        public List<Piece> createTeamPieces(BoardSetup boardSetup) {
            List<Piece> choPieces = new ArrayList<>(List.of(
                    new King(TeamName.CHO, new Position(4, 1)),
                    new Cannon(TeamName.CHO, new Position(1, 2)),
                    new Cannon(TeamName.CHO, new Position(7, 2)),
                    new Chariot(TeamName.CHO, new Position(0, 0)),
                    new Chariot(TeamName.CHO, new Position(8, 0)),
                    new Soldier(TeamName.CHO, new Position(0, 3)),
                    new Soldier(TeamName.CHO, new Position(2, 3)),
                    new Soldier(TeamName.CHO, new Position(4, 3)),
                    new Soldier(TeamName.CHO, new Position(6, 3)),
                    new Soldier(TeamName.CHO, new Position(8, 3)),
                    new Guard(TeamName.CHO, new Position(3, 0)),
                    new Guard(TeamName.CHO, new Position(5, 0))
            ));
            choPieces.addAll(boardSetup.getPieces());
            return choPieces;
        }
    },
    TEAM_HAN {
        @Override
        public List<Piece> createTeamPieces(BoardSetup boardSetup) {
            List<Piece> hanPieces = new ArrayList<>(List.of(
                    new King(TeamName.HAN, new Position(4, 8)),
                    new Cannon(TeamName.HAN, new Position(1, 7)),
                    new Cannon(TeamName.HAN, new Position(7, 7)),
                    new Chariot(TeamName.HAN, new Position(0, 9)),
                    new Chariot(TeamName.HAN, new Position(8, 9)),
                    new Soldier(TeamName.HAN, new Position(0, 6)),
                    new Soldier(TeamName.HAN, new Position(2, 6)),
                    new Soldier(TeamName.HAN, new Position(4, 6)),
                    new Soldier(TeamName.HAN, new Position(6, 6)),
                    new Soldier(TeamName.HAN, new Position(8, 6)),
                    new Guard(TeamName.HAN, new Position(3, 9)),
                    new Guard(TeamName.HAN, new Position(5, 9))
            ));
            hanPieces.addAll(boardSetup.getPieces());
            return hanPieces;
        }
    };

    public abstract List<Piece> createTeamPieces(BoardSetup boardSetup);

    public static Team createTeam(BoardSetup boardSetup) {
        if (boardSetup.getTeamName().equals(TeamName.CHO)) {
            return new Team(
                    TEAM_CHO.createTeamPieces(boardSetup),
                    PalaceFactory.createPalace(TeamName.CHO),
                    TeamName.CHO
            );
        }
        return new Team(
                TEAM_HAN.createTeamPieces(boardSetup),
                PalaceFactory.createPalace(TeamName.HAN),
                TeamName.HAN
        );
    }
}
