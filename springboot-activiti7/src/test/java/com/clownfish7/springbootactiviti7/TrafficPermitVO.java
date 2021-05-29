package com.clownfish7.springbootactiviti7;

/**
 * @author yzy
 * @classname TrafficPermitVO
 * @description TODO
 * @create 2020-04-21 1:53 PM
 */

public class TrafficPermitVO {

    private int needJd;
    private int needA;
    private int needB;
    private int needC;

    private int jd;
    private int policeA;
    private int policeB;
    private int policeC;

    public TrafficPermitVO() {

    }

    public int getNeedJd() {
        return needJd;
    }

    public void setNeedJd(int needJd) {
        this.needJd = needJd;
    }

    public int getNeedA() {
        return needA;
    }

    public void setNeedA(int needA) {
        this.needA = needA;
    }

    public int getNeedB() {
        return needB;
    }

    public void setNeedB(int needB) {
        this.needB = needB;
    }

    public int getNeedC() {
        return needC;
    }

    public void setNeedC(int needC) {
        this.needC = needC;
    }

    public int getJd() {
        return jd;
    }

    public void setJd(int jd) {
        this.jd = jd;
    }

    public int getPoliceA() {
        return policeA;
    }

    public void setPoliceA(int policeA) {
        this.policeA = policeA;
    }

    public int getPoliceB() {
        return policeB;
    }

    public void setPoliceB(int policeB) {
        this.policeB = policeB;
    }

    public int getPoliceC() {
        return policeC;
    }

    public void setPoliceC(int policeC) {
        this.policeC = policeC;
    }

    @Override
    public String toString() {
        return "TrafficPermitVO{" +
                "needJd=" + needJd +
                ", needA=" + needA +
                ", needB=" + needB +
                ", needC=" + needC +
                ", jd=" + jd +
                ", policeA=" + policeA +
                ", policeB=" + policeB +
                ", policeC=" + policeC +
                '}';
    }
}
