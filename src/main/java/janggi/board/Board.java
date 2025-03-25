package janggi.board;

import janggi.piece.Piece;
import janggi.piece.PieceGenerator;
import janggi.position.Position;
import janggi.team.Team;
import janggi.view.Input;
import janggi.view.Output;

import java.util.List;

public class Board {

    private Input inputView;
    private Output outputView;

    private final List<Piece> positionedPieces;

    public Board(List<Piece> positionedPieces) {
        this.positionedPieces = positionedPieces;
    }

    // TODO 상차림에 따른 보드 초기화
    // TODO 기물 출발 위치 및 도착 위치를 받아 기물 이동
    // TODO 턴을 넘겨주며 게임 진행

    public void play() {
        List<Piece> pieces = generateInitialPieces();
    }

    public List<Piece> generateInitialPieces() {
        String choTableOptionInput = inputView.readTableOption(Team.CHO);
        TableOption choTable = TableOption.from(choTableOptionInput);

        String hanTableOptionInput = inputView.readTableOption(Team.HAN);
        TableOption hanTable = TableOption.from(hanTableOptionInput);

        return new PieceGenerator().generateInitialPieces(hanTable, choTable);
    }

    public Piece findByPosition(Position startPosition) {
        return positionedPieces.stream()
                .filter(piece -> piece.matchesPosition(startPosition))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 위치에 기물이 존재하지 않습니다"));
    }
}
