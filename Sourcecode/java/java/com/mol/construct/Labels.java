package com.mol.construct;

import java.util.Hashtable;
/**
 * Labels
 * @author Sudha Eswaran
 *
 */
public class Labels {

	int index;
	double molWgt;
	Hashtable<String, Double> molW = new Hashtable<String, Double>() {
		private static final long serialVersionUID = 1L;
		{
			put("S", 184.191);
			put("G", 181.36);
			put("H", 138.164);
		}
	};
	Hashtable<String, Double> formula = new Hashtable<String, Double>() {
		private static final long serialVersionUID = 1L;
		{
			put("S", 184.191);
			put("G", 181.36);
			put("H", 138.164);
		}
	};

	public Labels(int ind, String type) {
		super();
		this.index = ind + 10;
		setMolWgt(molW.get(type));
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public double getMolWgt() {
		return molWgt;
	}

	public void setMolWgt(double molWgt) {
		this.molWgt = molWgt;
	}

	@Override
	public String toString() {
		return "Labels [index=" + index + ", molWgt=" + molWgt + "]";
	}

}
