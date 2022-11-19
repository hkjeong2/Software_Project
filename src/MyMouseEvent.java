import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MyMouseEvent extends MouseAdapter implements MouseListener{

	private PhotoAlbumFrame obj;
	
	public MyMouseEvent(PhotoAlbumFrame obj) {
		this.obj = obj;
	}
	
	public void mouseEntered(MouseEvent e) {
		
	}

	public void mouseClicked(MouseEvent e) {
		for(int i = 0; i < obj.photoPanel.size(); i++) {
			if(e.getSource() == obj.photoPanel.get(i)) {
				obj.getButton().setM(e);	
			}
		}
	}

	public void mousePressed(MouseEvent e) {
		
	}
	
	public void mouseReleased(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {
		
	}
}
