package com.tech.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tech.model.Product;
import com.tech.repository.ProductRepository;
import com.tech.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private  ProductRepository repo;

	@Override
	@Transactional
	public Long saveProduct(Product product) {

		return repo.save(product).getId();
	}

	@Override
	@Transactional
	public void updateProduct(Product product) {
		if(repo.findById(product.getId()).isPresent()) {
			repo.save(product);	
		}else throw new RuntimeException("Id is Not Exist");
	}

	@Override
	@Transactional
	public void deleteProduct(Long id) {
		repo.deleteById(id);
	}

	@Override
	public List<Product> getAllProduct() {
		return repo.findAll();
	}

	@Override
	public Optional<Product> getOneProduct(Long id) {
		return  repo.findById(id);
	}

	@Override
	public boolean isExist(Long id) {
		return repo.existsById(id);
	}

	@Override
	public ByteArrayInputStream customerToExcel(List<Product> product) throws IOException {

		String[]  colums = {"prodName","prodBrand","prodMadeIn","prodPrice"};
		@SuppressWarnings("resource")
		Workbook  workbook = new XSSFWorkbook();
		ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
		CreationHelper  ch = workbook.getCreationHelper();
		Sheet sh = workbook.createSheet();
		/*Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setColor(IndexedColors.BLUE.getIndex());

		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);*/

		// Row for Header
		Row headerRow = sh.createRow(0);

		// Header
		for (int col = 0; col < colums.length; col++) {
			Cell cell = headerRow.createCell(col);
			cell.setCellValue(colums[col]);
			//cell.setCellStyle(headerCellStyle);
		}

		// CellStyle for Age
		CellStyle ageCellStyle = workbook.createCellStyle();
		ageCellStyle.setDataFormat(ch.createDataFormat().getFormat("#"));

		int rowIdx = 1;
		for (Product pro : product) {
			Row row = sh.createRow(rowIdx++);

			row.createCell(0).setCellValue(pro.getProdName());
			row.createCell(1).setCellValue(pro.getProdBrand());
			row.createCell(2).setCellValue(pro.getProdMadeIn());
			row.createCell(3).setCellValue(pro.getProdPrice());

			Cell ageCell = row.createCell(3);
			ageCell.setCellValue(pro.getProdBrand());
			ageCell.setCellStyle(ageCellStyle);
		}

		workbook.write(byteArray);
		return new ByteArrayInputStream(byteArray.toByteArray());		
	}

}


