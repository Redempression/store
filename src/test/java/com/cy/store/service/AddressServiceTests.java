package com.cy.store.service;

import com.cy.store.entity.Address;
import com.cy.store.entity.User;
import com.cy.store.service.ex.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author 魏敏捷
 * @version 1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class AddressServiceTests {

    @Autowired
    private IAddressService addressService;


    @Test
    public void addNewAddress(){
        Address address = new Address();
        address.setPhone("175726");
        address.setName("男朋友");
        addressService.addNewAddress(address,11,"mxy");
    }


    @Test
    public void setDefault(){
        addressService.setDefault(2,13,"wwwwww");

    }

    @Test
    public void delete(){

        addressService.delete(9,13,"www");
    }


}