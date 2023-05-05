package com.samar.voitures.controllers;

import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.samar.voitures.entities.Marque;
import com.samar.voitures.entities.Voiture;
import com.samar.voitures.service.MarqueService;
import com.samar.voitures.service.VoitureService;

@Controller
public class VoitureController {
	

	@Autowired
	VoitureService voitureService;
	
	@Autowired
	MarqueService marqueService;
	
	@RequestMapping("/showCreateVoiture")
	public String showCreate(ModelMap modelMap)
	{
		List<Marque> marques = marqueService.getAllMarque();
		modelMap.addAttribute("marques", marques);
		modelMap.addAttribute("voiture", new Voiture());
		modelMap.addAttribute("mode", "new");
        modelMap.addAttribute("page",0);
	return "formVoiture";
	}


	

	
	@RequestMapping("/saveVoiture")
	public String saveVoiture(@Valid Voiture voiture,
			 BindingResult bindingResult ,ModelMap modelMap, @ModelAttribute("page") int  pageFromPrevious,
			    @RequestParam (name="size", defaultValue = "2") int size,
			    RedirectAttributes redirectAttributes)
	{	
		int page;
		List<Marque> marques = marqueService.getAllMarque();
		modelMap.addAttribute("marques", marques);
		if (bindingResult.hasErrors()) return "formVoiture";
		  if (voiture.getIdVoiture()==null) {
			  page = voitureService.getAllVoitures().size()/size;
		  }else {
			  page = pageFromPrevious;
		  }
		voitureService.saveVoiture(voiture);
		redirectAttributes.addAttribute("page", pageFromPrevious);
		return "redirect:/ListeVoitures";
	}
//	public String saveVoiture(@ModelAttribute("voiture") Voiture voiture, @RequestParam("date") String date,
//			ModelMap modelMap,
//			@RequestParam (name="page",defaultValue = "0") int page,
//			@RequestParam (name="size", defaultValue = "2") int size) throws ParseException
//	{
//		//conversion de la date
//		 SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
//		 Date dateFabrication = dateformat.parse(String.valueOf(date));
//		 voiture.setDateFabrication(dateFabrication);
//	
//		Voiture saveVoiture = voitureService.saveVoiture(voiture);
//		String msg ="voiture enregistrée avec Id "+saveVoiture.getIdVoiture();
//		modelMap.addAttribute("msg", msg);
//		
//		Page<Voiture> voits = voitureService.getAllVoituresParPage(page, size);
//		modelMap.addAttribute("voitures", voits);
//		modelMap.addAttribute("pages", new int[voits.getTotalPages()]);
//		modelMap.addAttribute("currentPage", page);
//		
//		return "listeVoitures";}

	
	@RequestMapping("/ListeVoitures")
	public String listeVoitures(ModelMap modelMap,
	@RequestParam (name="page",defaultValue = "0") int page,
	@RequestParam (name="size", defaultValue = "2") int size)
	{
		Page<Voiture> voits = voitureService.getAllVoituresParPage(page, size);
		modelMap.addAttribute("voitures", voits);
		modelMap.addAttribute("pages", new int[voits.getTotalPages()]);
		modelMap.addAttribute("currentPage", page);
		return "listeVoitures";
	}
	@RequestMapping("/supprimerVoiture")
	public String supprimerProduit(@RequestParam("id") Long id,
	ModelMap modelMap,
	@RequestParam (name="page",defaultValue = "0") int page,
	@RequestParam (name="size", defaultValue = "2") int size)
	{
		voitureService.deleteVoitureById(id);
		Page<Voiture> prods = voitureService.getAllVoituresParPage(page,
		size);
		modelMap.addAttribute("voitures", prods);
		modelMap.addAttribute("pages", new int[prods.getTotalPages()]);
		modelMap.addAttribute("currentPage", page);
		modelMap.addAttribute("size", size);
		return "listeVoitures";
	}
	@RequestMapping("/modifierVoiture")
	public String editerProduit(@RequestParam("id") Long id,@RequestParam("page") int page,ModelMap modelMap)
	{
		List<Marque> marqs = marqueService.getAllMarque();
		  System.out.println(page);

	modelMap.addAttribute("marques",marqs);
	Voiture v= voitureService.getVoiture(id);
	modelMap.addAttribute("voiture", v);
	modelMap.addAttribute("mode", "edit");
	modelMap.addAttribute("page",page);
	return "formVoiture";
	}


	@RequestMapping("/updateVoiture")
	public String updateVoiture(@ModelAttribute("voiture") Voiture voiture,
								@RequestParam("date") String date,
			                    ModelMap modelMap) throws ParseException 
	{
		
		//conversion de la date 
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateFabrication = dateformat.parse(String.valueOf(date));
        voiture.setDateFabrication(dateFabrication);
        
		  voitureService.updateVoiture(voiture);
		  Page<Voiture> voits = voitureService.getAllVoituresParPage(0, 2);
		  modelMap.addAttribute("voitures", voits);	
		  modelMap.addAttribute("pages", new int[voits.getTotalPages()]);

		
		return "listeVoitures";
	}
	
	}


