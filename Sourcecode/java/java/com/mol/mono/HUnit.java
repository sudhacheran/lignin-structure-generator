package com.mol.mono;

import java.io.IOException;
import java.util.ArrayList;

import org.openscience.cdk.atomtype.CDKAtomTypeMatcher;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomType;
import org.openscience.cdk.tools.manipulator.AtomTypeManipulator;

import com.mol.polymergen.GenerateStructure;

/**
 * Structural definition of HUNIT
 * @author Sudha Eswaran
 **/
public class HUnit extends MonolignolBase {

	IAtomContainer mol = null;
	ArrayList<IAtom> bondingAtom = new ArrayList<IAtom>();

	public static void main(String s[]) throws IOException {
		MonolignolBase hunit = new HUnit(1);
		System.out.println(hunit.mol);
		try {
			hunit = updatedStruct(hunit);
		} catch (CDKException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		hunit.toIMage("102", hunit.mol);
	}

	public HUnit(int i) {
		super();
		mol = generateBaseMol(i);

	}

	public IAtomContainer getMol() {
		return mol;
	}

	public void setMol(IAtomContainer mol) {
		this.mol = mol;
	}

	public ArrayList<IAtom> getBondingAtom() {
		return bondingAtom;
	}

	private void setBondingAtom(ArrayList<IAtom> bondingAtom) {
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
