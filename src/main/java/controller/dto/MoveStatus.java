package controller.dto;

public record MoveStatus(String teamName,
                         String pieceName,
                         int destinationRow,
                         int destinationColumn) {
    public static MoveStatus of(String teamName,
                                String pieceName,
                                int destinationRow,
                                int destinationColumn) {
        return new MoveStatus(teamName,
                pieceName,
                destinationRow,
                destinationColumn);
    }
}
