package dto;

public record GameInfo(
        int id,
        String turn,
        double cho_score,
        double han_score
) {
}
