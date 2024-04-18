package com.example.projectt2.controller;

import com.example.projectt2.entity.Person;
import com.example.projectt2.service.PersonService;
import com.example.projectt2.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class PersonController {
    @Autowired
    private PersonService service;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/person_register")
    public String personRegister(Model model) {
        model.addAttribute("person", new Person());
        return "personRegister";
    }

    @GetMapping("/person")
    public ModelAndView getAllPerson() {
        List<Person> list = service.getAllPerson();
        return new ModelAndView("allPerson", "person", list);
    }

    @PostMapping("/save")
    public String addPerson(@RequestParam("name") String name,
                            @RequestParam("address") String address,
                            @RequestParam("image") MultipartFile image) throws IOException
    //, @RequestParam("image") MultipartFile file) throws IOException
    {
        String originalFilename = image.getOriginalFilename();
        Path cleanPath = Paths.get(originalFilename).normalize();
        String fileName = cleanPath.toString().replaceAll("\\s", "");
        Person p = new Person();
        p.setImage(fileName);
        p.setAddress(address);
        p.setName(name);

        service.save(p);
        String uploadDir = "src/main/resources/static/images/";

        fileName = p.getId() + "-" + fileName;
        FileUploadUtil.saveFile(uploadDir, fileName, image);
        return "redirect:/person";
    }



    @GetMapping("/deletePerson/{id}")
    public String deletePerson(@PathVariable("id") int id) {
        service.deleteById(id);
        return "redirect:/person";
    }

    @GetMapping("/editPerson/{id}")
    public String editPerson(@PathVariable("id") int id, Model model) {
        Person b = service.getPersonById(id);
        if (b != null) {
            model.addAttribute("person", b);
            return "personEdit";
        } else {

            return "error";
        }
    }

    @PostMapping("/updateUser")
    public String updateUser(@RequestParam("name") String name,
                             @RequestParam("address") String address,
                             @RequestParam("id") int id,
                             @RequestParam("image") MultipartFile image) throws IOException {
        Person b = service.getPersonById(id);
        String originalFilename = image.getOriginalFilename();
        Path cleanPath = Paths.get(originalFilename).normalize();
        String fileName = cleanPath.toString().replaceAll("\\s", "");
        b.setName(name);
        b.setAddress(address);
        b.setImage(fileName);
        service.save(b);
        String uploadDir = "src/main/resources/static/images/";

        fileName = b.getId() + "-" + fileName;
        FileUploadUtil.saveFile(uploadDir, fileName, image);
        return "redirect:/person";
    }
}