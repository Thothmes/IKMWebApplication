package ru.kulakov.IKMWebApplication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kulakov.IKMWebApplication.entites.*;
import java.util.List;

@Repository
public interface ContinentRepository extends JpaRepository<Continent, Integer> {
    List<Continent> findAllByPlanet_Id(Integer id);

}