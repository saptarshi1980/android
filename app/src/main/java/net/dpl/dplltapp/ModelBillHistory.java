package net.dpl.dplltapp;

/**
 * Created by dpl 1 on 9/30/2015.
 */
public class ModelBillHistory {


    private String conNo;
    private String partyCode;
    private String name;
    private String billMonth;
    private String lastRead;
    private String currRead;
    private String mf;
    private String meterStatus;
    private String billAmount;
    private String dueDate1;
    private String dueDate2;
    private String unit;

    public String getConNo() {
        return conNo;
    }
    public void setConNo(String conNo) {
        this.conNo = conNo;
    }
    public String getPartyCode() {
        return partyCode;
    }
    public void setPartyCode(String partyCode) {
        this.partyCode = partyCode;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getBillMonth() {
        return billMonth;
    }
    public void setBillMonth(String billMonth) {
        this.billMonth = billMonth;
    }
    public String getLastRead() {
        return lastRead;
    }
    public void setLastRead(String lastRead) {
        this.lastRead = lastRead;
    }
    public String getCurrRead() {
        return currRead;
    }
    public void setCurrRead(String currRead) {
        this.currRead = currRead;
    }
    public String getMf() {
        return mf;
    }
    public void setMf(String mf) {
        this.mf = mf;
    }
    public String getMeterStatus() {
        return meterStatus;
    }
    public void setMeterStatus(String meterStatus) {
        this.meterStatus = meterStatus;
    }
    public String getBillAmount() {
        return billAmount;
    }
    public void setBillAmount(String billAmount) {
        this.billAmount = billAmount;
    }
    public String getDueDate1() {
        return dueDate1;
    }
    public void setDueDate1(String dueDate1) {
        this.dueDate1 = dueDate1;
    }
    public String getDueDate2() {
        return dueDate2;
    }
    public void setDueDate2(String dueDate2) {
        this.dueDate2 = dueDate2;
    }
    public String getUnit() {
        return unit;
    }
    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return 	"Bill Month: " + billMonth +
                "\nPrevious Reading: " + Math.round(Double.valueOf(lastRead)) +
                "\nPresent Reading: " + Math.round(Double.valueOf(currRead)) +
                "\nUnits Consumed: " + Math.round(Double.valueOf(unit)) +
                "\nBill Amount/Month: Rs " + Math.round(Double.valueOf(billAmount)) +"/-"+
                "\n1st Due Date: " + dueDate1 +
                "\n2nd Due Date: " + dueDate2           ;
    }

}

