package teste;

public class LogicVariable{
	private String name;
	private boolean value;
	private LogicGate calculatedBy;
	private String formula;
	private boolean logicAnterior;
	private boolean input;
	
	public LogicVariable(String name, boolean bool) {
		this.name = name;
		this.value = bool;
		formula=returnFormula(name);
	}
	private String returnFormula (String name) {
		String formula="";
	
			try {
				formula=calculatedBy.getFormula();
			}catch(NullPointerException e) {
				formula=name;
			}
		
		return formula;
	}
	
	public boolean isInput() {
		return input;
	}
	public void setInput(boolean input) {
		this.input = input;
	}
	public LogicVariable(String name) {
		this.name = name;
		this.value = false;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean getValue() {
		try {
			x(calculatedBy);
		}catch(NullPointerException e) {
			
		}
		return value;
	}
	private void x(LogicGate var) {
		if(var == null)
			return;
		
		x(var.inputs[0].calculatedBy);

		if(var.symbol.equals("AND")) {
			var.getOutput().setValue((var.inputs[0].getValue() && var.inputs[1].getValue()));
		}else if(var.symbol.equals("OR")) {
			var.getOutput().setValue((var.inputs[0].getValue() || var.inputs[1].getValue()));
		}else if (var.symbol.equals("NOT")){
			var.getOutput().setValue((!var.inputs[0].getValue()));
		}
		
	}
	public void setValue(boolean value) {
		this.value = value;
	}
	
	public LogicGate getCalculatedBy() {
		return calculatedBy;
	}
	
	public void setCalculatedBy(LogicGate calculatedBy) {
		this.calculatedBy = calculatedBy;
	}
	 
	public String getFormula() {
		return formula;
	}
	public void setFormula(String formula) {
		this.formula = formula;
	}
	
	public boolean isLogicAnterior() {
		return logicAnterior;
	}
	public void setLogicAnterior(boolean logicAnterior) {
		this.logicAnterior = logicAnterior;
	}
	@Override
	public boolean equals(Object obj) {
		return  obj instanceof LogicVariable && this.name.equals(((LogicVariable)obj).getName());
	}

	
}
