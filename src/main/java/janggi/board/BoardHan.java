package janggi.board;

import janggi.team.Team;
import janggi.piece.*;
import janggi.position.Position;

import java.util.ArrayList;
import java.util.List;

public class BoardHan {
    private final List<Piece> pieces;

    public BoardHan(BoardOption option) {
        pieces = initBoard(option);
    }

    public List<Piece> initBoard(BoardOption option) {
        List<Piece> hanDefaultPosition = new ArrayList<>(List.of(
                new King(Team.HAN, new Position(4, 8)),
                new Cannon(Team.HAN, new Position(1, 7)), new Cannon(Team.HAN, new Position(7, 7)),
                new Chariot(Team.HAN, new Position(0, 9)), new Chariot(Team.HAN, new Position(8, 9)),
                new Soldier(Team.HAN, new Position(0, 6)),
                new Soldier(Team.HAN, new Position(2, 6)),
                new Soldier(Team.HAN, new Position(4, 6)),
                new Soldier(Team.HAN, new Position(6, 6)),
                new Soldier(Team.HAN, new Position(8, 6)),
                new Guard(Team.HAN, new Position(3, 9)), new Guard(Team.HAN, new Position(5, 9))));
        hanDefaultPosition.addAll(option.getPieces());
        return hanDefaultPosition;
    }

    public boolean containPiece(String pieceName, Position position) {
        return pieces.stream()
                .anyMatch(piece -> piece.getName().equals(pieceName) && piece.getPosition().equals(position));
    }

    public void move(String nickname, Position startPosition ,Position endPosition) {
        Piece targetPiece = pieces.stream()
                .filter(piece ->
                        piece.getName().equals(nickname) && piece.getPosition().equals(startPosition)
                ).
                findFirst()
                .orElseThrow(() -> new IllegalArgumentException("이동할 말을 찾을 수 없습니다."));

        targetPiece.move(endPosition);
    }

    public boolean isLegalMove(List<Position> positionsOnPath) {
        return pieces.stream().anyMatch(
                piece -> positionsOnPath.contains(piece.getPosition())
        );
    }

    public boolean isLegalMoveForCannon(List<Position> positionsOnPath) {
        int obstacleCount = 0;
        for (Piece piece : pieces) {
            if (positionsOnPath.contains(piece.getPosition())){
                obstacleCount += 1;
            }
        }
        boolean isSameCannonKind = pieces.stream()
                .anyMatch(piece -> positionsOnPath.contains(piece.getPosition()) && "P".equals(piece.getName()));

        return obstacleCount == 1 && !isSameCannonKind;
    }

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
