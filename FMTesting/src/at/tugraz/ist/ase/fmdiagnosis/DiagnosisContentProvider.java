package at.tugraz.ist.ase.fmdiagnosis;

import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;

import at.tugraz.ist.ase.fmdiagnosis.DiagnosisTree;
import at.tugraz.ist.ase.fmdiagnosis.DiagnosisTree.TextualAnomaly;
import at.tugraz.ist.ase.fmdiagnosis.DiagnosisTree.TextualDiagnosis;
import at.tugraz.ist.ase.fmdiagnosis.DiagnosisTree.TextualExplanation;

public class DiagnosisContentProvider implements ITreeContentProvider {

	@Override
	public Object[] getChildren(Object parent) {
		DiagnosisTree.validateInput(parent);
		if (parent instanceof TextualAnomaly) {
			List<TextualExplanation> sourceList = ((TextualAnomaly) parent).getExplanations();
			return sourceList.toArray();
		}
		if (parent instanceof TextualExplanation) {
			List<TextualDiagnosis> sourceList = ((TextualExplanation) parent).getDiagnoses();
			return sourceList.toArray();
		}
		return ArrayUtils.EMPTY_OBJECT_ARRAY;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		if (!(inputElement instanceof DiagnosisTree)) {
			throw new RuntimeException("The root must be a DiagnosisTree!");
		}
		return ((DiagnosisTree) inputElement).getAnomalies().toArray();
	}

	@Override
	public Object getParent(Object element) {
		DiagnosisTree.validateInput(element);
		if (element instanceof TextualExplanation) return ((TextualExplanation) element).getParent();
		if (element instanceof TextualDiagnosis) return ((TextualDiagnosis) element).getParent();
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		DiagnosisTree.validateInput(element);
		if (element instanceof TextualDiagnosis) return false;
		return true;
	}
}
