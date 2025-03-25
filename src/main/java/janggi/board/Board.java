package janggi.board;

import janggi.piece.Piece;
import janggi.position.Position;
import janggi.team.Team;

import java.util.List;

public class Board {

    private final List<Piece> positionedPieces;

    public Board(List<Piece> positionedPieces) {
        this.positionedPieces = positionedPieces;
    }

    // TODO 기물 출발 위치 및 도착 위치를 받아 기물 이동
    // TODO 턴을 넘겨주며 게임 진행
    public void attack(Team turn, Position startPosition, Position arrivedPosition) {
        Piece selectedPiece = findByPosition(startPosition);
        checkTurn(turn, selectedPiece);
        selectedPiece.move(arrivedPosition,positionedPieces);
    }

    public void checkTurn(Team turn, Piece piece) {
        if (piece.isSameTeam(turn)) {
            return;
        };
        throw new IllegalArgumentException("순서를 확인하세요");
    }

    public Piece findByPosition(Position startPosition) {
        return positionedPieces.stream()
                .filter(piece -> piece.matchesPosition(startPosition))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 위치에 기물이 존재하지 않습니다"));
    }

    public List<Piece> getPositionedPieces() {
        return positionedPieces;
    }

    // TODO 살아있는 왕이 하나만 존재하면 게임 종료
}
