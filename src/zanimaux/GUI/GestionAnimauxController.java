/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zanimaux.GUI;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import javax.imageio.ImageIO;
import zanimaux.Service.AnimalService;
import zanimaux.Service.RefugeService;
import zanimaux.entities.Animal;
import zanimaux.entities.Refuge;
import zanimaux.entities.User;
import zanimaux.util.Session;
import zanimaux.util.Validation;

/**
 * FXML Controller class
 *
 * @author Azza
 */
public class GestionAnimauxController implements Initializable {

    @FXML
    private Label lb;
    @FXML
    private ImageView iv;
    @FXML
    private TextField picturepath;
    @FXML
    private TableColumn column_id;
    
    @FXML
    private TextField input_nom_animal;
    @FXML
    private TextField input_race;
    @FXML
    private TextField input_type;
    @FXML
    private TextField input_age;
    @FXML
    private ChoiceBox<String> choice_refuge;
    @FXML
    private ChoiceBox<String> choice_etat;
    @FXML
    private Button BtnChoixImage;
    @FXML
    private TableColumn column_nom;
    @FXML
    private TableColumn column_race;
    @FXML
    private TableColumn column_type;
    @FXML
    private TableColumn column_age;
    @FXML
    private TableColumn column_etat;
    @FXML
    private TableColumn column_refuge;
    @FXML
    private TableColumn column_photo;
    @FXML
    private Button btn_goBack;
    @FXML
    private Label logOut;
    @FXML
    private TableView table_list_animal;
    @FXML
    private Label lbnom;
    @FXML
    private Label lbrace;
    @FXML
    private Label lbtype;
    @FXML
    private Label lbage;
    @FXML
    private Label lbetat;
    @FXML
    private Label lbphoto;
    public String filePath;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         ObservableList<String> ids = FXCollections.observableArrayList();
        List<String> listetat = new ArrayList();
            listetat.add("à adopter");
            ObservableList<String> ob = FXCollections.observableArrayList();
            ob.addAll(listetat);
            choice_etat.setItems(ob);
        
