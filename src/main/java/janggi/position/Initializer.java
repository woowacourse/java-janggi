package janggi.position;

import static janggi.position.InitPositions.A0;
import static janggi.position.InitPositions.A3;
import static janggi.position.InitPositions.A6;
import static janggi.position.InitPositions.A9;
import static janggi.position.InitPositions.B0;
import static janggi.position.InitPositions.B2;
import static janggi.position.InitPositions.B7;
import static janggi.position.InitPositions.B9;
import static janggi.position.InitPositions.C0;
import static janggi.position.InitPositions.C3;
import static janggi.position.InitPositions.C6;
import static janggi.position.InitPositions.C9;
import static janggi.position.InitPositions.D0;
import static janggi.position.InitPositions.D9;
import static janggi.position.InitPositions.E1;
import static janggi.position.InitPositions.E3;
import static janggi.position.InitPositions.E6;
import static janggi.position.InitPositions.E8;
import static janggi.position.InitPositions.F0;
import static janggi.position.InitPositions.F9;
import static janggi.position.InitPositions.G0;
import static janggi.position.InitPositions.G3;
import static janggi.position.InitPositions.G6;
import static janggi.position.InitPositions.G9;
import static janggi.position.InitPositions.H0;
import static janggi.position.InitPositions.H2;
import static janggi.position.InitPositions.H7;
import static janggi.position.InitPositions.H9;
import static janggi.position.InitPositions.I0;
import static janggi.position.InitPositions.I3;
import static janggi.position.InitPositions.I6;
import static janggi.position.InitPositions.I9;

import janggi.piece.Piece;
import janggi.piece.Team;
import janggi.piece.jumpingPiece.Cannon;
import janggi.piece.normalPiece.ChoPawn;
import janggi.piece.normalPiece.Elephant;
import janggi.piece.normalPiece.HanPawn;
import janggi.piece.normalPiece.Horse;
import janggi.piece.normalPiece.Palace;
import janggi.piece.normalPiece.Soldier;
import janggi.piece.straightPiece.Chariot;
import java.util.HashSet;
import java.util.Set;

class Initializer {
    public Set<Piece> generate() {
        Set<Piece> pieces = new HashSet<>();
        pieces.add(new Palace(Team.HAN, E8));
        pieces.add(new Palace(Team.CHO, E1));

        pieces.add(new Soldier(Team.HAN, D9));
        pieces.add(new Soldier(Team.HAN, F9));
        pieces.add(new Soldier(Team.CHO, D0));
        pieces.add(new Soldier(Team.CHO, F0));

        pieces.add(new Elephant(Team.HAN, C9));
        pieces.add(new Elephant(Team.HAN, G9));
        pieces.add(new Elephant(Team.CHO, C0));
        pieces.add(new Elephant(Team.CHO, G0));

        pieces.add(new Horse(Team.HAN, B9));
        pieces.add(new Horse(Team.HAN, H9));
        pieces.add(new Horse(Team.CHO, B0));
        pieces.add(new Horse(Team.CHO, H0));

        pieces.add(new Chariot(Team.HAN, A9));
        pieces.add(new Chariot(Team.HAN, I9));
        pieces.add(new Chariot(Team.CHO, A0));
        pieces.add(new Chariot(Team.CHO, I0));

        pieces.add(new Cannon(Team.HAN, B7));
        pieces.add(new Cannon(Team.HAN, H7));
        pieces.add(new Cannon(Team.CHO, B2));
        pieces.add(new Cannon(Team.CHO, H2));

        pieces.add(new HanPawn(A6));
        pieces.add(new HanPawn(C6));
        pieces.add(new HanPawn(E6));
        pieces.add(new HanPawn(G6));
        pieces.add(new HanPawn(I6));
        pieces.add(new ChoPawn(A3));
        pieces.add(new ChoPawn(C3));
        pieces.add(new ChoPawn(E3));
        pieces.add(new ChoPawn(G3));
        pieces.add(new ChoPawn(I3));

        return pieces;
    }
}
