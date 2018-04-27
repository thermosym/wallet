package com.demo.wallet.controller.message;

import com.demo.wallet.controller.exception.RequestValidationException;
import org.junit.Test;

import static org.junit.Assert.*;

public class TransferReqTest {

    @Test
    public void testValidateFailOnBlankEmail() {
        TransferReq req = new TransferReq();
        req.setEmail(" ");
        req.setTransferee("B");
        req.setAmount(1);
        try {
            req.validate();
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof RequestValidationException);
        }
    }

    @Test
    public void testValidateFailOnBlankTransferee() {
        TransferReq req = new TransferReq();
        req.setEmail("A");
        req.setTransferee(" ");
        req.setAmount(1);
        try {
            req.validate();
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof RequestValidationException);
        }
    }

    @Test
    public void testValidateFailOnInvalidAmount() {
        TransferReq req = new TransferReq();
        req.setEmail("A");
        req.setTransferee("B");
        req.setAmount(-1);
        try {
            req.validate();
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof RequestValidationException);
        }

        req.setAmount(0);
        try {
            req.validate();
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof RequestValidationException);
        }
    }

    @Test
    public void testValidationPass() {
        TransferReq req = new TransferReq();
        req.setEmail("A");
        req.setTransferee("B");
        req.setAmount(1);
        req.validate();
    }
}