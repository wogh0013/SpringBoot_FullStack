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

	//���ϸ��� �ߺ��� �� (1)...(2)... �׸�(1).jpg, �׸�(2).jpg
	//���� �� �� �ð� �� �и��� ������ ���� ���ϸ����� �ϱ⵵ �Ѵ�


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
	
	//������ ������ ������ ���ε� �� �����̸��� ��ȯ�Ѵ�
	//ù ��° ����: ������ ���ε�� ���丮
	//�� ��° ����: ����
	public static String upload(String uploadDir,  MultipartFile multipartFile) throws IOException
	{

		String fileName = getFileName(uploadDir, multipartFile.getOriginalFilename());
		//���ο� ���ϸ�
		Path uploadPath = Paths.get(uploadDir); //���ε��� ���� ��θ� �����ͼ�
        
        if (!Files.exists(uploadPath)) { //���丮�� �������� ������
            try {
				Files.createDirectories(uploadPath); //���丮 �����
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
         
        //���� ���ε� �� �ݵ�� ������(������) ��θ� ����ߴµ�, ����� ��� ����� ��������
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            //����� ��θ� �̿��ؼ� ������ ���ε�
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
	
	



