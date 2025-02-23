## Lignin Structure(LGS) Generator Tool
Molecular structure simulation tool for lignin macromolecule. Lignin structure generator uses sequence-controlled structure generation approach for generating polymer chains of length 3 to 25. Workflow includes following functionalities <br>

a) Sequencing of lignin monomers and conditional linkage generation <br>
b) Generating the topological matrices and directed graph representation <br>
c) Converting the directed graphs into molecular structures <br>
d) Validation the dataset against the experimental observations and simulated structures from previous studies <br>
e) Storing the valid structures forming a dataset <br>

![image](https://user-images.githubusercontent.com/18223595/129457099-80c1b9e9-1307-4820-a73a-af8472f923d2.png)


## Dependencies (to run the tool)
1) Lignin generator requires JDK 8 or newer version.
2) CDK Toolkit (https://cdk.github.io/)   (Integrated in Jar)

---

## Executable jar and config file for download

[![image](https://user-images.githubusercontent.com/18223595/129457605-f9f67df5-0d2c-4250-9800-2aaf96c7195f.png) Lignin structure (LGS) generator](https://github.com/sudhacheran/lignin-structure-generator/blob/0d88cf08d85971118cb98ac52b1b2513779077b3/Executable_Jar_and_Config.zip)

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
	
     **project-config.yaml** will be available within "resource" folder as downloaded from Executable jar and config file. 
     
	![image](https://user-images.githubusercontent.com/18223595/146465932-091e7c36-272f-4a69-b551-6548fcd27464.png)
	
3) Edit the lignin structural configurations in **project-config.yaml**

4) Set JAVA_HOME in Environment variables

5) In Windows, execute **"lgs-run.bat"**
	
   In Linux/Unix and Mac OS, execute **"./lgs-run.sh"**

	Example: Executing from Window's command prompt
	
	![image](https://user-images.githubusercontent.com/18223595/146468231-56cf3919-fb42-4c9c-bd21-460dd9faad3c.png)
	
Generated structural data are stored as JSON / Matrices(*.csv) / MOL V3000(*.mol) files with respective folder names in 'output' folder 

![image](https://user-images.githubusercontent.com/18223595/146466117-87bc92b1-5ecb-4917-8bd3-f036bed92b12.png)
	
---
	
# Illustrations

## G Type - Model-1
![image](https://user-images.githubusercontent.com/18223595/129457493-43b2dc47-0959-40ee-843c-ebf030a47cb5.png)


---


## SG Type - Model-1
![image](https://user-images.githubusercontent.com/18223595/129457501-d2d6d6f2-8695-4739-9e8e-e9ecd8f92262.png)


# Related resources
> Eswaran, S. c. d., Subramaniam, S., Sanyal, U., Rallo, R. & Zhang, X. Molecular structural dataset of lignin macromolecule elucidating experimental structural compositions. Scientific Data 9, 647, doi:10.1038/s41597-022-01709-4 (2022)



   
   




