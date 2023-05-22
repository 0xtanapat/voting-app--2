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
		JLabel label = new JLabel();
		label.setText("Number of eligible voters");
		
		JLabel label1 = new JLabel();
		label1.setText("Number of party");
		
        textField = new JTextField();
        textField.setPreferredSize(new Dimension(250, 40));
        
        textField1 = new JTextField();
        textField1.setPreferredSize(new Dimension(250, 40));
        
        btn = new JButton("Submit");
        btn.addActionListener(this);
        
        add(label);
        add(textField);
        add(label1);
        add(textField1);
        add(btn);
        
        pack();
        setTitle("Enter the numbers");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
        setSize(400, 300);
        setLayout(new GridLayout(5, 1, 20, 10));
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
