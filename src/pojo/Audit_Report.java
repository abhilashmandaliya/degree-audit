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
@Entity
@Table(name = "Audit_report")

public class Audit_Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    
    private int date_generated;
    private double percentage_of_degree_finish;
    private double obtained_credit; 
    private double require__credit;
    private double present_CPI;
    private double require_CPI;
    private double present_cource;
    private double require_courcce;
    private double time_left_finish_degree;
    
    //private int CPI;
    
    //private int no_of_cource;

    public Audit_Report() {
    }
    

	public Audit_Report(int date_generated, double percentage_of_degree_finish, double obtained_credit,
			double require__credit, double present_CPI, double require_CPI, double present_cource,
			double require_courcce, double time_left_finish_degree) {
		super();
		this.date_generated = date_generated;
		this.percentage_of_degree_finish = percentage_of_degree_finish;
		this.obtained_credit = obtained_credit;
		this.require__credit = require__credit;
		this.present_CPI = present_CPI;
		this.require_CPI = require_CPI;
		this.present_cource = present_cource;
		this.require_courcce = require_courcce;
		this.time_left_finish_degree = time_left_finish_degree;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDate_generated() {
		return date_generated;
	}

	public void setDate_generated(int date_generated) {
		this.date_generated = date_generated;
	}

	public double getPercentage_of_degree_finish() {
		return percentage_of_degree_finish;
	}

	public void setPercentage_of_degree_finish(double percentage_of_degree_finish) {
		this.percentage_of_degree_finish = percentage_of_degree_finish;
	}

	public double getObtained_credit() {
		return obtained_credit;
	}

	public void setObtained_credit(double obtained_credit) {
		this.obtained_credit = obtained_credit;
	}

	public double getRequire__credit() {
		return require__credit;
	}

	public void setRequire__credit(double require__credit) {
		this.require__credit = require__credit;
	}

	public double getPresent_CPI() {
		return present_CPI;
	}

	public void setPresent_CPI(double present_CPI) {
		this.present_CPI = present_CPI;
	}

	public double getRequire_CPI() {
		return require_CPI;
	}

	public void setRequire_CPI(double require_CPI) {
		this.require_CPI = require_CPI;
	}

	public double getPresent_cource() {
		return present_cource;
	}

	public void setPresent_cource(double present_cource) {
		this.present_cource = present_cource;
	}

	public double getRequire_courcce() {
		return require_courcce;
	}

	public void setRequire_courcce(double require_courcce) {
		this.require_courcce = require_courcce;
	}

	public double getTime_left_finish_degree() {
		return time_left_finish_degree;
	}

	public void setTime_left_finish_degree(double time_left_finish_degree) {
		this.time_left_finish_degree = time_left_finish_degree;
	}



    
}