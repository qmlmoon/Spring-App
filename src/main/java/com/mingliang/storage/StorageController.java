package com.mingliang.storage;

import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.DecimalFormat;

/**
 * Created by qml_moon on 30/11/14.
 */
@RestController
public class StorageController {


	@RequestMapping(method = RequestMethod.GET, value = "/getfile/{filename:.*}")
	public FileSystemResource DownloadFileHandler(@PathVariable(value = "filename") String filename,
												  @RequestParam(value = "subdir", defaultValue = "") String subdir,
												  HttpServletResponse response) {
		response.setContentType("application/force-download");
		response.setHeader("Content-Disposition", "attachment;filename=" + filename );
		System.out.println(filename);
		File file = new File(Application.DIR + subdir + filename);
		return new FileSystemResource(file);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getdir")
	public String ListDirHandler(@RequestParam(value = "subdir", defaultValue = "") String subdir) {
		File dir = new File(Application.DIR + subdir);

		String html = "";

		String directories = "";
		for (File file: dir.listFiles()) {
			if (file.isFile()) {
				html += "<tr><td>" + readableFileSize(file.length()) + "</td><td>" +
					"<a href=\"./getfile/"+ file.getName() +
					"?subdir=" + subdir + "\" >" +
					file.getName() + "</a></td></tr>" ;
			} else {
				directories += "<tr><td>dir</td><td>" +
					"<a href=\"download.html?subdir="+ subdir + file.getName()  + "/\" >"
					+ file.getName() + "/"
					+ "</a></td></tr>" ;
			}
		}
		if (!subdir.equals("")) {
			String temp = subdir.substring(0, subdir.length() - 1);
			String sub = "";
			if (temp.contains("/")) {
				sub = "?subdir=" + temp.substring(0, temp.lastIndexOf("/") + 1);
			}
			directories += "<tr><td>dir</td><td><a href=\"download.html" + sub  + "\" >../</a></td></tr>" ;
		}
		return  html + directories;
	}

	public static String readableFileSize(long size) {
		if(size <= 0) return "0";
		final String[] units = new String[] { "B", "KB", "MB", "GB"};
		int digitGroups = (int) (Math.log10(size)/Math.log10(1024));
		return new DecimalFormat("#,##0.#").format(size/Math.pow(1024, digitGroups)) + " " + units[digitGroups];
	}
}
