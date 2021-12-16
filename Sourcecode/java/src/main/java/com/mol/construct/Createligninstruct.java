package com.mol.construct;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;

import com.mol.mono.MonoUnit;
import com.mol.pojo.Config;
import com.mol.util.AdjacencyAndConnectivityMatrix;
import com.mol.util.ConnectedGraphs;
import com.mol.util.Constants;
import com.mol.util.yamlread;

/**
 * Createligninstruct
 * @author Sudha Eswaran
 *
 */


public class Createligninstruct {

	// Inputs
	Logger logger = Logger.getLogger(Createligninstruct.class);

	int totalbond = 0;
	int totalmono = 0;

	public static void main(String s[]) {
		Date dt = new Date();
		Config prjConfig = yamlread.initializeConstants();
		Createligninstruct cls = new Createligninstruct();
		Constants.dp_units = 18;		
		
		System.out.println("Lignin chain lenght="+cls.getLiginList(Constants.dp_units, true).size());
		Date dt2 = new Date();
		System.out.println("Time taken=" + cls.getDifference(dt, dt2));
	}

	public String getMtype() {
		if (Constants.monoPer[1] > 0 && Constants.monoPer[0] > 0 && Constants.monoPer[2] > 0)
			return "sgh";
		else if (Constants.monoPer[1] == 0 && Constants.monoPer[0] > 0 && Constants.monoPer[2] > 0)
			return "gh";
		else if (Constants.monoPer[1] == 0 && Constants.monoPer[0] > 0 && Constants.monoPer[2] == 0)
			return "g";
		else if (Constants.monoPer[1] > 0 && Constants.monoPer[0] > 0 && Constants.monoPer[2] == 0)
			return "sg";
		return "";

	}

	public String getMonoPer() {
		String monoStr = Constants.monoPer[0] + " / " + Constants.monoPer[1] + " / " + Constants.monoPer[2];
		return monoStr;
	}

