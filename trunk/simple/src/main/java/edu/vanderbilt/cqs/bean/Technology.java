package edu.vanderbilt.cqs.bean;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "TECHNOLOGY")
public class Technology implements Serializable {
	private static final long serialVersionUID = 1031074673166990111L;

	@Id
	@GeneratedValue
	@Column(name="ID")
	private Long id;

	@NotEmpty
	@Column(name="NAME")
	private String name;

	@Column(name="DESCRIPTION")
	@Lob
	private String description;
	
	@Column(name="ENABLED")
	private Boolean enabled = true;
	
	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	@OneToMany(mappedBy = "technology", fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	private List<Platform> platforms;
	
	@OneToMany(mappedBy = "technology", fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	@OrderBy("moduleIndex")
	private List<Module> modules;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Platform> getPlatforms() {
		return platforms;
	}

	public void setPlatforms(List<Platform> platforms) {
		this.platforms = platforms;
	}

	public List<Module> getModules() {
		return modules;
	}

	public void setModules(List<Module> modules) {
		this.modules = modules;
	}
	
	public int getNextModuleIndex(){
		int result = 1;
		for(int i = 0;i < this.getModules().size();i++){
			int mindex = this.getModules().get(i).getModuleIndex();
			if(mindex >= result){
				result = mindex + 1;
			}
		}
		return result;
	}
}
