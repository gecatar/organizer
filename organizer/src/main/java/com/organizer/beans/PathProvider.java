package com.organizer.beans;

import javax.faces.bean.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped
public class PathProvider {

	public String getImageSourcePath(String id) {
		return "/images" + "?id=" + id;
	}
}
