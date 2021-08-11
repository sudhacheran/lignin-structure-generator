package com.mol.mono;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.openscience.cdk.Atom;
import org.openscience.cdk.AtomContainer;
import org.openscience.cdk.Bond;
import org.openscience.cdk.CDKConstants;
import org.openscience.cdk.config.Isotopes;
import org.openscience.cdk.depict.DepictionGenerator;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.io.CMLWriter;
import org.openscience.cdk.io.MDLV2000Writer;
import org.openscience.cdk.layout.StructureDiagramGenerator;
import org.openscience.cdk.smiles.SmiFlavor;
import org.openscience.cdk.smiles.SmilesGenerator;
import org.openscience.cdk.tools.CDKHydrogenAdder;

/**
 * MonolignolBase: Abstract for defining  the phenyl propane structure
 * @author Sudha Eswaran
 *
 */

public class MonolignolBase {

	IAtomContainer mol = null;
	IAtom o1, o2, o3;
	IAtom c1, c2, c3, c4, c5, c6, betaC, alphaC, gammaC;
	IBond b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11;

	boolean isSingleChain;
	
	int noOfChains;

	
	public int getNoOfChains() {
		return noOfChains;
	}

	public void setNoOfChains(int noOfChains) {
		this.noOfChains = noOfChains;
	}

	public boolean isSingleChain() {
		return isSingleChain;
	}

	public void setSingleChain(boolean isSingleChain) {
		this.isSingleChain = isSingleChain;
	}

	public IAtomContainer getMol() {
		return mol;
	}

	public void setMol(IAtomContainer mol) {
		this.mol = mol;
	}

	public MonolignolBase() {
		super();
	}

	public static MonolignolBase getMonolignolUnit(String type, int cnt) {
		if (type.equals("G"))
			return new GUnit(cnt);
		else if (type.equals("H"))
			return new HUnit(cnt);
		else
			return new SUnit(cnt);
	}

	public IAtomContainer generateBaseMol(int i) {

		c1 = new Atom(6);
		c1.setAtomTypeName("c1_" + i);

		c2 = new Atom(6);
		c2.setAtomTypeName("c2_" + i);

		c3 = new Atom(6);
		c3.setAtomTypeName("c3_" + i);

		c4 = new Atom(6);
		c4.setAtomTypeName("c4_" + i);

		c5 = new Atom(6);
		c5.setAtomTypeName("c5_" + i);

		c6 = new Atom(6);
		c6.setAtomTypeName("c6_" + i);

		alphaC = new Atom(6);
		alphaC.setAtomTypeName("aC_" + i);

		betaC = new Atom(6);
		betaC.setAtomTypeName("bC_" + i);

		gammaC = new Atom(6);
		gammaC.setAtomTypeName("gC_" + i);

		o1 = new Atom(8);
		o1.setAtomTypeName("O1_" + i);
		o2 = new Atom(8);
		o2.setAtomTypeName("O2_" + i);

		b1 = new Bond(c1, c2, IBond.Order.DOUBLE);
		b2 = new Bond(c2, c3);
		b3 = new Bond(c3, c4, IBond.Order.DOUBLE);
		b4 = new Bond(c4, c5);
		b5 = new Bond(c5, c6, IBond.Order.DOUBLE);
		b6 = new Bond(c1, c6);
		b1.setProperty(CDKConstants.ISAROMATIC, true);
		b2.setProperty(CDKConstants.ISAROMATIC, true);
		b3.setProperty(CDKConstants.ISAROMATIC, true);
		b4.setProperty(CDKConstants.ISAROMATIC, true);
		b5.setProperty(CDKConstants.ISAROMATIC, true);
		b6.setProperty(CDKConstants.ISAROMATIC, true);

		b7 = new Bond(c1, alphaC);
		b8 = new Bond(alphaC, betaC, IBond.Order.DOUBLE);
		b9 = new Bond(betaC, gammaC);
		b10 = new Bond(c4, o2); // Phenol oxygen
		b11 = new Bond(gammaC, o1); // Gamma oxygen

		mol = new AtomContainer();
		mol.addAtom(c1);
		mol.addAtom(c2);
		mol.addAtom(c3);
		mol.addAtom(c4);
		mol.addAtom(c5);
		mol.addAtom(c6);
		mol.addAtom(alphaC);
		mol.addAtom(betaC);
		mol.addAtom(gammaC);

		mol.addAtom(o1);
		mol.addAtom(o2);
		mol.addBond(b1);
		mol.addBond(b2);
		mol.addBond(b3);
		mol.addBond(b4);
		mol.addBond(b5);
		mol.addBond(b6);
		mol.addBond(b7);
		mol.addBond(b8);
		mol.addBond(b9);
		mol.addBond(b10);
		mol.addBond(b11);

		return mol;
	}

	public String getSmile(IAtomContainer mol) {
		String smi = null;
		try {
			StructureDiagramGenerator sdg = new StructureDiagramGenerator();
			sdg.generateCoordinates(mol);
			IAtomContainer OutMol = sdg.getMolecule();
			CDKHydrogenAdder adder = CDKHydrogenAdder.getInstance(OutMol.getBuilder());
			adder.addImplicitHydrogens(OutMol);
			SmilesGenerator sg = new SmilesGenerator(
					SmiFlavor.Unique | SmiFlavor.UseAromaticSymbols | SmiFlavor.Canonical);
			smi = sg.create(OutMol);

			// System.out.print(smi);

		} catch (CDKException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return smi;
	}

	public double getMolWt(IAtomContainer mol) throws IOException {
		Isotopes isotopeInfo = Isotopes.getInstance();
		double molWeight = 0.0;
		for (int i = 0; i < mol.getAtomCount(); i++) {
			molWeight += isotopeInfo.getNaturalMass(mol.getAtom(i));
		}
		return Math.round(molWeight);
	}

	@SuppressWarnings({ "unchecked", "deprecated" })
	public void toCML(IAtomContainer mol, String nm) {

		try {

			boolean dirpresent = getDir("cml");
			FileWriter output = new FileWriter("output/cml/lg_" + nm + ".cml");
			CMLWriter cmlwriter = new CMLWriter(output);
			cmlwriter.write(mol);
			cmlwriter.close();
		} catch (CDKException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void toMOL(IAtomContainer mol, String nm) {
		try {

			boolean dirpresent = getDir("mol");

			MDLV2000Writer writer = new MDLV2000Writer(new FileWriter(new File("output/mol/lg_" + nm + ".mol"), false));
			writer.write(mol);
			writer.close();

		} catch (CDKException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void toIMage(String nm, IAtomContainer mol) {
		try {
			StructureDiagramGenerator sdg = new StructureDiagramGenerator();
			sdg.generateCoordinates(mol);

			IAtomContainer OutMol = sdg.getMolecule();

			CDKHydrogenAdder adder = CDKHydrogenAdder.getInstance(OutMol.getBuilder());
			adder.addImplicitHydrogens(OutMol);

			boolean dirpresent = getDir("struct");
			new DepictionGenerator().withAtomColors().depict(OutMol) // .withAtomNumbers()
					.writeTo("output/struct/lg_" + nm + ".png");

		} catch (IOException | CDKException e) {

			e.printStackTrace();
		}
	}

	public boolean getDir(String dirname) {

		File directory = new File("output");
		if (!directory.exists()) {
			directory.mkdir();
		}
		File directory2 = new File("output/" + dirname);
		if (!directory2.exists()) {
			directory2.mkdir();
		}
		return true;
	}

}
