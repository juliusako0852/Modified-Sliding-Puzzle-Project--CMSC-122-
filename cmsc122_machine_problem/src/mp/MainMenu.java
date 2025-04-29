package mp;
import java.util.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.util.Timer;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
public class MainMenu implements ActionListener{
	public static boolean stopTime;

	int valueHolder[] = {1,2,3,4,5,6,7,8,9};
	int moveCounter = 0;
	
	boolean puzzType = false;
	boolean musicOn = true;
	boolean sfxOn = true;
	boolean inGame = false;
	
	//icons:
	//Main Menu:
	java.net.URL newGameButton1 = getClass().getResource("/newGameButton.png");
	java.net.URL loadGameButton1 = getClass().getResource("/loadGameButton.png");
	java.net.URL settingsMainButton1 = getClass().getResource("/settingsGameMain.png");
	java.net.URL exitGameMainButton1 = getClass().getResource("/exitGameMain.png");
	java.net.URL mainMenuBG1 = getClass().getResource("/mainMenu.png");
	//Exit Menu in Main Menu:
	
	
	
	
	Clip bgmClip;
	Clip bgmClip2;
	Clip sfxClip;
	//sound effects from: https://mixkit.co/free-sound-effects/click/
	String startGameClick = "mixkit-game-click-1114.wav";
	String nonMovableClick = "mixkit-click-error-1110.wav";
	String clickSound1 = "mixkit-select-click-1109.wav";
	
	//from https://www.fesliyanstudios.com/royalty-free-music/downloads-c/8-bit-music/6
	String musicBGM = "musicBGM.wav";
	String gameMusicBGM = "gameBGM1.wav";
	String winBGM = "mixkit-video-game-win-2016.wav";
	
	
	//puzzle solution frame
	JFrame puzzleSolutionFrame = new JFrame("Modified Puzzle Solution");
	JLabel puzzleSolutionBG = new JLabel();
	JButton puzzleSolutionMenuButton = new JButton();
	
	//win frame
	JFrame youWinFrame = new JFrame("Modified Puzzle Solution");
	JLabel youWinBG = new JLabel();
	JButton okayWinButton = new JButton();
	JLabel winMove = new JLabel(""+moveCounter);
	
	//actual puzzle tiles:
	JPanel puzzlePanel = new JPanel();
	JLabel puzzlePanelBG = new JLabel();
	JButton tile0 = new JButton ();
	JButton tile1 = new JButton ();
	JButton tile2 = new JButton ();
	JButton tile3 = new JButton ();
	JButton tile4 = new JButton ();
	JButton tile5 = new JButton ();
	JButton tile6 = new JButton ();
	JButton tile7 = new JButton ();
	JButton tile8 = new JButton ();
	JFrame frame = new JFrame("Modified Sliding Puzzle Game");
	
	//game exit frame:
	JFrame inGameExitFrame = new JFrame("Modified SLiding Puzzle Game");
	JLabel inGameExitBG = new JLabel();
	JButton inGameExitYesButton = new JButton();
	JButton inGameExitNoButton = new JButton();
	
	//game play Frame:
	JLabel moveNum = new JLabel(""+moveCounter, SwingConstants.LEFT);
	JFrame onGameFrame = new JFrame("Modified Sliding Puzzle Game");
	JLabel onGameBG = new JLabel();
	JButton onGameMenuButton = new JButton();
	JButton onGameSettingsButton = new JButton();
	JButton onGameRequestButton = new JButton();
	JButton onGameExitButton = new JButton();
	
	//main menu option during game
	JFrame onGameMainMenuFrame = new JFrame("Modified Sliding Puzzle Game");
	JLabel onGameMainMenuBG = new JLabel();
	JButton onGameMainMenuYesButton = new JButton();
	JButton onGameMainMenuNoButton = new JButton();
	
	//settings:
	JFrame settingsFrame = new JFrame("Modified Sliding Puzzle Game");
	JLabel settingsBG = new JLabel();
	JButton musicButton = new JButton();
	JButton sfxButton = new JButton();
	JButton puzzleTypeButton = new JButton();
	JButton okaySettingsButton = new JButton();
	
	//buttons:
	JLabel l = new JLabel();
	JButton startGameButton = new JButton();
	JButton loadGameButton = new JButton();
	JButton settingsGameButton = new JButton();
	JButton exitGameButton = new JButton();
	
	JButton yesExit = new JButton();
	JButton noExit = new JButton();
	JLabel exitBG = new JLabel();
	
	JFrame exitMainWindow = new JFrame("Modified Sliding Puzzle Game");
	
	ImageIcon mainMenuBG = new ImageIcon("mainMenu.png");
	
	//Unclicked Buttons
	ImageIcon startButtonMain1 = new ImageIcon("startGameMain.png");
	ImageIcon settingsButtonMain1 = new ImageIcon("settingsGameMain.png");
	ImageIcon exitButtonMain1 = new ImageIcon("exitGameMain.png");
	
