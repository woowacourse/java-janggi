package domain.board;

import static common.Constants.MAX_COLUMN;
import static common.Constants.MAX_ROW;
import static common.Constants.MIN_COLUMN;
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

    public Piece move(Position source, Position destination) {
        validateMovement(source, destination);
        Piece movePiece = findPiece(source);
        Piece destinationPiece = findPiece(destination);

        board.put(source, new None());
        board.put(destination, movePiece);

        return destinationPiece;
    }

    public BoardDTO createDTO() {
        List<List<String>> stringBoard = new ArrayList<>();

        for (int row = MIN_ROW; row <= MAX_ROW; row++) {
            List<String> lineOfStringBoard = makeLineOfStringBoard(row);
            stringBoard.add(lineOfStringBoard);
        }

        return new BoardDTO(stringBoard);
    }

    public boolean isPieceSameTeam(Position source, Team team) {
        return findPiece(source).isSameTeam(team);
    }

    private void validateMovement(Position source, Position destination) {
        Piece piece = findPiece(source);
        Path path = piece.calculatePath(source, destination);
        PathPieces pathPieces = createPathPieces(path);
        if (!piece.validatePath(pathPieces)) {
            throw new IllegalArgumentException("기물을 이동할 수 없습니다.");
        }
    }

    private List<String> makeLineOfStringBoard(int row) {
        List<String> lineOfStringBoard = new ArrayList<>();

        for (int column = MIN_COLUMN; column <= MAX_COLUMN; column++) {
            Piece piece = board.get(new Position(row, column));
            String pieceString = piece.getPieceString();
            String teamString = piece.getTeamString();
            lineOfStringBoard.add(teamString + pieceString);
        }

        return lineOfStringBoard;
    }

    private PathPieces createPathPieces(Path path) {
        List<Position> wayPoints = path.waypoints();
        List<Piece> pieces = new ArrayList<>();

        for (Position point : wayPoints) {
            Piece pointPiece = findPiece(point);
            addPieceInPath(pointPiece, pieces);
        }

        return new PathPieces(findPiece(path.source()), pieces, findPiece(path.destination()));
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
