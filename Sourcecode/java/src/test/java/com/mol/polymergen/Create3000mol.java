package com.mol.polymergen;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.exception.InvalidSmilesException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.io.MDLV3000Writer;
import org.openscience.cdk.silent.SilentChemObjectBuilder;
import org.openscience.cdk.smiles.SmilesParser;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mol.pojo.LigninsJson;
import com.mol.pojo.SingleStructDet;

public class Create3000mol {
	
	
	public static void main(String s[]) throws JsonParseException, JsonMappingException, IOException, InvalidSmilesException
	{
		File folder = new File("C:/Users/eswa765/eclipse-workspace/ligninstructuresimulation/output_SG type/json");
		File[] listOfFiles = folder.listFiles();

		for (File file : listOfFiles) {
			 System.out.println(file.getName());
		   
		    	 ObjectMapper mapper = new ObjectMapper();
		    	 	
		    	 System.out.println(file.getName());
		    	    // convert JSON file to map
		    	 	LigninsJson map = mapper.readValue(file, LigninsJson.class);
		    	 	int dp = 	map.getDp();
		    	 	
		    	 	for (SingleStructDet st : map.getLigninchains())
		    	 	{
		    	 		String id = st.getLg_id();
		    	 		String smilestr = st.getSmilestring();
		    	 		SmilesParser   sp  = new SmilesParser(SilentChemObjectBuilder.getInstance());
		    	 	    IAtomContainer mol   = sp.parseSmiles(smilestr);
		    	 	    toMOL (mol, dp, id);
		    	 		
		    	 	}
		    	   
		        
		    
		}
		
	}
	
	public static void toMOL(IAtomContainer mol, int dp, String nm) {
		try {

			boolean dirpresent = getDir("molsg/",dp);

			MDLV3000Writer writer = new MDLV3000Writer(new FileWriter(new File("output/molsg/"+dp+"/" + nm + ".mol"), false));
			writer.write(mol);
			writer.close();

		} catch (CDKException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public static boolean getDir(String dirname, int dp) {

		File directory = new File("output");
		if (!directory.exists()) {
			directory.mkdir();
		}
		File directory2 = new File("output/" + dirname);
		if (!directory2.exists()) {
			directory2.mkdir();
		}
		
		File directory3 = new File("output/" + dirname+"/"+dp);
		if (!directory3.exists()) {
			directory3.mkdir();
		}
		
		return true;
	}

}
