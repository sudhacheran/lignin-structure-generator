package com.mol.construct;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.deser.std.CollectionDeserializer;
import com.mol.util.Constants;

/**
 * GeneratePolymer
 * @author Sudha Eswaran
 *
 */
public class GeneratePolymer {
	
	static Logger logger = Logger.getLogger(GeneratePolymer.class);


	public static void main(String[] args) {

		int dp = 25;
		GeneratePolymer gp = new GeneratePolymer();
		long noOfPer = gp.factorial(dp);
		System.out.println("No of permutation=" + noOfPer);
		
		
		

	}

	public List<String> getCorrectedPolymers(String[] str, int[] bondper, String[] mono, int[] monoPer, int dp,
			long noOfPer) {
		List<String> polymerList = getPossiblePolymer(str, bondper, mono, monoPer, dp, noOfPer);

		for (int i = 0; i < polymerList.size(); i++) {
			System.out.println(polymerList.get(i));
		}

		return polymerList;
	}

	/**
	 * Method returns the possible polymer chain permutations (within the specified
	 * limit parameter)
	 * 
	 * @param str     - Bond array
	 * @param bondper - Bond percentage
	 * @param mono    - Monomer unit array
	 * @param monoPer - Monomer percentage
	 * @param dp      - Chain length (no. of monomer units)
	 * @param noOfPer - Limiting the permutation
	 * @return
	 */
	public List<String> getPossiblePolymer(String[] str, int[] bondper, String[] mono, int[] monoPer, int dp,
			long noOfPer) {

		String[] strBonds = getList(str, bondper, dp);
		String[] monoBonds = getList(mono, monoPer, dp);

		List<String[]> bondList = new ArrayList<String[]>();

		int n = strBonds.length;
		permute(strBonds, 0, n - 1, bondList, noOfPer);

		List<String[]> monoList = new ArrayList<String[]>();
		n = monoBonds.length;
		permute(monoBonds, 0, n - 1, monoList, noOfPer);

		List<String> polymer = new ArrayList<String>();

		for (String[] s4 : monoList) {
			for (String[] s2 : bondList) {
				// String[] s4 = monoList.get(d++);
				String polymerStr = "";
				int e = 0;
				for (String tt : s2) {
					polymerStr += s4[e++] + "-" + tt + "-";
				}
				if (!polymer.contains(polymerStr))
					polymer.add(polymerStr);
			}
		}
		return polymer;
	}

	/**
	 * Method returns the possible bond names and monomer names with in the dp
	 * 
	 * @param str     - Bond or monomer array
	 * @param bondper - Bond or monomer percentile list
	 * @param dp      - length of the polymer chain
	 * @return
	 */
	public String[] getList(String[] str, int[] bondper, int dp) {

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
				double cal = calPer(bondper[j], dp);
				if (cal > 0 && totcnt[j] <= cal & cnt < dp) {
					sop(cal + "<" + totcnt[j] + "=" + str[j] + " =" + cnt);
					strBonds[cnt++] = str[j];
					totcnt[j] = totcnt[j] + 1;
				}
			}
			brkLoop = true;
			for (int j = 0; j < str.length; j++) {
				double cal = calPer(bondper[j], dp);
				if (cal > 0 && totcnt[j] <= cal && cnt < dp) {
					brkLoop = false;
				}
			}
		}
		// Loop to add bonds at the end for chain length
		while (cnt < dp) {

			for (int k = 0; k < str.length; k++) {
				if (cnt >= dp) {
					break;
				}
				strBonds[cnt++] = str[k];
			}
		}

		return strBonds;
	}

	/**
	 * This is a recursive method to create the permutations of bond (or) monomer
	 * array
	 * 
	 * @param str             - Bond array / Monomer array
	 * @param l               - Initial index of the array to permute
	 * @param r               - end index of the array to permute
	 * @param bondList        - List to store the possible combinations of Bond /
	 *                        Monomers
	 * @param noOfPermutation
	 */
	public void permute(String[] str, int l, int r, List<String[]> bondList, long noOfPermutation) {
		
		
		if (bondList.size() >= noOfPermutation) {			

			//bondList= processuniqueValue(bondList);
			return;
		}

		
			String[] st3 = str.clone();
			bondList.add(st3);				
			
			for (int i = 0; i <= r; i++) {			
				 
				if (r % 2 == 1)
					str = swap(str, 0, r);
				else
					str = swap(str, i, l);
				// Recursive call
				l = str.length-1;
				permute(str, l , r-1, bondList, noOfPermutation);								
			}

	}
	
	
	
	List<String[]> processuniqueValue(List<String[]> bondList)
	{
		Set<String> bondSet = new HashSet<String>();
		//System.out.println(bondList.size());
		for (String[] strcnt: bondList)
		{
			String value="";
			for (String val: strcnt) {					
				value += val + ",";					
			}
			bondSet.add(value);
		}
		bondList.clear();
		for (String strVal: bondSet)
		{
			bondList.add(strVal.split(","));
		}
		return bondList;
	}
	
	/**
	 * 
	 * @param bondList
	 * @return
	 */
	private Set<String> getSet(List<String[]> bondList)
	{
		Set<String> bondSet = new HashSet<String>();		
		for (String[] strdata: bondList)
		{
			String value = "";
			for (String val : strdata)
				value += val +",";
			value = (value !="" ? value.substring(0,value.length() - 1): value);			
			bondSet.add(value);
		}
		return bondSet;
		
	}
	

	/**
	 * Method to calculate the ratio
	 * 
	 * @param val - Percentage value
	 * @param dp  - polymer chain length
	 * @return
	 */
	public double calPer(int val, int dp) {
		if (val == 0)
			return val;
		double flrval = Math.round(dp * (val / 100f));

		return ((flrval > 0) ? flrval : 1);
	}

	/**
	 * Method to swap tow values in a string array
	 * 
	 * @param a
	 * @param i
	 * @param j
	 * @return
	 */
	public String[] swap(String[] a, int i, int j) {
		String temp;
		temp = a[i];
		a[i] = a[j];
		a[j] = temp;
		return a;
	}
	
	

	/**
	 * 
	 * @param number
	 * @return
	 */
	private BigInteger factBI(int number) 
	{ 
		BigInteger factorial = BigInteger.ONE; 
		for (int i = number; i > 0; i--) 
		{ 
			factorial = factorial.multiply(BigInteger.valueOf(i)); 
		} 
		return factorial;
	}

	 
    long nPr(int n, int r)
    {	
    	BigInteger numberofsquence = factBI(n).divide(factBI(n - r));
		//logger.debug("numberofsquence="+numberofsquence);
		if (numberofsquence.longValue() > Constants.noOfPer_limit)
			numberofsquence = BigInteger.valueOf(Constants.noOfPer_limit);
    	
    	return numberofsquence.longValue();
    }
    
    long factorial(int n) {
		if (n > 25)
			return Constants.noOfPer_limit;
		long fact = 1;
		for (int i = 1; i <= n; i++) {
			if (fact > Constants.noOfPer_limit)
				return Constants.noOfPer_limit;
			fact = fact * i;
		}
		return fact;
	}
	
	
	
	

	public void sop(String s) {
		System.out.println(s);
	}
}
