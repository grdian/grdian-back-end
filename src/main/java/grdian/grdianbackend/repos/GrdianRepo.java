package grdian.grdianbackend.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import grdian.grdianbackend.entities.Grdian;

@Repository
public interface GrdianRepo extends CrudRepository<Grdian, Long> {

	Grdian findByEmailAddress(String emailAddress);

}
