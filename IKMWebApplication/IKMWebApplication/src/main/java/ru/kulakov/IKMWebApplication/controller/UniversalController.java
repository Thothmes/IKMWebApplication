package ru.kulakov.IKMWebApplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kulakov.IKMWebApplication.entites.*;
import ru.kulakov.IKMWebApplication.repositories.*;
/*import ru.kulakov.IKMWebApplication.services.*;*/
import java.util.Comparator;
import java.util.List;

@Controller
@AllArgsConstructor
public class UniversalController {
    
    private final ContinentRepository continentRepository;
    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;
    private final PlanetRepository planetRepository;
    private final GalaxyRepository galaxyRepository;

    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }

    @GetMapping("/view")
    public String view(Model model) {
        List<Galaxy> galaxies = galaxyRepository.findAll()
                .stream()
                .sorted(Comparator.comparingInt(Galaxy::getId))
                .toList();
        List<Planet> planets = planetRepository.findAll()
                .stream()
                .sorted(Comparator.comparingInt(Planet::getId))
                .toList();
        List<Continent> continents = continentRepository.findAll()
                .stream()
                .sorted(Comparator.comparingInt(Continent::getId))
                .toList();
        List<Country> countries = countryRepository.findAll()
                .stream()
                .sorted(Comparator.comparingInt(Country::getId))
                .toList();
        List<City> cities = cityRepository.findAll()
                .stream()
                .sorted(Comparator.comparingInt(City::getId))
                .toList();

        model.addAttribute("galaxies", galaxies);
        model.addAttribute("planets", planets);
        model.addAttribute("continents", continents);
        model.addAttribute("countries", countries);
        model.addAttribute("cities", cities);

        return "view";
    }

    @GetMapping("/addchoise")
    public String addchoise(Model model) {
        return "addchoise";
    }
}
