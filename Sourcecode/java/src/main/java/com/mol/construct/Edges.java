package com.mol.construct;

import java.util.ArrayList;
/**
 * Edges
 * @author Sudha Eswaran
 *
 */

public class Edges {
	ArrayList<Integer> pNode = new ArrayList<Integer>();
	ArrayList<Integer> cNode = new ArrayList<Integer>();
	ArrayList<String> edWgt = new ArrayList<String>(); // list of possible bonds [ array of bonds]
	ArrayList<Integer> edgWgtIn = new ArrayList<Integer>();
	Object monoL = new Object();
	Object monoC = new Object();

	public Object getMonoC() {
		return monoC;
	}

	public void setMonoC(Object monoC) {
		this.monoC = monoC;
	}

	public Object getMonoL() {
		return monoL;
	}

	public void setMonoL(Object monoL) {
		this.monoL = monoL;
	}

	public ArrayList<Integer> getpNode() {
		return pNode;
	}

	public void setpNode(ArrayList<Integer> pNode) {
		this.pNode = pNode;
	}

	public ArrayList<Integer> getcNode() {
		return cNode;
	}

	public void setcNode(ArrayList<Integer> cNode) {
		this.cNode = cNode;
	}

	public ArrayList<String> getEdWgt() {
		return edWgt;
	}

	public void setEdWgt(ArrayList<String> edWgt) {
		this.edWgt = edWgt;
	}

	public ArrayList<Integer> getEdgWgtIn() {
		return edgWgtIn;
	}

	public void setEdgWgtIn(ArrayList<Integer> edgWgtIn) {
		this.edgWgtIn = edgWgtIn;
	}

	@Override
	public String toString() {
		return "Edges [pNode=" + pNode + ", cNode=" + cNode + ", edgWgtIn=" + edgWgtIn + ", edgWgt=" + edWgt + "]";
	}

}
