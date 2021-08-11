package com.mol.construct;

import java.util.ArrayList;
import java.util.Arrays;
/**
 * monolignol
 * @author Sudha Eswaran
 *
 */
public class monolignol {
	static int NoOfBond = 3;
	static int id = 0;
	String type;
	String cbond1;
	String cbond2;
	String cbond3;
	monolignol child1;
	monolignol child2;
	monolignol child3;
	int objId;
	String[] edgeType = new String[5];
	ArrayList<String> edWgt = new ArrayList<String>();
	Labels lbl;
	boolean isEdgUpdated = false;
	String[] G_As = { "B", "5", "4" };
	String[] S_As = { "B", "4" };
	String[] H_As = { "B", "5", "4" };

	String[] activeSites;

	public monolignol(String type) {
		super();
		this.type = type;
		this.objId = ++id;
		lbl = new Labels(this.objId, this.type);
		if (type.equals("G"))
			this.activeSites = G_As;
		else if (type.equals("S"))
			this.activeSites = S_As;
		else
			this.activeSites = H_As;

	}

	monolignol(String type, int idV) {
		super();
		this.type = type;
		this.objId = idV;
		lbl = new Labels(this.objId, this.type);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public monolignol getchild1() {
		return child1;
	}

	public void setchild1(monolignol child1) {
		if (child1 != null)
			this.child1 = child1;
	}

	public monolignol getchild2() {

		return child2;
	}

	public void setchild2(monolignol child2) {
		if (child2 != null)
			this.child2 = child2;
	}

	public monolignol getchild3() {
		return child3;
	}

	public void setchild3(monolignol child3) {
		if (child3 != null)
			this.child3 = child3;
	}

	public ArrayList<String> getEdWgt() {
		return edWgt;
	}

	public void setEdWgt(ArrayList<String> edWgt) {
		this.edWgt = edWgt;
	}

	public String[] getEdgeType() {
		return edgeType;
	}

	public void setEdgeType(String[] edgeType) {
		this.edgeType = edgeType;
	}

	public String getCbond1() {
		return cbond1;
	}

	public void setCbond1(String cbond1) {
		this.cbond1 = cbond1;
	}

	public String getCbond2() {
		return cbond2;
	}

	public void setCbond2(String cbond2) {
		this.cbond2 = cbond2;
	}

	public String getCbond3() {
		return cbond3;
	}

	public void setCbond3(String cbond3) {
		this.cbond3 = cbond3;
	}

	@Override
	public String toString() {
		return "monolignol [type=" + type + ", child1=" + ((child1 == null) ? "" : child1.objId + child1.getType())
				+ ", child2=" + ((child2 == null) ? "" : child2.objId + child2.getType()) + ", child3="
				+ ((child3 == null) ? "" : child3.objId + child3.getType()) + ", objId=" + objId + ",bond1=" + cbond1
				+ ", edgeType=" + Arrays.toString(edgeType) + ", lbl=" + lbl + "]\n";
	}

}
