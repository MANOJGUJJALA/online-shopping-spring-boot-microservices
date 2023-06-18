package com.springboot.inventoryservice;


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



}
