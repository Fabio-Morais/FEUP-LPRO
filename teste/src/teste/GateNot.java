package teste;

public class GateNot extends LogicGate{
	public GateNot(LogicVariable y1, LogicVariable x2) throws ColisionException, CycleException {
		super(y1,x2);
		System.out.println(y1.getName());
		if(!x2.isInput()) {
			x2.setInput(true);
		}
		if(y1.isInput()) {
			System.out.println("entrou na exception");
			throw new CycleException();
		}
		System.out.println("depois do if");

		y1.setCalculatedBy(this);
		System.out.println("lol");
		y1.getCalculatedBy().setSymbol("NOT");
		y1.setFormula(returnString(y1, x2));
		setFormula(y1.getFormula());
		if(!y1.isLogicAnterior()) {
			y1.setLogicAnterior(true);
		}else {
			throw new ColisionException();
		}
		if(x2.getValue())
			y1.setValue(false);
		else
			y1.setValue(true);
		if(x2.getValue())
			y1.setValue(true);
		else
			y1.setValue(false);
		
		
	}
	
	private String returnString(LogicVariable y1, LogicVariable x2) {
		String formula="";
		try {
			formula="NOT("+x2.getCalculatedBy().getFormula()+")";
		}catch(NullPointerException e) {
			formula="NOT("+x2.getName()+")";
		}
		return formula;
	}
	
}