	/**
	 * getLiginList: Generates the list of possible lignin chains
	 * 
	 * @param dp
	 * @param branching
	 * @return
	 */
	public List<Lignin> getLiginList(int dp, boolean branching) {
		logger.info("Generates lignin chains");
		encodercopy ec = new encodercopy();

		for (int i : Constants.monoPer)
			totalmono += i;
		String[] monoArr = ec.getList(Constants.mono, Constants.monoPer, dp, totalmono);
		GeneratePolymer gp = new GeneratePolymer();
		long noOfPer = gp.nPr(dp, 1);

		
		List<String[]> monoListStr = new ArrayList<String[]>();
		gp.permute(monoArr, 0, monoArr.length - 1, monoListStr, noOfPer);
		
		
		List<String> uniqueBonds = new ArrayList<String>();
		for (int i = 0; i < Constants.bondper.length; i++)
		{
			totalbond += Constants.bondper[i];
			if (!uniqueBonds.contains(Constants.bonds[i]))
				uniqueBonds.add(Constants.bonds[i]);
			
		}
		for (int i = 0; i < Constants.branchingbndsper.length; i++)
		{
			totalbond += Constants.branchingbndsper[i];
			if (!uniqueBonds.contains(Constants.branchingbnds[i]))
				uniqueBonds.add(Constants.branchingbnds[i]);
		}
		
		String[] bondArr = ec.getList(Constants.bonds, Constants.bondper, dp, totalbond);
		
		
		List<String[]> bondListStr = new ArrayList<String[]>();
		List<String[]> mArr = monoListStr;

		if (dp == 2) {
			bondListStr = generatepossiblebonds(Constants.bonds, Constants.bondper, dp, bondArr);
		} else {			
			
			long noOfPer2 = gp.nPr(dp,uniqueBonds.size());
			gp.permute(bondArr, 0, bondArr.length - 1, bondListStr, noOfPer2);
			
			bondListStr = encodercopy.removeDuplicates(ec.possibleBonds(bondListStr));
			mArr = encodercopy.removeDuplicates(monoListStr);
		}
				
		Collections.shuffle(bondListStr, new Random());
		Collections.shuffle(mArr, new Random());
		ArrayList<ArrayList<Integer>> monoseq = CombinationAlg.getCombination(dp, 0);
		List<Lignin> ligninList = new ArrayList<Lignin>();

		for (String[] monoList : mArr) {
			for (String[] bondList : bondListStr) {
				int mlen = 0;
				Map<Integer, MonoUnit> monounitList = new HashMap<Integer, MonoUnit>();
				for (int i = 0; i < monoList.length; i++) {
					monounitList.put(i + 1, new MonoUnit(monoList[i], i + 1));
				}
				for (List<?> data : monoseq) {
					String pBnd = "" + bondList[mlen].charAt(0);
					String cBnd = "" + bondList[mlen].substring(1, bondList[mlen].length());

					if (pBnd.contentEquals("4")) {
						pBnd = "" + bondList[mlen].charAt(0) + bondList[mlen].charAt(1);
						cBnd = "" + bondList[mlen].charAt(bondList[mlen].length() - 1);
					}					

					int pID = (int) data.get(0);
					int cID = (int) data.get(1);
					MonoUnit pMono = monounitList.get(pID);
					MonoUnit cMono = monounitList.get(cID);
					 
					String pNode = pMono.getType();
					String cNode = cMono.getType();

					pMono = checkBonds(pMono, pBnd, "p", pNode, pID);
					cMono = checkBonds(cMono, cBnd, "c", pNode, pID); // setpBnd(cBnd); // bond from Child C - with P
																		// node

					if (pMono != null && cMono != null) {
						if (!pMono.isBetaC() && !cMono.isBetaC()) {
							pMono.setAlphaC(false);
							cMono.setAlphaC(false);
						}

						if (pMono.getChild1() == null)
							pMono.setChild1(cMono);
						else if (pMono.getChild2() == null)
							pMono.setChild2(cMono);
						else if (pMono.getChild3() == null)
							pMono.setChild3(cMono);
						monounitList.put((int) data.get(0), pMono);
						monounitList.put((int) data.get(1), cMono);
					}
					mlen++;
				}

				List<childNode> cNodeList = new ArrayList<childNode>();
				Lignin lignin = new Lignin();
				boolean ignore = false;
				for (int i : monounitList.keySet()) {
					MonoUnit mn = monounitList.get(i);
					if (mn.getChild1() != null) {
						MonoUnit cmn = monounitList.get(mn.getChild1().getId());
						childNode cN = new childNode();
						cN.setpType(cmn.getpID() + cmn.getpType());
						cN.setcType(cmn.getId() + cmn.getType());
						cN.setBondType(mn.getBnd1() + cmn.getpBnd());
						if ((cmn.getpType().equals("S") && (mn.getBnd1() == null || mn.getBnd1().equals("5")))
								|| (cmn.getType().equals("S")
										&& (cmn.getpBnd() == null || cmn.getpBnd().equals("5")))) {
							ignore = true;
						}
						if (!ignore)
						{
							cNodeList.add(cN);
						}
					}

				}
				if (!ignore) {
					if (cNodeList != null && cNodeList.size() > 0) {
						//logger.debug(cNodeList);
						AdjacencyAndConnectivityMatrix adjAndConnMx = new AdjacencyAndConnectivityMatrix(dp);
						adjAndConnMx.getAdjandConnMatrix(cNodeList);
						if (branching) // This now only has two oligomers linking
						{
							int prevsize = cNodeList.size();
							ConnectedGraphs cg = new ConnectedGraphs();
							int bondper_4O5 = (int) ec.calPer(Constants.branchingbndsper[0], dp, totalbond);
							int bondper_55 = (int) ec.calPer(Constants.branchingbndsper[1], dp, totalbond);
							int bondper_DBDO = (int) ec.calPer(Constants.branchingbndsper[2], dp, totalbond);
							int bondbranchper[] = { bondper_4O5, bondper_55, bondper_DBDO };
							//logger.debug("bondbranchper "+bondper_4O5+","+bondper_55+","+bondper_DBDO);
							cNodeList = cg.addingBrancingUnits(cNodeList, monounitList, dp, bondbranchper);
							if (cNodeList.size() > prevsize)
								lignin.setBranched(true);							
							@SuppressWarnings("unchecked")
							List<String> nodelables = (List<String>) cg.connectedChains(cNodeList).get("nodelabels");
							lignin.setNoOfNodes(nodelables.size());
							@SuppressWarnings("unchecked")
							List<List<String>> connectedgraphsAfter = (List<List<String>>) cg.connectedChains(cNodeList).get("connectedchains");							
						}
						adjAndConnMx.getAdjandConnMatrix(cNodeList);						
						lignin.setBranchingFactor(adjAndConnMx.getBranchingFactor());
						lignin.setPType(cNodeList.get(0).pType);
						lignin.setcNode(cNodeList);
						lignin.setBndCnts(getBondRatiosforLignin(cNodeList));
						lignin.setAdjAndConnMtx(adjAndConnMx);
						ligninList.add(lignin);						
					}
				}
			}
		}
		
		return ligninList;
	}

