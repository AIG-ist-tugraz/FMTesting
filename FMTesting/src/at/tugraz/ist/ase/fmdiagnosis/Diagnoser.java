package at.tugraz.ist.ase.fmdiagnosis;

import static at.tugraz.ist.ase.fmdiagnosis.debug.DebugInfo.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

import org.javatuples.Pair;

import at.tugraz.ist.ase.fm.builder.IFeatureBuildable;
import at.tugraz.ist.ase.fm.core.AbstractRelationship;
import at.tugraz.ist.ase.fm.core.CTConstraint;
import at.tugraz.ist.ase.fm.core.FeatureModel;
import at.tugraz.ist.ase.fm.parser.FMParserFactory;
import at.tugraz.ist.ase.fm.parser.FeatureModelParser;
import at.tugraz.ist.ase.fm.parser.FeatureModelParserException;
import at.tugraz.ist.ase.fma.FMAnalyzer;
import at.tugraz.ist.ase.fma.analysis.AbstractFMAnalysis;
import at.tugraz.ist.ase.fma.analysis.ConditionallyDeadAnalysis;
import at.tugraz.ist.ase.fma.analysis.DeadFeatureAnalysis;
import at.tugraz.ist.ase.fma.analysis.FalseOptionalAnalysis;
import at.tugraz.ist.ase.fma.analysis.FullMandatoryAnalysis;
import at.tugraz.ist.ase.fma.analysis.RedundancyAnalysis;
import at.tugraz.ist.ase.fma.analysis.VoidFMAnalysis;
import at.tugraz.ist.ase.fma.anomaly.AnomalyAwareFeature;
import at.tugraz.ist.ase.fma.anomaly.AnomalyAwareFeatureBuilder;
import at.tugraz.ist.ase.fma.anomaly.AnomalyType;
import at.tugraz.ist.ase.fma.explanation.AutomatedAnalysisExplanation;
import at.tugraz.ist.ase.fma.explanation.RawExplanation;
import at.tugraz.ist.ase.fmdiagnosis.debug.DebugInfo.State;
import de.ovgu.featureide.fm.core.base.IFeatureStructure;
import de.ovgu.featureide.fm.ui.editors.FeatureModelEditor;
import de.ovgu.featureide.fm.ui.editors.IFeatureModelEditorListener;
import lombok.Cleanup;

public class Diagnoser implements IFeatureModelEditorListener {
	private static FeatureModelEditor currentEditor;
	
	private static List<Pair<String, List<Pair<String, String>>>> latestDiagnosis;
	private static int latestNumFeatures;
	
	public static void diagnose() {
		if (currentEditor == null) {
			debugInfo(State.FAIL, "No model to analyze!");
			return;
		}
		
		debugInfo(State.INFO, "Diagnosis was started");
		latestNumFeatures = countFeatures(currentEditor.getOriginalFeatureModel().getStructure().getRoot()) + 1;
		
		try {
			latestDiagnosis = new ArrayList(Collections.emptyList());
			
			File fileFM = currentEditor.getModelFile().getLocation().toFile();
			
			// create the factory for anomaly feature models
			IFeatureBuildable featureBuilder = new AnomalyAwareFeatureBuilder();
			FMParserFactory<AnomalyAwareFeature, AbstractRelationship<AnomalyAwareFeature>, CTConstraint>
			factory = FMParserFactory.getInstance(featureBuilder);
			
			@Cleanup("dispose")
			FeatureModelParser<AnomalyAwareFeature, AbstractRelationship<AnomalyAwareFeature>, CTConstraint>
			parser = factory.getParser(fileFM.getName());
			FeatureModel<AnomalyAwareFeature, AbstractRelationship<AnomalyAwareFeature>, CTConstraint>
			featureModel = parser.parse(fileFM);
			
			// create an analyzer
			FMAnalyzer analyzer = new FMAnalyzer(featureModel);
			
			EnumSet<AnomalyType> options = EnumSet.allOf(AnomalyType.class);
			// generate analyses and run the analyzer
			analyzer.generateAndRun(options, true);
			
			// print the result
			RawExplanation explanation = new RawExplanation();
			for (AnomalyType option : options) {
	            Class<? extends AbstractFMAnalysis<?>> analysisClass =
	            switch(option) {
	                case VOID -> VoidFMAnalysis.class;
	                case DEAD -> DeadFeatureAnalysis.class;
	                case FULLMANDATORY -> FullMandatoryAnalysis.class;
	                case FALSEOPTIONAL -> FalseOptionalAnalysis.class;
	                case CONDITIONALLYDEAD -> ConditionallyDeadAnalysis.class;
	                case REDUNDANT -> RedundancyAnalysis.class;
	            };

	            Pair<String, List<Pair<String, String>>> explanations = explanation.getDescriptiveExplanation(analyzer.getAnalyses(), analysisClass, option);
	            latestDiagnosis.add(explanations);
	            
	            StringBuilder explain = new StringBuilder()
	                    .append("* ")
	                    .append(explanations.getValue0())
	                    .append("\n");
	            if (explanations.getValue1() == null) {
	                explain.append("[null]\n");
	            }
	            else {
	                for (Pair<String, String> pair : explanations.getValue1()) {
	                    explain.append(pair.getValue0())
	                            .append("\n")
	                            .append(pair.getValue1())
	                            .append("\n");
	                }
	            }
	            

	            System.out.println(explain);
			}
			debugInfo(State.INFO, "Diagnosis was performed successfully");
			
		}
		catch (FeatureModelParserException e) {
			debugError("Feature model could not be parsed");
			e.printStackTrace();
		} 
		catch (CloneNotSupportedException e) {
			debugError("Unable to clone!");
			e.printStackTrace();
		}
	}
	
