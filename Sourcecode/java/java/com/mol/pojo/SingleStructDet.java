package com.mol.pojo;

import java.util.Map;

/**
 * SingleStructDet: Lignin structure details
 * @author Sudha Eswaran
 *
 */

public class SingleStructDet {

	String smilestring;
	double molWeight;
	Map<String, Integer> bndCnts;
	boolean issingleChain;
	double branchingFactor;
	int numberOfOligomers;

	public double getBranchingFactor() {
		return branchingFactor;
	}

	public void setBranchingFactor(double branchingFactor) {
		this.branchingFactor = branchingFactor;
	}

	public boolean isIssingleChain() {
		return issingleChain;
	}

	public void setIssingleChain(boolean issingleChain) {
		this.issingleChain = issingleChain;
	}

	public String getSmilestring() {
		return smilestring;
	}

	public void setSmilestring(String smilestring) {
		this.smilestring = smilestring;
	}

	public double getMolWeight() {
		return molWeight;
	}

	public void setMolWeight(double molWeight) {
		this.molWeight = molWeight;
	}

	public Map<String, Integer> getBndCnts() {
		return bndCnts;
	}

	public void setBndCnts(Map<String, Integer> bndCnts) {
		this.bndCnts = bndCnts;
	}

	public int getNumberOfOligomers() {
		return numberOfOligomers;
	}

	public void setNumberOfOligomers(int numberOfOligomers) {
		this.numberOfOligomers = numberOfOligomers;
	}
	
	
}