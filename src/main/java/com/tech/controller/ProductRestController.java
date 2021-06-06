package com.tech.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tech.model.Product;
import com.tech.service.ProductService;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/product")
@AllArgsConstructor
public class ProductRestController {

	private final ProductService productService;
	//private final ExcelConverter converter;
	
	@GetMapping("/register")
	public String registerProdect(Model model){
		model.addAttribute("product", new Product());
		return "productsave";
	}

	@PostMapping("/save")
	public String uploadProdect(@ModelAttribute Product product,Model model){
		Long id =  productService.saveProduct(product);
		String str = "Product "+id+" is Saved In DataBase";
		model.addAttribute("message", str);
		model.addAttribute("product", new Product());
		return "productsave";
	}

	@PostMapping("/update")
	public String updateProduct(@ModelAttribute Product product,
			Model model){
		productService.updateProduct(product);
		String update = "Product is Updated";
		model.addAttribute("message", update);
		List<Product> list = productService.getAllProduct();
		model.addAttribute("list", list);
		return "productall";
	}

	@GetMapping("/delete/{id}")
	public String remove(@PathVariable Long id,Model model){
		if(productService.isExist(id)){
			productService.deleteProduct(id);
			model.addAttribute("message", "Product "+ id +" is deleted");
		}else throw new RuntimeException("ID NOT EXIST "+ id);
		List<Product> list = productService.getAllProduct();
		model.addAttribute("list", list);
		return "productall";
	}

	@GetMapping("/all")
	public String getAll(Model model){
		List<Product> list = productService.getAllProduct();
		model.addAttribute("list", list);
		return "productall";
	}

	@GetMapping("/edit/{id}")
	public String getOne(@PathVariable Long id,Model model){
		Optional<Product> opt = productService.getOneProduct(id);
		if(opt.isPresent()) {
			model.addAttribute("product", opt.get());
		}else throw new RuntimeException("ID NOT EXIST "+ id);
		return "productedit";
	}
}
