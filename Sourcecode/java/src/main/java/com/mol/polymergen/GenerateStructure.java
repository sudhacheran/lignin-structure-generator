package com.mol.polymergen;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.openscience.cdk.Atom;
import org.openscience.cdk.Bond;
import org.openscience.cdk.atomtype.CDKAtomTypeMatcher;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.graph.ConnectivityChecker;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.openscience.cdk.interfaces.IAtomType;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.isomorphism.Mappings;
import org.openscience.cdk.isomorphism.Ullmann;
import org.openscience.cdk.isomorphism.VentoFoggia;
import org.openscience.cdk.silent.SilentChemObjectBuilder;
import org.openscience.cdk.smiles.SmilesParser;
import org.openscience.cdk.tools.manipulator.AtomTypeManipulator;

import com.db.MongoDBClient;
import com.mol.construct.Createligninstruct;
import com.mol.construct.Lignin;
import com.mol.construct.childNode;
import com.mol.mono.MonolignolBase;
import com.mol.pojo.Config;
import com.mol.pojo.LigninsJson;
import com.mol.pojo.SingleStructDet;
import com.mol.util.ConnectedGraphs;
import com.mol.util.Constants;
import com.mol.util.FPSimilarity;
import com.mol.util.yamlread;


/**
 * GenerateStructure: Generating lignin structure
 * @author Sudha Eswaran
 *
 */
public class GenerateStructure {

	static Logger logger = Logger.getLogger(GenerateStructure.class);
	static private boolean debug= false;

	public static void main(String s[]) throws IOException, NumberFormatException, CDKException {
		
		DOMConfigurator.configure("resources/log4j.xml");
		Config prjConfig = yamlread.initializeConstants();
		GenerateStructure gs = new GenerateStructure();	
		
		if (debug)
		{
			Scanner in = new Scanner(System.in);
			System.out.println("Enter the DP (no. of monomer units) : ");
			String dp = in.nextLine();
			System.out.println("DP Entered = " + dp);
			gs.runData(Integer.parseInt(dp), prjConfig);	
		}
		else
		{
			System.out.println("\n\nLignin structure(LGS) generator");
			System.out.println("-------------------------------");
			if (Constants.dprange)
			{
				for (int i=Constants.min_dp; i<=Constants.max_dp; i++)
				{	
					gs.runData(i, prjConfig);
				}
			}
			else
			{	
				gs.runData(Constants.dp_units, prjConfig);				
			}
			System.out.println("\n\t\tCOMPLETED *Generated structures can be found in OUTPUT folder");
		}
	}

