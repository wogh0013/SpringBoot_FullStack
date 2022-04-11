package com.woori.mlp.common;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {

	//파일명이 중복될 때 (1)...(2)... 그림(1).jpg, 그림(2).jpg
	//연도 월 일 시간 초 밀리초 랜덤값 만들어서 파일명으로 하기도 한다


	public static String getFileName(String contextPath , String orifilename)
	{
		String filePath = contextPath ;
		 int pos = orifilename.lastIndexOf(".");       
		 String ext = orifilename.substring(pos+1);    
		 String oriFile = orifilename.substring(0, pos-1); 
					 
		 String filename = orifilename; 
		
		 File newFile = new File(filePath+"/" +filename); 
		 int i=1;
		 while(newFile.exists())  
		 {
			
			 filename = oriFile + "("+i+")." + ext; 
			 i++;
			 newFile = new File(filePath + "/" +filename);
			 
		 }
		 
		 return filename;
	}
	
	//파일을 지정된 폴더에 업로드 후 파일이름을 반환한다
	//첫 번째 인자: 파일이 업로드될 디렉토리
	//두 번째 인자: 파일
	public static String upload(String uploadDir,  MultipartFile multipartFile) throws IOException
	{

		String fileName = getFileName(uploadDir, multipartFile.getOriginalFilename());
		//새로운 파일명
		Path uploadPath = Paths.get(uploadDir); //업로드할 실제 경로를 가져와서
        
        if (!Files.exists(uploadPath)) { //디렉토리가 존재하지 않으면
            try {
				Files.createDirectories(uploadPath); //디렉토리 만들기
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
         
        //파일 업로드 시 반드시 물리적(절대적) 경로를 사용했는데, 상대적 경로 사용이 가능해짐
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            //상대적 경로를 이용해서 파일을 업로드
        } catch (IOException ioe) {        
            throw new IOException("Could not save image file: " + fileName, ioe);
        }    
        
        return fileName;
	
		
	}
	
	
	
	public static void upload(String uploadDir, List<MultipartFile> fileList,
			List<String> fileNameList)
	{

			
		//java.io.File
		File file = new File(uploadDir);
		if( !file.exists())
		{
			file.mkdir();
		}
		
		//System.out.println("filesize : " + fileList.size());
		
		if( fileList!=null && fileList.size()>0)
		{
			 for(MultipartFile multipartFile : fileList)
			 {		
				 try {
					String filename = upload(uploadDir,  multipartFile);
					fileNameList.add(filename);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 
			 }
				
							 }
		}
		
	}
	
	



