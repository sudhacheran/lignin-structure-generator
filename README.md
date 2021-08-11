# Lignin Structure Generator
Molecular structure simulation tool for lignin macromolecule. Lignin structure generator uses sequence-controlled structure generation approach. Workflow includes folloing functionalities <br>

a) Sequencing of lignin monomers and conditional linkage generation <br>
b) Generating the topological matrices and molecular graph as directed graph representation <br>
c) Converting the directed graphs into molecular structures <br>
d) Validation the dataset against the experimental observations and simulated structures from previous studies <br>
e) Storing the valid structures forming a dataset <br>

![image](https://user-images.githubusercontent.com/18223595/129066004-aba60238-de43-41b0-b802-9f9518cd94c2.png)




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

[x] Degree of polymerization(dp): Configure either single DP (or min & max dp for generating structures in a range of DP).
dp: 0
(or)
min_dp: 3
max_dp: 25

[x] Bond proportion: Experimental observation from NMR or other analysis results (Add in the percentage (%) values)
bondconfig:
  BO4: 61
  BB: 12
  B5: 3
  _4O5: 2
  _55: 1
  DBDO: 1

[X] Monomer proportion: Use either S/G ratio or Monomer percentages. If both the values are given S/G ratio takes precedence
s_g_ratio: 1.8      # set to -1 to use S/G/H percentage values below
(or)
monoconfig:  # percentage(%) values
  G: 32
  S: 68
  H: 0

[X] Output formats:  Stored as individual structurual definition
png: false   # 2D structure  
matrices: false   # Adjacency and Connectivity Matrix
sdf: true   # *.mol or SDF file
cml: false  # CML from CDK tool

[X] Json contains SMILES and evaluatied properties of the stuctures generated
json: true

---

## Generated Structures


   
   




