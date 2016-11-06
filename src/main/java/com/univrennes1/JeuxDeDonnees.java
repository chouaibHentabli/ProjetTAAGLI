package com.univrennes1;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

import com.univrennes1.domain.*;
import com.univrennes1.domain.util.JSR310PersistenceConverters;
import com.univrennes1.repository.UserRepository;
import com.univrennes1.service.*;

import com.mysql.jdbc.jdbc2.optional.SuspendableXAConnection;
import com.univrennes1.service.dto.AccidentDTO;
import com.univrennes1.service.dto.CauseDTO;
import com.univrennes1.service.dto.RubriqueDTO;
import com.univrennes1.service.mapper.AccidentMapper;
import com.univrennes1.service.mapper.BaacMapper;
import com.univrennes1.service.mapper.CauseMapper;
import com.univrennes1.service.mapper.RubriqueMapper;
import io.swagger.models.properties.IntegerProperty;

import javax.inject.Inject;

public class JeuxDeDonnees {

    @Inject
    private AccidentService accService;

    @Inject
    private RubriqueService rubriqueService;

    @Inject
    private CauseService causeService;

    @Inject
    private UserService userService;

    @Inject
    private UserRepository userRepository;

    @Inject
    private RubriqueMapper rubriqueMapper;

    @Inject
    private AccidentMapper accMapper;
    @Inject
    private CauseMapper causeMapper;
    @Inject
    private BaacMapper baacMapper;
    @Inject
    private BaacService baacService;

    public void execute() {
        for (int i = 0; i < 1000; i++) {
            Accident acc = new Accident();
            Random rand = new Random();
            Set<Valeur> listVal  = new HashSet<>();
            Date dateAccident = getRandomDate(2012);

            int longMin = -500000;
            int longMax = 830000;
            int latMin = 4200000;
            int latMax = 5110000;
            int longitude = longMin + rand.nextInt(longMax - longMin);
            int latitude = latMin + rand.nextInt(latMax - latMin);

            final ZoneId systemDefault = ZoneId.systemDefault();
            List<CauseDTO> listCause = causeService.findAll();
            List<User> listUsers = userRepository.findAll();
            User u = listUsers.get(rand.nextInt(listUsers.size()));

            acc.setUser(u);
            acc.setDateAcc(ZonedDateTime.ofInstant(dateAccident.toInstant(), systemDefault));
            acc.setHeure(ZonedDateTime.ofInstant(dateAccident.toInstant(), systemDefault));
            acc.setDateCreation(ZonedDateTime.ofInstant(dateAccident.toInstant(), systemDefault));
            acc.setLongitude((double)longitude);
            acc.setLatitude((double)latitude);
            acc.setStatus(false);
            acc.setNbrBlesse(rand.nextInt(4));
            acc.setNbrHospitalise(rand.nextInt(4));
            acc.setNbrIndemne(4);
            acc.setNbrMorts(4);

            for (int j = 0; j < (rand.nextInt(4)); j++) {
                Cause cause = causeMapper.causeDTOToCause(listCause.get(rand.nextInt(listCause.size())));
                acc.getCauses().add(cause);
            }
            /*
            for (RubriqueDTO rub : rubriqueService.findAll()) {
                Rubrique rubrique = rubriqueMapper.rubriqueDTOToRubrique(rub);
                if (rub.getRubUnique()) {
                    Baac baac = new Baac();
                    baac.setDateCreation(ZonedDateTime.ofInstant(dateAccident.toInstant(), systemDefault));
                    baac.setAccident(acc);
                    baac.setRubrique(rubrique);
                    baac.setUser(u);

                    for (Variable var : rubrique.getVariables()) {
                        if (rub.getActive() && (var.getValeurs() != null || var.getValeurs().isEmpty())) {
                            List<Valeur> valeurs = new ArrayList<Valeur>(var.getValeurs());
                            listVal.add(valeurs.get(rand.nextInt(valeurs.size())));
                        }
                    }
                    baac.setValeurs(listVal);
                    acc.getBaacs().add(baac);
                    baacService.save(baacMapper.baacToBaacDTO(baac));

                } else {
                    for (int j = 0; j < (rand.nextInt(4)); j++) {
                        Baac baac = new Baac();
                        baac.setAccident(acc);
                        baac.setRubrique(rubrique);
                        baac.setUser(u);
                        baac.setDateCreation(ZonedDateTime.ofInstant(dateAccident.toInstant(), systemDefault));

                        for (Variable var : rubrique.getVariables()) {
                            if (rub.getActive() && (var.getValeurs() != null || var.getValeurs().isEmpty())) {
                                List<Valeur> valeurs = new ArrayList<Valeur>(var.getValeurs());
                                listVal.add(valeurs.get(rand.nextInt(valeurs.size())));
                            }
                        }
                        acc.getBaacs().add(baac);
                        baacService.save(baacMapper.baacToBaacDTO(baac));
                    }
                }
            }*/
            accService.save(accMapper.accidentToAccidentDTO(acc));
        }
    }

    @SuppressWarnings( "deprecation" )
    private Date getRandomDate(Integer year) {
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