	public void runData(int dp, Config prjConfig ) throws IOException, CDKException {
		Date dt = new Date();
		// Read the project configurations
		Createligninstruct cls = new Createligninstruct();
		
		System.out.println("\nMonomer(G / S / H) ratio       : " + cls.getMonoPer());
		System.out.println("Degree of polymerization (DP)  : " + dp);
		
		logger.info("Monomer (G / S / H) ratio : " + cls.getMonoPer());
		logger.info("Degree of polymerization (DP) : " + dp);
		List<Lignin> ligninList = cls.getLiginList(dp, true);
	
		List<String> ligninSmiles = new ArrayList<String>();
		List<Double> molweight = new ArrayList<Double>();
		List<Map> bondkeys = new ArrayList<Map>();

		int i = 0;
		int totlig = 0;

		double s_g_ratio = Double.valueOf(Constants.monoPer[1]) / Double.valueOf(Constants.monoPer[0]);
		String s_g = String.format("%.2f", s_g_ratio);

		LigninsJson jsonObjLignin = new LigninsJson();
		jsonObjLignin.setDp(dp);
		jsonObjLignin.setMonoType(cls.getMtype());
		jsonObjLignin.setS_G_Ratio(new Double(s_g));
		jsonObjLignin.setName("polymer");
	
		int noofbranchedstructs = 0;
		int nooflinearstructs = 0;		
		int evaluationDP_sum=0;
		
		for (Lignin lignin : ligninList) {
			int parentNode = Integer.parseInt("" + lignin.getPType().substring(0, lignin.getPType().length() - 1));
			String parentNodeType = "" + lignin.getPType().charAt(lignin.getPType().length() - 1);

			List<Integer> cc = new ArrayList<Integer>();
			List<childNode> cNList = lignin.getcNode();
			MonolignolBase g = null;
			int evaluatedDP=lignin.getNoOfNodes();
			
			//System.out.println("lignin.getNoOfNodes()="+lignin.getNoOfNodes());

			if (lignin.getNoOfNodes() >= dp) {
				
				g = MonolignolBase.getMonolignolUnit(parentNodeType, parentNode);

				for (childNode cN : cNList) {
					// Child object initialization			
					int gp = Integer.parseInt("" + cN.getpType().substring(0, cN.getpType().length() - 1));
					String gptype = "" + cN.getpType().charAt(cN.getpType().length() - 1);
					int gc = Integer.parseInt("" + cN.getcType().substring(0, cN.getcType().length() - 1));
					String gctype = "" + cN.getcType().charAt(cN.getcType().length() - 1);				

					if (cN.getpType().equals(lignin.getPType())) {
						cc.add(gc);
						MonolignolBase g2 = MonolignolBase.getMonolignolUnit(gctype, gc);
						g = genStructTwo(g, g2, cN.getBondType(), gp, gc);
					} else {
						if (cc.contains(gp) && cc.contains(gc)) {
							if (cN.getBondType().equals(Constants.BO4))
								g = generateStruct(g, "bC_" + gp, "O2_" + gc, cN.getBondType());
							else if (cN.getBondType().equals(Constants._4OB))
								g = generateStruct(g, "O2_" + gp, "bC_" + gc, cN.getBondType());
							else if (cN.getBondType().equals(Constants.BB))
								g = generateStruct(g, "bC_" + gp, "bC_" + gc, cN.getBondType());
							else if (cN.getBondType().equals(Constants._55))
								g = generateStruct(g, "c5_" + gp, "c5_" + gc, cN.getBondType());
							else if (cN.getBondType().equals(Constants.AO4))
								g = generateStruct(g, "aC_" + gp, "O2_" + gc, cN.getBondType());
							else if (cN.getBondType().equals(Constants.B5))
								g = generateStruct(g, "bC_" + gp, "c5_" + gc, cN.getBondType());
							else if (cN.getBondType().equals(Constants._5B))
								g = generateStruct(g, "c5_" + gp, "bC_" + gc, cN.getBondType());
							else if (cN.getBondType().equals(Constants._4O5))
								g = generateStruct(g, "O2_" + gp, "c5_" + gc, cN.getBondType());
							else if (cN.getBondType().equals(Constants._5O4))
								g = generateStruct(g, "c5_" + gp, "O2_" + gc, cN.getBondType());
						} else if (!cc.contains(gc)) {
							if (!cc.contains(gp)) {
								cc.add(gp);
								MonolignolBase detachedParentnode = MonolignolBase.getMonolignolUnit(gptype, gp);
								g = addDetachednode(g, detachedParentnode);
							}
							cc.add(gc);							
							MonolignolBase g2 = MonolignolBase.getMonolignolUnit(gctype, gc);							
							g = genStructTwo(g, g2, cN.getBondType(), gp, gc);
						}
					}
				}
			}
			i++;
			
			if (g != null) {		
				
				g = updatedStruct(g);
				String smile = g.getSmile(g.getMol());
				double mwt = g.getMolWt(g.getMol());
				Map bndset = lignin.getBndCnts();
				
			
				if (!ligninSmiles.contains(smile)) {
					
				    ligninSmiles.add(smile);
					molweight.add(mwt);
					bondkeys.add(lignin.getBndCnts());
					if (lignin.isBranched())
						noofbranchedstructs++;
					else 
						nooflinearstructs++;						
					
					evaluationDP_sum += evaluatedDP;

					SingleStructDet oneChain = new SingleStructDet();
					oneChain.setLg_id("lg_"+dp + "_" + totlig);
					oneChain.setBndCnts(lignin.getBndCnts());
					oneChain.setMolWeight(g.getMolWt(g.getMol()));
					oneChain.setSmilestring(smile);
					oneChain.setIssingleChain(g.isSingleChain());					
					oneChain.setNumberOfOligomers(g.getNoOfChains());
					oneChain.setEvaluatedDP(evaluatedDP);
					if (!lignin.isBranched())
						oneChain.setBranchingFactor(1.0);
					else
						oneChain.setBranchingFactor(lignin.getBranchingFactor());
					if (oneChain.getSmilestring() != null) {
						jsonObjLignin.getLigninchains().add(oneChain);
					}

					if (prjConfig != null && prjConfig.isSdf())
						g.toMOL(g.getMol(), dp, dp + "_" + totlig);
					if (prjConfig != null && prjConfig.isPng())
						g.toIMage(dp + "_" + totlig, dp,  g.getMol());
					/*if (prjConfig != null && prjConfig.isCml())
						g.toCML(g.getMol(), dp,  dp+"_"+totlig);*/
					//g.toXYZfile(g.getMol(), dp,  dp+"_"+totlig);
					
					if (prjConfig != null && prjConfig.isMatrices())
						lignin.getAdjAndConnMtx().writetoCSV(dp + "_" + totlig, dp);
					
					lignin.setMolWeight(g.getMolWt(g.getMol()));				
					
					totlig++;					
				}
			}

		}
		if (ligninSmiles.size() > 0 ) {
			
			double molWtTotal = 0.0;
			double brachCoeffTotal = 0.0;
			for (SingleStructDet ss : jsonObjLignin.getLigninchains()) {
				molWtTotal += ss.getMolWeight();
				brachCoeffTotal += ss.getBranchingFactor();
			}
			double avgMolWt = molWtTotal / jsonObjLignin.getLigninchains().size();
			avgMolWt = Double.parseDouble(new DecimalFormat("#.##").format(avgMolWt));
			double avfBranchCoeff = brachCoeffTotal / jsonObjLignin.getLigninchains().size();
			avfBranchCoeff = Double.parseDouble(new DecimalFormat("#.##").format(avfBranchCoeff));			
			jsonObjLignin.setAvgBranchFactor(avfBranchCoeff);
			jsonObjLignin.setAvgMolWt(avgMolWt);
			jsonObjLignin.setNunber_of_structs(totlig);	
			jsonObjLignin.setNoOfBranchedChains(noofbranchedstructs);
			jsonObjLignin.setNoOflinearChains(nooflinearstructs);
			jsonObjLignin.setEvaluatedDP(evaluationDP_sum / jsonObjLignin.getLigninchains().size());
			Date dt2 = new Date();
			jsonObjLignin.setExecutionTime((dt2.getTime()- dt.getTime())/1000);
			
			if (prjConfig != null && prjConfig.isJson())
			{
				jsonObjLignin.createJSON(jsonObjLignin);
			}

			/**** Update in Database ****/
			if (Constants.addToDB) {
				MongoDBClient mdb = new MongoDBClient();
				mdb.insertData(jsonObjLignin.createJSON(jsonObjLignin));
			}
		}

		Date dt2 = new Date();
		logger.info("Time taken=" + getDifference(dt, dt2));

		logger.info("Number of polymers generated=" + totlig + ", No of linear:" + nooflinearstructs
				+ ",No. of branched:" + noofbranchedstructs);
	
		System.out.println("\nNumber of polymers generated=" + totlig + ", No of linear structures :" + nooflinearstructs
				+ ",No. of branched structures:" + noofbranchedstructs);		
		System.out.println("\nTime on execution=" + getDifference(dt, dt2));
	}

