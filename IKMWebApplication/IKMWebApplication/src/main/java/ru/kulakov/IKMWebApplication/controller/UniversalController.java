package ru.kulakov.IKMWebApplication.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kulakov.IKMWebApplication.entities.*;
import ru.kulakov.IKMWebApplication.repositories.*;

import java.util.*;

import jakarta.validation.*;

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
        String typeaction = "add";
        model.addAttribute("galaxy", new Galaxy());
        model.addAttribute("typeaction", typeaction);
        return "actionformgalaxy";
    }

    @PostMapping("/add/galaxy")
    public String AddGalaxyAction(@Valid @ModelAttribute Galaxy galaxy, @ModelAttribute String typeaction) {
        galaxyRepository.save(galaxy);
        return "redirect:/confirm";
    }

    @GetMapping("/edit/galaxy/{id}")
    public String EditGalaxyAction(@PathVariable Integer id, Model model) {
        String typeaction = "edit";
        Galaxy galaxy = galaxyRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid author Id:" + id));
        model.addAttribute("galaxy", galaxy);
        model.addAttribute("typeaction", typeaction);
        return "actionformgalaxy";
    }

    @PostMapping("/edit/galaxy/{id}")
    public String EditGalaxyAction(@PathVariable Integer id, @ModelAttribute Galaxy galaxy) {
        galaxy.setId(id);
        galaxyRepository.save(galaxy);
        return "redirect:/confirm";
    }

    @GetMapping("/edit/planet/{id}")
    public String editPlanet(@PathVariable Integer id, Model model) {
        Planet planet = planetRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid planet Id:" + id));
        List<Galaxy> galaxies = galaxyRepository.findAll();
        model.addAttribute("planet", planet);
        model.addAttribute("galaxies", galaxies);
        return "editplanet";
    }

    @PostMapping("/edit/planet/{id}")
    public String editPlanet(@PathVariable Integer id, @ModelAttribute Planet planet) {
        planet.setId(id);
        planetRepository.save(planet);
        return "redirect:/confirm";
    }

    @GetMapping("/add/planet")
    public String addPlanet(Model model) {
        model.addAttribute("planet", new Planet());
        model.addAttribute("galaxies", galaxyRepository.findAll());
        return "addplanet";
    }

    @PostMapping("/add/planet")
    public String addPlanet(@ModelAttribute Planet planet) {
        planetRepository.save(planet);
        return "redirect:/confirm";
    }

    @GetMapping("/edit/continent/{id}")
    public String editContinent(@PathVariable Integer id, Model model) {
        Continent continent = continentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid continent Id:" + id));
        List<Planet> planets = planetRepository.findAll();
        model.addAttribute("continent", continent);
        model.addAttribute("planets", planets);
        return "editcontinent";
    }

    @PostMapping("/edit/continent/{id}")
    public String editContinent(@PathVariable Integer id, @ModelAttribute Continent continent) {
        continent.setId(id);
        continentRepository.save(continent);
        return "redirect:/confirm";
    }

    @GetMapping("/add/continent")
    public String addContinent(Model model) {
        model.addAttribute("continent", new Continent());
        model.addAttribute("planets", planetRepository.findAll());
        return "addcontinent";
    }

    @PostMapping("/add/continent")
    public String addContinent(@ModelAttribute Continent continent) {
        continentRepository.save(continent);
        return "redirect:/confirm";
    }

    @GetMapping("/edit/country/{id}")
    public String editCountry(@PathVariable Integer id, Model model) {
        Country country = countryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid country Id:" + id));
        List<Continent> continents = continentRepository.findAll();
        model.addAttribute("country", country);
        model.addAttribute("continents", continents);
        return "editcountry";
    }

    @PostMapping("/edit/country/{id}")
    public String editCountry(@PathVariable Integer id, @ModelAttribute Country country) {
        country.setId(id);
        countryRepository.save(country);
        return "redirect:/confirm";
    }

    @GetMapping("/add/country")
    public String addCountry(Model model) {
        model.addAttribute("country", new Country());
        model.addAttribute("continents", continentRepository.findAll());
        return "addcountry";
    }

    @PostMapping("/add/country")
    public String addCountry(@ModelAttribute Country country) {
        countryRepository.save(country);
        return "redirect:/confirm";
    }

    @GetMapping("/edit/city/{id}")
    public String editCity(@PathVariable Integer id, Model model) {
        City city = cityRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid city Id:" + id));
        List<Country> countries = countryRepository.findAll();
        model.addAttribute("city", city);
        model.addAttribute("countries", countries);
        return "editcity";
    }

    @PostMapping("/edit/city/{id}")
    public String editCity(@PathVariable Integer id, @ModelAttribute City city) {
        city.setId(id);
        cityRepository.save(city);
        return "redirect:/confirm";
    }

    @GetMapping("/add/city")
    public String addCity(Model model) {
        model.addAttribute("city", new City());
        model.addAttribute("countries", countryRepository.findAll());
        return "addcity";
    }

    @PostMapping("/add/city")
    public String addCity(@ModelAttribute City city) {
        cityRepository.save(city);
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
                break;
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
                break;
            }
            case "continent": {
                List<Country> countries = countryRepository.findAllByContinent_Id(id);
                for (int i = 0; i < countries.toArray().length; i++) {
                    List<City> cities = cityRepository.findAllByCountry_Id(countries.get(i).getId());
                    cityRepository.deleteAll(cities);
                }
                countryRepository.deleteAll(countries);
                continentRepository.deleteById(id);
                break;
            }
            case "country": {
                List<City> cities = cityRepository.findAllByCountry_Id(id);
                cityRepository.deleteAll(cities);
                countryRepository.deleteById(id);
                break;
            }
            case "city": {
                cityRepository.deleteById(id);
                break;
            }
        }
        return "redirect:/confirm";
    }

    @GetMapping("/view/galaxy/{id}")
    public String ViewGalaxyAction(@PathVariable Integer id, Model model) {
        Optional<Galaxy> pregalaxy = galaxyRepository.findById(id);
        List<Planet> planet = planetRepository.findAllByGalaxy_Id(id);
        List<Continent> continent = new ArrayList<>();
        List<Country> country = new ArrayList<>();
        List<City> city = new ArrayList<>();

        if (!planet.isEmpty()) {
            for (Planet p : planet) {
                List<Continent> planetContinent = continentRepository.findAllByPlanet_Id(p.getId());
                if (!planetContinent.isEmpty()) {
                    continent.addAll(planetContinent);
                    for (Continent c : planetContinent) {
                        List<Country> continentCountry = countryRepository.findAllByContinent_Id(c.getId());
                        if (!continentCountry.isEmpty()) {
                            country.addAll(continentCountry);
                            for (Country co : continentCountry) {
                                List<City> countryCity = cityRepository.findAllByCountry_Id(co.getId());
                                if (!countryCity.isEmpty()) {
                                    city.addAll(countryCity);
                                }
                            }
                        }
                    }
                }
            }
        }

        Galaxy galaxy = pregalaxy.get();
        model.addAttribute("supremeobject", galaxy);
        model.addAttribute("galaxy", galaxy);
        model.addAttribute("planet", planet);
        model.addAttribute("continent", continent);
        model.addAttribute("country", country);
        model.addAttribute("city", city);
        model.addAttribute("type", "galaxy");
        return "viewgalaxy";
    }

    @GetMapping("/view/planet/{id}")
    public String ViewPlanetAction(@PathVariable Integer id, Model model) {
        Optional<Planet> preplanet = planetRepository.findById(id);
        List<Continent> continent = continentRepository.findAllByPlanet_Id(id);
        List<Country> country = new ArrayList<>();
        List<City> city = new ArrayList<>();

        if (!continent.isEmpty()) {
            for (Continent c : continent) {
                List<Country> continentCountry = countryRepository.findAllByContinent_Id(c.getId());
                if (!continentCountry.isEmpty()) {
                    country.addAll(continentCountry);
                    for (Country co : continentCountry) {
                        List<City> countryCity = cityRepository.findAllByCountry_Id(co.getId());
                        if (!countryCity.isEmpty()) {
                            city.addAll(countryCity);
                        }
                    }
                }
            }
        }

        Planet planet = preplanet.get();
        Optional<Galaxy> pregalaxy = galaxyRepository.findById(planet.getGalaxy().getId());
        Galaxy galaxy = pregalaxy.get();
        model.addAttribute("supremeobject", planet);
        model.addAttribute("planet", planet);
        model.addAttribute("continent", continent);
        model.addAttribute("country", country);
        model.addAttribute("city", city);
        model.addAttribute("galaxy", galaxy);
        model.addAttribute("type", "planet");
        return "viewplanet";
    }

    @GetMapping("/view/continent/{id}")
    public String ViewContinentAction(@PathVariable Integer id, Model model) {
        Optional<Continent> precontinent = continentRepository.findById(id);
        List<Country> country = countryRepository.findAllByContinent_Id(id);
        List<City> city = new ArrayList<>();

        if (!country.isEmpty()) {
            for (Country co : country) {
                List<City> countryCity = cityRepository.findAllByCountry_Id(co.getId());
                if (!countryCity.isEmpty()) {
                    city.addAll(countryCity);
                }
            }
        }

        Continent continent = precontinent.get();
        Optional<Planet> preplanet = planetRepository.findById(continent.getPlanet().getId());
        Planet planet = preplanet.get();
        Optional<Galaxy> pregalaxy = galaxyRepository.findById(planet.getGalaxy().getId());
        Galaxy galaxy = pregalaxy.get();
        model.addAttribute("supremeobject", continent);
        model.addAttribute("continent", continent);
        model.addAttribute("country", country);
        model.addAttribute("city", city);
        model.addAttribute("planet", planet);
        model.addAttribute("galaxy", galaxy);
        model.addAttribute("type", "continent");
        return "viewcontinent";
    }

    @GetMapping("/view/country/{id}")
    public String ViewCountryAction(@PathVariable Integer id, Model model) {
        Optional<Country> precountry = countryRepository.findById(id);
        List<City> city = cityRepository.findAllByCountry_Id(id);

        Country country = precountry.get();
        Optional<Continent> precontinent = continentRepository.findById(country.getContinent().getId());
        Continent continent = precontinent.get();
        Optional<Planet> preplanet = planetRepository.findById(continent.getPlanet().getId());
        Planet planet = preplanet.get();
        Optional<Galaxy> pregalaxy = galaxyRepository.findById(planet.getGalaxy().getId());
        Galaxy galaxy = pregalaxy.get();
        model.addAttribute("supremeobject", country);
        model.addAttribute("country", country);
        model.addAttribute("city", city);
        model.addAttribute("continent", continent);
        model.addAttribute("planet", planet);
        model.addAttribute("galaxy", galaxy);
        model.addAttribute("type", "country");
        return "viewcountry";
    }

    @GetMapping("/view/city/{id}")
    public String ViewCityAction(@PathVariable Integer id, Model model) {
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
        model.addAttribute("supremeobject", city);
        model.addAttribute("city", city);
        model.addAttribute("country", country);
        model.addAttribute("continent", continent);
        model.addAttribute("planet", planet);
        model.addAttribute("galaxy", galaxy);
        model.addAttribute("type", "city");
        return "viewcity";
    }

    @GetMapping("/confirm")
    public String ConfirmAction () {
        return "confirm";
    }

    @ExceptionHandler(Exception.class)
    public String handleException () {
        return "errors";
    }
}