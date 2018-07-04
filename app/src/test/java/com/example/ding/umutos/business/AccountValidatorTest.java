package com.example.ding.umutos.business;

import com.example.ding.umutos.persistence.AccountPersistenceStub;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static junit.framework.Assert.*;

public class AccountValidatorTest {

    private AccessAccounts accessAccounts;
    private AccountValidator accountValidator;

    @Before
    public void setup()
    {
        accessAccounts = new AccessAccounts(new AccountPersistenceStub());
        accountValidator = new AccountValidator();
    }

    @After
    public void tearDown()
    {
        accountValidator = null;
    }

    @Test
    public void testNotNull()
    {
        System.out.println("Test Not Null case.");
        assertNotNull(accountValidator);
        System.out.println("Finish test not Null cases.");

    }

    @Test
    public void testValidateUserName()
    {
        System.out.println("Test validating user name.");
        //exist user name
        assertTrue(!accountValidator.validateUserName("Yunlong Liu", accessAccounts.getAccounts()));

        //non exist user name
        assertTrue(accountValidator.validateUserName("zapp", accessAccounts.getAccounts()));
        System.out.println("End test validating user name.");
    }

    @Test
    public void testValidatePassWord()
    {
        System.out.println("Test validating password.");
        assertTrue(!accountValidator.validatePassword("123"));
        assertTrue(!accountValidator.validatePassword("abc"));
        assertTrue(!accountValidator.validatePassword("ABC"));
        assertTrue(accountValidator.validatePassword("1aC"));
        System.out.println("End test validating password.");
    }
}
