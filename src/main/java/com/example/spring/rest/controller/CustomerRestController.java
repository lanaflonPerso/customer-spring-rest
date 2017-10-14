package com.example.spring.rest.controller;

import com.example.spring.rest.CustomerNotFound;
import com.example.spring.rest.domain.Customer;
import com.example.spring.rest.repo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping ("/v1/customers")
public class CustomerRestController
{
    private CustomerRepository customerRepository;

    @Autowired
    public CustomerRestController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @RequestMapping(method = RequestMethod.OPTIONS)
    ResponseEntity<?> options() {
        return ResponseEntity.ok()
                             .allow(HttpMethod.DELETE,
                                    HttpMethod.GET,
                                    HttpMethod.HEAD,
                                    HttpMethod.PUT,
                                    HttpMethod.POST,
                                    HttpMethod.OPTIONS).build();
    }

    @GetMapping
    ResponseEntity<Collection<Customer>> getCollection () {
        return ResponseEntity.ok(customerRepository.findAll());
    }

    @GetMapping (value = "/{id}")
    ResponseEntity<Customer> get(@PathVariable Long id) {
        return Optional.ofNullable(customerRepository.findOne(id))
                                             .map(ResponseEntity::ok)
                                             .orElseThrow(() -> new CustomerNotFound(id));
    }

    @PostMapping
    ResponseEntity<Customer> post (@RequestBody Customer c) {
        Customer customer = new Customer(c.getFirstName(), c.getLastName());
        customerRepository.save(customer);

        URI uri;
        uri = MvcUriComponentsBuilder.fromController(getClass()).path("/{id}")
                                        .buildAndExpand(customer.getId()).toUri();

        return ResponseEntity.created(uri).body(customer);
    }

    @DeleteMapping (value = "/{id}")
    ResponseEntity<?> delete (@PathVariable Long id)
    {
        return Optional.ofNullable(customerRepository.findOne(id))
                .map(c -> {
                    customerRepository.delete(id);
                    return ResponseEntity.noContent().build();
                })
                .orElseThrow(() -> new CustomerNotFound(id));

    }

    @RequestMapping (value = "/{id}", method = RequestMethod.HEAD)
    ResponseEntity<?> head (@PathVariable Long id) {
        return Optional.ofNullable(customerRepository.findOne(id))
                .map(exists -> ResponseEntity.noContent().build())
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping (value = "/{id}")
    ResponseEntity<Customer> put(@PathVariable Long id, @RequestBody Customer c) {
        return Optional.ofNullable(customerRepository.findOne(id))
                .map(existing -> {
                    Customer customer = new Customer();
                    customer.setId(existing.getId());
                    customer.setFirstName(c.getFirstName());
                    customer.setLastName(c.getLastName());
                    customerRepository.save(customer);

                    URI selfLink = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().toUriString());

                    return ResponseEntity.created(selfLink).body(customer);
                })
                .orElseThrow(() -> new CustomerNotFound(id));
    }
}