	private MonolignolBase genStructTwo(MonolignolBase g, MonolignolBase g2, String bondType, int gp, int gc) {

		if (bondType.equals(Constants.BO4))
			g = generateStruct(g, g2, "bC_" + gp, "O2_" + gc, bondType);
		else if (bondType.equals(Constants._4OB))
			g = generateStruct(g, g2, "O2_" + gp, "bC_" + gc, bondType);
		else if (bondType.equals(Constants.BB))
			g = generateStruct(g, g2, "bC_" + gp, "bC_" + gc, bondType);
		else if (bondType.equals(Constants._55))
			g = generateStruct(g, g2, "c5_" + gp, "c5_" + gc, bondType);
		else if (bondType.equals(Constants.AO4))
			g = generateStruct(g, g2, "aC_" + gp, "O2_" + gc, bondType);
		else if (bondType.equals(Constants.B5))
			g = generateStruct(g, g2, "bC_" + gp, "c5_" + gc, bondType);
		else if (bondType.equals(Constants._5B))
			g = generateStruct(g, g2, "c5_" + gp, "bC_" + gc, bondType);
		else if (bondType.equals(Constants._4O5))
			g = generateStruct(g, g2, "O2_" + gp, "c5_" + gc, bondType);
		else if (bondType.equals(Constants._5O4))
			g = generateStruct(g, g2, "c5_" + gp, "O2_" + gc, bondType);

		return g;
	}

	MonolignolBase updatedStruct(MonolignolBase g) throws CDKException {
		IAtomContainer g1_mol = g.getMol();
		for (int i = 0; i < g1_mol.getAtomCount(); i++) {
			g1_mol.getAtom(i).setAtomTypeName(g1_mol.getAtom(i).getSymbol());
		}

		boolean isConnected = ConnectivityChecker.isConnected(g1_mol);
		g.setSingleChain(isConnected);
	
	    IAtomContainerSet containerSet = ConnectivityChecker.partitionIntoMolecules(g1_mol);
	    g.setNoOfChains(containerSet.getAtomContainerCount());
		
		CDKAtomTypeMatcher matcher = CDKAtomTypeMatcher.getInstance(g1_mol.getBuilder());
		for (IAtom atom : g1_mol.atoms()) {
			// System.out.println(atom);
			IAtomType type = matcher.findMatchingAtomType(g1_mol, atom);
			AtomTypeManipulator.configure(atom, type);
		}

		return g;
	}

