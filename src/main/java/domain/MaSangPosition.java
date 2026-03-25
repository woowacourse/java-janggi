//package domain;
//
//import java.util.List;
//import java.util.Map;
//
//public enum MaSangPosition {
//    MA_SANG_SANG_MA(1,
//            Map.of(new Position(1, 2), PieceType.MA.getChoPiece()
//                    , new Position(1, 3), PieceType.SANG.getChoPiece()
//            , new Position(1, 7), PieceType.SANG.getChoPiece(),
//                    new Position(1, 8), PieceType.MA.getChoPiece()),
//            Map.of(new Position(10, 2), PieceType.MA.getHanPiece()
//                    , new Position(10, 3), PieceType.SANG.getHanPiece()
//                    , new Position(10, 7), PieceType.SANG.getHanPiece(),
//                    new Position(10, 8), PieceType.MA.getHanPiece())
//    ),
//
//    MA_SANG_MA_SANG(2,
//            Map.of(new Position(1, 2), PieceType.MA.getChoPiece()
//                    , new Position(1, 3), PieceType.SANG.getChoPiece()
//                    , new Position(1, 7), PieceType.MA.getChoPiece(),
//                    new Position(1, 8), PieceType.SANG.getChoPiece()),
//            Map.of(new Position(10, 2), PieceType.MA.getHanPiece()
//                    , new Position(10, 3), PieceType.SANG.getHanPiece()
//                    , new Position(10, 7), PieceType.MA.getHanPiece(),
//                    new Position(10, 8), PieceType.SANG.getHanPiece())
//    ),
//
//    SANG_MA_SANG_MA(3,
//            Map.of(new Position(1, 2), PieceType.SANG.getChoPiece()
//                    , new Position(1, 3), PieceType.MA.getChoPiece()
//                    , new Position(1, 7), PieceType.SANG.getChoPiece(),
//                    new Position(1, 8), PieceType.MA.getChoPiece()),
//            Map.of(new Position(10, 2), PieceType.SANG.getHanPiece()
//                    , new Position(10, 3), PieceType.MA.getHanPiece()
//                    , new Position(10, 7), PieceType.SANG.getHanPiece(),
//                    new Position(10, 8), PieceType.MA.getHanPiece())
//    ),
//
//    SANG_MA_MA_SANG(4,
//            Map.of(new Position(1, 2), PieceType.SANG.getChoPiece()
//                    , new Position(1, 3), PieceType.MA.getChoPiece()
//                    , new Position(1, 7), PieceType.MA.getChoPiece(),
//                    new Position(1, 8), PieceType.SANG.getChoPiece()),
//            Map.of(new Position(10, 2), PieceType.SANG.getHanPiece()
//                    , new Position(10, 3), PieceType.MA.getHanPiece()
//                    , new Position(10, 7), PieceType.MA.getHanPiece(),
//                    new Position(10, 8), PieceType.SANG.getHanPiece())
//    );
//
//    private final int command;
//    private final Map<Position, Piece> cho;
//    private final Map<Position, Piece> han;
//
//    MaSangPosition(int command,
//                   Map<Position, Piece> cho, Map<Position, Piece> han) {
//        this.command = command;
//        this.cho = cho;
//        this.han = han;
//    }
//
//    public static MaSangPosition getChoMaFromNumber(int number) {
//        return domain.MaSangPosition.values()[number - 1];
//    }
//
//    public static MaSangPosition getHanMaFromNumber(int number) {
//        return domain.MaSangPosition.values()[number - 1];
//    }
//
//    public Map<Position, Piece> getChoMaPosition() {
//        return cho;
//    }
//
//    public Map<Position, Piece> getHanMaPosition() {
//        return han;
//    }
//}