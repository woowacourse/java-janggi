package dto;

import java.util.List;

public record BoardStatusDto(
        List<List<PositionStatusDto>> positionStatusDtos
) {

}
