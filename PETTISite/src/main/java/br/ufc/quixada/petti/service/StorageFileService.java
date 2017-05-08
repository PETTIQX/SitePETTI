package br.ufc.quixada.petti.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StorageFileService {
	
	public void deleteFile(String path) throws IOException{
		Files.delete(Paths.get(path));
	}
	
	public void saveFile(MultipartFile file, String path) throws Exception{
		
		if(!file.isEmpty()){
			Files.copy(file.getInputStream(), Paths.get(path));
		}else{
			throw new Exception("File is empty...");
		}
		
	}
	
}
