import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Photo {

	private String id;
	private String name;
	private String timeAddedToAlbum;
	private String category;
	private String imageFileName;
	private String timeAtPhotoTaken;
	LocalDateTime time = LocalDateTime.now();
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss:SSS");
	
	Photo(){
		this.id = "IMG" + time.format(formatter);
		this.timeAddedToAlbum = time.format(formatter);
		this.timeAtPhotoTaken = time.format(formatter);
	}
	
	Photo(String id, String name, String timeAddedToAlbum, String category, String imageFileName){
		this.id = id;
		this.name = name;
		this.timeAddedToAlbum = timeAddedToAlbum;
		this.category = category;
		this.imageFileName = imageFileName;
		this.timeAtPhotoTaken = time.format(formatter);
	}
	
	Photo(String imageFileName){
		this.id = "IMG" + time.format(formatter);
		this.timeAddedToAlbum = time.format(formatter);
		this.imageFileName = imageFileName;
		this.timeAtPhotoTaken = time.format(formatter);
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTimeAddedToAlbum() {
		return timeAddedToAlbum;
	}
	public void setTimeAddedToAlbum(String timeAddedToAlbum) {
		this.timeAddedToAlbum = timeAddedToAlbum;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getImageFileName() {
		return imageFileName;
	}
	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}
	public String getTimeAtPhotoTaken() {
		return timeAtPhotoTaken;
	}
	public void setTimeAtPhotoTaken(String timeAtPhotoTaken) {
		this.timeAtPhotoTaken = timeAtPhotoTaken;
	}
	
	public void print() {
		System.out.println("" + id + ";" + name + ";" + timeAddedToAlbum + ";" + category + ";" + imageFileName + ";");
	}
	
}
