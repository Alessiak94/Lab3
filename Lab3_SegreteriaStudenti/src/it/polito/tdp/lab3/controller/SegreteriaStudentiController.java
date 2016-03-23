package it.polito.tdp.lab3.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.lab3.model.Modello;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class SegreteriaStudentiController {

	private Modello model;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox<String> chcCorso;

    @FXML
    private TextField txtMatricola;

    @FXML
    private Button btnOk;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtCognome;

    @FXML
    private TextArea txtRisultato;

    
   

	@FXML
    void doCerca(ActionEvent event) {
		
		//Ricerca corsi dello studente
		if(chcCorso.getValue().length()==0)
		{txtRisultato.clear();
			if(model.cercaStudente(txtMatricola.getText()).length()==0){
				txtNome.setText("");
				txtCognome.setText("");
			txtRisultato.setText("Studente non Trovato");}
		else {
			txtRisultato.setText(model.getStudente(txtMatricola.getText()).getCorsi().toString());
			if(txtRisultato.getText().compareTo("[null]")==0){
				txtRisultato.clear();
				txtRisultato.setText("Lo studente non e' iscritto a nessun corso");
			}	}
		}
		//Rierca Studenti iscritti al corso
		if(txtMatricola.getText().length()==0)
		{txtRisultato.clear();
			if(chcCorso.getValue().length()==0)
			txtRisultato.setText("Inserire uno Studente o scegliere un corso");
		else {String[] s=chcCorso.getValue().split("-");
				txtRisultato.setText(model.getCorsoPerNome(s[1]).getStudenti().toString());
				if(txtRisultato.getText().compareTo("[null]")==0){
					txtRisultato.clear();
					txtRisultato.setText("Al corso non e' ncora iscritto nessuno studente");
				}}
		}
		//Ricerca studente e corso
		if(txtMatricola.getText().length()!=0&&chcCorso.getValue().length()!=0)
		{iscrittoAlCorso();
		}
    }
	
	public boolean iscrittoAlCorso(){
		{txtRisultato.clear();
		 String[] s= chcCorso.getValue().split("-");
			try{if(model.getStudente(txtMatricola.getText()).getCorsi().contains(model.getCorsoPerNome(s[1]))==true){
				txtRisultato.setText("Lo studente e' iscritto al corso");
				return true;}
			else txtRisultato.setText("Lo studente non e' ancora iscritto al corso");}
			catch(NullPointerException e){
				txtRisultato.setText("Lo studente non esiste");
				txtNome.setText("");
				txtCognome.setText("");
			}
			return false;
		}
	}

    @FXML
    void doIscrivi(ActionEvent event) {
    	if(txtMatricola.getText().length()!=0&&chcCorso.getValue().length()!=0){
    		if(iscrittoAlCorso()==true)
    			txtRisultato.setText("Lo studente e' gia iscritto al corso");
    		else {
    			if(model.cercaStudente(txtMatricola.getText()).length()==0){
    				txtNome.setText("");
    				txtCognome.setText("");
    				txtRisultato.setText("Lo studente non esiste");}
    			else{
    				 String[] s= chcCorso.getValue().split("-");
    				model.associa(model.getStudente(txtMatricola.getText()), model.getCorsoPerNome(s[1]));
    				txtRisultato.setText("Lo studente e' stato iscritto");
    			}
    		}
    	}
    		
    	

    }
    
    
    
    @FXML
    void doOk(ActionEvent event) {
    	String matricola= txtMatricola.getText();
    	String info=model.cercaStudente(matricola);
    	if(info.length()==0)
    		txtRisultato.setText("Studente non presente");
    	else{
    		String[] s=info.split("-");
    		try{
    			txtNome.setText(s[0]);
    			txtCognome.setText(s[1]);
    		}catch(Exception e){}
    	}
    }

    @FXML
    void doReset(ActionEvent event) {
    	chcCorso.setValue("");
    	txtMatricola.setText("");
    	txtNome.setText("");
    	txtCognome.setText("");
    	txtRisultato.setText("");

    }
    
    public void setModel(Modello model){
    	this.model=model;
        chcCorso.getItems().addAll(model.trovaCorsi());
    }
   
    

    @FXML
    void initialize() {
        assert chcCorso != null : "fx:id=\"chcCorso\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert txtMatricola != null : "fx:id=\"txtMatricola\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert btnOk != null : "fx:id=\"btnOk\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert txtNome != null : "fx:id=\"txtNome\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert txtCognome != null : "fx:id=\"txtCognome\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert txtRisultato != null : "fx:id=\"txtRisultato\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        txtNome.setEditable(false);
        txtCognome.setEditable(false);
        
        chcCorso.getItems().add("");
        chcCorso.setValue("");
        // chcCorso.getItems().addAll(model.trovaCorsi());
        
        
    }
}
