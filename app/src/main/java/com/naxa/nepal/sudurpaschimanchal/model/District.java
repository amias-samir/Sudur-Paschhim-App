package com.naxa.nepal.sudurpaschimanchal.model;

import java.util.ArrayList;

/**
 * Created by nishon.tan on 11/3/2016.
 */

public class District {
    private String id;
    private String enName;
    private String npName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getNpName() {
        return npName;
    }

    public void setNpName(String npName) {
        this.npName = npName;
    }

    public District(String id, String enName, String npName) {
        this.id = id;
        this.enName = enName;
        this.npName = npName;

    }

    public static ArrayList<District> createdistrictsList(int numContacts) {
        ArrayList<District> contacts = new ArrayList<District>();

        for (int i = 1; i <= numContacts; i++) {
            contacts.add(new District("District ", " District", "" + i));
        }

        return contacts;
    }
}
