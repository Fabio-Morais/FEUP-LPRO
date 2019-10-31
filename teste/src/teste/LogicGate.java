package teste;

abstract public class LogicGate {
	LogicVariable output;
	LogicVariable[] inputs;
	String symbol;
	private String formula;

	public LogicGate(LogicVariable x1, LogicVariable x2, LogicVariable x3) {
		this.output = x1;
		inputs = new LogicVariable[2];
		inputs[0] = x2;
		inputs[1] = x3;


	}
	public LogicGate(LogicVariable x1, LogicVariable x2) {
		this.output = x1;
		inputs = new LogicVariable[1];
		inputs[0] = x2;

	}

	public LogicVariable getOutput() {
		return output;
	}

	public void setOutput(LogicVariable output) {
		this.output = output;
	}
	public LogicVariable[] getInputs() {
		return inputs;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getFormula() {
		return formula;
	}
	public void setFormula(String formula) {
		this.formula = formula;
	}



}
