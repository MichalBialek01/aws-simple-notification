package pl.bialek.awssimplenotificationproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.bialek.awssimplenotificationproject.domain.Greeting;
import pl.bialek.awssimplenotificationproject.dynamoRepository.GreetingRepository;
import pl.bialek.awssimplenotificationproject.service.PublishSMS;

@Controller
@RequiredArgsConstructor
public class GreetingController {

    private final GreetingRepository greetingRepository;
    private final PublishSMS publishSMS;

    @GetMapping("/")
    public ResponseEntity<String> greetingForm(Model model) {
        model.addAttribute("greeting", new Greeting());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/greeting")
    public ResponseEntity<String> greetingSubmit(@ModelAttribute Greeting greeting) {
        greetingRepository.putObject(greeting);
        publishSMS.sendMessage(greeting.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
