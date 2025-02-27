package com.venned.simpletoons.enums;

public enum AgeStage {
    CHILD(-10, 2.0, -1.0, false),
    TEENAGER(-5, 3.0, 0.0, true),
    ADULT(0, 3.0, 0.0, true),
    OLD_AGE(-8, 2.5, -0.5, true);

    private final int healthModifier;
    private final double reach;
    private final double heightModifier;
    private final boolean canLevelUp;

    AgeStage(int healthModifier, double reach, double heightModifier, boolean canLevelUp) {
        this.healthModifier = healthModifier;
        this.reach = reach;
        this.heightModifier = heightModifier;
        this.canLevelUp = canLevelUp;
    }

    public int getHealthModifier() {
        return healthModifier;
    }

    public double getReach() {
        return reach;
    }

    public double getHeightModifier() {
        return heightModifier;
    }

    public boolean canLevelUp() {
        return canLevelUp;
    }
}
