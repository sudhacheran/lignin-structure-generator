package com.mol.util;

import java.io.File;
import java.util.Arrays;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonAppend.Prop;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.mol.pojo.Config;
import com.mol.polymergen.GenerateStructure;

/**
 * yamlread: Fetch and configure structural features 
 * @author eswa765
 *
 */
public class yamlread {
	static Logger logger = Logger.getLogger(yamlread.class);

	public static Config initializeConstants() {
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		try {
			Config prop = mapper.readValue(new File("resources/project_config.yaml"), Config.class);
			//System.out.println(ReflectionToStringBuilder.toString(prop, ToStringStyle.MULTI_LINE_STYLE));
						
			if (prop.getS_g_ratio() != -1)
			{
				int S = Math.round(prop.getS_g_ratio() / (1 + prop.getS_g_ratio()) * 100);
				int G = Math.round(1 / (1 + prop.getS_g_ratio()) * 100);
				System.out.println(S+","+G);
				int mono[] = { G, S, 0 };
				Constants.monoPer = mono;
			}
			else
			{
				int S = prop.getMonoconfig().get("S");
				int G = prop.getMonoconfig().get("G");
				int H = prop.getMonoconfig().get("H");
				System.out.println("Here="+S+","+G);
				int mono[] = { G, S, H };
				Constants.monoPer = mono;
			}
			
			
			if (prop.getDp() !=0)
			{
				Constants.dp_units = prop.getDp();
				Constants.dprange = false;
			}
			else
			{
				Constants.min_dp = prop.getMin_dp();
				Constants.max_dp = prop.getMax_dp();
				Constants.dprange = true;
			}
			
			
			int bo4 = (int) prop.getBondconfig().get(Constants.BO4);
			int bb = (int) prop.getBondconfig().get(Constants.BB);
			int b5 = (int) prop.getBondconfig().get(Constants.B5);
			int bondper[] = { bo4, bb, b5 };
			Constants.bondper = bondper;
			int _405 = (int) prop.getBondconfig().get("_4O5");
			int _55 = (int) prop.getBondconfig().get("_55");
			int DBDO = (int) prop.getBondconfig().get(Constants.DBDO);
			int bondbranchper[] = { _405, _55, DBDO };
			Constants.branchingbndsper = bondbranchper;
			Constants.dp_units = prop.getDp();
			Constants.mongo_URI= prop.getMongo_URI();
			Constants.mongo_dbname=prop.getMongo_dbname();
			Constants.mongo_collname= prop.getMongo_collname();
			return prop;

		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		return null;

	}

	public static void main(String s[]) {
		initializeConstants();
	}

}
