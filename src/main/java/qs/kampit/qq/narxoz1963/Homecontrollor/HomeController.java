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
import org.springframework.web.bind.annotation.PathVariable;
import qs.sukaworkplea.qq.narxoz1963.Repository.CategoryRepository;
import qs.sukaworkplea.qq.narxoz1963.Repository.MusicRepository;
import qs.sukaworkplea.qq.narxoz1963.config.servises.UserService;
import qs.sukaworkplea.qq.narxoz1963.joins.Category;
import qs.sukaworkplea.qq.narxoz1963.joins.Music;
import qs.sukaworkplea.qq.narxoz1963.joins.Users;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private MusicRepository musicRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserService userService;

    @GetMapping(value = "/category")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
    public String countries(Model model) {
        model.addAttribute("currentUser", getUserDate());
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("category", categories);
        return "categories";
    }

    @GetMapping("/addmusic")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
    public String addmusic(Model model) {
        model.addAttribute("currentUser", getUserDate());
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("category", categories);
        List<Music> music = musicRepository.findAll();
        model.addAttribute("music", music);
        return "addmusic";
    }


    @GetMapping("/addcategory")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String addcategory(Model model) {
        model.addAttribute("currentUser", getUserDate());
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("category", categories);
        return "addcategory";
    }

    @GetMapping(value = "/details/{id}")
    @PreAuthorize("isAuthenticated()")
    public String details(@PathVariable("id") Long id, Model model) {
        model.addAttribute("currentUser", getUserDate());
        Music music = musicRepository.findById(id).orElse(null);
        model.addAttribute("qweqwe", music);
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("category", categories);
        return "details";
    }

    @GetMapping(value = "/details1/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String details1(@PathVariable("id") Long id, Model model) {
        model.addAttribute("currentUser", getUserDate());
        Category category = categoryRepository.findById(id).orElse(null);
        model.addAttribute("category", category);
        return "editcategory";
    }

    @GetMapping(value = "/editcategory/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String editcategory(@PathVariable("id") Long id, Model model) {
        model.addAttribute("currentUser", getUserDate());
        Category category = categoryRepository.findById(id).orElse(null);
        model.addAttribute("category", category);
        return "editcategory";
    }

    @GetMapping(value = "/editmusic/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
    public String editmusic(@PathVariable("id") Long id, Model model) {
        model.addAttribute("currentUser", getUserDate());
        Music music = musicRepository.findById(id).orElse(null);
        model.addAttribute("qweqwe", music);
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("category", categories);
        return "editmusic";
    }


    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("currentUser", getUserDate());
        return "login";
    }
//    @GetMapping("/search")
//    public String search(Model model) {
//        model.addAttribute("currentUser", getUserDate());
//        List<Category> categories = categoryRepository.findAll();
//        model.addAttribute("category", categories);
//        List<Music> music = musicRepository.findAll();
//        model.addAttribute("music", music);
//        return "search";
//    }

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
