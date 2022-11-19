import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Album {

   ArrayList<Photo> tempAlbum = new ArrayList<Photo>();
   ArrayList<Photo> Album = new ArrayList<Photo>();
   private int numOfPhotos = 0;
   private int index = 0;
   private String line = null;
   private String trimmedLine = null;
   private String checkTokens[] = null;
   private String tokens[] = null;
   Scanner input;

   DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss:SSS");

	Album(String photoInfoFileName){
		this.loadPhotos(photoInfoFileName);
   }
	
	public int getNumOfPhotos() {
		return numOfPhotos;
	}

	public void setNumOfPhotos(int numOfPhotos) {
		this.numOfPhotos = numOfPhotos;
	}
   
   public Photo getPhoto(int i) {
      return Album.get(i);
   }
   
   public void createTokens(){
	   checkTokens = line.split(";");
	   tokens = trimmedLine.split(";");
   }
   
   public void readLineAndTrimIt() {
	   line = input.nextLine();                             
       trimmedLine = line.replaceAll(" ", "");
   }
   
   public boolean checkIfComment() {
      if(trimmedLine.startsWith("//") == true)
         return true;
      else
         return false;
   }
   
   public boolean checkIfSpace() {
      if(trimmedLine.equals("") == true)
         return true;
      else
         return false;
   }
   
   public boolean checkIfNoTitle(String token) {
	   if(token.equals(" ") == true) {
    	   System.out.println("No Image File : " + line);
    	   return true;
       }
	   else
		   return false;
   }
   
   public boolean checkIfOverlap(int index) {
	   boolean overlap = false;
	   Photo photo = new Photo(tokens[0], tokens[1], tokens[2], tokens[3], tokens[4]);
	   tempAlbum.add(photo);
       
       if(index>=1) {
    	   while(overlap == false) {
         	  
        	   for(int j = 0; j < index; j++) {
        		   for(int k = j + 1; k < index + 1; k++) {
        			   if(tempAlbum.get(j).getId().equals(tempAlbum.get(k).getId()) == true) {
        				   			System.out.println("ID Conflict (a photo with the same ID already exists) : " 
        						   + tempAlbum.get(k).getId() + ";" + tempAlbum.get(k).getName() + ";" + tempAlbum.get(k).getTimeAddedToAlbum() + ";" 
        						   + tempAlbum.get(k).getCategory() + ";" + tempAlbum.get(k).getImageFileName() + ";");
            			   return true; 
        			   }
        		   }
        	   }
        	   break;
           }
       } 
       return false;
   }
   
   public String getTimeForPanelTitle(int i) {
	   String[] time = Album.get(i).getTimeAddedToAlbum().split("_");
	   return time[0];
   }
   
   public void init() {
	   tempAlbum.removeAll(tempAlbum);
	   Album.removeAll(Album);
	   numOfPhotos = 0;
	   index = 0;
	   line = null;
	   trimmedLine = null;
	   checkTokens = null;
	   tokens = null;
   }
   
 public void createPhotosUnlessAbnormal() throws Exception{
	   
	   createTokens();
	   
	   if(checkIfNoTitle(checkTokens[4]) == true)
		   throw new Exception();
	  
       try {
    	   LocalDateTime.parse(tokens[2], formatter);
       }
       catch(Exception e) {
    	   System.out.println("Wrong Date Format : " + line);
    	   return;
       }
       
       if(checkIfOverlap(index) == true)
    	   throw new Exception();
       
       Photo photo = new Photo(tokens[0], tokens[1], tokens[2], tokens[3], tokens[4]);
       Album.add(photo);
       numOfPhotos++;
       index++; 
   }
   
   public void saveFile(String PhotoInfoFileName) {
      
      try {
         
         BufferedWriter file = new BufferedWriter(new FileWriter(PhotoInfoFileName, false));
         
         for(int i = 0; i < getNumOfPhotos(); i++) {
            file.write(Album.get(i).getId() + ";" 
            		+ Album.get(i).getName() + ";" + Album.get(i).getTimeAddedToAlbum() + ";" 
            		+ Album.get(i).getCategory() + ";" + Album.get(i).getImageFileName());
            file.newLine();
         }
         file.flush();
         file.close();
      	}
         catch (Exception e) {
            e.printStackTrace();
        }
   }
     
   public void loadPhotos(String photoInfoFileName) {
      File file = new File(photoInfoFileName);
         try {
         input = new Scanner(file);
            boolean flag = true;
            while(input.hasNext()) {
            	readLineAndTrimIt();
               if(checkIfSpace() == true)
            	   readLineAndTrimIt();
               else if(checkIfComment() == true) {
                  while(flag == true) {
                	  readLineAndTrimIt();
                	  
                       if(checkIfSpace() == true) 
                    	   readLineAndTrimIt();
                       if(checkIfComment() == true) 
                          flag = false;
                       else{
                    	   try {
                        	   createPhotosUnlessAbnormal();
                    	   }
                    	   catch(Exception e){
                    		   break;
                    	   }
                       	}
                  }
               }
               else {
            	   try {
                	   createPhotosUnlessAbnormal();
            	   	}
            	   catch(Exception e){
            		   break;
            	   }
                }
            }
         }
         catch(Exception e) {
            System.out.println("Unknown Album data File");
            return;
         }
   }
      
}
