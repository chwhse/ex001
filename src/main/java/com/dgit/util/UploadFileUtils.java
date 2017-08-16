package com.dgit.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.springframework.util.FileCopyUtils;

public class UploadFileUtils {
	
	//파일의 폴더와 이름을 리턴한다
	public static String uploadFile(String uploadPath,String originalName,byte[] fileData) throws IOException{
		UUID uid= UUID.randomUUID();
		String datePath=calcPath(uploadPath);
		String saveName=datePath+"/"+ uid.toString()+"_"+originalName;
		File target= new File(uploadPath, saveName);
		FileCopyUtils.copy(fileData, target);
		
		String thumbName = makeThumbnail(uploadPath, datePath, uid.toString()+"_"+originalName );
		
		System.out.println("thumbName:"+thumbName);
	//	return saveName; //2017/06/03/aaaaa_a.jpg
		return thumbName;
	}
	
	private static String calcPath(String uploadPath){
		Calendar cal= Calendar.getInstance();
		String yearPath="/"+cal.get(Calendar.YEAR);
		String monthPath= String.format("%s/%02d", yearPath, cal.get(Calendar.MONTH)+1);
		String dayPath= String.format("%s/%02d/%02d", yearPath, cal.get(Calendar.MONTH)+1,cal.get(Calendar.DATE));
		
		
		mkDir(uploadPath,yearPath,monthPath,dayPath);
		
		return dayPath;
	}
	
	private static void mkDir(String uploadPath,String...paths){
		for(String path:paths){
			File dirPath= new File(uploadPath+path);
			if(!dirPath.exists()){
				dirPath.mkdirs();
			}
		}
	}
	
	private static String makeThumbnail(String uploadpath, String datePath, String filename) throws IOException{
		String originalFileName = uploadpath+"/"+datePath+"/"+filename;
		BufferedImage sourceImg = ImageIO.read(new File(originalFileName));
		
		BufferedImage destImg = 
				Scalr.resize(
						sourceImg, 
						Scalr.Method.AUTOMATIC, 
						Scalr.Mode.FIT_TO_HEIGHT,100);
		String thumbFileName = uploadpath+"/"+datePath+"/s_"+filename;
		File newFile = new File(thumbFileName);
		String formatName = filename.substring(filename.lastIndexOf(".")+1);//확장자
		
		//thumbnail 경로/파일 이름에 resizing 된 이미지를 넣는다.
		ImageIO.write(destImg, formatName.toUpperCase(), newFile);
		return datePath+"/s_"+filename;
	}

	public static void deletefile9(String uploadPath, String filename) {
		File file = new File(uploadPath+filename);
		file.delete();
		
		String front = filename.substring(0,12);
		String end = filename.substring(14);
		String originalName = front+end;
		File file2 = new File(uploadPath+originalName);
		file2.delete();
	}
}
