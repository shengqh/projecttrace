package edu.vanderbilt.cqs.form;

import java.io.Serializable;
import java.util.List;

import edu.vanderbilt.cqs.bean.Pipeline;

public class PipelineForm implements Serializable {

	private static final long serialVersionUID = -457316539147199488L;

	private Boolean canEdit;

	private Pipeline pipeline;

	private List<Pipeline> pipelineList;

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

	public List<Pipeline> getPipelineList() {
		return pipelineList;
	}

	public void setPipelineList(List<Pipeline> pipelineList) {
		this.pipelineList = pipelineList;
	}

}
