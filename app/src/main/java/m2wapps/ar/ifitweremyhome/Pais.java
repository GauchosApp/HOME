package m2wapps.ar.ifitweremyhome;

import java.util.ArrayList;

/**
 * Created by mariano on 24/02/2017.
 */

public class Pais {
    private String digit;
    private String name;
    private ArrayList<String> info;

    public Pais(String name, String digit){
        this.name = name;
        this.digit = digit;
        info = new ArrayList<>();
    }
    public String getDigit() {
        return digit;
    }

    public void setDigit(String digit) {
        this.digit = digit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addInfo(String info){
        this.info.add(info);
    }
    public ArrayList<String> getInfo(){
        return this.info;
    }
}
