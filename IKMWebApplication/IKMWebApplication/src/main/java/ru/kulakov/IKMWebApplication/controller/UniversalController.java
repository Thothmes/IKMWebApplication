package ru.kulakov.IKMWebApplication.controller;

import com.sun.source.tree.CompilationUnitTree;
import org.springframework.beans.CachedIntrospectionResults;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kulakov.IKMWebApplication.entites.*;
import ru.kulakov.IKMWebApplication.repositories.*;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import jakarta.validation.*;
import org.springframework.validation.BindingResult;

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

        return "index";
    }

    @GetMapping("/add/galaxy")
    public String AddGalaxyAction(Model model) {
        model.addAttribute("galaxy", new Galaxy());
        return "actionformgalaxy";
    }

    @PostMapping("/add/galaxy")
    public String AddGalaxyAction(@Valid @ModelAttribute Galaxy galaxy) {
        galaxyRepository.save(galaxy);
        return "redirect:/confirm";
    }

    @GetMapping("/edit/galaxy/{id}")
    public String EditGalaxyAction(@PathVariable Integer id, Model model) {
        Galaxy galaxy = galaxyRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid author Id:" + id));
        model.addAttribute("galaxy", galaxy);
        return "actionformgalaxy";
    }

    @PostMapping("/edit/galaxy/{id}")
    public String EditGalaxyAction(@PathVariable Integer id, @ModelAttribute Galaxy galaxy) {
        galaxy.setId(id);
        galaxyRepository.save(galaxy);
        return "redirect:/confirm";
    }

    @GetMapping("/add/{type}")
    public String AddObjectAction(@PathVariable String type, Model model) {
        switch (type) {
            case "planet":
                model.addAttribute("object", new Planet());
                model.addAttribute("grand_object", galaxyRepository.findAll());
                break;
            case "continent":
                model.addAttribute("object", new Continent());
                model.addAttribute("grand_object", planetRepository.findAll());
                break;
            case "country":
                model.addAttribute("object", new Country());
                model.addAttribute("grand_object", continentRepository.findAll());
                break;
            case "city":
                model.addAttribute("object", new City());
                model.addAttribute("grand_object", countryRepository.findAll());
                break;
        }
        return "actionform";
    }

    @PostMapping("/add/{type}")
    public String AddObjectAction(@PathVariable String type,
                                  @Valid @ModelAttribute Planet planet,
                                  @Valid @ModelAttribute Continent continent,
                                  @Valid @ModelAttribute Country country,
                                  @Valid @ModelAttribute City city) {
       switch (type) {
            case "planet":
                planetRepository.save(planet);
                break;
            case "continent":
                continentRepository.save(continent);
                break;
            case "country":
                countryRepository.save(country);
                break;
            case "city":
                cityRepository.save(city);
                break;
        }
        return "redirect:/confirm";
    }

    @GetMapping("/edit/{type}/{id}")
    public String EditObjectAction(@PathVariable String type, @PathVariable Integer id, Model model) {
        switch (type) {
            case "planet": {
                Planet object = planetRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid author Id:" + id));
                model.addAttribute("object", object);
                model.addAttribute("grand_object", galaxyRepository.findAll());
                break; }
            case "continent": {
                Continent object = continentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid author Id:" + id));
                model.addAttribute("object", object);
                model.addAttribute("grand_object", planetRepository.findAll());
                break; }
            case "country": {
                Country object = countryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid author Id:" + id));
                model.addAttribute("object", object);
                model.addAttribute("grand_object", continentRepository.findAll());
                break; }
            case "city": {
                City object = cityRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid author Id:" + id));
                model.addAttribute("object", object);
                model.addAttribute("grand_object", countryRepository.findAll());
                break;}
        }
        model.addAttribute("type", type);
        return "actionform";
    }

    @PostMapping("/edit/{type}/{id}")
    public String EditObjectAction(@PathVariable String type, @PathVariable Integer id,
                                   @ModelAttribute Planet planet,
                                   @ModelAttribute Continent continent,
                                   @ModelAttribute Country country,
                                   @ModelAttribute City city) {
        switch (type) {
            case "planet": {
                planet.setId(id);
                planetRepository.save(planet);
                break;}
            case "continent": {
                continent.setId(id);
                continentRepository.save(continent);
                break;}
            case "country": {
                country.setId(id);
                countryRepository.save(country);
                break;}
            case "city": {
                city.setId(id);
                cityRepository.save(city);
                break;}
        }
        return "redirect:/confirm";
    }

    @GetMapping("/delete/{type}/{id}")
    public String DeleteObjectAction(@PathVariable String type, @PathVariable Integer id) {
        switch (type) {
            case "galaxy": {
                List<Planet> planets = planetRepository.findAllByGalaxy_Id(id);
                for (int i = 0; i < planets.toArray().length; i++) {
                    List<Continent> continents = continentRepository.findAllByPlanet_Id(id);
                    for (int i1 = 0; i1 < continents.toArray().length; i1++) {
                        List<Country> countries = countryRepository.findAllByContinent_Id(continents.get(i).getId());
                        for (int i2 = 0; i2 < countries.toArray().length; i2++) {
                            List<City> cities = cityRepository.findAllByCountry_Id(countries.get(i).getId());
                            cityRepository.deleteAll(cities);
                        }
                        countryRepository.deleteAll(countries);
                    }
                    continentRepository.deleteById(id);
                }
                planetRepository.deleteById(id);
                galaxyRepository.deleteById(id);
            }
            case "planet": {
                List<Continent> continents = continentRepository.findAllByPlanet_Id(id);
                for (int i = 0; i < continents.toArray().length; i++) {
                    List<Country> countries = countryRepository.findAllByContinent_Id(continents.get(i).getId());
                    for (int i1 = 0; i1 < countries.toArray().length; i1++) {
                        List<City> cities = cityRepository.findAllByCountry_Id(countries.get(i).getId());
                        cityRepository.deleteAll(cities);
                    }
                    countryRepository.deleteAll(countries);
                }
                continentRepository.deleteById(id);
                planetRepository.deleteById(id);
            }
            case "continent": {
                List<Country> countries = countryRepository.findAllByContinent_Id(id);
                for (int i = 0; i < countries.toArray().length; i++) {
                    List<City> cities = cityRepository.findAllByCountry_Id(countries.get(i).getId());
                    cityRepository.deleteAll(cities);
                }
                countryRepository.deleteAll(countries);
                continentRepository.deleteById(id);
            }
            case "country": {
                List<City> cities = cityRepository.findAllByCountry_Id(id);
                cityRepository.deleteAll(cities);
                countryRepository.deleteById(id);
            }
            case "city": {
                cityRepository.deleteById(id);
            }
        }
        return "redirect:/confirm";
    }

    @GetMapping("/view/{type}/{id}")
    public String ViewObjectAction(@PathVariable String type, @PathVariable Integer id, Model model) {
        switch (type) {

            case "galaxy": {
                Optional<Galaxy> pregalaxy = galaxyRepository.findById(id);
                List<Planet> planets = planetRepository.findAllByGalaxy_Id(id);
                for (int i = 0; i < planets.toArray().length; i++) {
                    List<Continent> continents = continentRepository.findAllByPlanet_Id(id);
                    for (int i1 = 0; i1 < continents.toArray().length; i1++) {
                        List<Country> countries = countryRepository.findAllByContinent_Id(continents.get(i).getId());
                        for (int i2 = 0; i2 < countries.toArray().length; i2++) {
                            List<City> cities = cityRepository.findAllByCountry_Id(countries.get(i).getId());
                        }
                    }
                }
                Galaxy galaxy = pregalaxy.get();
            }

            case "planet": {
                Optional<Planet> preplanet = planetRepository.findById(id);
                List<Continent> continents = continentRepository.findAllByPlanet_Id(id);
                for (int i = 0; i < continents.toArray().length; i++) {
                    List<Country> countries = countryRepository.findAllByContinent_Id(continents.get(i).getId());
                    for (int i1 = 0; i1 < countries.toArray().length; i1++) {
                        List<City> cities = cityRepository.findAllByCountry_Id(countries.get(i).getId());
                    }
                }
                Planet planet = preplanet.get();
                Optional<Galaxy> pregalaxy = galaxyRepository.findById(planet.getGalaxy().getId());
                Galaxy galaxy = pregalaxy.get();
            }

            case "continent": {
                Optional<Continent> precontinent = continentRepository.findById(id);
                List<Country> countries = countryRepository.findAllByContinent_Id(id);
                for (int i = 0; i < countries.toArray().length; i++) {
                    List<City> cities = cityRepository.findAllByCountry_Id(countries.get(i).getId());
                }
                Continent continent = precontinent.get();
                Optional<Planet> preplanet = planetRepository.findById(continent.getPlanet().getId());
                Planet planet = preplanet.get();
                Optional<Galaxy> pregalaxy = galaxyRepository.findById(planet.getGalaxy().getId());
                Galaxy galaxy = pregalaxy.get();
            }

            case "country": {
                Optional<Country> precountry = countryRepository.findById(id);
                List<City> cities = cityRepository.findAllByCountry_Id(id);
                Country country = precountry.get();
                Optional<Continent> precontinent = continentRepository.findById(country.getContinent().getId());
                Continent continent = precontinent.get();
                Optional<Planet> preplanet = planetRepository.findById(continent.getPlanet().getId());
                Planet planet = preplanet.get();
                Optional<Galaxy> pregalaxy = galaxyRepository.findById(planet.getGalaxy().getId());
                Galaxy galaxy = pregalaxy.get();

            }
            case "city": {
                Optional<City> precity = cityRepository.findById(id);
                City city = precity.get();
                Optional<Country> precountry = countryRepository.findById(city.getCountry().getId());
                Country country = precountry.get();
                Optional<Continent> precontinent = continentRepository.findById(country.getContinent().getId());
                Continent continent = precontinent.get();
                Optional<Planet> preplanet = planetRepository.findById(continent.getPlanet().getId());
                Planet planet = preplanet.get();
                Optional<Galaxy> pregalaxy = galaxyRepository.findById(planet.getGalaxy().getId());
                Galaxy galaxy = pregalaxy.get();
            }
        }
        return "view";
    }

    @GetMapping("/confirm")
    public String ConfirmAction () {
        return "confirm";
    }
}