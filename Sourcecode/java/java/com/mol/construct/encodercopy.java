package com.mol.construct;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * encodercopy
 * @author Sudha Eswaran
 *
 */

public class encodercopy {

	/**
	 * Function to map the child node to parent node. Mapping is set in the
	 * Monolignol object
	 * 
	 * @param ed
	 * @param l1
	 * @param l2
	 * @return
	 */
	public Object encode(Edges ed, monolignol l1, monolignol l2) {
		if (checkID(l1.getchild1(), l2.objId) && checkID(l1.getchild2(), l2.objId)
				&& checkID(l1.getchild3(), l2.objId)) {
			Random rn = new Random();
			sop("Edges" + ed.getEdWgt());
			int random = 0;
			boolean isUpdated = false;
			switch (random + 1) {
			case 3:
				if (l1.getchild1() == null) {
					l1.setchild1(l2);
					isUpdated = true;
				} else if (l1.getchild2() == null) {
					l1.setchild2(l2);
					isUpdated = true;
				} else if (l1.getchild3() == null) {
					l1.setchild3(l2);
					isUpdated = true;
				}
				break;
			case 2:
				if (l1.getchild1() == null) {
					l1.setchild1(l2);
					isUpdated = true;
				} else if (l1.getchild2() == null) {
					l1.setchild2(l2);
					isUpdated = true;
				}
				break;
			case 1:
				if (l1.getchild1() == null) {
					l1.setchild1(l2);
					isUpdated = true;
				}
				break;
			}
			if (!isFilled(l1.objId, ed) && isUpdated) {
				l1 = getEdgeType(ed, l1);
				if (l1.isEdgUpdated) {
					ed.getpNode().add(l1.objId);
					ed.getcNode().add(l2.objId);
					ed.setEdWgt(l1.getEdWgt());
					ed.setMonoL(l1);
				}
			}
		}
		sop(ed.getEdWgt());
		return ed;
	}

	static boolean checkID(monolignol obj, int id2) {
		if (obj == null || obj.objId != id2) {
			return true;
		}
		return false;
	}

	static boolean isFilled(int id, Edges ed) {
		boolean isfill = false;
		int cnt = 0;
		for (int pVer : ed.getpNode()) {
			if (id == pVer) {
				cnt++;
			}
		}
		if (cnt >= 3)
			isfill = true;
		return isfill;
	}

	static void sop(Object s) {
		System.out.println(s);
	}

	/**
	 * GetList : Creates possible combination of Monomer and Bond type
	 * 
	 * @param str
	 * @param bondper
	 * @param dp
	 * @param total
	 * @return
	 */
	String[] getList(String[] str, int[] bondper, int dp, int total) {

		String[] strBonds = new String[dp];
		int[] totcnt = new int[str.length];
		// initializing the totcount[] for each bond with 1
		for (int inc = 0; inc < totcnt.length; inc++) {
			totcnt[inc] += 1;
		}
		// Loop to create the possible bond names (or) monomer names with the dp count
		int cnt = 0;
		boolean brkLoop = false;
		while (!brkLoop) {
			for (int j = 0; j < str.length; j++) {

				double cal = calPer(bondper[j], dp, total);
				if (cal > 0 && totcnt[j] <= cal && cnt < dp) {
					strBonds[cnt++] = str[j];
					totcnt[j] = totcnt[j] + 1;
				}
			}
			brkLoop = true;
			for (int j = 0; j < str.length; j++) {
				double cal = calPer(bondper[j], dp, total);
				if (cal > 0 && totcnt[j] <= cal && cnt < dp) {
					brkLoop = false;
				}
			}
		}
		// for (String s: strBonds) sop("getlist ->"+s);
		// Loop to add bonds at the end for chain length
		while (cnt < dp) {
			for (int k = 0; k < str.length; k++) {
				if (cnt >= dp) {
					break;
				}
				if (bondper[k] > 0)
					strBonds[cnt++] = str[k];
			}
		}
		return strBonds;
	}

	/**
	 * calPer : calculates numbers from given percentages
	 * 
	 * @param val
	 * @param dp
	 * @param total
	 * @return
	 */
	double calPer(int val, int dp, int total) {
		if (val == 0)
			return val;
		double flrval = dp * (val / (double) total);
		return ((flrval > 0.5) ? Math.round(flrval) : 0);
	}