	MonolignolBase addDetachednode(MonolignolBase g, MonolignolBase detachedNode) {
		IAtomContainer g1_mol = g.getMol();
		IAtomContainer g2_mol = detachedNode.getMol();
		for (int i = 0; i < g2_mol.getAtomCount(); i++) {
			g1_mol.addAtom(g2_mol.getAtom(i));
		}
		for (int i = 0; i < g2_mol.getBondCount(); i++) {
			g1_mol.addBond(g2_mol.getBond(i));
		}
		return g;
	}

	MonolignolBase generateStruct(MonolignolBase g, MonolignolBase g2, String b1, String b2, String bondType) {
		IAtomContainer g1_mol = g.getMol();
		IAtomContainer g2_mol = g2.getMol();
		IAtom atm1 = null, atm2 = null, alphaC1 = null, gammaCO1 = null, alphaC2 = null, gammaCO2 = null, c4O = null,
				o5 = null;
		IBond bondalpha_OH = null;

		String[] mol1 = b1.split("_");
		String[] mol2 = b2.split("_");
		
		for (int i = 0; i < g1_mol.getAtomCount(); i++) {
			// Changing double bond for B position bonding in Monomer1 or oligomer
			if (g1_mol.getAtom(i).getAtomTypeName() != null && g1_mol.getAtom(i).getAtomTypeName().equals(b1)) {
				atm1 = g1_mol.getAtom(i);
				if (i > 1) {
					IAtom atm3 = g1_mol.getAtom(i - 1);
					if (mol1[0].equals("bC")) // betaCarbon in the side chain
					{
						alphaC1 = atm3;
						gammaCO1 = g1_mol.getAtom(i + 2);
		
					}
					if (mol1[0].equals("c5")) // 5th Carbon in the ring
					{
						c4O = g1_mol.getAtom(i + 6);
					}
		
					IBond bnd2 = g1_mol.getBond(atm3, atm1);
					if (bnd2 != null && bnd2.getOrder().equals(IBond.Order.DOUBLE)) {
						bnd2.setOrder(IBond.Order.SINGLE);
						o5 = new Atom(8);
						o5.setAtomTypeName("O5_" + i);
						bondalpha_OH = new Bond(atm3, o5);
					}
				}

			}
		}

		for (int i = 0; i < g2_mol.getAtomCount(); i++) {
			// Changing double bond for B position in the attaching monomer
			if (g2_mol.getAtom(i).getAtomTypeName() != null && g2_mol.getAtom(i).getAtomTypeName().equals(b2)) {
				atm2 = g2_mol.getAtom(i);
				if (mol2[0].equals("bC")) // betaCarbon in the side chain
				{
					alphaC2 = g2_mol.getAtom(i - 1);
					;
					gammaCO2 = g2_mol.getAtom(i + 2);
				}
				if (mol2[0].equals("c5")) // 5th Carbon in the ring
				{
					c4O = g2_mol.getAtom(i + 6);
				}
				if (i > 1) {
					IAtom atm3 = g2_mol.getAtom(i - 1);
					IBond bnd2 = g2_mol.getBond(atm3, atm2);
					if (bnd2 != null && bnd2.getOrder().equals(IBond.Order.DOUBLE)) {
						bnd2.setOrder(IBond.Order.SINGLE);
						o5 = new Atom(8);
						o5.setAtomTypeName("O5_" + i);
						bondalpha_OH = new Bond(atm3, o5);
					}
				}
			}
			g1_mol.addAtom(g2_mol.getAtom(i));

		}

		for (int i = 0; i < g2_mol.getBondCount(); i++) {
			g1_mol.addBond(g2_mol.getBond(i));
		}
		IBond bnd = new Bond(atm1, atm2);
		if (bondType.equals(Constants.BB)) {
			IBond bnd2 = new Bond(alphaC1, gammaCO2);
			IBond bnd3 = new Bond(alphaC2, gammaCO1);
			g1_mol.addBond(bnd2);
			g1_mol.addBond(bnd3);
		}
		if (bondType.equals(Constants.B5)) {
			IBond bnd2 = new Bond(alphaC1, c4O);
			g1_mol.addBond(bnd2);
		}
		if (bondType.equals(Constants._5B)) {
			IBond bnd2 = new Bond(c4O, alphaC2);
			g1_mol.addBond(bnd2);
		}
		g1_mol.addBond(bnd);
		if (bondalpha_OH != null && bondType.equals(Constants.BO4) || bondType.equals(Constants._4OB)) {
			g.getMol().addAtom(o5);
			g.getMol().addBond(bondalpha_OH);
		}

		for (int i = 0; i < g.getMol().getBondCount(); i++) {
			IAtom beg = g.getMol().getBond(i).getBegin();
			if (beg.getAtomTypeName().equals(b1) && mol1[0].equals("aC")) {
				IAtom[] endAtoms = g.getMol().getBond(i).getConnectedAtoms(beg);

				for (IAtom end : endAtoms) {
					String[] sepAtms = end.getAtomTypeName().split("_");

					if (sepAtms[0].equals("O5")) {
						g.getMol().removeBond(g.getMol().getBond(i));
						g.getMol().removeAtom(end);
					}
				}
			}

		}

		return g;
	}

