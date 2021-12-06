package com.mol.util;
/**
 * Constants
 * @author Sudha Eswaran
 *
 */
public final class Constants {
	

	public static String BO4 = "BO4";
	public static String _4OB = "4OB";
	public static String BB = "BB";
	public static String B5 = "B5";
	public static String _5B = "5B";
	public static String AO4 = "AO4";
	public static String _4OA = "4OA";
	public static String _4O5 = "4O5";
	public static String _5O4 = "5O4";
	public static String _55 = "55";
	public static String DBDO = "DBDO";

	public static String O4 = "O4";
	public static String _4O = "4O";
	public static String B = "B";
	public static String _5 = "5";
	public static String A = "A";

	public static String mono_S = "S";
	public static String mono_G = "G";
	public static String mono_H = "H";

	// Number of permutations assumed 
	public static final int noOfPer_limit = 1000000;
	
	// Ratio of the bonds
	public static String[] bonds = { Constants.BO4, Constants.BB, Constants.B5 }; // Bonds possible
	// public int[] bondper = {60,5,10};// softwood - spruce-MWL
	public static int[] bondper = { 50, 12, 3 }; // Hardowood - Birch MWL

	public static String[] branchingbnds = { Constants._4O5, Constants._55, Constants.DBDO };
	// static int[] branchingbndsper = {5, 10, 5}; // softwood - spruce-MWL
	public static int[] branchingbndsper = { 5, 2, 2 }; // Hardowood - Birch MWL

	public static String[] mono = { "G", "S", "H" }; // Monomer units
	// public int[] monoPer = {100,0,0}; // Ratio of the units
	public static int[] monoPer = {}; // Ratio of the units

	public static int dp_units = 0; // No of monomer units
	
	public static boolean dprange=false;
	
	public static int min_dp = 0;
	public static int max_dp = 0;	
	
	public static boolean addToDB=false;
	public static String mongo_URI="mongodb://localhost:27017/admin?readPreference=primary&appname=MongoDB%20Compass&ssl=false";
	public static String mongo_dbname="lignindata";
	public static String mongo_collname="ligninstructs_1Dec_test";	

}
