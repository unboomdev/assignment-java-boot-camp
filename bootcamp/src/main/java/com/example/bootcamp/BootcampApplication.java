package com.example.bootcamp;

import com.example.bootcamp.payment.Payment;
import com.example.bootcamp.payment.PaymentRepository;
import com.example.bootcamp.product.entities.Product;
import com.example.bootcamp.product.entities.ProductImage;
import com.example.bootcamp.product.ProductImageRepository;
import com.example.bootcamp.product.ProductRepository;
import com.example.bootcamp.user.entities.Address;
import com.example.bootcamp.user.AddressRepository;
import com.example.bootcamp.user.entities.User;
import com.example.bootcamp.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class BootcampApplication {

	@Autowired
	public UserRepository userRepository;

	@Autowired
	public AddressRepository addressRepository;

	@Autowired
	public ProductRepository productRepository;

	@Autowired
	public ProductImageRepository productImageRepository;

	@Autowired
	public PaymentRepository paymentRepository;

	@PostConstruct
	public void mockUser() {
		User user = new User(20001, "johnj345@test.com", "John", "Judd");
		userRepository.save(user);

		Address address = new Address(1, "johnj345@test.com", "123/4", "10234", "Rama X", "Bangkok", "0987654321", true, 20001);
		addressRepository.save(address);

		Product product1 = new Product(10001, "Adidas NMD R1 PK Japan", 15000, 14, "สวมใส่สบาย");
		Product product2 = new Product(10002, "Adidas NMD R1 Color Black", 12000, 33, "สวมใส่สบาย");
		Product product3 = new Product(10003, "NMD Sneakers Fashion", 1900, 79, "สวมใส่สบาย");
		productRepository.save(product1);
		productRepository.save(product2);
		productRepository.save(product3);

		productImageRepository.save(new ProductImage(1, "http://image.com/1", 10001));
		productImageRepository.save(new ProductImage(2, "http://image.com/2", 10002));
		productImageRepository.save(new ProductImage(3, "http://image.com/3", 10002));
		productImageRepository.save(new ProductImage(4, "http://image.com/4", 10003));

		Payment payment1 = new Payment(30001, "บัตรเครดิตหรือเดบิต");
		Payment payment2 = new Payment(30002, "ชำระเงินปลายทาง");
		Payment payment3 = new Payment(30003, "ชำระเงินผ่านเคาน์เตอร์");
		paymentRepository.save(payment1);
		paymentRepository.save(payment2);
		paymentRepository.save(payment3);
	}

	public static void main(String[] args) {
		SpringApplication.run(BootcampApplication.class, args);
	}

}
