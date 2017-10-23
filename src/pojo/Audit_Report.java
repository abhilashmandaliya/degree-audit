/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Ankit
 */
public class Audit_Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    
    private int data_generated;
    
    private double credit;
    
    private double total_credit;
    
    private int CPI;
    
    private int no_of_cource;

    public Audit_Report() {
    }

    public Audit_Report(int data_generated, double credit, double total_credit, int CPI, int no_of_cource) {
        //this.id = id;
        this.data_generated = data_generated;
        this.credit = credit;
        this.total_credit = total_credit;
        this.CPI = CPI;
        this.no_of_cource = no_of_cource;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getData_generated() {
        return data_generated;
    }

    public void setData_generated(int data_generated) {
        this.data_generated = data_generated;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public double getTotal_credit() {
        return total_credit;
    }

    public void setTotal_credit(double total_credit) {
        this.total_credit = total_credit;
    }

    public int getCPI() {
        return CPI;
    }

    public void setCPI(int CPI) {
        this.CPI = CPI;
    }

    public int getNo_of_cource() {
        return no_of_cource;
    }

    public void setNo_of_cource(int no_of_cource) {
        this.no_of_cource = no_of_cource;
    }

    
}
