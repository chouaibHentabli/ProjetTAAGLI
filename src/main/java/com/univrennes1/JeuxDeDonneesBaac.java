
package com.univrennes1;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

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

public class JeuxDeDonneesBaac {

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
        final ZoneId systemDefault = ZoneId.systemDefault();
        List<AccidentDTO> listAcc = accService.findAll();
        for(AccidentDTO acc : listAcc){
            Random rand = new Random();
            Accident a = accMapper.accidentDTOToAccident(acc);
            for (RubriqueDTO rub : rubriqueService.findAll()) {
                Rubrique rubrique = rubriqueMapper.rubriqueDTOToRubrique(rub);
                if (rub.getRubUnique()) {
                    Baac baac = new Baac();
                    baac.setAccident(a);
                    baac.setRubrique(rubrique);
                    baac.setUser(a.getUser());
                    baac.setDateCreation(ZonedDateTime.ofInstant(a.getDateCreation().toInstant(), systemDefault));

                    for (Variable var : rubrique.getVariables()) {
                        if (rub.getActive() && (var.getValeurs() != null || var.getValeurs().isEmpty())) {
                            List<Valeur> valeurs = new ArrayList<Valeur>(var.getValeurs());
                            baac.getValeurs().add(valeurs.get(rand.nextInt(valeurs.size())));
                        }
                    }
                    a.getBaacs().add(baac);
                    baacService.save(baacMapper.baacToBaacDTO(baac));

                } else {
                    for (int j = 0; j < (rand.nextInt(4)); j++) {
                        Baac baac = new Baac();
                        baac.setAccident(a);
                        baac.setRubrique(rubrique);
                        baac.setUser(a.getUser());
                        baac.setDateCreation(ZonedDateTime.ofInstant(a.getDateCreation().toInstant(), systemDefault));
                        for (Variable var : rubrique.getVariables()) {
                            if (rub.getActive() && (var.getValeurs() != null || var.getValeurs().isEmpty())) {
                                List<Valeur> valeurs = new ArrayList<Valeur>(var.getValeurs());
                                baac.getValeurs().add(valeurs.get(rand.nextInt(valeurs.size())));
                            }
                        }
                        a.getBaacs().add(baac);
                        baacService.save(baacMapper.baacToBaacDTO(baac));
                    }
                }
            }
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
