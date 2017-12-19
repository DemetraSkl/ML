# Naive Bayes implementation for text categorization

Two differerent implementations of naive Bayes are used to assess whether a set articles pertains to mac or pc and whether another set of articles pertains to baseball or hockey. In the first implementation each document has as many features as its number of words and each feature can be any word within the vocabulary (i.e. words seen in training files). In the second implementation the number of features is the number of words in the vocabulary and each feature is binary (i.e. 1 if a word appears in the document, otherwise 0). 

## Installing

To build the source code I used ant. All tasks are defined in the build.xml file. The main code routine is in Main.java

To get the results for all experiments simply run ant.

## Author

Demetra Skl

## Acknowledgements
Data for these experiments has been obtained from an online [source](http://qwone.com/~jason/20Newsgroups/).