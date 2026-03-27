package domain.board;

import static common.Constants.MAX_COL;
import static common.Constants.MAX_ROW;
import static common.Constants.MIN_COL;
import static common.Constants.MIN_ROW;

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

        for (int row = MIN_ROW; row <= MAX_ROW; row++) {
            List<String> lineOfStringBoard = makeLineOfStringBoard(row);
            stringBoard.add(lineOfStringBoard);
        }

        return new BoardDTO(stringBoard);
    }

    public boolean isPieceSameTeam(Position src, Team team) {
        return findPiece(src).isSameTeam(team);
    }

    private List<String> makeLineOfStringBoard(int row) {
        List<String> lineOfStringBoard = new ArrayList<>();

        for (int col = MIN_COL; col <= MAX_COL; col++) {
            Piece piece = board.get(new Position(row, col));
            String pieceString = piece.getPieceString();
            String teamString = piece.getTeamString();
            lineOfStringBoard.add(teamString + pieceString);
        }

        return lineOfStringBoard;
    }

    private PathPieces findPieceInPath(Path path) {
        List<Position> wayPoints = path.waypoints();
        List<Piece> pieces = new ArrayList<>();

        for (Position point : wayPoints) {
            Piece pointPiece = findPiece(point);
            addPieceInPath(pointPiece, pieces);
        }

        return new PathPieces(findPiece(path.src()), pieces, findPiece(path.dest()));
    }

    private void addPieceInPath(Piece piece, List<Piece> pieces) {
        if (piece.isNotNone()) {
            pieces.add(piece);
        }
    }

    private Piece findPiece(Position position) {
        return board.get(position);
    }
}
