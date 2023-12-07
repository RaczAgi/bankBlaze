package hu.bankblaze.bankblaze.repo;

import hu.bankblaze.bankblaze.model.Retail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RetailRepository extends JpaRepository<Retail, Long> {

}
