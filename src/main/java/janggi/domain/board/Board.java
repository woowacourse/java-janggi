package janggi.domain.board;

import janggi.domain.piece.EmptyPiece;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.Team;
import janggi.domain.vo.position.Position;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Board implements BoardView {
    private final Map<Position, Piece> board;

    public Board() {
        this(new HashMap<>());
    }

    public Board(Map<Position, Piece> board) {
        this.board = board.entrySet().stream()
                .filter(entry -> !entry.getValue().isEmpty())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (existing, replacement) -> existing,
                        HashMap::new
                ));
    }

    @Override
    public Piece findByPosition(Position position) {
        return board.getOrDefault(position, EmptyPiece.getInstance());
    }

    @Override
    public Team findTeamByPosition(Position position) {
        return board.getOrDefault(position, EmptyPiece.getInstance()).findTeam();
    }

    @Override
    public boolean isEmptyPosition(Position position) {
        return findByPosition(position).isEmpty();
    }

    @Override
    public PieceType findTypeByPosition(Position position) {
        return findByPosition(position).pieceType();
    }

    @Override
    public Palace palace() {
        return Palace.creatAllPalace();
    }

    @Override
    public List<Piece> kingsOnBoard() {
        return board.values().stream()
                .filter(piece -> piece.pieceType() == PieceType.KING)
                .collect(Collectors.toList());
    }

    @Override
    public List<Piece> piecesOf(Team team) {
        return board.values().stream()
                .filter(piece -> piece.findTeam() == team)
                .collect(Collectors.toList());
    }

    public void move(Position from, Position to, Team currentTeam) {
        Piece fromPiece = findByPosition(from);
        Piece toPiece = findByPosition(to);

        validateCommonMove(currentTeam, fromPiece, toPiece);

        if (!fromPiece.canMove(from, to, this)) {
            throw new IllegalArgumentException("해당 기물의 이동 규칙에 맞지 않습니다.");
        }

        remove(from);
        place(to, fromPiece);
    }

    public void remove(Position position) {
        board.remove(position);
    }

    public void place(Position position, Piece piece) {
        if (piece.isEmpty()) {
            return;
        }

        board.put(position, piece);
    }

    private void validateCommonMove(Team currentTeam, Piece fromPiece, Piece toPiece) {
        if (fromPiece.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 선택하신 칸에 기물이 없습니다.");
        }

        if (!fromPiece.isSameTeam(currentTeam)) {
            throw new IllegalArgumentException("자신 진영의 기물을 선택해야합니다.");
        }

        if (toPiece.isSameTeam(currentTeam)) {
            throw new IllegalArgumentException("이미 도착지점에 플레이어님의 진영 기물이 있습니다.");
        }
    }

    public static Board createBoardWith(Object... args) {
        Board board = new Board();
        for (int i = 0; i < args.length; i += 2) {
            board.place((Position) args[i], (Piece) args[i + 1]);
        }
        return board;
    }

    @Override
    public Map<Position, Piece> getBoard() {
        return Map.copyOf(board);
    }

    @Override
    public boolean canInnerGo(Position from, Position to) {
        if (findTeamByPosition(from) == Team.CHO) {
            return Palace.createChoPalace().canInnerGo(from, to);
        }

        return Palace.createHanPalace().canInnerGo(from, to);
    }
}