        try {
    
            RefugeService ms = new RefugeService();
            User a = Session.getLoggedInUser();
            List<Refuge> listRefuge= new ArrayList<>();
            listRefuge = ms.ListerRefugeByCin(a.getCin());
            List<Animal> listAnimal = new ArrayList<>();
            AnimalService sp = new AnimalService();
            List<Animal> list = new ArrayList<>();
            for(int i =0; i<listRefuge.size();i++){
                list=sp.ListerAnimalRefuge(listRefuge.get(i).getImmatriculation());
                listAnimal.addAll(list);
                ids.add( listRefuge.get(i).getImmatriculation());
            }
            choice_refuge.setItems(ids);
            ObservableList<Animal> data = FXCollections.observableArrayList(listAnimal); 
            
            column_id.setCellValueFactory(
                    new PropertyValueFactory<Animal,Integer>("idAnimal")
            );
            column_nom.setCellValueFactory(
                    new PropertyValueFactory<Animal,String>("nomAnimal")
            );
            column_race.setCellValueFactory(
                    new PropertyValueFactory<Animal,String>("race")
            );
            column_type.setCellValueFactory(
                    new PropertyValueFactory<Animal,String>("type")
            );
            column_age.setCellValueFactory(
                    new PropertyValueFactory<Animal,Integer>("age")
            );
            column_etat.setCellValueFactory(
                    new PropertyValueFactory<Animal,String>("etat")
            );
            column_refuge.setCellValueFactory(
                    new PropertyValueFactory<Animal,String>("refuge")
            );
            column_photo.setCellValueFactory(
                    new PropertyValueFactory<Animal,String>("photoanimal")
            );
                         
            table_list_animal.setItems(data);
            
           
            //modifier nom animal
            column_nom.setCellFactory(TextFieldTableCell.forTableColumn());
            column_nom.setOnEditCommit(
                    new EventHandler<TableColumn.CellEditEvent<Animal, String>>() {
                        
                        @Override
                        public void handle(TableColumn.CellEditEvent<Animal, String> t) {
                            ((Animal) t.getTableView().getItems().get(
                                    t.getTablePosition().getRow())
                                    ).setNomAnimal(t.getNewValue());
                            Animal p = (Animal) t.getTableView().getItems().get(t.getTablePosition().getRow());
                            p.setNomAnimal(t.getNewValue());
                            sp.ModifierAnimal(p);
                        };
                    });
            
           
          
              //modifier race
            column_race.setCellFactory(TextFieldTableCell.forTableColumn());
            column_race.setOnEditCommit(
                    new EventHandler<TableColumn.CellEditEvent<Animal, String>>() {
                        
                        @Override
                        public void handle(TableColumn.CellEditEvent<Animal, String> t) {
                            ((Animal) t.getTableView().getItems().get(
                                    t.getTablePosition().getRow())
                                    ).setRace(t.getNewValue());
                            Animal p = (Animal) t.getTableView().getItems().get(t.getTablePosition().getRow());
                            p.setRace(t.getNewValue());
                            sp.ModifierAnimal(p);
                        };
                       
                    });
             //modifier type
            column_type.setCellFactory(TextFieldTableCell.forTableColumn());
            column_type.setOnEditCommit(
                    new EventHandler<TableColumn.CellEditEvent<Animal, String>>() {
                        
                        @Override
                        public void handle(TableColumn.CellEditEvent<Animal, String> t) {
                            ((Animal) t.getTableView().getItems().get(
                                    t.getTablePosition().getRow())
                                    ).setType(t.getNewValue());
                            Animal p = (Animal) t.getTableView().getItems().get(t.getTablePosition().getRow());
                            p.setType(t.getNewValue());
                            sp.ModifierAnimal(p);
                        };
                       
                    }); 
           
              //modfier age
            column_age.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
            column_age.setOnEditCommit(
                    new EventHandler<TableColumn.CellEditEvent<Animal, Integer>>() {
                        
                        @Override
                        public void handle(TableColumn.CellEditEvent<Animal, Integer> t) {
                            ((Animal) t.getTableView().getItems().get(
                                    t.getTablePosition().getRow())
                                    ).setAge(t.getNewValue());
                            Animal p = (Animal) t.getTableView().getItems().get(t.getTablePosition().getRow());
                            p.setAge(t.getNewValue());
                            sp.ModifierAnimal(p);
                        };
                       
                    });          
              //modfier etat
            column_etat.setCellFactory(TextFieldTableCell.forTableColumn());
            column_etat.setOnEditCommit(
                    new EventHandler<TableColumn.CellEditEvent<Animal,String>>() {
                        
                        @Override
                        public void handle(TableColumn.CellEditEvent<Animal, String> t) {
                            ((Animal) t.getTableView().getItems().get(
                                    t.getTablePosition().getRow())
                                    ).setEtat(t.getNewValue());
                            Animal p = (Animal) t.getTableView().getItems().get(t.getTablePosition().getRow());
                            p.setEtat(t.getNewValue());
                            sp.ModifierAnimal(p);
                        };
                       
                    });   
               //modfier photo
            column_photo.setCellFactory(TextFieldTableCell.forTableColumn());
            column_photo.setOnEditCommit(
                    new EventHandler<TableColumn.CellEditEvent<Animal,String>>() {
                        
                        @Override
                        public void handle(TableColumn.CellEditEvent<Animal, String> t) {
                            ((Animal) t.getTableView().getItems().get(
                                    t.getTablePosition().getRow())
                                    ).setPhotoAnimal(t.getNewValue());
                            Animal p = (Animal) t.getTableView().getItems().get(t.getTablePosition().getRow());
                            p.setPhotoAnimal(t.getNewValue());
                            sp.ModifierAnimal(p);
                        };
                       
                    });   
        } catch (SQLException ex) {
            Logger.getLogger(GestionAnimauxController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
         public boolean controleSaisie() throws IOException, SQLException {
        boolean saisie = true;
        String z;
        int r = 0;
        z = choice_etat.getValue();
        if ("à adopter".equals(z)) {
            r = 1;
        }
        if((r!=1)){
            lbetat.setText("* vous devez choisir l'etat de l'animal");
            saisie = false;
        }else{
            lbetat.setText("");
             saisie = true;
        }


        if (!Validation.textalphabet(input_nom_animal, lbnom, "* le nom doit contenir que des lettre")) {
            saisie = false;
        }
        
         if (!Validation.textValidation(input_nom_animal,lbnom, "* tout les champs doivent etre remplis")) {
            saisie = false;
        }

        if (!Validation.textalphabet(input_race, lbrace, "* le race doit contenir que des lettre")) {
            saisie = false;
        }
         if (!Validation.textValidation(input_race, lbrace, "* tout les champs doivent etre remplis")) {
            saisie = false;
        }

        if (!Validation.textValidation(input_type, lbtype, "* tout les champs doivent etre remplis")) {
            saisie = false;
        }
       if (!Validation.textalphabet(input_type, lbtype, "* le type doit contenir que des lettre")) {
            saisie = false;
        }

       
        if (!Validation.textValidation(input_age, lbage, "* tout les champs doivent etre remplis")) {
            saisie = false;
        }
         if (!Validation.texNum(input_age, lbage, "* l'age doit contenir que des numero")) {
                    saisie = false;
                }
        if (!Validation.textValidation(picturepath, lbphoto, "photo manquante")) {
            saisie = false;
        }
         
      
        return saisie;
    }

    @FXML
    private void ajouterAnimal(ActionEvent event) throws SQLException, IOException {

            if(this.controleSaisie())
         {   
               AnimalService ase= new AnimalService();
               Animal listForm=new Animal();
               
               
                 listForm.setNomAnimal(input_nom_animal.getText());
                 listForm.setRefuge(choice_refuge.getValue());
                 listForm.setEtat(choice_etat.getValue());
                 listForm.setType(input_type.getText());
                 listForm.setAge(Integer.parseInt(input_age.getText()));
                 listForm.setPhotoAnimal(picturepath.getText());
                 listForm.setRace(input_race.getText());              
                 ase.ajouterAnimal(listForm);
                 resetTableData();
             
    }
    }
           public void resetTableData() throws SQLException
    {  RefugeService ms = new RefugeService();
        User a = Session.getLoggedInUser();
        List<Refuge> listRefuge= new ArrayList<>();
        listRefuge = ms.ListerRefugeByCin(a.getCin());
        List<Animal> listAnimal = new ArrayList<>();
        AnimalService ps = new AnimalService();
        List<Animal> list = new ArrayList<>();
            for(int i =0; i<listRefuge.size();i++){
                list=ps.ListerAnimalRefuge(listRefuge.get(i).getImmatriculation());
                listAnimal.addAll(list);
            }
            ObservableList<Animal> data = FXCollections.observableArrayList(listAnimal); 
            table_list_animal.setItems(data);
    }

       public String handle() {
        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

        //Show open file dialog
        File file = fileChooser.showOpenDialog(null);
        String filePath = file.getName();
        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            iv.setImage(image);

        } catch (IOException ex) {
            System.err.println(ex);
        }

        return filePath;
    }
    @FXML
    private void uploadpic(ActionEvent event) {
         
        picturepath.setText(handle());
    }
   
    @FXML
    private void logOut(MouseEvent event) {
         Session.setLoggedInUser(null);
        Parent root;
             try {
                 root = FXMLLoader.load(getClass().getResource("login.fxml"));
                 Stage myWindow = (Stage) logOut.getScene().getWindow();
                 Scene sc = new Scene(root);
                 myWindow.setScene(sc);
                 myWindow.setTitle("Login");
                 myWindow.show();
             } catch (IOException ex) {
                 Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
             }
    }

    @FXML
    private void supprimerAnimal(ActionEvent event) throws SQLException {
         Animal a = (Animal) table_list_animal.getSelectionModel().getSelectedItem();
        if(a == null){
            System.out.println("Choisir un de vos animaux");
                   
        }else{
            AnimalService.getInstance().SupprimerAnimal(a.getIdAnimal());
            System.out.println("SUCCESS");
                    resetTableData();
                    
           
        }
    }

    @FXML
    private void GoBack(ActionEvent event) {
        Parent root;
             try {
                 root = FXMLLoader.load(getClass().getResource("RefugeDashboard.fxml"));
                 Stage myWindow = (Stage) btn_goBack.getScene().getWindow();
                 Scene sc = new Scene(root);
                 myWindow.setScene(sc);
                 myWindow.setTitle("Proprietaire refuge");
                 myWindow.show();
             } catch (IOException ex) {
                 Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
             }
    }
    
}
