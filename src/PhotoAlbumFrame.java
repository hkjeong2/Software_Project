import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.*;
import javax.swing.border.TitledBorder;

public class PhotoAlbumFrame extends JFrame{
   
    private int numOfDifferentGroups;
    private int numOfPhotosOfSameGroup;
    private int nOffset = 0;
    private int nCount = 0;
    private int i = 0;	private int j = 0;	private int k = 0;
	private String tempStringForComparison = null;
    private String typeOfSort = "default";
    private String FileName = null;
	private ButtonListener button = null;
    private MyMouseEvent mouse = null;
    private JButton jbtDate = new JButton("Date");
    private JButton jbtCategory = new JButton("Category");
    private JButton jbtEdit = new JButton("EDIT");
    private JButton jbtAdd = new JButton("ADD");
    private JButton jbtDelete= new JButton("DELETE");
    private JButton jbtLoad= new JButton("LOAD");
    private JButton jbtSave= new JButton("SAVE");
    JPanel pTop = new JPanel();
    JPanel pCenter = new JPanel();
    JPanel pBottom = new JPanel();
    ArrayList<JPanel> photoPanel = new ArrayList<JPanel>();

    PhotoInfoFrame photoInfoFrame_edit = new PhotoInfoFrame();
	PhotoInfoFrame photoInfoFrame_add = new PhotoInfoFrame();
    Album album;

    public PhotoAlbumFrame(String FileName) {
    	  album = new Album(FileName);
    	  this.FileName = FileName;
    	  
		  mouse = new MyMouseEvent(this);
		  button = new ButtonListener(this);
		  jbtAdd.addActionListener(button);
		  jbtDate.addActionListener(button);
		  jbtCategory.addActionListener(button);
		  jbtEdit.addActionListener(button);
		  jbtDelete.addActionListener(button);
		  jbtLoad.addActionListener(button);
		  jbtSave.addActionListener(button);
		  photoInfoFrame_edit.OKButton.addActionListener(button);
		  photoInfoFrame_edit.CancelButton.addActionListener(button);
		  photoInfoFrame_add.OKButton.addActionListener(button);
		  photoInfoFrame_add.CancelButton.addActionListener(button);
		  photoInfoFrame_edit.FileButton.addActionListener(button);
		  photoInfoFrame_add.FileButton.addActionListener(button);
		
		  pTop.setLayout(new BorderLayout());
		  pTop.add(jbtDate, BorderLayout.WEST);
		  pTop.add(jbtCategory, BorderLayout.EAST);		
		  sortPhotos();		  
		  pBottom.setLayout(new GridLayout(1,5));
		  pBottom.add(jbtEdit);
		  pBottom.add(jbtAdd);
		  pBottom.add(jbtDelete);
		  pBottom.add(jbtLoad);
		  pBottom.add(jbtSave);
		  
		  setLayout(new BorderLayout());
		  add(pTop,BorderLayout.NORTH);
		  add(pCenter,BorderLayout.CENTER);
		  add(pBottom,BorderLayout.SOUTH);		
		  setTitle("Simple Photo Album");
		  setSize(560,750);
		  setLocationRelativeTo(null);
		  setDefaultCloseOperation(EXIT_ON_CLOSE);
		  setVisible(true);      
   }
    
	public void sortPhotos() {
		if(typeOfSort.equals("default")) {
		    SortDefaultDate sortDefaultDate = new SortDefaultDate();
		    Collections.sort(album.Album, sortDefaultDate);	
		    typeOfSort = "default";
		    tempStringForComparison = album.getTimeForPanelTitle(0);
		}
		else if(typeOfSort.equals("date")) {
			SortDate sortDate = new SortDate();
		    Collections.sort(album.Album, sortDate);
		    typeOfSort = "date";
		    tempStringForComparison = album.getTimeForPanelTitle(0);
		}
		else if(typeOfSort.equals("category")) {
			SortDate sortDate = new SortDate();
		    Collections.sort(album.Album, sortDate);
			SortCategory sortCategory = new SortCategory();
		    Collections.sort(album.Album, sortCategory);
		    typeOfSort = "category";
		    tempStringForComparison = album.getPhoto(0).getCategory();
		}
	    numOfDifferentGroups = 1;
	    numOfPhotosOfSameGroup = 0;
	    
	    if(typeOfSort.equals("default") || typeOfSort.equals("date")) {
		    for(i = 0; i < album.getNumOfPhotos(); i++) {
		  
		         if(tempStringForComparison.compareTo(album.getTimeForPanelTitle(i))!=0) {
		            tempStringForComparison = album.getTimeForPanelTitle(i);
		            numOfDifferentGroups++;
		            addSortedPanels("date");
		            nOffset = nCount;
		            numOfPhotosOfSameGroup = 1;
		         }
		         else
		            numOfPhotosOfSameGroup++;
		         
		         if(i == album.getNumOfPhotos()-1) {
		            addSortedPanels("date");
		            nOffset = 0;
		            nCount = 0;
		            tempStringForComparison= null;
		            break;
		         }
		            nCount++;
		    	}
	    }
	    else if(typeOfSort.equals("category")) {
	    	for(i = 0; i < album.getNumOfPhotos(); i++) {
	            
	            if(tempStringForComparison.compareTo(album.getPhoto(i).getCategory())!=0) {
	               tempStringForComparison = album.getPhoto(i).getCategory();
	               numOfDifferentGroups++;
	               addSortedPanels("category");
	               nOffset = nCount;
	               numOfPhotosOfSameGroup = 1;
	            }
	            else
	               numOfPhotosOfSameGroup++;
	            
	            if(i == album.getNumOfPhotos()-1) {
	               addSortedPanels("category");
	               nOffset = 0;
	               nCount = 0;
	               tempStringForComparison = null;
	               break;
	            }
	               nCount++;
	       }
	    }
	    pCenter.setLayout(new GridLayout(numOfDifferentGroups,1));
	 }
	
