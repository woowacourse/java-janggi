/*
package janggi.board;

import janggi.piece.*;
import janggi.position.Position;
import janggi.team.Team;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private final List<Piece> pieces;

    public Board(TableOption choOption, TableOption hanOption) {
        pieces = initBoard(choOption, hanOption);
    }

    public boolean isAvailablePiece(Team team, String pieceName, Position position) {
        if (team.equals(Team.CHO)) {
            return containPiece(pieceName, position);
        }
        return containPiece(pieceName, position);
    }

    public boolean checkLegalMove(List<Position> positionsOnPath) {
        return isLegalMove(positionsOnPath) || isLegalMove(positionsOnPath);
    }

    public List<Piece> initBoard(TableOption choOption, TableOption hanOption) {
        List<Piece> choDefaultPosition = new ArrayList<>(List.of(
                */
/*new Piece(Team.CHO,new Position(new PositionX(4), new PositionY(1))),
                new Piece(Team.CHO,new Position(new PositionX(1), new PositionY(2))),
                new Piece(Team.CHO,new Position(new PositionX(7), new PositionY(2))),
                new Piece(Team.CHO,new Position(new PositionX(0), new PositionY(0))),
                new Piece(Team.CHO,new Position(new PositionX(8), new PositionY(0))),
                new Piece(Team.CHO,new Position(new PositionX(0), new PositionY(3))),
                new Piece(Team.CHO,new Position(new PositionX(2), new PositionY(3))),
                new Piece(Team.CHO,new Position(new PositionX(4), new PositionY(3))),
                new Piece(Team.CHO,new Position(new PositionX(6), new PositionY(3))),
                new Piece(Team.CHO,new Position(new PositionX(8), new PositionY(3))),
                new Piece(Team.CHO,new Position(new PositionX(3), new PositionY(0))),
                new Piece(Team.CHO,new Position(new PositionX(5), new PositionY(0))),
                new Piece(Team.HAN, new Position(new PositionX(4), new PositionY(8))),
                new Piece(Team.HAN, new Position(new PositionX(1), new PositionY(7))),
                new Piece(Team.HAN, new Position(new PositionX(7), new PositionY(7))),
                new Piece(Team.HAN, new Position(new PositionX(0), new PositionY(9))),
                new Piece(Team.HAN, new Position(new PositionX(8), new PositionY(9))),
                new Piece(Team.HAN, new Position(new PositionX(0), new PositionY(6))),
                new Piece(Team.HAN, new Position(new PositionX(2), new PositionY(6))),
                new Piece(Team.HAN, new Position(new PositionX(4), new PositionY(6))),
                new Piece(Team.HAN, new Position(new PositionX(6), new PositionY(6))),
                new Piece(Team.HAN, new Position(new PositionX(8), new PositionY(6))),
                new Piece(Team.HAN, new Position(new PositionX(3), new PositionY(9))),
                new Piece(Team.HAN, new Position(new PositionX(5), new PositionY(9)))*//*

        ));
//        choDefaultPosition.addAll(choOption.getPieces());
//        choDefaultPosition.addAll(hanOption.getPieces());
        return choDefaultPosition;
    }

    public boolean containPiece(String pieceName, Position position) {
        return pieces.stream()
                .anyMatch(piece -> piece.getName().equals(pieceName) && piece.getPosition().equals(position));
    }

    public void move(Team team, String nickname, Position startPosition, Position endPosition) {
        Piece targetPiece = pieces.stream()
                .filter(piece ->
                        piece.getTeam() == team && piece.getName().equals(nickname) && piece.getPosition().equals(startPosition)
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
            if (positionsOnPath.contains(piece.getPosition())) {
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
*/
