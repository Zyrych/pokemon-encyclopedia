package com.dev.pokemon_encyclopedia.service;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.dev.pokemon_encyclopedia.dto.PokemonResponse;
import com.dev.pokemon_encyclopedia.model.Pokemon;
import com.dev.pokemon_encyclopedia.repository.PokemonRepository;

@Service
public class PokemonService {
    @Autowired
    private PokemonRepository pokemonRepository;

    public Pokemon getPokemon(String name) {
        // Ensure the name is lowercased for consistent retrieval
        String lowerCaseName = name.toLowerCase();
        
        Optional<Pokemon> pokemonOptional = pokemonRepository.findByName(lowerCaseName);
        if (pokemonOptional.isPresent()) {
            return capitalizePokemonName(pokemonOptional.get());
        } else {
            RestTemplate restTemplate = new RestTemplate();
            String url = "https://pokeapi.co/api/v2/pokemon/" + lowerCaseName;
            try {
                PokemonResponse response = restTemplate.getForObject(url, PokemonResponse.class);
                
                if (response != null) {
                    Pokemon pokemon = new Pokemon();
                    pokemon.setName(lowerCaseName); // Save the name in lowercase for consistency
                    pokemon.setHeight(response.getHeight());
                    pokemon.setWeight(response.getWeight());
                    
                    // Join the types into a comma-separated string
                    String types = response.getTypes().stream()
                        .map(typeInfo -> typeInfo.getType().getName())
                        .collect(Collectors.joining(", "));
                    pokemon.setType(types);
                    
                    response.getStats().forEach(stat -> {
                        switch (stat.getStat().getName()) {
                            case "hp":
                                pokemon.setHp(stat.getBaseStat());
                                break;
                            case "attack":
                                pokemon.setAttack(stat.getBaseStat());
                                break;
                            case "defense":
                                pokemon.setDefense(stat.getBaseStat());
                                break;
                            case "special-attack":
                                pokemon.setSpecialAttack(stat.getBaseStat());
                                break;
                            case "special-defense":
                                pokemon.setSpecialDefense(stat.getBaseStat());
                                break;
                            case "speed":
                                pokemon.setSpeed(stat.getBaseStat());
                                break;
                        }
                    });

                    // Set the sprite URL
                    pokemon.setSprite(response.getSprites().getFrontDefault());

                    // Save the Pokémon only if it does not already exist
                    if (!pokemonRepository.existsByName(lowerCaseName)) {
                        pokemonRepository.save(pokemon);
                    }
                    
                    return capitalizePokemonName(pokemon);
                } else {
                    return null;
                }
            } catch (HttpClientErrorException.NotFound e) {
                // Pokémon not found in the external API
                return null;
            }
        }
    }

    private Pokemon capitalizePokemonName(Pokemon pokemon) {
        String name = pokemon.getName();
        String capitalized = name.substring(0, 1).toUpperCase() + name.substring(1);
        pokemon.setName(capitalized);
        return pokemon;
    }
}
