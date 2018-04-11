/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zanimaux.GUI;

import javafx.scene.input.*;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Control;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;
import zanimaux.Service.AvisService;
import zanimaux.Service.ParcService;
import zanimaux.Service.PromenadeService;
import zanimaux.entities.Avis;
import zanimaux.entities.Parc;
import zanimaux.entities.Promenade;
import zanimaux.entities.User;
import zanimaux.util.Session;

/**
 * FXML Controller class
 *
 * @author BelhassenLimam
 */
public class ListePromenadeController implements Initializable {

    @FXML
    private Button button;
    @FXML
    private Button evenement;
    @FXML
    private Button userName;
    @FXML
    private Pane pane;
    @FXML
    private Button btn11;
    @FXML
    private Button btn1;
    @FXML
    private Button parc;
    @FXML
    private AnchorPane anchorEvent;
    @FXML
    private Button logOut;
    @FXML
    private Button acc;
    @FXML
    private TextField recherche;
    @FXML
    private Button vet;
    @FXML
    private Button refuge;
    @FXML
    private Button annonce;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        User u= Session.getLoggedInUser();
        userName.setText(u.getUsername());
        listPromenade();
        recherche.setOnKeyPressed(new EventHandler<KeyEvent>() {
 
    @Override
    public void handle(KeyEvent event) {
        if(event.getCode().equals(KeyCode.ENTER)) {
             // do something
        PromenadeService m=null;
        try {
            m = new PromenadeService();
        } catch (SQLException ex) {
            Logger.getLogger(ParcController.class.getName()).log(Level.SEVERE, null, ex);
        }
        User user=Session.getLoggedInUser();
        String cin=user.getCin();
        Promenade m1=new Promenade();
        ResultSet r =m.RecherchePromenade(recherche.getText());
        ScrollPane sp = new ScrollPane();
        sp.setPrefSize(900, 650);
        //         sp.setMaxSize(Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE);
//         sp.setMinSize(Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE);
VBox vb = new VBox();
HBox hb =null;
vb.setPadding(new Insets(100, 30, 0, 30));
vb.setSpacing(100);
int i=0;
try{
    while(r.next())
    {
        m1.setId(r.getString("id"));
          m1.setNomPromenade(r.getString("nomPromenade"));
          m1.setTypePromenade(r.getString("typePromenade"));
          m1.setLieuPromenade(r.getString("lieuPromenade"));
          m1.setDescriptionPromenade(r.getString("descriptionPromenade"));
          m1.setDatedebutPromenade(r.getDate("datedebutPromenade"));
          m1.setDatefinPromenade(r.getDate("datefinPromenade"));
          m1.setPhotoPromenade(r.getString("photoPromenade"));
          m1.setCinPetsitter(r.getString("cinPetsitter"));
       ImageView im= new ImageView();
          Image image= new Image("zanimaux/ImageUtile/"+m1.getPhotoPromenade(),150,120,false,false) ;
          im.setImage(image);
          Text t1 =new Text(m1.getNomPromenade());
          t1.setFont(Font.font("Verdana", 16));
          Text t3 = new Text("Type : ");
          t3.setFont(Font.font("Verdana", 15));
          t3.setFill(Color.web("#0076a3"));
          Text t2 = new Text(m1.getTypePromenade());
          t2.setFont(Font.font("Verdana", 14));
          Text t4 = new Text("Lieu : ");
          t4.setFont(Font.font("Verdana", 15));
          t4.setFill(Color.web("#0076a3"));
          Text t =new Text(m1.getLieuPromenade());
          t.setFont(Font.font("Verdana", 14) );
          Text t5 = new Text("Durée : ");
          t5.setFont(Font.font("Verdana", 15));
          t5.setFill(Color.web("#0076a3"));
          Text t6 =new Text("Du"+m1.getDatedebutPromenade()+"Au"+m1.getDatefinPromenade());
          t6.setFont(Font.font("Verdana", 14) );
          
        
        AvisService av = new AvisService();
        Avis av1= new Avis();
        
        boolean r1 = av.VerifierAvis(m1.getId(), cin);
        if (m1.getCinPetsitter().equals(cin)){
        
            
           
            VBox vbParc = new VBox(); 
          vbParc.setPadding(new Insets(-60,0,30,30));
          vbParc.setSpacing(20);
          vbParc.setStyle("-fx-background-color:#E3F9FE;-fx-background-radius:20px;");
          vbParc.setPrefSize(200, 150);
          vbParc.getChildren().add(im);
          vbParc.getChildren().add(t1);
          vbParc.getChildren().add(t3);
          vbParc.getChildren().add(t2);
          vbParc.getChildren().add(t4);
          vbParc.getChildren().add(t);
          vbParc.getChildren().add(t5);
          vbParc.getChildren().add(t6);
          
                    
               
            
            
            i++;
            System.out.println(m1.getId()+" "+m1.getPhotoPromenade());
            
            if(i%3!=1)
            {
                hb.getChildren().add(vbParc) ;
                
            }
            else
            {
                hb = new HBox();
                hb.setPadding(new Insets(0,0,0,0));
                hb.setSpacing(50);
                hb.getChildren().add(vbParc) ;
                vb.getChildren().add(hb);
            }
            
            
        
            
        }else{
           if (r1==false){
            Rating rating = new Rating (5);
            rating.setUpdateOnHover(true);
            rating.setRating(0);
           
            VBox vbParc = new VBox();
            vbParc.setPadding(new Insets(-60,0,30,30));
            vbParc.setSpacing(20);
            vbParc.setStyle("-fx-background-color:#E3F9FE;-fx-background-radius:20px;");
            vbParc.setPrefSize(200, 170);
            vbParc.getChildren().add(im);
            vbParc.getChildren().add(t1);
            vbParc.getChildren().add(t3);
            vbParc.getChildren().add(t2);
            vbParc.getChildren().add(t4);
            vbParc.getChildren().add(t);
            vbParc.getChildren().add(t5);
            vbParc.getChildren().add(t6);
            vbParc.getChildren().add(rating);
            rating.setId(String.valueOf(m1.getId()));
           rating.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    try {
                        AvisService av2 = new AvisService();
                        User user1 = Session.getLoggedInUser();
                        String cin1 = user1.getCin();
                        String e1 =((Node)event.getSource()).getId();
                        
                        Avis a = new Avis(e1, rating.getRating(), cin1);
                        av2.ajouterAvis(a);
                        System.out.println("Avis Ajouté");
                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ListePromenade.fxml"));
                            Parent root = (Parent) fxmlLoader.load();
                            Stage secondStage = new Stage();
                            secondStage.setScene(new Scene(root));
                            Stage stage = (Stage) btn1.getScene().getWindow();
                            // do what you have to do
                            stage.hide();
                            secondStage.show();
                        } catch (IOException ex) {
                            Logger.getLogger(ListePromenadeController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                          
                    } catch (SQLException ex) {
            Logger.getLogger(ListePromenadeController.class.getName()).log(Level.SEVERE, null, ex);
        }

           }
           
            });
               
            
            
            i++;
            System.out.println(m1.getId()+" "+m1.getPhotoPromenade());
            
            if(i%3!=1)
            {
                hb.getChildren().add(vbParc) ;
                
            }
            else
            {
                hb = new HBox();
                hb.setPadding(new Insets(0,0,0,0));
                hb.setSpacing(50);
                hb.getChildren().add(vbParc) ;
                vb.getChildren().add(hb);
            }
            
            
        }
        else{
            ResultSet r2=av.AfficherAvis(m1.getId(), cin);
            while(r2.next()){
                av1.setAvis(r2.getDouble("avis"));
                int av2 = (int)av1.getAvis();
                
            }
            
           Text ra = new Text("Votre note est de:");
           ra.setFont(Font.font("Verdana", 15));
           ra.setFill(Color.web("#0076a3"));
           HBox ras = new HBox(ra);
           ras.setAlignment(Pos.CENTER);
            Text rat = new Text(Integer.toString((int)av1.getAvis()));
            rat.setFont(Font.font("Verdana", 14));
            ImageView rats= new ImageView();
            Image imageStar= new Image("zanimaux/ImageUtile/star.png",26,26,false,false) ;
            rats.setImage(imageStar);
            HBox rate = new HBox(rat,rats);
            rate.setAlignment(Pos.CENTER);
            VBox vbParc = new VBox();
            vbParc.setPadding(new Insets(-60,0,30,30));
            vbParc.setSpacing(20);
            vbParc.setStyle("-fx-background-color:#E3F9FE;-fx-background-radius:20px;");
            vbParc.setPrefSize(200, 150);
            vbParc.getChildren().add(im);
            vbParc.getChildren().add(t1);
            vbParc.getChildren().add(t3);
            vbParc.getChildren().add(t2);
            vbParc.getChildren().add(t4);
            vbParc.getChildren().add(t);
            vbParc.getChildren().add(t5);
            vbParc.getChildren().add(t6);
            vbParc.getChildren().add(ra);
            vbParc.getChildren().add(rate);
            
            
            
            
            i++;
            System.out.println(m1.getId()+" "+m1.getPhotoPromenade());
            
            if(i%3!=1)
            {
                hb.getChildren().add(vbParc) ;
                
            }
            else
            {
                hb = new HBox();
                hb.setPadding(new Insets(0,0,0,0));
                hb.setSpacing(50);
                hb.getChildren().add(vbParc) ;
                vb.getChildren().add(hb);
            }
            
            
        }
        }
        
        
        
        
        
        
        
    }
}       catch( SQLException e){}
sp.setContent(vb);
anchorEvent.getChildren().setAll(sp);
    }
        }
    });
        
        
    }     
    
    
     //redirection page magasin
    @FXML
    void handleButtonAction(ActionEvent event) throws SQLException {

        try {
        Stage stage=(Stage) button.getScene().getWindow(); 
        stage.setTitle("NOS MAGASINS");
        Parent root = FXMLLoader.load(getClass().getResource("magasin.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        } catch (IOException ex) {
           Logger.getLogger(accueilOumaimaController.class.getName()).log(Level.SEVERE, null, ex);
       }

    }

    
//redirection page afficheEvent
     @FXML
    private void onClickEvenementAction(ActionEvent event) throws SQLException {
        try {
        Stage stage=(Stage) button.getScene().getWindow(); 
        stage.setTitle("Afficher Evenement");
        Parent root = FXMLLoader.load(getClass().getResource("afficheEvent.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        } catch (IOException ex) {
           Logger.getLogger(accueilOumaimaController.class.getName()).log(Level.SEVERE, null, ex);
       }
    }
    
 //redirection page VetAffiche   
 @FXML
    private void vetAffiche(ActionEvent event) {
         try {
        Stage stage=(Stage) button.getScene().getWindow(); 
        stage.setTitle("Vet");
        Parent root = FXMLLoader.load(getClass().getResource("VetFront.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        } catch (IOException ex) {
           Logger.getLogger(accueilOumaimaController.class.getName()).log(Level.SEVERE, null, ex);
       }
    }
    
    
    //redirection page annonce
    @FXML
    private void Annonce(ActionEvent event) {
         try {
        Stage stage=(Stage) button.getScene().getWindow(); 
        stage.setTitle("Annonces");
        Parent root = FXMLLoader.load(getClass().getResource("afficheAnnonce.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        } catch (IOException ex) {
           Logger.getLogger(accueilOumaimaController.class.getName()).log(Level.SEVERE, null, ex);
       }
    }
    
    //redirection page refugeClient
    @FXML
    private void AfficherRefugeAction(ActionEvent event) {
        try {
        Stage stage=(Stage) button.getScene().getWindow(); 
        stage.setTitle("NOS Refuges");
        Parent root = FXMLLoader.load(getClass().getResource("RefugeClient.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        } catch (IOException ex) {
           Logger.getLogger(accueilOumaimaController.class.getName()).log(Level.SEVERE, null, ex);
       }
    }

    
     @FXML
    private void parcAction(ActionEvent event) throws SQLException {
        try {
      User user=Session.getLoggedInUser();
        String role=user.getRoles();
            String dres="a:1:{i:0;s:13:\"ROLE_DRESSEUR\";}";
        Stage stage=(Stage) button.getScene().getWindow(); 
        if(role.equals(dres)){
        stage.setTitle("Accueil");
        Parent root = FXMLLoader.load(getClass().getResource("AccueilDresseur.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();}else{
            stage.setTitle("Accueil");
        Parent root = FXMLLoader.load(getClass().getResource("Quiz.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        }
        } catch (IOException ex) {
           Logger.getLogger(accueilOumaimaController.class.getName()).log(Level.SEVERE, null, ex);
       }
    }


    @FXML
    private void showPane(MouseEvent event) {
    }

    @FXML
    private void connexionAction(ActionEvent event) {
    }
    private void reinit(ActionEvent event) {
        
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AccueilDresseur.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage secondStage = new Stage();
            secondStage.setScene(new Scene(root));
            Stage stage = (Stage) btn1.getScene().getWindow();
            // do what you have to do
            stage.hide();
            secondStage.show();
        } catch (IOException ex) {
            Logger.getLogger(AjoutCabinetController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
  

    @FXML
    private void Deconnexion(ActionEvent event) {
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

    
    public void listPromenade(){
        PromenadeService m=null;
        try {
            m = new PromenadeService();
        } catch (SQLException ex) {
            Logger.getLogger(ParcController.class.getName()).log(Level.SEVERE, null, ex);
        }
        User user=Session.getLoggedInUser();
        String cin=user.getCin();
        ResultSet r =m.AfficherPromenade();
        Promenade m1=new Promenade();
        r= m.AfficherPromenade();
        ScrollPane sp = new ScrollPane();
        sp.setPrefSize(900, 650);
        //         sp.setMaxSize(Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE);
//         sp.setMinSize(Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE);
VBox vb = new VBox();
HBox hb =null;
vb.setPadding(new Insets(100, 30, 0, 30));
vb.setSpacing(100);
int i=0;
try{
    while(r.next())
    {
        m1.setId(r.getString("id"));
          m1.setNomPromenade(r.getString("nomPromenade"));
          m1.setTypePromenade(r.getString("typePromenade"));
          m1.setLieuPromenade(r.getString("lieuPromenade"));
          m1.setDescriptionPromenade(r.getString("descriptionPromenade"));
          m1.setDatedebutPromenade(r.getDate("datedebutPromenade"));
          m1.setDatefinPromenade(r.getDate("datefinPromenade"));
          m1.setPhotoPromenade(r.getString("photoPromenade"));
          m1.setCinPetsitter(r.getString("cinPetsitter"));
       ImageView im= new ImageView();
          Image image= new Image("zanimaux/ImageUtile/"+m1.getPhotoPromenade(),150,120,false,false) ;
          im.setImage(image);
          Text t1 =new Text(m1.getNomPromenade());
          t1.setFont(Font.font("Verdana", 16));
          Text t3 = new Text("Type : ");
          t3.setFont(Font.font("Verdana", 15));
          t3.setFill(Color.web("#0076a3"));
          Text t2 = new Text(m1.getTypePromenade());
          t2.setFont(Font.font("Verdana", 14));
          Text t4 = new Text("Lieu : ");
          t4.setFont(Font.font("Verdana", 15));
          t4.setFill(Color.web("#0076a3"));
          Text t =new Text(m1.getLieuPromenade());
          t.setFont(Font.font("Verdana", 14) );
          Text t5 = new Text("Durée : ");
          t5.setFont(Font.font("Verdana", 15));
          t5.setFill(Color.web("#0076a3"));
          Text t6 =new Text("Du"+m1.getDatedebutPromenade()+"Au"+m1.getDatefinPromenade());
          t6.setFont(Font.font("Verdana", 14) );
          
        
        AvisService av = new AvisService();
        Avis av1= new Avis();
        
        boolean r1 = av.VerifierAvis(m1.getId(), cin);
        if (m1.getCinPetsitter().equals(cin)){
        
            
           
            VBox vbParc = new VBox(); 
          vbParc.setPadding(new Insets(-60,0,30,30));
          vbParc.setSpacing(20);
          vbParc.setStyle("-fx-background-color:#E3F9FE;-fx-background-radius:20px;");
          vbParc.setPrefSize(200, 150);
          vbParc.getChildren().add(im);
          vbParc.getChildren().add(t1);
          vbParc.getChildren().add(t3);
          vbParc.getChildren().add(t2);
          vbParc.getChildren().add(t4);
          vbParc.getChildren().add(t);
          vbParc.getChildren().add(t5);
          vbParc.getChildren().add(t6);
          
                    
               
            
            
            i++;
            System.out.println(m1.getId()+" "+m1.getPhotoPromenade());
            
            if(i%3!=1)
            {
                hb.getChildren().add(vbParc) ;
                
            }
            else
            {
                hb = new HBox();
                hb.setPadding(new Insets(0,0,0,0));
                hb.setSpacing(50);
                hb.getChildren().add(vbParc) ;
                vb.getChildren().add(hb);
            }
            
            
        
            
        }else{
           if (r1==false){
            Rating rating = new Rating (5);
            rating.setUpdateOnHover(true);
            rating.setRating(0);
           
            VBox vbParc = new VBox();
            vbParc.setPadding(new Insets(-60,0,30,30));
            vbParc.setSpacing(20);
            vbParc.setStyle("-fx-background-color:#E3F9FE;-fx-background-radius:20px;");
            vbParc.setPrefSize(200, 170);
            vbParc.getChildren().add(im);
            vbParc.getChildren().add(t1);
            vbParc.getChildren().add(t3);
            vbParc.getChildren().add(t2);
            vbParc.getChildren().add(t4);
            vbParc.getChildren().add(t);
            vbParc.getChildren().add(t5);
            vbParc.getChildren().add(t6);
            vbParc.getChildren().add(rating);
            rating.setId(String.valueOf(m1.getId()));
           rating.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    try {
                        AvisService av2 = new AvisService();
                        User user1 = Session.getLoggedInUser();
                        String cin1 = user1.getCin();
                        String e1 =((Node)event.getSource()).getId();
                        
                        Avis a = new Avis(e1, rating.getRating(), cin1);
                        av2.ajouterAvis(a);
                        System.out.println("Avis Ajouté");
                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ListePromenade.fxml"));
                            Parent root = (Parent) fxmlLoader.load();
                            Stage secondStage = new Stage();
                            secondStage.setScene(new Scene(root));
                            Stage stage = (Stage) btn1.getScene().getWindow();
                            // do what you have to do
                            stage.hide();
                            secondStage.show();
                        } catch (IOException ex) {
                            Logger.getLogger(ListePromenadeController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                          
                    } catch (SQLException ex) {
            Logger.getLogger(ListePromenadeController.class.getName()).log(Level.SEVERE, null, ex);
        }

           }
           
            });
               
            
            
            i++;
            System.out.println(m1.getId()+" "+m1.getPhotoPromenade());
            
            if(i%3!=1)
            {
                hb.getChildren().add(vbParc) ;
                
            }
            else
            {
                hb = new HBox();
                hb.setPadding(new Insets(0,0,0,0));
                hb.setSpacing(50);
                hb.getChildren().add(vbParc) ;
                vb.getChildren().add(hb);
            }
            
            
        }
        else{
            ResultSet r2=av.AfficherAvis(m1.getId(), cin);
            while(r2.next()){
                av1.setAvis(r2.getDouble("avis"));
                int av2 = (int)av1.getAvis();
                
            }
            
           Text ra = new Text("Votre note est de:");
           ra.setFont(Font.font("Verdana", 15));
           ra.setFill(Color.web("#0076a3"));
           HBox ras = new HBox(ra);
           ras.setAlignment(Pos.CENTER);
            Text rat = new Text(Integer.toString((int)av1.getAvis()));
            rat.setFont(Font.font("Verdana", 14));
            ImageView rats= new ImageView();
            Image imageStar= new Image("zanimaux/ImageUtile/star.png",26,26,false,false) ;
            rats.setImage(imageStar);
            HBox rate = new HBox(rat,rats);
            rate.setAlignment(Pos.CENTER);
            VBox vbParc = new VBox();
            vbParc.setPadding(new Insets(-60,0,30,30));
            vbParc.setSpacing(20);
            vbParc.setStyle("-fx-background-color:#E3F9FE;-fx-background-radius:20px;");
            vbParc.setPrefSize(200, 150);
            vbParc.getChildren().add(im);
            vbParc.getChildren().add(t1);
            vbParc.getChildren().add(t3);
            vbParc.getChildren().add(t2);
            vbParc.getChildren().add(t4);
            vbParc.getChildren().add(t);
            vbParc.getChildren().add(t5);
            vbParc.getChildren().add(t6);
            vbParc.getChildren().add(ra);
            vbParc.getChildren().add(rate);
            
            
            
            
            i++;
            System.out.println(m1.getId()+" "+m1.getPhotoPromenade());
            
            if(i%3!=1)
            {
                hb.getChildren().add(vbParc) ;
                
            }
            else
            {
                hb = new HBox();
                hb.setPadding(new Insets(0,0,0,0));
                hb.setSpacing(50);
                hb.getChildren().add(vbParc) ;
                vb.getChildren().add(hb);
            }
            
            
        }
        }
        
        
        
        
        
        
        
    }
}       catch( SQLException e){}
sp.setContent(vb);
anchorEvent.getChildren().setAll(sp);
    }

    @FXML
    private void Deconnexion(KeyEvent event) {
    }
   
       
}
