package database;

import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class CitiInformation {
private int id;
private String mcity;
private String mcountry;
private String mdataDate;
private String mdataTime;
private String mclouds;
private String mdegree;
private String mhuminity;
    private List<String> massiv = new ArrayList<String>();

    public CitiInformation(int id,String mcity, String mcountry, String mdataDate, String mdataTime, String mclouds, String mdegree, String mhuminity) {
        this.id = id;
        this.mcity = mcity;
        this.mcountry = mcountry;
        this.mdataDate = mdataDate;
        this.mdataTime = mdataTime;
        this.mclouds = mclouds;
        this.mdegree = mdegree;
        this.mhuminity = mhuminity;
    }
    public int getId() {
        return id;
    }

    public String getMcity() {
        return mcity;
    }

    public String getMcountry() {
        return mcountry;
    }

    public String getMdataDate() {
        return mdataDate;
    }

    public String getMdataTime() {
        return mdataTime;
    }

    public String getMclouds() {
        return mclouds;
    }

    public String getMdegree() {
        return mdegree;
    }

    public String getMhuminity() {
        return mhuminity;
    }
     public String getDegree() {
        return getMcity().toString()+" : "+getMdegree().toString();
    }
     public List<String> getMassiv() {
         massiv.add(getMcity().toString());
         massiv.add(getMcountry().toString());
         massiv.add(getMdataDate().toString());
         massiv.add(getMdataTime().toString());
         massiv.add(getMclouds().toString());
         massiv.add(getMdegree().toString());
         massiv.add(getMhuminity().toString());
        return massiv;
    }
}
