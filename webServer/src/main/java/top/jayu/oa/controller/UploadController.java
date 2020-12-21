package top.jayu.oa.controller;

import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/upload")
public class UploadController {

	@Value("${upload.location}")
	private String rootPath;
	
	/**
	 * 上传单个文件
	 */
	@PostMapping("/file/one")
	public Map<String, Object> file(@RequestParam("file") MultipartFile file){
		
		Map<String, Object> map = new HashMap<>();
		
		if(file == null) {
			map.put("status", 0);
			map.put("errorInfo", "文件为空");
			return map;
		}
		
		File dest = fileName(file.getOriginalFilename());
		
		try {
			file.transferTo(dest);
			map.put("status", 1);
			map.put("rootPath", dest.getPath());
			map.put("path", "upload/" + currentDay() + file.getOriginalFilename());
			map.put("fileName", file.getOriginalFilename());
		} catch (Exception e) {
			map.put("status", 0);
			map.put("errorInfo", "上传时出错");
			e.printStackTrace();
		}
		
		return map;
	}
	
	private File fileName(String suffix) {
		final String dir = rootPath + "upload/" + currentDay();
		File dis = new File(dir);
		if(!dis.exists()) {
			dis.mkdirs();
		}
		return new File(dir + suffix);
	}
	private String getFileSuffix(String fileName) {
		if(StrUtil.isBlank(fileName)) {
			return "";
		}
		int index = fileName.lastIndexOf(".");
		return fileName.substring(index, fileName.length());
	}
	
	private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd");
	private String currentDay() {
		return formatter.format(new Date()) + "/";
	}
	
}
