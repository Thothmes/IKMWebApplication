package ru.kulakov.IKMWebApplication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.kulakov.IKMWebApplication.entities.*;
import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
    List<City> findAllByCountry_Id(Integer id);
}