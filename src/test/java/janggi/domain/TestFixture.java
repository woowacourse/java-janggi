package janggi.domain;

import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.TeamColor;

public class TestFixture {
    public static final Piece RED_HORSE = new Piece(TeamColor.RED, PieceType.HORSE);
    public static final Piece BLUE_HORSE = new Piece(TeamColor.BLUE, PieceType.HORSE);

    public static final Piece RED_SOLDIER = new Piece(TeamColor.RED, PieceType.SOLDIER);
    public static final Piece BLUE_SOLDIER = new Piece(TeamColor.BLUE, PieceType.SOLDIER);

    public static final Piece RED_CHARIOT = new Piece(TeamColor.RED, PieceType.CHARIOT);
    public static final Piece BLUE_CHARIOT = new Piece(TeamColor.BLUE, PieceType.CHARIOT);

    public static final Piece RED_CANNON = new Piece(TeamColor.RED, PieceType.CANNON);
    public static final Piece BLUE_CANNON = new Piece(TeamColor.BLUE, PieceType.CANNON);

    public static final Piece RED_ELEPHANT = new Piece(TeamColor.RED, PieceType.ELEPHANT);
    public static final Piece BLUE_ELEPHANT = new Piece(TeamColor.BLUE, PieceType.ELEPHANT);

    public static final Piece RED_GENERAL = new Piece(TeamColor.RED, PieceType.GENERAL);
    public static final Piece BLUE_GENERAL = new Piece(TeamColor.BLUE, PieceType.GENERAL);

    public static final Piece RED_GUARD = new Piece(TeamColor.RED, PieceType.GUARD);
    public static final Piece BLUE_GUARD = new Piece(TeamColor.BLUE, PieceType.GUARD);
}
