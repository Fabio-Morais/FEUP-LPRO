package teste;

import java.util.HashMap;
import java.util.HashSet;

public class CombinatorialCircuit {
	HashMap<String, LogicVariable> hash = new HashMap<>();
	HashSet<LogicVariable> hash2 = new HashSet<>();
	
	public boolean addVariable(LogicVariable a) {
		if(!hash.containsKey(a.getName())) {
			hash.put(a.getName(), a);
			return true;
		}
			
		else
			return false;
		 
	}

	public LogicVariable getVariableByName(String string) {
		return hash.get(string);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hash == null) ? 0 : hash.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CombinatorialCircuit other = (CombinatorialCircuit) obj;
		if (hash == null) {
			if (other.hash != null)
				return false;
		} else if (!hash.equals(other.hash))
			return false;
		return true;
	}



}