	   public void addSortedPanels(String type) {
	      for(j = nOffset; j < numOfPhotosOfSameGroup + nOffset; j++) {
	           JPanel pPhoto = new JPanel();
	           pPhoto.setLayout(new BorderLayout());
	           JLabel jlbTempImage = new JLabel(new ImageIcon(album.getPhoto(j).getImageFileName())); 
	           JLabel jlbTempName = new JLabel(""+album.getPhoto(j).getName());
	           jlbTempName.setHorizontalAlignment(JLabel.CENTER);
	           pPhoto.add(jlbTempImage,BorderLayout.CENTER);
	           pPhoto.add(jlbTempName,BorderLayout.SOUTH);
	           
	           photoPanel.add(pPhoto);
	           photoPanel.get(j).addMouseListener(mouse);
	           
	           if(j == numOfPhotosOfSameGroup-1 + nOffset) {
	              JPanel pGroup = new JPanel();
	              if(type.equals("date"))
	                 pGroup.setBorder(new TitledBorder(""+album.getTimeForPanelTitle(j)));
	              else if(type.equals("category"))
	                 pGroup.setBorder(new TitledBorder(""+album.getPhoto(j).getCategory()));
	              pGroup.setLayout(new FlowLayout(FlowLayout.LEFT,5,5));
	              for(k = nOffset; k < numOfPhotosOfSameGroup + nOffset; k++) {
	                 pGroup.add(photoPanel.get(k));
	              }
	              pCenter.add(pGroup);
	           }
	        }
	   }
	   
	   public String getTypeOfSort() {
			return typeOfSort;
		}
		public void setTypeOfSort(String typeOfSort) {
			this.typeOfSort = typeOfSort;
		}
		public String getFileName() {
			return FileName;
		}
		public ButtonListener getButton() {
			return button;
		}
		public void setButton(ButtonListener button) {
			this.button = button;
		}
		public MyMouseEvent getMouse() {
			return mouse;
		}
		public void setMouse(MyMouseEvent mouse) {
			this.mouse = mouse;
		}
		public JButton getJbtDate() {
			return jbtDate;
		}
		public void setJbtDate(JButton jbtDate) {
			this.jbtDate = jbtDate;
		}
		public JButton getJbtCategory() {
			return jbtCategory;
		}
		public void setJbtCategory(JButton jbtCategory) {
			this.jbtCategory = jbtCategory;
		}
		public JButton getJbtEdit() {
			return jbtEdit;
		}
		public void setJbtEdit(JButton jbtEdit) {
			this.jbtEdit = jbtEdit;
		}
		public JButton getJbtAdd() {
			return jbtAdd;
		}
		public void setJbtAdd(JButton jbtAdd) {
			this.jbtAdd = jbtAdd;
		}
		public JButton getJbtDelete() {
			return jbtDelete;
		}
		public void setJbtDelete(JButton jbtDelete) {
			this.jbtDelete = jbtDelete;
		}
		public JButton getJbtLoad() {
			return jbtLoad;
		}
		public void setJbtLoad(JButton jbtLoad) {
			this.jbtLoad = jbtLoad;
		}
		public JButton getJbtSave() {
			return jbtSave;
		}
		public void setJbtSave(JButton jbtSave) {

			this.jbtSave = jbtSave;
		}
   
}

class SortDefaultDate implements Comparator<Photo>{
	public int compare(Photo o1, Photo o2) {
		return o2.getTimeAddedToAlbum().compareTo(o1.getTimeAddedToAlbum());
	}
}

class SortDate implements Comparator<Photo>{
	public int compare(Photo o1, Photo o2) {
		return o1.getTimeAddedToAlbum().compareTo(o2.getTimeAddedToAlbum());
	}
}

class SortCategory implements Comparator<Photo>{
	public int compare(Photo o1, Photo o2) {
		return o1.getCategory().compareTo(o2.getCategory());
	}
}


