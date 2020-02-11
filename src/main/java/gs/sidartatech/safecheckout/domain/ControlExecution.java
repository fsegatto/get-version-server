package gs.sidartatech.safecheckout.domain;

import java.io.Serializable;
import java.util.Date;

public class ControlExecution implements Serializable {

	private static final long serialVersionUID = -3127650672813302639L;
	private Integer controlExecutionId;
	private Routine routine;
	private RoutineSituation routineSituation;
	private Date startTime;
	private Date endTime;
	private String description;

	public ControlExecution() {
	}

	public Integer getControlExecutionId() {
		return this.controlExecutionId;
	}

	public void setControlExecutionId(Integer controlExecutionId) {
		this.controlExecutionId = controlExecutionId;
	}

	public Routine getRoutine() {
		return routine;
	}

	public void setRoutine(Routine routine) {
		this.routine = routine;
	}

	public RoutineSituation getRoutineSituation() {
		return routineSituation;
	}

	public void setRoutineSituation(RoutineSituation routineSituation) {
		this.routineSituation = routineSituation;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}