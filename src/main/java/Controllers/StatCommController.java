/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;


import Utils.Datasource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FXML Controller class
 *
 * @author CC
 */
public class StatCommController implements Initializable {

    @FXML
    private PieChart piechart;

    private Statement st;
    private ResultSet rs;
    private Connection cnx;


    ObservableList<PieChart.Data>data=FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cnx=Datasource.getConn();
        stat();
    }
    private void stat()
    {


        try {

            String query = "SELECT COUNT(*),id FROM commentaire GROUP BY id" ;

            PreparedStatement PreparedStatement = cnx.prepareStatement(query);
            rs = PreparedStatement.executeQuery();


            while (rs.next()){
                data.add(new PieChart.Data(rs.getString("id"),rs.getInt("COUNT(*)")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ControlleurCrudcomm.class.getName()).log(Level.SEVERE, null, ex);
        }

        piechart.setTitle("*Statistiques des Commantaire *");
        piechart.setLegendSide(Side.LEFT);
        piechart.setData(data);

    }


}
