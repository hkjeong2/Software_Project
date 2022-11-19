import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ButtonListener extends JFrame implements ActionListener{

   private PhotoAlbumFrame obj;
   private JFileChooser jfc_edit;
   private JFileChooser jfc_add;
   private FileNameExtensionFilter filter = new FileNameExtensionFilter("image file", "jpg", "jpeg", "jfif", "png");
   private File selectedFile;
   private MouseEvent m;
   private String subStringForImageFileName;
   private String tokenForName[];
   private String imageFileName;
   private String name;
   private String temp;
   private int index = 0;
   
   public ButtonListener(PhotoAlbumFrame obj) {
          this.obj = obj;
   }
   
   	public MouseEvent getM() {
	   	return m;
   	}
	public void setM(MouseEvent m) {
		this.m = m;
	}
	
	public void repaintWithSortedAlbum() {
	   		obj.pCenter.removeAll();
	   		obj.photoPanel.removeAll(obj.photoPanel); 
            obj.sortPhotos();
            obj.add(obj.pCenter,BorderLayout.CENTER);
            obj.setVisible(true);
   }
   
   public void repaintOrAlert() {
	   if(obj.album.Album.size()==0)
           JOptionPane.showMessageDialog(null, "There is no photo in album to sort", "Message", JOptionPane.INFORMATION_MESSAGE);
        else
           repaintWithSortedAlbum();
   }
   
   public void init_needsForFile(){
	   subStringForImageFileName = null;
	   tokenForName = null;
	   imageFileName = null;
	   name = null;
	   index = 0;
   }
   
   public void findNameAndImageFileName() throws Exception {
	   try {
		   index = selectedFile.toString().indexOf("images");
	       subStringForImageFileName = selectedFile.toString().substring(index);
	       subStringForImageFileName = subStringForImageFileName.replace("\\","/");
	       imageFileName = subStringForImageFileName;
	       tokenForName = subStringForImageFileName.split("[/,.]");
	       name = tokenForName[1];
	   }
	   catch(Exception exception) {
           JOptionPane.showMessageDialog(null, "Wrong file clicked", "Message", JOptionPane.INFORMATION_MESSAGE);
		   throw new Exception();
	   }
   }
   
    public void actionPerformed(ActionEvent e) {
   
        if(e.getSource()==obj.getJbtDate()) {											//Date
        	temp = obj.getTypeOfSort();
        	temp = "date";
        	obj.setTypeOfSort(temp);
        	repaintOrAlert();
        	m = null;
        }
        else if(e.getSource()==obj.getJbtCategory()) {									//Category
        	temp = obj.getTypeOfSort(); 
        	temp = "category";
        	obj.setTypeOfSort(temp);
        	repaintOrAlert();
        	m = null;
        }
        else if(e.getSource()==obj.getJbtEdit()) {                                          //EDIT
           if(m == null) 
              JOptionPane.showMessageDialog(null, "Click one of photos before pressing the button", "Message", 
            		  JOptionPane.INFORMATION_MESSAGE);
           else {
              for(int i = 0; i < obj.photoPanel.size(); i++) {
                 if(this.m.getSource() == obj.photoPanel.get(i)) {
                    obj.photoInfoFrame_edit.jfName.setText(obj.album.getPhoto(i).getName());
                    obj.photoInfoFrame_edit.jfCategory.setText(obj.album.getPhoto(i).getCategory());
                    obj.photoInfoFrame_edit.jfImageFile.setText(obj.album.getPhoto(i).getImageFileName());
                    obj.photoInfoFrame_edit.jfCreatedTime.setText(obj.album.getPhoto(i).getTimeAtPhotoTaken());
                    obj.photoInfoFrame_edit.jlAddedTime.setText(obj.album.getPhoto(i).getTimeAddedToAlbum());
                    obj.photoInfoFrame_edit.pack();
                    obj.photoInfoFrame_edit.setVisible(true);
                 }
             }
           }
        }
        else if(e.getSource()==obj.photoInfoFrame_edit.OKButton) {                           //EDIT OKBUTTON
            for(int i = 0; i < obj.photoPanel.size(); i++) {
               if(this.m.getSource() == obj.photoPanel.get(i)) {
            	   if(obj.photoInfoFrame_edit.jfImageFile.getText().trim().equals("") || 
            			   obj.photoInfoFrame_edit.jfName.getText().trim().equals("")) {
                       JOptionPane.showMessageDialog(null, "Input the Photo Information", "Message", 
                    		   JOptionPane.INFORMATION_MESSAGE);
                    }
            	   else {
            		   obj.photoInfoFrame_edit.dispose();
                       obj.album.getPhoto(i).setName(obj.photoInfoFrame_edit.jfName.getText());
                       obj.album.getPhoto(i).setCategory(obj.photoInfoFrame_edit.jfCategory.getText());
                       obj.album.getPhoto(i).setImageFileName(obj.photoInfoFrame_edit.jfImageFile.getText());
                       obj.album.getPhoto(i).setTimeAtPhotoTaken(obj.photoInfoFrame_edit.jfCreatedTime.getText());
                       repaintWithSortedAlbum();
                       m = null;
                       break;
            	   }
               }
            }
         }
         else if(e.getSource()==obj.photoInfoFrame_edit.CancelButton) {                        //EDIT CANCELBUTTON
            obj.photoInfoFrame_edit.dispose();
            m = null;
         }
         else if(e.getSource()==obj.photoInfoFrame_edit.FileButton) {                        //EDIT FILEBUTTON              
             jfc_edit = new JFileChooser();
             jfc_edit.addChoosableFileFilter(filter);
             jfc_edit.setCurrentDirectory(new File("images"));
             int ret = jfc_edit.showOpenDialog(null);
             
             if(ret == 0) {
                init_needsForFile();
                boolean photoAlreadyExist = false;
               selectedFile = jfc_edit.getSelectedFile();
                try {
              	  findNameAndImageFileName();
                }
                catch(Exception exception){
                   return;
                }
                for(int i = 0; i < obj.album.getNumOfPhotos(); i++) {
                   if(obj.album.getPhoto(i).getImageFileName().equals(subStringForImageFileName)) {
                      obj.photoInfoFrame_edit.jfName.setText(obj.album.getPhoto(i).getName());
                      obj.photoInfoFrame_edit.jfCategory.setText(obj.album.getPhoto(i).getCategory());
                      obj.photoInfoFrame_edit.jfImageFile.setText(obj.album.getPhoto(i).getImageFileName());
                      obj.photoInfoFrame_edit.jfCreatedTime.setText(obj.album.getPhoto(i).getTimeAtPhotoTaken());
                      obj.photoInfoFrame_edit.pack();
                      obj.photoInfoFrame_edit.setVisible(true);
                      photoAlreadyExist = true;
                   }
                }
                if(photoAlreadyExist == false) {
                   for(int i = 0; i < obj.photoPanel.size(); i++) {
                        if(this.m.getSource() == obj.photoPanel.get(i)) {
                           obj.photoInfoFrame_edit.jfImageFile.setText(subStringForImageFileName);
                           obj.photoInfoFrame_edit.jfName.setText(name);
                           obj.photoInfoFrame_edit.jfCategory.setText("");
                        }
                     }
                }
             }
          }
        else if(e.getSource()==obj.getJbtAdd()) {                                          			//ADD
           obj.photoInfoFrame_add.jfName.setText("");
           obj.photoInfoFrame_add.jfCategory.setText("");
           obj.photoInfoFrame_add.jfImageFile.setText("");
           obj.photoInfoFrame_add.jfCreatedTime.setText("");
           obj.photoInfoFrame_add.setVisible(true);
       }
        else if(e.getSource()==obj.photoInfoFrame_add.OKButton) {                          		 //ADD OKBUTTON
            if(obj.photoInfoFrame_add.jfImageFile.getText().trim().equals("") ||
            	obj.photoInfoFrame_add.jfName.getText().trim().equals("")) {
               JOptionPane.showMessageDialog(null, "Input the Photo Information", "Message",
            		   JOptionPane.INFORMATION_MESSAGE);
            }
            else {
               Photo photo = new Photo(obj.photoInfoFrame_add.jfImageFile.getText());
                photo.setName(obj.photoInfoFrame_add.jfName.getText());
                photo.setCategory(obj.photoInfoFrame_add.jfCategory.getText());
                obj.album.Album.add(photo);
                int nTemp = 0;
                nTemp = obj.album.getNumOfPhotos();
                obj.album.setNumOfPhotos(++nTemp);
                repaintWithSortedAlbum();
                obj.photoInfoFrame_add.dispose();
            }
            m = null;
         }
         else if(e.getSource()==obj.photoInfoFrame_add.CancelButton) {                        //ADD CANCELBUTTON
            obj.photoInfoFrame_add.dispose();
            m = null;
         }
         else if(e.getSource()==obj.photoInfoFrame_add.FileButton) {                                    //ADD FILEBUTTON
             jfc_add = new JFileChooser();
             jfc_add.addChoosableFileFilter(filter);
             jfc_add.setCurrentDirectory(new File("images"));
             int ret = jfc_add.showOpenDialog(null);
             
             if(ret == 0) {
                init_needsForFile();
               selectedFile = jfc_add.getSelectedFile();
                try {
              	  findNameAndImageFileName();
                }
                catch(Exception exception){
                   return;
                }
                obj.photoInfoFrame_add.jfName.setText(name);
                obj.photoInfoFrame_add.jfImageFile.setText(imageFileName);
             }
          }
        else if(e.getSource()==obj.getJbtDelete()) {                                        		  //DELETE
           if(m == null)
              JOptionPane.showMessageDialog(null, "Click one of photos before pressing the button", "Message", 
            		  JOptionPane.INFORMATION_MESSAGE);
           else {
              for(int i = 0; i < obj.photoPanel.size(); i++) {
                 if(this.m.getSource() == obj.photoPanel.get(i)) {
                    if(obj.album.Album.size() == 1) {
                    	obj.album.Album.remove(i);
                    	int nTemp = 0;
                    	nTemp = obj.album.getNumOfPhotos();
                    	obj.album.setNumOfPhotos(--nTemp);;
                    	obj.pCenter.removeAll();
                    	obj.photoPanel.removeAll(obj.photoPanel);
                        obj.repaint();
                        obj.setVisible(true);
                    }
                    else {
                    	int nTemp = 0;
                    	nTemp = obj.album.getNumOfPhotos();
                        obj.album.Album.remove(i);
                        obj.album.setNumOfPhotos(--nTemp);
                        repaintWithSortedAlbum();
                    }
                 }
             }
              m = null;
           }
        }
        else if(e.getSource()==obj.getJbtLoad()) {															//LOAD 
        	try {
        	obj.album.init();
        	String fileName = obj.getFileName();
        	obj.album.loadPhotos(fileName);
        	temp = obj.getTypeOfSort();
        	temp = "default";
        	obj.setTypeOfSort(temp);
        	repaintWithSortedAlbum();
        	JOptionPane.showMessageDialog(null, "The album has loaded the original file", "Message", 
        			JOptionPane.INFORMATION_MESSAGE);
        	}
        	catch(Exception a){
                JOptionPane.showMessageDialog(null, "Wrong File Loaded", "Message", JOptionPane.INFORMATION_MESSAGE);
        	}
        	m = null;
        }
        else if(e.getSource()==obj.getJbtSave()) {															//SAVE
        	obj.album.saveFile("tempFile.txt");
        	JOptionPane.showMessageDialog(null, "The album has saved the current file", "Message", 
        			JOptionPane.INFORMATION_MESSAGE);
        	m = null;
        }
        
    }
    
}
	
	
	

