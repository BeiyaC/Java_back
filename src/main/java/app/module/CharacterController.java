package app.module;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/characters")
public class CharacterController {

    private static List<Character> characters = new ArrayList<>();

    static {
        characters.add(new Character(1, "Magicien 1", "magicien", 100));
        characters.add(new Character(2, "Guerrier 1", "guerrier", 150));
        characters.add(new Character(3, "Magicien 2", "magicien", 80));
        characters.add(new Character(4, "Guerrier 2", "guerrier", 120));
    }


    @GetMapping
    public List<Character> getCharacters(Model model) {

        model.addAttribute("charactersList", characters);


        return characters;
    }

    @GetMapping("/{id}")
    public Character getCharacter(@PathVariable("id") int id) {

        Character characterObj = characters.stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElse(null);


        return characterObj;
    }


    @PostMapping
    @ResponseBody
    public ResponseEntity addCharacter(@RequestBody CharacterForm characterForm ) {

        String name = characterForm.getName();
        String type = characterForm.getType();

        if(name != null && name.length()>0 //
                && type != null && type.length()>0) {
            Character newCharacter = new Character(characters.size()+1,name,type,150);
            characters.add(newCharacter);

            return new ResponseEntity<>(newCharacter,HttpStatus.OK);

        }

        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateCharacter(@RequestBody CharacterForm characterForm,
                                  @PathVariable("id") int id) {

        String name = characterForm.getName();
        String type = characterForm.getType();

        for (Character character : characters) {
            if (character.getId() == id) {
                character.setName(name);
                character.setType(type);
                return new ResponseEntity<>(character, HttpStatus.OK);
            }
        }

        return null;
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity deleteCharacter(@PathVariable("id") int id) {

        Character characterObj = characters.stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElse(null);

        String characterName = characterObj.getName();

        characters.removeIf(character -> character.getId() == id);

        return new ResponseEntity<>(String.format("Well done, character name: %s was deleted",characterName), HttpStatus.OK);
    }
}

