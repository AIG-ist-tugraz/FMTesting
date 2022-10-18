package at.tugraz.ist.ase.fmdiagnosis;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

public class DiagnosisLabelProvider implements ILabelProvider {

	@Override
	public void addListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean isLabelProperty(Object inputElement, String text) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Image getImage(Object inputElement) {
		DiagnosisTree.validateInput(inputElement);
		return null;
	}

	@Override
	public String getText(Object inputElement) {
		DiagnosisTree.validateInput(inputElement);
		return inputElement.toString();
	}

}
