package com.aiuiot.house.biz.service;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.io.Files;

/**
 * 该Service用于将文件保存到本地
 * 实现文件（图片）的保存
 * @author aiuiot
 *
 */
@Service
public class FileService {
	
	//该路径是nginx的静态文件的路径 路径在配置文件application.properties中
	@Value("${file.path:}")
	private String filePath;
	
	//该方法 将上传文件列表返回一个列表路径 存在数据库中是一个路径 文件需要保存的本地
	public List<String> getImgPaths(List<MultipartFile> files){	
		//判断如果文件路径为空
		if (Strings.isNullOrEmpty(filePath)) {
            filePath = getResourcePath();
        }
		
		List<String> paths = Lists.newArrayList();
		System.out.println("filePath:"+filePath);
		//对上传的每个文件进行操作
		files.forEach(file -> {
			File localFile = null;
			//判断上传的文件是否为空 如果不为空进行以下操作
			System.out.println(file.isEmpty());
			if(!file.isEmpty()) {
				try {
					//saveToLocal(方法) 该方法将上传文件保存到本地 
					localFile = saveToLocal(file, filePath);
					//移除掉路径的前缀，值截取文件相对路径的名字 注意此处引入的是 lang3.StringUtils; 包
					String path = StringUtils.substringAfterLast(localFile.getAbsolutePath(), filePath);
					//添加相对路径的名称
					paths.add(path);
				} catch (Exception e) {
					throw new IllegalArgumentException(e);
				}
			}
		});
		//返回 paths
		System.out.println("文件路径:"+paths);
		return paths;
	}
	
	/**
	 * 
	 * @return
	 */
	public static String getResourcePath(){
		  File file = new File(".");
		  String absolutePath = file.getAbsolutePath();
		  return absolutePath;
	}

	/**
	 * 该方法到用于保存文件
	 * @param file
	 * @param filePath
	 * @return
	 * @throws IOException 
	 */
	private File saveToLocal(MultipartFile file, String filePath) throws IOException {
		//Instant.now().getEpochSecond() 获取一个以秒为单位的时间戳
		//file.getOriginalFilename() 获取最原始的名字
		File newFile = new File(filePath+ "/" + Instant.now().getEpochSecond()+ "/" +file.getOriginalFilename());
		//如果文件不存在，则需要创建上级目录
		if(!newFile.exists()) {
			//创建上级目录
			newFile.getParentFile().mkdirs();
			newFile.createNewFile();	//创建一个临时的文件
		}
		
		//将上传的文件写到刚刚行创建的newFile目录
		Files.write(file.getBytes(), newFile);
		System.out.println(newFile);
		return newFile;
	}
	
	
}
