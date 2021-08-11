# Lignin Structure Generator
Molecular structure simulation tool for lignin macromolecule. Lignin structure generator uses sequence-controlled structure generation approach. Workflow includes folloing functionalities <br>

a) Sequencing of lignin monomers and conditional linkage generation <br>
b) Generating the topological matrices and directed graph representation <br>
c) Converting the directed graphs into molecular structures <br>
d) Validation the dataset against the experimental observations and simulated structures from previous studies <br>
e) Storing the valid structures forming a dataset <br>

![image](https://user-images.githubusercontent.com/18223595/129077431-3b68a856-5e16-41ce-b3ae-70b5b04cbebc.png)


## Dependencies (to run the tool)
1) Lignin generator requires JRE 8 or newer version.
2) CDK Toolkit (https://cdk.github.io/)   (Integrated in Jar)

---

## Executable version for download
Link to the jar file
*.bat file for windows

---

### Configuring structural features for Lignin generator
project-config.yaml

| Properties                   | Values                | Comments                              |
| ---------------------------- |---------------------  | --------|
| Degree of polymerization(dp) |_dp: 0 <br> (or) <br> min_dp: 3<br>  max_dp: 25_. | Configure either single DP (or min & max dp for generating structures in a range of DP)|
| Bond proportion |   **# percentage(%) values** <br> _BO4: 61 <br>   BB: 12 <br>     B5: 3 <br>   4O5: 2 <br>  55: 1 <br>  DBDO: 1_. | Experimental observation from NMR or other analysis results (Add in the percentage (%) values) |
| Monomer proportion | _s_g_ratio: 1.8 <br>  (or) <br>  **percentage(%) values** <br>  G: 32 <br> S: 68 <br>  H: 0_. |  Use either S/G ratio or Monomer percentages. If both the values are given S/G ratio takes precedence. <br> -set s_g_ratio to -1 to use S/G/H percentage values |
|Output formats| **# true/false value** <br> _png: false     <br>    matrices: false   <br>    sdf: true   <br>    cml: false_. | Enabling to store individual structurual definition in any of the formats <br> # 2D structure <br> # Adjacency and Connectivity Matrix <br> # mol file <br> # CML from CDK tool|
|Comprehensive dataset|**# true/false value** <br> _json: true_.  | Comprehensive dataset (SMILES and evaluatied properties of the stuctures generated) <br>  #To write json file
  
---

## Generated Structures


   
   




