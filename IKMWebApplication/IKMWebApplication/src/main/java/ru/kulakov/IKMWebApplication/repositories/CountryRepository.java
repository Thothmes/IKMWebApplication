package ru.kulakov.IKMWebApplication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kulakov.IKMWebApplication.entities.*;
import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {
    List<Country> findAllByContinent_Id(Integer id);
}