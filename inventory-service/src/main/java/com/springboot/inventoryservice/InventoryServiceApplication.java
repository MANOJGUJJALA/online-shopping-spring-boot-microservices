package com.springboot.inventoryservice;

import com.springboot.inventoryservice.model.Inventory;
import com.springboot.inventoryservice.repository.InventoryRepository;
import com.springboot.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication

public class InventoryServiceApplication {



	public static void main(String[] args) {SpringApplication.run(InventoryServiceApplication.class, args);}

	@Bean
	public CommandLineRunner loadData(InventoryRepository inventoryRepository){

		return args->{
			Inventory inventory=new Inventory();
			inventory.setSkuCode("samsung_a_30s");
			inventory.setQuantity(2);

			Inventory inventory1=new Inventory();
			inventory1.setSkuCode("lenovo");
			inventory1.setQuantity(0);

			inventoryRepository.save(inventory);
			inventoryRepository.save(inventory1);
		};
	}

}
