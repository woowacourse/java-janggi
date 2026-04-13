package domain.game;

import domain.board.Board;
import domain.board.Intersection;
import domain.piece.AlivePieces;
import domain.piece.Piece;
import domain.piece.PieceType;
import java.util.HashMap;
import java.util.Map;

public final class JanggiGameFixture {

    private JanggiGameFixture() {
    }

    public static JanggiGame create_game_with_sufficient_points_and_one_general_captured(Side generalUncapturedSide) {
        Map<Intersection, Piece> pieces = create_pieces_with_sufficient_points_for_game_continue();

        // 한쪽 진영의 왕만 생존
        pieces.put(position(5, 5), Piece.of(PieceType.GENERAL, generalUncapturedSide));

        Board board = new Board(new AlivePieces(pieces));

        return JanggiGame.load(board, generalUncapturedSide.nextTurn());
    }

    public static JanggiGame create_game_with_sufficient_points_and_both_general_uncaptured() {
        Map<Intersection, Piece> pieces = create_pieces_with_sufficient_points_for_game_continue();

        // 양쪽 진영의 왕 생존
        pieces.put(position(5, 5), Piece.of(PieceType.GENERAL, Side.CHO));
        pieces.put(position(6, 6), Piece.of(PieceType.GENERAL, Side.HAN));

        Board board = new Board(new AlivePieces(pieces));

        return JanggiGame.create(board);
    }

    public static JanggiGame create_game_with_sufficient_points_only_one_side(Side sufficientPointsSide) {
        Map<Intersection, Piece> pieces = new HashMap<>();

        // 양측 진영을 26점으로 설정
        pieces.put(position(1, 1), Piece.of(PieceType.CHARIOT, Side.CHO));
        pieces.put(position(1, 2), Piece.of(PieceType.CHARIOT, Side.CHO));
        pieces.put(position(1, 3), Piece.of(PieceType.GENERAL, Side.CHO));
        pieces.put(position(10, 1), Piece.of(PieceType.CHARIOT, Side.HAN));
        pieces.put(position(10, 2), Piece.of(PieceType.CHARIOT, Side.HAN));
        pieces.put(position(10, 3), Piece.of(PieceType.GENERAL, Side.HAN));

        // 한 쪽 진영만 30점이 되도록 4점을 추가
        pieces.put(position(5, 5), Piece.of(PieceType.SOLDIER, sufficientPointsSide));
        pieces.put(position(6, 6), Piece.of(PieceType.SOLDIER, sufficientPointsSide));

        Board board = new Board(new AlivePieces(pieces));

        return JanggiGame.create(board);
    }

    public static JanggiGame create_game_with_insufficient_points_and_both_general_uncaptured(Side winner) {
        Map<Intersection, Piece> pieces = new HashMap<>();

        // CHO 26점
        pieces.put(position(1, 1), Piece.of(PieceType.CHARIOT, Side.CHO));
        pieces.put(position(1, 2), Piece.of(PieceType.CHARIOT, Side.CHO));
        pieces.put(position(1, 3), Piece.of(PieceType.GENERAL, Side.CHO));

        // HAN 26점
        pieces.put(position(10, 1), Piece.of(PieceType.CHARIOT, Side.HAN));
        pieces.put(position(10, 2), Piece.of(PieceType.CHARIOT, Side.HAN));
        pieces.put(position(10, 3), Piece.of(PieceType.GENERAL, Side.HAN));

        // 승자로 설정되는 쪽이 기물 한 개를 더 가져서 29점이 됨
        pieces.put(position(5, 5), Piece.of(PieceType.ELEPHANT, winner));

        Board board = new Board(new AlivePieces(pieces));

        return JanggiGame.create(board);
    }

    public static JanggiGame create_game_with_insufficient_same_points_and_both_general_uncaptured() {
        return create_game_with_insufficient_points_and_both_general_uncaptured(Side.NONE);
    }

    public static JanggiGame create_game_with_insufficient_points_and_both_general_uncaptured() {
        final Side anySide = Side.CHO;

        return create_game_with_insufficient_points_and_both_general_uncaptured(anySide);
    }

    private static Map<Intersection, Piece> create_pieces_with_sufficient_points_for_game_continue() {
        Map<Intersection, Piece> pieces = new HashMap<>();

        // 초(CHO) 점수를 30점 이상으로 설정
        pieces.put(position(1, 1), Piece.of(PieceType.CHARIOT, Side.CHO));
        pieces.put(position(1, 9), Piece.of(PieceType.CHARIOT, Side.CHO));
        pieces.put(position(3, 2), Piece.of(PieceType.CANNON, Side.CHO));

        // 한(HAN) 점수를 30점 이상으로 설정
        pieces.put(position(10, 1), Piece.of(PieceType.CHARIOT, Side.HAN));
        pieces.put(position(10, 9), Piece.of(PieceType.CHARIOT, Side.HAN));
        pieces.put(position(8, 2), Piece.of(PieceType.CANNON, Side.HAN));

        return pieces;
    }

    private static Intersection position(int row, int file) {
        return new Intersection(row, file);
    }
}
