package hu.bankblaze.bankblaze.repo;

import hu.bankblaze.bankblaze.model.Teller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TellerRepository extends JpaRepository<Teller, Long> {
}
