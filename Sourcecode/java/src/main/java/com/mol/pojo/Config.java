package com.mol.pojo;

import java.util.Map;

/**
 * Config: property for project-config.yaml
 * @author Sudha Eswaran
 **/

public class Config {

	private boolean sdf;
	private boolean matrices;
	private boolean png;
	private boolean json;
	private float s_g_ratio;
	private int dp;
	private Map<String, Integer> bondconfig;
	private Map<String, Integer> monoconfig;
	
	private int min_dp;
	private int max_dp;
	private boolean addtoDB;
	private String mongo_URI;
	private String mongo_dbname;
	private String mongo_collname;
	
		
	

	public boolean isSdf() {
		return sdf;
	}

	public void setSdf(boolean sdf) {
		this.sdf = sdf;
	}

	public boolean isMatrices() {
		return matrices;
	}

	public void setMatrices(boolean matrices) {
		this.matrices = matrices;
	}

	public boolean isPng() {
		return png;
	}

	public void setPng(boolean png) {
		this.png = png;
	}

	public boolean isJson() {
		return json;
	}

	public void setJson(boolean json) {
		this.json = json;
	}

	public float getS_g_ratio() {
		return s_g_ratio;
	}

	public void setS_g_ratio(float s_g_ratio) {
		this.s_g_ratio = s_g_ratio;
	}

	public int getDp() {
		return dp;
	}

	public void setDp(int dp) {
		this.dp = dp;
	}

	public Map<String, Integer> getBondconfig() {
		return bondconfig;
	}

	public void setBondconfig(Map<String, Integer> bondconfig) {
		this.bondconfig = bondconfig;
	}

	public Map<String, Integer> getMonoconfig() {
		return monoconfig;
	}

	public void setMonoconfig(Map<String, Integer> monoconfig) {
		this.monoconfig = monoconfig;
	}

	
	public int getMin_dp() {
		return min_dp;
	}

	public void setMin_dp(int min_dp) {
		this.min_dp = min_dp;
	}

	public int getMax_dp() {
		return max_dp;
	}

	public void setMax_dp(int max_dp) {
		this.max_dp = max_dp;
	}

	public boolean isAddtoDB() {
		return addtoDB;
	}

	public void setAddtoDB(boolean addtoDB) {
		this.addtoDB = addtoDB;
	}

	public String getMongo_URI() {
		return mongo_URI;
	}

	public void setMongo_URI(String mongo_URI) {
		this.mongo_URI = mongo_URI;
	}

	public String getMongo_dbname() {
		return mongo_dbname;
	}

	public void setMongo_dbname(String mongo_dbname) {
		this.mongo_dbname = mongo_dbname;
	}

	public String getMongo_collname() {
		return mongo_collname;
	}

	public void setMongo_collname(String mongo_collname) {
		this.mongo_collname = mongo_collname;
	}
	
	

}
