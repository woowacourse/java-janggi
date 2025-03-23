package janggi.board;

import static janggi.board.InitPositions.A0;
import static janggi.board.InitPositions.A3;
import static janggi.board.InitPositions.A6;
import static janggi.board.InitPositions.A9;
import static janggi.board.InitPositions.B0;
import static janggi.board.InitPositions.B2;
import static janggi.board.InitPositions.B3;
import static janggi.board.InitPositions.B7;
import static janggi.board.InitPositions.B9;
import static janggi.board.InitPositions.C0;
import static janggi.board.InitPositions.C3;
import static janggi.board.InitPositions.C6;
import static janggi.board.InitPositions.C9;
import static janggi.board.InitPositions.D0;
import static janggi.board.InitPositions.D9;
import static janggi.board.InitPositions.E1;
import static janggi.board.InitPositions.E3;
import static janggi.board.InitPositions.E6;
import static janggi.board.InitPositions.F0;
import static janggi.board.InitPositions.F9;
import static janggi.board.InitPositions.G0;
import static janggi.board.InitPositions.G3;
import static janggi.board.InitPositions.G6;
import static janggi.board.InitPositions.G9;
import static janggi.board.InitPositions.H0;
import static janggi.board.InitPositions.H2;
import static janggi.board.InitPositions.H7;
import static janggi.board.InitPositions.H9;
import static janggi.board.InitPositions.I0;
import static janggi.board.InitPositions.I3;
import static janggi.board.InitPositions.I6;
import static janggi.board.InitPositions.I9;

import janggi.piece.pawn.ChoPawn;
import janggi.piece.pawn.HanPawn;
import java.util.ArrayList;
import java.util.List;

import janggi.Team;
import janggi.piece.Chariot;
import janggi.piece.Elephant;
import janggi.piece.Horse;
import janggi.piece.Palace;
import janggi.piece.Pao;
import janggi.piece.pawn.Pawn;
import janggi.piece.Piece;
import janggi.piece.Soldier;

class Initializer {

    public Board generate() {
        List<Piece> pieces = new ArrayList<>();
        pieces.add(new Palace(E1.position, Team.HAN));
        pieces.add(new Palace(E1.position, Team.CHO));

        pieces.add(new Soldier(D0.position, Team.HAN));
        pieces.add(new Soldier(F0.position, Team.HAN));
        pieces.add(new Soldier(D9.position, Team.CHO));
        pieces.add(new Soldier(F9.position, Team.CHO));

        pieces.add(new Elephant(C0.position, Team.HAN));
        pieces.add(new Elephant(G0.position, Team.HAN));
        pieces.add(new Elephant(C9.position, Team.CHO));
        pieces.add(new Elephant(G9.position, Team.CHO));

        pieces.add(new Horse(B0.position, Team.HAN));
        pieces.add(new Horse(H0.position, Team.HAN));
        pieces.add(new Horse(B9.position, Team.CHO));
        pieces.add(new Horse(H9.position, Team.CHO));

        pieces.add(new Chariot(A0.position, Team.HAN));
        pieces.add(new Chariot(I0.position, Team.HAN));
        pieces.add(new Chariot(A9.position, Team.CHO));
        pieces.add(new Chariot(I9.position, Team.CHO));

        pieces.add(new Pao(B2.position, Team.HAN));
        pieces.add(new Pao(H2.position, Team.HAN));
        pieces.add(new Pao(B7.position, Team.CHO));
        pieces.add(new Pao(H7.position, Team.CHO));

        pieces.add(new HanPawn(A3.position));
        pieces.add(new HanPawn(C3.position));
        pieces.add(new HanPawn(E3.position));
        pieces.add(new HanPawn(G3.position));
        pieces.add(new HanPawn(I3.position));
        pieces.add(new ChoPawn(A6.position));
        pieces.add(new ChoPawn(C6.position));
        pieces.add(new ChoPawn(E6.position));
        pieces.add(new ChoPawn(G6.position));
        pieces.add(new ChoPawn(I6.position));

        return new Board(pieces);
    }
}
