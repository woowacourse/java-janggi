package janggi.domain;

import janggi.domain.piece.EmptyPosition;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Team;
import janggi.domain.vo.Position;
import java.util.List;

public class Board {
    private final List<List<Piece>> board;

    public Board() {
        board = BoardInitializer.createBoard();
    }

    public Board(String x) {//PR
        board = BoardInitializer.createEmptyBoard();
    }

    public Piece findByPosition(Position position) {
        int row = position.getRow();
        int col = position.getCol();
        return board.get(row).get(col);
    }

    public boolean isEmptyPosition(Position position) {
        return findByPosition(position).isEmpty();
    }

    public void move(Position from, Position to, Team currentTeam) {// TODO void 반환 로직
        Piece fromPiece = findByPosition(from);
        Piece toPiece = findByPosition(to);

        validateCommonMove(currentTeam, fromPiece, toPiece);        // 공통 이동 검증 로직 시작

        fromPiece.move(from, to, this); // 기물별 이동 검증 로직

        //여기오면 이상없으니깐 이제 실제 기물들 위치 변경
        place(from, new EmptyPosition(Team.OTHER));
        place(to, fromPiece);
    }

    public void place(Position to, Piece nextPiece) {
        board.get(to.getRow()).set(to.getCol(), nextPiece);
    }


    private void validateCommonMove(Team currentTeam, Piece fromPiece, Piece toPiece) {
        if (!fromPiece.isSameTeam(currentTeam)) {
            throw new IllegalArgumentException("자신 진영의 기물을 선택해야합니다.");
        }

        if (fromPiece.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 선택하신 칸에 기물이 없습니다.");
        }

        if (toPiece.isSameTeam(currentTeam)) {
            throw new IllegalArgumentException("이미 도착지점에 플레이어님의 진영 기물이 있습니다.");
        }
    }


}
