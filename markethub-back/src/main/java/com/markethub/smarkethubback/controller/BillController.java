package com.markethub.smarkethubback.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.markethub.smarkethubback.model.Account;
import com.markethub.smarkethubback.model.Bill;
import com.markethub.smarkethubback.model.Product;
import com.markethub.smarkethubback.service.BillToMakeObject;
import com.markethub.smarkethubback.service.IBillService;

import jakarta.persistence.EntityNotFoundException;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/api/bills")
public class BillController {

    private final IBillService billService;

    public BillController(IBillService billService) {
        this.billService = billService;
    }
    
    @GetMapping
    public ResponseEntity<List<Bill>> getAllBills() {
        List<Bill> bills = billService.findAll();
        return new ResponseEntity<>(bills, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bill> getBillById(@PathVariable Long id) {
        try {
            Bill bill = billService.findById(id);
            return ResponseEntity.ok(bill);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Bill> createBill(@RequestBody Bill bill) {
        Bill savedBill = billService.save(bill);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBill);
    }

    
    @PostMapping("/generate")
    public ResponseEntity<Bill> generateBill(@RequestBody BillToMakeObject billToMakeObject) {
        try {
            long productId = billToMakeObject.getProductId();
            long accountBuyerId = billToMakeObject.getAccountBuyerId();

            Bill generatedBill = billService.generateBill(productId, accountBuyerId);

            return ResponseEntity.ok(generatedBill);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PostMapping("/invalidate/{id}")
    public ResponseEntity<String> invalidateBill(@PathVariable Long id) {
        try {
            billService.invalidate(id);
            return new ResponseEntity<>("Bill invalidated successfully", HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Bill not found", HttpStatus.NOT_FOUND);
        }
    }
}