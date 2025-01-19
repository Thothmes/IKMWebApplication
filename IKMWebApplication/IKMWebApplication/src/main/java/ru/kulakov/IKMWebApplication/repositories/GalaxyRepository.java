package ru.kulakov.IKMWebApplication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kulakov.IKMWebApplication.entities.*;

@Repository
public interface GalaxyRepository extends JpaRepository<Galaxy, Integer> {

}