	monolignol getEdgeType(Edges ed, monolignol m1) {
		m1.isEdgUpdated = false;
		ArrayList<String> edgArr = ed.getEdWgt();
		String c[] = new String[3];
		List<String> newEdg = Arrays.asList(m1.getEdgeType());
		int cnt = 0;
		for (String s : newEdg) {
			if (s != null)
				c[cnt++] = "" + s.charAt(0);
		}
		for (int k = 0; k < m1.getEdgeType().length; k++) {
			for (int j = 0; j < edgArr.size(); j++) {
				String oldegd = edgArr.get(j);
				String plnk = "" + oldegd.charAt(0);
				String clnk = "" + oldegd.charAt(oldegd.length() - 1);
				String ctype = m1.getchild1().type;
				if (k == 1)
					ctype = (m1.getchild2() != null ? m1.getchild2().type : "");
				else if (k == 2)
					ctype = (m1.getchild3() != null ? m1.getchild3().type : "");

				if (!((m1.edgeType.equals("S") && plnk.equals("5")) || (ctype.equals("S") && clnk.equals("5")))) {
					if (!Arrays.asList(m1.getEdgeType()).contains(edgArr.get(j)) && !Arrays.asList(c).contains(plnk)) {

						if (m1.getEdgeType()[k] == null || m1.getEdgeType()[k].isEmpty()) {

							m1.getEdgeType()[k] = edgArr.get(j);
							edgArr.remove(edgArr.get(j));
							m1.setEdWgt(edgArr);
							m1.isEdgUpdated = true;
							// sop(m1);
							return m1;
						}
					}
				}
			}
		}
		return m1;
	}

	/**
	 * RemoveRepetitionfromLigninList: Removes duplicate entries in the LigninList
	 * 
	 * @param ligninlist
	 * @return
	 */
	static List<Lignin> RemoveRepetitionfromLigninList(List<Lignin> ligninlist) {
		ArrayList<Lignin> modifiedList = new ArrayList<Lignin>();

		for (Lignin lignin : ligninlist) {
			Lignin newLignin = new Lignin();
			newLignin.setPType(lignin.getPType());
			List<childNode> newChildList = new ArrayList<childNode>();
			List<String> cNode = new ArrayList<String>();
			for (childNode cN : lignin.getcNode()) {
				String cNodd_bond = cN.getcType() + "-" + cN.bondType.substring(1, cN.bondType.length());

				boolean possibleBnd = true;
				String ctype = "" + cN.getcType().charAt(cN.getcType().length() - 1);
				String ptype = "" + cN.getpType().charAt(cN.getpType().length() - 1);
				String clink = "" + cN.bondType.charAt(cN.bondType.length() - 1);
				String plink = "" + cN.bondType.charAt(0);

				if ((ctype.equals("S") && clink.equals("5")) || (ptype.equals("S") && plink.equals("5"))) {
					possibleBnd = false;
				}
				if (!cNode.contains(cNodd_bond) && possibleBnd) {
					cNode.add(cNodd_bond);
					newChildList.add(cN);
				}
			}
			lignin.setcNode(newChildList);
			modifiedList.add(lignin);
		}
		return modifiedList;
	}

	/**
	 * removeDuplicates
	 * 
	 * @param strData
	 * @return
	 */
	static List<String[]> removeDuplicates(List<String[]> strData) {
		List<String[]> strList = new ArrayList<String[]>();
		for (int i = 0; i < strData.size(); i++) {
			if (strList.isEmpty() || !isDuplicate(strList, strData.get(i))) {
				strList.add(strData.get(i));
			}
		}
		return strList;
	}

	static boolean isDuplicate(List<String[]> strList, String[] strArr) {
		boolean isDup = false;
		for (int i = 0; i < strList.size(); i++) {
			String[] lclStrArr = strList.get(i);

			if (Arrays.toString(lclStrArr).equals(Arrays.toString(strArr)))
				isDup = true;
		}
		return isDup;
	}

	/**
	 * possibleBonds: Returns the possible bonds with monomer sequence formed
	 * 
	 * @param bondListStr
	 * @return
	 */
	public List<String[]> possibleBonds(List<String[]> bondListStr) {
		List<String[]> retArrr = new ArrayList<String[]>();
		for (String[] bnds : bondListStr) {
			char prevFirst = 0;
			char prevLast = 0;
			boolean ignore = false;
			// sop(Arrays.toString(bnds));
			List<String> bondArr = new ArrayList<String>();
			for (String bd : bnds) {
				char first = bd.charAt(0);
				char last = bd.charAt(bd.length() - 1);
				char middle = (bd.length() > 2 ? bd.charAt(1) : 0);
				if (prevLast == first) {
					if (first != last) {
						char temp = first;
						first = last;
						last = temp;
						String bond = String.valueOf(first) + (middle != 0 ? String.valueOf(middle) : "")
								+ String.valueOf(last);
						bondArr.add(bond);
					} else {
						bondArr.add(" ");
						bondArr.add(bd);
					}
				} else {
					bondArr.add(bd);
				}
				prevFirst = first;
				prevLast = last;
			}
			// sop(bondArr.toString());
			if (bondArr.size() > 0) {
				retArrr.add(bondArr.toArray(new String[0]));
			}
			// sop("Next>>");

		}
		return retArrr;
	}
}
