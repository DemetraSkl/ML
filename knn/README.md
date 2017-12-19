# A kNN implementation

## Installing

To build the source code I used ant. All tasks are defined in the build.xml file. The main code routine is in Main.java

Each dataset has been split into training and test arff files i.e. <dataset_name>-train.arff and <dataset_name>-test.arff. 

You can then run "ant" within the root directory (where the build.xml file is saved) and the code will:

1) Print out results for all experiments and dataset on the console.

2) Generate a csv file with the results in the data directory specified above.

The output naming scheme is:

* for accuracy when varying number of neighbors: 
<dataset_name>-knn.csv
* for accuracy when varying number of features taken into account:
<dataset_name>-knn-feature-selection.csv

You will also need to include the weka.jar file in a lib folder
within the root directory of this project. 

## Author 

Demetra Skl

## Acknowledgements

Data for these experiments has been obtained from [UCI's machine learning repository](https://archive.ics.uci.edu/ml/index.php).