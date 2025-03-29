package repository.entity;

import janggi.piece.Piece;

public record PieceEntity(
        String pieceId,
        String rowIndex,
        String columnIndex,
        String pieceTypeName,
        String teamName
) {

    public static PieceEntity toEntity(final Piece piece){
        return new PieceEntity(
    null,
            piece.row().ordinal()+"",
            piece.column().ordinal()+"",
            piece.type().toString(),
            piece.team().toString()
        );
    }

//    public Piece from(final PieceEntity pieceEntity){
//        return new Piece(
//                null,
//                piece.row().ordinal(),
//                piece.column().ordinal(),
//                piece.type().toString(),
//                piece.team().toString()
//        ) {
//        };
//    }
}
