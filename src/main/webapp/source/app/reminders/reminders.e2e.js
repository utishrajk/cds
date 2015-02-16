/**
 * Created by tomson.ngassa on 6/17/14.
 */

'use strict';

describe('BHCDS > Reminders', function() {
    var remindersPath = 'reminders';

    beforeEach(function(){
        browser.get('#/' + remindersPath);
    }),

    it('should show reminders page', function() {
        expect(browser.getCurrentUrl()).toEqual(browser.baseUrl + remindersPath);
    });

    it('should filter reminders', function() {

        // Verify that there are 2 reminders initially
        expect(element.all(by.repeater('reminder in filteredReminders')).count()).toEqual(2);

        //Enter seach text
        element(by.model('criteria')).sendKeys('linda');

        // Now there are one reminders
        expect(element.all(by.repeater('reminder in filteredReminders')).count()).toEqual(1);
    });

    it("should open the first reminder", function(){
        //Selecting the first reminder and open it
        element(by.id('reminder_0')).click()
        expect(browser.getCurrentUrl()).toEqual(browser.baseUrl  + 'reminder/0/recommendations');
    });

    it("should accept selected reminder", function(){
        //Selecting the first reminder and open it
        element(by.id('reminder_0')).click()
        expect(browser.getCurrentUrl()).toEqual(browser.baseUrl  + 'reminder/0/recommendations');
        //Accepting the reminder
        element(by.id('reminder-accept-btn')).click()
        expect(browser.getCurrentUrl()).toEqual(browser.baseUrl  + remindersPath);
    });

    it("should return to list of reminders from open reminder", function(){
        //Selecting the first reminder and open it
        element(by.id('reminder_0')).click()
        expect(browser.getCurrentUrl()).toEqual(browser.baseUrl  + 'reminder/0/recommendations');
        //Clicking the back button to go to the list of reminder
        element(by.id('reminder-back-btn')).click()
        expect(browser.getCurrentUrl()).toEqual(browser.baseUrl  + remindersPath);
    });

    it("should change selected reminder tabs", function(){
        //Selecting the first reminder and open it
        element(by.id('reminder_0')).click()
        expect(browser.getCurrentUrl()).toEqual(browser.baseUrl  + 'reminder/0/recommendations');

        //Default tab "general" should be selected
        var generalTab = element(by.id('reminder-general-tab'));
        expect(generalTab.getAttribute('class')).toBe('active');

        //Select the clinial tab
        var clinicalTab = element(by.id('reminder-clinical-tab'));
        clinicalTab.click();
        expect(clinicalTab.getAttribute('class')).toBe('active');

        //Select the rationale tab
        var rationaleTab = element(by.id('reminder-rationale-tab'));
        rationaleTab.click();
        expect(rationaleTab.getAttribute('class')).toBe('active');

        //Select the tools tab
        var toolsTab = element(by.id('reminder-tools-tab'));
        toolsTab.click();
        expect(toolsTab.getAttribute('class')).toBe('active');
    });


});

