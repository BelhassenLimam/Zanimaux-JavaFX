/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zanimaux.Service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import zanimaux.entities.Evenement;
import zanimaux.Technique.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import zanimaux.entities.Magasin;
import zanimaux.entities.User;
import zanimaux.util.Session;

/**
 *
 * @author Maroua
 */
public class EvenementService {
    public Connection con = DataSource.getInstance().getCon();
     public Statement ste;

    public EvenementService() throws SQLException {
        ste=con.createStatement();
    }
     
      
     public boolean ajouterEvenement(Evenement e)
    {
       String requete = "INSERT INTO Evenement (cin,lieu,dateDebut, dateFin, type, titre, description,nb_place,nbParticipants,image_evt) VALUES (?,?,?,?,?,?,?,?,0,?) ";
        User usr = Session.getLoggedInUser();
       
       try {
           java.util.Date dateDeb = new Date(e.getDateDebut().getTime());
           java.util.Date dateFin = new Date(e.getDateFin().getTime());
            PreparedStatement pst = con.prepareStatement(requete);
            pst.setObject(1,usr.getCin());
            pst.setString(2,e.getLieu());
            pst.setDate(3, (Date) dateDeb);
            pst.setDate(4, (Date) dateFin);
             pst.setString(5,e.getType());
            pst.setString(6,e.getTitre());
            pst.setString(7,e.getDescription());
            pst.setInt(8,e.getNbPlace());
           // pst.setInt(9,0);
            pst.setString(9,e.getImageEvt());
            
            pst.executeUpdate();
            System.out.println("ajouté avec succés");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
            return false;
    }
     
     
     public boolean modifierEvenement(int id,Evenement e)
    { 
    String requete="UPDATE Evenement SET lieu=?, dateDebut=?, dateFin=?, type=? , titre=? , description=?,imageEvt=?, nbPlace=? where idEvt='"+id+"'";
        try {
            PreparedStatement pst =con.prepareStatement(requete);
            pst.setString(1,e.getLieu());
            pst.setDate(2, (Date) e.getDateDebut());
            pst.setDate(3, (Date) e.getDateFin());
            pst.setString(4,e.getType());
            pst.setString(5,e.getTitre()); 
            pst.setString(6,e.getDescription());
            pst.setString(7,e.getImageEvt());
            pst.setInt(8,e.getNbPlace());
             pst.executeUpdate();
            System.out.println("modifié avec succés");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
            return false;
    }
     
     
      public void supprimerEvenement(int id)
         {
                
             String requete="DELETE FROM `Evenement` WHERE idEvt='"+id+"' ";     
             Statement st;
             try {
              st = con.createStatement(); 
              st.executeUpdate(requete);
              System.out.println("Supprimé avec succés");

            } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
     }
      
       public Evenement rechercheEvent(int i)
    { 

        Evenement listeEvent = new Evenement();
        try {  
            String requete = "SELECT * FROM Evenement WHERE `idEvt`="+i;

           
            ResultSet rs = ste.executeQuery(requete);

             while(rs.next()){
                 listeEvent.setLieu(rs.getString("lieu"));
                 listeEvent.setDateDebut(rs.getDate("dateDebut"));
                 listeEvent.setDateFin(rs.getDate("dateFin"));
                 listeEvent.setType(rs.getString("type"));
                 listeEvent.setTitre(rs.getString("titre"));
                 listeEvent.setDescription(rs.getString("description"));
                 listeEvent.setNbPlace(rs.getInt("nbPlace"));
                 listeEvent.setImageEvt(rs.getString("imageEvt"));
            }
             
        } catch (SQLException ex) {
            Logger.getLogger(EvenementService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listeEvent;
    
    }
       
        public ResultSet rechercheEvent()
    { 
        ResultSet rs=null;
        try {  
            String requete = "SELECT * FROM Evenement";
            rs = ste.executeQuery(requete);
             }catch (SQLException ex) {
                 System.out.println(" erreur rechercheEvent()");
        }
        return rs ;
    
    }
    
}
