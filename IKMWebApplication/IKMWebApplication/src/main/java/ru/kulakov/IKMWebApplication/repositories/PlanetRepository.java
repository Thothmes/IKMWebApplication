package ru.kulakov.IKMWebApplication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kulakov.IKMWebApplication.entites.Planet;

@Repository
public interface PlanetRepository extends JpaRepository<Planet, Integer> {
}