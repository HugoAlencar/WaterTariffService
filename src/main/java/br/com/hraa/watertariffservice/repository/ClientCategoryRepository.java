package br.com.hraa.watertariffservice.repository;

import br.com.hraa.watertariffservice.model.ClientCategory;
import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.repository.CrudRepository;

public interface ClientCategoryRepository extends JpaRepository<ClientCategory, Integer> {
    ClientCategory findByName(String name);
}
