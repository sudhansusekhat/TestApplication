package com.dbs.easyhomeloan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EMIModel {
    @SerializedName("Loan Amount")
    @Expose
    private Integer loanAmount;
    @SerializedName("Loan EMI")
    @Expose
    private Integer loanEMI;
    @SerializedName("Total Interest Payable")
    @Expose
    private Integer totalInterestPayable;
    @SerializedName("Total Payment")
    @Expose
    private Integer totalPayment;

    public Integer getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(Integer loanAmount) {
        this.loanAmount = loanAmount;
    }

    public Integer getLoanEMI() {
        return loanEMI;
    }

    public void setLoanEMI(Integer loanEMI) {
        this.loanEMI = loanEMI;
    }

    public Integer getTotalInterestPayable() {
        return totalInterestPayable;
    }

    public void setTotalInterestPayable(Integer totalInterestPayable) {
        this.totalInterestPayable = totalInterestPayable;
    }

    public Integer getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(Integer totalPayment) {
        this.totalPayment = totalPayment;
    }
}
