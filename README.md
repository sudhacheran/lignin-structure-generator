# Lignin Structure Generator
Molecular structure simulation tool for lignin macromolecule. Lignin structure generator uses sequence-controlled structure generation approach. Workflow includes folloing functionalities <br>
a) Sequencing of lignin monomers and conditional linkage generation <br>
b) Generating the topological matrices and molecular graph as directed graph representation <br>
c) Converting the directed graphs into molecular structures <br>
d) Validation the dataset against the experimental observations and simulated structures from previous studies <br>
e) Storing the valid structures forming a dataset <br>



# Dependencies (to run the tool)
1) Lignin generator requires JRE 8 or newer version.
2) CDK Toolkit (https://cdk.github.io/) 

# Executable version for download
Link to the jar file
*.bat file for windows

# Configuring structural features for Lignin generator
# Configurable properties
---

## Configure either single DP (or min & max dp for generating structures in a range of DP.
# Default Degree of polymerization(dp)
dp: 0

# To generate a range of DPs
min_dp: 3
max_dp: 25

# Experimental observation from NMR or other analysis on Bond precentages
# Add in the percentage (%) values
bondconfig:
  BO4: 55
  BB: 12
  B5: 3
  _4O5: 5
  _55: 2
  DBDO: 2

### Please use either of S/G ratio or Monomer percentages. If both the values are given S/G ratio takes precedence

## S/G ratio
s_g_ratio: 1.78

# Experimental observation from NMR or other analysis on Monomer precentages
# Add in the percentage(%) values
monoconfig:
  G: 32
  S: 68
  H: 0

# For individual lignin chains
png: true   # 2D structure  
matrices: true   # Adjacency and Connectivity Matrix
sdf: true   # *.mol or SDF file
cml: false  # CML from CDK tool

# Json contains SMILES and evaluatied properties of the stuctures generated
json: true


# To update in MongoDBDatabase (optional)
#addtoDB: false  #true, if mongodb instance is present
mongo_URI: 'mongodb://localhost:27017/admin?readPreference=primary&appname=MongoDB%20Compass&ssl=false'
mongo_dbname: 'lignindata'
mongo_collname: 'ligninstructs4'


  




# Generated Structures


   
   




