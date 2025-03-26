//package repo;
//
//import domain.board.Point;
//import domain.piece.Piece;
//import java.util.List;
//
//public class PieceDAO {
//    int row;
//    int column;
//    String gameRoomName;
//    String teamName;
//    String pieceTypeName;
//
//    //
//    public void createPieceDAO() {
//
//    }
//
//
//    // 불러오기. 게임 재시작
//    public List<PieceDAO> findAll(String gameRoomName) {
//        return "SELECT * FROM piece where=" + gameRoomName;
//    }
//
//    // move 커맨드
//    public void updatePieceDAO(String gameRoomName, Point point, Piece piece) {
//
//    }
//
//    // 게임 종료 커맨드
//    public void deleteAll(String gameRoomName) {
//        "DELETE FROM piece where=" + gameRoomName;
//    }
//
//}
