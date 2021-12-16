package com.mol.mono;

import com.mol.util.Constants;
/**
 * MonoUnit: Structural Definintion
 * @author Sudha Eswaran
 *
 */

public class MonoUnit {

	int id = 0;
	String type;
	boolean betaC = false;
	boolean fourthC = false;
	boolean fivthC = false;
	boolean alphaC = false;

	MonoUnit child1;
	MonoUnit child2;
	MonoUnit child3;
	String G = "G";
	String S = "S";
	String H = "H";
	String bnd1;
	String bnd2;
	String bnd3;

	String pType;
	String pBnd;
	int pID;

	public int getpID() {
		return pID;
	}

	public void setpID(int pID) {
		this.pID = pID;
	}

	public String getpType() {
		return pType;
	}

	public void setpType(String pType) {
		this.pType = pType;
	}

	public String getpBnd() {

		return pBnd;
	}

	public void setpBnd(String pBnd) {
		// setActivesites(pBnd);
		this.pBnd = pBnd;
	}

	public MonoUnit(String type, int id) {
		super();
		this.type = type;
		this.id = id;
		if (type.equals(G) || type.equals(H)) {
			betaC = true;
			fourthC = true;
			fivthC = true;
			alphaC = true;
		} else if (type.equals(S)) {
			betaC = true;
			fourthC = true;
			alphaC = true;
		}

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isBetaC() {
		return betaC;
	}

	public void setBetaC(boolean betaC) {
		this.betaC = betaC;
	}

	public boolean isFourthC() {
		return fourthC;
	}

	public void setFourthC(boolean fouthC) {
		this.fourthC = fouthC;
	}

	public boolean isFivthC() {
		return fivthC;
	}

	public void setFivthC(boolean fivthC) {
		this.fivthC = fivthC;
	}

	public boolean isAlphaC() {
		return alphaC;
	}

	public void setAlphaC(boolean alphaC) {
		this.alphaC = alphaC;
	}

	public MonoUnit getChild1() {
		return child1;
	}

	public void setChild1(MonoUnit child1) {
		this.child1 = child1;
	}

	public MonoUnit getChild2() {
		return child2;
	}

	public void setChild2(MonoUnit child2) {
		this.child2 = child2;
	}

	public MonoUnit getChild3() {
		return child3;
	}

	public void setChild3(MonoUnit child3) {
		this.child3 = child3;
	}

	public String getBnd1() {
		return bnd1;
	}

	public void setBnd1(String bnd1) {
		// setActivesites(bnd1);
		this.bnd1 = bnd1;
	}

	public String getBnd2() {
		return bnd2;
	}

	public void setBnd2(String bnd2) {
		// setActivesites(bnd2);
		this.bnd2 = bnd2;
	}

	public String getBnd3() {
		return bnd3;
	}

	public void setBnd3(String bnd3) {
		this.bnd3 = bnd3;
	}

	@Override
	public String toString() {
		String ss = "id=" + id + ",type=" + type + ",bond1=" + bnd1 + ",bond2=" + bnd2 + ",bond3=" + bnd3;
		ss = ss.concat(",child1 [Type="
				+ (child1 != null ? child1.getType() + ", ID=" + child1.getId() + ",ParentID=" + child1.getpID() : "")
				+ "]");
		ss = ss.concat(",child2 [Type="
				+ (child2 != null ? child2.getType() + ", ID=" + child2.getId() + ",ParentID=" + child2.getpID() : "")
				+ "]");
		ss = ss.concat(",child3 [Type="
				+ (child3 != null ? child3.getType() + ", ID=" + child3.getId() + ",ParentID=" + child3.getpID() : "")
				+ "]");
		ss = ss.concat(", [pID=" + pID + ",BondWithP=" + pBnd + ",pType=" + pType + "]");
		return ss;
	}

	void setActivesites(String bnd) {
		if (bnd.equals(Constants.O4) || bnd.equals(Constants._4O))
			this.fourthC = true;
		else if (bnd.equals(Constants.B))
			this.betaC = true;
		else if (bnd.equals(Constants._5) && !type.equals(S))
			this.fivthC = true;
		else if (bnd.equals(Constants.A))
			this.alphaC = true;
	}

	public String availableBonds() {
		String s = "Available Bonds: ID= " + id + ", BC=" + isBetaC() + ",5C=" + isFivthC() + ",4C=" + isFourthC();
		return s;
	}

	public static void main(String s[]) {
		MonoUnit mn = new MonoUnit("G", 1);

	}

}
