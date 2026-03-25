package domain.board;

import domain.piece.None;
import domain.piece.Piece;
import domain.player.Team;
import domain.position.Path;
import domain.position.Position;
import dto.BoardDTO;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Board {

    Map<Position, Piece> board = new LinkedHashMap<>();

    public List<Piece> findPieceInPath(Path path) {
        List<Position> wayPoints = path.getWaypoints();
        List<Piece> pieces = new ArrayList<>();

        for(Position point : wayPoints) {
            Piece pointPiece = findPiece(point);
            if(!(pointPiece instanceof None)) {
                pieces.add(pointPiece);
            }
        }

        return pieces;
    }

    public void move(Position src, Position dest) {
        Piece movePiece = findPiece(src);

        board.put(src, new None(Team.NULL));
        board.put(dest, movePiece);
    }

    private Piece findPiece(Position position) {
        return board.get(position);
    }
}
