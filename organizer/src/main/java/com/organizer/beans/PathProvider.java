package com.organizer.beans;

import javax.faces.bean.ApplicationScoped;
import javax.inject.Named;

/**
 * Create source paths.
 * 
 * @author GecaTM
 *
 */
@Named
@ApplicationScoped
public class PathProvider {

	/**
	 * Create image source path from record id.
	 * 
	 * @param id
	 *            record id.
	 * @return created source path.
	 */
	public String getImageSourcePath(String id) {
		return "/images" + "?id=" + id;
	}
}
