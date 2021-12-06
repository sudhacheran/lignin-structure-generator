package com.mol.construct;

import java.util.List;
import java.util.Map;

import com.mol.util.AdjacencyAndConnectivityMatrix;


/**
 * Lignin
 * @author Sudha Eswaran
 *
 */

public class Lignin {

	String PType;
	List<childNode> cNode;
	Map<String, Integer> bndCnts;
	AdjacencyAndConnectivityMatrix adjAndConnMtx;
	double molWeight;
	boolean isBranched = false;
	int noOfChains = 0;
	int noOfNodes = 0;
	double branchingFactor;

	public double getBranchingFactor() {
		return branchingFactor;
	}

	public void setBranchingFactor(double branchingFactor) {
		this.branchingFactor = branchingFactor;
	}

	public int getNoOfNodes() {
		return noOfNodes;
	}

	public void setNoOfNodes(int noOfNodes) {
		this.noOfNodes = noOfNodes;
	}

	public int getNoOfChains() {
		return noOfChains;
	}

	public void setNoOfChains(int noOfChains) {
		this.noOfChains = noOfChains;
	}

	public boolean isBranched() {
		return isBranched;
	}

	public void setBranched(boolean isBranched) {
		this.isBranched = isBranched;
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

	public String getPType() {
		return PType;
	}

	public void setPType(String pType) {
		PType = pType;
	}

	public List<childNode> getcNode() {
		return cNode;
	}

	public void setcNode(List<childNode> cNode) {
		this.cNode = cNode;
	}

	public AdjacencyAndConnectivityMatrix getAdjAndConnMtx() {
		return adjAndConnMtx;
	}

	public void setAdjAndConnMtx(AdjacencyAndConnectivityMatrix adjAndConnMtx) {
		this.adjAndConnMtx = adjAndConnMtx;
	}

	@Override
	public String toString() {
		return "Lignin [PType=" + PType + ", cNode=" + cNode + "] \n Bond counts: " + bndCnts;
	}
}
