package grdian.grdianbackend.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import grdian.grdianbackend.entities.Alert;

@Repository
public interface AlertRepo extends CrudRepository<Alert, Long> {

}
