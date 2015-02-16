/**
 * Created by tomson.ngassa on 6/17/14.
 */

'use strict';

xdescribe('BHCDS > Login', function() {
    var params = browser.params;
    var username = params.user.username;
    var password = params.user.password;

    var loginUrl = browser.baseUrl +  'login';
    var registrationUrl = browser.baseUrl +  'register';

    beforeEach(function () {
        browser.get('#/login');
    });

    it('should not login in user with incorrent credentials', function() {
        expect(browser.getCurrentUrl()).toEqual(loginUrl);
        element(by.model('username')).sendKeys('blablabla');
        element(by.model('password')).sendKeys('passpass');
        element(by.id('loginSubmitBtn')).click();
        expect(browser.getCurrentUrl()).toEqual(loginUrl);
    });

    it('should logout user after login', function() {
        //Login user
        element(by.model('username')).sendKeys(username);
        element(by.model('password')).sendKeys(password);
        element(by.id('loginSubmitBtn')).click();
        browser.get('#/logout');

        // To remain login
        expect(browser.getCurrentUrl()).toEqual(loginUrl);
        element(by.model('username')).sendKeys(username);
        element(by.model('password')).sendKeys(password);
        element(by.id('loginSubmitBtn')).click();
    });

    it('should go to registration page from login page', function() {
        expect(browser.getCurrentUrl()).toEqual(loginUrl);
        element(by.id('registrationBtn')).click()
        expect(browser.getCurrentUrl()).toEqual(registrationUrl);
        browser.get('#/login');
        // To remain login
        expect(browser.getCurrentUrl()).toEqual(loginUrl);
        element(by.model('username')).sendKeys(username);
        element(by.model('password')).sendKeys(password);
        element(by.id('loginSubmitBtn')).click();
    });

});

