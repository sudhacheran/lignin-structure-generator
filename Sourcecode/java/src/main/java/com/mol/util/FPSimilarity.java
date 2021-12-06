package com.mol.util;

import java.util.BitSet;
import java.util.List;

import org.openscience.cdk.aromaticity.Aromaticity;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.fingerprint.AtomPairs2DFingerprinter;
import org.openscience.cdk.fingerprint.ExtendedFingerprinter;
import org.openscience.cdk.fingerprint.Fingerprinter;
import org.openscience.cdk.fingerprint.IBitFingerprint;
import org.openscience.cdk.fingerprint.IFingerprinter;
import org.openscience.cdk.fingerprint.MACCSFingerprinter;
import org.openscience.cdk.fingerprint.PubchemFingerprinter;
import org.openscience.cdk.fingerprint.SignatureFingerprinter;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.similarity.Tanimoto;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;

import com.mol.mono.MonolignolBase;

public class FPSimilarity {
	
	
	public boolean checkSimilarity (List<MonolignolBase> mollist, MonolignolBase mol) throws CDKException
	{
		IAtomContainer mol1 = mol.getMol();
		IFingerprinter fingerprinter = new Fingerprinter();
		AtomContainerManipulator.percieveAtomTypesAndConfigureAtoms(mol1);
		Aromaticity.cdkLegacy().apply(mol1);
		BitSet fingerprint1 = fingerprinter.getFingerprint(mol1);

		int cnt=0;
		for (MonolignolBase mol_in: mollist)		
		{					
			IAtomContainer mol2 = mol_in.getMol();
			AtomContainerManipulator.percieveAtomTypesAndConfigureAtoms(mol2);
			Aromaticity.cdkLegacy().apply(mol2);
			BitSet fingerprint2 = fingerprinter.getFingerprint(mol2);


			double val2= Tanimoto.calculate(fingerprint1, fingerprint2);
			if (val2 == 1) 
			{
				System.out.println(mol.getSmile(mol1));
				System.out.println(mol_in.getSmile(mol2));
				System.out.println("--");
			}
		}
		return false;
	}	

}
