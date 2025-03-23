package piece;

import java.util.List;
import java.util.Map;
import move.ChaMoveBehavior;
import move.FoMoveBehavior;
import move.GungMoveBehavior;
import move.JolMoveBehavior;
import move.MaMoveBehavior;
import move.SaMoveBehavior;
import move.SangMoveBehavior;

public class JanggiTeamPiecesCreateFactory {

    private static final Map<Team, Pieces> initiatePieces = Map.of(
            Team.RED, new Pieces(
                    List.of(
                            new Piece(new Position(0, 0), new ChaMoveBehavior(), PieceType.CHA, Team.RED),
                            new Piece(new Position(0, 1), new SangMoveBehavior(), PieceType.SANG, Team.RED),
                            new Piece(new Position(0, 2), new MaMoveBehavior(), PieceType.MA, Team.RED),
                            new Piece(new Position(0, 3), new SaMoveBehavior(), PieceType.SA, Team.RED),
                            new Piece(new Position(0, 5), new SaMoveBehavior(), PieceType.SA, Team.RED),
                            new Piece(new Position(0, 6), new SangMoveBehavior(), PieceType.SANG, Team.RED),
                            new Piece(new Position(0, 7), new MaMoveBehavior(), PieceType.MA, Team.RED),
                            new Piece(new Position(0, 8), new ChaMoveBehavior(), PieceType.CHA, Team.RED),
                            new Piece(new Position(1, 4), new GungMoveBehavior(), PieceType.GUNG, Team.RED),
                            new Piece(new Position(2, 1), new FoMoveBehavior(), PieceType.FO, Team.RED),
                            new Piece(new Position(2, 7), new FoMoveBehavior(), PieceType.FO, Team.RED),
                            new Piece(new Position(3, 0), new JolMoveBehavior(), PieceType.JOL, Team.RED),
                            new Piece(new Position(3, 2), new JolMoveBehavior(), PieceType.JOL, Team.RED),
                            new Piece(new Position(3, 4), new JolMoveBehavior(), PieceType.JOL, Team.RED),
                            new Piece(new Position(3, 6), new JolMoveBehavior(), PieceType.JOL, Team.RED),
                            new Piece(new Position(3, 8), new JolMoveBehavior(), PieceType.JOL, Team.RED)
                    )
            ),
            Team.BLUE,
            new Pieces(
                    List.of(
                            new Piece(new Position(6, 0), new JolMoveBehavior(), PieceType.JOL, Team.BLUE),
                            new Piece(new Position(6, 2), new JolMoveBehavior(), PieceType.JOL, Team.BLUE),
                            new Piece(new Position(6, 4), new JolMoveBehavior(), PieceType.JOL, Team.BLUE),
                            new Piece(new Position(6, 6), new JolMoveBehavior(), PieceType.JOL, Team.BLUE),
                            new Piece(new Position(6, 8), new JolMoveBehavior(), PieceType.JOL, Team.BLUE),
                            new Piece(new Position(7, 1), new FoMoveBehavior(), PieceType.FO, Team.BLUE),
                            new Piece(new Position(7, 7), new FoMoveBehavior(), PieceType.FO, Team.BLUE),
                            new Piece(new Position(8, 4), new GungMoveBehavior(), PieceType.GUNG, Team.BLUE),
                            new Piece(new Position(9, 0), new ChaMoveBehavior(), PieceType.CHA, Team.BLUE),
                            new Piece(new Position(9, 1), new SangMoveBehavior(), PieceType.SANG, Team.BLUE),
                            new Piece(new Position(9, 2), new MaMoveBehavior(), PieceType.MA, Team.BLUE),
                            new Piece(new Position(9, 3), new SaMoveBehavior(), PieceType.SA, Team.BLUE),
                            new Piece(new Position(9, 5), new SaMoveBehavior(), PieceType.SA, Team.BLUE),
                            new Piece(new Position(9, 6), new SangMoveBehavior(), PieceType.SANG, Team.BLUE),
                            new Piece(new Position(9, 7), new MaMoveBehavior(), PieceType.MA, Team.BLUE),
                            new Piece(new Position(9, 8), new ChaMoveBehavior(), PieceType.CHA, Team.BLUE)
                    )
            ));

    public JanggiTeamPiecesCreateFactory() {
    }


    public Map<Team, Pieces> createJanggiInitiatePieces() {
        return initiatePieces;
    }
}

