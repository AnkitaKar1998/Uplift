package com.example.uplift;

public class ModelForMilestone {

    String percent, description;

    public ModelForMilestone(String percent, String description) {
        this.percent = percent;
        this.description = description;
    }

    public String getPercent() {
        return percent;
    }

    public String getDescription() {
        return description;
    }
}