	public static void diagnose(EnumSet<AnomalyType> options) {
		if (currentEditor == null) {
			debugInfo(State.FAIL, "No model to analyze!");
			return;
		}
		
		debugInfo(State.INFO, "Diagnosis was started");
		latestNumFeatures = countFeatures(currentEditor.getOriginalFeatureModel().getStructure().getRoot()) + 1;
		
		try {
			latestDiagnosis = new ArrayList(Collections.emptyList());
			
			File fileFM = currentEditor.getModelFile().getLocation().toFile();
			
			// create the factory for anomaly feature models
			IFeatureBuildable featureBuilder = new AnomalyAwareFeatureBuilder();
			FMParserFactory<AnomalyAwareFeature, AbstractRelationship<AnomalyAwareFeature>, CTConstraint>
			factory = FMParserFactory.getInstance(featureBuilder);
			
			@Cleanup("dispose")
			FeatureModelParser<AnomalyAwareFeature, AbstractRelationship<AnomalyAwareFeature>, CTConstraint>
			parser = factory.getParser(fileFM.getName());
			FeatureModel<AnomalyAwareFeature, AbstractRelationship<AnomalyAwareFeature>, CTConstraint>
			featureModel = parser.parse(fileFM);
			
			// create an analyzerimplements IFeatureModelEditorListener
			FMAnalyzer analyzer = new FMAnalyzer(featureModel);
			
			// generate analyses and run the analyzer
			analyzer.generateAndRun(options, true);
			
			// print the result
			RawExplanation explanation = new RawExplanation();
			for (AnomalyType option : options) {
	            Class<? extends AbstractFMAnalysis<?>> analysisClass =
	            switch(option) {
	                case VOID -> VoidFMAnalysis.class;
	                case DEAD -> DeadFeatureAnalysis.class;
	                case FULLMANDATORY -> FullMandatoryAnalysis.class;
	                case FALSEOPTIONAL -> FalseOptionalAnalysis.class;
	                case CONDITIONALLYDEAD -> ConditionallyDeadAnalysis.class;
	                case REDUNDANT -> RedundancyAnalysis.class;
	            };

	            Pair<String, List<Pair<String, String>>> explanations = explanation.getDescriptiveExplanation(analyzer.getAnalyses(), analysisClass, option);
	            latestDiagnosis.add(explanations);
	            
	            StringBuilder explain = new StringBuilder()
	                    .append("* ")
	                    .append(explanations.getValue0())
	                    .append("\n");
	            if (explanations.getValue1() == null) {
	                explain.append("[null]\n");
	            }
	            else {
	                for (Pair<String, String> pair : explanations.getValue1()) {
	                    explain.append(pair.getValue0())
	                            .append("\n")
	                            .append(pair.getValue1())
	                            .append("\n");
	                }
	            }
	            

	            System.out.println(explain);
			}
			debugInfo(State.INFO, "Diagnosis was performed successfully");
			
		}
		catch (FeatureModelParserException e) {
			debugError("Feature model could not be parsed");
			e.printStackTrace();
		} 
		catch (CloneNotSupportedException e) {
			debugError("Unable to clone!");
			e.printStackTrace();
		}
	}
	
	private static int countFeatures(IFeatureStructure root) {
		int count = root.getChildren().size();
		for (IFeatureStructure child : root.getChildren()) {
			count += countFeatures(child);
		}
		
		return count;
	}
	
	public static int getLatestNumFeatures() {
		return latestNumFeatures;
	}
	
	public static List<Pair<String, List<Pair<String, String>>>> getLatestDiagnosis() {
		return latestDiagnosis;
	}

	@Override
	public void editorOpened(FeatureModelEditor editor) {
		if (editor == null) {
			debugInfo(State.FAIL, "Opened editor was null!");
			return;
		}
		
		debugInfo(State.INFO, "New editor was opened: " + editor.getTitle());
		currentEditor = editor;
		debugInfo(State.INFO, "New editor was registered to the class");
		
	}

}
