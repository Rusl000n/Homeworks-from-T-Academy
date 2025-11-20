package src.main.java.gallowsGame.dataBase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import src.main.java.gallowsGame.Utils.WordDifficulty;
import src.main.java.gallowsGame.Utils.WordEntry;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class YamlDictionary implements Dictionary {
    private final Map<WordDifficulty, List<WordEntry>> dictionary;
    private Random random;
    private final String pathDictionaryDefault = "config/application.yaml";

    public YamlDictionary(String pathDictionary) {
        this.dictionary = new EnumMap<>(WordDifficulty.class);
        this.random = new Random();
        loadDictionary(pathDictionary);
    }

    public YamlDictionary() {
        this.dictionary = new EnumMap<>(WordDifficulty.class);
        this.random = new Random();
        loadDictionary(pathDictionaryDefault);
    }

    @Override
    public void loadDictionary(String pathDictionary) {
        try {
            ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());
            Path configPath = Paths.get(pathDictionary);
            InputStream inputStream = Files.newInputStream(configPath);
            if (inputStream != null) {
                Map<String, Object> config = yamlMapper.readValue(inputStream, Map.class);
                parseDictionary(config);
            } else {
                System.err.println("Файл словаря не найден");
            }
        } catch (Exception e) {
            System.err.println("Ошибка" + e.getMessage());
        }
    }

    @Override
    public WordEntry getRandomWord(WordDifficulty difficulty) {
        if (difficulty == WordDifficulty.RANDOM) {
            int diff = random.nextInt(3);
            switch (diff) {
                case 0 -> difficulty = WordDifficulty.SIMPLE;
                case 1 -> difficulty = WordDifficulty.MIDDLE;
                case 2 -> difficulty = WordDifficulty.HARD;
            }
        }
        List<WordEntry> words = dictionary.get(difficulty);
        if (words == null || words.isEmpty()) {
            return null;
        }
        return words.get(random.nextInt(words.size()));
    }

    @Override
    public List<WordEntry> getWordsByDifficulty(WordDifficulty difficulty) {
        return dictionary.getOrDefault(difficulty, new ArrayList<>());
    }

    private void parseDictionary(Map<String, Object> config) {
        Map<String, Object> dictConfig = (Map<String, Object>) config.get("dictionary");
        for (WordDifficulty difficulty : WordDifficulty.values()) {
            if (difficulty == WordDifficulty.RANDOM) continue;
            List<Map<String, Object>> wordEntries = (List<Map<String, Object>>)
                dictConfig.get(difficulty.name());
            if (wordEntries != null) {
                List<WordEntry> words = new ArrayList<>();
                for (Map<String, Object> entry : wordEntries) {
                    String word = (String) entry.get("word");
                    String hint = (String) entry.get("hint");
                    words.add(new WordEntry(word, hint));
                }
                dictionary.put(difficulty, words);
            } else {
                dictionary.put(difficulty, new ArrayList<>());
            }
        }
    }

    private int getTotalWords() {
        return dictionary.values().stream()
            .mapToInt(List::size)
            .sum();
    }
}
