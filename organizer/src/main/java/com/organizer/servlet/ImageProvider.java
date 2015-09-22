package com.organizer.servlet;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.organizer.beans.RecordsService;

/**
 * Servlet implementation class ImageProvider
 */
@WebServlet("/images")
public class ImageProvider extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = Logger.getLogger(ImageProvider.class
			.getName());

	private static byte[] noPictureThumbnail;

	@Inject
	private RecordsService recordsService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ImageProvider() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("image/gif");
		OutputStream out = response.getOutputStream();
		String id = request.getParameter("id");
		byte[] recordPicture = null;
		try {
			recordPicture = recordsService.getPictureById(Long.parseLong(id));
		} catch (NumberFormatException e) {
			LOGGER.log(Level.SEVERE, "Invalid id", e);
		}
		if (recordPicture != null) {
			out.write(recordPicture);
		} else {
			out.write(noPictureThumbnail);
		}
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	@Override
	public void init() {
		try {
			URL url = getServletContext().getResource(
					"/resources/images/nopicture.jpg");
			noPictureThumbnail = Files.readAllBytes(new File(url.getPath())
			.toPath());
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Error when loading thumnail", e);
		}

	}

}
