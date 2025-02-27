package com.venned.simpletoons.build;

import com.venned.simpletoons.professions.Profession;

import java.util.*;

public class PlayerTon {
    UUID owner_uuid;
    String name;
    int age;
    String culture;
    String gender;
    String description;
    String race;
    boolean active;
    Set<String> lockedStatuses;

    private Map<String, Profession> professions;

    public PlayerTon(UUID uuid) {
        this.owner_uuid = uuid;
        this.lockedStatuses = new HashSet<>();
        this.professions = new HashMap<>();
    }

    public PlayerTon(UUID uuid, String name, int age, String culture, String gender, String description, String race, boolean active) {
        this.owner_uuid = uuid;
        this.name = name;
        this.age = age;
        this.culture = culture;
        this.gender = gender;
        this.description = description;
        this.race = race;
        this.active = active;
        this.lockedStatuses = new HashSet<>();
        this.professions = new HashMap<>();
    }

    public boolean isActive() {
        return active;
    }

    public boolean isStatusLocked(String status) {
        return lockedStatuses.contains(status);
    }


    public void setActive(boolean active) {
        this.active = active;
    }

    public String getRace() {
        return race;
    }

    public UUID getOwner_uuid() {
        return owner_uuid;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getCulture() {
        return culture;
    }

    public String getGender() {
        return gender;
    }

    public String getDescription() {
        return description;
    }

    public Set<String> getLockedStatuses() {
        return lockedStatuses;
    }


    public void setOwner_uuid(UUID owner_uuid) {
        this.owner_uuid = owner_uuid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setCulture(String culture) {
        this.culture = culture;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRace(String race) {
        this.race = race;
    }


    public void lockStatus(String status) {
        lockedStatuses.add(status);
    }

    public void unlockStatus(String status) {
        lockedStatuses.remove(status);
    }


    public boolean addProfession(Profession profession) {
        String key = profession.getName().toLowerCase();
        if (professions.containsKey(key)) {
            return false;
        }
        professions.put(key, profession);
        return true;
    }

    public void removeProfession(Profession profession) {
        String key = profession.getName().toLowerCase();
        professions.remove(key);
    }

    public Profession getProfession(String name) {
        return professions.get(name.toLowerCase());
    }

    public Map<String, Profession> getAllProfessions() {
        return professions;
    }
}
