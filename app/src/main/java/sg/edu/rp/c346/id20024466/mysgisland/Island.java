package sg.edu.rp.c346.id20024466.mysgisland;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Island implements Serializable {

    private int id;
    private String name;
    private String desc;
    private int km;
    private int stars;

    public Island(int id, String name, String desc, int km, int stars) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.km = km;
        this.stars = stars;
    }

    @Override
    public String toString() {
        return "Island{" +
                "desc='" + desc + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getKm() {
        return km;
    }

    public void setKm(int km) {
        this.km = km;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }
    @NonNull
    public String toStringKm() {
        String kmString = "";
        for(int i = 0; i < km; i++){
            kmString += " * ";
       }
       return kmString;

    }

}

