package hu.bankblaze.bankblaze.repo;

import hu.bankblaze.bankblaze.model.Premium;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PremiumRepository extends JpaRepository<Premium, Long> {
}
