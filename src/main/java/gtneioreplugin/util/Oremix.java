package gtneioreplugin.util;

import java.util.Set;

public class Oremix implements Comparable<Oremix> {

    private String oreMixName;

    private String primary = "";

    private String secondary = "";

    private String inbetween = "";

    private String sporadic = "";

    private String oreMixIDs = "";

    private String height = "";

    private int density;

    private int size;

    private int weight;

    private static final int sizeData = 10; // hors dims

    private Set<String> dimensions;

    public void setDims(Set<String> dims) {
        this.dimensions = dims;
    }

    public String getOreMixName() {
        return this.oreMixName;
    }

    public void setOreMixName(String s) {
        this.oreMixName = s;
    }

    public void setPrimary(String s) {
        this.primary = s;
    }

    public void setSecondary(String s) {
        this.secondary = s;
    }

    public void setInbetween(String s) {
        this.inbetween = s;
    }

    public void setSporadic(String s) {
        this.sporadic = s;
    }

    public void setOreMixIDs(String s) {
        this.oreMixIDs = s;
    }

    public void setHeight(String s) {
        this.height = s;
    }

    public void setDensity(int i) {
        this.density = i;
    }

    public void setSize(int i) {
        this.size = i;
    }

    public void setWeight(int i) {
        this.weight = i;
    }

    @Override
    public int compareTo(Oremix ore) {
        return this.oreMixName.compareTo(ore.oreMixName);
    }

    public static String getCsvHeader() {
        return "";
    }

    public String getCsvEntry() {
        return "";
    }
}
