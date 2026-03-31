package janggi.domain.board;

import janggi.domain.piece.EmptyPiece;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Team;
import janggi.domain.vo.Position;

import java.util.HashMap;
import java.util.Map;

public class Board implements BoardView {
    private final Map<Position, Piece> board;

    public Board() {
        this(new HashMap<>());
    }

    public Board(Map<Position, Piece> board) {
        this.board = board;
    }

    @Override
    public Piece findByPosition(Position position) {
        return board.getOrDefault(position, new EmptyPiece(Team.NONE));
    }

    @Override
    public boolean isEmptyPosition(Position position) {
        return findByPosition(position).isEmpty();
    }

    public void move(Position from, Position to, Team currentTeam) {
        Piece fromPiece = findByPosition(from);
        Piece toPiece = findByPosition(to);

        validateCommonMove(currentTeam, fromPiece, toPiece);

        if (!fromPiece.canMove(from, to, this)) {
            throw new IllegalArgumentException("해당 기물의 이동 규칙에 맞지 않습니다.");
        }

        place(from, new EmptyPiece(Team.NONE));
        place(to, fromPiece);
    }

    private void place(Position position, Piece piece) {
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
}
