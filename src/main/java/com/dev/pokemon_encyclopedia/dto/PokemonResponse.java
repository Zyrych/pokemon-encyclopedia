package com.dev.pokemon_encyclopedia.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PokemonResponse {
    private String name;
    private int height;
    private int weight;
    private List<Type> types;
    private List<Stat> stats;
    private Sprites sprites;

    @Data
    public static class Type {
        private TypeInfo type;
    }

    @Data
    public static class TypeInfo {
        private String name;
    }

    @Data
    public static class Stat {
        @JsonProperty("base_stat")
        private int baseStat;
        private StatInfo stat;
    }

    @Data
    public static class StatInfo {
        private String name;
    }

    @Data
    public static class Sprites {
        @JsonProperty("front_default")
        private String frontDefault;
    }
}
