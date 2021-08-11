package com.mol.pojo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mol.polymergen.GenerateStructure;


/**
 * LigninsJson: Json dataset definiiton
 * @author Sudha Eswaran
 *
 */

public class LigninsJson {

	static Logger logger = Logger.getLogger(LigninsJson.class);
	String monoType;
	double S_G_Ratio;
	String name;
	int dp;
	int nunber_of_structs;

	List<SingleStructDet> ligninchains;

	int noOflinearChains = 0;
	int noOfBranchedChains = 0;
	double avgMolWt = 0.0;
	double avgBranchFactor = 0.0;

	public double getAvgBranchFactor() {
		return avgBranchFactor;
	}

	public void setAvgBranchFactor(double avgBranchFactor) {
		this.avgBranchFactor = avgBranchFactor;
	}

	public double getAvgMolWt() {
		return avgMolWt;
	}

	public void setAvgMolWt(double avgMolWt) {
		this.avgMolWt = avgMolWt;
	}

	public int getNoOflinearChains() {
		return noOflinearChains;
	}

	public void setNoOflinearChains(int noOflinearChains) {
		this.noOflinearChains = noOflinearChains;
	}

	public int getNoOfBranchedChains() {
		return noOfBranchedChains;
	}

	public void setNoOfBranchedChains(int noOfBranchedChains) {
		this.noOfBranchedChains = noOfBranchedChains;
	}

	public String getMonoType() {
		return monoType;
	}

	public void setMonoType(String monoType) {
		this.monoType = monoType;
	}

	public double getS_G_Ratio() {
		return S_G_Ratio;
	}

	public void setS_G_Ratio(double s_G_Ratio) {
		S_G_Ratio = s_G_Ratio;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDp() {
		return dp;
	}

	public void setDp(int dp) {
		this.dp = dp;
	}

	public int getNunber_of_structs() {
		return nunber_of_structs;
	}

	public void setNunber_of_structs(int nunber_of_structs) {
		this.nunber_of_structs = nunber_of_structs;
	}

	public List<SingleStructDet> getLigninchains() {
		if (ligninchains == null)
			ligninchains = new ArrayList<SingleStructDet>();
		return ligninchains;
	}

	public void setLigninchains(List<SingleStructDet> ligninchains) {
		this.ligninchains = ligninchains;
	}
	


	public String createJSON(Object ligninDet) {

		ObjectMapper mapper = new ObjectMapper();
		// Converting the Object to JSONString
		String jsonString = null;
		try {

			boolean dirpresent = getDir("json");
			PrintWriter writer = new PrintWriter("output/json/LigninStructs_" + dp + ".json", "UTF-8");
			jsonString = mapper.writeValueAsString(ligninDet);
			writer.write(jsonString);
			writer.close();
			logger.info("Successfully Copied JSON Object to File...");
			logger.info(jsonString);

		} catch (JsonProcessingException e) {

			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonString;
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
