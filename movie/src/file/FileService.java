package file;

import java.util.List;

public class FileService {

	FileDAO fileDAO;
	
	//기본생성자
	public FileService() {
		fileDAO = new FileDAO(); 
	}
	
	public List<FileVO> listFiles() {
		
		return fileDAO.selectAllFiles();
	}
	
	
	public int addFile(FileVO fileVO) {
		
		 return fileDAO.insertNewFile(fileVO);
	}

	
	public FileVO viewFile(int fileNO) {
		
	 return fileDAO.selectFile(fileNO);
		
	 
	}

	public void modFile(FileVO fileVO) {
		
		fileDAO.updateFile(fileVO);  
	}
		 
	public List<Integer> removeFile(int fileNO){
		
		List<Integer> fileNOList = fileDAO.selectRemoveFiles(fileNO);
		
		fileDAO.deleteFile(fileNO);
		
		return fileNOList; 
	}
	
	public int addReply(FileVO  fileVO) {

		 return  fileDAO.insertNewFile(fileVO);		
	}

	public List selectName(String num) {
		
		return fileDAO.selectName(num);
		
	}
	
	
	
}









