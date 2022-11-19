import java.awt.*;
import javax.swing.*;

public class PhotoInfoFrame extends JFrame{
	
	JPanel panel = new JPanel();
	JPanel pCenter = new JPanel();
	JPanel pBottom = new JPanel();
	JLabel jlbName = new JLabel("Name");
	JLabel jlbAddedTime = new JLabel("Added Time");
	JLabel jlbCategory = new JLabel("Category");
	JLabel jlbCreatedTime = new JLabel("Created Time");
	JLabel jlbImageFile = new JLabel("Image File");
	JLabel jlbSelect = new JLabel("Select");
	JTextField jfName = new JTextField(8);
	JLabel jlAddedTime = new JLabel("");
	JTextField jfCategory = new JTextField(8);
	JTextField jfCreatedTime = new JTextField(8);
	JTextField jfImageFile = new JTextField(8);
	JButton FileButton = new JButton("     File     ");
	JButton OKButton = new JButton("OK");
	JButton CancelButton = new JButton("Cancel");
	
	public PhotoInfoFrame() {
		
		pCenter.setLayout(new GridLayout(2,6));
		pCenter.add(jlbName);
		pCenter.add(jlbAddedTime);
		pCenter.add(jlbCategory);
		pCenter.add(jlbCreatedTime);
		pCenter.add(jlbImageFile);
		pCenter.add(jlbSelect);
		pCenter.add(jfName);
		pCenter.add(jlAddedTime);
		pCenter.add(jfCategory);
		pCenter.add(jfCreatedTime);
		pCenter.add(jfImageFile);
		pCenter.add(FileButton);

		pBottom.add(CancelButton);
		pBottom.add(OKButton);
	
		panel.setLayout(new BorderLayout());
		panel.add(pCenter,BorderLayout.CENTER);
		panel.add(pBottom,BorderLayout.SOUTH);
		
		add(panel);
		setTitle("Photo Information");
		pack();
	    setLocationRelativeTo(null);
		setSize(580,140);
	
	}
}



