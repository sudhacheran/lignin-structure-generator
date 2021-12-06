## Lignin Structure Generator Tool
Molecular structure simulation tool for lignin macromolecule. Lignin structure generator uses sequence-controlled structure generation approach for generating polymer chains of length 3 to 25. Workflow includes following functionalities <br>

a) Sequencing of lignin monomers and conditional linkage generation <br>
b) Generating the topological matrices and directed graph representation <br>
c) Converting the directed graphs into molecular structures <br>
d) Validation the dataset against the experimental observations and simulated structures from previous studies <br>
e) Storing the valid structures forming a dataset <br>

![image](https://user-images.githubusercontent.com/18223595/129457099-80c1b9e9-1307-4820-a73a-af8472f923d2.png)


## Dependencies (to run the tool)
1) Lignin generator requires JRE 8 or newer version.
2) CDK Toolkit (https://cdk.github.io/)   (Integrated in Jar)

---

## Executable jar and config file for download

[![image](https://user-images.githubusercontent.com/18223595/129457605-f9f67df5-0d2c-4250-9800-2aaf96c7195f.png) Lignin structure (LGS) generator](https://github.com/sudhacheran/lignin-structure-generator/blob/7c250d7bde269b78df7e817dba895016616b9234/Executable_Jar_and_Config_file.zip)

### Configuration file : Configure the structural composition for generating the structure

#### **project-config.yaml**

| Properties                   | Values                | Comments                              |
| ---------------------------- |---------------------  | --------|
| Degree of polymerization(dp) |_dp: 0 <br> (or) <br> min_dp: 3<br>  max_dp: 25_. | Configure either single DP (or min & max dp for generating structures in a range of DP)|
| Bond proportion |   **# percentage(%) values** <br> _BO4: 61 <br>   BB: 12 <br>     B5: 3 <br>   4O5: 2 <br>  55: 1 <br>  DBDO: 1_. | Experimental observation from NMR or other analysis results (Add in the percentage (%) values) |
| Monomer proportion | _s_g_ratio: 1.8 <br>  (or) <br>  **percentage(%) values** <br>  G: 32 <br> S: 68 <br>  H: 0_. |  Use either S/G ratio or Monomer percentages. If both the values are given S/G ratio takes precedence. <br> - set s_g_ratio to -1 to use S/G/H percentage values <BR> - set s_g_ratio to 0 to generate G Type structures <Br> - set s_g_ratio as S/G ratio to generate SG type structures. Example : 1.8 |
|Output formats| **# true/false value** <br> _png: false     <br>    matrices: false   <br>    sdf: true    | Enabling to store individual structurual definition in any of the formats <br> # 2D structure <br> # Adjacency and Connectivity Matrix <br> # mol file <br> |
|Comprehensive dataset|**# true/false value** <br> _json: true_.  | Comprehensive dataset (SMILES and evaluatied properties of the stuctures generated) <br>  #To write json file
  
### Instruction to run the executable version 

1) Download Executable_Jar_and_Config_file.zip file to local path  	

2) Unzip the file
	
     _project-config.yaml should be placed within "resource" folder as downloaded from [Executable jar and config file](https://github.com/sudhacheran/LigninStructureGenerator/tree/main/Executable%20Jar%20and%20Config)_. 
      ![image](https://user-images.githubusercontent.com/18223595/129494159-cb8e0b77-ffb2-40f4-ab8c-f15387d4d933.png)
      
      ![image](https://user-images.githubusercontent.com/18223595/129085991-d36352f6-ae6c-4727-bfac-00cedf65a305.png)
3) Change lignin structural configurations in [project-config.yaml](https://github.com/sudhacheran/LigninStructureGenerator/blob/main/Executable%20Jar%20and%20Config/resources/project_config.yaml)

4) Set JAVA_HOME in Environment variables

5) Move to folder "Executable_Jar_and_Config_file" extracted in Step 2 and Execute the below jar file using below command

	> java -jar lgs_generator.jsr

Generated structures will be stored in 'output' folder in the location of the jar file

![image](https://user-images.githubusercontent.com/18223595/129085903-555e278c-86ba-489b-8102-e1b8061073e8.png)

  
---

# Illustrations

## G Type - Model-1
![image](https://user-images.githubusercontent.com/18223595/129457493-43b2dc47-0959-40ee-843c-ebf030a47cb5.png)


---


## SG Type - Model-1
![image](https://user-images.githubusercontent.com/18223595/129457501-d2d6d6f2-8695-4739-9e8e-e9ecd8f92262.png)


# Related resources
> Sudha Eswaran (2021) "Molecular structural dataset of lignin macromolecule elucidating experimental structural compositions "  Manuscript



   
   




