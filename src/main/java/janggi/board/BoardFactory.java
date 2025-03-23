package janggi.board;

import janggi.piece.Byeong;
import janggi.piece.Cannon;
import janggi.piece.Chariot;
import janggi.piece.Guard;
import janggi.piece.Jol;
import janggi.piece.King;
import janggi.piece.Piece;
import janggi.Team.Team;
import janggi.position.Position;
import java.util.HashMap;
import java.util.Map;

public class BoardFactory {

    public Board makeBoard(ElephantSetting choElephantSetting, ElephantSetting hanElephantSetting) {
        Map<Position, Piece> pieces = new HashMap<>();
        makeHanPieces(pieces, hanElephantSetting);
        makeChoPieces(pieces, choElephantSetting);
        return new Board(pieces);
    }

    private static void makeChoPieces(Map<Position, Piece> pieces, ElephantSetting elephantSetting) {
        final Team targetTeam = Team.CHO;
        pieces.putAll(getElephantSetting(targetTeam, elephantSetting));

        pieces.put(new Position(10, 1), new Chariot(targetTeam));
        pieces.put(new Position(10, 4), new Guard(targetTeam));
        pieces.put(new Position(10, 6), new Guard(targetTeam));
        pieces.put(new Position(10, 9), new Chariot(targetTeam));

        pieces.put(new Position(9, 5), new King(targetTeam));
        pieces.put(new Position(8, 2), new Cannon(targetTeam));
        pieces.put(new Position(8, 8), new Cannon(targetTeam));

        pieces.put(new Position(7, 1), new Jol());
        pieces.put(new Position(7, 3), new Jol());
        pieces.put(new Position(7, 5), new Jol());
        pieces.put(new Position(7, 7), new Jol());
        pieces.put(new Position(7, 9), new Jol());
    }

    private static void makeHanPieces(Map<Position, Piece> pieces, ElephantSetting elephantSetting) {
        final Team targetTeam = Team.HAN;
        pieces.putAll(getElephantSetting(targetTeam, elephantSetting));

        pieces.put(new Position(1, 1), new Chariot(targetTeam));
        pieces.put(new Position(1, 4), new Guard(targetTeam));
        pieces.put(new Position(1, 6), new Guard(targetTeam));
        pieces.put(new Position(1, 9), new Chariot(targetTeam));

        pieces.put(new Position(2, 5), new King(targetTeam));
        pieces.put(new Position(3, 2), new Cannon(targetTeam));
        pieces.put(new Position(3, 8), new Cannon(targetTeam));

        pieces.put(new Position(4, 1), new Byeong());
        pieces.put(new Position(4, 3), new Byeong());
        pieces.put(new Position(4, 5), new Byeong());
        pieces.put(new Position(4, 7), new Byeong());
        pieces.put(new Position(4, 9), new Byeong());
    }

    private static Map<Position, Piece> getElephantSetting(Team team, ElephantSetting elephantSetting) {
        if (team == Team.HAN) {
            return elephantSetting.getElephantSetting(team, 1);
        }
        return elephantSetting.getElephantSetting(team, 10);
    }
}
