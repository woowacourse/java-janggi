package view;

import domain.Team;
import domain.UnitType;
import domain.position.Position;
import domain.position.Route;
import domain.unit.Unit;
import java.util.List;

public class OutputView {

    public void printUnits(List<Unit> units) {
        for (Unit unit : units) {
            Team team = unit.getTeam();
            UnitType type = unit.getType();
            System.out.printf("%s : %s : x = %d, y = %d\n"
                    , teamToName(team), typeToName(type), unit.getPosition().getX(), unit.getPosition().getY());
        }
    }

    public void printAvailableRoute(List<Route> routes) {
        for (Route route : routes) {
            Position endPoint = route.searchEndPoint();
            System.out.printf("x = %d, y = %d\n"
                    , endPoint.getX(), endPoint.getY());
        }
    }

    private String typeToName(UnitType unitType) {
        if(unitType == UnitType.KING) {
            return "궁";
        }
        if(unitType == UnitType.SCHOLAR) {
            return "사";
        }
        if(unitType == UnitType.CAR) {
            return "차";
        }
        if(unitType == UnitType.HORSE) {
            return "마";
        }
        if(unitType == UnitType.ELEPHANT) {
            return "상";
        }
        if(unitType == UnitType.BOMB) {
            return "포";
        }
        if(unitType == UnitType.JOL) {
            return "병";
        }
        return "";
    }

    private String teamToName(Team team) {
        if (team == Team.HAN) {
            return "한나라";
        }
        return "초나라";
    }
}
