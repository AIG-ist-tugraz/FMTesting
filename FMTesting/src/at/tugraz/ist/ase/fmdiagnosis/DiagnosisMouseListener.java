package at.tugraz.ist.ase.fmdiagnosis;

import static at.tugraz.ist.ase.fmdiagnosis.debug.DebugInfo.debugInfo;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;

import at.tugraz.ist.ase.fmdiagnosis.debug.DebugInfo.State;

public class DiagnosisMouseListener implements MouseListener {
	private final DiagnosisView owner;
	
	public DiagnosisMouseListener(DiagnosisView owner) {
		super();
		this.owner = owner;
	}

	@Override
	public void mouseDoubleClick(MouseEvent ev) {
		debugInfo(State.INFO, "MouseEvent occured: DoubleClick");				
	}

	@Override
	public void mouseDown(MouseEvent ev) {
		debugInfo(State.INFO ,"MouseEvent occured: Down");
	}

	@Override
	public void mouseUp(MouseEvent ev) {
		debugInfo(State.INFO, "MouseEvent occured: Up");
		
//		AnalysisDialog ad = new AnalysisDialog(parent.getShell());
//		ad.open();
		try {
			Diagnoser.diagnose(owner.getChosenAnalysisTypes());
			owner.setDiagnosis();			
		}
		catch (UnsupportedOperationException ex) {
			MessageDialog.openError(owner.getParentShell(), "Error", ex.getMessage());
		}
	}

}
