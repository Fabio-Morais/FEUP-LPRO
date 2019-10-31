package teste;

public class GateOr extends LogicGate {

	public GateOr(LogicVariable y1, LogicVariable x2, LogicVariable x3) throws ColisionException, CycleException {
		super(y1,x2,x3);
		y1.setCalculatedBy(this);
		y1.getCalculatedBy().setSymbol("OR");
		y1.setFormula(returnString(y1,x2,x3));
		setFormula(y1.getFormula());
		if(!y1.isLogicAnterior()) {
			y1.setLogicAnterior(true);
		}else {
			throw new ColisionException();
		}
		if(x2.getValue() || x3.getValue())
			y1.setValue(true);
		else
			y1.setValue(false);
		
		if(x2.getValue() && x3.getValue())
			y1.setValue(true);
		else
			y1.setValue(false);
		
		
		
		if(!x2.isInput()) {
			x2.setInput(true);
		}
		if(!x3.isInput()) {
			x3.setInput(true);
		}
		if(y1.isInput()) {
			throw new CycleException();
		}
	}
	private String returnString(LogicVariable y1, LogicVariable x2, LogicVariable x3) {
		String formula="";
		try {
			formula="OR("+x2.getCalculatedBy().getFormula()+","+x3.getName()+")";
		}catch(NullPointerException e) {
			formula="OR("+x2.getName()+","+x3.getName()+")";
		}
		return formula;
	}
}
