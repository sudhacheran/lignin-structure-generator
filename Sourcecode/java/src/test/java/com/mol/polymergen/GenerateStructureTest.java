package com.mol.polymergen;

import java.io.IOException;

import org.openscience.cdk.exception.CDKException;

import com.db.MongoDBClient;
import com.mol.pojo.Config;
import com.mol.util.yamlread;

import junit.framework.TestCase;

public class GenerateStructureTest extends TestCase {

	public final void testRunData() {
		GenerateStructure gs = new GenerateStructure();		
		
		try {
			Config prjConfig = yamlread.initializeConstants();
			int dp= 3;
			gs.runData(dp, prjConfig);
		} catch (IOException e) {
			fail("Exception :"+ e.getMessage());
		} catch (CDKException e) {
			fail("Exception :"+ e.getMessage());
		}
	}	
}
