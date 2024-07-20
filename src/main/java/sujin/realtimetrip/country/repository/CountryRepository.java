package sujin.realtimetrip.country.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sujin.realtimetrip.country.entity.Country;
import java.util.Optional;


@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

    Optional<Country> findByCountryCode(String countryCode);
}
