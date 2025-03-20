package janggi.board;

import janggi.team.Team;
import janggi.piece.*;
import janggi.position.Position;

import java.util.ArrayList;
import java.util.List;

public class BoardCho {
    private final List<Piece> pieces;

    public BoardCho(BoardOption option) {
        pieces = initBoard(option);
    }

    public List<Piece> initBoard(BoardOption option) {
        List<Piece> choDefaultPosition = new ArrayList<>(List.of(
                new King(Team.CHO,new Position(4, 1)),
                new Cannon(Team.CHO,new Position(1, 2)), new Cannon(Team.CHO,new Position(7, 2)),
                new Chariot(Team.CHO,new Position(0, 0)), new Chariot(Team.CHO,new Position(8, 0)),
                new Soldier(Team.CHO,new Position(0, 3)),
                new Soldier(Team.CHO,new Position(2, 3)),
                new Soldier(Team.CHO,new Position(4, 3)),
                new Soldier(Team.CHO,new Position(6, 3)),
                new Soldier(Team.CHO,new Position(8, 3)),
                new Guard(Team.CHO,new Position(3, 0)), new Guard(Team.CHO,new Position(5, 0))
        ));
        choDefaultPosition.addAll(option.getPieces());
        return choDefaultPosition;
    }

    public boolean containPiece(String pieceName, Position position) {
        return pieces.stream()
                .anyMatch(piece -> piece.getName().equals(pieceName) && piece.getPosition().equals(position));
    }

//    public boolean isLegalMove(String pieceName, List<Position> positionsOnPath) {
//        //각 지나치는 좌표에 대해 초진영 지닌 말을 돌면서 차지하고 있는지 확인한다
//        //만약 사용자가 입력한 말이 상이라면,
//        // 상은 앞의 한 칸을 확인해야 한다
//        // 포는 앞의 한칸에 무언가 있어야 한다
//        //
//        for (Position position : positionsOnPath) { //이동하는 좌표들
//            for (Piece piece : pieces) {
////           if( piece.isOccupiedByMe()
//            }
//        }
//    }

    public boolean isOccupiedByOurTeamPiece(Team teamName, Position movedPosition) {
        // 움직이고자 하는 도착 위치가 자신의 팀의 말이 차지하고 있는지 확인하다
        for (Piece piece : pieces) {
            if (piece.isOccupiedByMe(movedPosition) && teamName.equals(piece.getTeam())) {
                return true;
//                throw new IllegalArgumentException("이미 해당 위치에 같은 팀의 말이 자리하고 있습니다!");
            }
        }
        return false;
    }

    public List<Piece> getBoard() {
        return pieces;
    }
}