	//the method named isSolvable, countInversions, and findBlank index is
	//based on the code from 
	//https://developerslogblog.wordpress.com/2020/04/01/how-to-shuffle-an-slide-puzzle/
    public static boolean isSolvable(int[] puzzle) {
        int inversionCount = countInversions(puzzle);
        int blankIndex = findBlankIndex(puzzle);

        // If the puzzle size is even
        if (puzzle.length % 2 == 0) {
            // If the blank tile is on an even row counting from the bottom
            if ((puzzle.length - blankIndex) % 2 == 0) {
                return inversionCount % 2 == 0;
            } else {
                return inversionCount % 2 != 0;
            }
        }
        // If the puzzle size is odd, check if the inversion count is even
        return inversionCount % 2 == 0;
    }

    private static int countInversions(int[] array) {
        int inversions = 0;
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if (array[i] > array[j] && array[j] != 9 && array[i] != 9) {
                    inversions++;
                }
            }
        }
        return inversions;
    }

    private static int findBlankIndex(int[] puzzle) {
        for (int i = 0; i < puzzle.length; i++) {
            if (puzzle[i] == 9) {
                return i;
            }
        }
        return -1; // Blank tile not found
    }
	
	public void shuffle() {
		for (int i=0; i<valueHolder.length;i++) {
			int s = i + (int)(Math.random()*(valueHolder.length-i));
			int temp = valueHolder[s];
			valueHolder[s] = valueHolder[i];
			valueHolder[i] = temp;
		}
		if(isSolvable(valueHolder)==true) {
			return;
		}
		else {
			shuffle();
		}
	}
	public MainMenu() {
		try {
			readData();
			loadGame();
			saveGame();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		initUI();
	}
	
	public void initUI() {
		playBGM2(musicBGM);
		frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.out.println("Window deactivated");
                try {
                bgmClip2.stop();
                bgmClip2.close();
                }
                catch(Exception e1) {}

            }
            public void windowClosed(WindowEvent e) {
                System.out.println("Window deactivated");
                try {
                bgmClip2.stop();
                bgmClip2.close();
                }
                catch(Exception e1) {}
            }
        });
		
		startGameButton.removeActionListener(this);
		loadGameButton.removeActionListener(this);
		settingsGameButton.removeActionListener(this);
		exitGameButton.removeActionListener(this);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		l.setSize(600,600);
		
		startGameButton.setSize(240, 50);
		loadGameButton.setSize(240, 50);
		settingsGameButton.setSize(240,50);
		exitGameButton.setSize(240,50);
		
		startGameButton.setLocation(180, 250);
		loadGameButton.setLocation(180, 325);
		settingsGameButton.setLocation(180,400);
		exitGameButton.setLocation(180,475);
		
		startGameButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("newGameButton.png")));
		loadGameButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("loadGameButton.png")));
		settingsGameButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("settingsGameMain.png")));
		exitGameButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("exitGameMain.png")));
		l.setIcon(new ImageIcon(getClass().getClassLoader().getResource("mainMenu.png")));
		
		startGameButton.addActionListener(this);
		loadGameButton.addActionListener(this);
		settingsGameButton.addActionListener(this);
		exitGameButton.addActionListener(this);
			
		frame.add(startGameButton);
		frame.add(loadGameButton);
		frame.add(settingsGameButton);
		frame.add(exitGameButton);
		frame.add(l);
		
		frame.setLayout(null);
		frame.setSize(615,630);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		
		frame.setVisible(true);
		
	}
	
	public void showExitMain() {
		
		yesExit.removeActionListener(this);
		noExit.removeActionListener(this);
		
		exitMainWindow.setSize(615,630);
		exitMainWindow.setLayout(null);
		exitMainWindow.setLocationRelativeTo(null);
		exitMainWindow.setResizable(false);
		
		exitMainWindow.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.out.println("Window deactivated");
                try {
                bgmClip2.stop();
                bgmClip2.close();
                }
                catch(Exception e1) {}

            }
            public void windowClosed(WindowEvent e) {
                System.out.println("Window deactivated");
                try {
                bgmClip2.stop();
                bgmClip2.close();
                }
                catch(Exception e1) {}
            }
        });
		
		yesExit.setBounds(150,325, 120, 50);
		noExit.setBounds(340,325, 120, 50);
		
		yesExit.setIcon(new ImageIcon(getClass().getClassLoader().getResource("yes1.png")));
		noExit.setIcon(new ImageIcon(getClass().getClassLoader().getResource("no1.png")));
		
		yesExit.addActionListener(this);
		noExit.addActionListener(this);
	
		exitBG.setSize(600,600);
		exitBG.setIcon(new ImageIcon(getClass().getClassLoader().getResource("mainMenuExit.png")));
		
		
		exitMainWindow.add(yesExit);
		exitMainWindow.add(noExit);
		frame.setVisible(false);
		exitMainWindow.add(exitBG);
		exitMainWindow.setVisible(true);	
		
	}
	
	public void showSettingMain() {
		puzzleTypeButton.removeActionListener(this);
		musicButton.removeActionListener(this);
		sfxButton.removeActionListener(this);
		okaySettingsButton.removeActionListener(this);
		
		settingsFrame.setSize(615,630);
		settingsFrame.setLayout(null);
		settingsFrame.setLocationRelativeTo(null);
		settingsFrame.setResizable(false);
		
		settingsFrame.setDefaultCloseOperation(WindowConstants. DO_NOTHING_ON_CLOSE);  
		
		puzzleTypeButton.setBounds(320,215,180,60);
		if(!puzzType) {
			puzzleTypeButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("imageButton.png")));
		}
		else {
			puzzleTypeButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("digitsButton.png")));
		}
		
		musicButton.setBounds(320,280,180,60);
		if(musicOn) {
			musicButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("onButton.png")));
		}
		else {
			musicButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("offButton.png")));
		}
		
		sfxButton.setBounds(320,345,180,60);
		if(sfxOn) {
			sfxButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("onButton.png")));
		}
		else {
			sfxButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("offButton.png")));
		}
		
		okaySettingsButton.setBounds(200,425,180,75);
		okaySettingsButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("okay1.png")));
		
		settingsBG.setSize(600,600);
		settingsBG.setIcon(new ImageIcon(getClass().getClassLoader().getResource("settingsFrame.png")));
		
		//addActionListener:
		puzzleTypeButton.addActionListener(this);
		musicButton.addActionListener(this);
		sfxButton.addActionListener(this);
		okaySettingsButton.addActionListener(this);
		
		settingsFrame.add(puzzleTypeButton);
		settingsFrame.add(musicButton);
		settingsFrame.add(sfxButton);
		settingsFrame.add(okaySettingsButton);
		settingsFrame.add(settingsBG);
		
		if(inGame) {
			onGameFrame.setVisible(false);
		}
		else {
			frame.setVisible(false);
		}
		settingsFrame.setVisible(true);
	}
	
	public void setTiles(boolean puzType) {
		String[] iconI = {"i1.png","i2.png","i3.png","i4.png",
				"i5.png","i6.png","i7.png","i8.png", "blank.png"
		};
		String[] iconD = {
				"d1.png","d2.png","d3.png","d4.png",
				"d5.png","d6.png","d7.png","d8.png", "blank.png"
		};
		
		if (puzzType == false) {
			
			tile0.setIcon(new ImageIcon(getClass().getClassLoader().getResource(iconI[valueHolder[0]-1])));
			tile1.setIcon(new ImageIcon(getClass().getClassLoader().getResource(iconI[valueHolder[1]-1])));
			tile2.setIcon(new ImageIcon(getClass().getClassLoader().getResource(iconI[valueHolder[2]-1])));
			tile3.setIcon(new ImageIcon(getClass().getClassLoader().getResource(iconI[valueHolder[3]-1])));
			tile4.setIcon(new ImageIcon(getClass().getClassLoader().getResource(iconI[valueHolder[4]-1])));
			tile5.setIcon(new ImageIcon(getClass().getClassLoader().getResource(iconI[valueHolder[5]-1])));
			tile6.setIcon(new ImageIcon(getClass().getClassLoader().getResource(iconI[valueHolder[6]-1])));
			tile7.setIcon(new ImageIcon(getClass().getClassLoader().getResource(iconI[valueHolder[7]-1])));
			tile8.setIcon(new ImageIcon(getClass().getClassLoader().getResource(iconI[valueHolder[8]-1])));
			
		}
		else {
			tile0.setIcon(new ImageIcon(getClass().getClassLoader().getResource(iconD[valueHolder[0]-1])));
			tile1.setIcon(new ImageIcon(getClass().getClassLoader().getResource(iconD[valueHolder[1]-1])));
			tile2.setIcon(new ImageIcon(getClass().getClassLoader().getResource(iconD[valueHolder[2]-1])));
			tile3.setIcon(new ImageIcon(getClass().getClassLoader().getResource(iconD[valueHolder[3]-1])));
			tile4.setIcon(new ImageIcon(getClass().getClassLoader().getResource(iconD[valueHolder[4]-1])));
			tile5.setIcon(new ImageIcon(getClass().getClassLoader().getResource(iconD[valueHolder[5]-1])));
			tile6.setIcon(new ImageIcon(getClass().getClassLoader().getResource(iconD[valueHolder[6]-1])));
			tile7.setIcon(new ImageIcon(getClass().getClassLoader().getResource(iconD[valueHolder[7]-1])));
			tile8.setIcon(new ImageIcon(getClass().getClassLoader().getResource(iconD[valueHolder[8]-1])));
		}
	}
	
	public void startGame() {
		playBGM(gameMusicBGM);
		onGameFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try {
                bgmClip.stop();
                bgmClip.close();
                }
                catch(Exception e1) {}

            }
            public void windowClosed(WindowEvent e) {
                try {
                bgmClip.stop();
                bgmClip.close();
                }
                catch(Exception e1) {}
            }
        });
		
		setTiles(puzzType);
		moveNum.setText("" + moveCounter);
		moveNum.setBounds(400,28,300,50);
		moveNum.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 60));
		moveNum.setForeground(Color.white);
		puzzlePanelBG.setSize(320,320);
		puzzlePanel.setSize(320,320);
		puzzlePanel.setLocation(150,150);
		puzzlePanel.setLayout(null);
		puzzlePanelBG.setIcon(new ImageIcon(getClass().getClassLoader().getResource("puzBGFrame.png")));
		
		onGameMenuButton.removeActionListener(this);
		onGameSettingsButton.removeActionListener(this);
		onGameRequestButton.removeActionListener(this);
		onGameExitButton.removeActionListener(this);
		puzzleSolutionMenuButton.removeActionListener(this);
		tile0.removeActionListener(this);
		tile1.removeActionListener(this);
		tile2.removeActionListener(this);
		tile3.removeActionListener(this);
		tile4.removeActionListener(this);
		tile5.removeActionListener(this);
		tile6.removeActionListener(this);
		tile7.removeActionListener(this);
		tile8.removeActionListener(this);
		
		tile0.setBounds(10,10,100,100);
		tile1.setBounds(110,10,100,100);
		tile2.setBounds(210,10,100,100);
		tile3.setBounds(10,110,100,100);
		tile4.setBounds(110,110,100,100);
		tile5.setBounds(210,110,100,100);
		tile6.setBounds(10,210,100,100);
		tile7.setBounds(110,210,100,100);
		tile8.setBounds(210,210,100,100);

		tile0.addActionListener(this);
		tile1.addActionListener(this);
		tile2.addActionListener(this);
		tile3.addActionListener(this);
		tile4.addActionListener(this);
		tile5.addActionListener(this);
		tile6.addActionListener(this);
		tile7.addActionListener(this);
		tile8.addActionListener(this);
	
		onGameFrame.setSize(615,630);
		onGameFrame.setLayout(null);
		onGameFrame.setLocationRelativeTo(null);
		onGameFrame.setResizable(false);
		
		onGameMenuButton.setVisible(inGame);
		onGameSettingsButton.setVisible(inGame);
		onGameRequestButton.setVisible(inGame);
		onGameExitButton.setVisible(inGame);
		
		onGameBG.setSize(600,600);
		onGameBG.setIcon(new ImageIcon(getClass().getClassLoader().getResource("gameFrame2.png")));
		
		onGameMenuButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("gameMainMenuButton1.png")));
		onGameSettingsButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("gameSettingsButton1.png")));
		onGameRequestButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("gameRequestButton1.png")));
		onGameExitButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("gameExitButton1.png")));
		
		
		onGameMenuButton.setBounds(5,540,140,50);
		onGameSettingsButton.setBounds(5+140+9,540,140,50);
		onGameRequestButton.setBounds(5+140+9+140+9,540,140,50);
		onGameExitButton.setBounds(5+280+9+9+140+9,540,140,50);
		
		onGameMenuButton.addActionListener(this);
		onGameSettingsButton.addActionListener(this);
		onGameRequestButton.addActionListener(this);
		onGameExitButton.addActionListener(this);
		
		frame.dispose();
		
		onGameFrame.add(puzzleSolutionMenuButton);
		puzzlePanel.add(tile0);
		puzzlePanel.add(tile1);
		puzzlePanel.add(tile2);
		puzzlePanel.add(tile3);
		puzzlePanel.add(tile4);
		puzzlePanel.add(tile5);
		puzzlePanel.add(tile6);
		puzzlePanel.add(tile7);
		puzzlePanel.add(tile8);
		puzzlePanel.add(puzzlePanelBG);
		
		
		onGameFrame.add(puzzlePanel);
		onGameFrame.add(onGameMenuButton);
		onGameFrame.add(onGameSettingsButton);
		onGameFrame.add(onGameRequestButton);
		onGameFrame.add(onGameExitButton);
		onGameFrame.add(moveNum);
		onGameFrame.add(onGameBG);
		
		onGameFrame.setVisible(true);
	
	}
	
	public void showMainMenuGameFrame() {
		onGameMainMenuYesButton.removeActionListener(this);
		onGameMainMenuNoButton.removeActionListener(this);
		
		onGameMainMenuFrame.setSize(615,630);
		onGameMainMenuFrame.setLayout(null);
		onGameMainMenuFrame.setLocationRelativeTo(null);
		onGameMainMenuFrame.setResizable(false);
		
		onGameMainMenuBG.setSize(600,600);
		onGameMainMenuBG.setIcon(new ImageIcon(getClass().getClassLoader().getResource("gameMenuFrame.png")));
		
		onGameMainMenuYesButton.setBounds(150,325, 120, 50);
		onGameMainMenuNoButton.setBounds(340,325, 120, 50);
		
		onGameMainMenuYesButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("yes1.png")));
		onGameMainMenuNoButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("no1.png")));
	
		onGameMainMenuYesButton.addActionListener(this);
		onGameMainMenuNoButton.addActionListener(this);
		
		onGameMainMenuFrame.add(onGameMainMenuYesButton);
		onGameMainMenuFrame.add(onGameMainMenuNoButton);
		onGameMainMenuFrame.add(onGameMainMenuBG);
		
		onGameMainMenuFrame.setVisible(true);
	}
	
	public void showInGameExit() {
		
		inGameExitYesButton.removeActionListener(this);
		inGameExitNoButton.removeActionListener(this);
		
		inGameExitFrame.setSize(615,630);
		inGameExitFrame.setLayout(null);
		inGameExitFrame.setLocationRelativeTo(null);
		inGameExitFrame.setResizable(false);
		
		inGameExitBG.setSize(600,600);
		inGameExitBG.setIcon(new ImageIcon(getClass().getClassLoader().getResource("gameExitFrame.png")));
		
		inGameExitYesButton.setBounds(150,325, 120, 50);
		inGameExitNoButton.setBounds(340,325, 120, 50);
		
		inGameExitYesButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("yes1.png")));
		inGameExitNoButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("no1.png")));
	
		inGameExitYesButton.addActionListener(this);
		inGameExitNoButton.addActionListener(this);
		
		inGameExitFrame.add(inGameExitYesButton);
		inGameExitFrame.add(inGameExitNoButton);
		inGameExitFrame.add(inGameExitBG);
		
		inGameExitFrame.setVisible(true);
	}
	public void checkWin() {
		okayWinButton.removeActionListener(this);
		if(valueHolder[0] == 1 && valueHolder[1] == 2 && valueHolder[2] == 3
			&& valueHolder[3] == 4 && valueHolder[4] == 5 && valueHolder[6] == 7
			&& valueHolder[7] == 8 && valueHolder[8] == 9) {
			playClick(winBGM);
			inGame = false;
			onGameFrame.dispose();
			
			winMove.setText(""+moveCounter);
			winMove.setBounds(370,265,200,50);
			winMove.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 60));
			winMove.setForeground(Color.white);
			
			
			youWinFrame.setSize(615,630);
			youWinFrame.setLayout(null);
			youWinFrame.setLocationRelativeTo(null);
			youWinFrame.setResizable(false);
			
			okayWinButton.setBounds(105, 400, 400, 75);
			okayWinButton.addActionListener(this);
			okayWinButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("puzzleSolvedMenu.png")));
			
			
			youWinBG.setIcon(new ImageIcon(getClass().getClassLoader().getResource("winFrame2.png")));
			youWinBG.setSize(600,600);
			
			youWinFrame.add(okayWinButton);
			youWinFrame.add(winMove);
			youWinFrame.add(youWinBG);
			youWinFrame.setVisible(true);
			}
	}
	
	public void writeData () throws IOException {
		BufferedWriter writerData = new BufferedWriter(new FileWriter("game_data.txt"));
		writerData.write("");
		writerData.write(String.valueOf(puzzType) + "/" + String.valueOf(musicOn)
		+ "/" + String.valueOf(sfxOn) +"\n");
		writerData.close();
	}
	
	public void readData() throws IOException{
		BufferedReader readerData = new BufferedReader(new FileReader("game_data.txt"));
		String line = readerData.readLine();
		String lineArr[] = line.split("/",-2);
		puzzType = Boolean.valueOf(lineArr[0]);
		musicOn = Boolean.valueOf(lineArr[1]);
		sfxOn = Boolean.valueOf(lineArr[2]);
		
		for (int i = 0; i<lineArr.length; i++) {
			System.out.println(lineArr[i]);
		}
		readerData.close();
	}
	
	public void saveGame() throws IOException {
		BufferedWriter saveGame = new BufferedWriter(new FileWriter("game_save.txt"));
		saveGame.write("");
		saveGame.write(String.valueOf(valueHolder[0]) + "/" + String.valueOf(valueHolder[1])
		+ "/" + String.valueOf(valueHolder[2]) + "/" + String.valueOf(valueHolder[3]) + "/"
		+  String.valueOf(valueHolder[4]) + "/" + String.valueOf(valueHolder[5]) + "/"
		+  String.valueOf(valueHolder[6]) + "/" + String.valueOf(valueHolder[7]) + "/"
		+  String.valueOf(valueHolder[8]) +  "/" + String.valueOf(moveCounter) + "\n");
		saveGame.close();
	}
	
	public void loadGame() throws IOException{
		BufferedReader loadGame = new BufferedReader(new FileReader("game_save.txt"));
		String line = loadGame.readLine();
		String lineArr[] = line.split("/",-2);
		
		for(int n=0; n<lineArr.length-1; n++) {
			valueHolder[n] = Integer.valueOf(lineArr[n]);
		}
		moveCounter = Integer.valueOf(lineArr[9]);
		moveNum.setText(""+moveCounter);
		for (int i = 0; i<lineArr.length-1; i++) {
			System.out.println(valueHolder[i]);
		}
		loadGame.close();
	}
	
	public void testWin() {
		for (int i=0; i<valueHolder.length; i++) {
			if(i<8) {
				valueHolder[i] = i+1;
			}
			else {
				valueHolder[8] = 8;
				valueHolder[7] = 9;
			}
			
		}
		System.out.println("You Win!");
	}
	
	public void testWin2() {

		valueHolder[0] = 1;
		valueHolder[1] = 2;
		valueHolder[2] = 3;
		valueHolder[3] = 4;
		valueHolder[4] = 5;
		valueHolder[5] = 9;
		valueHolder[6] = 7;
		valueHolder[7] = 8;
		valueHolder[8] = 6;
		System.out.println("You Win!");
	}
	
	public void showPuzzleSolution() {
		puzzleSolutionMenuButton.removeActionListener(this);
		tile0.removeActionListener(this);
		tile1.removeActionListener(this);
		tile2.removeActionListener(this);
		tile3.removeActionListener(this);
		tile4.removeActionListener(this);
		tile5.removeActionListener(this);
		tile6.removeActionListener(this);
		tile7.removeActionListener(this);
		tile8.removeActionListener(this);
		onGameMenuButton.removeActionListener(this);
		onGameSettingsButton.removeActionListener(this);
		onGameRequestButton.removeActionListener(this);
		onGameExitButton.removeActionListener(this);
		puzzleSolutionMenuButton.setVisible(true);
		
		puzzleSolutionMenuButton.setBounds(105, 500, 400, 75);
		puzzleSolutionMenuButton.addActionListener(this);
		puzzleSolutionMenuButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("puzzleSolvedMenu.png")));
			
		
		onGameFrame.remove(onGameMenuButton);
		onGameFrame.remove(onGameSettingsButton);
		onGameFrame.remove(onGameRequestButton);
		onGameFrame.remove(onGameExitButton);
		
	}
	 
	public void playBGM(String soundFilePath) {
		if(musicOn) {
			try {
				AudioInputStream audioInputStream = AudioSystem.getAudioInputStream
						(getClass().getClassLoader().getResource(soundFilePath));
		        bgmClip = AudioSystem.getClip();
		        bgmClip.open(audioInputStream);
		        bgmClip.loop(bgmClip.LOOP_CONTINUOUSLY);
		    } catch (Exception e3) {
		        e3.printStackTrace();
		    }
		}
		 
    }
	
	public void playBGM2(String soundFilePath) {
		if(musicOn) {
			try {
				AudioInputStream audioInputStream = AudioSystem.getAudioInputStream
						(getClass().getClassLoader().getResource(soundFilePath));
		        bgmClip2 = AudioSystem.getClip();
		        bgmClip2.open(audioInputStream);
		        bgmClip2.loop(bgmClip.LOOP_CONTINUOUSLY);
		    } catch (Exception e3) {
		        e3.printStackTrace();
		    }
		}
		 
    }
	
	//sound effects
	public void playClick(String soundFilePath) {
		if(sfxOn) {
			try {
				 AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
	                        getClass().getClassLoader().getResource(soundFilePath)
	                );

	                Clip clip = AudioSystem.getClip();
	                clip.open(audioInputStream);
	                clip.start();
		    } catch (Exception e3) {
		        e3.printStackTrace();
		    }
		}
		 
    }
	
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getSource() == loadGameButton) {
			try {
				playClick(startGameClick);
				writeData ();
				readData();
				loadGame();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			inGame = true;
            startGame();
		}
		
		if(e.getSource() == startGameButton) {
			moveCounter =0;
			try {
				playClick(startGameClick);
				writeData ();
				readData();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			inGame = true;
			
			shuffle();
            startGame();
            testWin2();
            setTiles(true);
            
			
		}
		if(e.getSource() == settingsGameButton) {
			showSettingMain();
            frame.setVisible(false);
            playClick(clickSound1);
			
		}
		
		if(e.getSource() == exitGameButton) {
			playClick(clickSound1);
            showExitMain();
		}
		
		if(e.getSource() == yesExit) {
			playClick(clickSound1);
            System.exit(0);
            
		}
		
		if(e.getSource() == noExit) {
			playClick(clickSound1);
			frame.setVisible(true);
            exitMainWindow.dispose();
            
		}
		
		if(e.getSource() == puzzleTypeButton) {
			//playClick(clickSound1);
			if(puzzType) {
				setTiles(puzzType);
				onGameFrame.repaint();
				puzzType = false;
				puzzleTypeButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("imageButton.png")));
			}
			else {
				setTiles(puzzType);
				onGameFrame.repaint();
				puzzType = true;
				puzzleTypeButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("digitsButton.png")));
			}
			try {
				writeData ();
				readData();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			playClick(clickSound1);
		}
		
		if(e.getSource() == musicButton) {
			
			if(!musicOn) {
				if(inGame) {
					musicOn = true;
					musicButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("onButton.png")));
					playBGM(gameMusicBGM);
					System.out.println("in game");
					
				}
				else {
					musicOn = true;
					musicButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("onButton.png")));
					System.out.println("main menu");
					playBGM2(musicBGM);
				}
			}
			else {
				musicOn = false;
				musicButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("offButton.png")));
				
				try{
					if(inGame) {
						bgmClip.stop(); bgmClip.close();
					}
					else {
						bgmClip2.stop(); bgmClip2.close();
					}
					
				}catch(Exception e2) {};
				
				
			}
			try {
				writeData ();
				readData();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			playClick(clickSound1);
		}
		
		if(e.getSource() == sfxButton) {
			//playClick(clickSound1);
			if(!sfxOn) {
				sfxButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("onButton.png")));
				sfxOn = true;
			}
			else {
				sfxButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("offButton.png")));
				sfxOn = false;
			}
			try {
				writeData ();
				readData();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			playClick(clickSound1);
		}
		
		if(e.getSource() == okaySettingsButton) {
			//playClick(clickSound1);
			playClick(clickSound1);
            if(inGame) {
            	settingsFrame.dispose();
            	onGameFrame.setVisible(true);
 
            }
            else {
            	settingsFrame.dispose();
                frame.setVisible(true);
            }
            try {
				writeData();
				readData();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            setTiles(puzzType);
		}
		
		//for in-game buttons:
		
		if(e.getSource() == onGameMenuButton) {
			playClick(clickSound1);
            showMainMenuGameFrame();
            onGameFrame.setVisible(false);
		}
		
		if(e.getSource() == onGameMainMenuYesButton) {
			//playClick(clickSound1);
            onGameMainMenuFrame.dispose();
            onGameFrame.dispose();
            initUI();
            inGame = false;
            
            moveNum.setText(""+moveCounter);
            try {
            	bgmClip.stop();
            	bgmClip.close();
				saveGame();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            playClick(clickSound1);
            moveCounter = 0;
            
            
		}
		
		
		if(e.getSource() == onGameMainMenuNoButton) {
			onGameMainMenuFrame.dispose();
            onGameFrame.setVisible(true);
            playClick(clickSound1);
		}
		
		if(e.getSource() == onGameSettingsButton) {
			//playClick(clickSound1);
			showSettingMain();
            onGameFrame.setVisible(false);
            playClick(clickSound1);
		}
		
		if(e.getSource() == onGameRequestButton) {
			//playClick(clickSound1);
			for (int i=0;i<valueHolder.length;i++) {
				valueHolder[i] = i+1;
				
			}
			setTiles(puzzType);
			showPuzzleSolution();
			onGameFrame.repaint();
			inGame = false;
			moveCounter = 0;
			playClick(clickSound1);
		}
		
		if(e.getSource() == onGameExitButton) {
			playClick(clickSound1);
			onGameFrame.setVisible(false);
			showInGameExit();
		}
		
		if(e.getSource() == inGameExitYesButton) {
			playClick(clickSound1);
			 try {
					saveGame();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			System.exit(0);
		}
		
		if(e.getSource() == inGameExitNoButton) {
			playClick(clickSound1);
			inGameExitFrame.dispose();
			onGameFrame.setVisible(true);
			
		}
		
		if(e.getSource() == puzzleSolutionMenuButton) {
			playClick(clickSound1);
			onGameFrame.dispose();
			puzzleSolutionMenuButton.setVisible(false);
			inGame = false;
			initUI();
			puzzleSolutionMenuButton.setVisible(false);
			
		}
		
		//if the puzzle is solved:
		if(e.getSource() == okayWinButton) {
			playClick(clickSound1);
			inGame = false;
			youWinFrame.dispose();
			initUI();
		}
		
		//this code is based on the work of  Nikol Roseva
		//retrieved from https://softuni.org/project-tutorials/making-a-sliding-puzzle-in-java/
		//date of creation: October 17, 2022
		//puzzle tiles:
		if(e.getSource() == tile0){
			int temp = valueHolder[0];
			if(valueHolder[1] == 9){
				valueHolder[0] = valueHolder[1];
				valueHolder[1] = temp;
				moveCounter++;
				moveNum.setText(""+moveCounter);
				playClick(clickSound1);}
			else if(valueHolder[3]==9){
				valueHolder[0] = valueHolder[3];
				valueHolder[3] = temp;
				moveCounter++;
				moveNum.setText(""+moveCounter);
				playClick(clickSound1);}
			else {
				playClick(nonMovableClick);
			}
			System.out.println(temp + "->" + valueHolder[0]);
			setTiles(puzzType);
		}
		
		if(e.getSource() == tile1){
			int temp = valueHolder[1];
			if(valueHolder[0] == 9){
				valueHolder[1] = valueHolder[0];
				valueHolder[0] = temp;
				moveCounter++;
				moveNum.setText(""+moveCounter);
				playClick(clickSound1);}
			else if(valueHolder[2]==9){
				valueHolder[1] = valueHolder[2];
				valueHolder[2] = temp;
				moveCounter++;
				moveNum.setText(""+moveCounter);
				playClick(clickSound1);}
			else if (valueHolder[4] == 9) {
				valueHolder[1] = valueHolder[4];
				valueHolder[4] = temp;
				moveCounter++;
				moveNum.setText(""+moveCounter);
				playClick(clickSound1);
			}
			else {
				playClick(nonMovableClick);
			}
			System.out.println(temp + "->" + valueHolder[1]);
			setTiles(puzzType);
		}
		
		if(e.getSource() == tile2){
			int temp = valueHolder[2];
			if(valueHolder[1] == 9){
				valueHolder[2] = valueHolder[1];
				valueHolder[1] = temp;
				moveCounter++;
				moveNum.setText(""+moveCounter);
				playClick(clickSound1);}
			else if(valueHolder[5]==9){
				valueHolder[2] = valueHolder[5];
				valueHolder[5] = temp;
				moveCounter++;
				moveNum.setText(""+moveCounter);
				playClick(clickSound1);}
			else {
				playClick(nonMovableClick);
			}
			System.out.println(temp + "->" + valueHolder[2]);
			setTiles(puzzType);
		}
		if(e.getSource() == tile3){
			int temp = valueHolder[3];
			if(valueHolder[0] == 9){
				valueHolder[3] = valueHolder[0];
				valueHolder[0] = temp;
				moveCounter++;
				moveNum.setText(""+moveCounter);
				playClick(clickSound1);}
			else if(valueHolder[4]==9){
				valueHolder[3] = valueHolder[4];
				valueHolder[4] = temp;
				moveCounter++;
				moveNum.setText(""+moveCounter);
				playClick(clickSound1);}
			else if(valueHolder[6]==9){
				valueHolder[3] = valueHolder[6];
				valueHolder[6] = temp;
				moveCounter++;
				moveNum.setText(""+moveCounter);
				playClick(clickSound1);}
			else {
				playClick(nonMovableClick);
			}
			System.out.println(temp + "->" + valueHolder[3]);
			setTiles(puzzType);
		}
		if(e.getSource() == tile4){
			int temp = valueHolder[4];
			if(valueHolder[3] == 9){
				valueHolder[4] = valueHolder[3];
				valueHolder[3] = temp;
				moveCounter++;
				moveNum.setText(""+moveCounter);
				playClick(clickSound1);}
			else if(valueHolder[1]==9){
				valueHolder[4] = valueHolder[1];
				valueHolder[1] = temp;
				moveCounter++;
				moveNum.setText(""+moveCounter);
				playClick(clickSound1);}
			else if(valueHolder[5]==9){
				valueHolder[4] = valueHolder[5];
				valueHolder[5] = temp;
				moveCounter++;
				moveNum.setText(""+moveCounter);
				playClick(clickSound1);}
			else if(valueHolder[7]==9){
				valueHolder[4] = valueHolder[7];
				valueHolder[7] = temp;
				moveCounter++;
				moveNum.setText(""+moveCounter);
				playClick(clickSound1);}
			else {
				playClick(nonMovableClick);
			}
			System.out.println(temp + "->" + valueHolder[4]);
			setTiles(puzzType);
			}
		
		if(e.getSource() == tile5){
			int temp = valueHolder[5];
			if(valueHolder[4] == 9){
				valueHolder[5] = valueHolder[4];
				valueHolder[4] = temp;
				moveCounter++;
				moveNum.setText(""+moveCounter);
				playClick(clickSound1);}
			else if(valueHolder[2]==9){
				valueHolder[5] = valueHolder[2];
				valueHolder[2] = temp;
				moveCounter++;
				moveNum.setText(""+moveCounter);
				playClick(clickSound1);}
			else if(valueHolder[8]==9){
				valueHolder[5] = valueHolder[8];
				valueHolder[8] = temp;
				moveCounter++;
				moveNum.setText(""+moveCounter);
				playClick(clickSound1);}
			else {
				playClick(nonMovableClick);
			}
			System.out.println(temp + "->" + valueHolder[5]);
			setTiles(puzzType);
			}
		
		if(e.getSource() == tile6){
			int temp = valueHolder[6];
			if(valueHolder[3] == 9){
				valueHolder[6] = valueHolder[3];
				valueHolder[3] = temp;
				moveCounter++;
				moveNum.setText(""+moveCounter);
				playClick(clickSound1);}
			else if(valueHolder[7]==9){
				valueHolder[6] = valueHolder[7];
				valueHolder[7] = temp;
				moveCounter++;
				moveNum.setText(""+moveCounter);
				playClick(clickSound1);}
			else {
				playClick(nonMovableClick);
			}
			System.out.println(temp + "->" + valueHolder[6]);
			setTiles(puzzType);
		}
		if(e.getSource() == tile7){
			int temp = valueHolder[7];
			if(valueHolder[6] == 9){
				valueHolder[7] = valueHolder[6];
				valueHolder[6] = temp;
				moveCounter++;
				moveNum.setText(""+moveCounter);
				playClick(clickSound1);}
			else if(valueHolder[4]==9){
				valueHolder[7] = valueHolder[4];
				valueHolder[4] = temp;
				moveCounter++;
				moveNum.setText(""+moveCounter);
				playClick(clickSound1);}
			else if(valueHolder[8]==9){
				valueHolder[7] = valueHolder[8];
				valueHolder[8] = temp;
				moveCounter++;
				moveNum.setText(""+moveCounter);
				playClick(clickSound1);}
			else {
				playClick(nonMovableClick);
			}
			System.out.println(temp + "->" + valueHolder[7]);
			setTiles(puzzType);
		}
		if(e.getSource() == tile8){
			int temp = valueHolder[8];
			if(valueHolder[7] == 9){
				valueHolder[8] = valueHolder[7];
				valueHolder[7] = temp;
				moveCounter++;
				moveNum.setText(""+moveCounter);
				playClick(clickSound1);}
			else if(valueHolder[5]==9){
				valueHolder[8] = valueHolder[5];
				valueHolder[5] = temp;
				moveCounter++;
				moveNum.setText(""+moveCounter);
				playClick(clickSound1);}
			else {
				playClick(nonMovableClick);
			}
			System.out.println(temp + "->" + valueHolder[8]);
			setTiles(puzzType);
			checkWin();
		}

	}
}
