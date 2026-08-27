package gtneioreplugin.util;

import java.util.Set;

@SuppressWarnings("unused")
public class SmallOre implements Comparable<SmallOre> {

    private String oreName;
    private String oreMaterial;
    private int amount;
    private String height = "";
    private static final int sizeData = 4; // hors dims

    private Set<String> dimensions;

    public void setDims(Set<String> dims) {
        this.dimensions = dims;
    }

    public String getOreName() {
        return this.oreName;
    }

    public void setOreName(String s) {
        this.oreName = s;
    }

    public String getOreMaterial() {
        return this.oreMaterial;
    }

    public void setOreMaterial(String mat) {
        this.oreMaterial = mat;
    }

    public String getHeight() {
        return this.height;
    }

    public void setHeight(String s) {
        this.height = s;
    }

    public void setAmount(int i) {
        this.amount = i;
    }

    public int getAmount() {
        return this.amount;
    }

    public static String getCsvHeader() {
        return "";
    }

    public String getCsvEntry() {
        return "";
    }

    @Override
    public int compareTo(SmallOre other) {
        return oreName.compareTo(other.oreName);
    }
}
