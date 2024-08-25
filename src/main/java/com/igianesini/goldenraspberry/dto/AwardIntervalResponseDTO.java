package com.igianesini.goldenraspberry.dto;

import java.util.List;

public class AwardIntervalResponseDTO {
    private List<AwardIntervalDTO> min;
    private List<AwardIntervalDTO> max;

    public AwardIntervalResponseDTO(List<AwardIntervalDTO> min, List<AwardIntervalDTO> max) {
        this.min = min;
        this.max = max;
    }

    public AwardIntervalResponseDTO() {
    }

    public List<AwardIntervalDTO> getMin() {
        return min;
    }

    public void setMin(List<AwardIntervalDTO> min) {
        this.min = min;
    }

    public List<AwardIntervalDTO> getMax() {
        return max;
    }

    public void setMax(List<AwardIntervalDTO> max) {
        this.max = max;
    }
}
