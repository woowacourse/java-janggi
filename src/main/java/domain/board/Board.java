package domain.board;

import domain.Path;
import domain.piece.Piece;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Board {
    private final Map<Position, Piece> pieces;

    public Board(Map<Position, Piece> pieces) {
        this.pieces = pieces;
    }

    public Piece getPiece(Position position) {
        return pieces.get(position);
    }

    public void move(Position from, Position to) {

        // (1) piece 가 해당 도착지(to)로 도착하는 경로 반환  : Piece의 getPathPositions 호출

        Piece fromPiece = pieces.get(from); // 실제 움직일 애
        Piece toPiece = pieces.get(to);
        List<Position> pathPositions = fromPiece.getPathPositions(from, to); // 좌표(경유지) 목록을 반환
        List<Path> path = getPath(pathPositions); // 해당 좌표(경유지)에 대해서 반환함.
        fromPiece.canMove(path, toPiece);


        if (fromPiece.isSameTeam(toPiece)) {
            throw new IllegalStateException("같은 팀의 기물을 잡을 수 없습니다.");
        }

        // 잡는 행위
        // 실제로 옮겨진
        // 상대편 위치, 지금할껀지 or 있다가 할껀지, 초/한

        // (2) 1에서 받은 값으로 getPathWithPiece 호출하여 Piece의 canMove 호출
        // 해당 위치(from)에 기물이 위치하고 있는지
    }

    public List<Path> getPath(List<Position> positions) {
        List<Path> paths = new ArrayList<>();
        for (Position position : positions) {
            if (pieces.containsKey(position)) {
                paths.add(new Path(position, pieces.get(position)));
            }
        }
        return paths;
    }

    // TODO 같은 팀이면 예외 발생, 다른 팀이면 잡는 함수 작성 필요(move 안에)
}
