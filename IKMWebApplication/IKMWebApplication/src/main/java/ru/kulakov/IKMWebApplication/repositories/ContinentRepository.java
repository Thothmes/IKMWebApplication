package ru.kulakov.IKMWebApplication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kulakov.IKMWebApplication.entites.Continent;

@Repository
public interface ContinentRepository extends JpaRepository<Continent, Integer> {
}