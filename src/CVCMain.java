import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
	
	
	
public class CVCMain extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel panel, southPanel;
	private JLabel label, vLabel, spaceLabel, spaceLabel2, spaceLabel3, cOneLabel, vMiddleLabel, cTwoLabel;
	private JButton generate;
	private JCheckBox randomCheckBox, cOne, cTwo;
	private JComboBox beginningFamily, middleFamily, endFamily;
	//private boolean cOneTrue, cTwoTrue;
	private char[] consonants, vowels;
	private String[] restricted, beginCombo, middleCombo, endCombo;
	private Random random;
	private int cOneCounter, cTwoCounter, vCounter;
	private String word;
	private boolean resetApp;
	
	CVCMain() {
		consonants = new char[] {'b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 
				'p', 'q', 'r', 's', 't', 'v', 'w', 'x', 'y', 'z'};	
		vowels = new char[] {'a', 'e', 'i', 'o', 'u'};
		restricted = new String[] {"as", "fuc", "fuk", "suc", "suk", "sex"};
		beginCombo = new String[] {"any", "b", "c", "d", "f", "g", "h", "j", "k", "l", "m", "n",
				"p", "q", "r", "s", "t", "v", "w", "x", "y", "z"};
		middleCombo = new String[] {"any", "a", "e", "i", "o", "u"};
		endCombo = new String[] {"any", "b", "c", "d", "f", "g", "h", "j", "k", "l", "m", "n",
				"p", "q", "r", "s", "t", "v", "w", "x", "y", "z"};
		word = "Ready";
		random = new Random();
		resetApp = false;
		
		label = new JLabel(word);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setFont(new Font("Comic Sans MS", Font.PLAIN, 200));
		
		panel = new JPanel();
		panel.setPreferredSize(new Dimension(1000, 400));
		panel.setLayout(new BorderLayout(1, 1));
		panel.setBackground(Color.white);
		panel.add(label, BorderLayout.CENTER);
		
		randomCheckBox = new JCheckBox("random order");
		randomCheckBox.setSelected(true);
		randomCheckBox.addActionListener(this);
		
		cOne = new JCheckBox("C");
		cOne.setSelected(true);
		cOne.addActionListener(this);
		
		vLabel = new JLabel(" V ");
		
		cTwo = new JCheckBox("C");
		cTwo.setSelected(true);
		cTwo.addActionListener(this);
		
		spaceLabel = new JLabel("       ");
		spaceLabel2 = new JLabel("                  ");
		spaceLabel3 = new JLabel("                  ");
		
		generate = new JButton("generate");
		generate.addActionListener(this);
		
		cOneLabel = new JLabel("Lock: C ");
		beginningFamily = new JComboBox(beginCombo);
		beginningFamily.setSelectedIndex(0);
		beginningFamily.addActionListener(this);
		vMiddleLabel = new JLabel(" V ");
		middleFamily = new JComboBox(middleCombo);
		middleFamily.setSelectedIndex(0);
		middleFamily.addActionListener(this);
		cTwoLabel = new JLabel(" C ");
		endFamily = new JComboBox(endCombo);
		endFamily.setSelectedIndex(0);
		endFamily.addActionListener(this);
		
		southPanel = new JPanel();
		southPanel.setBackground(Color.orange);
		southPanel.add(randomCheckBox);
		southPanel.add(spaceLabel2);
		southPanel.add(cOne);
		southPanel.add(vLabel);
		southPanel.add(cTwo);
		southPanel.add(spaceLabel);
		southPanel.add(generate);
		southPanel.add(spaceLabel3);
		southPanel.add(cOneLabel);
		southPanel.add(beginningFamily);
		southPanel.add(vMiddleLabel);
		southPanel.add(middleFamily);
		southPanel.add(cTwoLabel);
		southPanel.add(endFamily);
		
		panel.add(southPanel, BorderLayout.SOUTH);
		
		getContentPane().add(panel);
		setTitle("CVC Generator");
		WindowListener l = new WindowAdapter() {
	         public void windowClosing(WindowEvent e) {
	           System.exit(0);
	         }
	      };
	        addWindowListener(l);
	        pack();
	        setVisible(true);
	}
	
	//Will reset all to be able to start again
		private void resetApp() {
			resetApp = false;
			cOneCounter = 0;
			cTwoCounter = 0;
			vCounter = 0;
			label.setText("Ready");
			word = "";
		}
		
	private void generate() {
		if(resetApp) {
			resetApp();
			return;
		}
		if(cOne.isSelected()) {
			if(randomCheckBox.isSelected()){
				cOneCounter = random.nextInt(21);
			} 
			if (beginningFamily.getSelectedIndex() != 0) {
				cOneCounter = beginningFamily.getSelectedIndex() -1;
			}
			word = "" + consonants[cOneCounter];
		}
		if(randomCheckBox.isSelected()) {
			vCounter = random.nextInt(5);
		} 
		if(middleFamily.getSelectedIndex() != 0) {
			vCounter = middleFamily.getSelectedIndex() - 1;
		}
		
		word = word + vowels[vCounter];
		
		if(cTwo.isSelected()) {
			if(randomCheckBox.isSelected()) {
				cTwoCounter = random.nextInt(21);
			} 
			if(endFamily.getSelectedIndex() != 0) {
				cTwoCounter = endFamily.getSelectedIndex() -1;
			}
			word = word + consonants[cTwoCounter];
		}
		if(!checkRestricted(word)) {
				label.setText(word);
				word = "";
				addCounters();
		} else {
			word = "";
			addCounters();
			generate();
		}
	}
	
	
	private boolean checkRestricted(String word) {
		boolean toBeReturned = false;
		for (int i=0; i < restricted.length; i++) {  //Filter is working fine
			if(word.matches(restricted[i])) {
				toBeReturned = true;				
			}
		}
		return toBeReturned;
	}
	
	private void addCounters() {
		if (b() && m() && e()) {
			cOneCounter++;
			if (cOneCounter == 21) {
				cTwoCounter++;
				cOneCounter = 0;
			}
			if(cTwoCounter == 21) {
				cOneCounter = 0;
				cTwoCounter = 0;
				vCounter++;
			}
			if(vCounter == 5 && !randomCheckBox.isSelected()) {
				resetApp = true;
			}
		}
		
		if(b() && m() && !e()) {
			cOneCounter++;
			if(cOneCounter == 21) {
				vCounter++;
				cOneCounter = 0;
			}
			if(vCounter == 5 && !randomCheckBox.isSelected()) {
				resetApp = true;
			}
		}
		
		if(b() && !m() && e()) {
			cOneCounter++;
			if (cOneCounter == 21) {
				cTwoCounter++;
				cOneCounter = 0;
			}
			if(cTwoCounter == 21 && !randomCheckBox.isSelected()) {
				resetApp = true;
			}
		}
		
		if(!b() && m() && e()) {
			//System.out.println("Before counter: vCounter is " + vCounter+ " and cTwoCounter is " + cTwoCounter);
			cTwoCounter++;
			if(cTwoCounter == 21) {
				cTwoCounter = 0;
				vCounter++;
			}
			//System.out.println("After counter, but before reset: vCounter is "+vCounter+ " and cTwoCounter is " + cTwoCounter);
			if(vCounter == 5 && !randomCheckBox.isSelected()) {
				resetApp = true;
			}
		}
		
		if(!b() && !m() && e()) {
			cTwoCounter++;
			if(cTwoCounter == 21 && !randomCheckBox.isSelected()) {
				resetApp = true;
			}
		}
		
		if(b() && !m() && !e()) {
			cOneCounter++;
			if(cOneCounter == 21 && !randomCheckBox.isSelected()) {
				resetApp = true;
			}
		}
		
		if(!b() && m() && !e()) {
			vCounter++;
			if(vCounter == 5 && !randomCheckBox.isSelected()) {
				resetApp = true;
			}
		}
	}
	
	private boolean b() {
		if(cOne.isSelected() && beginningFamily.getSelectedIndex() == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	private boolean m() {
		if(middleFamily.getSelectedIndex() == 0){
			return true;
		} else {
			return false;
		}
	}
	
	private boolean e() {
		if(cTwo.isSelected() && endFamily.getSelectedIndex() == 0) {
			return true;
		} else {
			return false;
		}
	}
	
		
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == generate) {
			generate();
		} else if (e.getSource() == randomCheckBox || e.getSource() == cOne || e.getSource() == cTwo ||
				e.getSource() == beginningFamily || e.getSource() == middleFamily || e.getSource() == endFamily) {
			resetApp();
		}
		
		
		
		
	}

	public static void main(String[] args) {
		
		CVCMain window = new CVCMain();
		window.resetApp();
		
	}
	
}
