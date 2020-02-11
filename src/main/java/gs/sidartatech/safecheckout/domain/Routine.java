package gs.sidartatech.safecheckout.domain;

import java.io.Serializable;

public class Routine implements Serializable {

	private static final long serialVersionUID = -2523262373022958967L;
	private Integer routineId;
	private String routineName;
	private String frequency;

	public Routine() {
	}

	public Integer getRoutineId() {
		return this.routineId;
	}

	public void setRoutineId(Integer routineId) {
		this.routineId = routineId;
	}

	public String getRoutineName() {
		return routineName;
	}

	public void setRoutineName(String routineName) {
		this.routineName = routineName;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
}