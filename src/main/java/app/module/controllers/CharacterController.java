package app.module.controllers;
import app.module.models.Character;
import app.module.models.CharacterForm;
import app.module.repositories.CharacterDAO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/characters")
public class CharacterController {

    private static List<Integer> idList = new ArrayList<>();

    private CharacterDAO characterDAO = new CharacterDAO();

    @GetMapping
    public List<Character> getCharacters(Model model) {

        model.addAttribute("charactersList", characterDAO.findAll());


        return characterDAO.findAll();
    }

    @GetMapping("/{id}")
    public Character getCharacter(@PathVariable("id") int id) {
        return characterDAO.findById(id);
    }


    @PostMapping
    @ResponseBody
    public ResponseEntity addCharacter(@RequestBody CharacterForm characterForm ) {

        String name = characterForm.getName();
        String type = characterForm.getType();
        int id = idList.size() > 0 ? idList.get(0) : characterDAO.findAll().size()+1;

        if(name != null && name.length()>0 //
                && type != null && type.length()>0) {
            Character newCharacter = new Character(id,name,type,150);
            characterDAO.save(newCharacter);
            if (idList.size() > 0){
                idList.remove(0);
            }

            return new ResponseEntity<>(newCharacter,HttpStatus.OK);

        }

        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateCharacter(@RequestBody CharacterForm characterForm,
                                  @PathVariable("id") int id) {

        String name = characterForm.getName();
        String type = characterForm.getType();

        for (Character character : characterDAO.findAll()) {
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

        String characterName = characterDAO.findById(id).getName();
        int characterId = characterDAO.findById(id).getId();
        characterDAO.delete(id);
        idList.add(characterId);

        return new ResponseEntity<>(String.format("Well done, character name: %s was deleted",characterName), HttpStatus.OK);
    }
}

