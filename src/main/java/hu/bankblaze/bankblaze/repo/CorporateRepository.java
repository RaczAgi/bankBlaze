package hu.bankblaze.bankblaze.repo;

import hu.bankblaze.bankblaze.model.Corporate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CorporateRepository extends JpaRepository<Corporate, Long> {
}
