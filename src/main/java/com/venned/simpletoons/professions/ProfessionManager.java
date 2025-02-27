package com.venned.simpletoons.professions;

import com.venned.simpletoons.professions.blacksmith.Blacksmith;
import com.venned.simpletoons.professions.chef.Chef;
import com.venned.simpletoons.professions.farmer.Farmer;
import com.venned.simpletoons.professions.fisherman.Fisherman;
import com.venned.simpletoons.professions.husbander.Husbander;
import com.venned.simpletoons.professions.mason.Mason;
import com.venned.simpletoons.professions.woodcutter.Woodcutter;

import java.util.ArrayList;
import java.util.List;

public class ProfessionManager {
    private List<Profession> availableProfessions;

    public ProfessionManager() {
        availableProfessions = new ArrayList<>();

        availableProfessions.add(new Chef());
        availableProfessions.add(new Blacksmith());
        availableProfessions.add(new Farmer());
        availableProfessions.add(new Husbander());
        availableProfessions.add(new Mason());
        availableProfessions.add(new Woodcutter());
        availableProfessions.add(new Fisherman());
    }

    public List<Profession> getAllProfessions() {
        return availableProfessions;
    }

    public Profession createProfession(String name) {
        switch (name.toLowerCase()) {
            case "chef":
                return new Chef();
            case "blacksmith":
                return new Blacksmith();
            case "farmer":
                return new Farmer();
            case "husbander":
                return new Husbander();
            case "mason":
                return new Mason();
            case "woodcutter":
                return new Woodcutter();
            case "fisherman":
                return new Fisherman();
            default:
                return null;
        }
    }
}