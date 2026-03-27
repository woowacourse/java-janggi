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

    public void canMove(Position src, Position dest) {
        Piece piece = findPiece(src);
        Path path = piece.calculatePath(src, dest);
        PathPieces pathPieces = findPieceInPath(path);
        if (!piece.validatePath(pathPieces)) {
            throw new IllegalArgumentException("기물을 이동할 수 없습니다.");
        }
    }

    public Piece move(Position src, Position dest) {
        canMove(src, dest);
        Piece movePiece = findPiece(src);
        Piece destPiece = findPiece(dest);

        board.put(src, new None());
        board.put(dest, movePiece);
        return destPiece;
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

    public boolean isPieceSameTeam(Position src, Team team) {
        return findPiece(src).isSameTeam(team);
    }

    private PathPieces findPieceInPath(Path path) {
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

    private Piece findPiece(Position position) {
        return board.get(position);
    }
}
