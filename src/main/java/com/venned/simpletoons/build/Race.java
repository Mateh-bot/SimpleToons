package com.venned.simpletoons.build;

public class Race {
    String name;
    int maxAge;
    double height;
    double reach;
    double attack;

    public Race(String name, int maxAge, double height, double reach, double attack) {
        this.name = name;
        this.maxAge = maxAge;
        this.height = height;
        this.reach = reach;
        this.attack = attack;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public String getName() {
        return name;
    }

    public double getAttack() {
        return attack;
    }

    public double getHeight() {
        return height;
    }

    public double getReach() {
        return reach;
    }
}
