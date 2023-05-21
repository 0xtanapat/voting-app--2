package voting;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

@SuppressWarnings("serial")
public class BallotPaperFrame extends JFrame implements ActionListener {
	JLabel label;
	ButtonGroup group;
	JButton btn;
	
	int eligible_voters;
	int party_num;
	int counter = 0;
	List<JRadioButton> radioBtnList;
	
	BallotPaperFrame(int eligible_voters, int party_num) {
		this.eligible_voters = eligible_voters;
		this.party_num = party_num;
		
		label = new JLabel();
		label.setText("Ballot Paper");
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setVerticalAlignment(JLabel.TOP);
		add(label);
		
        group = new ButtonGroup();
        radioBtnList = new ArrayList<>();
        for (int i = 0; i < party_num; i++) {
        	JRadioButton radioBtn = new JRadioButton("Party " + (i + 1));
        	radioBtnList.add(radioBtn);
        	group.add(radioBtn);
			add(radioBtn);
		}
        
        btn = new JButton("Submit");
        btn.addActionListener(this);
        add(btn);
        
        setTitle("Ballot paper");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
        setSize(640, 640);
        setLayout(new GridLayout(10, 1, 10, 10));
        setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int party_selected = 0;
		for (int i = 0; i < party_num; i++) {
			if (radioBtnList.get(i).isSelected()) {
				party_selected = i + 1;
			}
		}
		
		if (party_selected == 0) {
			JOptionPane.showMessageDialog(null, "Please select at least 1 party!");
			return;
		}
		
		if (e.getSource() == btn ) {
			counter++;
			if (counter > eligible_voters) {
				JOptionPane.showMessageDialog(null, "Error!");
            } else {
            	sendNumberToServer(party_selected);
            	System.out.println("Vote #" + counter + ". Party selected: " + party_selected);
            }
		}
	}
	
	private void sendNumberToServer(int party_selected) {
		String localhost = "127.0.0.1";
		int port = 3000;
		
		try (Socket socket = new Socket(localhost, port)) {
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(party_selected);
            outputStream.flush();
            JOptionPane.showMessageDialog(null, "You have voted party: " + party_selected);
        } catch (IOException e1) {
            e1.printStackTrace();
        }  
	}
}
