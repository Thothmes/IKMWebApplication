package ru.kulakov.IKMWebApplication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kulakov.IKMWebApplication.entites.*;
import java.util.List;

@Repository
public interface GalaxyRepository extends JpaRepository<Galaxy, Integer> {

}