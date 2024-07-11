package com.dev.pokemon_encyclopedia.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.dev.pokemon_encyclopedia.model.Pokemon;

public interface PokemonRepository extends JpaRepository<Pokemon, Long> {
    Optional<Pokemon> findByName(String name);
    boolean existsByName(String name);
}
