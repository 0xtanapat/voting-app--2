package voting;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class PromptFrame extends JFrame implements ActionListener {
	JTextField textField;
	JTextField textField1;
	JButton btn;
	
	private int eligible_voters;
	private int party_num;
	
	public PromptFrame() {
        textField = new JTextField();
        textField.setPreferredSize(new Dimension(250, 40));
        add(textField);
        
        textField1 = new JTextField();
        textField1.setPreferredSize(new Dimension(250, 40));
        add(textField1);
        
        btn = new JButton("Submit");
        btn.addActionListener(this);
        add(btn);
        
        pack();
        setTitle("Enter the numbers");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
        setSize(640, 640);
        setLayout(new FlowLayout());
        setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btn) {
			this.eligible_voters = Integer.parseInt(textField.getText());
			this.party_num = Integer.parseInt(textField1.getText());
		}
	}

	public int getNumOfEligibleVoters() {
		return this.eligible_voters;
	}
	
	public int getNumOfParty() {
		return this.party_num;
	}
}
