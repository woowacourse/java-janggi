package domain.boardgenerator;

import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Position;
import domain.piece.Team;
import java.util.ArrayList;
import java.util.List;

public class JanggiBoardGenerator implements BoardGenerator {
    @Override
    public List<Piece> generateBoard() {
        List<Piece> board = new ArrayList<>();
        board.add(new Piece(Team.HAN, PieceType.CHA, new Position(1, 1)));
        board.add(new Piece(Team.HAN, PieceType.MA, new Position(1, 2)));
        board.add(new Piece(Team.HAN, PieceType.SANG, new Position(1, 3)));
        board.add(new Piece(Team.HAN, PieceType.SA, new Position(1, 4)));
        board.add(new Piece(Team.HAN, PieceType.SA, new Position(1, 6)));
        board.add(new Piece(Team.HAN, PieceType.SANG, new Position(1, 7)));
        board.add(new Piece(Team.HAN, PieceType.MA, new Position(1, 8)));
        board.add(new Piece(Team.HAN, PieceType.CHA, new Position(1, 9)));
        board.add(new Piece(Team.HAN, PieceType.GUNG, new Position(2, 5)));
        board.add(new Piece(Team.HAN, PieceType.PO, new Position(3, 2)));
        board.add(new Piece(Team.HAN, PieceType.PO, new Position(3, 8)));
        board.add(new Piece(Team.HAN, PieceType.PAWN, new Position(4, 1)));
        board.add(new Piece(Team.HAN, PieceType.PAWN, new Position(4, 3)));
        board.add(new Piece(Team.HAN, PieceType.PAWN, new Position(4, 5)));
        board.add(new Piece(Team.HAN, PieceType.PAWN, new Position(4, 7)));
        board.add(new Piece(Team.HAN, PieceType.PAWN, new Position(4, 9)));
        board.add(new Piece(Team.CHO, PieceType.PAWN, new Position(7, 1)));
        board.add(new Piece(Team.CHO, PieceType.PAWN, new Position(7, 3)));
        board.add(new Piece(Team.CHO, PieceType.PAWN, new Position(7, 5)));
        board.add(new Piece(Team.CHO, PieceType.PAWN, new Position(7, 7)));
        board.add(new Piece(Team.CHO, PieceType.PAWN, new Position(7, 9)));
        board.add(new Piece(Team.CHO, PieceType.PO, new Position(8, 2)));
        board.add(new Piece(Team.CHO, PieceType.PO, new Position(8, 8)));
        board.add(new Piece(Team.CHO, PieceType.GUNG, new Position(9, 5)));
        board.add(new Piece(Team.CHO, PieceType.CHA, new Position(10, 1)));
        board.add(new Piece(Team.CHO, PieceType.SANG, new Position(10, 2)));
        board.add(new Piece(Team.CHO, PieceType.MA, new Position(10, 3)));
        board.add(new Piece(Team.CHO, PieceType.SA, new Position(10, 4)));
        board.add(new Piece(Team.CHO, PieceType.SA, new Position(10, 6)));
        board.add(new Piece(Team.CHO, PieceType.MA, new Position(10, 7)));
        board.add(new Piece(Team.CHO, PieceType.SANG, new Position(10, 8)));
        board.add(new Piece(Team.CHO, PieceType.CHA, new Position(10, 9)));
        return board;
    }
}
