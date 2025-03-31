package janggi.position;

import static janggi.position.Positions.A0;
import static janggi.position.Positions.A3;
import static janggi.position.Positions.A6;
import static janggi.position.Positions.A9;
import static janggi.position.Positions.B0;
import static janggi.position.Positions.B2;
import static janggi.position.Positions.B7;
import static janggi.position.Positions.B9;
import static janggi.position.Positions.C0;
import static janggi.position.Positions.C3;
import static janggi.position.Positions.C6;
import static janggi.position.Positions.C9;
import static janggi.position.Positions.D0;
import static janggi.position.Positions.D9;
import static janggi.position.Positions.E1;
import static janggi.position.Positions.E3;
import static janggi.position.Positions.E6;
import static janggi.position.Positions.E8;
import static janggi.position.Positions.F0;
import static janggi.position.Positions.F9;
import static janggi.position.Positions.G0;
import static janggi.position.Positions.G3;
import static janggi.position.Positions.G6;
import static janggi.position.Positions.G9;
import static janggi.position.Positions.H0;
import static janggi.position.Positions.H2;
import static janggi.position.Positions.H7;
import static janggi.position.Positions.H9;
import static janggi.position.Positions.I0;
import static janggi.position.Positions.I3;
import static janggi.position.Positions.I6;
import static janggi.position.Positions.I9;

import janggi.piece.Piece;
import janggi.piece.Team;
import janggi.piece.jumpingPiece.Cannon;
import janggi.piece.pawnPiece.ChoPawn;
import janggi.piece.normalPiece.Elephant;
import janggi.piece.pawnPiece.HanPawn;
import janggi.piece.normalPiece.Horse;
import janggi.piece.palacePiece.King;
import janggi.piece.palacePiece.Soldier;
import janggi.piece.straightPiece.Chariot;
import java.util.HashSet;
import java.util.Set;
import repository.connection.ConnectMysql;
import repository.dao.PieceDao;
import repository.dao.TurnDao;
import repository.converter.PieceConverter;
import repository.converter.TurnConverter;

class Initializer {
    private final PieceDao pieceDao = new PieceDao(new ConnectMysql());
    private final TurnDao turnDao = new TurnDao(new ConnectMysql());

    public Set<Piece> generate() {
        Set<Piece> byPiece = pieceDao.findAll();
        if(!byPiece.isEmpty()){
            return byPiece;
        }

        Set<Piece> pieces = new HashSet<>();
        pieces.add(new King(Team.HAN, E8));
        pieces.add(new King(Team.CHO, E1));

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

        Set<PieceConverter> pieceConverters = new HashSet<>();
        for (Piece piece : pieces) {
            pieceConverters.add(PieceConverter.toEntity(piece));
        }
        pieceDao.addAll(pieceConverters);

        return pieces;
    }

    public Team setTurn() {
        String turn = turnDao.findTurn();
        if(turn==null){
            turnDao.addTurn(TurnConverter.toEntity(Team.CHO));
            return Team.CHO;
        }
        return Team.convert(turn);
    }
}
