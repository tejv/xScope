package org.ykc.xscope;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import org.controlsfx.control.StatusBar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MainWindowController implements Initializable{

	public static final Logger logger = LoggerFactory.getLogger(MainWindowController.class.getName());

    @FXML
    private BorderPane bPaneMainWindow;

    @FXML
    private TextField txtTest;

    @FXML
    private Button bOne;

    @FXML
    private Button bAbout;

    @FXML
    private StatusBar statusBar;

    private Stage myStage;


    XScope lGraph;

    @FXML
    private LineChart<Number,Number> lchartData;

    @FXML
    private NumberAxis xAxis ;

    @FXML
    private NumberAxis yAxis ;

    @FXML
    private CheckBox chkGraphCC1;

    @FXML
    private CheckBox chkGraphCC2;

    @FXML
    private CheckBox chkGraphVbus;

    @FXML
    private CheckBox chkGraphAmp;

    @FXML
    private ComboBox<String> cboxGraphXScale;

    @FXML
    private Label lblGraphYValue;

    @FXML
    private Label lblGraphXValue;

    @FXML
    private Label lblGraphDeltaY;

    @FXML
    private Label lblGraphDeltaX;

    @FXML
    private Button bGraphScrollLeft;

    @FXML
    private Button bGraphScrollRight;


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Preferences.loadPreferences();
		bOne.setGraphic(new ImageView(new Image("/start_stop.png")));
		bOne.setTooltip(new Tooltip("Start"));

		bGraphScrollLeft.setGraphic(new ImageView(new Image("/arrow_left.png")));
		bGraphScrollLeft.setTooltip(new Tooltip("Previous plot"));
		bGraphScrollRight.setGraphic(new ImageView(new Image("/arrow_right.png")));
		bGraphScrollRight.setTooltip(new Tooltip("Next plot"));
		
		bAbout.setGraphic(new ImageView(new Image("/info.png")));
		bAbout.setTooltip(new Tooltip("About xScope"));

		lGraph = new XScope(lchartData, xAxis, yAxis, cboxGraphXScale, bGraphScrollLeft, bGraphScrollRight,
				chkGraphCC1, chkGraphCC2, chkGraphVbus, chkGraphAmp, lblGraphYValue, lblGraphXValue, lblGraphDeltaY,
				lblGraphDeltaX);

		Platform.runLater(() -> {
			 handleArgs();
       });

		txtTest.setOnAction(e -> lGraph.displaySpecificTimeWindow(Utils.castLongtoUInt(Utils.parseStringtoNumber(txtTest.getText())) ));

	}

    private void handleArgs() {
//		if(Main.arg.length > 0){
//			File f = new File(Main.arg[0]);
//			if(f.exists()){
//				if(Utils.getFileExtension(f).equals("ucx1")){
//
//            	}
//			}
//		}
	}

    @FXML
    void bOneClicked(ActionEvent event) {
    	loadData();
    }

    private void loadData() {
    	lGraph.setDataPoints(CreateDataSet.getDataSet());
	}

	@FXML
    void showAboutMe(ActionEvent event) {
    	displayAboutMe();
    }

	private void displayAboutMe() {
		Properties prop = new Properties();
		InputStream input = null;
		try {
			input = getClass().getResource("/version.properties").openStream();
			prop.load(input);
			String ver = prop.getProperty("MAJOR_VERSION") + "."+ prop.getProperty("MINOR_VERSION") + "." + prop.getProperty("BUILD_NO");
			MsgBox.display("About Me", "xScope -> Real Time scope\nVersion: "+ ver +"\nAuthor: Tejender Sheoran\nEmail: tejendersheoran@gmail.com\nCopyright(C) (2016-2018) Tejender Sheoran\nThis program is free software. You can redistribute it and/or modify it\nunder the terms of the GNU General Public License Ver 3.\n<http://www.gnu.org/licenses/>");

		} catch (IOException e) {

		}
		finally{
			if(input != null){
				try {
					input.close();
				} catch (IOException e) {
				}
			}
		}
	}

	private void appClosing(){
		Preferences.storePreferences();
	}

	public void setStage(Stage stage) {
	    myStage = stage;
		myStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we) {
		    	  appClosing();
		    	  /* TODO: Close all threads */
		    	  try {
						Thread.sleep(50);
					  } catch (InterruptedException e) {
					  	}
				Platform.exit();
				System.exit(0);
		     }
		 });
	}

}




