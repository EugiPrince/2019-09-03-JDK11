/**
 * Sample Skeleton for 'Food.fxml' Controller Class
 */

package it.polito.tdp.food;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import it.polito.tdp.food.model.Model;
import it.polito.tdp.food.model.PorzioneAdiacente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FoodController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtCalorie"
    private TextField txtCalorie; // Value injected by FXMLLoader

    @FXML // fx:id="txtPassi"
    private TextField txtPassi; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="btnCorrelate"
    private Button btnCorrelate; // Value injected by FXMLLoader

    @FXML // fx:id="btnCammino"
    private Button btnCammino; // Value injected by FXMLLoader

    @FXML // fx:id="boxPorzioni"
    private ComboBox<String> boxPorzioni; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCammino(ActionEvent event) {
    	txtResult.clear();
    	
    	String partenza = this.boxPorzioni.getValue();
    	Integer N;
    	
    	if(partenza == null) {
    		this.txtResult.appendText("Devi selezionare una porzione");
    		return;
    	}
    	
    	try {
    		N = Integer.parseInt(this.txtPassi.getText());
    	} catch(NumberFormatException e) {
    		this.txtResult.appendText("Inserisci un valore valido");
    		return;
    	}
    	
    	this.model.trovaPercorso(partenza, N);
    	if(this.model.getBest() == null) {
    		this.txtResult.appendText("Non ho trovato un cammino di lunghezza N");
    	}
    	else {
    		this.txtResult.appendText("Cammino massimo trovato di peso: "+this.model.getPesoMax()+"\n");
    		for(String v : this.model.getBest()) {
    			this.txtResult.appendText(v+"\n");
    		}
    	}
    }

    @FXML
    void doCorrelate(ActionEvent event) {
    	txtResult.clear();
    	
    	String partenza = this.boxPorzioni.getValue();
    	if(partenza == null) {
    		this.txtResult.appendText("Devi selezionare una porzione");
    		return;
    	}
    	
    	List<PorzioneAdiacente> adiacenti = this.model.getAdiacenti(partenza);
    	this.txtResult.appendText("Porzioni correlate a "+partenza+"\n");
    	for(PorzioneAdiacente pa : adiacenti)
    		this.txtResult.appendText(String.format("Nome: %s, peso: %f\n", pa.getNomePorzione(), pa.getPeso()));
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	
    	Integer C;
    	try {
    		C = Integer.parseInt(this.txtCalorie.getText());
    	} catch(NumberFormatException e) {
    		this.txtResult.appendText("Scrivi il numero di calorie nel formato corretto");
    		return;
    	}
    	
    	String msg = this.model.creaGrafo(C);
    	this.boxPorzioni.getItems().clear();
    	this.boxPorzioni.getItems().addAll(this.model.getVertici());
    	
    	this.txtResult.appendText(msg);
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtCalorie != null : "fx:id=\"txtCalorie\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtPassi != null : "fx:id=\"txtPassi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCorrelate != null : "fx:id=\"btnCorrelate\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCammino != null : "fx:id=\"btnCammino\" was not injected: check your FXML file 'Food.fxml'.";
        assert boxPorzioni != null : "fx:id=\"boxPorzioni\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Food.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
