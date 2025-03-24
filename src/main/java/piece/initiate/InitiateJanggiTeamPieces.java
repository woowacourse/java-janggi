package piece.initiate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import move.ChaMoveBehavior;
import move.FoMoveBehavior;
import move.GungMoveBehavior;
import move.JolMoveBehavior;
import move.SaMoveBehavior;
import piece.Piece;
import piece.Pieces;
import piece.Team;
import piece.position.Position;

public class InitiateJanggiTeamPieces {

    private static final Map<Team, List<Piece>> initiatePiecesWithoutMaSang = Map.of(
            Team.RED,
            List.of(
                    new Piece(new Position(0, 0), new ChaMoveBehavior(), Team.RED),
                    new Piece(new Position(0, 3), new SaMoveBehavior(), Team.RED),
                    new Piece(new Position(0, 5), new SaMoveBehavior(), Team.RED),
                    new Piece(new Position(0, 8), new ChaMoveBehavior(), Team.RED),
                    new Piece(new Position(1, 4), new GungMoveBehavior(), Team.RED),
                    new Piece(new Position(2, 1), new FoMoveBehavior(), Team.RED),
                    new Piece(new Position(2, 7), new FoMoveBehavior(), Team.RED),
                    new Piece(new Position(3, 0), new JolMoveBehavior(), Team.RED),
                    new Piece(new Position(3, 2), new JolMoveBehavior(), Team.RED),
                    new Piece(new Position(3, 4), new JolMoveBehavior(), Team.RED),
                    new Piece(new Position(3, 6), new JolMoveBehavior(), Team.RED),
                    new Piece(new Position(3, 8), new JolMoveBehavior(), Team.RED)
            ),
            Team.BLUE,
            List.of(
                    new Piece(new Position(6, 0), new JolMoveBehavior(), Team.BLUE),
                    new Piece(new Position(6, 2), new JolMoveBehavior(), Team.BLUE),
                    new Piece(new Position(6, 4), new JolMoveBehavior(), Team.BLUE),
                    new Piece(new Position(6, 6), new JolMoveBehavior(), Team.BLUE),
                    new Piece(new Position(6, 8), new JolMoveBehavior(), Team.BLUE),
                    new Piece(new Position(7, 1), new FoMoveBehavior(), Team.BLUE),
                    new Piece(new Position(7, 7), new FoMoveBehavior(), Team.BLUE),
                    new Piece(new Position(8, 4), new GungMoveBehavior(), Team.BLUE),
                    new Piece(new Position(9, 0), new ChaMoveBehavior(), Team.BLUE),
                    new Piece(new Position(9, 3), new SaMoveBehavior(), Team.BLUE),
                    new Piece(new Position(9, 5), new SaMoveBehavior(), Team.BLUE),
                    new Piece(new Position(9, 8), new ChaMoveBehavior(), Team.BLUE)
            )
    );

    private final Map<Team, Pieces> initiatePieces = new HashMap<>();

    public InitiateJanggiTeamPieces() {
        this(TableSetting.defaultOption());
    }


    public InitiateJanggiTeamPieces(Map<Team, TableSetting> teamTableSetting) {
        Team blueTeam = Team.BLUE;
        Team redTeam = Team.RED;
        List<Piece> bluePieces = addAllTeamPieces(teamTableSetting, blueTeam);
        List<Piece> redPieces = addAllTeamPieces(teamTableSetting, redTeam);
        initiatePieces.put(blueTeam, new Pieces(bluePieces));
        initiatePieces.put(redTeam, new Pieces(redPieces));
    }

    private List<Piece> addAllTeamPieces(Map<Team, TableSetting> teamTableSetting, Team team) {
        List<Piece> tableSettingPieces = new ArrayList<>(
                teamTableSetting.get(team).getTableSettingPieces(team));
        List<Piece> resultPieces = new ArrayList<>(initiatePiecesWithoutMaSang.get(team));
        resultPieces.addAll(tableSettingPieces);
        return resultPieces;
    }

    public Map<Team, Pieces> janggiInitiatePieces() {
        return initiatePieces;
    }
}

