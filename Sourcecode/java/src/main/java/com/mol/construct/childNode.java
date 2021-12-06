package com.mol.construct;

/**
 * childNode: Node definition
 * @author Sudha Eswaran
 *
 */

public class childNode {
	
	String pType;
	String cType;
	String bondType;
	static int id = 0;
	int objId;

	public childNode() {
		super();
		this.objId = ++id;
	}

	public String getpType() {
		return pType;
	}

	public void setpType(String pType) {
		this.pType = pType;
	}

	public String getcType() {
		return cType;
	}

	public void setcType(String cType) {
		this.cType = cType;
	}

	public String getBondType() {
		return bondType;
	}

	public void setBondType(String bondType) {
		this.bondType = bondType;
	}

	@Override
	public String toString() {
		return "[" + pType + "-" + bondType + "-" + cType + "]";
		//return "['"+pType+"','"+ cType+"']";
		//return "childNode [pType=" + pType + ", cType=" + cType + ", bondType=" + bondType + "]";
	}

	public String getData() {
		return "[" + pType + "-" + bondType + "-" + cType + "]";
	}

}