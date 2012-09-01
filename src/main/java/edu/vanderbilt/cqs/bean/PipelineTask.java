package edu.vanderbilt.cqs.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PIPELINETASK")
public class PipelineTask implements ITask, Serializable {

	private static final long serialVersionUID = -7027971400364281832L;

	@Id
	@Column(name = "ID")
	@GeneratedValue
	private Integer id;

	@Column(name = "NAME")
	private String name;

	@Column(name = "TASK_INDEX")
	private Integer taskIndex;

	@Column(name = "PEOPLE_TIME")
	private Double peopleTime;

	@Column(name = "MACHINE_TIME")
	private Double machineTime;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PIPELINE_ID")
	private Pipeline pipeline;

	public PipelineTask() {
	}

	public PipelineTask(String aName, int aTaskIndex, double aPeopleTime,
			double aMachineTime) {
		this.name = aName;
		this.taskIndex = aTaskIndex;
		this.peopleTime = aPeopleTime;
		this.machineTime = aMachineTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getTaskIndex() {
		return taskIndex;
	}

	public void setTaskIndex(Integer taskIndex) {
		this.taskIndex = taskIndex;
	}

	public Double getPeopleTime() {
		return peopleTime;
	}

	public void setPeopleTime(Double peopleTime) {
		this.peopleTime = peopleTime;
	}

	public Double getMachineTime() {
		return machineTime;
	}

	public void setMachineTime(Double machineTime) {
		this.machineTime = machineTime;
	}

	public Pipeline getPipeline() {
		return pipeline;
	}

	public void setPipeline(Pipeline pipe) {
		this.pipeline = pipe;
	}
}
