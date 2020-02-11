package gs.sidartatech.safecheckout.domain;

import java.io.Serializable;

public class RoutineSituation implements Serializable {

	private static final long serialVersionUID = 1384656602545979137L;
	private Integer routineSituationId;
	private String routineSituationName;

	public RoutineSituation() {
	}

	public Integer getRoutineSituationId() {
		return this.routineSituationId;
	}

	public void setRoutineSituationId(Integer routineSituationId) {
		this.routineSituationId = routineSituationId;
	}

	public String getRoutineSituationName() {
		return this.routineSituationName;
	}

	public void setRoutineSituationName(String routineSituationName) {
		this.routineSituationName = routineSituationName;
	}
}