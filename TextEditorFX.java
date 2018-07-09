//package application;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import javafx.application.Application;
import javafx.event.*;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/*
 * Wyatt Hoodes
 * +Assignment13
 * Core Concept: the ability to perform file I/O using javafx
 * Date: 04/08/18
 */
public class TextEditorFX extends Application {

	//Create keyboard shortcuts for the menu items.
	public static final KeyCombination saveShortcut =
			new KeyCodeCombination(KeyCode.S, KeyCombination.SHORTCUT_DOWN);
	
	public static final KeyCombination openShortcut = 
			new KeyCodeCombination(KeyCode.O, KeyCombination.SHORTCUT_DOWN);
	
	public void start(Stage stage) {

		//Create a TextArea and turn on the WrapText function.
		TextArea textEdit = new TextArea();
		textEdit.setWrapText(true);
		
		//Create a menu bar, a file menu, and open and save menu items.
		MenuBar mainMenu = new MenuBar();
		final Menu file = new Menu("File");
		
		MenuItem open = new MenuItem("Open...\t\t\t\t");
		open.setAccelerator(openShortcut);
		
		MenuItem save = new MenuItem("Save...");
		save.setAccelerator(saveShortcut);
		
		
		//Anonymous Event handler for the "open..." menu item.
		open.setOnAction(new EventHandler <ActionEvent>() {
			public void handle(ActionEvent actionEvent) {
				
				//Create a new FileChooser and assign a new file to the FileChooser dialog.
				FileChooser myFile = new FileChooser();
				File file = myFile.showOpenDialog(stage);
				
				//if the file is not null, empty the TextArea, read in each line 
				//from the file with the Scanner. 
				if(file != null) {
					textEdit.setText(null);
					try {
						Scanner input = new Scanner(file);
						
						while(input.hasNext()) {
							textEdit.appendText(input.nextLine() + "\n");
						}
						input.close();
					}catch(IOException e) {
						System.out.println("An error occured.");
					}
				}
			}
		});
		
		//Anonymous Event handler for the "save..." menu item.
		save.setOnAction(new EventHandler <ActionEvent>() {
			public void handle(ActionEvent actionEvent) {
				
				//Create a new FileChooser and assign a new file to the FileChooser dialog.
				FileChooser myFile = new FileChooser();
				File file = myFile.showSaveDialog(stage);
				
				//if the file is not null, create a new file if it does not exist.
				//print the contents of the textArea to the file. Close the output.
				if(file != null) {
					try {
						file.createNewFile();
						PrintWriter output = new PrintWriter(file);
						output.print(textEdit.getText());
						output.close();
					}catch(IOException e) {
						System.out.println("An error occured.");
					}
				}
			}
		});
		
		//Add the items to the file menu, add the file menu to the bar, display the menu bar
		file.getItems().addAll(open, save);
		mainMenu.getMenus().add(file);
		mainMenu.setUseSystemMenuBar(true);
		
		/*
		 * Create a BorderPane as the root node.
		 * Add the main menu as the top child.
		 * Place the textEdit node as the center child.
		 * Adjusted the padding and the border color. 
		 * Create a scene and place the root BorderPane node.
		*/
		BorderPane root = new BorderPane();
		root.setTop(mainMenu);
		root.setCenter(textEdit);
		root.setStyle("-fx-padding: 10 20 10 20; -fx-border-color: black;");
		Scene scene = new Scene(root, 400, 400);
		
		//set the stage and display.
		stage.setTitle("J Notes 2.0.1");
		stage.setScene(scene);
		stage.show();
		
	}
	
}