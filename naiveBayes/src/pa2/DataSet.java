package pa2;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;

public class DataSet {
	HashSet<Document> documents;
	HashSet<Document> yesDocuments; // text files with yes label 
	HashSet<Document> noDocuments; // text files with no label
	HashSet<String> yesVocabulary; // vocabulary of yes label text files
	HashSet<String> noVocabulary; // vocabulary of no label text files
	HashSet<String> vocabulary; // complete vobulary 
	int numDocsYes = 0;
	int numDocsNo = 0;
	int wordCountYes = 0;
	int wordCountNo = 0;
	int totalVocabSize = 0;
	int numDocs = 0; // Total number of documents to find prior probability
	long nlines = 0;

	public DataSet(String filename, long lines, String dataDirectoryPrefix) {
		ArrayList<Document> docList = new ArrayList<Document>();
		ArrayList<Document> yesDocList = new ArrayList<Document>();
		ArrayList<Document> noDocList = new ArrayList<Document>();
		ArrayList<String> yesVocabList = new ArrayList<String>();
		ArrayList<String> noVocabList = new ArrayList<String>();
		ArrayList<String> vocabList = new ArrayList<String>();
		long lineAccumulator = -1;
		this.nlines = lines;
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String line;
			while ((line = br.readLine()) != null) {
				lineAccumulator++;
				if (lineAccumulator == nlines) {
					break;
				}
				int index = line.indexOf("|");
				int lastindex = line.length() - 1;
				String docname = line.substring(0, index);
				String doclabel = line.substring(index + 1, lastindex);
				Document doc = new Document(docname, doclabel, dataDirectoryPrefix);
				Set<String> docVocabulary = new HashSet<String>(Arrays.asList(doc.vocabulary));

				if (doc.label.equals("yes")) {
					yesVocabList.addAll(docVocabulary);
					yesDocList.add(doc);
					this.wordCountYes += doc.features.length;
				} else {
					noVocabList.addAll(docVocabulary);
					noDocList.add(doc);
					this.wordCountNo += doc.features.length;
				}
				vocabList.addAll(docVocabulary);
				docList.add(doc);
			
			}
			this.yesVocabulary = new HashSet<String>(yesVocabList);
			this.noVocabulary = new HashSet<String>(noVocabList);
			this.vocabulary = new HashSet<String>(vocabList);
			this.documents = new HashSet<Document>(docList);
			this.yesDocuments = new HashSet<Document>(yesDocList);
			this.noDocuments = new HashSet<Document>(noDocList);

			this.numDocsYes = this.yesDocuments.size();
			this.numDocsNo = this.noDocuments.size();
			this.numDocs = this.documents.size();
			this.totalVocabSize = this.vocabulary.size();


		} catch (Exception e) {
			Utils.error(e.getMessage());
		}
	}
	// Returns prior probability of class label based on the training data labels
	public double getPriorProbability(String label){
		if(label.equals("yes")){
			return (double) this.numDocsYes/this.numDocs;
		}
		else{
			return (double) this.numDocsNo/this.numDocs;
		}
	}
	// Returns (number of words in class c that are word w + smoothing m)/(number of word tokens in class c + m*Vocabulary size)
	public double getConditionalProbabilityType1(String word, String label, double m){
		double numerator = getWordCountInLabel(word, label)+m;

		if(label.equals("yes")){

			return numerator/(this.wordCountYes+m*this.totalVocabSize);
		} else{
			return numerator/(this.wordCountNo+m*this.totalVocabSize);
		}
	}
	// Returns (number of documents in class c that contain word w + m)/(number of documents in class c +2*m)
	public double getConditionalProbabilityType2(String word, String label, double m){
		int v = 2;
		double numerator = getDocCountinLabel(word, label)+m;

		if(label.equals("yes")){

			return numerator/(this.numDocsYes+m*v);
		} else{
			return numerator/(this.numDocsNo+m*v);
		}
	}


	private int getWordCountInDocument(String word, Document document) {
		if (!isWordinDoc(word,document)) {
			return 0;
		}
		return document.featureCount.get(word);
	}
	// Word count in word instances of class c 
	public int getWordCountInLabel(String word, String label) {
		int wordCount = 0;
		if (label.equals("yes")) {

			for (Document document : this.yesDocuments) {
				wordCount += getWordCountInDocument(word, document);
			}
		} else {
			for (Document document : this.noDocuments) {
				wordCount += getWordCountInDocument(word, document);
			}
		}
		return wordCount;
	}


	public boolean isWordinDoc(String word, Document document){
		if (document.featureCount.get(word) == null) {
			return false;
		}
		return true;
	}
	//Document count of word in label
	public int getDocCountinLabel(String word, String label){
	int docCount = 0;
		if (label.equals("yes")) {

			for (Document document : this.yesDocuments) {
				if (isWordinDoc(word,document)){
					docCount +=1;
				}
			}
		} else {
			for (Document document : this.noDocuments) {
				if (isWordinDoc(word,document)){
					docCount +=1;
				}
			}
		}
		return docCount;
	}
}