	MonolignolBase generateStruct(MonolignolBase g, String b1, String b2, String bondType) {
		IAtomContainer g1_mol = g.getMol();
		IAtom atm1 = null, atm2 = null, atm3 = null, alphaC1 = null, gammaCO1 = null, alphaC2 = null, gammaCO2 = null,
				c4O = null, o5 = null;
		IBond bondalpha_OH = null;
		
		String[] mol1 = b1.split("_");
		String[] mol2 = b2.split("_");

		for (int i = 0; i < g1_mol.getAtomCount(); i++) {
			if (g1_mol.getAtom(i).getAtomTypeName() != null && g1_mol.getAtom(i).getAtomTypeName().equals(b1)) {
				atm1 = g1_mol.getAtom(i);
				
				if (i > 1) {
					atm3 = g1_mol.getAtom(i - 1);
					IBond bnd2 = g1_mol.getBond(atm3, atm1);
				
					if (bnd2 != null && bnd2.getOrder().equals(IBond.Order.DOUBLE)) {
						bnd2.setOrder(IBond.Order.SINGLE);
						o5 = new Atom(8);
						o5.setAtomTypeName("O5_" + i);
						bondalpha_OH = new Bond(atm3, o5);
					}
				}
			}
		}

		for (int i = 0; i < g1_mol.getAtomCount(); i++) {
			if (g1_mol.getAtom(i).getAtomTypeName() != null && g1_mol.getAtom(i).getAtomTypeName().equals(b2)) {
				atm2 = g1_mol.getAtom(i);
				
			}
		}
		IBond bnd = new Bond(atm1, atm2);
		g.getMol().addBond(bnd);
		if (bondalpha_OH != null && bondType.equals(Constants.BO4) || bondType.equals(Constants._4OB)) {
			g.getMol().addAtom(o5);
			g.getMol().addBond(bondalpha_OH);
		}

		for (int i = 0; i < g.getMol().getBondCount(); i++) {
			IAtom beg = g.getMol().getBond(i).getBegin();
			if (beg.getAtomTypeName().equals(b1) && mol1[0].equals("aC")) {
				IAtom[] endAtoms = g.getMol().getBond(i).getConnectedAtoms(beg);
				for (IAtom end : endAtoms) {
					String[] sepAtms = end.getAtomTypeName().split("_");
				
					if (sepAtms[0].equals("O5")) {
						g.getMol().removeBond(g.getMol().getBond(i));
						g.getMol().removeAtom(end);
					}
				}
			}

		}

		return g;
	}

	void subsctructurecount(MonolignolBase g) throws CDKException {
		IAtomContainer g1_mol = g.getMol();
		int hits = 0;
		SmilesParser sp = new SmilesParser(SilentChemObjectBuilder.getInstance());
		IAtomContainer m = sp.parseSmiles("OH");
		int totalatom = m.getAtomCount();
		System.out.println("atomcount="+totalatom);
		Mappings count = Ullmann.findSubstructure(m).matchAll(g1_mol);		
		System.out.println(g.getSmile(g1_mol));
		System.out.println("hits=" + count.count());
	}

	private static String getDifference(Date d1, Date d2) {
		String result = null;
		/** in milliseconds */
		long diff = d2.getTime() - d1.getTime();
		
		/** 3 remove the milliseconds part */
		diff = diff / 1000;		
		
		
		long hours = diff / (60 * 60) % 24;
		long minutes = diff / 60 % 60;
		long seconds = diff % 60;
		result = hours + ":" + minutes + ":" + seconds + "(Hrs:Mins:Secs)";
		return result;
	}

}
