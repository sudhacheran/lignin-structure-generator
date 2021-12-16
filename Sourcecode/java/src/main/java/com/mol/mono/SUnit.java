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
 * Structural definition of SUNIT
 * @author Sudha Eswaran
 **/
public class SUnit extends MonolignolBase {

	IAtomContainer mol = null;
	ArrayList<IAtom> bondingAtom = new ArrayList<IAtom>();

	public static void main(String s[]) throws IOException {
		MonolignolBase sunit = new SUnit(1);
		System.out.println(sunit.mol);
		try {
			sunit = updatedStruct(sunit);
			System.out.println(sunit.getSmile(sunit.mol));
		} catch (CDKException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sunit.toIMage("101",1, sunit.mol);
	}

	public IAtomContainer getMol() {
		return mol;
	}

	public void setMol(IAtomContainer mol) {
		this.mol = mol;
	}

	public SUnit(int i) {
		super();
		mol = generateBaseMol(i);
		IAtom o3, o4, c7, c8, c5 = null, c3 = null;
		IBond b13, b14, b15, b16;

		c7 = new Atom(6);
		c7.setAtomTypeName("c7_" + i);
		c8 = new Atom(6);
		c8.setAtomTypeName("c8_" + i);

		o3 = new Atom(8);
		o3.setAtomTypeName("O3_" + i);
		o4 = new Atom(8);
		o4.setAtomTypeName("O4_" + i);

		for (int i1 = 0; i1 < mol.getAtomCount(); i1++) {
			if (mol.getAtom(i1).getAtomTypeName() != null && mol.getAtom(i1).getAtomTypeName().equals("c5_" + i)) {
				c5 = mol.getAtom(i1);
			}
			if (mol.getAtom(i1).getAtomTypeName() != null && mol.getAtom(i1).getAtomTypeName().equals("c3_" + i)) {
				c3 = mol.getAtom(i1);
			}
		}

		b13 = new Bond(c3, o3);
		b14 = new Bond(c5, o4);
		b15 = new Bond(o3, c7);
		b16 = new Bond(o4, c8);
		mol.addAtom(c7);
		mol.addAtom(c8);
		mol.addAtom(o3);
		mol.addAtom(o4);
		mol.addBond(b13);
		mol.addBond(b14);
		mol.addBond(b15);
		mol.addBond(b16);
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
