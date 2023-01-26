package qs.sukaworkplea.qq.narxoz1963.Homecontrollor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import qs.sukaworkplea.qq.narxoz1963.Repository.CategoryRepository;
import qs.sukaworkplea.qq.narxoz1963.Repository.MusicRepository;
import qs.sukaworkplea.qq.narxoz1963.config.servises.UserService;
import qs.sukaworkplea.qq.narxoz1963.joins.Category;
import qs.sukaworkplea.qq.narxoz1963.joins.Music;
import qs.sukaworkplea.qq.narxoz1963.joins.Users;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private MusicRepository musicRepository;

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserService userService;


    @GetMapping(value = "/profile")
    @PreAuthorize("isAuthenticated()")
    public String indexPage(Model model) {

        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("currentUser", getUserDate());
        model.addAttribute("category", categories);
        List<Music> music = musicRepository.findAll();
        model.addAttribute("music", music);
        return "index";
    }

    @PostMapping(value = "/addmusic")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
    public String addMusic(@RequestParam("singer") String name,
                           @RequestParam("songname") String songname,
                           @RequestParam("date") int date,
                           @RequestParam("category_id") Long category_id,Model model) {
        model.addAttribute("currentUser", getUserDate());
        Category category = categoryRepository.findById(category_id).orElse(null);
        if (category != null) {
            Music music = new Music();
            music.setSinger(name);
            music.setSongname(songname);
            music.setDate(date);
            music.setCategory(category);
            musicRepository.save(music);
        }
        return "redirect:/profile";
    }

    @PostMapping(value = "/addcategory")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
    public String addCategory(@RequestParam("category_name") String name,Model model) {
        model.addAttribute("currentUser", getUserDate());
        Category category = new Category();
        category.setCategory(name);
        categoryRepository.save(category);
        return "redirect:/category";
    }

    @PostMapping("/savemusic")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
    public String saveMusics(
            @RequestParam(name = "id") Long id,
            @RequestParam(name = "editSinger") String name,
            @RequestParam(name = "editSongname") String surname,
            @RequestParam(name = "editdate") int data,
            @RequestParam(name = "editcategory_id") Long city,Model model) {
        model.addAttribute("currentUser", getUserDate());
        Music music = musicRepository.findById(id).orElse(null);
        Category category = categoryRepository.findById(city).orElse(null);
        if (music != null && category != null) {
            music.setSinger(name);
            music.setSongname(surname);
            music.setDate(data);
            music.setCategory(category);
            musicRepository.save(music);
            return "redirect:/details/" + id;
        }
        return "redirect:/details/" + id;
    }


    @PostMapping("/savecategory")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
    public String saveCategory(
            @RequestParam(name = "id") Long id,
            @RequestParam(name = "editName") String name,Model model) {
        model.addAttribute("currentUser", getUserDate());
        Category category = categoryRepository.findById(id).orElse(null);
        if (category != null) {
            category.setCategory(name);
            categoryRepository.save(category);
            return "redirect:/details1/" + id;
        }
        return "redirect:/details1/" + id;
    }

    @PostMapping("/sort")
    @PreAuthorize("isAuthenticated()")
    public String sort(@RequestParam("sort") Long sort, Model model) {
        model.addAttribute("currentUser", getUserDate());
        if (sort != null) {
            if (sort == 1) {
                List<Category> categories = categoryRepository.findAll();
                model.addAttribute("category", categories);
                List<Music> music = musicRepository.findAllByOrderBySingerAsc();
                model.addAttribute("music", music);
            }
            if (sort == 2) {
                List<Category> categories = categoryRepository.findAll();
                model.addAttribute("category", categories);
                List<Music> music = musicRepository.findAllByOrderBySongnameAsc();
                model.addAttribute("music", music);
            }
            if (sort == 3) {
                List<Category> categories = categoryRepository.findAll();
                model.addAttribute("category", categories);
                List<Music> music = musicRepository.findAllByOrderByDateDesc();
                model.addAttribute("music", music);
            }
            if (sort == 4) {
                List<Category> categories = categoryRepository.findAll();
                model.addAttribute("category", categories);
                List<Music> music = musicRepository.findAllByOrderByCategoryAsc();
                model.addAttribute("music", music);
            }
            if (sort == 5) {
                List<Category> categories = categoryRepository.findAll();
                model.addAttribute("category", categories);
                List<Music> music = musicRepository.findAll();
                model.addAttribute("music", music);
            }
        }
        return "index";
    }

    @PostMapping("/deletemusic")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String deleteUser(@RequestParam("id") Long id,Model model) {
        model.addAttribute("currentUser", getUserDate());
        Music music = musicRepository.findById(id).orElse(null);
        if (music != null) {
            musicRepository.delete(music);
            return "redirect:/profile";
        }
        return "redirect:/profile";
    }

    @PostMapping("/deletecategory")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String deleteCategory(@RequestParam("id") Long id,Model model) {
        model.addAttribute("currentUser", getUserDate());
        Category category = categoryRepository.findById(id).orElse(null);
        if (category != null) {
            categoryRepository.delete(category);
            return "redirect:/category";
        }
        return "redirect:/category";
    }

    @GetMapping("/emphty")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String sidebar(Model model) {
        model.addAttribute("currentUser", getUserDate());
        return "layout/sidebar-top";
    }

    @GetMapping("/403")
    public String accesPage(Model model) {
        model.addAttribute("currentUser", getUserDate());
        return "403";
    }

    @GetMapping(value = "/register")
    public String register(Model model) {
        model.addAttribute("currentUser", getUserDate());
        return "register";
    }

    @PostMapping(value = "/register")
    public String toRegister(@RequestParam("user_full_name") String fullName,
                             @RequestParam("user_email") String email,
                             @RequestParam("user_password") String password,
                             @RequestParam("user_re_password") String repassword,Model model) {
        model.addAttribute("currentUser", getUserDate());

        if (password.equals(repassword)) {
            Users newUser = new Users();
            newUser.setFullname(fullName);
            newUser.setPassword(password);
            newUser.setEmail(email);

            if (userService.createUser(newUser) != null) {
                return "redirect:/register?success";
            }
        }
        return "redirect:/register?error";
    }

    private Users getUserDate() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            User secUser = (User) authentication.getPrincipal();
            Users myUser = userService.getUserByEmail(secUser.getUsername());
            return myUser;
        }
        return null;
    }

}
