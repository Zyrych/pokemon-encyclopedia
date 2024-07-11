package com.dev.pokemon_encyclopedia.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dev.pokemon_encyclopedia.model.Pokemon;
import com.dev.pokemon_encyclopedia.service.PokemonService;

@Controller
public class ProductsController {

    @Autowired
    private PokemonService pokemonService;

    @GetMapping("/products")
    public String products() {
        return "products";
    }
// Search Pokemon Functionality
    @GetMapping("/searchPokemonsResult")
    public String showSearchPokemonResult(@RequestParam("name") String name, Model model) {
        Pokemon pokemon = pokemonService.getPokemon(name);
        model.addAttribute("pokemon", pokemon);
        return "searchPokemonsResult";
    }

    @GetMapping("/searchPokemons")
    public String showSearchPokemonsPage(Model model) {
        return "searchPokemons";
    }
//End of Search Pokemon Functionality

// Search Items Functionality
    @GetMapping("/searchItems")
    public String showSearchItemsPage(Model model) {
        return "searchItems";
    }
}