package at.tugraz.ist.ase.fmdiagnosis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.javatuples.Pair;

import at.tugraz.ist.ase.fmdiagnosis.DiagnosisTree.TextualAnomaly;
import at.tugraz.ist.ase.fmdiagnosis.DiagnosisTree.TextualDiagnosis;
import at.tugraz.ist.ase.fmdiagnosis.DiagnosisTree.TextualExplanation;

public class DiagnosisTree {
	private List<TextualAnomaly> anomalies;
	
	public DiagnosisTree(List<Pair<String, List<Pair<String, String>>>> input) {
		this.anomalies = new ArrayList<>();
		
		if (input == null) {
			anomalies = Collections.emptyList();
		}
		else {
			for (Pair<String, List<Pair<String, String>>> explanation : input) {
				if (explanation.getValue1() == null) {
					anomalies.add(new TextualAnomaly(explanation.getValue0(), Collections.emptyList()));
				}
				else {
					anomalies.add(new TextualAnomaly(explanation.getValue0(), explanation.getValue1()));
				}
			}			
		}	
	}
	
	public class TextualAnomaly {
		private String text;
		private List<TextualExplanation> explanations;
		
		public TextualAnomaly(String text, List<Pair<String, String>> explanationCandidates) {
			this.text = text;
			this.explanations = new ArrayList<>();
			
			for (Pair<String, String> explanation : explanationCandidates) {
				explanations.add(new TextualExplanation(explanation.getValue0(), explanation.getValue1(), this));
			}
		}
		
		public List<TextualExplanation> getExplanations() {
			return explanations;
		}
		
		public String toString() {
			return text;
		}
	}
	
	public class TextualExplanation {
		private String text;
		private List<TextualDiagnosis> diagnoses;
		private TextualAnomaly parent;
		
		public TextualExplanation(String text, String diagnosisCandidates, TextualAnomaly parent) {
			this.text = text;
			this.diagnoses = new ArrayList<>();
			this.parent = parent;
			
			String[] splitDiagnosis = diagnosisCandidates.split("\n");
			for(String diagnosis : splitDiagnosis) {
				diagnoses.add(new TextualDiagnosis(diagnosis, this));
			}
		}
		
		public List<TextualDiagnosis> getDiagnoses() {
			return diagnoses;
		}
		
		public TextualAnomaly getParent() {
			return parent;
		}
		
		public String toString() {
			return text;
		}
	}
	
	public class TextualDiagnosis {
		private String text;
		private TextualExplanation parent;
		
		public TextualDiagnosis(String text, TextualExplanation parent) {
			this.text = text;
			this.parent = parent;
		}
		
		public TextualExplanation getParent() {
			return parent;
		}
		
		public String toString() {
			return text;
		}
	}
	
	public List<TextualAnomaly> getAnomalies() {
		return anomalies;
	}
	
	public static void validateInput(Object input) {
		if(!(input instanceof TextualAnomaly) &&
		   !(input instanceof TextualExplanation) &&
		   !(input instanceof TextualDiagnosis) &&
		   !(input instanceof DiagnosisTree)) {
			throw new IllegalArgumentException("The tree view has received an invalid Object!");
		}
	}
	
	@Override
	public String toString() {
		return "DIAGNOSIS";
	}
}
