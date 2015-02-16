/**
 * Created by tomson.ngassa on 6/17/14.
 */

'use strict';


describe('BHCDS > Caremanager', function() {
    var caremanagerPath = 'caremanager/2';

    beforeEach(function(){
        browser.get('#/' + caremanagerPath);

    }),


    it('should show caremanager page', function() {
        expect(browser.getCurrentUrl()).toEqual(browser.baseUrl + caremanagerPath);
    });

    it("should change tabs", function(){
        expect(browser.getCurrentUrl()).toEqual(browser.baseUrl + caremanagerPath);

        //Default tab "basic Info" should be selected
        var basicInfoTab = element(by.id('caremanager-basicinfo-tab'));
        expect(basicInfoTab.getAttribute('class')).toBe('active');

        //Select the contact tab
        var contactTab = element(by.id('caremanager-contact-tab'));
        contactTab.click();
        expect(contactTab.getAttribute('class')).toBe('active');

        //Select the rationale tab
        var passwordTab = element(by.id('caremanager-password-tab'));
        passwordTab.click();
        expect(passwordTab.getAttribute('class')).toBe('active');
    });

    it("should save caremanager profile", function(){
        expect(browser.getCurrentUrl()).toEqual(browser.baseUrl + caremanagerPath);

        var caremanagerSaveBtn = element(by.id('caremanager-save-btn'));
        caremanagerSaveBtn.click();
        expect(browser.getCurrentUrl()).toEqual(browser.baseUrl + caremanagerPath);
    });

    it("should update caremanager general information", function(){
        var prefixElt = element(by.model('caremanager.namePrefix'));
        var firstNameElt = element(by.model('caremanager.firstName'));
        var lastNameElt = element(by.model('caremanager.lastName'));
        var genderElt = element(by.model('caremanager.administrativeGenderCode'));

        expect(browser.getCurrentUrl()).toEqual(browser.baseUrl + caremanagerPath);

        var prefixEltValue = prefixElt.getAttribute('value');
        var firstNameValue = firstNameElt.getAttribute('value');
        var lastNameValue = lastNameElt.getAttribute('value');
        var genderValue = genderElt.getAttribute('value');

//        expect(genderElt.getAttribute('value')).toContain('M');

//        var specialityValue = specialityElt.getText();

        prefixElt.clear();
        firstNameElt.clear();
        lastNameElt.clear();
//        specialityElt.clear();

        prefixElt.sendKeys(prefixEltValue);
        firstNameElt.sendKeys(firstNameValue);
        lastNameElt.sendKeys(lastNameValue);


//        if(genderValue ==='M')(
//            element.all(by.model('caremanager.administrativeGenderCode')).get(1).click();
//            expect(genderElt.getAttribute('value')).toContain('F');
//        )else if(genderValue ==='F') {
//            element.all(by.model('caremanager.administrativeGenderCode')).get(0).click();
//            expect(genderElt.getAttribute('value')).toContain('M');
//        }

//        genderElt.sendKeys(genderValue);
//        specialityElt.sendKeys(specialityValue);

        //Save changes
        var caremanagerSaveBtn = element(by.id('caremanager-save-btn'));
        caremanagerSaveBtn.click();
        expect(browser.getCurrentUrl()).toEqual(browser.baseUrl + caremanagerPath);
    });
});

