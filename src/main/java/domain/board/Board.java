package domain.board;

import domain.piece.None;
import domain.piece.Piece;
import domain.player.Team;
import domain.position.Path;
import domain.position.Position;
import dto.BoardDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Board {

    private final Map<Position, Piece> board;

    public Board(Map<Position, Piece> board) {
        this.board = board;
    }

    public PathPieces findPieceInPath(Path path) {
        List<Position> wayPoints = path.getWaypoints();
        List<Piece> pieces = new ArrayList<>();

        for (Position point : wayPoints) {
            Piece pointPiece = findPiece(point);
            if (!(pointPiece instanceof None)) {
                pieces.add(pointPiece);
            }
        }

        return new PathPieces(findPiece(path.getSrc()), pieces, findPiece(path.getDest()));
    }

    public void move(Position src, Position dest) {
        Piece movePiece = findPiece(src);

        board.put(src, new None(Team.NULL));
        board.put(dest, movePiece);
    }

    public BoardDTO createDTO() {
        List<List<String>> stringBoard = new ArrayList<>();
        for (int y = 0; y < 10; y++) {
            List<String> lineOfStringBoard = new ArrayList<>();
            for (int x = 0; x < 9; x++) {
                lineOfStringBoard.add(board.get(new Position(x, y)).getPieceString());
            }
            stringBoard.add(lineOfStringBoard);
        }

        return new BoardDTO(stringBoard);
    }

    private Piece findPiece(Position position) {
        return board.get(position);
    }
}
