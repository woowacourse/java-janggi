package janggi.board.dao;

import janggi.board.JanggiBoard;
import janggi.piece.Piece;
import janggi.piece.Pieces;
import janggi.value.JanggiPosition;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class FakeJanggiBoardDao implements JanggiBoardDao {
    //실제 DB에서 FK로 team_id로 조회를 하니까
    private final Map<Integer, List<Piece>> database = new HashMap<>();

    //실제 DB에서 insert할 때 janggiBoard를 받아서 꺼내서 하니까
    @Override
    public void insertPieces(JanggiBoard janggiBoard) {
        database.put(1, janggiBoard.getChoPieces());
        database.put(2, janggiBoard.getHanPieces());
    }

    //실제 DB에서도 Pieces 테이블을 지우니까
    @Override
    public void dropTables() {
        database.clear();
    }

    //실제 DB에서 출발지랑 도착지 전달받아서 바꿔주는 방식이니까
    @Override
    public void updateRecords(JanggiPosition current, JanggiPosition destination, int teamId) {
        List<Piece> pieces = database.get(teamId);

        if (teamId == 1) {
            Piece targetPiece = pieces.stream()
                    .filter(piece -> piece.getPosition().equals(current))
                    .findFirst()
                    .orElseThrow();

            Piece movedPiece = targetPiece.move(destination, new Pieces(database.get(2)), new Pieces(pieces));
            database.put(teamId, List.of(movedPiece));
            return;
        }
        Piece targetPiece = pieces.stream()
                .filter(piece -> piece.getPosition().equals(current))
                .findFirst()
                .orElseThrow();

        Piece movedPiece = targetPiece.move(destination, new Pieces(database.get(1)), new Pieces(pieces));
        database.put(teamId, List.of(movedPiece));
    }

    @Override
    public void deleteRecords(JanggiPosition destination, int teamId) {
        List<Piece> pieces = database.get(teamId);
        boolean isInRecord = pieces.stream()
                .anyMatch(piece -> piece.getPosition().equals(destination));
        if (isInRecord) {
            database.put(teamId, List.of());
        }
    }

    @Override
    public List<Piece> selectChoRecords() {
        return new ArrayList<>(database.get(1));
    }

    @Override
    public List<Piece> selectHanRecords() {
        return new ArrayList<>(database.get(2));
    }
}
