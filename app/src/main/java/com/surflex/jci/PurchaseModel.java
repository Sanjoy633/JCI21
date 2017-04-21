package com.surflex.jci;

/**
 * Created by USER on 17-04-2017.
 */

public class PurchaseModel {
    int id;
    String  formNo;
    String basis;
    String binNo;
    String juteVariety;
    String grossQuantity;
    String deductionQuantity;
    String netQuantity;
    String avgRate;
    String estGradeComp;
    String allGrade;
    String entryDate;
    String imgPath;

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public PurchaseModel(String formNo, String basis, String binNo,
                         String juteVariety, String grossQuantity, String deductionQuantity,
                         String netQuantity, String avgRate, String estGradeComp, String allGrade,String imgPath,String entryDate) {

        this.formNo = formNo;
        this.basis = basis;
        this.binNo = binNo;
        this.juteVariety = juteVariety;
        this.grossQuantity = grossQuantity;
        this.deductionQuantity = deductionQuantity;
        this.netQuantity = netQuantity;
        this.avgRate = avgRate;
        this.estGradeComp = estGradeComp;

        this.allGrade = allGrade;
        this.entryDate = entryDate;
        this.imgPath = imgPath;
    }
    public PurchaseModel(int id, String formNo, String basis, String binNo,
                         String juteVariety, String grossQuantity, String deductionQuantity,
                         String netQuantity, String avgRate, String estGradeComp, String allGrade,String imgPath,String entryDate) {
        this.id = id;
        this.formNo = formNo;
        this.basis = basis;
        this.binNo = binNo;
        this.juteVariety = juteVariety;
        this.grossQuantity = grossQuantity;
        this.deductionQuantity = deductionQuantity;
        this.netQuantity = netQuantity;
        this.avgRate = avgRate;
        this.estGradeComp = estGradeComp;
        this.allGrade = allGrade;
        this.entryDate = entryDate;
        this.entryDate = entryDate;
        this.imgPath = imgPath;
    }

    public PurchaseModel() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFormNo() {
        return formNo;
    }

    public void setFormNo(String formNo) {
        this.formNo = formNo;
    }

    public String getBasis() {
        return basis;
    }

    public void setBasis(String basis) {
        this.basis = basis;
    }

    public String getBinNo() {
        return binNo;
    }

    public void setBinNo(String binNo) {
        this.binNo = binNo;
    }

    public String getJuteVariety() {
        return juteVariety;
    }

    public void setJuteVariety(String juteVariety) {
        this.juteVariety = juteVariety;
    }

    public String getGrossQuantity() {
        return grossQuantity;
    }

    public void setGrossQuantity(String grossQuantity) {
        this.grossQuantity = grossQuantity;
    }

    public String getDeductionQuantity() {
        return deductionQuantity;
    }

    public void setDeductionQuantity(String deductionQuantity) {
        this.deductionQuantity = deductionQuantity;
    }

    public String getNetQuantity() {
        return netQuantity;
    }

    public void setNetQuantity(String netQuantity) {
        this.netQuantity = netQuantity;
    }

    public String getAvgRate() {
        return avgRate;
    }

    public void setAvgRate(String avgRate) {
        this.avgRate = avgRate;
    }

    public String getEstGradeComp() {
        return estGradeComp;
    }

    public void setEstGradeComp(String estGradeComp) {
        this.estGradeComp = estGradeComp;
    }
    public String getAllGrade() {
        return allGrade;
    }

    public void setAllGrade(String allGrade) {
        this.allGrade = allGrade;
    }



}
