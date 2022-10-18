package at.tugraz.ist.ase.fmdiagnosis.debug;

import lombok.Setter;

public abstract class DebugInfo {
	public enum Mode {
		SILENT,
		ERROR,
		VERBOSE;
	}
	
	@Setter
	private static Mode mode = Mode.VERBOSE;
	
	public enum State {
		INFO,
		FAIL;
	}
	
	public static void debugInfo(State state, String message) {
		if (mode.ordinal() >= Mode.VERBOSE.ordinal()) {
			StringBuilder sb = new StringBuilder()
					.append("[" + state.toString() + "] ")
					.append(message);
			
			System.out.println(sb.toString());
		}
	}
	
	public static void debugError(String message) {
		if (mode.ordinal() >= Mode.ERROR.ordinal()) {
			StringBuilder sb = new StringBuilder()
					.append("[ERR] ")
					.append(message);
			
			System.err.println(sb.toString());			
		}
	}

}
