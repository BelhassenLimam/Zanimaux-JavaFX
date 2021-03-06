/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zanimaux.GUI;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import zanimaux.Service.MagasinService;
import zanimaux.Service.PanierService;
import zanimaux.Service.ProduitService;
import zanimaux.entities.ContenuPanier;
import zanimaux.entities.Magasin;
import zanimaux.entities.Panier;
import zanimaux.entities.Produit;
import zanimaux.entities.User;
import zanimaux.util.Session;

/**
 * FXML Controller class
 *
 * @author macbookpro
 */
public class magasinController implements Initializable {

    @FXML
    private Button button;
    @FXML
    private Button evenement;
    @FXML
    private Button userName;
    @FXML
    private Button btn11;
    @FXML
    private Button btn1;
    @FXML
    private AnchorPane anchorEvent;
    @FXML
    private Button buttonRefuge;
    @FXML
    private Pane paneProfil;
    @FXML
    private Label sommePanier;
  
    private ScrollPane sp = new ScrollPane();
    @FXML
    private AnchorPane sidePane;
    @FXML
    private AnchorPane bigAnchor;
    @FXML
    private Button Petsitter;
    @FXML
    private Button annonceBtn;
    @FXML
    private Button parc;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        User u= Session.getLoggedInUser();
        userName.setText(u.getUsername());
     
