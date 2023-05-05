package com.samar.voitures.controllers;
import java.text.ParseException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


import com.samar.voitures.entities.Marque;
import com.samar.voitures.service.MarqueService;



@Controller
public class MarqueController {
	
	@Autowired
	MarqueService marqueService;
	
	@RequestMapping("/showCreateMarque")
	public String showCreateMarque(ModelMap modelMap)
	{
		modelMap.addAttribute("marque", new Marque());
		return "formMarque";

	}
	
	@RequestMapping("/saveMarque")
	public String saveMarque(@Valid Marque marque,
			 BindingResult bindingResult , ModelMap modelMap , 
			 @RequestParam (name="page",defaultValue = "0") int page,
				@RequestParam (name="size", defaultValue = "2") int size)
	
	{
		if (bindingResult.hasErrors()) return "formMarque";

		marqueService.saveMarque(marque);
		Page<Marque> marqs = marqueService.getAllMarquesParPage(page, size);
		modelMap.addAttribute("marques", marqs);
		 modelMap.addAttribute("pages", new int[marqs.getTotalPages()]);
		modelMap.addAttribute("currentPage", page);
		return "listeMarques";
	}
	
	@RequestMapping("/ListeMarques")
	public String listeMarques(ModelMap modelMap,
	@RequestParam (name="page",defaultValue = "0") int page,
	@RequestParam (name="size", defaultValue = "2") int size)
	{
		Page<Marque> marqs = marqueService.getAllMarquesParPage(page, size);
		modelMap.addAttribute("marques", marqs);
		 modelMap.addAttribute("pages", new int[marqs.getTotalPages()]);
		modelMap.addAttribute("currentPage", page);
		return "listeMarques";
	}
	@RequestMapping("/supprimerMarque")
	public String supprimerMarque(@RequestParam("id") Long id,
	ModelMap modelMap,
	@RequestParam (name="page",defaultValue = "0") int page,
	@RequestParam (name="size", defaultValue = "2") int size)
	{
		marqueService.deleteMarqueById(id);
		Page<Marque> marqs = marqueService.getAllMarquesParPage(page,size);
		modelMap.addAttribute("marques", marqs);
		modelMap.addAttribute("pages", new int[marqs.getTotalPages()]);
		modelMap.addAttribute("currentPage", page);
		modelMap.addAttribute("size", size);
		return "listeMarques";
	}
	@RequestMapping("/modifierMarque")
	public String editerMarque(@RequestParam("id") Long id,ModelMap modelMap)
	{
		Marque m= 	marqueService.getMarque(id);
		modelMap.addAttribute("marque", m);	
		return "editerMarque";	
	}
	@PostMapping("/updateMarque")
	public String updateMarque(@ModelAttribute("marque") Marque marque,
			                    ModelMap modelMap) 
	{
		
	
        
		  marqueService.updateMarque(marque);
		  Page<Marque> marqs = marqueService.getAllMarquesParPage(0, 2);
		  modelMap.addAttribute("marques", marqs);	
		  modelMap.addAttribute("pages", new int[marqs.getTotalPages()]);

		
		return "listeMarques";
	}
}
