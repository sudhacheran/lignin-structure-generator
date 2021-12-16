package com.mol.mono;

import java.io.IOException;
import java.util.ArrayList;

import org.openscience.cdk.Atom;
import org.openscience.cdk.Bond;
import org.openscience.cdk.atomtype.CDKAtomTypeMatcher;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomType;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.tools.manipulator.AtomTypeManipulator;
/**
 * Structural definition of GUNIT
 * @author Sudha Eswaran
 **/
public class GUnit extends MonolignolBase {

	IAtomContainer mol = null;
	ArrayList<IAtom> bondingAtom = new ArrayList<IAtom>();

	public static void main(String s[]) throws IOException {
		MonolignolBase gunit = new GUnit(1);

		System.out.println(gunit.mol);
		try {
			gunit = updatedStruct(gunit);
			System.out.println(gunit.getSmile(gunit.mol));
		} catch (CDKException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		gunit.toIMage("103",1, gunit.mol);
		// gunit.getSmile(gunit.mol);
	}

	public IAtomContainer getMol() {
		return mol;
	}

	public void setMol(IAtomContainer mol) {
		this.mol = mol;
	}

	public GUnit(int i) {
		super();
		mol = generateBaseMol(i);
		IAtom o3, c7, c5 = null, c3 = null;
		IBond b13, b14;

		c7 = new Atom(6);
		c7.setAtomTypeName("c7_" + i);

		o3 = new Atom(8);
		o3.setAtomTypeName("O3_" + i);

		for (int i1 = 0; i1 < mol.getAtomCount(); i1++) {
			if (mol.getAtom(i1).getAtomTypeName() != null && mol.getAtom(i1).getAtomTypeName().equals("c5_" + i)) {
				c5 = mol.getAtom(i1);
			}
			if (mol.getAtom(i1).getAtomTypeName() != null && mol.getAtom(i1).getAtomTypeName().equals("c3_" + i)) {
				c3 = mol.getAtom(i1);
			}
		}

		b13 = new Bond(c3, o3);
		b14 = new Bond(o3, c7);
		mol.addAtom(c7);
		mol.addAtom(o3);
		mol.addBond(b13);
		mol.addBond(b14);
		setBondingAtom(bondingAtom);

	}

	public ArrayList<IAtom> getBondingAtom() {
		return bondingAtom;
	}

	private void setBondingAtom(ArrayList<IAtom> bondingAtom) {
		bondingAtom = new ArrayList<IAtom>();
		bondingAtom.add(c4);
		bondingAtom.add(c1);
		bondingAtom.add(betaC);
		bondingAtom.add(alphaC);
		this.bondingAtom = bondingAtom;
	}

	static MonolignolBase updatedStruct(MonolignolBase g) throws CDKException {
		IAtomContainer g1_mol = g.getMol();
		for (int i = 0; i < g1_mol.getAtomCount(); i++) {
			g1_mol.getAtom(i).setAtomTypeName(g1_mol.getAtom(i).getSymbol());
		}
		// g.toIMage(10, g.getMol());

		CDKAtomTypeMatcher matcher = CDKAtomTypeMatcher.getInstance(g1_mol.getBuilder());
		for (IAtom atom : g1_mol.atoms()) {
			// System.out.println(atom);
			IAtomType type = matcher.findMatchingAtomType(g1_mol, atom);
			AtomTypeManipulator.configure(atom, type);
		}

		return g;
	}

}
