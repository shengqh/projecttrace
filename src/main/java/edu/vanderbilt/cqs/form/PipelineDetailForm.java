package edu.vanderbilt.cqs.form;

import java.io.Serializable;

import edu.vanderbilt.cqs.bean.Pipeline;
import edu.vanderbilt.cqs.bean.PipelineTask;

public class PipelineDetailForm implements Serializable {

	private static final long serialVersionUID = 6628476060133253335L;

	private Boolean canEdit;

	private PipelineTask pipelineTask;
	
	private Pipeline pipeline;

	public Boolean getCanEdit() {
		return canEdit;
	}

	public void setCanEdit(Boolean canEdit) {
		this.canEdit = canEdit;
	}

	public Pipeline getPipeline() {
		return pipeline;
	}

	public void setPipeline(Pipeline pipeline) {
		this.pipeline = pipeline;
	}

	public PipelineTask getPipelineTask() {
		return pipelineTask;
	}

	public void setPipelineTask(PipelineTask pipelineTask) {
		this.pipelineTask = pipelineTask;
	}

}