	Map<String, Integer> getBondRatiosforLignin(List<childNode> cNodeList) {
		Map<String, Integer> bondpercent = new HashMap<String, Integer>();
		for (childNode cn : cNodeList) {

			String bndType = cn.getBondType();
			if (cn.getBondType().equals(Constants._4OB))
				bndType = Constants.BO4;
			if (cn.getBondType().equals(Constants._5B))
				bndType = Constants.B5;
			if (cn.getBondType().equals(Constants._5O4))
				bndType = Constants._4O5;

			if (bondpercent.containsKey(bndType))
				bondpercent.replace(bndType, bondpercent.get(bndType) + 1);
			else
				bondpercent.put(bndType, 1);
		}
		return bondpercent;
	}

	private List<String[]> generatepossiblebonds(String[] bonds2, int[] bondper2, int dp2, String[] bondArr) {
		List<String[]> lstBonds = new ArrayList<String[]>();
		if (dp2 == 2) {
			for (int i = 0; i < bondper2.length; i++) {
				String[] possiblebonds = new String[1];
				if (bondper2[i] != 0)
					possiblebonds[0] = bonds2[i];
				if (possiblebonds.length > 0 && possiblebonds[0] != null)
					lstBonds.add(possiblebonds);
			}
		} else {
			GeneratePolymer gp = new GeneratePolymer();
			gp.permute(bondArr, 0, bondArr.length - 1, lstBonds, dp2);
		}
		return lstBonds;
	}

	MonoUnit checkBonds(MonoUnit mono, String bnd, String type, String pNode, int pID) {
		boolean isupdated = false;
		if (bnd.equals("B") || bnd.equals("OB") && mono.isBetaC()) {
			mono.setBetaC(false);
			mono = setBonds(mono, bnd, type, pNode, pID);
			if (mono != null)
				isupdated = true;

		}
		if (bnd.equals("5") && mono.isFivthC()) {
			mono.setFivthC(false);
			mono.setFourthC(false);
			mono = setBonds(mono, bnd, type, pNode, pID);
			if (mono != null)
				isupdated = true;
		}
		if ((bnd.equals("O4") || bnd.equals("4") || bnd.equals("4O")) && mono.isFourthC()) {
			mono.setFourthC(false);
			mono = setBonds(mono, bnd, type, pNode, pID);
			if (mono != null)
				isupdated = true;
		}
		if (isupdated)
			return mono;
		return null;
	}

	MonoUnit setBonds(MonoUnit mono, String bnd, String type, String pNode, int pID) {
		boolean isUpdated = false;
		if (type.equals("p")) {
			if (mono.getBnd1() == null) {
				mono.setBnd1(bnd);
				isUpdated = true;
			} else if (mono.getBnd2() == null) {
				mono.setBnd2(bnd);
				isUpdated = true;
			} else if (mono.getBnd3() == null) {
				mono.setBnd3(bnd);
				isUpdated = true;
			}
		} else if (type.equals("c")) {
			if (mono.getpBnd() == null) {
				mono.setpBnd(bnd);
				mono.setpType(pNode);
				mono.setpID(pID);
				isUpdated = true;
			}
		}
		if (isUpdated = true)
			return mono;

		return null;
	}

	Map<String, Integer> setBondcount(List<String> bondList) {
		Map<String, Integer> boundcount = new HashMap<String, Integer>();
		boundcount.put(Constants.BO4, 0);
		boundcount.put(Constants.B5, 0);
		boundcount.put(Constants.BB, 0);
		boundcount.put(Constants._55, 0);
		boundcount.put(Constants.AO4, 0);
		for (String str : bondList) {
			if (boundcount.containsKey(str)) {
				boundcount.put(str, boundcount.get(str) + 1);
			} else {
				boundcount.put(str, 1);
			}
		}
		return boundcount;
	}

	private String getDifference(Date d1, Date d2) {
		String result = null;
		/** in milliseconds */
		long diff = d2.getTime() - d1.getTime();
		/** 3 remove the milliseconds part */
		diff = diff / 1000;
		// long days = diff / (24 * 60 * 60);
		long hours = diff / (60 * 60) % 24;
		long minutes = diff / 60 % 60;
		long seconds = diff % 60;
		result = hours + ":" + minutes + ":" + seconds + "(Hrs:Mins:Secs)";
		return result;
	}

	void sop(String s) {
		System.out.println(s);
	}
}