        PanierService pan= null;
        MagasinService m=null;
        try {
            pan= new PanierService();
            m = new MagasinService();
        } catch (SQLException ex) {
            Logger.getLogger(magasinController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Panier p = pan.recherchePanier(u.getCin());
        if (p==null)
        {
        sommePanier.setText("0 DT");}
        else{
        sommePanier.setText(String.valueOf(p.getSomme())+" DT");}
        // TODO
        ResultSet r =m.rechercheMagasin();
        Magasin m1=new Magasin();
        
        sp.setPadding(new Insets(30, 30, 30, 30));
        
       
        sp.setPrefSize(Control.USE_COMPUTED_SIZE, 650);
        sp.setMaxSize(Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE);
        sp.setMinSize(Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE);
        VBox vb = new VBox();
        HBox hb =null;
        vb.setPadding(new Insets(100, 30, 30, 30));
        vb.setSpacing(100);
        int i=0;
         try{
      while(r.next())
      {
          m1.setIdMagasin(r.getInt("idMagasin"));
          m1.setNumRC(r.getString("numRC"));
          m1.setNomMagasin(r.getString("nomMagasin"));
          m1.setAdresseMagasin(r.getString("adresseMagasin"));
          m1.setCodePostaleMagasin(r.getInt("codePostaleMagasin"));
          m1.setPhotoMagasin(r.getString("photoMagasin"));
          m1.setVilleMagasin(r.getNString("villeMagasin"));
          m1.setCinProprietaireMagasin(r.getString("cinProprietaireMagasin"));
          m1.setBestSellerMagasin(r.getInt("bestSellerMagasin")); 
          ImageView im= new ImageView();
          Image image= new Image("zanimaux/ImageUtile/"+m1.getPhotoMagasin(),150,120,false,false) ;
          im.setImage(image);
          Label t1 =new Label(m1.getNomMagasin());
          t1.setFont(Font.font("TIMES NEW ROMAN", 20));
       
          Label t =new Label(m1.getAdresseMagasin()+" "+m1.getVilleMagasin()+", "+m1.getCodePostaleMagasin());
          t.setFont(Font.font("TIMES NEW ROMAN", 15) );
          t.setWrapText(true);
          t.setStyle("-fx-alignment: CENTER;-fx-text-fill:#000058;");
         t1.setStyle("-fx-text-fill:#000058;-fx-alignment: CENTER;");
         
          Button b = new Button();
          
          b.setText("consulter magasin"); 
          b.setId(String.valueOf(m1.getIdMagasin()));
          b.setOnAction(e->{
              try {
                  consulterMagasin(e);
              } catch (SQLException ex) {
                  Logger.getLogger(accueilOumaimaController.class.getName()).log(Level.SEVERE, null, ex);
              }
          });
          
          b.setStyle("-fx-background-color:transparent;-fx-text-fill:#1d7e9e;-fx-border-width: 0px 0px 2px 0px;-fx-border-color:#1d7e9e;");
          b.setFont(Font.font("TIMES NEW ROMAN",15));
          b.setPrefSize(200, 20);
          VBox vbMagasin = new VBox(); 
          vbMagasin.setPadding(new Insets(-60,0,20,20));
          vbMagasin.setSpacing(50);
          vbMagasin.setStyle("-fx-background-color:#E3F9FE;-fx-background-radius:20px;");
          vbMagasin.setMaxSize(200, 150);
         
          vbMagasin.getChildren().add(im);
          vbMagasin.getChildren().add(t1);
          vbMagasin.getChildren().add(t);
          vbMagasin.getChildren().add(b);
          i++;
          System.out.println(m1.getIdMagasin()+" "+m1.getPhotoMagasin());
          if(i%3!=1)
          {
            hb.getChildren().add(vbMagasin) ;
          }
          else
          {
            hb = new HBox();
            hb.setPadding(new Insets(0,0,0,0));
            hb.setSpacing(50);
            hb.getChildren().add(vbMagasin) ;
            
            vb.getChildren().add(hb);
            
           }
                 
      }
      }catch( SQLException e){}
        sp.setContent(vb);
               
        
        anchorEvent.getChildren().add(sp);
        anchorEvent.getChildren().add(paneProfil);
        
    }    


    @FXML
    private void onClickEvenementAction(ActionEvent event) {
             try {
        Stage stage=(Stage) buttonRefuge.getScene().getWindow(); 
        stage.setTitle("Evénement");
        Parent root = FXMLLoader.load(getClass().getResource("afficheEvent.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        } catch (IOException ex) {
           Logger.getLogger(accueilOumaimaController.class.getName()).log(Level.SEVERE, null, ex);
       }
    }

     @FXML
     void AfficherRefugeAction(ActionEvent event) throws SQLException {

        try {
        Stage stage=(Stage) buttonRefuge.getScene().getWindow(); 
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
    private void connexionAction(ActionEvent event) {
                  Session.setLoggedInUser(null);
        Parent root;
             try {
                 root = FXMLLoader.load(getClass().getResource("login.fxml"));
                 Stage myWindow = (Stage) btn11.getScene().getWindow();
                 Scene sc = new Scene(root);
                 myWindow.setScene(sc);
                 myWindow.setTitle("Login");
                 myWindow.show();
             } catch (IOException ex) {
                 Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
             }
    }
    void consulterMagasin(ActionEvent e) throws SQLException {
    
        ResultSet r =null;
        
            ProduitService m= new ProduitService();
          
            int a =Integer.parseInt(((Node)e.getSource()).getId());
            r=m.rechercheProduits(a) ;
            int i=0;
            Produit m1=new Produit();
            ScrollPane sp = new ScrollPane();
            sp.setPrefSize(900, 650);
            sp.setMaxSize(Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE);
            sp.setMinSize(Control.USE_COMPUTED_SIZE, Control.USE_COMPUTED_SIZE);
            VBox vb = new VBox();
            HBox hb =null;
            vb.setPadding(new Insets(100, 30, 30, 30));
            vb.setSpacing(100);
            
             while(r.next())
            {
                m1.setIdProduit(r.getInt("idProduit"));
                m1.setDescription(r.getString("description"));
                m1.setLibelle(r.getString("libelle"));
                m1.setMarque(r.getString("marque"));
                m1.setPhotoProduit(r.getString("photoProduit"));
                m1.setPrix(r.getFloat("prix"));
                m1.setIdMagasin(r.getInt("idMagasin"));
                m1.setQuantite(r.getInt("quantite"));
                m1.setNbFoisVendu(r.getInt("nbFoisVendu"));
                ImageView im= new ImageView();
                Image image= new Image("zanimaux/ImageUtile/"+m1.getPhotoProduit(),150,120,false,false) ;
                im.setImage(image);
                Label t1 =new Label(m1.getLibelle());
                t1.setFont(Font.font("TIMES NEW ROMAN", 20));
                Label t =new Label(m1.getDescription());
                Label prix = new Label("Prix: "+(String.valueOf(m1.getPrix()))+"DT");
                prix.setFont(Font.font("TIMES NEW ROMAN", 15));
                prix.setWrapText(true);
                prix.setStyle("-fx-alignment: CENTER;-fx-text-fill:#000058;");
                t.setFont(Font.font("TIMES NEW ROMAN", 12));
                t.setWrapText(true);
                t.setStyle("-fx-alignment: CENTER;-fx-text-fill:#000058;");
                t1.setStyle("-fx-text-fill:#000058;-fx-alignment: CENTER;");
                Button b = new Button();
                b.setId(String.valueOf(m1.getIdProduit()));
                b.setText("Ajouter au panier"); 
                
                b.setStyle("-fx-background-color:transparent;-fx-text-fill:#1d7e9e;-fx-border-width: 0px 0px 2px 0px;-fx-border-color:#1d7e9e;");
                b.setFont(Font.font("TIMES NEW ROMAN",15));
                b.setPrefSize(200, 20);
                b.setOnAction(l->{
                    try{
                  ajoutProduitPanier(l);
                    }catch (SQLException ex)
                    { System.out.println(ex.getMessage());}
            
          });
          
                      VBox vbProduit = new VBox(); 
          vbProduit.setPadding(new Insets(-60,0,20,20));
          vbProduit.setSpacing(50);
          vbProduit.setStyle("-fx-background-color:#E3F9FE;-fx-background-radius:20px;");
         
          vbProduit.setMaxSize(200, 150);
          vbProduit.getChildren().add(im);
          vbProduit.getChildren().add(t1);
          vbProduit.getChildren().add(t);
          vbProduit.getChildren().add(prix);
          vbProduit.getChildren().add(b);
          i++;
          
          if(i%3!=1)
          {
            hb.getChildren().add(vbProduit) ;
          }
          else
          {
            hb = new HBox();
            hb.setPadding(new Insets(0,0,0,0));
            hb.setSpacing(50);
            hb.getChildren().add(vbProduit) ;
            vb.getChildren().add(hb); 
            vb.getChildren().add(paneProfil);
           }
                 
      }
        sp.setContent(vb);
         anchorEvent.getChildren().setAll(sp);
           
    }

    private void ajoutProduitPanier(ActionEvent e) throws SQLException {
        int a =Integer.parseInt(((Node)e.getSource()).getId());
        ProduitService ps = new ProduitService();
        Produit prod= ps.rechercheProduitMagasin(a);
        PanierService p = new PanierService();
        p.ajouterProduitPanier(prod);
    }
    
    @FXML
    private void showPaneProfil(MouseEvent event) 
    {       
            paneProfil.setVisible(true);
    }

    @FXML
    private void hidePaneProfil(MouseEvent event) 
    {
            paneProfil.setVisible(false);
    }

    @FXML
    private void affichePanierAction(ActionEvent event) throws SQLException 
    {
             try {
        Stage stage=(Stage) buttonRefuge.getScene().getWindow(); 
        stage.setTitle("Mon Panier");
        Parent root = FXMLLoader.load(getClass().getResource("AffichePanier.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        } catch (IOException ex) {
           Logger.getLogger(accueilOumaimaController.class.getName()).log(Level.SEVERE, null, ex);
       }
    }

    private void afficherMagasin(ActionEvent event) {
                     try {
        Stage stage=(Stage) button.getScene().getWindow(); 
        stage.setTitle("NOS MAGSINS");
        Parent root = FXMLLoader.load(getClass().getResource("magasin.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        } catch (IOException ex) {
           Logger.getLogger(accueilOumaimaController.class.getName()).log(Level.SEVERE, null, ex);
       }
    }

    @FXML
    private void MagasinButtonAction(ActionEvent event) {
        try {
        Stage stage=(Stage) buttonRefuge.getScene().getWindow(); 
        stage.setTitle("NOS MAGASINS");
        Parent root = FXMLLoader.load(getClass().getResource("magasin.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        } catch (IOException ex) {
           Logger.getLogger(accueilOumaimaController.class.getName()).log(Level.SEVERE, null, ex);
       }
    }

       //redirection bouton parc
    @FXML
    private void AfficherParc(ActionEvent event) {
        try {
        Stage stage=(Stage) buttonRefuge.getScene().getWindow(); 
        stage.setTitle("Liste des parcs");
        Parent root = FXMLLoader.load(getClass().getResource("ListeParc.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        } catch (IOException ex) {
           Logger.getLogger(accueilOumaimaController.class.getName()).log(Level.SEVERE, null, ex);
       }
    }
    
    //redirection bouton petsitter
    @FXML
    private void AfficherPromenade(ActionEvent event) {
        
        try {
            User user=Session.getLoggedInUser();
        String role=user.getRoles();
            String pet="a:1:{i:0;s:14:\"ROLE_PETSITTER\";}";
        Stage stage=(Stage) buttonRefuge.getScene().getWindow(); 
        stage.setTitle("Gestion des promenades");
        if(role.equals(pet)){
        Parent root = FXMLLoader.load(getClass().getResource("Promenade.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();}else{
            Parent root = FXMLLoader.load(getClass().getResource("ListePromenade.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        }
        } catch (IOException ex) {
           Logger.getLogger(accueilOumaimaController.class.getName()).log(Level.SEVERE, null, ex);
       }
    }
      @FXML
    private void goToAnn(ActionEvent event) {
         try {
        Stage stage=(Stage) annonceBtn.getScene().getWindow(); 
        stage.setTitle("Deposez votre annonce");
        Parent root = FXMLLoader.load(getClass().getResource("afficheAnnonce.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        } catch (IOException ex) {
           Logger.getLogger(accueilOumaimaController.class.getName()).log(Level.SEVERE, null, ex);
       }
    }
    @FXML
    private void goToVet(ActionEvent event) {
        
          try {
        Stage stage=(Stage) button.getScene().getWindow(); 
        stage.setTitle("Vétérinaire");
        Parent root = FXMLLoader.load(getClass().getResource("VetFront.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        } catch (IOException ex) {
           Logger.getLogger(accueilOumaimaController.class.getName()).log(Level.SEVERE, null, ex);
       }
    }

    @FXML
    private void afficheAccueil(ActionEvent event) {
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
        stage.show();}
        else{
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
    }

  
    
 

