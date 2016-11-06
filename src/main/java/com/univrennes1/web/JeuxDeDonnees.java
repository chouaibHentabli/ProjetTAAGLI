/*package com.univrennes1.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.gendarmerie.accident.persistence.modele.Accident;
import org.gendarmerie.accident.persistence.modele.Baac;
import org.gendarmerie.accident.persistence.modele.Cause;
import org.gendarmerie.accident.persistence.modele.Groupement;
import org.gendarmerie.accident.persistence.modele.Rubrique;
import org.gendarmerie.accident.persistence.modele.Utilisateur;
import org.gendarmerie.accident.persistence.modele.Valeur;
import org.gendarmerie.accident.persistence.modele.Variable;
import org.gendarmerie.accident.persistence.service.interfaces.IAccidentService;
import org.gendarmerie.accident.persistence.service.interfaces.ICauseService;
import org.gendarmerie.accident.persistence.service.interfaces.IGroupementService;
import org.gendarmerie.accident.persistence.service.interfaces.IRubriqueService;
import org.springframework.beans.factory.annotation.Autowired;

import com.mysql.jdbc.jdbc2.optional.SuspendableXAConnection;

public class JeuxDeDonnees {

    @Autowired
    private IAccidentService   accService;
    @Autowired
    private IRubriqueService   rubriqueService;
    @Autowired
    private IGroupementService groupementService;
    @Autowired
    private ICauseService      causeService;

    public void execute() {
        for ( int i = 0; i < 1000; i++ ) {
            Accident acc = new Accident();
            Random rand = new Random();
            Date dateAccident = getRandomDate( 2012 );
            //Rubrique list  = rubriqueService.findOne( 1 );
            //System.out.print(list);
            List<Groupement> listGroupement = groupementService.findAll();
            //System.out.print( listGroupement );
            List<Cause> listCause = causeService.findAll();
            acc.setDATE( dateAccident );
            acc.setHEURE( dateAccident );
            acc.setDATECREATION( dateAccident );
//            acc.setDATEMODIFICATION( dateAccident );
//            acc.setHOSPITALISE( rand.nextInt( 4 ) );
//            acc.setINDEMNE( rand.nextInt( 4 ) );
//            acc.setMORT( rand.nextInt( 4 ) );
//            acc.setBLESSE( rand.nextInt( 4 ) );
//            acc.setSTATUS( true );loca
//            acc.setGroupement( listGroupement.get( rand.nextInt( listGroupement.size() ) ) );
            for ( int j = 0; j < ( rand.nextInt( 4 ) ); j++ ) {
                acc.getCauses().add( listCause.get( rand.nextInt( listCause.size() ) ) );
            }
            for ( Rubrique rub : rubriqueService.findAll() ) {
                if ( rub.isUNIQUE() ) {
                    Baac baac = new Baac( acc, rub, dateAccident, dateAccident );
                    for ( Variable var : rub.getVariables() ) {
                        if ( rub.getACTIVE() && ( var.getValeurs() != null || var.getValeurs().isEmpty() ) ) {
                            List<Valeur> valeurs = new ArrayList<Valeur>( var.getValeurs() );
                            baac.getValeurs().add( valeurs.get( rand.nextInt( valeurs.size() ) ) );
                        }
                    }
                } else {
                    for ( int j = 0; j < ( rand.nextInt( 4 ) ); j++ ) {
                        Baac baac = new Baac( acc, rub, dateAccident, dateAccident );
                        for ( Variable var : rub.getVariables() ) {
                            if ( rub.getACTIVE() && ( var.getValeurs() != null || var.getValeurs().isEmpty() ) ) {
                                List<Valeur> valeurs = new ArrayList<Valeur>( var.getValeurs() );
                                baac.getValeurs().add( valeurs.get( rand.nextInt( valeurs.size() ) ) );
                            }
                        }
                    }
                }
            }
            accService.create( acc );
        }
    }

    @SuppressWarnings( "deprecation" )
    private Date getRandomDate( Integer year ) {
        Date randomDate = new Date();
        Random r = new Random();
        randomDate.setYear( year - 1900 );
        randomDate.setMonth( r.nextInt( 12 ) );
        randomDate.setDate( r.nextInt( 31 ) + 1 );
        randomDate.setHours( r.nextInt( 24 ) );
        randomDate.setMinutes( r.nextInt( 60 ) );
        randomDate.setSeconds( r.nextInt( 60 ) );

        return randomDate;
    }

}
*/
